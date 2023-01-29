/* ProgAst "multiply.adap" Begin */
	/* FileAst "multiply.adap" Begin */
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
int getinput() ;

int getinput() {
      {
      int a;
      recordInst();
      a = - 1;
      recordInst();
      goto L3;
   L2:;
      
      recordInst();
      scanf("%d", (&a));
      recordInst();
      if ((0 > a)) goto L8;
      recordInst();
      if ((a <= 100)) goto L6;
   L8:;
      
      recordInst();
      printf("I need a non-negative number less than 100: ");
      recordInst();
      a = - 1;
   L6:;
   L3:;
      recordInst();
      if ((0 > a)) goto L2;
      return a;
      }
   L1:;
}
int main() ;

int main() {
      init();
      {
      int i;
      int A[100];
      int B[100];
      int size;
      int result;
      int redat1;
      recordInst();
      printf("Please give the size of the vectors to be multiplied: ");
      recordInst();
      redat1 = getinput();
      recordInst();
      size = redat1;
      recordInst();
      A[0]  = 0;
      recordInst();
      i = 1;
      recordInst();
      goto L15;
   L12:;
      int temp_0;
      recordInst();
      temp_0 = (1 * i);
      int temp_1;
      recordInst();
      temp_1 = (temp_0 - 1);
      int temp_2;
      recordInst();
      temp_2 = A[temp_1] ;
      int temp_3;
      recordInst();
      temp_3 = (i * i);
      int temp_4;
      recordInst();
      temp_4 = (1 * i);
      recordInst();
      A[temp_4]  = (temp_2 + temp_3);
   L13:;
      recordInst();
      i = (i + 1);
   L15:;
      recordInst();
      if ((i < size)) goto L12;
      recordInst();
      B[0]  = 0;
      recordInst();
      i = 1;
      recordInst();
      goto L19;
   L16:;
      int temp_5;
      recordInst();
      temp_5 = (1 * i);
      int temp_6;
      recordInst();
      temp_6 = (temp_5 - 1);
      int temp_7;
      recordInst();
      temp_7 = B[temp_6] ;
      int temp_8;
      recordInst();
      temp_8 = (1 * i);
      recordInst();
      B[temp_8]  = (i + temp_7);
   L17:;
      recordInst();
      i = (i + 1);
   L19:;
      recordInst();
      if ((i < size)) goto L16;
      recordInst();
      result = 0;
      recordInst();
      i = 0;
      recordInst();
      goto L23;
   L20:;
      int temp_9;
      recordInst();
      temp_9 = (1 * i);
      int temp_10;
      recordInst();
      temp_10 = A[temp_9] ;
      int temp_11;
      recordInst();
      temp_11 = (1 * i);
      int temp_12;
      recordInst();
      temp_12 = B[temp_11] ;
      int temp_13;
      recordInst();
      temp_13 = (temp_10 * temp_12);
      recordInst();
      result = (result + temp_13);
   L21:;
      recordInst();
      i = (i + 1);
   L23:;
      recordInst();
      if ((i < size)) goto L20;
      recordInst();
      printf("0\n");
      }
      report();
      return 0;
   L10:;
}
/* FileAst "multiply.adap" End */
/* ProgAst "multiply.adap" End */
