import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
 
import java.lang.NumberFormatException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class MainPanel extends JPanel {
	
	//creating all the array's that are needed for this program in particular
	private final static String[] comboBox_selection = {
			"Pound to Euro",
			"Pound to US Dollars",
			"Pounds to Australian Dollars",
			"Pound to Canadian Dollars",
			"Pound to Icelandic krona",
			"Pound to United Arab Emirates Dirham",
			"Pound to South Korean Won",
			"Pound to Thai Baht"
			};
		
	private static Double[] newFactorArray = {1.06, 1.14, 1.52, 1.77, 130.62, 4.60, 17.96, 44.28}; 		
	private final static String[] currency_Symbol = {"€", "U$", "A$", "C$", "kr", "د.إ", "R", "฿"}; 		//storing the symbol of different currency in an array
		
	String textSplitter[] = new String[3]; 
	String name [] = new String[40];		
	double price [] = new double[40];	
	String symbol [] = new String[40];
			
	private JTextField Text;	
	private JLabel Result,Result1, total_Conversion;	
	private JComboBox<String> comboBox;	
	private JCheckBox reverse_checkBox;
	private JButton convertButton, clearButton;
	
	int total_conversion = 0; 				
	int count=0; 							
	
	JMenuBar setupMenu() { 					
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileOption = new JMenu("File");
		JMenu editOption = new JMenu("Edit");
		JMenu helpOption = new JMenu("Help");
		
		menuBar.add(fileOption);
		menuBar.add(editOption);
		menuBar.add(helpOption);

		JMenuItem File = new JMenuItem("New");	            									//creating MenuItems												
		File.setIcon(new ImageIcon("src/new.png"));	 											
		File.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));	
		
		JMenuItem Open = new JMenuItem("Load File");											
		Open.setIcon(new ImageIcon("src/open.png")); 											
		Open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
			
		Open.addActionListener(new ActionListener()
		{
			
//			file loading code
			@Override
				public void actionPerformed(ActionEvent e) 
				{
					File file;                                        //variable named "file" of type "File"
					JFileChooser fileChooser = new JFileChooser();   // creates instance of the JFileChooser class
					int fileStat = fileChooser.showOpenDialog(null);
						
					if (fileStat == JFileChooser.APPROVE_OPTION) 
					{
						file = fileChooser.getSelectedFile(); 
							
							try { 		
									
									BufferedReader fileOpener = new BufferedReader(new InputStreamReader( new FileInputStream(file),"UTF8")); 	//reads the file
									Object[] line = fileOpener.lines().toArray();   //code is reading the lines of a file and stores them into an array of Objects.
									comboBox.removeAllItems(); 
									ArrayList<Double> arrList = new ArrayList<Double>();   // instance of an ArrayList of type Double
										for(int i=0; i < line.length; i++)
											{
												try {		
													String s = line[i].toString(); 	
							
													textSplitter=s.split(","); 		
							
													if(textSplitter.length == 3) 	
														{
								
															name[count]=textSplitter[0].trim(); 	//storing the first value of the splitted item to an array
															price[count]=Double.parseDouble(textSplitter[1].trim());  
															arrList.add(price[count]);
															symbol[count]=textSplitter[2].trim(); 	//storing the third value of the Splitted item to an array
								
															if(name[count].isEmpty() || symbol[count].isEmpty())
															{
																continue;
															}
								
															else
																comboBox.addItem(s); 	//adding items into the combobox after exception handling of the data
														}
							
													}	
							
												catch (NumberFormatException er)
												{
													JOptionPane.showMessageDialog(null,"Exception : Price - Invalid Format");
												}
							
											}
						
									newFactorArray = arrList.toArray(new Double[0]);
									fileOpener.close(); 	//stops accessing the file which the user selected
								}
		
							catch(Exception er){
								JOptionPane.showMessageDialog(null,"Exception: Empty File"); 	//exception that show's up once file is not found
							}
					}
		
				} //file load
		});	

		
		JMenuItem Save = new JMenuItem("Save");													//adding save option to the file menu
		Save.setIcon(new ImageIcon("src/save.png")); 												//adding shortcut keys to menu item
		Save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK)); 	//adding icons to menu item

		JMenuItem Exit = new JMenuItem("Exit"); 												//adding exit option to the file menu
		Exit.setIcon(new ImageIcon("src/ex.png")); 												//adding icons to menu item
		Exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK)); 	//adding shortcut keys to menu item
		Exit.addActionListener(e->{ 															//adding action listener for the 'Exit' menu item

		//confirms the user's decision of exiting the application by making sure, they really want to exit the application with the help of confirm dialog
		int appExit = JOptionPane.showConfirmDialog(null , "\t\t Do you want to exit the Application?", "Exit Application",JOptionPane.YES_NO_OPTION);

		
		if (appExit == JOptionPane.YES_OPTION){		//exits the application once 'yes' option is selected
				System.exit(0);
			}
		});
			
		JMenuItem Copy = new JMenuItem("Copy");													//declaring copy option to the edit menu
		Copy.setIcon(new ImageIcon("src/Copy.png")); 												//adding icons to menu item
		Copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK)); 	//adding shortcut keys to menu item
			
		JMenuItem Paste = new JMenuItem("Paste");												//declaring paste option to the edit menu
		Paste.setIcon(new ImageIcon("src/Paste.png")); 												//adding icons to menu item
		Paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK)); //adding shortcut keys to menu item

		JMenuItem about_us = new JMenuItem("About Us");												//declaring contact us option to the about menu
		about_us.setIcon(new ImageIcon("src/about.png")); 											//adding icon to menu item
		about_us.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK)); 	//adding shortcut keys to menu item
		about_us.addActionListener(e-> { 
			JOptionPane.showMessageDialog(null, "GUI basesd Currency Convertor Application that converts the following currency and vice versa.\n\"Pound to Euro\",\r\n"
					+ "			\"Pound to US Dollars\",\r\n"
					+ "			\"Pounds to Australian Dollars\",\r\n"
					+ "			\"Pound to Canadian Dollars\",\r\n"
					+ "			\"Pound to Icelandic krona\",\r\n"
					+ "			\"Pound to United Arab Emirates Dirham\",\r\n"
					+ "			\"Pound to South Korean Won\",\r\n"
					+ "			\"Pound to Thai Baht\" \n\n(Developed by: Sandesh Bohara)\n\n ©Copyright 2023"); //message is displayed once 'About' menu item is clicked.

		});

		//declaring separators for proper spacing between menu items
		JSeparator seperator1 = new JSeparator();
		JSeparator seperator2 = new JSeparator();
		JSeparator seperator3 = new JSeparator();
		JSeparator seperator4 = new JSeparator();
		
		fileOption.add(File);					//Adding new option to the file menu
		fileOption.add(seperator1); 
		
		fileOption.add(Open);					//Adding open option to the file menu
		fileOption.add(seperator2);

		fileOption.add(Save);					//Adding save option to the file menu
		fileOption.add(seperator3);

		fileOption.add(Exit);					//Adding exit option to the file menu
		
		editOption.add(Copy);					//Adding copy option to the edit menu
		editOption.add(seperator4);
		
		editOption.add(Paste);					//Adding paste option to the edit menu
			
		helpOption.add(about_us);				//Adding about us option to the help menu

		return menuBar;

	}

	MainPanel() { 		//a constructor which handles action listeners, tool tip texts and addition of the components to the frame

		ActionListener Convert = new ConvertListener(); 

		JLabel inputLabel1  = new JLabel("Currency Convertor GUI Application"); 	//a label for displaying the name of application
		JLabel inputLabel2  = new JLabel("Select your Currency:");					//a label for displaying the selection of currency conversion
		JLabel inputLabel   = new JLabel("Insert your Value:"); 					//a label for displaying where to enter the value

		
		
		convertButton = new JButton("Convert"); 						
		convertButton.setToolTipText("Click to convert your value");	

		clearButton = new JButton("Clear"); 							
		clearButton.setToolTipText("Clear All Data");					
		
		convertButton.addActionListener(Convert); 						
		clearButton.addActionListener(Convert); 	
		

		comboBox = new JComboBox<String>(comboBox_selection);
		comboBox.setToolTipText("ComboBox"); 							
//		comboBox.addActionListener(Convert); 							
		
		
		reverse_checkBox = new JCheckBox("Reverse Conversion"); 		
		reverse_checkBox.setToolTipText("Reverse Conversion"); 			
		
		Result1  = new JLabel("Result : "); 								
		Result   = new JLabel(""); 											
		total_Conversion = new JLabel("Total Number of Conversion : 0"); 	
		Text     = new JTextField(5); 										
		Text.addKeyListener(new KeyAdapter() { 		
			public void keyPressed(KeyEvent a) {
				if (a.getKeyCode() == KeyEvent.VK_ENTER) { 					
					convertButton.doClick();
						
				}
				
			}
			
		});

		//adding all the components into the frame
		
		add(inputLabel1);
		inputLabel1.setBounds(120,20,400,80);
		inputLabel1.setFont(new Font("Garamond", Font.BOLD, 25));
		
		add(inputLabel2);
		inputLabel2.setBounds(100,132,200,40);
		inputLabel2.setFont(new Font("Times New Roman", Font.PLAIN, 21));
		
		add(comboBox);
		comboBox.setBounds(300,135,200,40);
		
		add(inputLabel);
		inputLabel.setBounds(100,215,200,40);
		inputLabel.setFont(new Font("Times New Roman", Font.PLAIN, 21));
		
		
		add(Text);
		Text.setBounds(300,220,200,30);
		
		add(convertButton);
		convertButton.setBounds(195,370,90,40);
		convertButton.setFocusable(false);
		
		add(clearButton);
		clearButton.setBounds(315,370,90,40);
		clearButton.setFocusable(false);
		
		add(reverse_checkBox);
		reverse_checkBox.setBounds(190,290,230,40);
		reverse_checkBox.setBackground(new Color(150, 220, 50));
		reverse_checkBox.setFont(new Font("Times New Roman", Font.PLAIN, 21));
		reverse_checkBox.setFocusable(false); 
		
		add(Result1);
		Result1.setBounds(230,430,90,40);
		Result1.setFont(new Font("Berlin Sans FB", Font.PLAIN, 18));
		
		add(Result);
		Result.setBounds(310,432,80,40);
		Result.setFont(new Font("Garamond", Font.PLAIN, 18));
		
		add(total_Conversion);
		total_Conversion.setBounds(190,480,280,40);
		total_Conversion.setFont(new Font("Garamond", Font.BOLD, 18));

		//settings for the frame
		setPreferredSize(new Dimension(650, 600));
		setBackground(new Color( 100, 150, 100));
	}

	private class ConvertListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent b) {
			
			Object object = b.getSource();
					
			if (object == clearButton) {
				Text.setText(null); 											
				total_Conversion.setText("Total Number Of Conversion : 0" ); 
				Result1.setText("Result: "); 				
				Result.setText(" "); 
				
			}
			
			String text = Text.getText().trim();
			if (object == convertButton) {				
				total_Conversion.setText("Total Number Of Conversion : " + Integer.toString(++total_conversion));

				if (text.isEmpty() == true)
				{

					JOptionPane.showMessageDialog(null, "Exception: Null Value Entered");
					
					

					}
			}

			
				if (text.isEmpty() == false) {

					
					if (reverse_checkBox.isSelected()){

						try { //exception handling
							double value = Double.parseDouble(text);

							//calculating the end result
							double result = (1/newFactorArray[comboBox.getSelectedIndex()]) *value;
							
							//making the output so that it only has 2 decimal places
							String output = "£"+(String.format("%.2f", result)); 

							//displaying the end result
							Result.setText(String.valueOf(output));	//converting the end result to string from double

						} catch (Exception e) { //handling exception in case the value entered by the user is of invalid data type

							//displays a message dialog in case user inputs a invalid data type
							JOptionPane.showMessageDialog(null, "Exception: Incorrect Data Type Entered!");

						}

					}

					else {

						try {

							//converting string into value using wrapper class
							double value = Double.parseDouble(text);

							//calculating the end result
							double result = newFactorArray[comboBox.getSelectedIndex()] * value;
							String symbol = currency_Symbol[comboBox.getSelectedIndex()];
							
							//making the output so that it only has 2 decimal places
							String output = symbol + (String.format("%.2f", result)); 
							

							//displaying the end result
							Result.setText(String.valueOf(output));		
						} 
						catch(Exception e) { 
							
	
							JOptionPane.showMessageDialog(null, "Exception: Incorrect Data Type Entered!");

					}
				}
			}
		}	
	}
}