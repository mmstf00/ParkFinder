package com.parkfinder.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAnyException(Exception e) {
        return new ModelAndView("error").addObject("exception", e.getMessage());
    }

    @ExceptionHandler(UserExistsException.class)
    public ModelAndView userExistHandler(UserExistsException e) {
        return new ModelAndView("registration-fail").addObject("exception", e.getMessage());
    }
}
