package assign5_narayanans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.awt.Color;
import java.awt.Graphics;
import java.lang.Math;

/**
 * @author shwethanarayanan
 * NUID: (001422526)
 *
 */

/* Class in which there's the growth method of the plant with observable*/ 
public class MyStem extends Observable{
	String StemId="";
	int xstart;
	int ystart;
	int xend;
	int yend;
	int length;
	int radian;
	double theta;
	int PlantAge=1; // inititalising the plant age to be one - starting from january
	
	

	/* MyStem Constructor */
	MyStem(int xstart, int ystart, int xend, int yend) {
		this.xstart=xstart;
		this.ystart=ystart;
		this.xend=xend;
		this.yend=yend;
	}
	
	/* the doAction method for observable */
	public void doAction(MyStem stem) {
		setChanged(); //to indicate that changed has happened
		notifyObservers(stem); // Notifying the growth of the stem every time a new stem grows
	}
	
	
	
	/* the method used to grow the branches recursively */
	public void stem_growth_rule1(int xstart,int ystart,int length, double radian) {
		
		 if(length==0) return; // Check given for number of splits
		 
		 int xend = xstart + (int) (Math.cos(Math.toRadians(radian)) * length * 10.0);
	     int yend = ystart + (int) (Math.sin(Math.toRadians(radian)) * length * 10.0);

	     MyStem add=new MyStem(xstart,ystart,xend,yend);
	     StemList.add(add);	 //Adding each stem to an arraylist called StemList
	    
	     
		 doAction(add); // To pass on the stem values to the observer
	     stem_growth_rule1(xend,yend, length-1, radian-20);
	    
		 stem_growth_rule1(xend,yend, length-1, radian+20);
		
		 PlantAge++; // Age of the plant increases everytime a branch grows
		try {
			Thread.sleep(300L);
		} catch (InterruptedException e) { // sleeps for 300 ms everytime a branch grows
			e.printStackTrace();
		}
		if(PlantAge>12) {
			 PlantAge=1; // to make plantage 1 again after the 12 months are over
		 }

	 }
	
	public void stem_growth_rule2(int xstart,int ystart,int length, double radian) {
		
		 if(length==0) return; // Check given for number of splits
		 
		 int xend = xstart + (int) (Math.cos(Math.toRadians(radian)) * length * 10.0);
	     int yend = ystart + (int) (Math.sin(Math.toRadians(radian)) * length * 10.0);

	     MyStem add=new MyStem(xstart,ystart,xend,yend);
	     StemList.add(add);	 //Adding each stem to an arraylist called StemList
	    
	     
		 doAction(add); // To pass on the stem values to the observer
	     stem_growth_rule2(xend,yend, length-1, radian-20);
	    
		 stem_growth_rule2(xend,yend, length-1, radian+20);
		 stem_growth_rule2(xend,yend, length-1, radian-30);
		    
		 stem_growth_rule2(xend,yend, length-1, radian+30);
		 PlantAge++; // Age of the plant increases everytime a branch grows
		try {
			Thread.sleep(200L);
		} catch (InterruptedException e) { // sleeps for 500 ms everytime a branch grows
			e.printStackTrace();
		}
		if(PlantAge>12) {
			 PlantAge=1; // to make plantage 1 again after the 12 months are over
		 }

	 }
 
 
	 
	 /* getters and setters */
	public int getxend() {
		return xend;
	}
	
	public void setxend(int xend) {
		this.xend=xend;
	}
	
	public int getxstart() {
		return xstart;
	}
	
	public void setxstart(int xstart) {
		 this.xstart=xstart;
	}
	
	public int getyend() {
		return yend;
	}
	
	public void setyend(int yend) {
		this.yend=yend;
	}
	
	public int getystart() {
		return ystart;
	}
	
	public void setystart( int ystart) {
		this.ystart=ystart;
	}
	public int getPlantAge() {
		return PlantAge;
	}
	public void setPlantAge(int PlantAge) {
		this.PlantAge=PlantAge;
	}
	public MyStem() {

			StemList = new ArrayList<MyStem>();
			
			StemMap=new HashMap<String, MyStem>();
	}
			public ArrayList<MyStem> StemList;
			
			public HashMap<String,MyStem>StemMap=new HashMap<String, MyStem>();

			

			

			

	
}
