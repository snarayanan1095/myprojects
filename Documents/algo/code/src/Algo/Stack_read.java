package Algo;
import java.util.*; 
  
public class Stack_read 
{ 
     public static Stack<String> stack_read(Stack<String> input)
     {
        Stack<String> tmpStack = new Stack<String>(); 
        String tmp;
        while(!input.isEmpty()){ 
    	   for(int i=0;i<input.size();i++){
        	tmp=input.pop();	//pops each element and assigns to tmp
        	tmpStack.push(tmp); //tmp is pushed to tmpstack-- so the order should be "time,of,best,the,was,it"
    	   }
        }
       
       while(!tmpStack.isEmpty()){
    	   for(int i=0;i<tmpStack.size();i++){
    	   tmp=tmpStack.pop();		//pops each element from tmpstack and assigns to tmp
    	   input.push(tmp);			//tmp is pushed into input--so the order is "it,was,the,best,of,time"
    	   }
       }
       return input; 
     }

    public static void main(String args[]) 
    { 
        Stack<String> input = new Stack<String>(); 
        input.push("It"); 
        input.push("was"); 
        input.push("the"); 
        input.push("best"); 
        input.push("of"); 
        input.push("time"); 
   
        while(!input.isEmpty()){
        for(int i=0;i<input.size();i++){
        	Stack<String> tmpStack=stack_read(input);
        	System.out.println(tmpStack.pop());
        }}}
} 