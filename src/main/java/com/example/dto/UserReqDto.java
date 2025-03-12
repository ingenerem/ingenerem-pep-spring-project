package com.example.dto;


public class UserReqDto {
    String username;
    String password;

    public UserReqDto(){

    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }
    
}
