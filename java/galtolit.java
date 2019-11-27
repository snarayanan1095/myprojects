class galtolit{
	public static void main(String args[]){
	double gal;
	double lit;
	int i;
	i=0;

	for(gal=0;gal<=100;gal++) {
		lit=gal*3.785;
		System.out.println(lit + " liter is " +gal +" gallons");
	if(i%10==0){
		System.out.println();

	}
	i=i+1;
    }
		
}
}