/* ProgAst "jacobi.adap" Begin */
	/* FileAst "jacobi.adap" Begin */
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
int a[262144];
int b[262144];
int main() ;

int main() {
      init();
      {
      int j;
      int i;
      recordInst();
      i = 0;
   L2:;
      recordInst();
      j = 0;
   L6:;
      {
      int temp_0;
      recordInst();
      temp_0 = (i + 12);
      int temp_1;
      recordInst();
      temp_1 = (512 * i);
      int temp_2;
      recordInst();
      temp_2 = (j + temp_1);
      int temp_3;
      recordInst();
      temp_3 = (1 * temp_2);
      recordInst();
      a[temp_3]  = (temp_0 + j);
      int temp_4;
      recordInst();
      temp_4 = (i + 12);
      int temp_5;
      recordInst();
      temp_5 = (512 * i);
      int temp_6;
      recordInst();
      temp_6 = (j + temp_5);
      int temp_7;
      recordInst();
      temp_7 = (1 * temp_6);
      recordInst();
      b[temp_7]  = (temp_4 + j);
      }
   L7:;
      recordInst();
      j = (j + 1);
      recordInst();
      if ((j < 512)) goto L6;
   L3:;
      recordInst();
      i = (i + 1);
      recordInst();
      if ((i < 512)) goto L2;
      recordInst();
      i = 0;
   L11:;
      recordInst();
      j = 0;
   L15:;
      {
      int temp_8;
      recordInst();
      temp_8 = (i - 1);
      int temp_9;
      recordInst();
      temp_9 = (512 * j);
      int temp_10;
      recordInst();
      temp_10 = (temp_8 + temp_9);
      int temp_11;
      recordInst();
      temp_11 = (1 * temp_10);
      int temp_12;
      recordInst();
      temp_12 = b[temp_11] ;
      int temp_13;
      recordInst();
      temp_13 = (i + 1);
      int temp_14;
      recordInst();
      temp_14 = (512 * j);
      int temp_15;
      recordInst();
      temp_15 = (temp_13 + temp_14);
      int temp_16;
      recordInst();
      temp_16 = (1 * temp_15);
      int temp_17;
      recordInst();
      temp_17 = b[temp_16] ;
      int temp_18;
      recordInst();
      temp_18 = (temp_12 + temp_17);
      int temp_19;
      recordInst();
      temp_19 = (i + j);
      int temp_20;
      recordInst();
      temp_20 = (1 * temp_19);
      int temp_21;
      recordInst();
      temp_21 = (temp_20 - 512);
      int temp_22;
      recordInst();
      temp_22 = b[temp_21] ;
      int temp_23;
      recordInst();
      temp_23 = (temp_18 + temp_22);
      int temp_24;
      recordInst();
      temp_24 = (i + j);
      int temp_25;
      recordInst();
      temp_25 = (1 * temp_24);
      int temp_26;
      recordInst();
      temp_26 = (temp_25 + 512);
      int temp_27;
      recordInst();
      temp_27 = b[temp_26] ;
      int temp_28;
      recordInst();
      temp_28 = (temp_23 + temp_27);
      int temp_29;
      recordInst();
      temp_29 = (512 * j);
      int temp_30;
      recordInst();
      temp_30 = (i + temp_29);
      int temp_31;
      recordInst();
      temp_31 = (1 * temp_30);
      recordInst();
      a[temp_31]  = (0.25 * temp_28);
      }
   L16:;
      recordInst();
      j = (j + 1);
      recordInst();
      if ((j < 511)) goto L15;
   L12:;
      recordInst();
      i = (i + 1);
      recordInst();
      if ((i < 511)) goto L11;
      recordInst();
      i = 0;
   L20:;
      recordInst();
      j = 0;
   L24:;
      {
      int temp_32;
      recordInst();
      temp_32 = (512 * j);
      int temp_33;
      recordInst();
      temp_33 = (i + temp_32);
      int temp_34;
      recordInst();
      temp_34 = (1 * temp_33);
      int temp_35;
      recordInst();
      temp_35 = (512 * j);
      int temp_36;
      recordInst();
      temp_36 = (i + temp_35);
      int temp_37;
      recordInst();
      temp_37 = (1 * temp_36);
      recordInst();
      b[temp_34]  = a[temp_37] ;
      }
   L25:;
      recordInst();
      j = (j + 1);
      recordInst();
      if ((j < 511)) goto L24;
   L21:;
      recordInst();
      i = (i + 1);
      recordInst();
      if ((i < 511)) goto L20;
      int temp_38;
      recordInst();
      temp_38 = (256 + 131072);
      int temp_39;
      recordInst();
      temp_39 = b[temp_38] ;
      recordInst();
      printf("The sampled element is %d\n", temp_39);
      }
      report();
      return 0;
   L1:;
}
/* FileAst "jacobi.adap" End */
/* ProgAst "jacobi.adap" End */
