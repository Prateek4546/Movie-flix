package com.movieflix.mivieAPI.exception;

public class EmptyFileException extends  RuntimeException{
    public EmptyFileException(String message){
        super(message);
    }
}
