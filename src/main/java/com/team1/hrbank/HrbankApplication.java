package com.team1.hrbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HrbankApplication {

  public static void main(String[] args) {
    SpringApplication.run(HrbankApplication.class, args);
  }

}
