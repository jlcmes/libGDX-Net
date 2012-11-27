package com.jlcm.servidor;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "servidor";
		cfg.useGL20 = false; //true; //updated to support non powers of two sized images
		cfg.width = 800;
		cfg.height = 480;
		
		new LwjglApplication(new JAVAServer(JAVAServer.platformCode.DESKTOP), cfg);
	}
}
