package org.example.stack.exceptions;

public class PopOnEmptyException extends RuntimeException{
    public PopOnEmptyException(String message) {
        super(message);
    }
}
