package com.jlcm.prototipo.client;

public interface ComClient
{
		public void connectClient (String ip);
		
		public boolean sendMsg(String msg);

		public boolean isConnected();

		public int getId();
		
		public void close();
}