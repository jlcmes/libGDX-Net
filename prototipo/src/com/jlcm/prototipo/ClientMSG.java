package com.jlcm.prototipo;

public class ClientMSG
{
	ComClient cc;
	JAVAClient c;
	
	//For Bidirectional Communication mode
	public ClientMSG(String ip, int port, JAVAClient c, JAVAClient.platformCode pc)
	{
		if (pc == JAVAClient.platformCode.HTML5)
		{
			//Only available on the HTML project - ClientMSG class
			//cc = new GWTClient(ip, port, this);
		}
		else
		{
			//Only available on the JAVA-ANDROID project
			cc = new WSClient(ip, port, this, pc);
		}
		this.c = c; //To call the methods of the the upper level class	
	}


	public void onMessage(String message)
	{
		
		String [] values = message.split("\\s+"); //splitter with the " " separator
		
		//int ClientID = Integer.valueOf(values[0]); //Check of the ID (not required)			
		
		//Calls to the upper level class methods
		if (values[0].equals("POSITION")) //POSITION player_id pos_x pos_y
		{
			c.changePosition(Integer.valueOf(values[1]), Integer.valueOf(values[2]), Integer.valueOf(values[3]));
		}
	}


	public boolean sendMessage(String message)
	{		
		if (cc != null && cc.isConnected())
		{
			//Only send message
			return (cc.sendMsg(message));
		}	
		else return false; 
	}
	
	public int getId()
	{
		return (cc.getId());
	}
	
	//get name from client class
	
	//one method for each messages / actions that the client can do
	
	public void close()
	{
		cc.close();
	}
}