
CREATE PROCEDURE addNewBook (INOUT addID INT, 
IN addTitle VARCHAR(128),
IN addInfo VARCHAR(128),
  IN addIsbn VARCHAR(128),
  IN addAuthorFname VARCHAR (128),
  IN addAuthorLname VARCHAR(128),
  IN addPublisherName VARCHAR (128),
  IN addPublisherAddress VARCHAR (128),
  IN addCover BLOB,
  OUT currentSize INT)

PARAMETER STYLE JAVA READS SQL DATA LANGUAGE JAVA EXTERNAL NAME 'addNewBookProcedure.generateID';
  
 
  
