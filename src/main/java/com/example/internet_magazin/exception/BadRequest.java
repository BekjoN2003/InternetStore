package com.example.internet_magazin.exception;

public class BadRequest extends RuntimeException{
    public BadRequest(String message){
        super(message);
    }
}
