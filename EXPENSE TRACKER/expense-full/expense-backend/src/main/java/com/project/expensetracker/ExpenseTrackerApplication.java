package com.project.expensetracker;

import com.project.expensetracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ExpenseTrackerApplication {

	public static void main(String [] args) {
		SpringApplication.run(ExpenseTrackerApplication.class, args);
		System.out.println("Hello World");


	}

}
