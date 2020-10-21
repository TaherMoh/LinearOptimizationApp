import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Solver {

	public static void main(String[] args) throws IOException {
		ArrayList<ConstrainedVariable> listOfVar = new ArrayList<ConstrainedVariable>();
		ArrayList<String> constraints = new ArrayList<String>();
		
		listOfVar.add(new ConstrainedVariable("x1", 0, 0, 0.15));
		listOfVar.add(new ConstrainedVariable("x2", 0.71, 0, 0.35));
		listOfVar.add(new ConstrainedVariable("x3", 1, 0, 0.15));
		listOfVar.add(new ConstrainedVariable("x4", 1, 0, 0.6));
				
		constraints.add("x1 >= 0.1");
		constraints.add("x1 <= 0.15");
		
		constraints.add("x2 >= 0.15");
		constraints.add("x2 <= 0.35");
		
		constraints.add("x3 <= 0.25");
		constraints.add("x3 >= 0.15");
		
		constraints.add("x4 >=  0.4");
		constraints.add("x4 <=  0.6");

		constraints.add("x1 + x2 + x3 + x4 == 1");
		
		String maximizeFormula = "0 * x1 + 0.71 * x2 + 1 * x3 + 1 * x4 > z";
		
		File file = new File("src\\main\\java\\GeneratedSolver.java");
		if(!file.exists()) {
			file.createNewFile();
		}
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\java\\GeneratedSolver.java"));
	    writer.write(bruteForceWriter(listOfVar, constraints, maximizeFormula).toString());
	    
	    writer.close();
		
		double a = 0.49;
		double b = 0.51;
		
		if(a+b == 1) {
			System.out.println("yes");
		}
	}
	
	@SuppressWarnings("unused")
	private static StringBuilder bruteForceWriter(ArrayList<ConstrainedVariable> listOfVar, ArrayList<String> constraints, String maximizeFormula) {
		double min = findLowestLimit(listOfVar);
		double max = findHighestLimit(listOfVar);
		
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
				
			sb.append("for(double " + x.getName().toLowerCase() + "=0; "
					+ x.getName().toLowerCase() + "<=" + max + "; "
					+ x.getName().toLowerCase() + "+=0.01) {");
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
