import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsDevice.WindowTranslucency;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public class FullFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GraphicsConfiguration gc; 
	
	public FullFrame() { 
		initFrame(); 
	}
	
	private void initFrame() { 
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment(); 
		GraphicsDevice gd = ge.getDefaultScreenDevice(); 
		gc = gd.getDefaultConfiguration(); 
		
		setSize(new Dimension(gc.getBounds().width, gc.getBounds().width));
		setUndecorated(true);

		if (gd.isWindowTranslucencySupported(WindowTranslucency.TRANSLUCENT))
		{
			setOpacity(0.0001f); 
			setAlwaysOnTop(true);
			System.out.println("Transparency Available");
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void paintComponents(Graphics g) {
		super.paintComponents(g);
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	
	public int getWidth() { 
		return (int)gc.getBounds().getWidth(); 
	}
	public int getHeight() { 
		return (int) gc.getBounds().getHeight(); 
	}
}
