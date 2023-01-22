#include <stdio.h>
int Counter;
RecordInst(){Counter++;}
Init(){Counter = 0;}
Report(){printf("No. of instructions are %d\n", Counter);}
/* FileAst "automaton.adap" Begin */
void state_0() ;
void state_1() ;
void state_2() ;
void state_3() ;
int getnextdigit() ;

int getnextdigit() {
      int n;
      RecordInst();
      goto L3;
   L2:;
      RecordInst();
      printf("Give me a number (-1 to quit): ");
      RecordInst();
      scanf("%d", (&n));
      RecordInst();
      if ((- 1 > n)) goto L7;
      RecordInst();
      if ((1 < n)) goto L7;
      RecordInst();
      goto L4;
   L7:;
      RecordInst();
      printf("I need a number that's either 0 or 1.\n");
   L3:;
      RecordInst();
      goto L2;
   L4:;
      return n;
   L1:;
}
void state_0() ;

void state_0() {
      int redat1;
      int a;
      RecordInst();
      redat1 = getnextdigit();
      RecordInst();
      a = redat1;
      RecordInst();
      if ((a != - 1)) goto L11;
      RecordInst();
      printf("You gave me an even number of 0's.\n");
      RecordInst();
      printf("You gave me an even number of 1's.\n");
      RecordInst();
      printf("I therefore accept this input.\n");
      RecordInst();
      goto L10;
   L11:;
      RecordInst();
      if ((a != 0)) goto L16;
      RecordInst();
      state_2();
   L16:;
      RecordInst();
      if ((a != 1)) goto L18;
      RecordInst();
      state_1();
   L18:;
   L10:;
}
void state_1() ;

void state_1() {
      int redat1;
      int a;
      RecordInst();
      redat1 = getnextdigit();
      RecordInst();
      a = redat1;
      RecordInst();
      if ((a != - 1)) goto L21;
      RecordInst();
      printf("You gave me an even number of 0's.\n");
      RecordInst();
      printf("You gave me an odd number of 1's.\n");
      RecordInst();
      printf("I therefore reject this input.\n");
      RecordInst();
      goto L20;
   L21:;
      RecordInst();
      if ((a != 0)) goto L25;
      RecordInst();
      state_3();
   L25:;
      RecordInst();
      if ((a != 1)) goto L27;
      RecordInst();
      state_0();
   L27:;
   L20:;
}
void state_2() ;

void state_2() {
      int redat1;
      int a;
      RecordInst();
      redat1 = getnextdigit();
      RecordInst();
      a = redat1;
      RecordInst();
      if ((a != - 1)) goto L30;
      RecordInst();
      printf("You gave me an odd number of 0's.\n");
      RecordInst();
      printf("You gave me an even number of 1's.\n");
      RecordInst();
      printf("I therefore reject this input.\n");
      RecordInst();
      goto L29;
   L30:;
      RecordInst();
      if ((a != 0)) goto L33;
      RecordInst();
      state_0();
   L33:;
      RecordInst();
      if ((a != 1)) goto L35;
      RecordInst();
      state_3();
   L35:;
   L29:;
}
void state_3() ;

void state_3() {
      int redat1;
      int a;
      RecordInst();
      redat1 = getnextdigit();
      RecordInst();
      a = redat1;
      RecordInst();
      if ((a != - 1)) goto L38;
      RecordInst();
      printf("You gave me an odd number of 0's.\n");
      RecordInst();
      printf("You gave me an odd number of 1's.\n");
      RecordInst();
      printf("I therefore reject this input.\n");
      RecordInst();
      goto L37;
   L38:;
      RecordInst();
      if ((a != 0)) goto L40;
      RecordInst();
      state_1();
   L40:;
      RecordInst();
      if ((a != 1)) goto L42;
      RecordInst();
      state_2();
   L42:;
   L37:;
}
int main() ;

int main() {
      Init();
      RecordInst();
      state_0();
      Report();
      return 0;
   L44:;
      Report();
}
/* FileAst "automaton.adap" End */
