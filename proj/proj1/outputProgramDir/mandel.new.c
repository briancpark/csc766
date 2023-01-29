/* ProgAst "mandel.adap" Begin */
	/* FileAst "mandel.adap" Begin */
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
int square(int p0) ;

int square(int x) {
      {
      int temp_0;
      recordInst();
      temp_0 = (x * x);
      int temp_1;
      recordInst();
      temp_1 = (temp_0 + 500);
      int temp_2;
      recordInst();
      temp_2 = (temp_1 / 1000);
      return temp_2;
      }
   L1:;
}
int complex_abs_squared(int p0, int p1) ;

int complex_abs_squared(int real, int imag) {
      {
      int redat1;
      int redat2;
      recordInst();
      redat1 = square(real);
      recordInst();
      redat2 = square(imag);
      int temp_3;
      recordInst();
      temp_3 = (redat1 + redat2);
      return temp_3;
      }
   L2:;
}
int check_for_bail(int p0, int p1) ;

int check_for_bail(int real, int imag) {
      {
      int redat1;
      recordInst();
      if ((real > 4000)) goto L6;
      recordInst();
      if ((imag <= 4000)) goto L4;
   L6:;
      
      return 0;
      recordInst();
      goto L3;
   L4:;
      recordInst();
      redat1 = complex_abs_squared(real, imag);
      recordInst();
      if ((1600 <= redat1)) goto L7;
      
      return 0;
      recordInst();
      goto L3;
   L7:;
      return 1;
      }
   L3:;
}
int absval(int p0) ;

int absval(int x) {
      
      recordInst();
      if ((x >= 0)) goto L10;
      {
      int temp_4;
      recordInst();
      temp_4 = (x * - 1);
      return temp_4;
      recordInst();
      goto L9;
      }
   L10:;
      return x;
   L9:;
}
int checkpixel(int p0, int p1) ;

int checkpixel(int x, int y) {
      {
      int real;
      int imag;
      int iter;
      int temp;
      int bail;
      recordInst();
      x = x;
      recordInst();
      y = y;
      recordInst();
      real = 0;
      recordInst();
      imag = 0;
      recordInst();
      iter = 0;
      recordInst();
      bail = 16000;
      recordInst();
      goto L14;
   L13:;
      {
      int redat1;
      int redat2;
      int redat3;
      int redat4;
      recordInst();
      redat1 = square(real);
      recordInst();
      redat2 = square(imag);
      int temp_5;
      recordInst();
      temp_5 = (redat1 - redat2);
      recordInst();
      temp = (temp_5 + x);
      int temp_6;
      recordInst();
      temp_6 = (2 * real);
      int temp_7;
      recordInst();
      temp_7 = (temp_6 * imag);
      int temp_8;
      recordInst();
      temp_8 = (temp_7 + 500);
      int temp_9;
      recordInst();
      temp_9 = (temp_8 / 1000);
      recordInst();
      imag = (temp_9 + y);
      recordInst();
      real = temp;
      recordInst();
      redat3 = absval(real);
      recordInst();
      redat4 = absval(imag);
      int temp_10;
      recordInst();
      temp_10 = (redat3 + redat4);
      recordInst();
      if ((temp_10 <= 5000)) goto L16;
      
      return 0;
      recordInst();
      goto L12;
   L16:;
      recordInst();
      iter = (iter + 1);
      }
   L14:;
      recordInst();
      if ((iter < 255)) goto L13;
      return 1;
      }
   L12:;
}
int main() ;

int main() {
      init();
      {
      int x;
      int on;
      int y;
      recordInst();
      y = 950;
      recordInst();
      goto L20;
   L19:;
      
      recordInst();
      x = - 2100;
      recordInst();
      goto L23;
   L22:;
      {
      int redat1;
      recordInst();
      redat1 = checkpixel(x, y);
      recordInst();
      on = redat1;
      recordInst();
      if ((on != 1)) goto L25;
      
      recordInst();
      printf("X");
   L25:;
      recordInst();
      if ((on != 0)) goto L28;
      
      recordInst();
      printf(" ");
   L28:;
      recordInst();
      x = (x + 40);
      }
   L23:;
      recordInst();
      if ((x < 1000)) goto L22;
      recordInst();
      printf("\n");
      recordInst();
      y = (y - 50);
   L20:;
      recordInst();
      if ((y > - 950)) goto L19;
      }
      report();
      return 0;
   L18:;
}
/* FileAst "mandel.adap" End */
/* ProgAst "mandel.adap" End */
