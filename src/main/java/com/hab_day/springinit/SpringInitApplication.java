package com.hab_day.springinit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//이 어노테이션이 있는 부분부터 설정 읽어간다.
public class SpringInitApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringInitApplication.class, args);
	}

}

