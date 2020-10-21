
public class GeneratedSolver {

	public static void main(String[] args) {
		double X1 = 0;
		double X2 = 0;
		double X3 = 0;
		double z = 0;

		for(double x1=0.0; x1<=7.0; x1+=1.0) {
			for(double x2=0.0; x2<=6.0; x2+=1.0) {
				for(double x3=0.0; x3<=6.0; x3+=1.0) {
					x1= Double.parseDouble(String.format("%.2f", x1));
					x2= Double.parseDouble(String.format("%.2f", x2));
					x3= Double.parseDouble(String.format("%.2f", x3));
					if(x1 + x2 <=  9
					     && 3*x1 + x2 <= 18
					     && x1 <=  7
					     && x2 <=  6
					     && x3 <=  6
					     && x1 >=  0
					     && x2 >=  0
					     && x3 >=  0) {
						if(x1 + 2*x2 + 3*x3 > z){
							z = x1 + 2*x2 + 3*x3 ;
							X1 = x1;
							X2 = x2;
							X3 = x3;
						}
					}
				}
			}
		}
		System.out.println("Z = " + String.format("%.2f", z));
		System.out.println("X1 = " + String.format("%.2f", X1));
		System.out.println("X2 = " + String.format("%.2f", X2));
		System.out.println("X3 = " + String.format("%.2f", X3));
	}
}
