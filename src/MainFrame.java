import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

@SuppressWarnings("unused")

public class MainFrame implements ActionListener,
MouseMotionListener, MouseListener, KeyListener{
	private static final int WIDTH = 300; 
	private static final int HEIGHT = 100; 
	
	private JFrame mainFrame; 
	private JButton fullButton; 
	private JButton windButton; 
	
	private Toolkit tool; 
	
	private FullFrame fullFrame; 
	private WindowFrame windFrame; 
	
	private Date date; 
	private SimpleDateFormat formatter;
	
	private int x, y, x1, y1; 
	
	public MainFrame() { 
		tool = Toolkit.getDefaultToolkit(); 
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss-");
		date = new Date();		
				
		GridBagConstraints c = new GridBagConstraints(); 
		
		mainFrame = new JFrame("JShooter"); 
		mainFrame.setFocusable(true);
		mainFrame.requestFocus();
		
		mainFrame.setLayout(new GridBagLayout());
		
		fullButton = new JButton(); 
		fullButton.setText("Full Window");
		fullButton.setFont(new Font("Arial", Font.PLAIN, 20));
		fullButton.setPreferredSize(new Dimension(200, 30));
		fullButton.addActionListener(this);
		
		c.fill = GridBagConstraints.NONE; 
		c.anchor = GridBagConstraints.BASELINE; 
		c.gridx = 1; 
		c.gridy = 1; 
		mainFrame.add(fullButton, c);
		
		windButton = new JButton(); 
		windButton.setText("Select Area");
		windButton.setFont(new Font("Arial", Font.PLAIN, 20));
		windButton.setPreferredSize(new Dimension(200, 30));
		windButton.addActionListener(this);

		c.fill = GridBagConstraints.NONE; 
		c.anchor = GridBagConstraints.BASELINE; 
		c.gridx = 1; 
		c.gridy = 2; 
		c.insets = new Insets(10,0,0,0);
		mainFrame.add(windButton, c); 
		
		mainFrame.addKeyListener(this);
				
		mainFrame.setSize(300, 100);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocation(tool.getScreenSize().width/2 - WIDTH/2, 
				tool.getScreenSize().height/2 - HEIGHT/2);
		mainFrame.setVisible(true);

		fullFrame = new FullFrame(); 
		
	}
	
	private void initWindFrame() { 
		windFrame = new WindowFrame(); 
		windFrame.addMouseListener(this);
		windFrame.addMouseMotionListener(this);
		windFrame.addKeyListener(this);

	}
	
	private String getName() { 
		return "JShoot-"+formatter.format(date)+System.nanoTime()+".png";  
	}
	
	private void takeFullScreenShot() { 
		try
		{
			mainFrame.setVisible(false);
			fullFrame.setVisible(true);
			
			Rectangle r = new Rectangle(0, 0, fullFrame.getWidth(),
					fullFrame.getHeight()); 
			
			Thread.sleep(250);
			
			Robot robot = new Robot(); 
			BufferedImage image = robot.createScreenCapture(r);
		
			ImageIO.write(image, "png", new File(getName()));
		}
		catch(Exception e) {e.printStackTrace();}
		
		finally {
			fullFrame.setVisible(false);
			fullFrame.dispose();
			mainFrame.setLocation(tool.getScreenSize().width/2 - WIDTH/2, 
					tool.getScreenSize().height/2 - HEIGHT/2);
			mainFrame.setVisible(true);
		}
	}
	
	private void takeFixedSizeShoot() { 
		try
		{	
			Rectangle r = new Rectangle(windFrame.getShootRectagle());
			
			if (r.width <= 0 || r.height <=0)
				return; 
			
			windFrame.setOpacity(0.0f);
			Thread.sleep(200);
			
			Robot robot = new Robot(); 
			BufferedImage image = robot.createScreenCapture(r);
			
			windFrame.dispose(); 

			choosingDirectory();
			
			ImageIO.write(image, "png", new File(getName()));
		}
		catch(Exception e) {e.printStackTrace();}
		
		finally {
			mainFrame.setLocation(tool.getScreenSize().width/2 - WIDTH/2, 
					tool.getScreenSize().height/2 - HEIGHT/2);
			mainFrame.setVisible(true);
		}
	}
	
	private void choosingDirectory() { 
		String userHome = System.getProperty("user.home"); 
		String windows = "Windows"; 
		String linux =  "Linux"; 
		String os = System.getProperty("os.name"); 
		
		JFileChooser chooser = new JFileChooser(); 
		
		if (os.contains(windows)) { 
			chooser.setCurrentDirectory(new File(userHome));
			System.out.println("Windows");
		}
		else if (os.contains(linux)) { 
			chooser.setCurrentDirectory(new File(userHome));
			System.out.println("Linux");
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == fullButton) { 
			takeFullScreenShot();
		}
		else if (e.getSource() == windButton) { 
			mainFrame.setVisible(false);
			initWindFrame();
			windFrame.setVisible(true);
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		x = e.getX(); 
		y = e.getY(); 
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		x1 = e.getX(); 
		y1 = e.getY(); 
		windFrame.drawRectangle(x, y, x1, y1);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		takeFixedSizeShoot();
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { 
			if (windFrame.isVisible()) { 
				windFrame.setVisible(false);
				windFrame.dispose();
				
				mainFrame.requestFocus();
				mainFrame.setFocusable(true); 
				
				mainFrame.setLocation(tool.getScreenSize().width/2 - WIDTH/2, 
						tool.getScreenSize().height/2 - HEIGHT/2);
				mainFrame.setVisible(true);
			}
			else if (mainFrame.isVisible()) { 
				System.exit(0); 
			}
		}		
	}
	
	//--------silent functions----------------
	public void mouseMoved(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	//-----main-----------------------------------------
	public static void main(String []args) { 
		new MainFrame(); 
	}
}

