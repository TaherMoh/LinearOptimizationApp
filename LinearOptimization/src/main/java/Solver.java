import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Solver {

	public static void main(String[] args) throws IOException {
		InputParser inputParser = new InputParser();
		ArrayList<Object> parsedInput = inputParser.parseInput();
		
		@SuppressWarnings("unchecked")
		ArrayList<ConstrainedVariable> listOfVar = (ArrayList<ConstrainedVariable>) parsedInput.get(0);
		@SuppressWarnings("unchecked")
		ArrayList<String> constraints = (ArrayList<String>) parsedInput.get(1);
		String formula = (String) parsedInput.get(2);
		Double step = (Double) parsedInput.get(3);
		
		formula = formula + " > z";
		
		File file = new File("src\\main\\java\\GeneratedSolver.java");
		if(!file.exists()) {
			file.createNewFile();
		}
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\java\\GeneratedSolver.java"));
	    writer.write(bruteForceWriter(listOfVar, constraints, formula, step).toString());
	    
	    writer.close();
	}
	
	@SuppressWarnings("unused")
	private static StringBuilder bruteForceWriter(ArrayList<ConstrainedVariable> listOfVar, ArrayList<String> constraints, String maximizeFormula, Double step) {
		StringBuilder sb = new StringBuilder();

		sb.append("\n");

		sb.append("public class GeneratedSolver {");
		sb.append("\n");

		sb.append("\n");
		
		sb.append("\tpublic static void main(String[] args) {");
		sb.append("\n");

		// Declare variables
		for(ConstrainedVariable x: listOfVar) {			
			sb.append("\t\tdouble " + x.getName().toUpperCase() + " = 0;");
			sb.append("\n");
		}
		sb.append("\t\tdouble z = 0;");
		sb.append("\n");

		sb.append("\n");
		
		// For loops
		int count = 2;
		for(ConstrainedVariable x: listOfVar) {
			// Tabbing
			for(int t = 0; t<count; t++)
				sb.append("\t");
				
			sb.append("for(double " + x.getName().toLowerCase() + "=" + x.getLowerLimit() +"; "
					+ x.getName().toLowerCase() + "<=" + x.getUpperLimit() + "; "
					+ x.getName().toLowerCase() + "+=" + step + ") {");
			sb.append("\n");
			
			count++;
		}
		
		// Tabbing
		for(ConstrainedVariable x: listOfVar) {
			for(int t = 0; t<count; t++)
			sb.append("\t");
			sb.append(x.getName().toLowerCase() + 
					"= Double.parseDouble(String.format(\"%.2f\", " + 
					x.getName().toLowerCase() + "));");
			sb.append("\n");
		}
		
		// Tabbing
		for(int t = 0; t<count; t++)
			sb.append("\t");
		
		int consCount = 1;
		for(String constraint: constraints) {
			if(consCount == 1) {
				sb.append("if(" + constraint + "\n");	
			}else {
				for(int t = 0; t<count; t++)
					sb.append("\t");
				
				sb.append("     && " + constraint);	
				if(consCount != constraints.size()) {
					sb.append("\n");
				}
			}
		
			consCount ++;
		}
		
		sb.append(") {");
		sb.append("\n");
		
		count++;
		
		for(int t = 0; t<count; t++)
			sb.append("\t");
		
		sb.append("if(" + maximizeFormula + "){");
		sb.append("\n");
		
		count++;
		
		for(int t = 0; t<count; t++)
			sb.append("\t");
		sb.append("z = " + maximizeFormula.substring(0, maximizeFormula.indexOf(">")) + ";");
		sb.append("\n");
		
		// New max values for Z and x1...xn found so update them
		for(ConstrainedVariable x: listOfVar) {
			for(int t = 0; t<count; t++)
				sb.append("\t");
			sb.append(x.getName().toUpperCase() + " ");
			sb.append("= " + x.getName().toLowerCase() + ";");
			sb.append("\n");
		}
		
		count--;
		for(int t = 0; t<count; t++)
			sb.append("\t");
		sb.append("}");
		sb.append("\n");
		
		count--;
		for(int t = 0; t<count; t++)
			sb.append("\t");
		sb.append("}");
		sb.append("\n");

		for(int x=0; x<listOfVar.size(); x++) {
			count--;
			for(int t = 0; t<count; t++)
				sb.append("\t");
			sb.append("}");
			sb.append("\n");
		}

		for(int t = 0; t<count; t++)
			sb.append("\t");
		sb.append("System.out.println(\"Z = \" + String.format(\"%.2f\", z));");
		sb.append("\n");

		for(ConstrainedVariable x: listOfVar) {
			for(int t = 0; t<count; t++)
				sb.append("\t");
			String name = x.getName().toUpperCase();
			sb.append("System.out.println(\"" + name + " = \" + String.format(\"%.2f\", " + name + "));");
			sb.append("\n");
		}
		
		count--;
		for(int t = 0; t<count; t++)
			sb.append("\t");
		sb.append("}\n");
		
		count--;
		for(int t = 0; t<count; t++)
			sb.append("\t");
		sb.append("}\n");
		
		return sb;
	}

	private static double findLowestLimit(ArrayList<ConstrainedVariable> listOfVar) {
		double lowestLimit = 999999;
		
		for(ConstrainedVariable x: listOfVar) {
			if(x.getLowerLimit() < lowestLimit) {
				lowestLimit = x.getLowerLimit();
			}
		}
		
		return lowestLimit;
	}
	
	private static double findHighestLimit(ArrayList<ConstrainedVariable> listOfVar) {
		double highestLimit = 0.0;
		
		for(ConstrainedVariable x: listOfVar) {
			if(x.getUpperLimit() > highestLimit) {
				highestLimit = x.getUpperLimit();
			}
		}
		
		return highestLimit;
	}
}
