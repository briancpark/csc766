#include <stdio.h>
int Counter;
RecordInst(){Counter++;}
Init(){Counter = 0;}
Report(){printf("No. of instructions are %d\n", Counter);}
/* FileAst "sort_insertion.adap" Begin */
int data[30];
void insertSort(int p0, int p1) ;

void insertSort(int lb, int ub) {
      int temp_6;
      int temp_7;
      int temp_4;
      int temp_5;
      int temp_3;
      int temp_1;
      int temp_2;
      int temp_0;
      int i;
      int t;
      int j;
      RecordInst();
      lb = lb;
      RecordInst();
      ub = ub;
      RecordInst();
      i = (lb + 1);
      RecordInst();
      goto L5;
   L2:;
      RecordInst();
      temp_0 = (1 * i);
      RecordInst();
      t = data[temp_0] ;
      RecordInst();
      j = (i - 1);
      RecordInst();
      goto L9;
   L6:;
      RecordInst();
      temp_2 = (1 * j);
      RecordInst();
      temp_1 = (temp_2 + 1);
      RecordInst();
      temp_3 = (1 * j);
      RecordInst();
      data[temp_1]  = data[temp_3] ;
   L7:;
      RecordInst();
      j = (j - 1);
   L9:;
      RecordInst();
      if ((j < lb)) goto L10;
      RecordInst();
      temp_5 = (1 * j);
      RecordInst();
      temp_4 = data[temp_5] ;
      RecordInst();
      if ((temp_4 > t)) goto L6;
   L10:;
      RecordInst();
      temp_7 = (1 * j);
      RecordInst();
      temp_6 = (temp_7 + 1);
      RecordInst();
      data[temp_6]  = t;
   L3:;
      RecordInst();
      i = (i + 1);
   L5:;
      RecordInst();
      if ((i <= ub)) goto L2;
   L1:;
}
void fill(int p0, int p1) ;

void fill(int lb, int ub) {
      int temp_8;
      int redat1;
      int i;
      RecordInst();
      ub = ub;
      RecordInst();
      srand(1);
      RecordInst();
      i = lb;
      RecordInst();
      goto L15;
   L12:;
      RecordInst();
      redat1 = rand();
      RecordInst();
      temp_8 = (1 * i);
      RecordInst();
      data[temp_8]  = (redat1 % 1000);
   L13:;
      RecordInst();
      i = (i + 1);
   L15:;
      RecordInst();
      if ((i <= ub)) goto L12;
   L11:;
}
int main(int p0, char** p1) ;

int main(int argc, char** argv) {
      int temp_9;
      int temp_10;
      int maxnum;
      int lb;
      int ub;
      int i;
      Init();
      RecordInst();
      maxnum = 30;
      RecordInst();
      lb = 0;
      RecordInst();
      ub = (maxnum - 1);
      RecordInst();
      fill(lb, ub);
      RecordInst();
      insertSort(lb, ub);
      RecordInst();
      printf("results:\n");
      RecordInst();
      i = lb;
      RecordInst();
      goto L21;
   L18:;
      RecordInst();
      temp_10 = (1 * i);
      RecordInst();
      temp_9 = data[temp_10] ;
      RecordInst();
      printf("%d: %d\n", i, temp_9);
   L19:;
      RecordInst();
      i = (i + 1);
   L21:;
      RecordInst();
      if ((i <= ub)) goto L18;
      Report();
      return 0;
   L16:;
      Report();
}
/* FileAst "sort_insertion.adap" End */
