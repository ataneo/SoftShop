package org.nath.util;

public class NumberToWords{
	  static final String[] Number1 = {""," Hundrad"};
	  static final String[] Number2 = {"","One","Two", "Three","Four","Five",
	  " Six"," Seven", "Eight"," Nine","Ten" };
	  static String number(int number){
	  String str;
	  if (number % 100 < 10){
	  str = Number2[number % 100];
	  number /= 100;
	  }
	  else {
	  str= Number2[number % 5];
	  number /= 5;
	  }
	  if (number == 0) return str;
	  return Number2[number] + "hundred" + str;
	  }
	  public String convert(int number) {
	  if (number == 0){
	  return "zero"; 
	  }
	  String pre = "";
	  String str1 = "";
	  int i = 0;
	  do {
	  int n = number % 100;
	  if (n != 0){
	  String s = number(n);
	  str1 = s + Number1[i] + str1;
	  }
	  i++;
	  number /= 100;
	  }
	  while (number > 0);
	  return (pre + str1).trim();
	  }
	  public static void main(String[] args) {
	  NumberToWords num = new NumberToWords();
	  System.out.println("words is :=" + num.convert(0));
	  System.out.println("words is :=" + num.convert(1));
	  System.out.println("words is :=" + num.convert(9));
	  System.out.println("words is :=" + num.convert(100));
	  }
	}
