/* ProgAst "sort_insertion.adap" Begin */
	/* FileAst "sort_insertion.adap" Begin */
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
int data[30];
void insertSort(int p0, int p1) ;

void insertSort(int lb, int ub) {
      {
      int j;
      int t;
      int i;
      recordInst();
      lb = lb;
      recordInst();
      ub = ub;
      recordInst();
      i = (lb + 1);
      recordInst();
      goto L5;
   L2:;
      {
      int temp_0;
      recordInst();
      temp_0 = (1 * i);
      recordInst();
      t = data[temp_0] ;
      recordInst();
      j = (i - 1);
      recordInst();
      goto L9;
   L6:;
      int temp_1;
      recordInst();
      temp_1 = (1 * j);
      int temp_2;
      recordInst();
      temp_2 = (temp_1 + 1);
      int temp_3;
      recordInst();
      temp_3 = (1 * j);
      recordInst();
      data[temp_2]  = data[temp_3] ;
   L7:;
      recordInst();
      j = (j - 1);
   L9:;
      recordInst();
      if ((j < lb)) goto L10;
      int temp_4;
      recordInst();
      temp_4 = (1 * j);
      int temp_5;
      recordInst();
      temp_5 = data[temp_4] ;
      recordInst();
      if ((temp_5 > t)) goto L6;
   L10:;
      int temp_6;
      recordInst();
      temp_6 = (1 * j);
      int temp_7;
      recordInst();
      temp_7 = (temp_6 + 1);
      recordInst();
      data[temp_7]  = t;
      }
   L3:;
      recordInst();
      i = (i + 1);
   L5:;
      recordInst();
      if ((i <= ub)) goto L2;
      }
   L1:;
}
void fill(int p0, int p1) ;

void fill(int lb, int ub) {
      {
      int i;
      int redat1;
      recordInst();
      ub = ub;
      recordInst();
      srand(1);
      recordInst();
      i = lb;
      recordInst();
      goto L15;
   L12:;
      recordInst();
      redat1 = rand();
      int temp_8;
      recordInst();
      temp_8 = (1 * i);
      recordInst();
      data[temp_8]  = (redat1 % 1000);
   L13:;
      recordInst();
      i = (i + 1);
   L15:;
      recordInst();
      if ((i <= ub)) goto L12;
      }
   L11:;
}
int main(int p0, char** p1) ;

int main(int argc, char** argv) {
      init();
      {
      int i;
      int ub;
      int lb;
      int maxnum;
      recordInst();
      maxnum = 30;
      recordInst();
      lb = 0;
      recordInst();
      ub = (maxnum - 1);
      recordInst();
      fill(lb, ub);
      recordInst();
      insertSort(lb, ub);
      recordInst();
      printf("results:\n");
      recordInst();
      i = lb;
      recordInst();
      goto L21;
   L18:;
      int temp_9;
      recordInst();
      temp_9 = (1 * i);
      int temp_10;
      recordInst();
      temp_10 = data[temp_9] ;
      recordInst();
      printf("%d: %d\n", i, temp_10);
   L19:;
      recordInst();
      i = (i + 1);
   L21:;
      recordInst();
      if ((i <= ub)) goto L18;
      report();
      return 0;
      }
   L16:;
}
/* FileAst "sort_insertion.adap" End */
/* ProgAst "sort_insertion.adap" End */
