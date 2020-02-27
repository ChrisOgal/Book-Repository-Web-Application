<%-- 
    Document   : Details
    Created on : Nov 12, 2019, 11:22:14 PM
    Author     : chris
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.christopheridah.soen387repositorybusiness.core.*"%>
<%@page import="com.christopheridah.soen387repository.ui.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> Book Information</title>
    </head>
    <body>
        <h1> Book Information</h1>
        
            ${bookBean.bookInfo}
            
            <br> </br>   
            <c:choose>
            <c:when test = '${empty bookBean.coverImage}' >
                
            <br></br>
            <br></br>
            <h1>This book has no cover. </h1>
                <h2>  <a href = "DeleteServlet?deleteparam1=update&deleteparam2=${bookBean.isbn}"> Update Book</a> </h2>
            </c:when>
            
            <c:otherwise>
                
            Cover Image: <img src="data:image/jpg;base64,${bookBean.coverImage}">
        
            
          
            
            </c:otherwise>
            
            </c:choose>
           
            
    </body>
</html>
