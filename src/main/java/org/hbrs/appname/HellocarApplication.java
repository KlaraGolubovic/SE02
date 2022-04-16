package org.hbrs.appname;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class HellocarApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(HellocarApplication.class, args);
    }
}
