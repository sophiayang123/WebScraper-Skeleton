/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entity.Feed;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.FeedLogic;

/**
 *
 * @author shutingyang
 */
@WebServlet(name = "CreateFeed", urlPatterns = {"/CreateFeed"})
public class CreateFeed extends HttpServlet {
    
    private String errorMessage = null;
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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>SCreate Feed</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<div style=\"text-align: center;\">");
            out.println("<div style=\"display: inline-block; text-align: left;\">");
            out.println("<form method=\"post\">");
            
            out.println("Path:<br>");
            out.printf("<input type=\"text\" name=\"%s\" value=\"\"><br>",FeedLogic.PATH);
            out.println("<br>");
            
            out.println("Type:<br>");
            out.printf("<input type=\"text\" name=\"%s\" value=\"\"><br>",FeedLogic.TYPE);
            out.println("<br>");
            
            out.println("Name:<br>");
            out.printf("<input type=\"text\" name=\"%s\" value=\"\"><br>",FeedLogic.NAME);
            out.println("<br>");
            
            out.println("Host:<br>");
            out.printf("<input type=\"text\" name=\"%s\" value=\"\"><br>",FeedLogic.HOST_ID);
            out.println("<br>");
            
            out.println("<input type=\"submit\" name=\"view\" value=\"Add and View\">");
            out.println("<input type=\"submit\" name=\"add\" value=\"Add\">");
 
            out.println("</form>");
            if( errorMessage!=null && errorMessage.isEmpty()){
                out.println("<p color=red>");
                out.println("<font color=red size=4px>");
                out.println(errorMessage);
                out.println("</font>");
                out.println("</p>");
            }
            out.println("<pre>");
            out.println("Submitted keys and values:");
            out.println(toStringMap(request.getParameterMap()));
            out.println("</pre>");
            out.println("</div>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    private String toStringMap(Map<String, String[]> values){
        StringBuilder builder = new StringBuilder();
        values.forEach((k, v) -> builder.append("Key=").append(k)
                .append(", ")
                .append("Value/s=").append(Arrays.toString(v))
                .append(System.lineSeparator()));
        return builder.toString();
    } 

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
        log("GET");
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
        log("POST");
        FeedLogic fLogic = new FeedLogic();
        String path = request.getParameter(FeedLogic.PATH);
        String type = request.getParameter(FeedLogic.TYPE);
        String name = request.getParameter(FeedLogic.NAME);
        String hostid = request.getParameter(FeedLogic.HOST_ID);        
        processRequest(request, response);
        
        

        if( request.getParameter("add")!=null){
            if(fLogic.getHostWithName(name)==null){
               //if no duplicates create the entity and add it.
               try{
                   Feed feed = fLogic.createEntity( request.getParameterMap());
                   fLogic.add( feed);
               }catch(RuntimeException runtimeEX){
                   errorMessage = "Your input feed: \"" + name + "\" is not valid";
               }
           }else{
               //if duplicate print the error message
               errorMessage = "Feed: \"" + name + "\" already exists";
           }
            //if add button is pressed return the same page
            processRequest(request, response);
        }else if(request.getParameter("view")!=null){
            //if view button is pressed redirect to the appropriate table
            response.sendRedirect("FeedTable");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
