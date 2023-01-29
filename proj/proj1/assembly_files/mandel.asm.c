#include <stdio.h>
int Counter;
RecordInst(){Counter++;}
Init(){Counter = 0;}
Report(){printf("No. of instructions are %d\n", Counter);}
/* FileAst "mandel.adap" Begin */
int square(int p0) ;

int square(int x) {
      int temp_0;
      int temp_1;
      int temp_2;
      RecordInst();
      temp_2 = (x * x);
      RecordInst();
      temp_1 = (temp_2 + 500);
      RecordInst();
      temp_0 = (temp_1 / 1000);
      return temp_0;
   L1:;
}
int complex_abs_squared(int p0, int p1) ;

int complex_abs_squared(int real, int imag) {
      int temp_3;
      int redat2;
      int redat1;
      RecordInst();
      redat1 = square(real);
      RecordInst();
      redat2 = square(imag);
      RecordInst();
      temp_3 = (redat1 + redat2);
      return temp_3;
   L2:;
}
int check_for_bail(int p0, int p1) ;

int check_for_bail(int real, int imag) {
      int redat1;
      RecordInst();
      if ((real > 4000)) goto L6;
      RecordInst();
      if ((imag <= 4000)) goto L4;
   L6:;
      return 0;
      RecordInst();
      goto L3;
   L4:;
      RecordInst();
      redat1 = complex_abs_squared(real, imag);
      RecordInst();
      if ((1600 <= redat1)) goto L7;
      return 0;
      RecordInst();
      goto L3;
   L7:;
      return 1;
   L3:;
}
int absval(int p0) ;

int absval(int x) {
      int temp_4;
      RecordInst();
      if ((x >= 0)) goto L10;
      RecordInst();
      temp_4 = (x * - 1);
      return temp_4;
      RecordInst();
      goto L9;
   L10:;
      return x;
   L9:;
}
int checkpixel(int p0, int p1) ;

int checkpixel(int x, int y) {
      int redat4;
      int redat3;
      int redat2;
      int redat1;
      int temp_10;
      int temp_6;
      int temp_7;
      int temp_8;
      int temp_9;
      int temp_5;
      int bail;
      int temp;
      int iter;
      int imag;
      int real;
      RecordInst();
      x = x;
      RecordInst();
      y = y;
      RecordInst();
      real = 0;
      RecordInst();
      imag = 0;
      RecordInst();
      iter = 0;
      RecordInst();
      bail = 16000;
      RecordInst();
      goto L14;
   L13:;
      RecordInst();
      redat1 = square(real);
      RecordInst();
      redat2 = square(imag);
      RecordInst();
      temp_5 = (redat1 - redat2);
      RecordInst();
      temp = (temp_5 + x);
      RecordInst();
      temp_9 = (2 * real);
      RecordInst();
      temp_8 = (temp_9 * imag);
      RecordInst();
      temp_7 = (temp_8 + 500);
      RecordInst();
      temp_6 = (temp_7 / 1000);
      RecordInst();
      imag = (temp_6 + y);
      RecordInst();
      real = temp;
      RecordInst();
      redat3 = absval(real);
      RecordInst();
      redat4 = absval(imag);
      RecordInst();
      temp_10 = (redat3 + redat4);
      RecordInst();
      if ((temp_10 <= 5000)) goto L16;
      return 0;
      RecordInst();
      goto L12;
   L16:;
      RecordInst();
      iter = (iter + 1);
   L14:;
      RecordInst();
      if ((iter < 255)) goto L13;
      return 1;
   L12:;
}
int main() ;

int main() {
      int redat1;
      int y;
      int on;
      int x;
      Init();
      RecordInst();
      y = 950;
      RecordInst();
      goto L20;
   L19:;
      RecordInst();
      x = - 2100;
      RecordInst();
      goto L23;
   L22:;
      RecordInst();
      redat1 = checkpixel(x, y);
      RecordInst();
      on = redat1;
      RecordInst();
      if ((on != 1)) goto L25;
      RecordInst();
      printf("X");
   L25:;
      RecordInst();
      if ((on != 0)) goto L28;
      RecordInst();
      printf(" ");
   L28:;
      RecordInst();
      x = (x + 40);
   L23:;
      RecordInst();
      if ((x < 1000)) goto L22;
      RecordInst();
      printf("\n");
      RecordInst();
      y = (y - 50);
   L20:;
      RecordInst();
      if ((y > - 950)) goto L19;
      Report();
      return 0;
   L18:;
      Report();
}
/* FileAst "mandel.adap" End */
