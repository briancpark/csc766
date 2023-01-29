/* ProgAst "automaton.adap" Begin */
	/* FileAst "automaton.adap" Begin */
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
void state_0() ;
void state_1() ;
void state_2() ;
void state_3() ;
int getnextdigit() ;

int getnextdigit() {
      {
      int n;
      recordInst();
      goto L3;
   L2:;
      
      recordInst();
      printf("Give me a number (-1 to quit): ");
      recordInst();
      scanf("%d", (&n));
      recordInst();
      if ((- 1 > n)) goto L7;
      recordInst();
      if ((1 < n)) goto L7;
      
      recordInst();
      goto L4;
   L7:;
      recordInst();
      printf("I need a number that's either 0 or 1.\n");
   L3:;
      recordInst();
      goto L2;
   L4:;
      return n;
      }
   L1:;
}
void state_0() ;

void state_0() {
      {
      int a;
      int redat1;
      recordInst();
      redat1 = getnextdigit();
      recordInst();
      a = redat1;
      recordInst();
      if ((a != - 1)) goto L11;
      
      recordInst();
      printf("You gave me an even number of 0's.\n");
      recordInst();
      printf("You gave me an even number of 1's.\n");
      recordInst();
      printf("I therefore accept this input.\n");
      recordInst();
      goto L10;
   L11:;
      recordInst();
      if ((a != 0)) goto L16;
      
      recordInst();
      state_2();
   L16:;
      recordInst();
      if ((a != 1)) goto L18;
      
      recordInst();
      state_1();
   L18:;
      }
   L10:;
}
void state_1() ;

void state_1() {
      {
      int a;
      int redat1;
      recordInst();
      redat1 = getnextdigit();
      recordInst();
      a = redat1;
      recordInst();
      if ((a != - 1)) goto L21;
      
      recordInst();
      printf("You gave me an even number of 0's.\n");
      recordInst();
      printf("You gave me an odd number of 1's.\n");
      recordInst();
      printf("I therefore reject this input.\n");
      recordInst();
      goto L20;
   L21:;
      recordInst();
      if ((a != 0)) goto L25;
      
      recordInst();
      state_3();
   L25:;
      recordInst();
      if ((a != 1)) goto L27;
      
      recordInst();
      state_0();
   L27:;
      }
   L20:;
}
void state_2() ;

void state_2() {
      {
      int a;
      int redat1;
      recordInst();
      redat1 = getnextdigit();
      recordInst();
      a = redat1;
      recordInst();
      if ((a != - 1)) goto L30;
      
      recordInst();
      printf("You gave me an odd number of 0's.\n");
      recordInst();
      printf("You gave me an even number of 1's.\n");
      recordInst();
      printf("I therefore reject this input.\n");
      recordInst();
      goto L29;
   L30:;
      recordInst();
      if ((a != 0)) goto L33;
      
      recordInst();
      state_0();
   L33:;
      recordInst();
      if ((a != 1)) goto L35;
      
      recordInst();
      state_3();
   L35:;
      }
   L29:;
}
void state_3() ;

void state_3() {
      {
      int a;
      int redat1;
      recordInst();
      redat1 = getnextdigit();
      recordInst();
      a = redat1;
      recordInst();
      if ((a != - 1)) goto L38;
      
      recordInst();
      printf("You gave me an odd number of 0's.\n");
      recordInst();
      printf("You gave me an odd number of 1's.\n");
      recordInst();
      printf("I therefore reject this input.\n");
      recordInst();
      goto L37;
   L38:;
      recordInst();
      if ((a != 0)) goto L40;
      
      recordInst();
      state_1();
   L40:;
      recordInst();
      if ((a != 1)) goto L42;
      
      recordInst();
      state_2();
   L42:;
      }
   L37:;
}
int main() ;

int main() {
      init();
      
      recordInst();
      state_0();
      report();
      return 0;
   L44:;
}
/* FileAst "automaton.adap" End */
/* ProgAst "automaton.adap" End */
