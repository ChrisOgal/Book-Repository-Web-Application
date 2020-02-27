<%-- 
    Document   : Update
    Created on : Nov 12, 2019, 11:22:36 PM
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
        <title>Update Book</title>
    </head>
    <body>
        <h1>Update Book</h1>
        
        <form id = "update" action = "UpdateServlet" enctype="multipart/form-data" method = "POST">
            <fieldset>
                <legend> <h2>Fill in the fields you would like to update</h2> </legend>
              
          Title <input name = "title" type = "text"> </input>
          <br></br>
          Description <input name = "description" type = "text"> </input>
          <br></br>
          ISBN <input name = "isbn" type = "text"> </input>
          <br></br>
          Author's First Name<input name = "authorFname" type = "text"> </input>
          <br></br>
          Author's Last Name<input name = "authorLname" type = "text"> </input>
          <br></br>
          Publisher <input name = "publisher" type = "text"> </input>
          <br></br>
          Publisher's Address <input name = "address" type = "text"> </input>
          <br></br>
          Cover Image Format <input name = "format" type = "text"> </input>
           <br></br>
          Cover Image <input type="file" name="pic" accept="image/*"> </input>
           <br></br>
          <input type = "hidden" name = "oldIsbn" value = "${oldIsbn}"></input>
           <input type = "submit" value = "update"> </input>
          </fieldset>
        </form>
    </body>
</html>
