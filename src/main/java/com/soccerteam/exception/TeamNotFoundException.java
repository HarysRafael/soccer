package com.soccerteam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Team not found!")
public class TeamNotFoundException extends RuntimeException{

    public TeamNotFoundException(String message){
        super(message);
    }

    public TeamNotFoundException(){

    }

}
