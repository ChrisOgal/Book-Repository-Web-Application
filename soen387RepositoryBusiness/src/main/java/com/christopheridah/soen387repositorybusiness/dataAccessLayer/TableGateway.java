/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.christopheridah.soen387repositorybusiness.dataAccessLayer;

import com.christopheridah.soen387repositorybusiness.core.Publisher;
import com.christopheridah.soen387repositorybusiness.core.Author;
import com.christopheridah.soen387repositorybusiness.core.RepositoryException;
import com.christopheridah.soen387repositorybusiness.core.BookRepository;
import com.christopheridah.soen387repositorybusiness.core.Book;
import com.christopheridah.soen387repositorybusiness.core.Session;
import com.christopheridah.soen387repositorybusiness.core.CoverImage;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chris
 */
public class TableGateway {
    
    private static final Object lock = new Object();
    private static final String databaseURL = "jdbc:derby://localhost:1527/Book Repository";
    private static Connection conn;
    
    
    public TableGateway () throws SQLException
    {
        conn = DriverManager.getConnection(databaseURL);
        conn.setAutoCommit(false);
     
    }

    public static Connection getConn() {
        return conn;
    }

    public static void setConn(Connection conn) {
        TableGateway.conn = conn;
    }
    
    
    public List<Book> findallBooks (Session currentUser) throws SQLException
    {
     
        List <Book> bookList = new ArrayList<>();
        String query = "select * from Book";
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            try ( ResultSet rset = ps.executeQuery()) 
            {
               while (rset.next()) {
                Book nextEntry = new Book();

                nextEntry.setId(rset.getInt(1));
                nextEntry.setTitle(rset.getString(2));
                nextEntry.setDescription(rset.getString(3));
                nextEntry.setIsbn(rset.getString(4));
                String firstName = rset.getString(5);
                String lastName = rset.getString(6);
                Author nextAuthor= new Author (firstName, lastName);
                nextEntry.setAuthor(nextAuthor);
                String pubName = rset.getString(7);
                String pubAddress = rset.getString(8);
                Publisher nextPublisher = new Publisher (pubName, pubAddress);
                nextEntry.setPublisher(nextPublisher);
                String imageType = rset.getString(9);
                Blob imageData = rset.getBlob(10);
                CoverImage nextCover = new CoverImage (imageType, imageData);
                nextEntry.setCover(nextCover);
                bookList.add(nextEntry);   
                
                }
            }   
        }
       
        
        return bookList;
    }
    
    public String getBookInfo (Session currentUser, int bookId)
    {
        
        String bookInfo = "";
        String query = "select * from Book where id = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, bookId);
            
            try (ResultSet rset = ps.executeQuery()) {
                
                rset.next();
                bookInfo = "ID: " + rset.getInt(1)
                    + "\nTitle: " + rset.getString(2)
                    + "\nDescription: " + rset.getString(3)
                    + "\nISBN: " + rset.getString(4)
                    + "\nAuthor: " + rset.getString(5) + " " + rset.getString(6)
                    + "\nPublisher: " + rset.getString(7)
                    + "\nPublisher Address: " + rset.getString(8)
                    + "\nCover image data type: " + rset.getString(9);
            }
        }
        
        catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return bookInfo;
    }
    
    public String getBookInfo (Session currentUser, String bookISBN)
    {
        String bookInfo = "";
        
         String query = "select * from Book where isbn = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, bookISBN);
            
            try (ResultSet rset = ps.executeQuery()) {
                
                rset.next();
                bookInfo = "ID: " + rset.getInt(1)
                    + "<br></br>\nTitle: " + rset.getString(2)
                    + "<br></br>\nDescription: " + rset.getString(3)
                    + "<br></br>\nISBN: " + rset.getString(4)
                    + "<br></br>\nAuthor: " + rset.getString(5) + " " + rset.getString(6)
                    + "<br></br>\nPublisher: " + rset.getString(7)
                    + "<br></br>\nPublisher Address: " + rset.getString(8);
                
            }
        }
        
        catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return bookInfo;
    }
    
    public int addNewBook(Session currentUser, String title, String description, String isbn, Author author, Publisher publisher, CoverImage cover) throws RepositoryException, SQLException
     {

         int confirmedBookID = 0;
         
         int bookID = addNewBookProcedure.generateID(conn, currentUser);

            if (checkDuplicateId(currentUser, bookID) > 0) {
                throw new RepositoryException("Attempting to add a duplicate ID to the Repository");

            } else {
                if (checkDuplicateIsbn(currentUser, isbn) > 0) {
                    throw new RepositoryException("Attempting to add a duplicate ISBN to the Repository");
                } else {
                    String insertBook = "INSERT INTO Book VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    
                    try (PreparedStatement ps = conn.prepareStatement(insertBook)) {
                        
                        ps.setInt(1, bookID);
                        ps.setString(2, title);
                        ps.setString(3, description);
                        ps.setString(4, isbn);
                        ps.setString(5, author.getFirstName());
                        ps.setString(6, author.getLastName());
                        ps.setString(7, publisher.getName());
                        ps.setString(8, publisher.getAddress());
                        ps.setString(9, cover.getMimeType());
                        ps.setBlob(10, cover.getImageData());
                        
                        
                         synchronized (lock) {
                          ps.executeUpdate();
                          
                        String confirmID = "select MAX(id) from Book";
                        
                        try (PreparedStatement ps2 = conn.prepareStatement(confirmID)) {
                            
                            try (ResultSet rset = ps2.executeQuery()) {
                                
                                rset.next();
                            confirmedBookID = rset.getInt(1);
                            }
                            
                        }
                        
                      }
                         
                    } catch (SQLException ex) {
                        Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   
                }
        }

         return confirmedBookID;
     }
     
    public void updateBook(Session currentUser, int bookID, String title, String description, String isbn, Author author, Publisher publisher, CoverImage cover) throws RepositoryException, SQLException {

        
        
            if (checkDuplicateId(currentUser, bookID) > 1 || checkDuplicateIsbn(currentUser, isbn) > 1)
            {
                throw new RepositoryException ("Attempting to duplicate Isbn");
            }
            
            else
            {
                String update = "update Book set title = ?, info = ?, isbn = ?, authorFname = ?, authorLname = ?, publisherName = ?, publisherAddress = ?, imageType = ?, imageData = ?"
                        + " where id = ?";
                
                try (PreparedStatement ps = conn.prepareStatement(update)) {
                    
                        ps.setString(1, title);
                        ps.setString(2, description);
                        ps.setString(3, isbn);
                        ps.setString(4, author.getFirstName());
                        ps.setString(5, author.getLastName());
                        ps.setString(6, publisher.getName());
                        ps.setString(7, publisher.getAddress());
                        ps.setString(8, cover.getMimeType());
                        ps.setBlob(9, cover.getImageData());
                        ps.setInt(10, bookID);
                       
                    
                        ps.executeUpdate();
                } catch (SQLException ex) {
                    Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
                }
               
            } 
            
            
            
    }
    
    public void setBookCoverImage(Session currentUser, String isbn, CoverImage cover) throws SQLException
    {
        
        
        String update = "update Book set imageType = ?, imageData = ?"
                        + " where isbn = ?";
                
                try (PreparedStatement ps = conn.prepareStatement(update)) {
                    
                        
                        ps.setString(8, cover.getMimeType());
                        ps.setBlob(9, cover.getImageData());
                        ps.setString(10, isbn);
                    
                        ps.executeUpdate();
                } catch (SQLException ex) {
                    Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
                }
          
                 
    }
    
    public void deleteBook(Session currentUser, String isbn) throws RepositoryException {


            if (bookExists(currentUser, isbn) == false)
        {
            throw new RepositoryException ("Book does not exist");
        }
            else 
            {
                String deleteQuery = "delete from Book where isbn = ?";
            
            try (PreparedStatement ps2 = conn.prepareStatement(deleteQuery)) {
                
                
                ps2.setString(1, isbn);
                ps2.executeUpdate();
                
            }
          
        catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
            }

    }
     
    public void deleteBook(Session currentUser, int bookID) throws RepositoryException {

        
            if (bookExists(currentUser, bookID) == false)
        {
            throw new RepositoryException ("Book does not exist");
        }
            else 
            {
                String deleteQuery = "delete from Book where id = ?";
            
            try (PreparedStatement ps2 = conn.prepareStatement(deleteQuery)) {
                
                
                ps2.setInt(1, bookID);
                ps2.executeUpdate();
                
            }
          
        catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
            }

    }
    
    public void deleteAllBooks(Session currentUser) throws RepositoryException {

       
            int tableSize = 0;
            
            String getTableSize = "select MAX(id) from Book";
            
            try (PreparedStatement ps = conn.prepareStatement(getTableSize)) {
                
                try (ResultSet rset = ps.executeQuery()) {
                    
                    rset.next();
                    tableSize = rset.getInt(1);
                    for (int i = 1; i < tableSize + 1; i++) {
                    deleteBook(currentUser, i);
            }
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Blob getCoverImage (Session currentUser, String isbn) throws SQLException
    {
        
       Blob coverImage = null;
        
         String query = "select imageData from Book where isbn = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, isbn);
            
            try (ResultSet rset = ps.executeQuery()) {
                
                rset.next();
                
                coverImage = rset.getBlob(1);
                
            }
        }
        
        catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        return coverImage;
    }
    
    //Support Methods
     
     public int checkDuplicateId(Session currentUser, int bookID) {

        int duplicate = 0;

       
            String duplicateTest = "select COUNT(id) from Book where id = ?";

            try (PreparedStatement ps = conn.prepareStatement(duplicateTest)) {
                
                ps.setInt(1, bookID);
                
                try (ResultSet rset = ps.executeQuery()) {
                    rset.next();
            
            duplicate = rset.getInt(1);
                }
            }
                       
         catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        return duplicate;
    }

    public int checkDuplicateIsbn( Session currentUser, String isbn) {

        int duplicate = 0;

       
            String duplicateTest = "select COUNT(id) from Book where isbn = ?";

            try (PreparedStatement ps = conn.prepareStatement(duplicateTest)) {
                
                ps.setString(1, isbn);
                
                try (ResultSet rset = ps.executeQuery()) {
                    rset.next();
            
                    duplicate = rset.getInt(1);
                }
            }
                       
         catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        return duplicate;
    }
    
     public boolean bookExists ( Session currentUser, int bookID) {
        
        boolean exists = false;
        
        String query = "select * from Book where id = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, bookID);
            
            try (ResultSet rset = ps.executeQuery()) {
                
                rset.next();
                
                if (rset.getInt(1) >= 1)
                {
                    exists = true;
                }
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return exists;
        
    }
    
    public boolean bookExists ( Session currentUser, String isbn) {
        
        boolean exists = false;
        
        String query = "select * from Book where isbn = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, isbn);
            
            try (ResultSet rset = ps.executeQuery()) {
                
                rset.next();
                
                if (rset.getString(4).equals(isbn))
                {
                    exists = true;
                }
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return exists;
        
    }
    
    public Book getBook ( Session currentUser, String isbn) throws SQLException
    {
        
        Book subjectBook = new Book();
        
        if (bookExists(currentUser, isbn))
        {
            String query = "select * from Book where isbn = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, isbn);
            
            try (ResultSet rset = ps.executeQuery()) {
                
                rset.next();
                subjectBook.setId(rset.getInt(1));
                subjectBook.setTitle(rset.getString(2));
                subjectBook.setDescription(rset.getString(3));
                subjectBook.setIsbn(rset.getString(4));
                String firstName = rset.getString(5);
                String lastName = rset.getString(6);
                Author nextAuthor= new Author (firstName, lastName);
                subjectBook.setAuthor(nextAuthor);
                String pubName = rset.getString(7);
                String pubAddress = rset.getString(8);
                Publisher nextPublisher = new Publisher (pubName, pubAddress);
                subjectBook.setPublisher(nextPublisher);
                CoverImage nextCover = new CoverImage ();
                nextCover.setMimeType(rset.getString(9));
                nextCover.setImage(rset.getBlob(10));
                subjectBook.setCover(nextCover);
            }
        }
        
        catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        }

        
        return subjectBook;
    }
    
    public Book getBook ( Session currentUser, int bookID) throws SQLException
    {
        
        Book subjectBook = new Book();
        
        if (bookExists(currentUser, bookID))
        {
            String query = "select * from Book where id = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, bookID);
            
            try (ResultSet rset = ps.executeQuery()) {
                
                rset.next();
                subjectBook.setId(rset.getInt(1));
                subjectBook.setTitle(rset.getString(2));
                subjectBook.setDescription(rset.getString(3));
                subjectBook.setIsbn(rset.getString(4));
                String firstName = rset.getString(5);
                String lastName = rset.getString(6);
                Author nextAuthor= new Author (firstName, lastName);
                subjectBook.setAuthor(nextAuthor);
                String pubName = rset.getString(7);
                String pubAddress = rset.getString(8);
                Publisher nextPublisher = new Publisher (pubName, pubAddress);
                subjectBook.setPublisher(nextPublisher);
                CoverImage nextCover = new CoverImage ();
                nextCover.setMimeType(rset.getString(9));
                nextCover.setImage(rset.getBlob(10));
                subjectBook.setCover(nextCover);
            }
        }
        
        catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        }

        return subjectBook;
    }
}
