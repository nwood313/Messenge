package com.coderscampus.messenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class MessengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessengeApplication.class, args);
    }
}
