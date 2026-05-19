package com.project.userservice.exception;

public class BadCredentialsException extends RuntimeException {
    public BadCredentialsException(String msg) { super(msg); }
}
