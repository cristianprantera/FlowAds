package com.hackathon.hackathon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HackathonApplication {

	public static void main(String[] args) {
		SpringApplication.run(HackathonApplication.class, args);
	    System.out.println("🟡 CLIENT_ID: " + System.getenv("OPEN_GATEWAY_CLIENT_ID"));
	    System.out.println("🟡 CLIENT_SECRET: " + System.getenv("OPEN_GATEWAY_CLIENT_SECRET"));
	}

}
