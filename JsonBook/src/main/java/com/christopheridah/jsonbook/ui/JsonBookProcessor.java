/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.christopheridah.jsonbook.ui;

import com.christopheridah.soen387repositorybusiness.core.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;


/**
 *
 * @author chris
 */
public class JsonBookProcessor extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        
        repo = BookRepository.getInstance();
        
        Map<String, String[]> parameters = request.getParameterMap();
        
        ObjectMapper objectMapper = new ObjectMapper();
        
        
        
       
        int bookID = 0;
               
        if (isInteger((parameters.get("bookID")[0])))
        {
            bookID = Integer.parseInt(parameters.get("bookID")[0]);
        }

        
        if (bookID == 0)
        {
            allBooks(request, response, objectMapper);
        }
        
        else 
        {
            singleBook(request, response, objectMapper, bookID);
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
            
            ObjectMapper exceptionMapper = new ObjectMapper ();
            
            String errorMessage = exceptionMapper.writeValueAsString("There was an SQL Exception encountered. This has been handled in JSON");
            request.setAttribute("jsonResponse", errorMessage);
                        
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsonDisplay.jsp");
           dispatcher.forward(request, response);
            Logger.getLogger(JsonBookProcessor.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(JsonBookProcessor.class.getName()).log(Level.SEVERE, null, ex);
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

public boolean isInteger (String input)
    {
        try 
        {
            Integer.parseInt(input);
            return true;
        }
        
        catch (NumberFormatException e)
        {
            return false;
        }
    }


public void singleBook (HttpServletRequest request, HttpServletResponse response, ObjectMapper objectMapper, int bookID) throws IOException, ServletException, SQLException
{
    Book requestedBook = new Book ();
            
            if (repo.bookExists(active, bookID))
            {
                requestedBook = repo.getBook(active, bookID);
                
               JsonBookBean requestedBean = new JsonBookBean();

                requestedBean.setId(requestedBook.getId());
                requestedBean.setIsbn(requestedBook.getIsbn());
                requestedBean.setTitle(requestedBook.getTitle());
                requestedBean.setDescription(requestedBook.getDescription());
                requestedBean.setAuthor(requestedBook.getAuthor());
                requestedBean.setPublisher(requestedBook.getPublisher());
                
                if (requestedBook.getCover() != null && requestedBook.getCover().getImageData() != null && requestedBook.getCover().getImageData().length() > 0)
                {
                    requestedBean.setCover(true);
                }
                
                else
                {
                    requestedBean.setCover(false);
                }
               
                String requestedJsonString = objectMapper.writeValueAsString(requestedBean);
                
                request.setAttribute("jsonResponse", requestedJsonString);
                        
                RequestDispatcher dispatcher = request.getRequestDispatcher("/jsonDisplay.jsp");
                dispatcher.forward(request, response);
            }
            
            else
            {
                String noBookJson = objectMapper.writeValueAsString("The requested book does not exist");
                
                request.setAttribute("jsonResponse", noBookJson);
                        
                RequestDispatcher dispatcher = request.getRequestDispatcher("/jsonDisplay.jsp");
                dispatcher.forward(request, response);
            }
}

public void allBooks (HttpServletRequest request, HttpServletResponse response, ObjectMapper objectMapper) throws IOException, ServletException
{
    List <Book> currentStock = repo.listAllBooks(active);
            
            List <JsonBookBean> beanStock = new ArrayList<>();
            
            for (Book current: currentStock)
            {
                JsonBookBean currentBean = new JsonBookBean ();
                
                currentBean.setId(current.getId());
                currentBean.setIsbn(current.getIsbn());
                currentBean.setTitle(current.getTitle());
                currentBean.setDescription(current.getDescription());
                currentBean.setAuthor(current.getAuthor());
                currentBean.setPublisher(current.getPublisher());

                beanStock.add(currentBean);
    
            }
                String currentJsonString = objectMapper.writeValueAsString(beanStock);
                
                
                request.setAttribute("jsonResponse", currentJsonString);
                        
                RequestDispatcher dispatcher = request.getRequestDispatcher("/jsonDisplay.jsp");
                dispatcher.forward(request, response);
}
}
