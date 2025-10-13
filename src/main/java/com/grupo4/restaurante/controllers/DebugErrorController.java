/*package com.grupo4.restaurante.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class DebugErrorController implements ErrorController {
    private final ErrorAttributes errorAttributes;

    @GetMapping("/error")
    public Map<String, Object> handleError(WebRequest webRequest) {
        return errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.of(
                ErrorAttributeOptions.Include.EXCEPTION,
                ErrorAttributeOptions.Include.MESSAGE,
                ErrorAttributeOptions.Include.STACK_TRACE,
                ErrorAttributeOptions.Include.BINDING_ERRORS
        ));
    }

}*/
