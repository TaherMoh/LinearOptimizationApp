package GLPK;

import java.io.IOException;
import java.io.InputStream;

public class GLPK_Solver {

	public static void main(String[] args) {
		Runtime runtime = Runtime.getRuntime();
		try {
		    Process p1 = runtime.exec("cmd /c start C:/Users/Taher/Desktop/LinearOptimizationApp/LinearOptimization/src/main/java/GLPK/solver.bat");
		    InputStream is = p1.getInputStream();
		    int i = 0;
		    while( (i = is.read() ) != -1) {
		        System.out.print((char)i);
		    }
		} catch(IOException ioException) {
		    System.out.println(ioException.getMessage() );
		}
	}

}
