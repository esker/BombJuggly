package com.FabledOwl.BombJuggly.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;



import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.FabledOwl.BombJuggly.BombJuggly;

public class GameOver implements Screen {

	private Stage stage;

	private final BombJuggly game;
	Texture mainMenuBGImage, play, exit;

	public GameOver(BombJuggly game) {
		// Texture.setEnforcePotImages(false);//avoids needing powers of 2 for
		// images
		mainMenuBGImage = new Texture(
				Gdx.files.internal("data/gameover.png"));
		play = new Texture(Gdx.files.internal("data/start.png"));
		exit = new Texture(Gdx.files.internal("data/exit.png"));
		this.game = game;

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.batch.begin();
		// draw bg image
		game.batch.draw(mainMenuBGImage, 0, 0);
		game.batch.end();

		stage.act(delta);
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		stage = new Stage();

		Gdx.input.setInputProcessor(stage);

		Image playImage = new Image(play);
		playImage.setX(70);
		playImage.setY(140);
		playImage.setWidth(80);
		playImage.setHeight(25);

		Image exitImage = new Image(exit);
		exitImage.setX(70);
		exitImage.setY(75);
		exitImage.setWidth(50);
		exitImage.setHeight(25);

		stage.addActor(playImage);
		stage.addActor(exitImage);

		playImage.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new MainGame(game));
				return true;
			}
		});
		exitImage.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.app.exit();
				return true;
			}
		});

	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		stage.dispose();
		play.dispose();
		exit.dispose();
		mainMenuBGImage.dispose();

	}

}
