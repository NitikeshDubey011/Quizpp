package com.nitikesh.abc.quizpp.Model;

/**
 * Created by Nitikesh Dubey on 12/26/2017.
 */

public class Category {
    private String Name;
    private String Image;

    public Category() {
    }

    public Category(String name, String image) {
        Name = name;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
