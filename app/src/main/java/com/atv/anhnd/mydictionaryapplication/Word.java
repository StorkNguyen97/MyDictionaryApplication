package com.atv.anhnd.mydictionaryapplication;

public class Word {
    public String tu = "";
    public String nghia = "";

    public Word() {

    }

    //Word constructor
    public Word(String key, String value) {
        this.tu = key;
        this.nghia = value;
    }
}
