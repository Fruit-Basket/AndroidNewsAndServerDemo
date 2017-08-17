package com.example.mynews;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connection {
	
	public final static String SERVER_IP="192.168.0.100";
	//public final static String SERVER_IP="10.0.2.2";
	public final static int SERVER_PORT=8000;
	public final static String SERVER_URL_STRING=SERVER_IP+String.valueOf(SERVER_PORT);
	
	private Connection(){}
	
	
	public static Socket connectToServer(String hostAddress,int port) 
			throws UnknownHostException, IOException{
		
		Socket socket=new Socket(hostAddress,port);
		
		return socket;
	}
}
