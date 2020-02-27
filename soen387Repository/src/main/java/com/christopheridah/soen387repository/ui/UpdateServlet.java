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
import static com.christopheridah.soen387repository.ui.RepositoryServlet.repo;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.rowset.serial.SerialBlob;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author chris
 */

@MultipartConfig
public class UpdateServlet extends HttpServlet {

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
            throws ServletException, IOException, SQLException, RepositoryException, FileUploadException {
        response.setContentType("text/html;charset=UTF-8");
        
        repo = BookRepository.getInstance();
        
        Map<String, String[]> parameters = new HashMap<>();
         byte [] image = null;
         
        List <FileItem> items = new ServletFileUpload (new DiskFileItemFactory()).parseRequest(request);
        
        for (FileItem item: items)
        {
            if (item.isFormField())
            {
                String fieldName = item.getFieldName();
                String fieldValue [] = {item.getString()};
                parameters.put(fieldName, fieldValue);
            }
            
            else
            {
                if (item != null)
                {
                    image = item.get();
                }
               
            }
        }
        
        updateBook(request, response, parameters, image);
        
        
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
            Logger.getLogger(UpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RepositoryException ex) {
            Logger.getLogger(UpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileUploadException ex) {
            Logger.getLogger(UpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(UpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RepositoryException ex) {
            Logger.getLogger(UpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileUploadException ex) {
            Logger.getLogger(UpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
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

    public void updateBook (HttpServletRequest request, HttpServletResponse response, Map<String, String[]> parameters, byte[] image) throws SQLException, RepositoryException, ServletException, IOException
    {
        
       
        
        Book dirtyBook = repo.getBook(active, parameters.get("oldIsbn")[0]);
        
        
        if (parameters.get("title")[0].equals("") == false && parameters.get("title")[0] == null == false)
        {
            dirtyBook.setTitle(parameters.get("title")[0]);
        }
        
        if (parameters.get("description")[0].equals("") == false && parameters.get("description")[0] == null == false)
        {
            dirtyBook.setDescription(parameters.get("description")[0]);
        }
        
        if (parameters.get("isbn")[0].equals("") == false && parameters.get("isbn")[0] == null == false)
        {
            dirtyBook.setIsbn(parameters.get("isbn")[0]);
        }
        
        if (parameters.get("authorFname")[0].equals("") == false && parameters.get("authorFname")[0] == null == false)
        {
            dirtyBook.getAuthor().setFirstName(parameters.get("authorFname")[0]);
        }
        
        if (parameters.get("authorLname")[0].equals("") == false && parameters.get("authorLname")[0] == null == false)
        {
            dirtyBook.getAuthor().setLastName(parameters.get("authorLname")[0]);
        }
        
        if (parameters.get("publisher")[0].equals("") == false && parameters.get("publisher")[0] == null == false)
        {
            dirtyBook.getPublisher().setName(parameters.get("publisher")[0]);
        }
        
        if (parameters.get("address")[0].equals("") == false && parameters.get("address")[0] == null == false)
        {
            dirtyBook.getPublisher().setAddress(parameters.get("address")[0]);
        }
        
         if (parameters.get("format")[0].equals("") == false && parameters.get("format")[0] == null == false)
        {
            dirtyBook.getCover().setMimeType(parameters.get("format")[0]);
        }
        
        if (image != null && image.length > 0)
        {
         
            dirtyBook.getCover().setImage(new SerialBlob (image));
          
        }
        
        repo.updateBook( active, dirtyBook.getId(),dirtyBook.getTitle() , dirtyBook.getDescription(), dirtyBook.getIsbn(), dirtyBook.getAuthor(), dirtyBook.getPublisher(), dirtyBook.getCover());
        
        
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
}
