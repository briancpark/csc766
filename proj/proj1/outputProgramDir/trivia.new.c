/* ProgAst "trivia.adap" Begin */
	/* FileAst "trivia.adap" Begin */
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
int main(int p0, char** p1) ;

int main(int argc, char** argv) {
      init();
      {
      int c;
      int a;
      int s;
      int b;
      recordInst();
      printf("Please input an integer number: ");
      recordInst();
      scanf("%d", (&a));
      recordInst();
      b = 50;
      recordInst();
      c = 500;
      recordInst();
      s = 0;
      int temp_0;
      recordInst();
      temp_0 = (c / c);
      recordInst();
      s = (s / temp_0);
      int temp_1;
      recordInst();
      temp_1 = (c + c);
      recordInst();
      s = (s + temp_1);
      int temp_2;
      recordInst();
      temp_2 = (c / a);
      recordInst();
      s = (s / temp_2);
      recordInst();
      c = (b + c);
      int temp_3;
      recordInst();
      temp_3 = (b + a);
      recordInst();
      s = (s + temp_3);
      int temp_4;
      recordInst();
      temp_4 = (b + a);
      recordInst();
      s = (s + temp_4);
      int temp_5;
      recordInst();
      temp_5 = (b + c);
      recordInst();
      s = (s + temp_5);
      int temp_6;
      recordInst();
      temp_6 = (a + b);
      recordInst();
      s = (s + temp_6);
      int temp_7;
      recordInst();
      temp_7 = (a / a);
      recordInst();
      s = (s / temp_7);
      recordInst();
      c = (b / a);
      int temp_8;
      recordInst();
      temp_8 = (b / a);
      recordInst();
      s = (s / temp_8);
      int temp_9;
      recordInst();
      temp_9 = (c + b);
      recordInst();
      s = (s + temp_9);
      int temp_10;
      recordInst();
      temp_10 = (a + b);
      recordInst();
      s = (s + temp_10);
      int temp_11;
      recordInst();
      temp_11 = (c + a);
      recordInst();
      s = (s + temp_11);
      int temp_12;
      recordInst();
      temp_12 = (c + c);
      recordInst();
      s = (s + temp_12);
      int temp_13;
      recordInst();
      temp_13 = (c + b);
      recordInst();
      s = (s + temp_13);
      recordInst();
      c = (b + a);
      int temp_14;
      recordInst();
      temp_14 = (c / b);
      recordInst();
      s = (s / temp_14);
      recordInst();
      b = (c + a);
      int temp_15;
      recordInst();
      temp_15 = (b / a);
      recordInst();
      s = (s / temp_15);
      int temp_16;
      recordInst();
      temp_16 = (a + b);
      recordInst();
      s = (s + temp_16);
      int temp_17;
      recordInst();
      temp_17 = (c + b);
      recordInst();
      s = (s + temp_17);
      int temp_18;
      recordInst();
      temp_18 = (c + a);
      recordInst();
      s = (s + temp_18);
      recordInst();
      c = (a + a);
      int temp_19;
      recordInst();
      temp_19 = (a + c);
      recordInst();
      s = (s + temp_19);
      recordInst();
      c = (b + a);
      int temp_20;
      recordInst();
      temp_20 = (a + a);
      recordInst();
      s = (s + temp_20);
      recordInst();
      c = (b / a);
      recordInst();
      c = (b / c);
      recordInst();
      a = (a / c);
      int temp_21;
      recordInst();
      temp_21 = (b + a);
      recordInst();
      s = (s + temp_21);
      recordInst();
      printf("Final Result: s = %d\n", s);
      }
      report();
      return 0;
   L1:;
}
/* FileAst "trivia.adap" End */
/* ProgAst "trivia.adap" End */
