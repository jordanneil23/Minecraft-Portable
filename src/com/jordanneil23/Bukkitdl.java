package com.jordanneil23;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bukkitdl {
	
	private static final String JENKINS_URL = "http://ci.bukkit.org/job/dev-CraftBukkit/promotion/latest/Recommended/";
	public static int currentBuildNumber = -1;
	public static int serverBuildNumber = -1;
	
	public Bukkitdl() throws IOException {
			serverBuildNumber = getServerBuildNumber();
			currentBuildNumber = getCurrentBuildNumber();
	}
	
	public static int getCurrentBuildNumber() throws IOException {
	    URL url = new URL(JENKINS_URL);
	    URLConnection urlConnection = url.openConnection();
	    HttpURLConnection connection = null;
	    connection = (HttpURLConnection) urlConnection;
	    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    String current;
	    while ((current = in.readLine()) != null) {
	    	if(current.contains("dev-CraftBukkit #")) {
	    	    String txt = current;
	    	    String re1=".*?";
	    	    String re2="(#)";
	    	    String re3="(\\d+)";
	    	    
	    	    Pattern p = Pattern.compile(re1+re2+re3,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	    	    Matcher m = p.matcher(txt);
	    	    if (m.find())
	    	    {
	    	        String int1=m.group(2);
	    	        return Integer.parseInt(int1.toString());
	    	    }
	    		break;
	    	}
	    }
	    return -1;
	}
	
	public String getCurrentJarNumber() throws IOException {
	    URL url = new URL("http://ci.bukkit.org/job/dev-CraftBukkit/" + getCurrentBuildNumber() + "/");
	    URLConnection urlConnection = url.openConnection();
	    HttpURLConnection connection = null;
	    connection = (HttpURLConnection) urlConnection;
	    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    String current;
	    while ((current = in.readLine()) != null) {
	    	if(current.contains("1.0.1")) {
	    	    String txt = current;
	    	    String re1=".";
	    	    
	    	    Pattern p = Pattern.compile(re1,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	    	    Matcher m = p.matcher(txt);
	    	    if (m.find())
	    	    {
	    	        //String int1=m.group();
	    	        return "1.0.1";
	    	    }
	    		break;
	    	}
	    }
	    return "-1";
	}
	
	public int getServerBuildNumber() {
		File directory = new File("Bukkit");
		if(!directory.exists()) {
			directory.mkdir();
		} else {
			File c = new File("Bukkit/curversion.txt");
			if(c.exists()) {
				try {
					BufferedReader br = new BufferedReader(new FileReader(c));
					String b = "";
					String n = "";
					while((n = br.readLine()) != null) {
						b = n;
					}
					return Integer.parseInt(b);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					c.createNewFile();
					update();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return -1;
	}
	
	public void update() throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter("Bukkit/curversion.txt"));
		bw.write(""+currentBuildNumber);
		bw.close();
	}
	
	public void downloadLatestBuild() throws IOException {
		URL url = new URL("http://ci.bukkit.org/job/dev-CraftBukkit/" + getCurrentBuildNumber() + "/artifact/target/craftbukkit-" + getCurrentJarNumber() + "-R1.jar");
		BufferedInputStream in = new BufferedInputStream(url.openStream());
		FileOutputStream fos = new FileOutputStream("Bukkit/CraftBukkit.jar");
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

}
