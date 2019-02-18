class twod_map{
	public static void main(String args[]){
	long region_map[][] = {{1,2,3,4,5},
						{9,8,7,6,5}};

	int rows=5;
	int col=5;
	int i,j;

	for (i=0; i<rows; i++){
		for (j=0; j<col; j++){
			System.out.print(region_map[i][j]);
		}
		System.out.println();
	}

	}
	}
