package com.training.restfullcrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestfullcrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestfullcrudApplication.class, args);
        System.out.println("Its ok ! the api is running on port 8080");
    }

}
