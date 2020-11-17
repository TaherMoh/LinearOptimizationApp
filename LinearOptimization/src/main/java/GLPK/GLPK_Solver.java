package GLPK;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GLPK_Solver {

	public static void main(String[] args) throws IOException {
		ProcessBuilder builder = new ProcessBuilder(
				"cmd.exe", "/c", "C:/Users/Taher/GLPK/glpk-4.65/w32/glpsol --math C:/Users/Taher/Desktop/LinearOptimizationApp/LinearOptimization/src/main/java/script.ampl");

		builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) { break; }
            System.out.println(line);
        }
	}

}
