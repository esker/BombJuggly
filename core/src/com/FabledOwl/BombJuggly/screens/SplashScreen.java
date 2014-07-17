package com.FabledOwl.BombJuggly.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.FabledOwl.BombJuggly.BombJuggly;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SplashScreen implements Screen {
	Texture splashTexture;
	Sprite splashSprite;
	SpriteBatch batch;
	BombJuggly game;
	TweenManager manager;

	public SplashScreen(BombJuggly game){
		//Texture.setEnforcePotImages(false);
		this.game = game;
		splashTexture = new Texture("data/FabledOwl_Page.png");// set texture to the splash screen picture
		splashTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear); // set texture filters
		
		splashSprite = new Sprite(splashTexture); // creates a sprite of our splash logo picture
		splashSprite.setColor(1, 1, 1, 0);//sets the background color of the sprite (this is black)
		splashSprite.setX(Gdx.graphics.getWidth() / 2 - (splashSprite.getWidth() / 2));//centers the sprites x axis to the screen
		splashSprite.setY(Gdx.graphics.getHeight() / 2 - (splashSprite.getHeight() / 2));//centers the sprites y axis to the screen
		
		batch = new SpriteBatch();
	}
	
	@Override
	public void render(float delta) {
		// game loop
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		manager.update(delta); //asset manager
		batch.begin();//sprite batch
		splashSprite.draw(batch);//draw the sprites in the batch the draw command needs to be inbetween the batch.begin() and batch.end()
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void show() {
		// method called to display on screen
		
		
		Tween.registerAccessor(Sprite.class, new SpriteTween());//sets the sprite that we just made (which is the splash logo) to a tween so week can 
		//
		
		manager = new TweenManager();
		TweenCallback tcb = new TweenCallback(){

			@Override
			public void onEvent(int type, BaseTween<?> source) {
				// TODO Auto-generated method stub
				tweenCompleted();
			}
		};
			
		//a tween is a slight animation that goes for a specific amount of time
		//here we are setting the 
		Tween.to(splashSprite, SpriteTween.ALPHA, 4f).target(1).ease(TweenEquations.easeInQuad).repeatYoyo(1, 0.5f).setCallback(tcb).setCallbackTriggers(TweenCallback.COMPLETE).start(manager);
	}
	
	private void tweenCompleted() {
		//this will display the MainMenu screen 
		//Gdx.app.log(BombJuggly.LOG, "TweenComplete");
		game.setScreen(new MainMenu(game));
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// what this does later
		batch.dispose();
		splashSprite.getTexture().dispose();

	}

}
