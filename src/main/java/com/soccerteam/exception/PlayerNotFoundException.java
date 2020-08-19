package com.soccerteam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Player not found!")
public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException(String message) {
        super(message);
    }

    public PlayerNotFoundException() {

    }
}
