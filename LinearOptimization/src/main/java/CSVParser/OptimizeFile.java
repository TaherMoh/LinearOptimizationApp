package CSVParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class OptimizeFile {

	public static void optimizeStudentGrade(ArrayList<String> components, int totalNumComponents, int colStart, int colEnd, ArrayList<String> sameComponentsBefore, ArrayList<String> sameComponentsAfter) throws IOException {
		String currentDir = System.getProperty("user.dir");

		ProcessBuilder builder = new ProcessBuilder(
				"cmd.exe", "/c", currentDir + "/src/glpk-4.65/w32/glpsol --math " + currentDir + "/src/main/java/script.ampl");

		builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        int count = 0;
        
        BufferedWriter outputWriter = new BufferedWriter(new FileWriter("synthesized_grades_output.csv", true));

        String objVal = "";
        StringBuilder sb = new StringBuilder();

        String oldLine = "";
        while (true) {
            line = r.readLine();
            if (line == null) { break; }
            // Assignment weights and stuff
            for(String s: components) {
            	if(line.contains(s + ".val")) {
            		/*
            		 * Add old values that are not going to change (left hand)
            		 */
            		if(s.equals(components.get(0))) {
            			for(int n=0; n<colStart-1; n++) {
            				sb.append(sameComponentsBefore.get(n) + ",");
            			}
            		}
            		
            		Double num = Double.parseDouble(line.substring(line.indexOf("=")+1).trim());
            		sb.append(String.format("%.2f", num) + ",");
            		
            		/*
            		 * Add old values that are not going to change (right hand)
            		 */
            		if(s.equals(components.get(components.size()-1))) {
            			for(int z=0; z<sameComponentsAfter.size(); z++) {
            				sb.append(sameComponentsAfter.get(z) + ",");
            			}
            		}
            	}
            }
            
            // Objective value result: 
            if(line.contains("SOLUTION FOUND")) {
            	line = oldLine;
            	Double exp = 0.0;
            	Double base = 0.0;
            	if(line.contains("+")) {
            		exp = Double.parseDouble(line.substring(line.indexOf("+")+1, line.indexOf("inf")).trim());            		
            	}
        		base = Double.parseDouble(line.substring(line.indexOf("=")+1, line.indexOf("e")).trim());
        		
        		objVal = base * Math.pow(10, exp) + "";
        	}
            

            oldLine = line;
        }
        
		Double num = Double.parseDouble(objVal);
		
        sb.append(String.format("%.2f", num) + "\n");
//        sb.append(objVal + "\n");
        outputWriter.write(sb.toString());
        
        outputWriter.close();
	}
}
