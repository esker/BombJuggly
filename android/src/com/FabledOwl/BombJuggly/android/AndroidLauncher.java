package com.FabledOwl.BombJuggly.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.buffbeans.tail.Tail;
import com.FabledOwl.BombJuggly.BombJuggly;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new BombJuggly(), config);
		Tail.getInstance(this).start("e746e573-8559-40d0-b53c-0ec7995f00df");
		
	}
}
