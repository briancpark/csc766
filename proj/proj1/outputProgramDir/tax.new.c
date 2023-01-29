/* ProgAst "tax.adap" Begin */
	/* FileAst "tax.adap" Begin */
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
int getinput() ;

int getinput() {
      {
      int inp;
      recordInst();
      inp = - 1;
      recordInst();
      goto L3;
   L2:;
      
      recordInst();
      scanf("%d", (&inp));
      recordInst();
      if ((0 <= inp)) goto L6;
      
      recordInst();
      printf("I need a non-negative number: ");
   L6:;
   L3:;
      recordInst();
      if ((0 > inp)) goto L2;
      return inp;
      }
   L1:;
}
int main() ;

int main() {
      init();
      {
      int line10;
      int line8;
      int line7;
      int line6;
      int deadline12;
      int e;
      int d;
      int line4;
      int deadline11;
      int dependant;
      int single;
      int b;
      int line1;
      int line2;
      int line3;
      int line5;
      int eic;
      int c;
      int f;
      int g;
      int spousedependant;
      int redat1;
      int redat2;
      int redat3;
      int redat4;
      int redat8;
      int redat9;
      recordInst();
      printf("Welcome to the United States 1040 federal income tax program.\n");
      recordInst();
      printf("(Note: this isn't the real 1040 form. If you try to submit your\n");
      recordInst();
      printf("taxes this way, you'll get what you deserve!\n\n");
      recordInst();
      printf("Answer the following questions to determine what you owe.\n\n");
      recordInst();
      printf("Total wages, salary, and tips? ");
      recordInst();
      redat1 = getinput();
      recordInst();
      line1 = redat1;
      recordInst();
      printf("Taxable interest (such as from bank accounts)? ");
      recordInst();
      redat2 = getinput();
      recordInst();
      line2 = redat2;
      recordInst();
      printf("Unemployment compensation, qualified state tuition, and Alaska\n");
      recordInst();
      printf("Permanent Fund dividends? ");
      recordInst();
      redat3 = getinput();
      recordInst();
      line3 = redat3;
      int temp_0;
      recordInst();
      temp_0 = (line1 + line2);
      recordInst();
      line4 = (temp_0 + line3);
      recordInst();
      printf("Your adjusted gross income is: ");
      recordInst();
      printf("%d\n", line4);
      recordInst();
      printf("Enter <1> if your parents or someone else can claim you on their");
      recordInst();
      printf(" return. \nEnter <0> otherwise: ");
      recordInst();
      redat4 = getinput();
      recordInst();
      dependant = redat4;
      recordInst();
      if ((dependant == 0)) goto L22;
      {
      int redat5;
      recordInst();
      b = 700;
      recordInst();
      c = b;
      int temp_1;
      recordInst();
      temp_1 = (line1 + 250);
      recordInst();
      if ((c >= temp_1)) goto L24;
      
      recordInst();
      c = (line1 + 250);
   L24:;
      recordInst();
      printf("Enter <1> if you are single, <0> if you are married: ");
      recordInst();
      redat5 = getinput();
      recordInst();
      single = redat5;
      recordInst();
      if ((single == 0)) goto L27;
      
      recordInst();
      d = 7350;
      recordInst();
      goto L28;
   L27:;
      
      recordInst();
      d = 7350;
   L28:;
      recordInst();
      e = c;
      recordInst();
      if ((e <= d)) goto L29;
      
      recordInst();
      e = d;
   L29:;
      recordInst();
      f = 0;
      recordInst();
      if ((single != 0)) goto L31;
      {
      int redat6;
      recordInst();
      printf("Enter <1> if your spouse can be claimed as a dependant, ");
      recordInst();
      printf("enter <0> if not: ");
      recordInst();
      redat6 = getinput();
      recordInst();
      spousedependant = redat6;
      recordInst();
      if ((spousedependant != 0)) goto L35;
      
      recordInst();
      f = 2800;
   L35:;
      }
   L31:;
      recordInst();
      g = (e + f);
      recordInst();
      line5 = g;
      }
   L22:;
      recordInst();
      if ((dependant != 0)) goto L37;
      {
      int redat7;
      recordInst();
      printf("Enter <1> if you are single, <0> if you are married: ");
      recordInst();
      redat7 = getinput();
      recordInst();
      single = redat7;
      recordInst();
      if ((single == 0)) goto L39;
      
      recordInst();
      line5 = 12950;
   L39:;
      recordInst();
      if ((single != 0)) goto L41;
      
      recordInst();
      line5 = 7200;
   L41:;
      }
   L37:;
      recordInst();
      line6 = (line4 - line5);
      recordInst();
      if ((line6 >= 0)) goto L43;
      
      recordInst();
      line6 = 0;
   L43:;
      recordInst();
      printf("Your taxable income is: ");
      recordInst();
      printf("%d\n", line6);
      recordInst();
      printf("Enter the amount of Federal income tax withheld: ");
      recordInst();
      redat8 = getinput();
      recordInst();
      line7 = redat8;
      recordInst();
      printf("Enter <1> if you get an earned income credit (EIC); ");
      recordInst();
      printf("enter 0 otherwise: ");
      recordInst();
      redat9 = getinput();
      recordInst();
      eic = redat9;
      recordInst();
      line8 = 0;
      recordInst();
      if ((eic == 0)) goto L49;
      
      recordInst();
      printf("OK, I'll give you a thousand dollars for your credit.\n");
      recordInst();
      line8 = 1000;
   L49:;
      recordInst();
      printf("Your total tax payments amount to: ");
      int temp_2;
      recordInst();
      temp_2 = (line8 + line7);
      recordInst();
      printf("%d\n", temp_2);
      int temp_3;
      recordInst();
      temp_3 = (line6 * 28);
      int temp_4;
      recordInst();
      temp_4 = (temp_3 + 50);
      recordInst();
      line10 = (temp_4 / 100);
      recordInst();
      printf("Your total tax liability is: ");
      recordInst();
      printf("%d\n", line10);
      int temp_5;
      recordInst();
      temp_5 = (line8 + line7);
      int temp_6;
      recordInst();
      temp_6 = (temp_5 - line10);
      recordInst();
      if ((temp_6 >= 0)) goto L54;
      
      recordInst();
      deadline11 = 0;
      recordInst();
      goto L55;
   L54:;
      recordInst();
      deadline11 = 0;
   L55:;
      int temp_7;
      recordInst();
      temp_7 = (line8 + line7);
      int temp_8;
      recordInst();
      temp_8 = (temp_7 - line10);
      recordInst();
      if ((temp_8 <= 0)) goto L56;
      {
      recordInst();
      printf("Congratulations, you get a tax refund of $");
      int temp_9;
      recordInst();
      temp_9 = (line8 + line7);
      int temp_10;
      recordInst();
      temp_10 = (temp_9 - line10);
      recordInst();
      printf("%d\n", temp_10);
      }
   L56:;
      int temp_11;
      recordInst();
      temp_11 = (line8 + line7);
      int temp_12;
      recordInst();
      temp_12 = (line10 - temp_11);
      recordInst();
      if ((temp_12 < 0)) goto L59;
      {
      recordInst();
      printf("Bummer. You owe the IRS a check for $");
      int temp_13;
      recordInst();
      temp_13 = (line8 + line7);
      int temp_14;
      recordInst();
      temp_14 = (line10 - temp_13);
      recordInst();
      printf("%d\n", temp_14);
      }
   L59:;
      int temp_15;
      recordInst();
      temp_15 = (line8 + line7);
      int temp_16;
      recordInst();
      temp_16 = (line10 - temp_15);
      recordInst();
      if ((temp_16 >= 0)) goto L62;
      
      recordInst();
      deadline12 = 0;
      recordInst();
      goto L63;
   L62:;
      recordInst();
      deadline12 = 0;
   L63:;
      recordInst();
      printf("%d\n", line6);
      int temp_17;
      recordInst();
      temp_17 = (line8 + line7);
      recordInst();
      printf("%d\n", temp_17);
      recordInst();
      printf("%d\n", line10);
      recordInst();
      printf("%d\n", b);
      recordInst();
      printf("%d\n", e);
      recordInst();
      printf("%d\n", d);
      recordInst();
      printf("%d\n", deadline11);
      recordInst();
      printf("%d\n", deadline12);
      recordInst();
      line6 = line10;
      recordInst();
      line8 = 0;
      recordInst();
      line10 = 0;
      recordInst();
      deadline11 = (b + deadline12);
      recordInst();
      deadline12 = (e + d);
      recordInst();
      printf("Thank you for using ez-tax.\n");
      }
      report();
      return 0;
   L9:;
}
/* FileAst "tax.adap" End */
/* ProgAst "tax.adap" End */
