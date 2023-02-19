#include <stdio.h>
int Counter;
RecordInst(){Counter++;}
Init(){Counter = 0;}
Report(){printf("No. of instructions are %d\n", Counter);}
/* FileAst "fibonacci.adap" Begin */
int array[32];
void initialize_array() ;

void initialize_array() {
      int temp_0;
      int bound;
      int idx;
      RecordInst();
      bound = 32;
      RecordInst();
      idx = 0;
      RecordInst();
      goto L3;
   L2:;
      RecordInst();
      temp_0 = (1 * idx);
      RecordInst();
      array[temp_0]  = - 1;
      RecordInst();
      idx = (idx + 1);
   L3:;
      RecordInst();
      if ((idx < bound)) goto L2;
   L1:;
}
int fib(int p0) ;

int fib(int val) {
      int redat2;
      int redat1;
      int temp_6;
      int temp_7;
      int temp_5;
      int temp_4;
      int temp_3;
      int temp_1;
      int temp_2;
      RecordInst();
      val = val;
      RecordInst();
      if ((val >= 2)) goto L6;
      return 1;
      RecordInst();
      goto L5;
   L6:;
      RecordInst();
      temp_2 = (1 * val);
      RecordInst();
      temp_1 = array[temp_2] ;
      RecordInst();
      if ((temp_1 != - 1)) goto L8;
      RecordInst();
      temp_3 = (val - 1);
      RecordInst();
      redat1 = fib(temp_3);
      RecordInst();
      temp_4 = (val - 2);
      RecordInst();
      redat2 = fib(temp_4);
      RecordInst();
      temp_5 = (1 * val);
      RecordInst();
      array[temp_5]  = (redat1 + redat2);
   L8:;
      RecordInst();
      temp_7 = (1 * val);
      RecordInst();
      temp_6 = array[temp_7] ;
      return temp_6;
   L5:;
}
int main() ;

int main() {
      int redat1;
      int bound;
      int idx;
      Init();
      RecordInst();
      bound = 32;
      RecordInst();
      initialize_array();
      RecordInst();
      idx = 0;
      RecordInst();
      printf("The first few digits of the Fibonacci sequence are:\n");
      RecordInst();
      goto L13;
   L12:;
      RecordInst();
      redat1 = fib(idx);
      RecordInst();
      printf("%d\n", redat1);
      RecordInst();
      idx = (idx + 1);
   L13:;
      RecordInst();
      if ((idx < bound)) goto L12;
      Report();
      return 0;
   L10:;
      Report();
}
/* FileAst "fibonacci.adap" End */
