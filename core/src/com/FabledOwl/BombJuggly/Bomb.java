package com.FabledOwl.BombJuggly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Bomb {
	private int jumpHeight, jumpOffset, weight, jumps;
		
	Rectangle bomb;
	public int x = 0, y = 0;
	private Body bombBody;
	private Sprite bombSprite;
	private float delay;
	private Sound explosion = Gdx.audio.newSound(Gdx.files.internal("sounds/Explosion.wav"));
	
	public Bomb(){//stay with standard bomb size
		bomb = new Rectangle();
		x = 320 / 2 - 16 / 2;
		y = 200;
		bomb.width = 16;
		bomb.height = 16;		
	}

	public Body getBombBody() {
		return bombBody;
	}

	public int getJumps(){
		return jumps;
	}
	
	public void setJumps(int i){
		jumps = i;
	}
	
	public void tap(){
		y += (weight - jumpHeight);
		jumps++;		
	}
	
	public void createBombBody(final World world){
		//ball body definition
		BodyDef bombDef = new BodyDef();
		
		bombDef.type = BodyType.DynamicBody;
		bombDef.position.set(0, 1);//remember these are meters vector2 is in meters
		
		//ball shape
		CircleShape shape = new CircleShape();
		shape.setPosition(new Vector2 (0, 0));
		shape.setRadius(1.45f);
		
		//ball fixture definition
		//physical properties of the thing you're creating
		FixtureDef bombfixtureDef = new FixtureDef();
		bombfixtureDef.shape = shape;//this will tell the fixture to be around the ball
		bombfixtureDef.density = 2.5f;//how much mass per square meter kilo grams think of stones and feathers
		bombfixtureDef.friction = 0.25f;
		bombfixtureDef.restitution = 0.15f;//how much bounce it has in the opposite direction 0-1 value, value is the amount reflected in the opposite direction
		
		//tell teh world to create the body
		bombBody = world.createBody(bombDef);
		bombBody.createFixture(bombfixtureDef);	//tell body to attach the fixture on it
		
		//create new sprite
		bombSprite = new Sprite(new Texture("data/Bomb.png"));
		//need to change sprite to box2d meters
		bombSprite.setSize(3f, 2.8f);
		bombSprite.setOrigin(bombSprite.getWidth()/2, bombSprite.getHeight()/2);
		
		bombBody.setUserData(bombSprite);
		
		//after some time has passed get rid of bomb
		//I mean it's a bomb after all

		shape.dispose();
	}
}
