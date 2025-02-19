package com.movieflix.mivieAPI.exception;

public class MovieNotFoundException extends  RuntimeException{

    public  MovieNotFoundException(String message){
        super(message);
    }
}
