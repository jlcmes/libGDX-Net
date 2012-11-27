package com.jlcm.servidor;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = false; //true;
        cfg.useAccelerometer = false; // to save battery
        cfg.useCompass = false;
        
        initialize(new JAVAServer(JAVAServer.platformCode.ANDROID), cfg);
    }
}