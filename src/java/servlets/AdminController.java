/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Student;
import entity.Role;
import entity.User;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@WebServlet(name = "AdminController", loadOnStartup = 1, urlPatterns = {
    "/showChangeRole",
    "/changeRole",

})
public class AdminController extends HttpServlet {
    @EJB private UserFacade userFacade;
    @EJB private RoleFacade roleFacade;
    @EJB private StudentFacade readerFacade;
    @EJB private UserRolesFacade userRolesFacade;
    
    @Override
    public void init() throws ServletException {
        List<User> listUsers = userFacade.findAll();
        if(!listUsers.isEmpty()){
            return;
        }
        Student reader = new Student("juri.melnikov@ivkhk.ee", "Juri", "Melnikov");
        readerFacade.create(reader);
        Encription encription = new Encription();
        String password = encription.getEncriptionPass("admin");
        User user = new User("admin", password, true, reader);
        userFacade.create(user);
        Role role = new Role(RoleLogic.ROLE.ADMINISTRATOR.toString());
        roleFacade.create(role);
        role.setName(RoleLogic.ROLE.MANAGER.toString());
        roleFacade.create(role);
        role.setName(RoleLogic.ROLE.USER.toString());
        roleFacade.create(role);
        RoleLogic rl = new RoleLogic();
        role = rl.getRole(RoleLogic.ROLE.ADMINISTRATOR.toString());
        rl.setRole(role, user);
    }
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
        if(session == null){
            request.setAttribute("info", "Войдите!");
            request.getRequestDispatcher("/showLogin").forward(request, response);
        }
        User regUser = (User) session.getAttribute("regUser");
        if(regUser == null){
            request.setAttribute("info", "Войдите!");
            request.getRequestDispatcher("/showLogin").forward(request, response);
            return;
        }
        if(!rl.isRole(RoleLogic.ROLE.ADMINISTRATOR.toString(), regUser)){
            request.setAttribute("info", "Вы должны быть администратором!");
            request.getRequestDispatcher("/showLogin").forward(request, response);
            return;
        }
        request.setAttribute("role", rl.getRole(regUser));
        
        String path = request.getServletPath();
        if(null != path) switch (path) {
            case "/showChangeRole":
                List<Role> listRoles = roleFacade.findAll();
                List<User> listUsers = userFacade.findAll();
                Role role;
                Map<User,Role> mapUsers = new HashMap<>();
                for(User user : listUsers){
                    role=rl.getRole(user);
                    mapUsers.put(user, role);
                }
                request.setAttribute("listRoles", listRoles);
                request.setAttribute("mapUsers", mapUsers);
                request.getRequestDispatcher(PagePathLoader.getPagePath("showChangeRole")).forward(request, response);
                break;
            case "/changeRole":
                String roleId = request.getParameter("roleId");
                String userId = request.getParameter("userId");
                role = roleFacade.find(Long.parseLong(roleId));
                User user = userFacade.find(Long.parseLong(userId));
                if(!"admin".equals(user.getLogin())){
                    rl.setRole(role,user);
                }
                request.getRequestDispatcher("/showChangeRole").forward(request, response);
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
