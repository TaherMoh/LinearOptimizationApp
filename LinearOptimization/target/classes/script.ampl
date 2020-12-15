var Assignment_Assignment_1_Real_ >= 0;
var Assignment_Assignment_2_Real_ >= 0;
var Assignment_Project_Real_ >= 0;
var Assignment_Assignment_3_Real_ >= 0;
var Midterm_Real_ >= 0;
var Assignment_Assignment_4_Real_ >= 0;

maximize z:  Assignment_Assignment_1_Real_*100 + Assignment_Assignment_2_Real_*100 + Assignment_Project_Real_*100 + Assignment_Assignment_3_Real_*88.6 + Midterm_Real_*77.5 + Assignment_Assignment_4_Real_*77.8 ;

subject to c11:  Assignment_Assignment_1_Real_ + Assignment_Assignment_2_Real_ + Assignment_Project_Real_ + Assignment_Assignment_3_Real_ + Midterm_Real_ + Assignment_Assignment_4_Real_  = 1;
subject to c12: Assignment_Assignment_1_Real_ >= 0.03;
subject to c13: Assignment_Assignment_1_Real_ <= 0.1;
subject to c14: Assignment_Assignment_2_Real_ >= 0.03;
subject to c15: Assignment_Assignment_2_Real_ <= 0.1;
subject to c16: Assignment_Project_Real_ >= 0.1;
subject to c17: Assignment_Project_Real_ <= 0.25;
subject to c18: Assignment_Assignment_3_Real_ >= 0.03;
subject to c19: Assignment_Assignment_3_Real_ <= 0.1;
subject to c20: Midterm_Real_ >= 0.1;
subject to c21: Midterm_Real_ <= 0.4;
subject to c22: Assignment_Assignment_4_Real_ >= 0.03;
subject to c23: Assignment_Assignment_4_Real_ <= 0.1;

solve;

display Assignment_Assignment_1_Real_;
display Assignment_Assignment_2_Real_;
display Assignment_Project_Real_;
display Assignment_Assignment_3_Real_;
display Midterm_Real_;
display Assignment_Assignment_4_Real_;

end;
