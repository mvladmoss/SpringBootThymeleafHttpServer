package com.bsuir.second.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
@Controller
public class ApiExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public String handle(ServiceException e, Model model) {
        log.error(e.getMessage());
        model.addAttribute("message", e.getMessage());
        return "error";
    }
}
