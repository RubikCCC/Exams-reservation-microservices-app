package com.cloud_project.reservation_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ReservationMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationMsApplication.class, args);
	}

}
