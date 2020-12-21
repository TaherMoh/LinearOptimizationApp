# LinearOptimizationApp

## About
This is an online tool that uses a linear optimization solver to optimize a students final grades

The student grade optimizer (SGO) uses linear optimization to optimize students grades weights. Given a lower and upper bound for each grade, the optimizer uses Linear Optimization (also known as Linear Programming) to find an optimal weights for grades to get the best overall grade for the student. The SGO converts the user .CSV input combined with the inputed weights to solve for a Linear objective function for each students optimal grade. The output is a .CSV file that contains the same headers (coloumn names) as the input, but for each coloumn in the selected there will be the optimal weight calculated by the algorithm to give the student the best grade. Any coloumn that was not selected to be optimized will be ignored by the algorithm and will be the same in the output file. This feature is used to perseve student names, student numbers, dates, etc. Empty coloumnns or columns whose value is "-" will be treated as a 0.

## How to run
### Running the backend
1) Open Eclipse 
2) Click on "Import Projects..."
3) Select "Existing Maven Projects..."
4) Select "LinearOptimizationApp/LinearOptimization" for the directory
5) Right click on "LinearOptimizationApp\LinearOptimization\src\main\java\api\Application.java" and click on "Run As..."

### Running the frontend
1) Open git bash or command line
2) Navigate to "LinearOptimizationApp\linear-op-web-app"
3) Type "npm install" (Make sure you have node installed)
4) Type "npm start" and the application will launch
5) You are ready to start
