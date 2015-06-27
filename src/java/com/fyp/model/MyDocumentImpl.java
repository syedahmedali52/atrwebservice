/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fyp.model;

import java.net.URL;
import uk.ac.shef.wit.textractor.model.Document;
/**
 *
 * @author AHMED ALI
 */
public class MyDocumentImpl implements Document  {
    

    private String text;

    public MyDocumentImpl(String input) {
        text = input;
    }

    @Override
    public URL getUrl() {
     return null;
    }

    @Override
    public String getContent() {
        return text;
    }

}
