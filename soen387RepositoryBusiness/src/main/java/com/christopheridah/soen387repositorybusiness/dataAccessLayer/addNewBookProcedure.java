/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.christopheridah.soen387repositorybusiness.dataAccessLayer;

import com.christopheridah.soen387repositorybusiness.core.Session;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author chris
 */
public class addNewBookProcedure {
    
    private static final Object lock = new Object ();
    
    
    public static int generateID(Connection conn, Session currentUser)
    {
        int additionID = 0;
        
            
            synchronized (lock)
            {
                String lastID = "Select MAX(id) from Book";
                
                try (PreparedStatement ps = conn.prepareStatement(lastID)) {
                    
                    try (ResultSet rset = ps.executeQuery()) {
                        
                        rset.next();
                additionID = rset.getInt(1) + 1;
                    }
                } catch (SQLException ex) {
                Logger.getLogger(addNewBookProcedure.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                
            }
        
        return additionID;
    }
}
