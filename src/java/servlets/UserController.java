/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Project;
import entity.Cover;
import entity.CoverProject;
import entity.History;
import entity.User;
import entity.UserProjects;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import securitylogic.RoleLogic;
import session.CoverFacade;
import session.ProjectFacade;
import session.CoverProjectFacade;
import session.HistoryFacade;
import session.StudentFacade;
import session.UserFacade;
import session.UserProjectsFacade;
import session.UserRolesFacade;
import utils.DateUtils;
import utils.Encription;
import utils.PagePathLoader;

@WebServlet(name = "UserController", urlPatterns = {
    "/showListProjects",
    "/showChangePassword",
    "/changePassword",
    "/showProject",
    
    "/showAddNewProject",
    "/addNewProject",
    "/showEditProject",
    "/editProject",
    
    "/showUploadFile",
    "/deleteProject",
    "/showStat",
    "/showStatistic"
    
})
public class UserController extends HttpServlet {
    
    
    @EJB private UserRolesFacade userRolesFacade;
    @EJB private UserFacade userFacade;
   
     @EJB private ProjectFacade projectFacade;
    @EJB private StudentFacade studentFacade;
    @EJB private HistoryFacade historyFacade;
   @EJB private UserProjectsFacade userProjectsFacade;
    @EJB private CoverFacade coverFacade;
    @EJB private CoverProjectFacade coverProjectFacade;
  
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
        Encription encription = new Encription();
        Calendar c = new GregorianCalendar();
        
        HttpSession session = request.getSession(false);
        if(session == null){
            request.setAttribute("info", "Войдите!");
            request.getRequestDispatcher("/showLogin").forward(request, response);
        }
        User regUser = (User) session.getAttribute("regUser");
        if(regUser == null){
            request.setAttribute("info", "Войдите!");
            request.getRequestDispatcher("/showLogin").forward(request, response);
        }
        RoleLogic rl = new RoleLogic();
        boolean isRole = rl.isRole(RoleLogic.ROLE.USER.toString(), regUser);
        if(!isRole){
            request.setAttribute("info", "Вы должны залогииться!");
            request.getRequestDispatcher("/showLogin").forward(request, response);
        }
        //request.setAttribute("role", rl.getRole(regUser));
        
        String path = request.getServletPath();
        
        
        switch (path) {
            case "/showListProjects":
                List<UserProjects> listProjects = userProjectsFacade.selectUserProjects(regUser);
                request.setAttribute("listProjects", listProjects);
                request.setAttribute("info", "");
                request.getRequestDispatcher(PagePathLoader.getPagePath("showListProjects")).forward(request, response);
                break;
            case "/showChangePassword":
                String username = regUser.getStudent().getName()+" "+regUser.getStudent().getSurname();
                request.setAttribute("username", username);
                String login = regUser.getLogin();
                request.setAttribute("login", login);
                request.getRequestDispatcher(PagePathLoader.getPagePath("changePassword")).forward(request, response);
                break;
            case "/changePassword":
                String oldPassword = request.getParameter("oldPassword");
                String encriptOldPassword = encription.getEncriptionPass(oldPassword);
                if(!encriptOldPassword.equals(regUser.getPassword())){
                    request.setAttribute("info", "Вы должны войти");
                    request.getRequestDispatcher("/showLogin").forward(request, response);
                    break;
                }
                String newPassword1 = request.getParameter("newPassword1");
                String newPassword2 = request.getParameter("newPassword2");
                if(newPassword1.equals(newPassword2)){
                    regUser.setPassword(encription.getEncriptionPass(newPassword1));
                    userFacade.edit(regUser);
                }
                request.setAttribute("info", "Вы успешно изменили пароль");
                request.getRequestDispatcher("/logout");
                request.getRequestDispatcher("/showLogin").forward(request, response);
                break;  
                case "/showAddNewProject":
                List<Cover> listCovers = coverFacade.findAll();
                request.setAttribute("listCovers", listCovers);
                request.getRequestDispatcher(PagePathLoader.getPagePath("showAddNewProject")).forward(request, response);
                break;
            case "/addNewProject":
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                
                String git = request.getParameter("git");
               Project project = new Project(name, description, git, false);
                projectFacade.create(project);
                UserProjects userProjects = new UserProjects(regUser, project);
                userProjectsFacade.create(userProjects);
                History history = new History(project, regUser.getStudent(), c.getTime());
                
                    historyFacade.create(history);
                String coverId = request.getParameter("coverId");
                Cover cover = coverFacade.find(new Long(coverId));
                CoverProject coverProject = new CoverProject(project, cover);
                coverProjectFacade.create(coverProject);
                request.setAttribute("info", "Проект \""+project.getName()+"\"добавлен");
                request.getRequestDispatcher(PagePathLoader.getPagePath("userIndex")).forward(request, response);
                break;
            case "/showEditProject":
               String projectId = request.getParameter("projectId");
                project = projectFacade.find(new Long(projectId));
                 
                 cover = coverProjectFacade.findCover(project);
               
                request.setAttribute("cover", cover);
                request.setAttribute("project", project);
                Cover selectedCover = coverProjectFacade.findCover(project);
                request.setAttribute("selectedCover", selectedCover);
                listCovers = coverFacade.findAll();
                request.setAttribute("listCovers", listCovers);
                request.getRequestDispatcher(PagePathLoader.getPagePath("showEditProject")).forward(request, response);
                break;
            case "/editProject":
                 projectId = request.getParameter("projectId");
                
                name = request.getParameter("name");
                description = request.getParameter("description");
                
                git = request.getParameter("git");
                
                coverId = request.getParameter("coverId");
                cover = coverFacade.find(new Long(coverId));
                project = projectFacade.find(new Long(projectId));
                project.setName(name);
                project.setDescription(description);
                project.setGit(git);
               
                projectFacade.edit(project);
                coverProject = coverProjectFacade.findCoverProjectByProjectId(new Long(projectId));
                coverProject.setProject(project);
                coverProject.setCover(cover);
                coverProjectFacade.edit(coverProject);
                //listCovers = coverFacade.findAll();
                request.setAttribute("project", project);
                request.getRequestDispatcher("/showProject").forward(request, response);
                break;
            
            case "/deleteProject":
                projectId = request.getParameter("projectId");
                    try {
                       Project projectForDelete = projectFacade.find(new Long(projectId));

                        projectForDelete.setIsDeleted(true);
                        projectFacade.edit(projectForDelete);
                        
                        request.setAttribute("info", "Проект \"" + projectForDelete.getName()+ "\" удален!");
                    } catch (Exception e) {
                        request.setAttribute("info", "Проект не удалось удалить");
                        request.getRequestDispatcher("/showListProjects").forward(request, response);
                        break;
                    }

                    
                    request.getRequestDispatcher("/showListProjects").forward(request, response);
                break;    
                
            case "/showUploadFile":
                request.getRequestDispatcher(PagePathLoader.getPagePath("showUploadFile")).forward(request, response);
                break;
            case "/showProject":
                projectId = request.getParameter("projectId");
                project = projectFacade.find(new Long(projectId));
                 cover = coverProjectFacade.findCover(project);
               
                request.setAttribute("cover", cover);
                request.setAttribute("project", project);
                request.getRequestDispatcher(PagePathLoader.getPagePath("showProject")).forward(request, response);
                break;
            case "/showStatistic":
                 request.getRequestDispatcher(PagePathLoader.getPagePath("showStat")).forward(request, response);
            case "/showStat":
                String timeRange = request.getParameter("timeRange");
               
                if(timeRange != null){
                    String fromDay = request.getParameter("fromDay");
                    String fromMonth = request.getParameter("fromMonth");
                    String fromYear = request.getParameter("fromYear");
                    String toDay = request.getParameter("toDay");
                    String toMonth = request.getParameter("toMonth");
                    String toYear = request.getParameter("toYear");
                    LocalDate fromLd = LocalDate.of(
                            new Integer(fromYear),
                            new Integer(fromMonth),
                            new Integer(fromDay)
                    );
                    LocalDate toLd = LocalDate.of(
                            new Integer(toYear),
                            new Integer(toMonth),
                            new Integer(toDay)
                    );
                    Date fromDate = DateUtils.getStartOfDay(DateUtils.asDate(fromLd));
                    Date toDate = DateUtils.getStartOfDay(DateUtils.asDate(toLd));
//                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
//                    request.setAttribute("dateFrom", sdf.format(fromDate));
//                    request.setAttribute("dateTo", sdf.format(toDate));
                    List<History> listHistories = historyFacade.findByRange(fromDate,toDate);
                    request.setAttribute("listHistories", listHistories);
                    request.setAttribute("fromDay", fromDay);
                    request.setAttribute("fromMonth", fromMonth);
                    request.setAttribute("fromYear", fromYear);
                    request.setAttribute("toDay", toDay);
                    request.setAttribute("toMonth", toMonth);
                    request.setAttribute("toYear", toYear);
                }

                
                
                request.getRequestDispatcher(PagePathLoader.getPagePath("showStat"))
                        .forward(request, response);
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
