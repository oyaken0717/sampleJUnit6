package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("app.db.entity")//■ domain
@SpringBootApplication(scanBasePackages = "app.db.dao")//■ DAO = repository = service
public class SampleJUnit6Application {

	public static void main(String[] args) {
		SpringApplication.run(SampleJUnit6Application.class, args);
	}

}

//@EntityScan("app.db.entity")
//@SpringBootApplication(scanBasePackages = "app.db.dao")
//public class DaoTestApplication {
//
//    public static void main(String[] args) {
//        SpringApplication.run(DaoTestApplication.class, args);
//    }
//
//}