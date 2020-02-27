/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function deleteBook()
{
    var link = "";
    var bookISBN = prompt ("Please enter the book isbn to be deleted:");
    if (bookISBN !== null)
    {
        link = document.getElementById("delete");
        link.href = "DeleteServlet?deleteparam1=delete1&deleteparam2=" + bookISBN;
    }
    
    
}

function deleteAllBooks()
{
    var delLink = "";
    var accept = confirm("Are you sure you want to delete all the books?");
    
    if (accept === true)
    {
        delLink = document.getElementById("deleteAll");
        delLink.href = "DeleteServlet?deleteparam1=delete2";
    }
    
}

function getBookInfo ()
{
    var infLink = "";
    
    var bookInfoISBN = prompt ("Please enter the book isbn:");
    
    if (bookInfoISBN !== null)
    {
        infLink = document.getElementById("viewInfo");
        infLink.href = "DeleteServlet?deleteparam1=info&deleteparam2=" + bookInfoISBN;
    }
}

function addBook ()
{
    var addLink = "";
    
   
        addLink = document.getElementById("addBook");
        addLink.href = "DeleteServlet?deleteparam1=add";
    
}

function updateBook()
{
    var updateLink = "";
    
    var updateISBN = prompt ("Please enter the book isbn:");
    
    if (updateISBN !== null)
    {
        updateLink = document.getElementById("updateBook");
        updateLink.href = "DeleteServlet?deleteparam1=update&deleteparam2=" + updateISBN;
    }
}


