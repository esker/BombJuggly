package com.FabledOwl.BombJuggly;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.FabledOwl.BombJuggly.screens.MainGame;
import com.FabledOwl.BombJuggly.screens.MainMenu;
import com.FabledOwl.BombJuggly.screens.SplashScreen;


public class BombJuggly extends Game {
	public static final String VERSION = "0.0.0.01 Pre-Alpha";
	public static final String LOG = "BombJuggly";	
	
	public static final int GAME_WIDTH = 320;
	public static final int GAME_HEIGHT = 480;
		
	private boolean running = false;
	public SpriteBatch batch;
	
	
	
	public void create() {		
		batch = new SpriteBatch();
		this.setScreen(new SplashScreen(this)); //change this to splash screen later
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}