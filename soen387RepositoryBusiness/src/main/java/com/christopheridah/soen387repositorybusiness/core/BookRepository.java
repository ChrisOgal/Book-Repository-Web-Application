/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.christopheridah.soen387repositorybusiness.core;


import com.christopheridah.soen387repositorybusiness.dataAccessLayer.TableGateway;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookRepository implements IBookRepository {

    private static BookRepository instance;
    private static final Object lock = new Object();
    private TableGateway gateway;
    

    private BookRepository() throws SQLException {

        gateway = new TableGateway ();
    }

    public static BookRepository getInstance() throws SQLException {

        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new BookRepository();
                }
            }

        }

        return instance;
    }

    public TableGateway getGateway() {
        return gateway;
    }

    public void setGateway(TableGateway gateway) {
        
        gateway = gateway;
    }

    
    @Override
    public List<Book> listAllBooks(Session currentUser) {

        List<Book> bookList = new ArrayList<>();

        try {
            bookList = gateway.findallBooks( currentUser);
        } catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bookList;
    }

    @Override
    public String getBookInfo( Session currentUser, int bookId) {

        String bookInfo = "";
        
        bookInfo = gateway.getBookInfo( currentUser, bookId);

        return bookInfo;
    }

    @Override
    public String getBookInfo( Session currentUser, String bookISBN) {
        String bookInfo = "";
        
       bookInfo = gateway.getBookInfo( currentUser, bookISBN);

        return bookInfo;
    }

    @Override
    public int addNewBook( Session currentUser, String title, String description, String isbn, Author author, Publisher publisher, CoverImage cover) throws RepositoryException {

        int confirmedBookID = 0;
        
        try {
            confirmedBookID = gateway.addNewBook( currentUser, title, description, isbn, author, publisher, cover);
        } catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        return confirmedBookID;
    }

    @Override
    public void updateBook( Session currentUser, int bookID, String title, String description, String isbn, Author author, Publisher publisher, CoverImage cover) throws RepositoryException {

        try {
            gateway.updateBook( currentUser, bookID, title, description, isbn, author, publisher, cover);
        } catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setBookCoverImage( Session currentUser, String isbn, CoverImage cover) {
       
        try {
            gateway.setBookCoverImage( currentUser, isbn, cover);
        } catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteBook( Session currentUser, String isbn) throws RepositoryException {


         gateway.deleteBook( currentUser, isbn);

    }

     @Override
    public void deleteBook( Session currentUser, int bookID) throws RepositoryException {

        gateway.deleteBook( currentUser, bookID);

    }
    @Override
    public void deleteAllBooks( Session currentUser) throws RepositoryException {

        gateway.deleteAllBooks( currentUser);
    }
    
    public Book getBook ( Session currentUser, String isbn)
    {
       Book subjectBook = new Book ();
        
        try {
            subjectBook =  gateway.getBook( currentUser, isbn);
        } catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return subjectBook;
    }
    
    @Override
    public Book getBook ( Session currentUser, int bookID)
    {
       Book subjectBook = new Book ();
        
        try {
            subjectBook =  gateway.getBook( currentUser, bookID);
        } catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return subjectBook;
    }
    
    public Blob getCoverImage ( Session currentUser, String isbn)
    {
        Blob bookImage = null;
        
        try {
            bookImage = gateway.getCoverImage( currentUser, isbn);
        } catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return bookImage;
    }
    
    @Override
    public boolean bookExists (Session currentUser, int bookID)
    {
        boolean exists = false;
        
        exists = gateway.bookExists(currentUser, bookID);
        
        return exists;
    }
    
    public boolean bookExists (Session currentUser, String isbn)
    {
        boolean exists = false;
        
        exists = gateway.bookExists(currentUser, isbn);
        
        return exists;
    }
    @Override
    public void commitConnection()
    {
        try {
            TableGateway.getConn().commit();
        } catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
