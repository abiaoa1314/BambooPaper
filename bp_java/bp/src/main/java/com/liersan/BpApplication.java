package com.liersan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.liersan.bp.mapper")
public class BpApplication {
    public static void main(String[] args) {
        SpringApplication.run(BpApplication.class,args);
    }
}
