package com.jlcm.servidor;

public class ServerMSG
{
	WSServer wss;
	JAVAServer s;
	
	//For Bidirectional Communication mode	
	public ServerMSG(int port, JAVAServer s, JAVAServer.platformCode pC)
	{
		wss = new WSServer(port, this, pC);
		this.s = s; //To call the methods of the the upper level class			
	}
	
	//Please, read Message codes in the ClientMSG comments.
	public void onMessage(String message)
	{
		
		String [] values = message.split("\\s+"); //splitter with the " " separator
		
		//int ClientID = Integer.valueOf(values[0]); //Check of the ID (not required)			
		
		//Calls to the upper level class methods
		if (values[0].equals("POSITION")) //POSITION player_id pos_x pos_y
		{
			s.changePosition(Integer.valueOf(values[1]), Integer.valueOf(values[2]), Integer.valueOf(values[3]));
		}
	}
	
	//Please, read Message codes in the ClientMSG comments.
	public boolean sendMessageToClient(int clientID, String message)
	{		
		return (wss.sendToClient(clientID, message));
		 
	}
	
	public void sendMessageToAll(String message)
	{
		wss.sendToAll(message);
	}
	
	//one method for each messages / actions that the server can do
	
	public void close()
	{
		wss.stop();
	}
}