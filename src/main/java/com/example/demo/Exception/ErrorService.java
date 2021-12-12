package com.example.demo.Exception;



public class ErrorService extends Exception {

    public ErrorService(String msg) {
        super(msg);
    }

    
    public ErrorService(String msg, Throwable cause) {
        super(msg, cause);
    }

}