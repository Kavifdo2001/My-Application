package com.example.myapplication;

public class ReadwriteUserDetails {
    public String name , email, password;

    public ReadwriteUserDetails(){};
    public ReadwriteUserDetails(String fullName , String Email , String password){
        this.name=fullName;
        this.email=Email;
        this.password= password;
    }

}
