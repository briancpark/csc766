#include <stdio.h>
int Counter;
RecordInst(){Counter++;}
Init(){Counter = 0;}
Report(){printf("No. of instructions are %d\n", Counter);}
/* FileAst "binrep.adap" Begin */
void recursedigit(int p0) ;

void recursedigit(int n) {
      int temp_3;
      int temp_0;
      int temp_1;
      int temp_2;
      int on;
      RecordInst();
      n = n;
      RecordInst();
      if ((n != 0)) goto L2;
      RecordInst();
      goto L1;
   L2:;
      RecordInst();
      on = 0;
      RecordInst();
      temp_2 = (n / 2);
      RecordInst();
      temp_1 = (2 * temp_2);
      RecordInst();
      temp_0 = (n - temp_1);
      RecordInst();
      if ((temp_0 == 0)) goto L4;
      RecordInst();
      on = 1;
   L4:;
      RecordInst();
      temp_3 = (n / 2);
      RecordInst();
      recursedigit(temp_3);
      RecordInst();
      if ((on != 0)) goto L6;
      RecordInst();
      printf("0");
   L6:;
      RecordInst();
      if ((on != 1)) goto L9;
      RecordInst();
      printf("1");
   L9:;
   L1:;
}
int main() ;

int main() {
      int a;
      Init();
      RecordInst();
      a = 0;
      RecordInst();
      goto L14;
   L13:;
      RecordInst();
      printf("Give me a number: ");
      RecordInst();
      scanf("%d", (&a));
      RecordInst();
      if ((0 < a)) goto L18;
      RecordInst();
      printf("I need a positive integer.\n");
   L18:;
   L14:;
      RecordInst();
      if ((0 >= a)) goto L13;
      RecordInst();
      printf("The binary representation of: ");
      RecordInst();
      printf("%d\n", a);
      RecordInst();
      printf("is: ");
      RecordInst();
      recursedigit(a);
      RecordInst();
      printf("\n\n");
      Report();
      return 0;
   L12:;
      Report();
}
/* FileAst "binrep.adap" End */
