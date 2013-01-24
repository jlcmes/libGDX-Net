package com.jlcm.prototipo;

import java.net.URI;
import java.net.URISyntaxException;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17; //This is the Standard WebSocket Implementation
import org.java_websocket.handshake.ServerHandshake;

public class WSClient implements ComClient 
{	
	private int port;
	private WebSocketClient wsc; //Websocket
	private boolean connected;
	
	//For the Client side only
	private int myID;
	private ClientMSG c;
	
	//For Bidirectional Communication mode
	public WSClient (String ip, int port, ClientMSG c, JAVAClient.platformCode pC)
	{
		this.port = port;
		connected = false;
		this.connectClient(ip);
		myID = -1;
		this.c = c; //To call the methods of the the upper level class
	}
	
	public void connectClient (String ip)
	{
		if (!ip.isEmpty())
		{
			//Websocket implementation
			URI url = null; //URI (url address of the server)
			try {
				url = new URI("ws://"+ ip +":"+ port); //We create the URI of the server. Use a port upper than 1024 on Android and Linux!
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} 

			//We select the standard implementation of WebSocket
			Draft standard = new Draft_17(); 

			wsc = new WebSocketClient( url, standard) {

				@Override
				public void onOpen( ServerHandshake handshake ) {
					connected = true;
					requestID();
				}
				
				@Override
				public void onMessage( String message ) {
					
					//Low level control of Messages received from server
					//SERVER CLOSES MY WS CONNECTION.
					if (message.equals("MSG_CLOSE_WS")) 
					{
						this.close(); 
					}

					//SERVER SEND MY CLIENT ID.
					else if (message.startsWith("MSG_SEND_ID"))
					{
						String [] values = message.split("\\s+"); //splitter with the " " separator
						myID = Integer.valueOf(values[1]);	
					}
					//High level Message, send to the ClientMSG class
					else
					{
						c.onMessage(message);		
					}	
				}

				@Override
				public void onError( Exception ex ) {
					System.out.println("WSClient Error.");
				}
				
				@Override
				public void onClose( int code, String reason, boolean remote ) {
					connected = false;
				}
			};
			wsc.connect(); //And we create the connection between client and server
		}
	}

	private void requestID()
	{
		sendMsg("MSG_REQUEST_ID");
	}
	
	public boolean sendMsg(String msg)
	{
		if (connected)
		{
			wsc.send(msg);
			return true;
		}
		else return false;
	}

	public boolean isConnected()
	{
		return connected;
	}

	public int getId()
	{
		return myID;
	}
	
	public void close()
	{
		wsc.close();
		connected = false;
	}
	

}
