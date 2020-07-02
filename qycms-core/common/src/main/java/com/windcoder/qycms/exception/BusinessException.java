package com.windcoder.qycms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
public class BusinessException  extends RuntimeException{
//    private BusinessExceptionCode code;

    public BusinessException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public BusinessException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }
}
