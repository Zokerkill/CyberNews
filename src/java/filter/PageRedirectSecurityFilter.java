/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import entity.Role;
import entity.User;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import securitylogic.RoleLogic;

/**
 *
 * @author Melnikov
 */
@WebFilter(filterName = "PageRedirectSecurityFilter", urlPatterns = {"/page/*"})
public class PageRedirectSecurityFilter implements Filter {
    
   

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        RoleLogic rl = new RoleLogic();
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        HttpSession session = req.getSession(false);
        if(session == null){
            res.sendRedirect(req.getContextPath()+"/showLogin.jsp");
        }
        User regUser = (User) session.getAttribute("regUser");
        if(regUser == null){
            res.sendRedirect(req.getContextPath()+"/showLogin.jsp");
        }
        Role role = rl.getRole(regUser);
        if(role.getName().equals(RoleLogic.ROLE.ADMINISTRATOR.toString())){
            req.setAttribute("role", role);
            req.getRequestDispatcher("/page/admin/index.jsp")
                    .forward(req, res);
        }
        if(role.getName().equals(RoleLogic.ROLE.MANAGER.toString())){
            req.setAttribute("role", role);
            req.getRequestDispatcher("/page/manager/index.jsp")
                    .forward(req, res);
        }
        if(role.getName().equals(RoleLogic.ROLE.USER.toString())){
            req.setAttribute("role", role);
            req.getRequestDispatcher("/page/user/index.jsp")
                    .forward(req, res);
        }
        chain.doFilter(request, response);
    }
    @Override
    public void destroy(){

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
   
}
