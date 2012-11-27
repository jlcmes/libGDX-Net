package com.jlcm.prototipo;

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
        
        initialize(new JAVAClient(JAVAClient.platformCode.ANDROID), cfg);
    }
}