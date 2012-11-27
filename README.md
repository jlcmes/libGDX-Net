libGDXNet
=========

Extension to provide network / multiplayer support for libGDX multiplatform games. 
Can be used too in Java applications to provide Websocket support creating Websockets based applications.

Here you can watch a Demo of a prototype using libGDXNet:
http://www.youtube.com/watch?v=DpluzKKMNtI

This project has been made as a part of my Final Degree Project "Design and coding of a collaborative multiplatform system with workspace" (in Spanish: "Diseño e implementación de un sistema colaborativo multidispositivo con espacio de trabajo compartido") of the University of Castilla La-Mancha (Spain).
Visit http://www.esiiab.uclm.es/ for more details.

This project uses the next libraries:
http://code.google.com/p/gwt-ws/
https://github.com/TooTallNate/Java-WebSocket
Thanks to the authors of this libraries.

Thanks to my teacher, Victor Manuel López Jaquero for his support.

How to use it
=============

There are different classes for Java (Android/Desktop) and HTML5 (GWT)

SERVER (ONLY FOR JAVA ANDROID - DESKTOP)

WSServer -> Create a server and wait for client connections. Returns IP Server. Assign an unique ID to the clients. Check if it's listening and get the number of clients connected. The server can send packets to any client, to All, and disconnect clients.

ServerMSG -> Send and receive messages from the server using WSServer.

CLIENT (FOR ANDROID - DESKTOP - HTML5)

WSClient -> Create a client Websocket to the server. Get an unique ID. Can send packets to the server. Check if it's connected.

GWTClient -> Same that WSClient but for GWT.

ClientMSG -> Send and receive messages to the server using WSClient or GWTClient.

Card example
============

In the card example, you can test libGDXNet with different configurations. You can change in the JAVAClient and HTML5Client class the IP to connect to a different IP, not only localhost.
The HTML5 version has all these methods on the "prototipo-html" project, because Java-WebSocket is not compatible with GWT.
I modified too the Entry class in all the versions to inform all the classes the platform that I'm using.

All the code is documented. Read it before asking anything, please.

Is my first project on GitHub, it will have a lot of errors. Please don't kill me for that.

Thanks.
