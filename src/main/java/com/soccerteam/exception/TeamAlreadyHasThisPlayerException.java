package com.soccerteam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Team already has this player!")
public class TeamAlreadyHasThisPlayerException extends RuntimeException{

    public TeamAlreadyHasThisPlayerException(String message){
        super(message);
    }

    public  TeamAlreadyHasThisPlayerException(){

    }
}
