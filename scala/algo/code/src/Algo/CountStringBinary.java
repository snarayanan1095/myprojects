package Algo;



public class CountStringBinary {

	  static String st="";
	
	public static void bin(int n) {
		if (n > 1) {
	    	bin(n/2);
	    }
	   
		st=st.concat(String.valueOf(n%2));
	  }
	
	public void append(int num) {
		for(int i=0;i<Math.pow(2, num);i++){
			st="";
			bin(i);
			while(st.length()!=num){
				st="0".concat(st);
			}
			
			if(st.indexOf("00")==-1)
			    System.out.println(st);
			    }

	}
	
	
	
	  public static void main(String[] args)  
	{ 
		
		CountStringBinary csb=new CountStringBinary();
		csb.append(3);
	} 
	} 


