import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Converter {

	public static void main(String[] args) {
		//Creates and displays the main program frame
	    
        JFrame frame = new JFrame("Converter");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ImageIcon image = new ImageIcon("src/Logo.png");        //Creates an image icon.
        frame.setIconImage(image.getImage());
 
        MainPanel panel = new MainPanel();
 
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(600, 600));
        frame.setJMenuBar(panel.setupMenu());
        
        frame.getContentPane().add(panel);
        frame.setLocation(500,75);
        frame.pack();
        frame.setVisible(true);
    }
}