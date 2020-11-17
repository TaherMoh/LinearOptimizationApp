var Assignment_1 >= 0;
var Assignment_2 >= 0;
var Project >= 0;
var Assignment_3 >= 0;
var Midterm >= 0;
var Assignment_4 >= 0;
var FinalExamCOMP >= 0;

maximize z:  Assignment_1*73 + Assignment_2*73.7 + Project*85 + Assignment_3*83.2 + Midterm*86.25 + Assignment_4*86.1 + FinalExamCOMP*50 ;

subject to c11:  Assignment_1 + Assignment_2 + Project + Assignment_3 + Midterm + Assignment_4 + FinalExamCOMP  = 1;
subject to c12: Assignment_1 >= 0.03;
subject to c13: Assignment_1 <= 0.1;
subject to c14: Assignment_2 >= 0.03;
subject to c15: Assignment_2 <= 0.1;
subject to c16: Project >= 0.1;
subject to c17: Project <= 0.25;
subject to c18: Assignment_3 >= 0.03;
subject to c19: Assignment_3 <= 0.1;
subject to c20: Midterm >= 0.1;
subject to c21: Midterm <= 0.4;
subject to c22: Assignment_4 >= 0.03;
subject to c23: Assignment_4 <= 0.1;
subject to c24: FinalExamCOMP >= 0.3;
subject to c25: FinalExamCOMP <= 0.7;

solve;

display Assignment_1;
display Assignment_2;
display Project;
display Assignment_3;
display Midterm;
display Assignment_4;
display FinalExamCOMP;

end;
