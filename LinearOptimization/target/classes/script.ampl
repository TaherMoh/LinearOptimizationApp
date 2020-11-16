var Assignment_Assignment_1(Real) >= 0;
var Assignment_Assignment_2(Real) >= 0;
var Assignment_Project(Real) >= 0;
var Assignment_Assignment_3(Real) >= 0;
var Midterm(Real) >= 0;
var Assignment_Assignment_4(Real) >= 0;
var Externaltool_FinalExamCOMP(Real) >= 0;

maximize z:  Assignment_Assignment_1(Real)*73 + Assignment_Assignment_2(Real)*73.7 + Assignment_Project(Real)*85 + Assignment_Assignment_3(Real)*83.2 + Midterm(Real)*86.25 + Assignment_Assignment_4(Real)*86.1 + Externaltool_FinalExamCOMP(Real)*40 ;

subject to c11:  Assignment_Assignment_1(Real) + Assignment_Assignment_2(Real) + Assignment_Project(Real) + Assignment_Assignment_3(Real) + Midterm(Real) + Assignment_Assignment_4(Real) + Externaltool_FinalExamCOMP(Real)  = 1;
subject to c12: Assignment_Assignment_1(Real) <= 0.14;
subject to c13: Assignment_Assignment_2(Real) <= 0.12;
subject to c14: Assignment_Project(Real) <= 0.05;
subject to c15: Midterm(Real) <= 0.15;
subject to c16: Assignment_Assignment_4(Real) <= 0.35;
subject to c17: Externaltool_FinalExamCOMP(Real) <= 0.25;

solve;

display Assignment_Assignment_1(Real);

end;