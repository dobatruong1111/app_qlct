package com.example.projecttwonew.object;

import java.util.ArrayList;

public class User {

    public String email;
    public String pass;
    public int countWorks = 0;

    public User(){
    }

    public User(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getCountWorks() {
        return countWorks;
    }

    public void setCountWorks(int countWorks) {
        this.countWorks = countWorks;
    }
}
