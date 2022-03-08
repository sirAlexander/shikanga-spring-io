package com.codeboard.monitoring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/monitor")
    public String monitor() {

        try {
            boolean condition = true;
            while (condition){
                Runnable r = () -> {
                    while (true) {

                    }
                };
                new Thread(r).start();
                Thread.sleep(5000);
            }
        } catch (Exception ex){}
        return "Hello World";
    }
}
