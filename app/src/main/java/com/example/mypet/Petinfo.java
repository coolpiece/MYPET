package com.example.mypet;

import android.widget.EditText;

public class Petinfo {
    private String name;
    private String category;
    private String birth;

    public Petinfo(String name, String category, String birth){
        this.name=name;
        this.category =category;
        this.birth = birth;
    }
public String getName(){
        return this.name;
}
public void setName(String name){
        this.name=name;
}

public String getCategory(){
        return this.category;
}
public void setCategory(){
        this.category=category;
}
public String getBirth(){
        return this.birth;
}
public void setBirth(){
        this.birth=birth;
}
}
