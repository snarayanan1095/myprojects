package assign5_narayanans;



/**
 * @author shwethanarayanan
 * NUID: (001422526)
 *
 */



import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * A Test application for the Wolfram Biological Growth application
 *
 */
public class WolfApp extends BGApp {

	private static Logger log = Logger.getLogger(WolfApp.class.getName());

	protected JPanel mainPanel;
	protected JPanel northPanel;
	protected JButton startBtn_rule1;
	protected JButton startBtn_rule2;
	protected JButton stopBtn;
    private BGCanvas bgPanel;
  
	
    /**
     * Sample app constructor
     */
    public WolfApp() {
    	frame.setSize(1000, 1000); // initial Frame size
		frame.setTitle("MyPlant");
		
		menuMgr.createDefaultActions(); // Set up default menu items
		
    	showUI(); // Cause the Swing Dispatch thread to display the JFrame
    }
   
    /**
     * Create a main panel that will hold the bulk of our application display
     */
	@Override
	public JPanel getMainPanel() {
	
		mainPanel = new JPanel();
    	mainPanel.setLayout(new BorderLayout());
    	mainPanel.add(BorderLayout.NORTH, getNorthPanel());
    	
    	bgPanel = new BGCanvas();
    	mainPanel.add(BorderLayout.CENTER, bgPanel);
    	
    	return mainPanel;
	}
    

	/**
	 * Create a top panel that will hold control buttons
	 * @return
	 */
    public JPanel getNorthPanel() {
    	northPanel = new JPanel();
    	northPanel.setLayout(new FlowLayout());
    	
    	startBtn_rule1 = new JButton("Start-rule1");
    	startBtn_rule1.addActionListener(this);
    	
    	startBtn_rule2 = new JButton("Start-rule2");
    	startBtn_rule2.addActionListener(this);

    	northPanel.add(startBtn_rule1);
    	northPanel.add(startBtn_rule2);
    	
    	stopBtn = new JButton("Stop"); // Allow the app to hear about button pushes
    	stopBtn.addActionListener(this);
    	northPanel.add(stopBtn);

    	return northPanel;
    }
    
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		log.info("We received an ActionEvent " + ae);
		if (ae.getSource() == startBtn_rule1){
			System.out.println("Start for rule 1 pressed");
			
			MyStem stem=new MyStem();
			this.bgPanel.stem=stem;
			stem.addObserver(this.bgPanel);

			stem.stem_growth_rule1(400, 500, 7, -90);
			}
		else if (ae.getSource() == startBtn_rule2){
			System.out.println("Start for rule 2 pressed");
			MyStem stem=new MyStem();
	
			this.bgPanel.stem=stem;
			stem.addObserver(this.bgPanel);
	
			
			stem.stem_growth_rule2(400, 500, 7, -90);
			}
		else if (ae.getSource() == stopBtn)
			System.out.println("Stop pressed");
	}

	@Override
	public void windowOpened(WindowEvent e) {
		log.info("Window opened");
	}

	@Override
	public void windowClosing(WindowEvent e) {	
		log.info("Window closing");
	}



	@Override
	public void windowClosed(WindowEvent e) {
		log.info("Window closed");
	}



	@Override
	public void windowIconified(WindowEvent e) {
		log.info("Window iconified");
	}



	@Override
	public void windowDeiconified(WindowEvent e) {	
		log.info("Window deiconified");
	}



	@Override
	public void windowActivated(WindowEvent e) {
		log.info("Window activated");
	}



	@Override
	public void windowDeactivated(WindowEvent e) {	
		log.info("Window deactivated");
	}
	
	/**
	 * Sample Wolf application starting point
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args)  {
		WolfApp wapp = new WolfApp();
		MyStem stem=new MyStem();
		
		wapp.bgPanel.stem=stem;
		stem.addObserver(wapp.bgPanel); //adding observer
		
		stem.stem_growth_rule1(400, 500, 7, -90);
		
		log.info("WolfApp started");
	}


}

