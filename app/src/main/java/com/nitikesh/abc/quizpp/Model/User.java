package com.nitikesh.abc.quizpp.Model;

/**
 * Created by Nitikesh Dubey on 12/21/2017.
 */

public class User {
    private String userName,password,email;

    public User() {
    }

    public User(String userName, String password, String email) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
