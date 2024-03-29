package com.capgemini.training.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("hello")
    @ApiOperation(value = "Get hello world")
    public String getHello(){
        return "Hello World";
    }

    @GetMapping("demo")
    @ApiOperation(value = "Get hello world")
    public String getCiCd(){
        return "Hello from DEMO";
    }

}
