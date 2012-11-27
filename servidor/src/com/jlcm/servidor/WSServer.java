package com.jlcm.servidor;

//From the Java_websocket implementation (Server side)
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class WSServer
{
	//private static int DEFAULT_SERVER_PORT = 80;
	private int port;
	private WebSocketServer wss; //Websocket
	private boolean isReady;
	
	//For the Server side only
	private List<Connection> clientSockets; //To save all the current WebSocket open connections
	private int nClients; //Number of clients connected to our server
	private int clientId; //To create a unique Id for each client (never decreased)
	
	private ServerMSG s;
	JAVAServer.platformCode pC;
	
	//For Bidirectional Communication mode
	public WSServer (int port, ServerMSG s, JAVAServer.platformCode pC)
	{
		this.port = port;
		isReady = false;
		nClients = 0;
		clientId = 0;
		clientSockets = new ArrayList<Connection>();
		startServer();
		this.pC = pC;
			System.out.println("Server IP: "+this.getIP());
		this.s = s; //To call the methods of the the upper level class		
	}
	
	public void startServer ()
	{
		//Here we must create the server and all the behavior for the messages received by the clients
		wss = new WebSocketServer( new InetSocketAddress(port)) {
			
			@Override
			public void onOpen(WebSocket arg0, ClientHandshake arg1) 
			{
				System.out.println("Handshake :"+arg1.toString());
			}
			
			@Override
			public void onMessage(WebSocket arg0, String arg1) {

				System.out.println("Server receives:  "+arg1+" "+arg0.getRemoteSocketAddress());
				
				if (arg1.equals("MSG_REQUEST_ID"))
				{
					//SERVER SEND THE CLIENT ID AND REGISTER A NEW CONNECTION
					clientSockets.add(new Connection(arg0, clientId));  
					
					arg0.send("MSG_SEND_ID " + clientId);
						System.out.println("Server sent MSG_SEND_ID "+clientId);
					clientId++;
					nClients++;
				}
				else s.onMessage(arg1); //High level message
			}
			
			@Override
			public void onError(WebSocket arg0, Exception arg1) {
				// TODO Auto-generated method stub
				System.out.println("Server Error "+arg1);
			}
			
			@Override
			public void onClose(WebSocket arg0, int arg1, String arg2, boolean arg3) {

				closeConnection(arg0);
			}
		};
		
		wss.start(); //Start Server functionality
		isReady = true;
			System.out.println("Server started and ready.");
	}

	public boolean isListening()
	{
		return isReady;
	}
	
	public void sendToAll (String text) 
	{
		synchronized (clientSockets)
		{
			for ( Connection c : clientSockets )
			{
				if (c.getWS().isOpen()) c.getWS().send( text );
				System.out.println("Server send to all:"+c.getWS().isOpen() +"  "+text);
				//Only we must send the message if the WS is Open.
			}			
		}
	}
	
	public boolean sendToClient (int ID, String text) //not tested already
	{	
		for ( Connection c : clientSockets )
		{
			if (c.getID() == ID)
			{
				c.getWS().send( text );
				System.out.println("Server send to: "+ ID +" "+text);
				return true;
			}
		}
		return false;	
	}
	
	private boolean closeConnection (WebSocket ws)
	{
		int i = 0, clientToDelete = 0, clientID = 0;
		boolean found = false;
		
		if (nClients == 0) return found; //0 Clients
		
		synchronized (clientSockets)
		{	
			for ( Connection c : clientSockets )
			{
				if (!found &&(c.getWS().hashCode() == ws.hashCode())) //There are the same WebSocket
				{
					clientToDelete = i; //We can't delete the connection here, ConcurrentException!
					clientID = c.getID();
					found = true;
				}
				i++;
			}
		}
		
		if (found) 
		{
			clientSockets.remove(clientToDelete);
			nClients--;			
			System.out.println("Client "+ clientID +" disconnected. "+nClients+" clients connected.");
			
		}	
		return found;
	}
	
	public int nClients ()
	{
		return nClients;
	}
	
	public void dropAllClients()
	{
		this.sendToAll("MSG_CLOSE_WS");
	}
	
	public void stop()
	{
		dropAllClients();
		
		try {
			wss.stop();
			System.out.println("Server Stopped.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		isReady = false;
	}
	
	public String getIP()
	{	
		if (pC == JAVAServer.platformCode.ANDROID) //Only for Android devices.
		{
			//This code works only for android devices
			//Thanks to: http://www.droidnova.com/get-the-ip-address-of-your-device,304.html
		    try {
		        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
		            NetworkInterface intf = en.nextElement();
		            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
		                InetAddress inetAddress = enumIpAddr.nextElement();
		                if (!inetAddress.isLoopbackAddress()) {
		                    return inetAddress.getHostAddress().toString();
		                }
		            }
		        }
		    } catch (SocketException ex) {
		        System.out.println("Not an android!!!");
		    }
		}
		else //Only for Desktop version
		{
			InetAddress thisIp;
			try {
				thisIp = InetAddress.getLocalHost();

				return (thisIp.getHostAddress().toString());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ("127.0.0.1");
		}
		return ("127.0.0.1");
	}
}

class Connection
{
	WebSocket ws;
	int clientID;
	
	public Connection (WebSocket ws, int ID)
	{
		this.ws = ws;
		this.clientID = ID;
	}
	
	public int getID()
	{
		return clientID;
	}
	
	public WebSocket getWS()
	{
		return ws;
	}
}