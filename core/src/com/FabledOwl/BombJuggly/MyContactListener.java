package com.FabledOwl.BombJuggly;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.FabledOwl.BombJuggly.screens.GameOver;

public class MyContactListener implements ContactListener{

	private Sprite bombSprite = new Sprite();
	private Array<Body> bodiesToRemove;

	
	public MyContactListener(){
		super();
		bodiesToRemove = new Array<Body>();
	}
		
	@Override
	public void beginContact(Contact c) {
		// called when two fixture collide
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		
		if (fa == null || fb == null) return;
		
		if (fa.getUserData()!= null && fa.getUserData().equals("ground")){//couldn't find where the bomb is so if it's something is colliding with the ground it has to be a bomb, so get rid of it
			bodiesToRemove.add(fb.getBody());
		}	
		if (fb.getUserData()!= null && fb.getUserData().equals("ground")){
			bodiesToRemove.add(fa.getBody());
		}
				
	}

	public Array<Body> getBodiesToRemove() {
		return bodiesToRemove;
	}

	public void setBodiesToRemove(Array<Body> bodiesToRemove) {
		this.bodiesToRemove = bodiesToRemove;
	}

	@Override
	public void endContact(Contact c) {
		// called when two fixture stop colliding
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		
		if (fa == null || fb == null) return;
		
		
	}
	//beginContact - when the contact begins
	//presolve - what to do about the contact that happening between the two fixtures
	//endContact - when the contact stops
	//postsolve - what to do after the contact stops
	
	@Override
	public void postSolve(Contact arg0, ContactImpulse ci) {}
	@Override
	public void preSolve(Contact arg0, Manifold m) {}

}
