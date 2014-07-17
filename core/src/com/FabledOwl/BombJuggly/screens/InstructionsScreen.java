package com.FabledOwl.BombJuggly.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.FabledOwl.BombJuggly.BombJuggly;

public class InstructionsScreen implements Screen {

	private final BombJuggly game;

	OrthographicCamera camera;
	private Stage stage;

	Texture mainMenuBGImage, play, exit;

	public InstructionsScreen(BombJuggly game) {
		// Texture.setEnforcePotImages(false);//avoids needing powers of 2 for
		// images
		mainMenuBGImage = new Texture(Gdx.files.internal("data/howto_page.png"));
		Sprite bg = new Sprite(mainMenuBGImage);
		bg.setColor(1, 1, 1, 0);//sets the background color of the sprite (this is black)
		bg.setX(Gdx.graphics.getWidth() / 2 - (bg.getWidth() / 2));
		bg.setY(Gdx.graphics.getHeight() / 2 - (bg.getHeight() / 2));
		
		this.game = game;
		play = new Texture(Gdx.files.internal("data/start.png"));
		exit = new Texture(Gdx.files.internal("data/exit.png"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 320, 480);
	}

	@Override
	public void render(float delta) {
		// when mouse is clicked

		// arguments to glClearColor are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		Gdx.gl.glClearColor(0, 0, 0, 0);// should be black
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell the camera to update its matrices.
		camera.update();
		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		// draw bg image
		game.batch.draw(mainMenuBGImage, 0, 0);
		game.batch.end();
		
		stage.act(delta);
		stage.draw();


	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void show() {
		stage = new Stage();

		Gdx.input.setInputProcessor(stage);

		Image playImage = new Image(play);
		playImage.setX(70);
		playImage.setY(75);
		playImage.setWidth(80);
		playImage.setHeight(25);

		Image exitImage = new Image(exit);
		exitImage.setX(70);
		exitImage.setY(50);
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
		//
		dispose();
	}

	@Override
	public void pause() {
		//
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		// get rid of stuff created
		mainMenuBGImage.dispose();

	}
}
