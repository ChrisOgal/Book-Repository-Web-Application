/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.christopheridah.soen387repositorybusiness.core;
import com.christopheridah.soen387repositorybusiness.core.IBookRepository;
import com.christopheridah.soen387repositorybusiness.core.Publisher;
import com.christopheridah.soen387repositorybusiness.core.Session;
import com.christopheridah.soen387repositorybusiness.core.BookRepository;
import com.christopheridah.soen387repositorybusiness.core.Author;
import com.christopheridah.soen387repositorybusiness.core.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.*;
import static org.junit.Assert.assertNotEquals;
import org.apache.derby.client.*;
import org.apache.derby.*;
import static org.junit.Assert.assertNotNull;




/**
 *
 * @author chris
 */
public class BookRepositoryTest {
    
    static Session testSession;
    static Author testAuthor;
    static Publisher testPublisher;
    static IBookRepository testRep;
    static String databaseURL;
    static Connection conn;
    static CoverImage testCoverImage;
    
    @BeforeClass
    public static void initializeConnection () throws SQLException {
        
         User currentUser = new User ("chris", "cake", true);
         testSession = new Session (currentUser);
         testAuthor = new Author ("Who" , "Cares");
         testPublisher = new Publisher ("SomeOne", "SomeWhere");
         testCoverImage = new CoverImage ();
         testRep = BookRepository.getInstance();
         databaseURL = "jdbc:derby://localhost:1527/Book Repository";
         conn = DriverManager.getConnection(databaseURL);
    }
    
 /* @Test //Delete book 2 before running
    public void notZeroId () throws RepositoryException  {

        assertNotEquals(0, testRep.addNewBook(testSession, "Eat, Pray, Love", "The greatest book ever written", "A156358", testAuthor, testPublisher, testCoverImage));
      
    }
    
    
    @Test (expected = RepositoryException.class) //Delete book 3 before running
    
    public void repeatISBN () throws RepositoryException {
        
        testRep.addNewBook(testSession, "Introduction to Really Hard Algebra", "It reaaaly hard", "AS12323" , testAuthor, testPublisher, testCoverImage);
        
    } 
    
    @Test (expected = RepositoryException.class)
    
    public void failedDelete() throws RepositoryException 
    {
        testRep.deleteBook(testSession, 120);
    }   
    
    @Test 
    
    public void listOfBooks () throws RepositoryException
    {
        assertNotEquals(0, testRep.listAllBooks( testSession).size());
    }  */
}
