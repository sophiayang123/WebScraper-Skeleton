 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import common.FileUtility;
import entity.Feed;
import entity.Image;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.FeedLogic;
import logic.ImageLogic;
import scraper.Post;
import scraper.Scraper;
import scraper.Sort;


/**
 *
 * @author shutingyang
 */
@WebServlet(name = "ImageView", urlPatterns = {"/ImageView"})
public class ImageView extends HttpServlet {
    
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
            String cssTag="<link rel='stylesheet' type='text/css' href='style/ImageView.css'>";
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ImageDelivery</title>"); 
            out.println(cssTag); 
            out.println("</head>");
            out.println("<body>");
            ImageLogic ilogic = new ImageLogic();

            for(Image image : ilogic.getAll()){
                out.println("<div class=\"imageContainer\">");
                out.printf(" <img class=\"imageThumb\" src=\"image/%s\">", FileUtility.getFileName(image.getPath()));
                out.println("</div>");
            }
            out.println("</body>");
            out.println("</html>");
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
        
        ImageLogic iLogic = new ImageLogic();
        FeedLogic fl = new FeedLogic();

        String filename = System.getProperty("user.home")+"/Documents/Reddit Images/" ;
        FileUtility.createDirectory(filename);
        
        //create a lambda that accepts post
        Consumer<Post> saveImage = (Post post) -> {
            //if post is an image and SFW, and check the database if url is unique
            if (post.isImage() && !post.isOver18() && iLogic.getImageWithPath(post.getUrl())==null) {
                //get the path for the image which is unique
                Map<String, String[]> map = new HashMap();
                String path = post.getUrl();
//                String name = filename + filename_r;
                //what is filename here??? folder name only??? or folder name + image url?????
                FileUtility.downloadAndSaveFile(path, filename);
                map.put("path", new String[]{path});
                map.put("date", new String[]{Long.toString(post.getDate().getTime())});
                map.put("name", new String[]{post.getTitle()});        
                Feed f = fl.getWithId(4);       
                //map.put("feedid", new String[]{Integer.toString(feed.getHostid().getId())});              
                Image ima = iLogic.createEntity(map);
                
                ima.setFeedid(f);
                
                //imageLogic call genericDAO and add the image info to database
                iLogic.add( ima);
            }
        };   
        
        //create a new scraper
        Scraper scrap = new Scraper();
        //authenticate and set up a page for wallpaper subreddit with 5 posts soreted by HOT order
        scrap.authenticate().buildRedditPagesConfig("Wallpaper", 5, Sort.HOT);
        //get the next page 3 times and save the images.
        scrap.requestNextPage().proccessNextPage(saveImage);
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
//        ImageLogic iLogic = new ImageLogic();
//        Feed feed = new Feed();
//        Image image = new Image();
//
//        String filename = System.getProperty("user.home")+"/Documents/Reddit Images/";
//        FileUtility.createDirectory(filename);
        //create a lambda that accepts post
//        Consumer<Post> saveImage = (Post post) -> {
            //if post is an image and SFW
//            if (post.isImage() && !post.isOver18()) {
                //get the path for the image which is unique
//                Map<String, String[]> map = new HashMap();
//                String path = post.getUrl();
//                FileUtility.downloadAndSaveFile(path, filename);
//                map.put("path", new String[]{path});
//                map.put("title", new String[]{post.getTitle()});
//                map.put("date", new String[]{Long.toString(post.getDate().getTime())});
//                map.put("name", new String[]{FileUtility.getFileName(path)});
//                map.put("feedid", new String[]{Integer.toString(feed.getHostid().getId())});
//                Image ima = iLogic.createEntity(map);
//                iLogic.add( ima);
                //save it in img directory
            //}
        //};   
        
        //create a new scraper
        //Scraper scrap = new Scraper();
        //authenticate and set up a page for wallpaper subreddit with 5 posts soreted by HOT order
        //scrap.authenticate().buildRedditPagesConfig("Wallpaper", 5, Sort.HOT);
        //get the next page 3 times and save the images.
        //scrap.requestNextPage().proccessNextPage(saveImage);
        
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
