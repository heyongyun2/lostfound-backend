package org.example.springboot.exception;
import org.example.springboot.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice("org.example.springboot.controller")

public class GlobalExceptionHandle {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return  Result.error("禁止请求");
    }
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result error(CustomException e){
        e.printStackTrace();
        return Result.error(e.getCode(),e.getMsg());
    }


}
