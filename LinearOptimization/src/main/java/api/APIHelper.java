package api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIHelper {
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/test_API")
	public static String testAPI() {
		return "Hello World";
	}
	
}
