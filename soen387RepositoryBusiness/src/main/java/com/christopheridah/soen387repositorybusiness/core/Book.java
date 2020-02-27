/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.christopheridah.soen387repositorybusiness.core;

/**
 *
 * @author chris
 */
public class Book {
    
    private int id;
    private String isbn, title, description;
    private Author author;
    private Publisher publisher;
    private CoverImage cover;
    
    public Book()
    {
    
    }

    public Book(int id, String isbn, String title, String description, Author author, Publisher publisher, CoverImage cover) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
        this.cover = cover;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public CoverImage getCover() {
        return cover;
    }

    public void setCover(CoverImage cover) {
        this.cover = cover;
    } 
    
}
