package assignment3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
	 public static void main(String[] args) throws IOException {
		 ServerSocket serverSocket = null;
		 DataInputStream clientData = null;
		 Socket clientSocket = null;
		 String fileName;
		 while(true){
	        try {
	            serverSocket = new ServerSocket(12345);
	        } catch (IOException ex) {
	            System.out.println("Can't setup server on this port number. ");
	        }
	        Socket socket = null;
	        InputStream in = null;
	        OutputStream out = null;
	        try {
	            socket = serverSocket.accept();
	        } catch (IOException ex) {
	            System.out.println("Can't accept client connection. ");
	        }
	        try {
	            in = socket.getInputStream();
	            clientData = new DataInputStream(in); 
	        } catch (IOException ex) {
	            System.out.println("Can't get socket input stream. ");
	        }	 	        
	        fileName = clientData.readUTF() + ".c";
	        //create the file
	        try {
	            File file = new File(fileName);
	            if(file.createNewFile())
	                System.out.println("File creation successfull" + fileName);
	            else
	                System.out.println("Error while creating File, file already exists in specified path");
	        }
	        catch(IOException io) {
	            io.printStackTrace();
	        }
	        try 
	        {
	            Thread.sleep(1000);
	        } 
	        catch(InterruptedException e)
	        {
	        	 System.out.println("Sleep failed");
	        }
	        //write to file
	        try {
	            out = new FileOutputStream(fileName);
	        } catch (FileNotFoundException ex) {
	            System.out.println("File not found. ");
	        }
	        byte[] bytes = new byte[16*1024];
	        int count;
	        System.out.println("File creation successfull132143243543");
	        while ((count = in.read(bytes)) > 0) {
	            out.write(bytes, 0, count);
	        }
	        //write to file end
	        //compile and run
	        compileCFile(fileName);
	        ////////////////////
	        try 
	        {
	            Thread.sleep(1000);
	        } 
	        catch(InterruptedException e)
	        {
	        	 System.out.println("Sleep failed");
	        }
	        /////////////////
	        PrintStream ps = new PrintStream(socket.getOutputStream());
	        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        fileReader(fileName,"testcase1.txt");
	        ps.print(br.readLine());
	        clientData.close();
	        out.close();
	        in.close();
	        socket.close();	 
	        serverSocket.close();
		 }

	 }
	static void compileCFile(String fileName)
    {
		String fileNameDir = "/home/alam11a/" + fileName;
        String compileFileCommand = "cc " + fileNameDir + " -o " + fileName.split(".c")[0];
        try
        {
            System.out.println("Compiling C File");
            Process processCompile = Runtime.getRuntime().exec(compileFileCommand);
            
            BufferedReader brCompileError = new BufferedReader(new InputStreamReader(processCompile.getErrorStream()));
            String errorCompile = brCompileError.readLine();
            if (errorCompile != null)
                System.out.println("Error Compiler = " + errorCompile);
            	
            BufferedReader brCompileRun = new BufferedReader(new InputStreamReader(processCompile.getErrorStream()));
            String outputCompile = brCompileRun.readLine();
            if (outputCompile != null)
                System.out.println("Output Compiler = " + outputCompile);
            
            

        } catch (Exception e)
        {
            // TODO: handle exception
            System.out.println("Exception ");
            System.out.println(e.getMessage());
        }
    }
	static String runCFile(String ... args)
	{
	String fileName = args[0];
		String runFileCommand = ("./" + fileName.split(".c")[0]);
			  try{
		            System.out.println("Running C File");
		            Process processRun = Runtime.getRuntime().exec(runFileCommand);
		            OutputStream stdin = processRun.getOutputStream ();
		            String line =  args[1] + "\n";		          
		            
		            stdin.write(line.getBytes());
		            stdin.flush();		            
		            if (args.length == 3){
		            	line =  args[2] + "\n";
		            	stdin.write(line.getBytes());
		            	stdin.flush();
		            }
		            stdin.close();
		            
		            BufferedReader brRun = new BufferedReader(new InputStreamReader(processRun.getErrorStream()));
		            String errorRun = brRun.readLine();
		            if (errorRun != null)
		                System.out.println("Error Run = " + errorRun);

		            BufferedReader brResult = new BufferedReader(new InputStreamReader(processRun.getInputStream()));
		            String outputRun = brResult.readLine();
		            if (outputRun != null)
		                return outputRun;
   
		        } catch (Exception e)
		        {
		            // TODO: handle exception
		            System.out.println("Exception ");
		            System.out.println(e.getMessage());
		        }
			return "";  
		}	
	static String fileReader(String cFile, String testFile){
		String finalString = cFile + "`";
		String FINALOUTPUT = "";
		server server = new server();
		try (BufferedReader br = new BufferedReader(new FileReader(testFile)))
		{
			String sCurrentLine;
			String testOutput = null;
			while ((sCurrentLine = br.readLine()) != null) {
				if(testOutput != null){	
					System.out.println(sCurrentLine + "=" + testOutput + "\n");
					FINALOUTPUT += sCurrentLine + "=" + testOutput;
					testOutput = null;
					sCurrentLine = "#";
				}
				if(!sCurrentLine.equals("*") && !sCurrentLine.equals("#")){
					finalString += sCurrentLine + "`";
				}
				else if(sCurrentLine.equals("*")){
					String[] x  =  finalString.split("`");
					String output = runCFile(x);
					if(output != null){
						testOutput = output;
						finalString = cFile + "`";
					}				
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return FINALOUTPUT;
	}
}





