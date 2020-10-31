package api;

import solver.GeneratedSolver;
import solver.Solver;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIHelper {
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/test_API")
	public static HashMap<String, String> testAPI() throws IOException, InterruptedException {
		Solver.generateSolver();
		
		TimeUnit.SECONDS.sleep(3);
		
		Application.restart();
		
		return GeneratedSolver.solve();
	}
	
}
