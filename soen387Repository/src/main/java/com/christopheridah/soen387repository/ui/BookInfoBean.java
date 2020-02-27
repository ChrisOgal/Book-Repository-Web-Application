/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.christopheridah.soen387repository.ui;

import java.io.Serializable;

/**
 *
 * @author chris
 */
public class BookInfoBean implements Serializable {
    
    private String bookInfo;
    private String isbn;
    private String coverImage;
    
    public BookInfoBean ()
    {
        
    }

    public String getBookInfo() {
        return bookInfo;
    }

    public void setBookInfo(String bookInfo) {
        this.bookInfo = bookInfo;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
    
    public boolean isCoverImageEmpty()
    {
        if (coverImage == null)
        {
            return true;
        }
        
        else 
            return false;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    
    
}
