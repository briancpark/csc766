/* ProgAst "movingedge.adap" Begin */
	/* FileAst "movingedge.adap" Begin */
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
int nbr_5[5];
int magic= 35 ;
int V[100];
void init_nbr() ;

void init_nbr() {
      
      recordInst();
      nbr_5[0]  = - 3;
      recordInst();
      nbr_5[1]  = 12;
      recordInst();
      nbr_5[2]  = 17;
      recordInst();
      nbr_5[3]  = 12;
      recordInst();
      nbr_5[4]  = - 3;
   L1:;
}
int getinput() ;

int getinput() {
      {
      int a;
      recordInst();
      a = - 1;
      recordInst();
      goto L4;
   L3:;
      
      recordInst();
      scanf("%d", (&a));
      recordInst();
      if ((0 > a)) goto L9;
      recordInst();
      if ((a <= 100)) goto L7;
   L9:;
      
      recordInst();
      printf("I need a non-negative number less than 100: ");
      recordInst();
      a = - 1;
   L7:;
   L4:;
      recordInst();
      if ((0 > a)) goto L3;
      return a;
      }
   L2:;
}
void moving(int p0) ;

void moving(int size) {
      {
      int j;
      int i;
      recordInst();
      size = size;
      recordInst();
      i = 2;
      recordInst();
      goto L15;
   L12:;
      {
      int temp;
      recordInst();
      temp = 0;
      recordInst();
      j = - 2;
   L16:;
      {
      int temp_0;
      recordInst();
      temp_0 = (i + j);
      int temp_1;
      recordInst();
      temp_1 = (1 * temp_0);
      int temp_2;
      recordInst();
      temp_2 = V[temp_1] ;
      int temp_3;
      recordInst();
      temp_3 = (1 * j);
      int temp_4;
      recordInst();
      temp_4 = (temp_3 + 2);
      int temp_5;
      recordInst();
      temp_5 = nbr_5[temp_4] ;
      int temp_6;
      recordInst();
      temp_6 = (temp_2 * temp_5);
      recordInst();
      temp = (temp + temp_6);
      }
   L17:;
      recordInst();
      j = (j + 1);
      recordInst();
      if ((j < 3)) goto L16;
      int temp_7;
      recordInst();
      temp_7 = (1 * i);
      recordInst();
      V[temp_7]  = (temp / magic);
      }
   L13:;
      recordInst();
      i = (i + 1);
   L15:;
      int temp_8;
      recordInst();
      temp_8 = (size - 2);
      recordInst();
      if ((i < temp_8)) goto L12;
      }
   L11:;
}
int main() ;

int main() {
      init();
      {
      int i;
      int size;
      int redat1;
      recordInst();
      printf("Please input the size of the vector to be transformed: ");
      recordInst();
      redat1 = getinput();
      recordInst();
      size = redat1;
      recordInst();
      i = 0;
      recordInst();
      goto L25;
   L22:;
      {
      int redat2;
      recordInst();
      redat2 = rand();
      int temp_9;
      recordInst();
      temp_9 = (1 * i);
      recordInst();
      V[temp_9]  = (redat2 % 100);
      }
   L23:;
      recordInst();
      i = (i + 1);
   L25:;
      recordInst();
      if ((i < size)) goto L22;
      recordInst();
      printf("Original vector:\n");
      recordInst();
      i = 0;
      recordInst();
      goto L30;
   L27:;
      int temp_10;
      recordInst();
      temp_10 = (1 * i);
      int temp_11;
      recordInst();
      temp_11 = V[temp_10] ;
      recordInst();
      printf("%d\n", temp_11);
   L28:;
      recordInst();
      i = (i + 1);
   L30:;
      recordInst();
      if ((i < size)) goto L27;
      recordInst();
      printf("\n");
      recordInst();
      init_nbr();
      recordInst();
      moving(size);
      recordInst();
      printf("Moving edge vector:\n");
      recordInst();
      i = 0;
      recordInst();
      goto L37;
   L34:;
      int temp_12;
      recordInst();
      temp_12 = (1 * i);
      int temp_13;
      recordInst();
      temp_13 = V[temp_12] ;
      recordInst();
      printf("%d\n", temp_13);
   L35:;
      recordInst();
      i = (i + 1);
   L37:;
      recordInst();
      if ((i < size)) goto L34;
      recordInst();
      printf("\n");
      }
      report();
      return 0;
   L20:;
}
/* FileAst "movingedge.adap" End */
/* ProgAst "movingedge.adap" End */
