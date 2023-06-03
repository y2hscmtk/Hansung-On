package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Credit2 {

    //"crew"
    @SerializedName("director")
    private String director;

    //"cast"
    @SerializedName("leading_role")
    private String leading_role;

    //"id"
    private Long id;

    public Credit2(String director, String leading_role, Long id) {
        this.director = director;
        this.leading_role = leading_role;
        this.id = id;
    }

    public String getDirector() {
        return director;
    }

    public void setCrew(String director) {
        this.director = director;
    }

    public String getLeading_role() {
        return leading_role;
    }

    public void setLeading_role(String leading_role) {
        this.leading_role = leading_role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



}
