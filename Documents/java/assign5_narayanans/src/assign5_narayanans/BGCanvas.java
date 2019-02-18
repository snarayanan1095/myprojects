package assign5_narayanans;





import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;



/**
 * @author shwethanarayanan
 * NUID: (001422526)
 *
 */

/**
 * A sample canvas that draws a rainbow of lines
 *
 */
public class BGCanvas extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(BGCanvas.class.getName());
    private int lineSize = 20;
    private Color col = null;
    private long counter = 0L;
    MyStem stem=new MyStem();
    /**
     * CellAutCanvas constructor
     */
	public BGCanvas() {
		col = Color.white;
		}
	/**
	 * The UI thread calls this method when the screen changes, or in response
	 * to a user initiated call to repaint();
	 */

	
	public void paint(Graphics g) {
		drawBG(g);
		}
	
	
	/**
	 * Draw the CA graphics panel
	 * @param g
	 */
	public void drawBG(Graphics g) {
		
		log.info("Drawing BG " + counter++);
		Graphics2D g2d = (Graphics2D) g;
		
		Dimension size = getSize();
		
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, size.width, size.height);
		
		g2d.setColor(Color.RED);
		g2d.drawString("MyPlant", 250, 15);
		
		int maxRows = size.height / lineSize;
		int maxCols = size.width / lineSize;
		for (int j = 0; j < maxRows; j++) {
		   for (int i = 0; i < maxCols; i++) {
			   int redVal = validColor(i*5);
			   int greenVal = validColor(255-j*5);
			   int blueVal = validColor((j*5)-(i*2));
			   col = new Color(redVal, greenVal, blueVal);
			   // Draw box, one pixel less to create a black outline
			   int startx = i*lineSize;
			   int starty = j*lineSize;
			   int endx = startx + 15;
			   int endy = starty + 15;

		}
		   
		/*getting the stemlist from MyStem class */
		for(int i=0;i<stem.StemList.size();i++){ 
		
				int x1=stem.StemList.get(i).getxstart();
				int y1=stem.StemList.get(i).getystart();
				int x2=stem.StemList.get(i).getxend();
				int y2=stem.StemList.get(i).getyend();
				
				
				g2d.setColor(Color.GREEN); // the branches are made green 
				paintLine( g2d, x1, y1, x2, y2, col); 
			
		
				if(stem.getPlantAge()>=2 && stem.getPlantAge()<=5) {
					g2d.setColor(Color.red); // flowers are made red
					g2d.drawLine(x1, y1, x1+2, y1+2); //Flowers Bloom in the Spring season
				}
		}
		}
				
		}
		
		
	
	
	
	/*
	 * A local routine to ensure that the color value is in the 0 to 255 range.
	 */
	private int validColor(int colorVal) {
		if (colorVal > 255)
			colorVal = 255;
		if (colorVal < 0)
			colorVal = 0;
		return colorVal;
	}
	
	
	/**
	 * A convenience routine to set the color and draw a line
	 * @param g2d the 2D Graphics context
	 * @param startx the line start position on the x-Axis
	 * @param starty the line start position on the y-Axis
	 * @param endx the line end position on the x-Axis
	 * @param endy the line end position on the y-Axis
	 * @param color the line color
	 */
	
	
	public void update(Observable o, Object arg) {
	
		MyStem branch =(MyStem) arg;
		repaint();
	}
	
	
	public void paintLine(Graphics2D g2d, int startx, int starty,int endx, int endy, Color color) {

			g2d.setColor(Color.GREEN);
			g2d.drawLine(startx,starty,endx,endy);
			
	}

}

