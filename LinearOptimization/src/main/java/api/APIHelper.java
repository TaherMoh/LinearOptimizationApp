package api;

import solver.GeneratedSolver;
import solver.Solver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIHelper {
	
//	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/test_API")
	public static HashMap<String, String> testAPI() throws IOException, InterruptedException {
		Solver.generateSolver();
		
		TimeUnit.SECONDS.sleep(3);
		
		Application.restart();
		
		return GeneratedSolver.solve();
	}
	
//	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/test_Input")
	public static boolean testInput(@RequestBody String contents) throws IOException, InterruptedException {
		File file = new File("src\\main\\java\\input.txt");
		if(!file.exists()) {
			file.createNewFile();
		}
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\java\\input.txt"));
	    writer.write(contents);
	    
	    writer.close();
	    
	    return true;
	}
}
