import java.io.*;
import java.net.*;
import java.util.*;

public class AppServer extends Thread 
{
	private ServerSocket serverSocket;

	private ServerFrame sFrame;

	private static Vector userOnline = new Vector(1, 1);

	private static Vector v = new Vector(1, 1);

	/*
	 * Build a server and start to monitor port 1001
	 */
	public AppServer() 
	{
		sFrame = new ServerFrame();
		try {
			// Get IP address and host name for this server
			serverSocket = new ServerSocket(1001);
			InetAddress address = InetAddress.getLocalHost();
			sFrame.txtServerName.setText(address.getHostName());
			sFrame.txtIP.setText(address.getHostAddress());
			sFrame.txtPort.setText("1001");
		} catch (IOException e) {
			fail(e, "Failed to start");
		}
		sFrame.txtStatus.setText("Starting...");
		this.start(); // start thread
	}
    /*
     *  Exit server
     */
	public static void fail(Exception e, String str) {
		System.out.println(str + " ¡£" + e);
	}

	/*
	 * Monitor request from client and make a connection when the client request
	 */
	public void run() {
		try {
			while (true) 
			{
				Socket client = serverSocket.accept();
				new Connection(sFrame, client, userOnline, v); 
			}
		} catch (IOException e) 
		{
			fail(e, "Failed to monitor!");
		}
	}

	/*
	 * Start server
	 */
	public static void main(String args[]) 
	{
		new AppServer();
	}
}
