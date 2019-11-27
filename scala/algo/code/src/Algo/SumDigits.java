package Algo;

public class SumDigits {
	int n;
	int sum=0;
	
	
	public int sumofdigits(int n) {
		if(n==0) {
			return sum;
		}
		else if(n<0) {
			throw new IllegalArgumentException("The integer specified is negative");
		}
		else {
			 return n%10+sumofdigits(n/10);
		}
	}
	
	public static void main(String[] args) {
		SumDigits sd=new SumDigits();
		System.out.println("The sum of the digits is "+sd.sumofdigits(26397));
		
		}

}
