package Algo;

import sun.security.util.Length;

public class StackusingArrays {
	
		private Item[] s;
		

		public StackusingArrays() {
			s = new Item[0];
		}

		public boolean isEmpty() {
			return s.length == 0;
		}

		public void push(Item item) {
			increaseCapacity();
			s[s.length-1] = item;
		}
		
		private void increaseCapacity(){
			Item[] incS = new Item[s.length+1];
			for(int i=0; i<s.length; i++){
				incS[i] = s[i];
			}
			s = incS;
		}
		
		private void decreaseCapacity(){
			Item[] decS = new Item[s.length-1];
			for(int i=0; i<decS.length; i++){
				decS[i] = s[i];
			}
			s = decS;
		}

		public Item pop() {
			Item toreturn = s[s.length-1];
			decreaseCapacity();
			return toreturn;
		}
		
		 public static void main(String[] args) 
		 { 
			 Item it1=new Item(31, "name1");
			 Item it2=new Item(24, "name2");
			 Item it3=new Item(10, "name3");
			 Item it4=new Item(44, "name4");
			 Item it5=new Item(81, "name5");
			 StackusingArrays sa = new StackusingArrays();
			 sa.push(it1);
			 sa.push(it2);
			 sa.push(it3);
			 sa.push(it4);
			 sa.push(it5);
			 while(!sa.isEmpty()){
				 System.out.println(sa.pop());
			 }
		}
	
}

 class Item {
	int age;
	String name;
	
	
	public Item(int age, String name)
	{
		this.age = age;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Age is: "+String.valueOf(age)+" | Name is: "+name;
	}

	public int getAge() {
		return age;
	}

	public String getName() {
		return name;
	}
}
 