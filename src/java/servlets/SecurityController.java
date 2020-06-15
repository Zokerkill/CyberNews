/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Student;
import entity.Role;
import entity.User;
import entity.UserRoles;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import securitylogic.RoleLogic;
import session.StudentFacade;
import session.RoleFacade;
import session.UserFacade;
import session.UserRolesFacade;
import utils.Encription;
import utils.PagePathLoader;

/**
 *
 * @author Melnikov
 */
@WebServlet(name = "SecutityServlet", urlPatterns = {
    
    "/showLogin",
    "/login",
    "/logout",
    "/showRegistration",
    "/registration",
    

})
public class SecurityController extends HttpServlet {
    @EJB private UserFacade userFacade;
    @EJB private StudentFacade readerFacade;
    @EJB private UserRolesFacade userRolesFacade;
    @EJB private RoleFacade roleFacade;

    
    
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        RoleLogic rl = new RoleLogic();
        HttpSession session = request.getSession(false);
        User regUser = null;
        if(session != null){
            regUser=(User) session.getAttribute("regUser");
        }
        String path = request.getServletPath();
       
        switch (path) {
            case "/showLogin":
                request.getRequestDispatcher(PagePathLoader.getPagePath("showLogin")).forward(request, response);
                break;
            case "/login":
                String login = request.getParameter("login");
                String password = request.getParameter("password");
                regUser = userFacade.findUserByLogin(login);
                if(regUser == null){
                    request.setAttribute("info", "Неправильный логин или пароль!");
                    request.getRequestDispatcher(PagePathLoader.getPagePath("showLogin")).forward(request, response);
                }
                Encription encription = new Encription();
                String encriptPassword = encription.getEncriptionPass(password);
                if(!encriptPassword.equals(regUser.getPassword())){
                    request.setAttribute("info", "Неправильный логин или пароль!");
                    request.getRequestDispatcher(PagePathLoader.getPagePath("showLogin")).forward(request, response);
                }
                session = request.getSession(true);
                session.setAttribute("regUser", regUser);
                request.setAttribute("info", "Вы вошли как "+regUser.getLogin());
                request.setAttribute("role", rl.getRole(regUser));
                request.getRequestDispatcher("/SessionContextServlet").forward(request, response);

                break;
            case "/logout":
                session = request.getSession(false);
                if(session != null){
                    session.invalidate();
                    request.setAttribute("info", "Вы вышли!");
                    request.getRequestDispatcher(PagePathLoader.getPagePath("index")).forward(request, response);
                }
                break;
            case "/showRegistration":
                request.getRequestDispatcher(PagePathLoader.getPagePath("showRegistration")).forward(request, response);
                break;
            case "/registration":
                String name = request.getParameter("name");
                String surname = request.getParameter("surname");
                String email = request.getParameter("email");
                login = request.getParameter("login");
                String password1 = request.getParameter("password1");
                String password2 = request.getParameter("password2");
                if(!password1.equals(password2)){
                    request.setAttribute("info", "Несовпадает пароль!");
                    request.getRequestDispatcher(PagePathLoader.getPagePath("showRegistration")).forward(request, response);
                }
                Student reader = new Student(email, name, surname);
                readerFacade.create(reader);
                encription = new Encription();
                encriptPassword = encription.getEncriptionPass(password1);
                User user = new User(login, encriptPassword, true, reader);
                userFacade.create(user);
                UserRoles ur = new UserRoles();
                ur.setUser(user);
                Role role = roleFacade.findByName(RoleLogic.ROLE.USER.toString());
                ur.setRole(role);
                userRolesFacade.create(ur);
                request.setAttribute("info", "Регистрация успешна!");
                request.getRequestDispatcher(PagePathLoader.getPagePath("index")).forward(request, response);
                break;

        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
