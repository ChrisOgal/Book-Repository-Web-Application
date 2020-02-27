/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.christopheridah.soen387repositorybusiness.core;

import java.sql.Blob;
import java.sql.Connection;
import java.util.List;
/**
 *
 * @author chris
 */
public interface IBookRepository {
   
    public List<Book> listAllBooks(Session currentUser);
    public String getBookInfo(  Session currentUser, int bookId);
    public String getBookInfo (  Session currentUser, String bookISBN);
    public int addNewBook (  Session currentUser, String title, String description, String isbn, Author author, Publisher publisher, CoverImage cover) throws RepositoryException;
    public void updateBook (  Session currentUser, int bookID, String title, String description, String isbn, Author author, Publisher publisher, CoverImage cover) throws RepositoryException;
    public void setBookCoverImage(  Session currentUser,String isbn, CoverImage cover);
    public void deleteBook(  Session currentUser, String isbn) throws RepositoryException;
    public void deleteBook( Session currentUser, int bookID) throws RepositoryException;
    public void deleteAllBooks(  Session currentUser) throws RepositoryException;
    public Book getBook ( Session currentUser, String isbn);
    public Book getBook ( Session currentUser, int bookID);
    public Blob getCoverImage ( Session currentUser, String isbn);
    public void commitConnection();
    public boolean bookExists (Session currentUser, int bookID);
    public boolean bookExists (Session currentUser, String isbn);
    
    
    
}
