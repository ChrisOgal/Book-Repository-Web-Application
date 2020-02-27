/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.christopheridah.soen387repository.ui;

import com.christopheridah.soen387repositorybusiness.core.User;
import com.christopheridah.soen387repositorybusiness.core.Book;
import com.christopheridah.soen387repositorybusiness.core.BookRepository;
import com.christopheridah.soen387repositorybusiness.core.RepositoryException;
import com.christopheridah.soen387repositorybusiness.core.Session;
import com.christopheridah.soen387repositorybusiness.core.IBookRepository;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author chris
 */
public class DeleteServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
     static IBookRepository repo;
      
      static User currentUser = new User ("chris"  , "cake" , true);
      static Session active = new Session (currentUser);
      
      
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, RepositoryException {
        response.setContentType("text/html;charset=UTF-8");
        
        repo = BookRepository.getInstance();
        
        Map<String, String[]> parameters = request.getParameterMap();
        String function = "";
       
        
        if (parameters.isEmpty() == false) {
            function = parameters.get("deleteparam1")[0];
        }
        
        if (function.equalsIgnoreCase("delete1"))
        {
            function = parameters.get("deleteparam2")[0];
            deleteBook(request, response, function);
        }
        
        if (function.equalsIgnoreCase("delete2"))
        {
            deleteAllBooks(request, response);
        }
        
        if (function.equalsIgnoreCase("info"))
        {
            function = parameters.get("deleteparam2")[0];
            viewInfo(request, response, function);
            
        }
        
        if (function.equalsIgnoreCase("update"))
        {
            function = parameters.get("deleteparam2")[0];
            updateBook(request, response, function);
            
        }
        
        if (function.equalsIgnoreCase("add"))
        {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Add.jsp");
            dispatcher.forward(request, response);
            
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
         try {
             processRequest(request, response);
         } catch (SQLException ex) {
             Logger.getLogger(DeleteServlet.class.getName()).log(Level.SEVERE, null, ex);
         } catch (RepositoryException ex) {
             Logger.getLogger(DeleteServlet.class.getName()).log(Level.SEVERE, null, ex);
         }
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
         try {
             processRequest(request, response);
         } catch (SQLException ex) {
             Logger.getLogger(DeleteServlet.class.getName()).log(Level.SEVERE, null, ex);
         } catch (RepositoryException ex) {
             Logger.getLogger(DeleteServlet.class.getName()).log(Level.SEVERE, null, ex);
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
    }// </editor-fold>

    public void deleteBook (HttpServletRequest request, HttpServletResponse response, String isbn) throws SQLException, ServletException, IOException, RepositoryException
    {
       
        
        
        repo.deleteBook( active, isbn);
        
        List <Book> currentStock = repo.listAllBooks( active);
        List <String> bookCovers = new ArrayList<>();
        
        for (Book current: currentStock)
       {
          
        byte[] updatedImage = null;
        String encodedImage = null;
        
        Blob requestedCoverImage = repo.getCoverImage( active, current.getIsbn());
        if (requestedCoverImage != null)
        {
           updatedImage = requestedCoverImage.getBytes(1, (int)requestedCoverImage.length());
           encodedImage = Base64.getEncoder().encodeToString(updatedImage);
        }
        
        bookCovers.add(encodedImage);
       }
        
        
        repo.commitConnection();
        request.setAttribute("allBooks", currentStock);
        request.setAttribute("coverInfo", bookCovers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Home.jsp");
        dispatcher.forward(request, response);
    }
    
    public void deleteAllBooks (HttpServletRequest request, HttpServletResponse response) throws SQLException, RepositoryException, ServletException, IOException
    {
      
        
        
        repo.deleteAllBooks( active);
        
        
        List <Book> currentStock = repo.listAllBooks( active);
        
        repo.commitConnection();
        
        request.setAttribute("allBooks", currentStock);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Home.jsp");
        dispatcher.forward(request, response);
    }
    
    public void viewInfo (HttpServletRequest request, HttpServletResponse response, String isbn) throws SQLException, ServletException, IOException
    {
        
        String requestedBook = repo.getBookInfo( active, isbn);
    
        Blob requestedCoverImage = repo.getCoverImage( active, isbn);
        String encodedImage = null;
        if (requestedCoverImage != null)
        {
            byte[] image = requestedCoverImage.getBytes(1, (int)requestedCoverImage.length());
            encodedImage = Base64.getEncoder().encodeToString(image);
        }
        
        
        BookInfoBean requestedInfo = new BookInfoBean();
        requestedInfo.setBookInfo(requestedBook);
        requestedInfo.setIsbn(isbn);
        
        if (encodedImage !=null)
        {
            requestedInfo.setCoverImage(encodedImage);
        }
        else
        {
            requestedInfo.setCoverImage(null);
        }
        
        repo.commitConnection();
        request.getSession().setAttribute("bookBean", requestedInfo);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Details.jsp");
        dispatcher.forward(request, response);
     
    }
    
    
    public void updateBook (HttpServletRequest request, HttpServletResponse response, String isbn) throws SQLException, ServletException, IOException
    {
        
       String confirm = repo.getBook(active, isbn).getIsbn();
        if (confirm.equals(isbn))
        {
            repo.commitConnection();
            request.setAttribute("oldIsbn", isbn);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Update.jsp");
            dispatcher.forward(request, response);
        }
          
        
    }
    
    
}
