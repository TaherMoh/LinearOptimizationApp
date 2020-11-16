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
		weights.add(new Pair<Double, Double>(0.1, 0.3));
		
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
        
        /*
         * Create empty .ampl file
         */
        File fileScript = new File("src\\main\\java\\script.ampl");
        if(!fileScript.exists()) {
        	fileScript.createNewFile();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\java\\script.ampl"));
               
        /*
         * Only write variable names that are specified by the user input start and finish
         */
        for(int x=orderStart-1; x<=orderEnd; x++) {
            components.add(componentSplit[x].replaceAll("\\s+",""));
            /*
             * Write variable names to ampl file
             */
            writer.write("var " + components.get(x-orderStart+1) + " >= 0;\n");
        }

        /*
         * For each line, we're going to have to 
         * create a optimization 
         */
        for(int x=1; x<contentsSplit.length; x++) {
        	String[] lineSplit = contentsSplit[x].split(",");
        	ArrayList<String> lineList = new ArrayList<String>();
        	
        	int varIndex = 0;
        	boolean flag = true;
        	
        	StringBuilder sbObjective = new StringBuilder();
        	StringBuilder sbConstraint = new StringBuilder();
        	for(int y=0; y<lineSplit.length; y++) {
        		if(!lineSplit[y].isEmpty()) {
        			if(flag) {
        				sbObjective.append("maximize z: ");
        				sbConstraint.append("subject to c11: ");
        			}
        				
        			sbObjective.append(" " + components.get(varIndex) + "*" + lineSplit[y] + " +");
        			sbConstraint.append(" " + components.get(varIndex) + " +");
        			
        			varIndex++;
        			
        			flag = false;
        		}        		
        	}
        	
        	sbObjective.setLength(sbObjective.length() - 1);
        	sbConstraint.setLength(sbConstraint.length() - 1);
        	
        	sbObjective.append(";");
        	sbConstraint.append(" = 1;");
        	
        	writer.write("\n" + sbObjective.toString() + "\n");        	
        	writer.write("\n" + sbConstraint.toString() + "\n");        	
        // End of row parsing / processing
        }
        
	    writer.close();
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
