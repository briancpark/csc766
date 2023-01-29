int foo(int x){
  int y;

  RecordInst();
  y = x + 1;

  return y;
}

int main(){
  int b;
  int c[10];

  Init();

  RecordInst();
  b = 0;

  RecordInst();
  c[0] = 10;

  RecordInst();
  c[1] = foo(b);

  RecordInst();
  b = c[0] + c[1];

  RecordInst();
  printf("%d\n",b);

  Report();
  return 0;
}

