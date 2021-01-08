package kr.ac.pusan.bsclab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@SpringBootApplication
public class GeneticAlgorithmApplication {

	@GetMapping(value="/")
	public String sayHello() {
		return "Hello";
	}

	public static void main(String[] args) {
		SpringApplication.run(GeneticAlgorithmApplication.class, args);
	}

}
