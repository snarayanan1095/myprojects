package Algo;

public class Arrayofqueue {
	
	int head, tail, queuecap;
	int arr[];
	
	Arrayofqueue(int capacity){
		head=0;
		tail=0;
		queuecap=capacity;
		arr=new int[queuecap];
	}
	
	void enqueue(int num) {
		if(queuecap==tail){
			System.out.println("The queue is full");
		}
		else {
		arr[tail]=num;
		tail++;
		}
	}
	
	void dequeue() {
		if(head==tail) {
			System.out.println("The queue is empty");
			return;
		}
		else {
			System.out.println("The dequeued item is: "+arr[head]);
			head++;
			}
	}
	
	void queuedisplay() {
		if(head==tail) {
			System.out.println("The queue is empty");
			return;
		}
		else{
			System.out.println("The components of the queue are:");
			for(int i=head;i<tail;i++) {
				System.out.println(arr[i]);
			}
		}
	}

	public static void main(String[] args) {
		Arrayofqueue aq=new Arrayofqueue(5);
		aq.enqueue(4);
		aq.queuedisplay();
        aq.dequeue();
        aq.queuedisplay();
        aq.enqueue(56);
        aq.queuedisplay();
        aq.enqueue(2);
        aq.queuedisplay();
        aq.enqueue(67);
        aq.queuedisplay();
        aq.dequeue();
        aq.queuedisplay();
        aq.dequeue();
        aq.queuedisplay();

	}

}
