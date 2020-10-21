import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class InputParser {
	ArrayList<ConstrainedVariable> constrainedVariables;
	ArrayList<String> constraints;
	String[] weights;
	String formula;
	Double step;
	
	public InputParser() {
		constrainedVariables = new ArrayList<ConstrainedVariable>();
		constraints = new ArrayList<String>();
	}
	
	public ArrayList<Object> parseInput() throws FileNotFoundException{
		ArrayList<Object> returnVal = new ArrayList<Object>();
		
		Scanner scanner = new Scanner(new File("src\\main\\java\\input.txt"));
		while (scanner.hasNextLine()) {
		   String line = scanner.nextLine();
				   
		   // Variable line
		   if(line.contains("var"))
			  constrainedVariables.add(new ConstrainedVariable(line.substring(4).trim(), -1, 9999, -9999));

		   if(line.contains("weights")) {
			   String varWeights = line.substring(8).trim();
			   weights = varWeights.split(",");
			   
			   for(int x=0; x<weights.length; x++) {
				   weights[x] = weights[x].trim();
				   
				   constrainedVariables.get(x).setWeight(Double.parseDouble(weights[x]));
			   }
		   }
		   
		   if(line.contains("maximize"))
			   formula = line.substring(9).trim();
		   
		   if(line.contains("step"))
			   step = Double.parseDouble(line.substring(5).trim());
		   
		   if(line.contains("subject to")) {
			   if(countNumberOfVarsInConstrained(line, constrainedVariables) > 1) {
				   constraints.add(line.substring(line.indexOf(":")+1).trim());
			   }else {
				   constraints.add(line.substring(line.indexOf(":")+1).trim());

				   // it's a single var constraint
				   // check if lower than lower bound, update if needed
				   // check if higher than higher bound, update if needed
				   
				   String singleVarCons = line.substring(line.indexOf(":")+1).trim();
				   
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
		}
		
		scanner.close();
		
		returnVal.add(constrainedVariables);
		returnVal.add(constraints);
		returnVal.add(formula);
		returnVal.add(step);
		
		return returnVal;
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
	
	
}
