package br.com.douglas.rsql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "br.com.douglas")
@EntityScan(basePackages = "br.com.douglas")
@EnableJpaRepositories(basePackages = "br.com.douglas")
public class RsqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(RsqlApplication.class, args);
    }

}
