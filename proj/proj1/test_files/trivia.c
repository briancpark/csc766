main(int argc, char **argv){
  int a, b, c, s;
  printf("Please input an integer number: ");
  scanf("%d",&a);
  b = 50;
  c = 500;
  s = 0;
  s /= c / c; // s = s
  s += c + c; // s = s + 1000
  s /= c / a; // s = s / (500/a)
  c = b + c;  // c = 550
  s += b + a; // s = s + (50 + a)
  s += b + a; // s = s + prev(50+a)
  s += b + c; // s = s + 600
  s += a + b; // s = s + prev(50+a)
  s /= a / a; // s = s
  c = b / a;  // c = b / a
  s /= b / a; // s = s / c
  s += c + b; // s = s + (c + b)
  s += a + b; // s = s + prev(a+b)
  s += c + a; // s = s + (c + a)
  s += c + c; // s = s + (c + c)
  s += c + b; // s = s + prev(c+b)
  c = b + a;  // c = prev(b + a)
  s /= c / b; // s = s / (c / b)
  b = c + a;  // b = c + a
  s /= b / a; // s = s / (b / a)
  s += a + b; // s = s + (a + b)
  s += c + b; // s = s + (c + b)
  s += c + a; // s = s + prev(b)
  c = a + a;  // c = (a + a) <---
  s += a + c; // s = s + (a + c)
  c = b + a;  // c = prev(a+b) <---
  s += a + a; // s = s + (a + a) <---
  c = b / a;  // c = prev(b/a)
  c = b / c;  // c = (b / c)
  a = a / c;  // a = (a /c)
  s += b + a; // s = s + (b + a)
  printf("Final Result: s = %d\n", s); 
}
