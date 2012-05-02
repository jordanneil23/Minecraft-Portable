package com.jordanneil23;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class popup1 {

	JFrame frame;
	static TextArea info;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					popup1 p = new popup1();
					p.frame.setVisible(true);
					redirectSystemStreams(info);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public popup1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Downloading Bukkit!");
		frame.setBounds(100, 100, 391, 191);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		info = new TextArea();
		info.setEditable(false);
		info.setText("Downloading Bukkit.\r\nThis may take a while depending on your internet connection. \r\nPlease be paitent!\r\nYou should hear a ding or beep when done...");
		info.setForeground(Color.GREEN);
		info.setBackground(Color.BLACK);
		info.setBounds(0, 0, 385, 167);
		frame.getContentPane().add(info);
	}
	
	 static void updateTextArea(final String text, final TextArea t) {
		    SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		    	 t.append(text);
		      }
		    });
		  }

		  private static void redirectSystemStreams(final TextArea t) {
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
}
