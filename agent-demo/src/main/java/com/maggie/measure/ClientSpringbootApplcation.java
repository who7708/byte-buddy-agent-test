package com.maggie.measure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Chris
 * @version 1.0.0
 * @date 2021/01/24
 */
@SpringBootApplication(scanBasePackages = {"com"})
public class ClientSpringbootApplcation {
    public static void main(String[] args) {
        SpringApplication.run(ClientSpringbootApplcation.class, args);
    }
}
