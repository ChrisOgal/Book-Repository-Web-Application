/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.christopheridah.soen387repositorybusiness.core;

import javax.json.*;
import java.io.*;
import java.security.MessageDigest;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.*;


/**
 *
 * @author chris
 */
public class User {
    
    private String userId, password;
    private boolean loggedIn;
    
    public User ()
    {
        
    }

    public User(String userId, String password, boolean loggedIn) {
        this.userId = userId;
        this.password = password;
        this.loggedIn = loggedIn;
    }

    public String getUserId() {
        return userId;
    }
 
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
    
    public void createProfile () throws IOException, NoSuchAlgorithmException {
        
      try (InputStream propLoc  = new FileInputStream ("/config.properties")) {
          
         Properties filePath = new Properties();
         
         if (propLoc == null)
         {
             System.err.println("Invalid config file");
             return;
         }
         
         else 
         {
             filePath.load(propLoc);  
         }
         
         
         try (JsonWriter configOutput = Json.createWriter(new FileOutputStream (filePath.getProperty("json"), true)))
        {
            JsonObjectBuilder createUser = Json.createObjectBuilder();
            
            createUser.add("username", userId);
            createUser.add("password", encryptPassword(password));
            
            
            configOutput.writeObject(createUser.build());
        }
      }
    }
    
    
    public String encryptPassword (String password) throws NoSuchAlgorithmException {
        
        String hashPass = "";
        
        MessageDigest enc = MessageDigest.getInstance("MD5");
        byte [] messageIn = enc.digest(password.getBytes());
        
        BigInteger num = new BigInteger (1 , messageIn);
        
        hashPass = num.toString(16);
        
        while (hashPass.length() < 32) {
           
            hashPass = 0 + hashPass;
        }
        return hashPass;
        
    }
    
    
    
    
}
