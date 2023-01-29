/* ProgAst "fibonacci.adap" Begin */
	/* FileAst "fibonacci.adap" Begin */
int counter;
void init() ;

void init() {
      counter = 0;
}
void recordInst() ;

void recordInst() {
      counter = (counter + 1);
}
void report() ;

void report() {
      printf("instruction %d", counter);
}
int array[32];
void initialize_array() ;

void initialize_array() {
      {
      int idx;
      int bound;
      recordInst();
      bound = 32;
      recordInst();
      idx = 0;
      recordInst();
      goto L3;
   L2:;
      {
      int temp_0;
      recordInst();
      temp_0 = (1 * idx);
      recordInst();
      array[temp_0]  = - 1;
      recordInst();
      idx = (idx + 1);
      }
   L3:;
      recordInst();
      if ((idx < bound)) goto L2;
      }
   L1:;
}
int fib(int p0) ;

int fib(int val) {
      {
      recordInst();
      val = val;
      recordInst();
      if ((val >= 2)) goto L6;
      
      return 1;
      recordInst();
      goto L5;
   L6:;
      int temp_1;
      recordInst();
      temp_1 = (1 * val);
      int temp_2;
      recordInst();
      temp_2 = array[temp_1] ;
      recordInst();
      if ((temp_2 != - 1)) goto L8;
      {
      int redat1;
      int redat2;
      int temp_3;
      recordInst();
      temp_3 = (val - 1);
      recordInst();
      redat1 = fib(temp_3);
      int temp_4;
      recordInst();
      temp_4 = (val - 2);
      recordInst();
      redat2 = fib(temp_4);
      int temp_5;
      recordInst();
      temp_5 = (1 * val);
      recordInst();
      array[temp_5]  = (redat1 + redat2);
      }
   L8:;
      int temp_6;
      recordInst();
      temp_6 = (1 * val);
      int temp_7;
      recordInst();
      temp_7 = array[temp_6] ;
      return temp_7;
      }
   L5:;
}
int main() ;

int main() {
      init();
      {
      int idx;
      int bound;
      recordInst();
      bound = 32;
      recordInst();
      initialize_array();
      recordInst();
      idx = 0;
      recordInst();
      printf("The first few digits of the Fibonacci sequence are:\n");
      recordInst();
      goto L13;
   L12:;
      {
      int redat1;
      recordInst();
      redat1 = fib(idx);
      recordInst();
      printf("%d\n", redat1);
      recordInst();
      idx = (idx + 1);
      }
   L13:;
      recordInst();
      if ((idx < bound)) goto L12;
      }
      report();
      return 0;
   L10:;
}
/* FileAst "fibonacci.adap" End */
/* ProgAst "fibonacci.adap" End */
