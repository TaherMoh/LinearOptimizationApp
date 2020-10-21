import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class inputParser {
	ArrayList<ConstrainedVariable> constrainedVariables;
	
	public void InputParser() {
		constrainedVariables = new ArrayList<ConstrainedVariable>();
	}
	
	public ArrayList<ConstrainedVariable> parseInput() throws FileNotFoundException{
		Scanner scanner = new Scanner(new File("input.txt"));
		while (scanner.hasNextLine()) {
		   String line = scanner.nextLine();
		   
		   System.out.println(line);
		   // process the line
		}
		
		return constrainedVariables;
	}
	
	public static int countNumberOfVarsInConstrained(String line, ArrayList<ConstrainedVariable> constrainedVariables) {
		int numVar = 0;
		
		for(ConstrainedVariable x: constrainedVariables) {
			if(line.contains(x.getName())) {
				numVar++;
			}
		}
		
		return numVar;
	}
	
	public static void main(String args[]) throws FileNotFoundException {
		ArrayList<ConstrainedVariable> constrainedVariables =  new ArrayList<ConstrainedVariable>();;
		ArrayList<String> constraints = new ArrayList<String>();
		
		Scanner scanner = new Scanner(new File("src\\main\\java\\input.txt"));
		while (scanner.hasNextLine()) {
		   String line = scanner.nextLine();
				   
		   // Variable line
		   if(line.contains("var"))
			  constrainedVariables.add(new ConstrainedVariable(line.substring(4), -1, 9999, -9999));

		   if(line.contains("subject to ")) {
			   if(countNumberOfVarsInConstrained(line, constrainedVariables) > 1) {
				   constraints.add(line.substring(line.indexOf(":")+1, line.indexOf(";")).trim());
			   }else {
				   // it's a single var constraint
				   // check if lower than lower bound, update if needed
				   // check if higher than higher bound, update if needed
				   
				   String singleVarCons = line.substring(line.indexOf(":")+1, line.indexOf(";")).trim();
				   
				   for(ConstrainedVariable x: constrainedVariables) {
					   if(singleVarCons.contains(x.getName())) {
						   if(singleVarCons.contains(">=")) {
							   String numString = singleVarCons.substring(singleVarCons.indexOf(">=")+2).trim();							   
							   Double newNum = Double.parseDouble(numString);
							   if(newNum < x.getLowerLimit()) {
								   x.setLowerLimit(newNum);
							   }
							   if(newNum > x.getUpperLimit()) {
								   x.setUpperLimit(newNum);
							   }
						   }else if(singleVarCons.contains("<=")) {
							   String numString = singleVarCons.substring(singleVarCons.indexOf("<=")+2).trim();							   
							   Double newNum = Double.parseDouble(numString);
							   if(newNum < x.getLowerLimit()) {
								   x.setLowerLimit(newNum);
							   }
							   if(newNum > x.getUpperLimit()) {
								   x.setUpperLimit(newNum);
							   }
						   }else if(singleVarCons.contains(">")) {
							   String numString = singleVarCons.substring(singleVarCons.indexOf(">")+1).trim();							   
							   Double newNum = Double.parseDouble(numString);
							   if(newNum < x.getLowerLimit()) {
								   x.setLowerLimit(newNum);
							   }
							   if(newNum > x.getUpperLimit()) {
								   x.setUpperLimit(newNum);
							   }
						   }else if(singleVarCons.contains("<")) {
							   String numString = singleVarCons.substring(singleVarCons.indexOf("<")+1).trim();							   
							   Double newNum = Double.parseDouble(numString);
							   if(newNum < x.getLowerLimit()) {
								   x.setLowerLimit(newNum);
							   }
							   if(newNum > x.getUpperLimit()) {
								   x.setUpperLimit(newNum);
							   }
						   }
					    }
				   }
			   }
		   }
		   // System.out.println(line);
		   // process the line
		}
		
		for(ConstrainedVariable x: constrainedVariables) {
			System.out.println(x.getLowerLimit() + ">" + x.getName() + ">" + x.getUpperLimit());
		}
		for(String s: constraints) {
			System.out.println(s);
		}
		scanner.close();
	}
}
