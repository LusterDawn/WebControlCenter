package com.example.keshe;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;

/*
exclude = { DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class }
 */
@SpringBootApplication
@MapperScan("com.example.keshe.dao")
public class KesheApplication {

    public static void main(String[] args) {
        SpringApplication.run(KesheApplication.class, args);
    }

}
