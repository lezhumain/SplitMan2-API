package com.dju.demo;

import com.dju.demo.helpers.SQLiteJDBC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) throws InterruptedException {
		SQLiteJDBC.main(args);
		SpringApplication.run(DemoApplication.class, args);
	}
}
