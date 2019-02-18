package Algo;

import java.util.*; 
  
public class SortStack 
{ 
	public static Stack<Integer> stack_read(Stack<Integer>  
    input) 
	{ 
Stack<Integer> tmpStack = new Stack<Integer>(); 
int tmp;
while(!input.isEmpty()){
tmp=input.pop();
tmpStack.push(tmp);
}
return tmpStack;
}
	
    public static void main(String args[]) 
    { 
        Stack<Integer> input = new Stack<Integer>(); 
        input.push(89); 
        input.push(88); 
        input.push(23); 
        input.push(34); 
        input.push(43); 
        input.push(56); 
        
        stack_read(input);
        
        for(int i=0;i<input.size();i++){
        	System.out.println(input.pop());
        }
}
}