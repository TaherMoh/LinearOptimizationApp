package api;

import solver.GeneratedSolver;
import solver.Solver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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
//	@GetMapping("/test_API")
//	public static HashMap<String, String> testAPI() throws IOException, InterruptedException {
//		Solver.generateSolver();
//		
//		TimeUnit.SECONDS.sleep(3);
//		
//		Application.restart();
//		
//		return GeneratedSolver.solve();
//	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/test_Multi")
	public static HashMap<String, String> testMulti(@RequestBody String formula) throws IOException, InterruptedException {
	    
		Solver.generateSolver(formula);
		
		TimeUnit.SECONDS.sleep(3);
		
		Application.restart();
		
		return GeneratedSolver.solve();
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/test_GLPK")
	public static HashMap<String, String> testGLPK(@RequestBody String contents) throws IOException, InterruptedException {
		HashMap<String, String> retVal = new HashMap<String, String>();
		
		File file = new File("src\\main\\java\\GLPK\\ex1.ampl");
		if(!file.exists()) {
			file.createNewFile();
		}
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\java\\GLPK\\ex1.ampl"));
	    writer.write(contents);	    
	    writer.close();
	    
		Thread.sleep(1000);

		Runtime runtime = Runtime.getRuntime();
		try {
		    Process p1 = runtime.exec("cmd /c start C:/Users/Taher/Desktop/LinearOptimizationApp/LinearOptimization/src/main/java/GLPK/solver.bat");
		} catch(IOException ioException) {
		    System.out.println(ioException.getMessage() );
		}

		Thread.sleep(1000);
		
		String ret = readFileIntoString("C:/Users/Taher/Desktop/LinearOptimizationApp/LinearOptimization/src/main/java/OutputFiles/output.txt");
		retVal.put("log", ret);
		
//		file.delete();
//		File file2 = new File("src\\main\\java\\OutputFiles\\output.txt");
//		file2.delete();
//		
		return retVal;
	}
	
	/**
     * This method reads the contents of a file and returns it into as a single String
     * 
     * @param filePath: The path of the file to read
     * @return string with the contents of the specified file
     */
    public static String readFileIntoString(String filePath) 
    {
        String content = "";
        try
        {
            content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        return content;
    }
}
