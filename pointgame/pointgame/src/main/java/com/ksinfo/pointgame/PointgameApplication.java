package com.ksinfo.pointgame;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ksinfo.pointgame.dao") 
public class PointgameApplication {

	public static void main(String[] args) {
		SpringApplication.run(PointgameApplication.class, args);
	}

}
