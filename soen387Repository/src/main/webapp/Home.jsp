<%-- 
    Document   : Home
    Created on : Nov 12, 2019, 11:21:40 PM
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
        <script type="text/javascript" src="javascript/Home.js"></script>
        <title>Home</title>
    </head>
    <body>
        <h2> Current Stock: </h2>
       
       <table>
            <tr>
                <th>Title</th>
                <th>ISBN</th>
                <th>Author</th>
                <th>Publisher</th>
            </tr>
        
        <c:forEach items = "${allBooks}" var = "bookBean" varStatus = "status">
                <tr>
                    <td> ${bookBean.title} </td>
                    <td>${bookBean.isbn}</td>
                    <td>${bookBean.author.firstName} &nbsp; ${bookBean.author.lastName}</td>
                    <td>${bookBean.publisher.name}</td>
                    <%--  <td><img src="data:image/jpg;base64,${coverInfo[status.index]}"></td> --%>
                </tr>
                </c:forEach>
       </table>
        <h1> <a id = "delete" href ="#delete" onclick = "deleteBook()"> Delete Book</a></h1>
        <h1> <a id = "deleteAll" href = "#deleteAll"  onclick = "deleteAllBooks()" > Delete All Books</a></h1>
        <h1> <a id = "viewInfo" href = "#viewInfo" onclick="getBookInfo()"> View Book Info </a></h1>
        <h1> <a id = "addBook" href = "#addBook" onclick="addBook()"> Add Book </a></h1>
        <h1> <a id = "updateBook" href = "#updateBook" onclick="updateBook()"> Update Book  </a></h1>
        
        <h2>Click here to <a href = "logout.html"> log out</a></h2>
    </body>
</html>
