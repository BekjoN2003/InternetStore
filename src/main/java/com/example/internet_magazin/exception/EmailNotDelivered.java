package com.example.internet_magazin.exception;

public class EmailNotDelivered extends RuntimeException {

    public EmailNotDelivered (String message){
        super(message);
    }
}
