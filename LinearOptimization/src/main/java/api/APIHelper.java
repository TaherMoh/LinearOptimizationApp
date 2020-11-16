package api;

import solver.GeneratedSolver;
import solver.Solver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	@PostMapping("/test_FileUpload")
    public String ingestDataFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
     if (file.isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "No File is Present");
                return "redirect:uploadStatus";
            }
             try {

                // Get the file and save it somewhere
                byte[] bytes = file.getBytes();
                Path path = Paths.get(file.getOriginalFilename());
                Files.write(path, bytes);

                return "File upload successful'" + file.getOriginalFilename();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return "redirect:/uploadStatus";
    }
	
    @GetMapping("/uploadStatus")
	public String uploadStatus() {
	    return "uploadStatus";
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
		
		String[] lines = contents.split(System.getProperty("line.separator"));
		
//		ProcessBuilder builder = new ProcessBuilder(
//	            "cmd.exe", "/c", "C:/Users/Taher/GLPK/glpk-4.65/w32/glpsol --math C:/Users/Taher/Desktop/LinearOptimizationApp/LinearOptimization/src/main/java/GLPK/ex1.ampl > C:/Users/Taher/Desktop/LinearOptimizationApp/LinearOptimization/src/main/java/OutputFiles/output.txt");
		ProcessBuilder builder = new ProcessBuilder(
				"cmd.exe", "/c", "C:/Users/Taher/GLPK/glpk-4.65/w32/glpsol --math C:/Users/Taher/Desktop/LinearOptimizationApp/LinearOptimization/src/main/java/GLPK/ex1.ampl");

		builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) { break; }
            System.out.println(line);
        }

		Thread.sleep(1000);
		
		String ret = readFileIntoString("C:/Users/Taher/Desktop/LinearOptimizationApp/LinearOptimization/src/main/java/OutputFiles/output.txt");
		retVal.put("log", ret);

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
