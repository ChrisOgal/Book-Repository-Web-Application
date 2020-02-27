/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.christopheridah.soen387repositorybusiness.core;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import javax.json.*;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

/**
 *
 * @author chris
 */
public class Session {
    
    private User currentUser;
    
    public Session ()
    {
        
    }

    public Session(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    /**
     *
     * @param currentUser
     */
    public void login(String userName, String passWord) throws IOException, NoSuchAlgorithmException {
       
        if (loadProfile(userName, passWord))
        {
            currentUser.setLoggedIn(true);
        }
        
    }
    
    public boolean isUserLoggedIn (User testUser)
    {
        return testUser.isLoggedIn();
    }
    
    public void logout ()
    {
        this.currentUser = null;
    }
    
    public static void logOut()
    {
        
    }
    
     public boolean loadProfile (String userName, String passWord) throws IOException, NoSuchAlgorithmException {
        
         boolean userExists = false;
         User potentialUser = new User (userName, passWord, false);
         
         try (InputStream propLoc = getClass().getResourceAsStream("G:\\netbeans\\soen387Repository\\src\\main\\java\\com\\christopheridah"
                                                                 + " \\soen387repository\\resources\\config.properties")) {
          
         Properties filePath = new Properties();
         
         if (propLoc == null)
         {
             System.err.println("Invalid config file");
             return false;
         }
         
         else 
         {
             filePath.load(propLoc);  
         }
         
         
         
         try (JsonParser userParser = Json.createParser(new FileInputStream (filePath.getProperty("json"))))
        {
            
            String name = "";
            String pass = "";
            int full = 0;
            
           while (userParser.hasNext())
           {
               JsonParser.Event event = userParser.next();
               
              switch (event) {
                  
                  case VALUE_STRING:
                  {
                      switch (full % 2)
                      {
                          case 0:
                              name = userParser.getString();
                              full += 1;
                              break;
                          case 1:
                              pass = userParser.getString();
                              full += 1;
                              break;
                      }
                      
                      if (potentialUser.getUserId().equals(name) && potentialUser.encryptPassword(potentialUser.getPassword()).equals(pass))
                      {
                          userExists = true;
                          return userExists;
                      }
                  }
                      
              }
           } 
        }
      }
         
         return userExists;
    }
    
}
