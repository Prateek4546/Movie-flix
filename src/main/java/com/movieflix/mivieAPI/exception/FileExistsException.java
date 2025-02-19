package com.movieflix.mivieAPI.exception;

public class FileExistsException extends RuntimeException{

    public FileExistsException(String message){
        super(message);
    }
}
