package com.hms.user.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

public class HMSException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public HMSException(String message)
    {
        super(message);
    }
}
