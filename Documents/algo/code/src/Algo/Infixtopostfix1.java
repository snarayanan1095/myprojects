package Algo;

import java.util.Scanner;
import java.util.Stack;

public class Infixtopostfix1 {

	 private boolean isOperator(char c){
	  if(c == '+' || c == '-' || c == '*' || c =='/' || c == '^')
	   return true;
	  return false;
	 }

	 private boolean checkPrecedence(char c1, char c2){
	  if((c2 == '+' || c2 == '-') && (c1 == '+' || c1 == '-'))
	   return true;
	  else if((c2 == '*' || c2 == '/') && (c1 == '+' || c1 == '-' || c1 == '*' || c1 == '/'))
	   return true;
	  else if((c2 == '^') && (c1 == '+' || c1 == '-' || c1 == '*' || c1 == '/'))
	   return true;
	  else
	   return false;
	 }

	 public String convert(String infix){
	  String postfix = "";  
	  Stack<Character> s = new Stack<>(); 
	  s.push('#'); 
	 
	
	 
	  for(int i = 0; i < infix.length(); i++){
	   char inputSymbol = infix.charAt(i);  
	   if(isOperator(inputSymbol)){ 
	    while(checkPrecedence(inputSymbol, s.peek()))
	     postfix += s.pop();
	    s.push(inputSymbol);
	   }
	   else if(inputSymbol == '(')
	    s.push(inputSymbol); 
	   else if(inputSymbol == ')'){
	    while(s.peek() != '(') 
	     postfix += s.pop();
	    s.pop();
	   }
	   else
	    postfix += inputSymbol;
	  }
	
	  while(s.peek() != '#'){
	   postfix += s.pop();
	   System.out.printf("%-8s%-10s%-15s\n", "",format(s.toString()),postfix);  
	 
	  }
	   
	  return postfix;
	 }

	 private String format(String s){
	  s = s.replaceAll(",","");  
	  s = s.replaceAll(" ",""); 
	  s = s.substring(1, s.length()-1);  
	   
	  return s;
	 }
	  
	 public static void main(String[] args) {
	 Infixtopostfix1 obj = new Infixtopostfix1();
	  Scanner sc = new Scanner(System.in);
	  System.out.print("Infix : \t");
	  String infix = sc.next();
	  System.out.print("Postfix : \t"+obj.convert(infix));
	 }
	}