package com.in28minutes.rest.webservices.restfulwebservices.helloworld;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    private final MessageSource messageSource;

    public HelloWorldController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping(value = "/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World");
    }

    // hello-world/path-variable/in28minutes
    @GetMapping(value = "/hello-world/path-variable/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }

    @GetMapping(value = "/hello-world-internationalized")
    public String helloWorldInternationalized(
            @RequestHeader(name = HttpHeaders.ACCEPT_LANGUAGE, required = false) Locale locale
    ) {
        return messageSource.getMessage(
                "good.morning.message",
                null,
                "Default Message",
                locale);

        //en = Hello World
        //nl = Goede Morgen
        //fr = Bonjour
    }

    @GetMapping(value = "/hello-world-internationalized-v2")
    public String helloWorldInternationalizedv2() {
        return messageSource.getMessage(
                "good.morning.message",
                null,
                "Default Message",
                LocaleContextHolder.getLocale());

        //en = Hello World
        //nl = Goede Morgen
        //fr = Bonjour
    }


}
