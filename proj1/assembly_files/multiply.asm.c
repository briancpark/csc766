#include <stdio.h>
int Counter;
RecordInst(){Counter++;}
Init(){Counter = 0;}
Report(){printf("No. of instructions are %d\n", Counter);}
/* FileAst "multiply.adap" Begin */
int getinput() ;

int getinput() {
      int a;
      RecordInst();
      a = - 1;
      RecordInst();
      goto L3;
   L2:;
      RecordInst();
      scanf("%d", (&a));
      RecordInst();
      if ((0 > a)) goto L8;
      RecordInst();
      if ((a <= 100)) goto L6;
   L8:;
      RecordInst();
      printf("I need a non-negative number less than 100: ");
      RecordInst();
      a = - 1;
   L6:;
   L3:;
      RecordInst();
      if ((0 > a)) goto L2;
      return a;
   L1:;
}
int main() ;

int main() {
      int temp_9;
      int temp_12;
      int temp_13;
      int temp_10;
      int temp_11;
      int temp_6;
      int temp_7;
      int temp_8;
      int temp_5;
      int temp_4;
      int temp_1;
      int temp_2;
      int temp_3;
      int temp_0;
      int redat1;
      int result;
      int size;
      int B[100];
      int A[100];
      int i;
      Init();
      RecordInst();
      printf("Please give the size of the vectors to be multiplied: ");
      RecordInst();
      redat1 = getinput();
      RecordInst();
      size = redat1;
      RecordInst();
      A[0]  = 0;
      RecordInst();
      i = 1;
      RecordInst();
      goto L15;
   L12:;
      RecordInst();
      temp_0 = (1 * i);
      RecordInst();
      temp_3 = (1 * i);
      RecordInst();
      temp_2 = (temp_3 - 1);
      RecordInst();
      temp_1 = A[temp_2] ;
      RecordInst();
      temp_4 = (i * i);
      RecordInst();
      A[temp_0]  = (temp_1 + temp_4);
   L13:;
      RecordInst();
      i = (i + 1);
   L15:;
      RecordInst();
      if ((i < size)) goto L12;
      RecordInst();
      B[0]  = 0;
      RecordInst();
      i = 1;
      RecordInst();
      goto L19;
   L16:;
      RecordInst();
      temp_5 = (1 * i);
      RecordInst();
      temp_8 = (1 * i);
      RecordInst();
      temp_7 = (temp_8 - 1);
      RecordInst();
      temp_6 = B[temp_7] ;
      RecordInst();
      B[temp_5]  = (i + temp_6);
   L17:;
      RecordInst();
      i = (i + 1);
   L19:;
      RecordInst();
      if ((i < size)) goto L16;
      RecordInst();
      result = 0;
      RecordInst();
      i = 0;
      RecordInst();
      goto L23;
   L20:;
      RecordInst();
      temp_11 = (1 * i);
      RecordInst();
      temp_10 = A[temp_11] ;
      RecordInst();
      temp_13 = (1 * i);
      RecordInst();
      temp_12 = B[temp_13] ;
      RecordInst();
      temp_9 = (temp_10 * temp_12);
      RecordInst();
      result = (result + temp_9);
   L21:;
      RecordInst();
      i = (i + 1);
   L23:;
      RecordInst();
      if ((i < size)) goto L20;
      RecordInst();
      printf("0\n");
      Report();
      return 0;
   L10:;
      Report();
}
/* FileAst "multiply.adap" End */
