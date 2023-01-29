int foo(int x){
  int y;
  y = x + 1;
  return y;
}

int main(){
  int b;
  int c[10];

  b = 0;
  c[0] = 10;
  c[1] = foo(b);
  b = c[0] + c[1];

  printf("%d\n",b);
  return 0;
}
