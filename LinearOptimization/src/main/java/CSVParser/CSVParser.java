package CSVParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class CSVParser {

	@SuppressWarnings("unchecked")
	public static void main(String args[]) throws IOException {
		
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
         * Create output file 
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
    
    
}
