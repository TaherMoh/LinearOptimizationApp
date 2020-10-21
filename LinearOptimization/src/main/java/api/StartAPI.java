package api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StartAPI {

	public static void main(String[] args) {
		SpringApplication.run(StartAPI.class, args);
	}

}