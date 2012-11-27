package com.jlcm.prototipo.client;

import com.google.gwt.core.client.JavaScriptException;
import net.zschech.gwt.websockets.client.CloseHandler;
import net.zschech.gwt.websockets.client.ErrorHandler;
import net.zschech.gwt.websockets.client.MessageEvent;
import net.zschech.gwt.websockets.client.MessageHandler;
import net.zschech.gwt.websockets.client.OpenHandler;
import net.zschech.gwt.websockets.client.WebSocket;

public class GWTClient implements ComClient
{	
	//private static int DEFAULT_SERVER_PORT = 80;
	private int port;
	private WebSocket wsc; //Websocket to connect to a remote server
	private boolean connected;

	//For the Client side only
	private int myID;
	private ClientMSG c;
	
	//For Bidirectional Communication mode	
	public GWTClient (String ip, int port, ClientMSG c)
	{
		this.port = port;
		this.connected = false;
		this.connectClient(ip);
		myID = -1;
		this.c = c; //To call the methods of the the upper level class
	}	
	
	public void connectClient (String ip)
	{

		if (!ip.isEmpty())
		{
			//We create the URI in String format
			String url = null; //URI (url address of the server)
			url = new String("ws://"+ ip +":"+ port);
			
			try
			{
				wsc = WebSocket.create(url); //"ws://echo.websocket.org" //For the echo testing server

				//Handler methods override the original methods for the webSocket functionality
				wsc.setOnOpen(new OpenHandler()
				{
					@Override
					public void onOpen(WebSocket webSocket)
					{
						connected = true;
						requestID();
					}
				});

				wsc.setOnMessage(new MessageHandler()
				{
					@Override
					public void onMessage(WebSocket webSocket, MessageEvent event)
					{	
						String message = event.getData(); //Different implementation respect WSClient 
						
						//Low level control of Messages received from server
						//SERVER CLOSES MY WS CONNECTION.
						if (message.equals("MSG_CLOSE_WS")) 
						{
							wsc.close();
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
				});
				
				wsc.setOnError(new ErrorHandler()
				{
					@Override
					public void onError(WebSocket webSocket)
					{
						System.out.println("GWTClient Error.");
					}
				});				
				
				wsc.setOnClose(new CloseHandler()
				{
					@Override
					public void onClose(WebSocket webSocket)
					{
						connected = false;
					}
				});
			}
			catch (JavaScriptException e) { }
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