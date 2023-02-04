package br.com.douglas.controler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "br.com.douglas")
@EntityScan(basePackages = "br.com.douglas.entity.entities.temp")
public class ControlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ControlerApplication.class, args);
    }
}
