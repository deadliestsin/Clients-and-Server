package assignment3;


import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JFrame; 

public class client {
	static String host = "127.0.0.1";
	static int port = 12345;
	public static void main(String[] args) throws IOException  {
		// TODO Auto-generated method stub
			Scanner reader = new Scanner(System.in);  // Reading from System.in
			String testCase = "1";
			String testCase2 = "2";
				System.out.println("Input your file name");
				String file = reader.next();
				System.out.println("Input your name");
				String name = reader.next();				
				connect(file, name);				

}
	
	public static void connect(String filename, String name) throws IOException{	 	
			Socket socket = null;			
			socket = new Socket(host, port);
		
			File file2 = new File(filename);
			InputStream in = new FileInputStream(file2);
	        OutputStream out = socket.getOutputStream();	      
	        DataOutputStream dos = new DataOutputStream(out);
	        long length = file2.length();
	        byte[] bytes = new byte[16 * 1024];	        	        	        
	        dos.writeUTF(name);
	        dos.flush();
	        int count;
	        while ((count = in.read(bytes)) > 0) {
	            out.write(bytes, 0, count);
	        }
	        in.close();
	        out.close();	        	        
	        socket.close();		
	}
	}
















