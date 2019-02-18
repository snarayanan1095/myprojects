package Algo;

import static java.lang.System.exit; 

public class StackUsingLinkedlist { 
	
 private class Node { 

     int Age; 
     String Name;
     Node link; 
 } 
 
 Node top; 

 StackUsingLinkedlist() 
 { 
     this.top = null; 
 } 


 public void push(int x, String name) 
 { 
     
     Node temp = new Node(); 
  
     temp.Age = x; 
     temp.Name=name;
     temp.link = top; 

     top = temp; 
 } 

 public boolean isEmpty() 
 { 
     return top == null; 
 } 

 
 public int peekAge() 
 { 
  
     if (!isEmpty()) { 
         return top.Age; 
     } 
     else { 
         System.out.println("Stack is empty"); 
         return -1; 
     } 
 } 
 
 public String peekName() 
 { 
 
     if (!isEmpty()) { 
         return top.Name; 
     } 
     else { 
         System.out.println("Stack is empty"); 
         return null; 
     } 
 } 


 public void pop()
 { 
   
     if (top == null) { 
         System.out.print("\nStack Underflow"); 
         return; 
     } 

  
     top = (top).link; 
 } 

 public void display() 
 { 
     if (top == null) { 
         System.out.println("Stack Underflow"); 
         exit(1); 
     } 
     else { 
         Node temp = top; 
         while (temp != null) { 

             System.out.println("The age is "+temp.Age+" and the name is "+temp.Name);
        

             temp = temp.link; 
         } 
     } 
 } 
 


 public static void main(String[] args) 
 { 
    
     StackUsingLinkedlist obj = new StackUsingLinkedlist(); 
     
     obj.push(31,"Name1"); 
     obj.push(24,"Name2"); 
     obj.push(10,"Name3"); 
     obj.push(44,"Name4"); 
     obj.push(81,"Name5");
   

     obj.display(); 
 }


} 