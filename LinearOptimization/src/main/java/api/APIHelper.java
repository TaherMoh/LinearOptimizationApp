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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import CSVParser.OptimizeFile;
import CSVParser.Pair;

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
	@GetMapping("/get_CSV")
	public static HashMap<String, String> getCSV() throws IOException, InterruptedException {		
		HashMap<String, String> ret = new HashMap<String, String>();
		ret.put("content", readFileIntoString("synthesized_grades_output.csv"));
		return ret;
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/test_FileUpload")
    public boolean ingestDataFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
     if (file.isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "No File is Present");
                return false;
            }
             try {

                // Get the file and save it somewhere
                byte[] bytes = file.getBytes();
                Path path = Paths.get(file.getOriginalFilename());
                Files.write(path, bytes);
                System.out.println("UPLOADED :!!:!:!");
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
    }
	
    @GetMapping("/uploadStatus")
	public String uploadStatus() {
	    return "uploadStatus";
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/run_Optimizer")
	public static boolean runOptimizer(@RequestParam("file") MultipartFile filee, RedirectAttributes redirectAttributes) throws IOException {
		
		/*
		 * TODO:
		 * 	- Change these values to be passed in from front-end UI.
		 */
		int numColumns = 7;
		int orderStart = 6;
		int orderEnd = 11;
		List<Pair<Double,Double>> weights = new ArrayList<Pair<Double,Double>>();
		weights.add(new Pair<Double, Double>(0.03, 0.10));
		weights.add(new Pair<Double, Double>(0.03, 0.10));
		weights.add(new Pair<Double, Double>(0.1, 0.25));
		weights.add(new Pair<Double, Double>(0.03, 0.10));
		weights.add(new Pair<Double, Double>(0.1, 0.40));
		weights.add(new Pair<Double, Double>(0.03, 0.1));
		weights.add(new Pair<Double, Double>(0.3, 0.70));
		
		/*
		 * This file will be uploaded from the UI first, when its uploaded it will be read here. 
		 */
		File file = new File("synthesized_grades.csv");
				 
		/*
		 * Parse first line contents and store values based on user input
		 */
        String contents = readLineByLineJava8( file.getCanonicalPath() );
        String[] contentsSplit = contents.split("\n");
        String componentTitles = contentsSplit[0];
        String[] componentSplit = componentTitles.split(",");
        ArrayList<String> components = new ArrayList<String>();
        ArrayList<String> componentsOG = new ArrayList<String>();
        
        /*
         * Create empty .ampl file
         */
        File csvOutput = new File("synthesized_grades_output.csv");
        if(!csvOutput.exists()) {
        	csvOutput.createNewFile();
        }
		
        /*
         * Create empty output file
         */
        BufferedWriter outputWriter = new BufferedWriter(new FileWriter("synthesized_grades_output.csv"));
        outputWriter.write(componentTitles + ",total\n");
        outputWriter.close();
        
        /*
         * Only write variable names that are specified by the user input start and finish
         */
        for(int x=orderStart-1; x<=orderEnd; x++) {
        	componentsOG.add(componentSplit[x]);
        	String comp = componentSplit[x].replaceAll("\\s+","");
        	comp = comp.replace(":", "_");
        	comp = comp.replace("(", "_");
        	comp = comp.replace(")", "_");
            components.add(comp);
        }

        /*
         * For each line, we're going to have to 
         * create a optimization solution
         */
        for(int x=1; x<contentsSplit.length; x++) {            
        	String[] lineSplit = contentsSplit[x].split(",", -1);
        	ArrayList<String> lineList = new ArrayList<String>();
        	ArrayList<String> sameComponentsBefore = new ArrayList<String>();
        	ArrayList<String> sameComponentsAfter = new ArrayList<String>();
        	
        	int varIndex = 0;
        	boolean flag = true;
        	
        	/*
        	 * Remember the values of the fields that are not going to change
        	 */
        	for(int b=0; b<orderStart-1; b++) {
            	sameComponentsBefore.add(lineSplit[b]);
            }
        	
        	for(int b=(orderStart-1)+components.size(); b<lineSplit.length; b++) {
    			sameComponentsAfter.add(lineSplit[b]);
        	}
        	        	
        	/*
             * Create empty .ampl file
             */
            File fileScript = new File("src\\main\\java\\script.ampl");
            if(!fileScript.exists()) {
            	fileScript.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\java\\script.ampl"));
        	
        	// Handling variable declaration
        	for(int y=orderStart-1; y<=orderEnd; y++) {
                /*
                 * Write variable names to ampl file
                 */
                writer.write("var " + components.get(y-orderStart+1) + " >= 0;\n");
            }
        	
        	// Handling object function + first constraint
        	StringBuilder sbObjective = new StringBuilder();
        	StringBuilder sbConstraint = new StringBuilder();
        	for(int y=orderStart-1; y<orderEnd+1; y++) {
    			if(flag) {
    				sbObjective.append("maximize z: ");
    				sbConstraint.append("subject to c11: ");
    			}
    			
    			if(lineSplit[y].isEmpty() || lineSplit[y].contains("-")) {
    				lineSplit[y] = "0";
    			}
    			sbObjective.append(" " + components.get(varIndex) + "*" + lineSplit[y] + " +");
    			sbConstraint.append(" " + components.get(varIndex) + " +");
    			
    			varIndex++;
    			
    			flag = false;
        	}
        	
        	sbObjective.setLength(sbObjective.length() - 1);
        	sbConstraint.setLength(sbConstraint.length() - 1);
        	
        	sbObjective.append(";");
        	sbConstraint.append(" = 1;");
        	
        	writer.write("\n" + sbObjective.toString() + "\n");        	
        	writer.write("\n" + sbConstraint.toString() + "\n");  
        	
        	// Handling remaining constraints
        	StringBuilder sb = new StringBuilder();
        	int count = 12;
        	for(int w=0; w<components.size(); w++) {
        		sb.append("subject to c"+ (count) + ": ");
        		sb.append(components.get(w) + " >= " + weights.get(w).getL() + ";");
        		sb.append("\n");
        		count++;
        		
        		sb.append("subject to c"+ (count) + ": ");
        		sb.append(components.get(w) + " <= " + weights.get(w).getR() + ";");
        		sb.append("\n");
        		count++;        		
        	}
        	
        	writer.write(sb.toString() + "\n");
//        	System.out.println(sb.toString());
        	
        	
        	writer.write("solve;\n\n");
//        	System.out.println("solve; \n");
        	
        	for(String s: components) {
        		writer.write("display " + s + ";\n");
//        		System.out.println("display " + s + ";");
        	}
        	
        	writer.write("\nend;\n");
//        	System.out.println("\nend;");

        	writer.close();
        	
        	OptimizeFile.optimizeStudentGrade(components, componentSplit.length, orderStart, orderEnd
        			, sameComponentsBefore, sameComponentsAfter);
        	
//        	System.out.println("\n\n");
        	// End of row parsing / processing
        }
		return true;
	}

	//Read file content into the string with - Files.lines(Path path, Charset cs)
    private static String readLineByLineJava8(String filePath) 
    {
        StringBuilder contentBuilder = new StringBuilder();
 
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8)) 
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
 
        return contentBuilder.toString();
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
