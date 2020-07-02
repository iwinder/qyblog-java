package com.windcoder.qycms.handler;

import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler  extends ExceptionHandlerExceptionResolver {
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResponseDto businessExceptionHandler(BusinessException e) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(false);
        log.error("业务异常：{}", e.getMessage());
        responseDto.setMessage(e.getMessage());
        return responseDto;
    }
}
