package Algo;

import java.util.Stack;

public class Stack_readback {
	
	private String a;
	Stack<String> time = new Stack<String>(); 
	
	public void split(){
		String[] arr=a.split(" ");
		for(int i=arr.length-1;i>=0;i--) {
			time.push(arr[i]);
		}
		
	}

	public static void main(String[] args) {
		Stack_readback rb=new Stack_readback();
		rb.a="It was the best of the time";
		rb.split();
		while(!rb.time.isEmpty()){
		System.out.println(rb.time.pop());
		}

}
}
