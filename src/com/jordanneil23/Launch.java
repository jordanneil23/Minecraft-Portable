package com.jordanneil23;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JTabbedPane;

import java.awt.TextArea;
import javax.swing.JCheckBox;
import java.awt.Dialog.ModalExclusionType;
import javax.swing.UIManager;
import javax.swing.JTextPane;
/**
 * @author jordanneil23
 * @version 1.0
 */
public class Launch extends JFrame {

	
	private static final long serialVersionUID = 5448542780804572609L;
	private JPanel content;
	private JTextField username;
	private JTextField password;
	public static String jr;
	public static String jav;
	public JCheckBox runmc;
	boolean done;
	/*
	final static Bukkitdl bukkit(){
		try {
			return new Bukkitdl();
		} catch (IOException e) {
			System.out.println("Error while checking Bukkit build numbers" + e.getMessage());
		}
		return null;
	}
	*/
	private static JTextField javaloc;
	private static JTextField jarloc;
	static TextArea output;
	static Process bukkitproc;
	static System s;
	final JFileChooser chooser = new JFileChooser();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Launch frame = new Launch();
					frame.setVisible(true);
					redirectSystemStreams(output);
					File directory = new File("../Launchers");
					if(!directory.exists()) {
						System.out.println("Directory 'Launchers' not found! Creating directory 'Launchers'");
						directory.mkdir();
						System.out.println("Minecraft.jar not found! Attempting to download to 'Launchers/Minecraft.jar'");
						downloadLauncher();
					}else{
						File c = new File("../Launchers/Minecraft.jar");
						if(!c.exists()) {
							System.out.println("Minecraft.jar not found! Attempting to download to 'Launchers/Minecraft.jar'");
							downloadLauncher();
						}
					}
					File s = new File("settings.properties");
					if(s.exists()) {
						loadSettings();
						
					}else{
						writeSettings();
					}
					System.out.println("Hello there!");
					System.out.println("To begin using Minecraft Portable click the 'Run Minecraft' checkbox and click the Start button!");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws URISyntaxException 
	 */
	public Launch() throws URISyntaxException {
		
		setForeground(Color.GREEN);
		setBackground(Color.BLACK);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setTitle("Minecraft Portable");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 559, 412);
		setResizable(false);
        
		content = new JPanel();
		content.setBorder(new EmptyBorder(5, 5, 5, 5));
		content.setLayout(new BorderLayout(0, 0));
		setContentPane(content);
		
		JPanel Main_panel = new JPanel();
		content.add(Main_panel, BorderLayout.CENTER);
		Main_panel.setLayout(null);
		final URI uri = new URI("http://github.com/jordanneil23");
		class OpenUrlAction implements ActionListener {
		      @Override public void actionPerformed(ActionEvent e) {
		    	  open(uri);
		      }
		    }
		
		JTabbedPane Main_pane = new JTabbedPane(JTabbedPane.TOP);
		Main_pane.setForeground(Color.BLACK);
		Main_pane.setBackground(Color.LIGHT_GRAY);
		Main_pane.setBounds(0, 0, 543, 374);
		Main_panel.add(Main_pane);
		
		JPanel main_tab = new JPanel();
		main_tab.setForeground(Color.BLACK);
		main_tab.setBackground(UIManager.getColor("Button.background"));
		Main_pane.addTab("Main", null, main_tab, null);
		main_tab.setLayout(null);
		
		username = new JTextField();
		username.setForeground(Color.BLUE);
		username.setBackground(Color.LIGHT_GRAY);
		username.setEnabled(false);
		username.setBounds(418, 246, 110, 20);
		main_tab.add(username);
		username.setToolTipText("This will not steal passwords!");
		username.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(348, 249, 60, 14);
		main_tab.add(lblUsername);
		lblUsername.setToolTipText("This will not steal passwords!");
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(348, 276, 60, 14);
		main_tab.add(lblPassword);
		lblPassword.setToolTipText("This will not steal passwords!");
		
		password = new JTextField();
		password.setForeground(Color.BLUE);
		password.setBackground(Color.LIGHT_GRAY);
		password.setEnabled(false);
		password.setBounds(418, 273, 110, 20);
		main_tab.add(password);
		password.setToolTipText("This will not steal passwords!");
		password.setColumns(10);
		
		JButton login = new JButton("Start");
		login.setBounds(348, 301, 76, 20);
		main_tab.add(login);
		login.setToolTipText("");
		
		final JCheckBox uselogin = new JCheckBox("Use login");
		uselogin.setEnabled(false);
		uselogin.setToolTipText("Enable or disable the auto-login feature.");
		uselogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(uselogin.isSelected() == true){
				password.setEnabled(true);
				username.setEnabled(true);
				}else{
					password.setEnabled(false);
					username.setEnabled(false);
					password.setText("");
					username.setText("");
				}
			}
		});
		uselogin.setBounds(443, 300, 85, 23);
		main_tab.add(uselogin);
		
		runmc = new JCheckBox("Run Minecraft");
		runmc.setToolTipText("Run Minecraft");
		runmc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(runmc.isSelected() == true){
					uselogin.setEnabled(true);
					}else{
						uselogin.setEnabled(false);
						password.setEnabled(false);
						username.setEnabled(false);
						password.setText("");
						username.setText("");
					}
			}
		});
		runmc.setBounds(418, 216, 110, 23);
		main_tab.add(runmc);
		
		javaloc = new JTextField();
		javaloc.setForeground(Color.BLUE);
		javaloc.setBackground(Color.LIGHT_GRAY);
		javaloc.setText((String) null);
		javaloc.setEditable(false);
		javaloc.setColumns(10);
		javaloc.setBounds(65, 217, 110, 20);
		main_tab.add(javaloc);
		
		JLabel lblJava = new JLabel("Java -");
		lblJava.setBounds(11, 220, 44, 14);
		main_tab.add(lblJava);
		
		JButton brwsjav = new JButton("Browse");
		brwsjav.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int answer = chooser.showOpenDialog(Launch.this);
				if (answer == JFileChooser.APPROVE_OPTION)
				{
				    File file = chooser.getSelectedFile();
				    javaloc.setText(file.getAbsolutePath());
				    jav = file.getAbsolutePath();
				    saveSettings();
				}
			}
		});
		brwsjav.setToolTipText("Select the java location");
		brwsjav.setBounds(185, 216, 89, 23);
		main_tab.add(brwsjav);
		
		JButton brwsjar = new JButton("Browse");
		brwsjar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int answer = chooser.showOpenDialog(Launch.this);
				if (answer == JFileChooser.APPROVE_OPTION)
				{
				    File file = chooser.getSelectedFile();
				    jarloc.setText(file.getAbsolutePath());
				    jr = file.getAbsolutePath();
				    saveSettings();
				}
			}
		});
		brwsjar.setToolTipText("Select the launcher Jar");
		brwsjar.setBounds(185, 245, 89, 23);
		main_tab.add(brwsjar);
		
		jarloc = new JTextField();
		jarloc.setForeground(Color.BLUE);
		jarloc.setBackground(Color.LIGHT_GRAY);
		jarloc.setText((String) null);
		jarloc.setEditable(false);
		jarloc.setColumns(10);
		jarloc.setBounds(65, 246, 110, 20);
		main_tab.add(jarloc);
		
		JLabel label_2 = new JLabel("Jar -");
		label_2.setBounds(11, 249, 33, 14);
		main_tab.add(label_2);
		
		JButton button = new JButton("<HTML><FONT color=\"#000099\"><U>Source Code</U></FONT></HTML>");
		button.setBorderPainted(false);
		button.setOpaque(false);
		button.setBackground(Color.WHITE);
		button.setToolTipText(uri.toString());
		button.addActionListener(new OpenUrlAction());
		button.setToolTipText("http://github.com/jordanneil23");
		button.setBounds(199, 323, 117, 23);
		main_tab.add(button);
		
		output = new TextArea();
		output.setForeground(Color.BLUE);
		output.setEditable(false);
		output.setBackground(Color.LIGHT_GRAY);
		output.setBounds(0, 0, 538, 207);
		main_tab.add(output);
		
		JTextPane txtpnMinecraftIsCopyright = new JTextPane();
		txtpnMinecraftIsCopyright.setText("Select your Launcher jar and Java jar (leave alone for default to be used) then check \"Run Minecraft.\" The login option is optional. (Thats why it's a option)\r\n\r\nDISCLAMER:\r\nMojang Copyright \u00A9 2009-2012. \"Minecraft\" is a trademark of Mojang\r\nJAVA is Copyright \u00A9 1993, 2012, Oracle and/or its affiliates. \r\nAll rights reserved.");
		Main_pane.addTab("Help", null, txtpnMinecraftIsCopyright, null);
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				login();
			}
		});
	}
	
	public void login(){
		//loadSettings();
		if(runmc.isSelected() == true){				
				System.out.println("Attempting to start Minecraft Launcher...");
				ArrayList<String> params2 = new ArrayList<String>();
				params2.add(javaloc.getText());
				params2.add("-Xmx1024m");
		        params2.add("-Xms512M");
				params2.add("-jar"); 
				params2.add(jarloc.getText());
				params2.add(username.getText());
		        params2.add(password.getText());
				ProcessBuilder pb2 = new ProcessBuilder(params2);
				try {
					pb2.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			System.out.println("Finished running Minecraft! Thanks for using my software!");
		}else{
			System.out.print("Nothing was selected to run...!");
		}
	}
	
	public static void downloadLauncher() throws IOException {
		URL url = new URL("https://s3.amazonaws.com/MinecraftDownload/launcher/minecraft.jar");
		BufferedInputStream in = new BufferedInputStream(url.openStream());
		FileOutputStream fos = new FileOutputStream("Launchers/Minecraft.jar");
		BufferedOutputStream bout = new BufferedOutputStream(fos,1024);
		byte data[] = new byte[1024];
		int count;
		while((count = in.read(data,0,1024)) != -1)
		{
		bout.write(data,0,count);
		}
		bout.close();
		in.close();
	}
	
	 static void updateTextArea(final String text, final TextArea t) {
		    SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		    	 t.append(text);
		      }
		    });
		  }

		  static void redirectSystemStreams(final TextArea t) {
		    OutputStream out = new OutputStream() {
		      @Override
		      public void write(int b) throws IOException {
		        updateTextArea(String.valueOf((char) b), t);
		      }

		      @Override
		      public void write(byte[] b, int off, int len) throws IOException {
		        updateTextArea(new String(b, off, len), t);
		      }

		      @Override
		      public void write(byte[] b) throws IOException {
		        write(b, 0, b.length);
		      }
		    };
		    
		    

		    System.setOut(new PrintStream(out, true));
		    System.setErr(new PrintStream(out, true));
		  }
		  
		    public static void loadSettings() {
		    	try {
		    		//System.out.print("Loading settings... ");
		    		Properties props = new Properties();
		    		props.load(new FileReader("settings.properties"));
		    		jr = props.getProperty("Jar_Location");
		    		jav = props.getProperty("Java_Location");
		    		jarloc.setText(jr);
		    		javaloc.setText(jav);
		    		//System.out.print("Done!");
		    		//System.out.println();
		    	} catch (IOException ex) {
		    		System.out.println("Unable to load the settings!");
		    	}
		    }
		    
		    public static void saveSettings() {
	        	//System.out.print("Saving settings... ");
	    		Properties props = new Properties();
	    		props.setProperty("Jar_Location", jr);
	    		props.setProperty("Java_Location", jav);
	    		try {
	    			props.store(new FileOutputStream("settings.properties"), null);
	    			loadSettings();
	    		} catch (FileNotFoundException e) {
	    			System.out.println("FileNotFoundException while saving settings");
	    		} catch (IOException e) {
	    			System.out.println("IOException while saving settings");
	    		}		
	    	}
		    
		        public static void writeSettings() {
		        	System.out.print("Writing default settings... ");
		    		Properties props = new Properties();
		    		props.setProperty("Jar_Location", "Launchers\\Minecraft.jar");
		    		props.setProperty("Java_Location", "Java\\bin\\java.exe");
		    		try {
		    			props.store(new FileOutputStream("settings.properties"), null);
		    			System.out.print("Done!");
		    			System.out.println();
		    		} catch (FileNotFoundException e) {
		    			System.out.println("FileNotFoundException while saving settings");
		    		} catch (IOException e) {
		    			System.out.println("IOException while saving settings");
		    		}		
		    	}
		        
		        public void done() {
		            done = true;
		            Toolkit.getDefaultToolkit().beep();
		            setCursor(null);
		        }
		        
		        private static void open(URI uri) {
		            if (Desktop.isDesktopSupported()) {
		              try {
		                Desktop.getDesktop().browse(uri);
		              } catch (IOException e) { System.out.println(e.toString()); }
		            } else { System.out.println("Can not open link, Java Desktop is not supported!"); }
		          }
}
