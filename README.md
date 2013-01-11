libGDXNet
=========

Extension to provide network / multiplayer support for libGDX multiplatform games. 
It can be used in Java applications too to provide Websocket support by creating Websockets based applications.

Here you can watch a Demo of a prototype using libGDXNet:
http://www.youtube.com/watch?v=DpluzKKMNtI

This project has been made as a part of my Final Degree Project "Design and coding of a collaborative multiplatform system with shared workspace" (in Spanish: "Diseño e implementación de un sistema colaborativo multidispositivo con espacio de trabajo compartido") at the University of Castilla La-Mancha (Spain).
Visit http://www.esiiab.uclm.es/ for more details.

This project uses the following libraries:
http://code.google.com/p/gwt-ws/

https://github.com/TooTallNate/Java-WebSocket

Thanks to the authors of these libraries.

Thanks to my teacher, Victor Manuel López Jaquero for his support.

Try it yourself!
================

I made a playing card game multiplatform and multiplayer using libGDX-Net.

It's called CARDma. It's available from Google Play Store and http://cebrianbrothers.blogspot.com/2013/01/cardma-beta.html.

You can download it for free.

It's only available in Spanish, but the gaming process is simple:
1. Download and Start "CARDma Servidor" (CARDma Server). Click on "Iniciar Servidor" (Start Server). Click on "OK". Wait for players.
2. Donwload and Start "CARDma Cliente" (CARDma Client). Click on "Iniciar Cliente" (Start Client). Select Avatar and click on "OK". Write the IP of the server and click OK.
3. Click on "Iniciar" (Start button) to start the game. Enjoy!

From 2 to 6 players can play together!.
(BETA Version, please post a comment on http://cebrianbrothers.blogspot.com/2013/01/cardma-beta.html if you found any bug).
Thanks.

How to use it
=============

There are different classes for Java (Android/Desktop) and HTML5 (GWT)

SERVER (ONLY FOR JAVA ANDROID - DESKTOP)

WSServer class -> Creates a server and wait for client connections. It returns the address of the IP Server. It assigns an unique ID to every client. It checks if it's listening and gets the number of clients currently connected. The server can send packets to any client connected, to All clients at the same time (broadcast), and disconnect clients.

ServerMSG class -> Sends and receives messages from the server by using WSServer class.

CLIENT (FOR ANDROID - DESKTOP - HTML5)

WSClient class -> Creates a client Websocket to the server. It gets an unique ID. It can send packets only to the server and check if it is currently connected.

GWTClient class -> Same as WSClient but for GWT.

ClientMSG class -> Sends and receives messages to/from the server by using either WSClient or GWTClient.

Card example
============

In the card example, you can test libGDXNet with different configurations. You can change in the JAVAClient and HTML5Client classes the IP to connect to a different IP, not only localhost.
The HTML5 version has all these methods on the "prototipo-html" project, because Java-WebSocket is not compatible with GWT.
I modified the Entry class in all the versions too to report to all the classes the platform being used.

All the code is documented. Read it before asking anything, please.

Is my first project on GitHub, it might have some errors. Please don't kill me for that.

Thanks.
