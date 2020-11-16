var x1_Taher >= 0;
var x2 >= 0;
var x3 >= 0;
var x4) >= 0;

maximize z:     0*x1_Taher + 0.71*x2 + 1*x3 + 1*x4);

subject to c11: x1_Taher + x2 + x3 + x4) = 1;
subject to c12: x1_Taher >= 0.1;
subject to c13: x1_Taher <= 0.15;
subject to c14: x2 >= 0.15;
subject to c15: x2 <= 0.35;
subject to c16: x3 <= 0.25;
subject to c17: x3 >= 0.15;
subject to c18: x4) >=  0.4;
subject to c19: x4) <=  0.6;

solve;

display x1_Taher;
display x2;
display x3;
display x4);

end;