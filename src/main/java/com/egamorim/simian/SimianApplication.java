package com.egamorim.simian;

import com.egamorim.simian.util.SimianUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SimianApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimianApplication.class, args);
	}

	@Bean
	public SimianUtil simianUtil() {
		return new SimianUtil();
	}
}
