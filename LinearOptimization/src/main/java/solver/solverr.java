package solver;

import java.util.HashMap;

public class solverr {

	public static void main(String[] args) {
		HashMap<String, String> results = new HashMap<String, String>();
		double X1 = 0;
		double X2 = 0;
		double X3 = 0;
		double X4 = 0;
		double z = 0;

		for(double x1=0.1; x1<=0.15; x1+=0.001) {
			for(double x2=0.15; x2<=0.35; x2+=0.001) {
				for(double x3=0.15; x3<=0.25; x3+=0.001) {
					for(double x4=0.4; x4<=0.6; x4+=0.001) {
						x1= Double.parseDouble(String.format("%.2f", x1));
						x2= Double.parseDouble(String.format("%.2f", x2));
						x3= Double.parseDouble(String.format("%.2f", x3));
						x4= Double.parseDouble(String.format("%.2f", x4));
						if(x1 + x2 + x3 + x4 == 1
						     && x1 >= 0.1
						     && x1 <= 0.15
						     && x2 >= 0.15
						     && x2 <= 0.35
						     && x3 <= 0.25
						     && x3 >= 0.15
						     && x4 >=  0.4
						     && x4 <=  0.6) {
							if(0 * x1 + 0.71 * x2 + 1 * x3 + 1 * x4 > z){
								z = 0 * x1 + 0.71 * x2 + 1 * x3 + 1 * x4 ;
								X1 = x1;
								X2 = x2;
								X3 = x3;
								X4 = x4;
							}
						}
					}
				}
			}
		}
		results.put("Z", String.format("%.2f", z));
		results.put("X1", String.format("%.2f", X1));
		results.put("X2", String.format("%.2f", X2));
		results.put("X3", String.format("%.2f", X3));
		results.put("X4", String.format("%.2f", X4));
		

		System.out.println(results.values());

	}

}
