package com.cloud_project.result_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ResultMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResultMsApplication.class, args);
	}

}
