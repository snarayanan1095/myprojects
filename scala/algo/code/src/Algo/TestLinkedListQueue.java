package Algo;

public class TestLinkedListQueue {

	private class Node {
		String item;
		Node next;
	}
	Node first,last;
	
	TestLinkedListQueue() {
		this.first=null;
		this.last=null;
	}
	
	
	public void enqueue(String item) {
		Node oldlast=last;
		last=new Node();
		last.item=item;
		last.next=null;
		if(isEmpty()) first=last;
		else oldlast.next=last;
	}
	
	public boolean isEmpty() {
		return first==null;
	}
	
	public String dequeue(){
		String item=first.item;
		first=first.next;
		if(isEmpty()) last=null;
		return item;
		}
	
	public static void main(String[] args) {
		TestLinkedListQueue tlq=new TestLinkedListQueue();
		tlq.enqueue("Item1");
		tlq.enqueue("Item2");
		tlq.enqueue("Item3");
		tlq.enqueue("Item4");
		tlq.enqueue("Item5");
		
		System.out.println("Item1 is dequeud "+tlq.dequeue());
		System.out.println("Item2 is dequeud "+tlq.dequeue());
		System.out.println("Item3 is dequeud "+tlq.dequeue());
		System.out.println("Item4 is dequeud "+tlq.dequeue());
		System.out.println("Item5 is dequeud "+tlq.dequeue());
		
		

	}

}
