
CREATE TABLE Book
(
  id INT NOT NULL,
  title VARCHAR(128),
  info VARCHAR(128),
  isbn VARCHAR(128) UNIQUE,
  authorFname VARCHAR (128),
  authorLname VARCHAR(128),
  publisherName VARCHAR (128),
  publisherAddress VARCHAR (128),
  imageType VARCHAR (128),
  imageData BLOB,
  PRIMARY KEY (id) 
);