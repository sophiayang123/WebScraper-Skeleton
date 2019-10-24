/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import common.FileUtility;
import entity.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.ImageLogic;
import scraper.Scraper;

/**
 *
 * @author shutingyang
 */
@WebServlet(name = "ImageView", urlPatterns = {"/ImageView"})
public class ImageView extends HttpServlet {
    
    Scraper scraper = new Scraper();

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
            out.println("<title>Servlet CreateImage</title>");            
            out.println("</head>");
            out.println("<body>");
            
            ImageLogic ilogic = new ImageLogic();
            List<Image> all= ilogic.getAll();
            
            out.println("<caption>Generated Images</caption>");
            out.println("<table align=\"center\" border=\"0\">");
            out.println("<table>");
            out.println("<tbody>");
            for (Image a : all) {
                out.printf("<tr><td>%s</td><td>%s</td></tr>", a.getId(), a.getName());
                out.printf("<tr><td><img src=\"%s\"  alt=\"Smiley face\" height=\"42\" width=\"42\"></td></tr>", "image/"+FileUtility.getFileName(a.getPath()) ); 
            } 
            
            out.println("</tbody>");
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ServletContext cntx= req.getServletContext();
        // Get the absolute path of the image
        String filename = System.getProperty("user.home")+"/Documents/Reddit Images/"; 
        File file = new File(filename);
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
        // retrieve mimeType dynamically
        System.out.print(filename);
        String mime = cntx.getMimeType(filename);
        
        if (mime == null) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        resp.setContentType(mime);
        File file2 = new File(filename);
        try(                          
            FileInputStream in = new FileInputStream(file2);
            ){
            resp.setContentLength((int)file.length());
            OutputStream out = resp.getOutputStream();
            byte[] buf = new byte[1024];
            int count = 0;
            while ((count = in.read(buf)) >= 0) {
            out.write(buf, 0, count);
        }
        }catch(FileNotFoundException e){
            System.out.println(e);
        }

        // Copy the contents of the file to the output stream

        processRequest(req, resp);
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
