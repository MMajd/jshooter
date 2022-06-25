import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsDevice.WindowTranslucency;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class WindowFrame extends JFrame { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GraphicsConfiguration gc; 
	PanelFrame panel; 
	
	@SuppressWarnings("deprecation")
	public WindowFrame () { 
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment(); 
		GraphicsDevice gd = ge.getDefaultScreenDevice(); 
		gc = gd.getDefaultConfiguration(); 

		setSize(new Dimension(gc.getBounds().width, gc.getBounds().height)); 
		
		panel = new PanelFrame(); 
		setContentPane(panel);
		
		setCursor(Cursor.CROSSHAIR_CURSOR);

		setUndecorated(true);
		if (gd.isWindowTranslucencySupported(WindowTranslucency.TRANSLUCENT))
		{
			setOpacity(0.07f); 
			setAlwaysOnTop(true);
			System.out.println("Transparency Available");
		}	
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void drawRectangle(int x, int y, int x1, int y1) 
	{ 
		panel.drawRectangle(x, y, x1, y1);
	}

	public Rectangle getShootRectagle() {
		return panel.getRectangle(); 
	}
	
}

class PanelFrame extends JPanel { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Graphics2D g2d; 
	
	
	
	private int x, y; 
	private int x1, y1; 
	int startx, starty; 

	public PanelFrame() { 
		g2d = null; 
		repaint(); 
	}

	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g2d = (Graphics2D) g; 
		
		
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, 
				Toolkit.getDefaultToolkit().getScreenSize().height);
		
		g2d.setColor(Color.RED);
		if (x < x1) startx = x; 
		else startx = x1; 
		if (y < y1) starty = y; 
		else starty = y1; 
			
		g2d.fillRect(startx, starty, Math.abs(x-x1), Math.abs(y-y1));
		
		g2d.dispose(); 
		
	}
	
	public void whiteScreen() { 
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, 
				Toolkit.getDefaultToolkit().getScreenSize().height);

	}
	
	public void drawRectangle(int x, int y, int x1, int y1)
	{ 
		this.x 	= x; 
		this.y 	= y; 
		this.x1 = x1; 
		this.y1 = y1; 
		repaint();
	}
	
	public Rectangle getRectangle() { 
		return new Rectangle(startx, starty, Math.abs(x-x1), Math.abs(y-y1)); 
	}
}
