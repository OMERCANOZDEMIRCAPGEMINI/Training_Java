package com.capgemini.training.loggers;

import org.springframework.stereotype.Component;

@Component
public class Logger implements ILogger{
    @Override
    public void log(String message) {
        System.out.println(message);
    }
}
