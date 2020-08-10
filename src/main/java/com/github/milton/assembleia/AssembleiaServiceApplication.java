package com.github.milton.assembleia;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = { "classpath:restful.properties" }, encoding = "UTF-8")
@SpringBootApplication
public class AssembleiaServiceApplication {

	   @Bean
	   public ModelMapper modelMapper() {
	      ModelMapper modelMapper = new ModelMapper();
	      return modelMapper;
	   }
	
	public static void main(String[] args) {
		SpringApplication.run(AssembleiaServiceApplication.class, args);
	}

}
