/* ProgAst "binrep.adap" Begin */
	/* FileAst "binrep.adap" Begin */
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
void recursedigit(int p0) ;

void recursedigit(int n) {
      {
      int on;
      recordInst();
      n = n;
      recordInst();
      if ((n != 0)) goto L2;
      
      recordInst();
      goto L1;
   L2:;
      recordInst();
      on = 0;
      int temp_0;
      recordInst();
      temp_0 = (n / 2);
      int temp_1;
      recordInst();
      temp_1 = (2 * temp_0);
      int temp_2;
      recordInst();
      temp_2 = (n - temp_1);
      recordInst();
      if ((temp_2 == 0)) goto L4;
      
      recordInst();
      on = 1;
   L4:;
      int temp_3;
      recordInst();
      temp_3 = (n / 2);
      recordInst();
      recursedigit(temp_3);
      recordInst();
      if ((on != 0)) goto L6;
      
      recordInst();
      printf("0");
   L6:;
      recordInst();
      if ((on != 1)) goto L9;
      
      recordInst();
      printf("1");
   L9:;
      }
   L1:;
}
int main() ;

int main() {
      init();
      {
      int a;
      recordInst();
      a = 0;
      recordInst();
      goto L14;
   L13:;
      
      recordInst();
      printf("Give me a number: ");
      recordInst();
      scanf("%d", (&a));
      recordInst();
      if ((0 < a)) goto L18;
      
      recordInst();
      printf("I need a positive integer.\n");
   L18:;
   L14:;
      recordInst();
      if ((0 >= a)) goto L13;
      recordInst();
      printf("The binary representation of: ");
      recordInst();
      printf("%d\n", a);
      recordInst();
      printf("is: ");
      recordInst();
      recursedigit(a);
      recordInst();
      printf("\n\n");
      }
      report();
      return 0;
   L12:;
}
/* FileAst "binrep.adap" End */
/* ProgAst "binrep.adap" End */
