import java.io.IOException;

/**
 * 
 */

/**
 * @author shwethanarayanan
 *
 */
public class assignment1 {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String students[][] = { {"Name1", "9", "2", "3" },  {"Name2", "3", "1", "3"},
				 {"Name3",  "7", "4", "1"}, {"Name4","6","1", "2"},{"Name5","10","3", "1"}};
		String map[][]={{" "," "," "," "," "},{" "," "," "," "," "},{" "," "," "," "," "},{" "," "," "," "," "},{" "," "," "," "," "}};
		

char inChar = ' ';
int elementary_count=0;
int middle_count=0;
int high_count=0;
char[] command=new char[3];
int c=0;
	
System.out.println("Enter a choice:");
System.out.println("Enter s for student grade level");
System.out.println("Enter m to see student region map");
System.out.println("Enter h to see history of commands");
System.out.println("Enter q to quit the program");

do{

inChar = (char) System.in.read();

	if(inChar=='s'||inChar=='m'||inChar=='h'){
		command[c%3]=inChar;
	}


	 
switch(inChar){
case 's':
	elementary_count=0;
	middle_count=0;
	high_count=0;
	for (int k=0; k<students.length;k++){
		if ( Integer.parseInt(students[k][1])<7){
			 elementary_count=elementary_count+1;
		}
		else if( Integer.parseInt(students[k][1])>6 && Integer.parseInt(students[k][1])<9){
			middle_count=middle_count+1;
		}
		else if (Integer.parseInt(students[k][1])>9){
		high_count=high_count+1;
		}
	}
	System.out.println("The number of students in elementary school is "+elementary_count);
	System.out.println("The number of students in middle school is "+middle_count);
	System.out.println("The number of students in high school is "+high_count);
		break;
case 'm':{
	for( int m=0;m<map.length;m++){
		if(Integer.parseInt(students[m][1])<7){
		map[Integer.parseInt(students[m][2])][Integer.parseInt(students[m][3])]="e";
		}
		else if( Integer.parseInt(students[m][1])>6 && Integer.parseInt(students[m][1])<9){
		map[Integer.parseInt(students[m][2])][Integer.parseInt(students[m][3])]="m";
		}
		else if (Integer.parseInt(students[m][1])>9){
		map[Integer.parseInt(students[m][2])][Integer.parseInt(students[m][3])]="h";
		}
	}
	for ( int n=0; n<map.length; n++){
		for (int p=0; p<map[n].length; p++){
		System.out.print(map[n][p]);

		}
		System.out.println();
		}}
	break;
case 'h':

	
	for(int p=0;p<3;p++){
		if (command[p]=='s'){
			System.out.println("s: Student grade level");
		}
		else if(command[p]=='m'){
			System.out.println("m: Student region map");
		}
		else if(command[p]=='h'){
			System.out.println("h: History of commands");
		}
	

	}
	break;
case 'q': 
	
	break;
	}
c++;
}
while (inChar != 'q'); // Exit on quit
System.out.println("Quiting...");
}
}






		




