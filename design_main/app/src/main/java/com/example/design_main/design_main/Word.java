package com.example.design_main.design_main;

/**
 * Created by User on 12/31/2017.
 */

public class Word {

    private String heading;
    private String content;

    Word(String h, String c){
        heading = h;
        content = c;
    }

    public String getHeading() { return heading; }

    public String getContent(){
        return content;
    }
}