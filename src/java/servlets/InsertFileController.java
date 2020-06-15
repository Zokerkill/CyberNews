/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.User;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import securitylogic.RoleLogic;
import utils.Encription;
import utils.PropertyLoader;

/**
 *
 * @author Melnikov
 */
@WebServlet(name = "InsertFileController", urlPatterns = {
    "/insertFile/*",
})
public class InsertFileController extends HttpServlet {
    
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
    
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
        Encription encription = new Encription();
        Calendar c = new GregorianCalendar();
        String path = request.getServletPath();
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
        if(!rl.isRole(RoleLogic.ROLE.USER.toString(), regUser)){
            request.setAttribute("info", "Вы должны войти в систему!");
            request.getRequestDispatcher("/showLogin").forward(request, response);
            return;
        }
        //request.setAttribute("role", rl.getRole(regUser));
        
        String requestedFile = request.getPathInfo();
        if(requestedFile == null){
            response.sendError((HttpServletResponse.SC_NOT_FOUND));
            return;
        }
        String imageFolderPath = PropertyLoader.getFolderPath("path");
        File file = new File(imageFolderPath,
                URLDecoder.decode(requestedFile, "UTF-8"));
        if(!file.exists()){
            response.sendError((HttpServletResponse.SC_NOT_FOUND));
            return;
        }
        String contentType = getServletContext().getMimeType(file.getName());
        if(contentType == null){
            contentType = "application/octet-stream";
        }
        response.reset();
        response.setContentType(contentType);
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "attachment; filename=\"" 
                + file.getName() + "\"");
        BufferedInputStream input = null;
        BufferedOutputStream output = null;
        try {
            input = new BufferedInputStream(new FileInputStream(file),DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(),DEFAULT_BUFFER_SIZE);
            byte[] buffer =  new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while((length = input.read(buffer))>0){
                output.write(buffer,0,length);
            }
        } finally {
            if(output != null){
                output.close();
            }
            if(input != null){
                input.close();
            }
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
