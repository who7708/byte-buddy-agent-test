package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }

    // @Bean
    // CommandLineRunner commandLineRunner(ApplicationContext ctx) {
    //     return args -> Arrays.stream(ctx.getBeanDefinitionNames()).forEach(System.out::println);
    // }
}
