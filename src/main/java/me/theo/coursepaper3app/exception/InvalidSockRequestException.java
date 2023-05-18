package me.theo.coursepaper3app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "invalid sock request")
public class InvalidSockRequestException extends RuntimeException {
    public InvalidSockRequestException(String message) {
        super(message);
    }

}
