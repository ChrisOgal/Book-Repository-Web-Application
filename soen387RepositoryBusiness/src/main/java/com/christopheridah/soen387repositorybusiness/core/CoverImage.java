/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.christopheridah.soen387repositorybusiness.core;

import java.sql.Blob;

/**
 *
 * @author chris
 */
public class CoverImage {
    
    private String mimeType;
    private Blob image;
    
    public CoverImage ()
    {
        
    }
    
    public CoverImage (String mimeType, Blob image)
    {
        this.mimeType = mimeType;
        this.image = image;
    }

    public Blob getImageData() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
    
    
}
