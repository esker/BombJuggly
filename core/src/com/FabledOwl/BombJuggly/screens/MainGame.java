package com.FabledOwl.BombJuggly.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.FabledOwl.BombJuggly.Bomb;
import com.FabledOwl.BombJuggly.BombJuggly;
import com.FabledOwl.BombJuggly.InputController;
import com.FabledOwl.BombJuggly.MyContactListener;


public class MainGame implements Screen {
	final BombJuggly game;
	
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera, cam2;
	
	private Sprite bombSprite;
	private SpriteBatch sb;
	private Sprite bgImage;
	private Texture bgTex;
	
    private final float TIMESTEP = 1/60f;
    private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS =3;
	
    private Skin skin;
    
    private MouseJointDef mjDef;
    private MouseJoint joint;
    
    private Body bomb;
    
    private MyContactListener cl;
    private Vector2 maxSpeed = new Vector2(0, 250);
    private Vector2 movement;
    
    private Array<Body> tmpbodies = new Array<Body>();
    
    private Bomb b = new Bomb();
    private float delay;
    private Label score;
    
    private Sound tapSound, explosion;
    
    private int scr;
    private String scoreName;
    private BitmapFont font = new BitmapFont();
    
    
	public MainGame(final BombJuggly game) {
		//Texture.setEnforcePotImages(false);// non powers of two
		bgTex = new Texture(Gdx.files.internal("data/GamePlay_Page.png"));
		this.game = game;
		sb = new SpriteBatch();
		world = new World(new Vector2(0, -9.8f), true);//
		
		cl = new MyContactListener();
		world.setContactListener(cl);
		debugRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera();
		cam2 = new OrthographicCamera(game.GAME_WIDTH, game.GAME_HEIGHT);
		
		bgImage = new Sprite(bgTex);		
	}

	@Override
	public void render(float delta) {
		// renders my stuff
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//
		sb.setProjectionMatrix(cam2.combined);
		sb.begin();
		
		bgImage.draw(sb);
		//all box 2d drawing stuff
				
		world.getBodies(tmpbodies);//get all bodies in the world
		for (Body b : tmpbodies){
			if (b.getUserData() != null && b.getUserData() instanceof Sprite){//if there are any bodies that are an instace of sprite then draw them
				Sprite spr = (Sprite) b.getUserData();
				spr.setPosition(b.getPosition().x - spr.getWidth() / 2, b.getPosition().y - spr.getHeight() / 2);
				spr.setRotation(b.getAngle() * MathUtils.radiansToDegrees);
				spr.draw(sb);
			}
		}		
		sb.end();
		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		
		//remove bombs
		Array<Body> bodies = cl.getBodiesToRemove();
		for(int i = 0; i < bodies.size; i++){
			Body b = bodies.get(i);
			explosion.play();
			world.destroyBody(b);	
		}
		bodies.clear();
		
		//debugRenderer.render(world, camera.combined);		
		if (world.getBodyCount() < 5)
			gameOver();
	}

	@Override
	public void resize(int width, int height) {
		// updates the orthogonal camera
		//divide to zoom camera in resulting in meter to pixel ratio 10m to 1
		cam2.viewportWidth = width / 15;
		cam2.viewportHeight = height / 15;
		cam2.update();
		
		camera.viewportWidth = width / 15;
		camera.viewportHeight = height / 15;
		camera.update();
	}
	
	
	@Override
	public void show() {
		//load controls
		controls();
		//set background image
		bgImage.setPosition(-10.5f, -18f);
		bgImage.setSize(20.9f, 34);
		//load sounds
		tapSound = Gdx.audio.newSound(Gdx.files.internal("sounds/tap.wav"));
		explosion = Gdx.audio.newSound(Gdx.files.internal("sounds/Explosion.wav"));
		//create first bomb
		b.createBombBody(world);
		bomb = b.getBombBody();
				
		ChainShape groundshape = new ChainShape();//create ground fixture
		ChainShape topShape = new ChainShape();//top fixtureshape
		ChainShape leftShape = new ChainShape();//left fixtureshape
		ChainShape rightShape = new ChainShape();//right fixtureshape
		//fixture shapes
		groundshape.createChain(new Vector2[] {new Vector2(-500, -20), new Vector2(500, -20)});
		topShape.createChain(new Vector2[] {new Vector2(-10, 15.7f), new Vector2(10, 15.7f)});
		leftShape.createChain(new Vector2[] {new Vector2(-10, -12), new Vector2(-10, 15.7f)});
		rightShape.createChain(new Vector2[] {new Vector2(10, -12), new Vector2(10, 15.7f)});
		
		//body for the ground
		//body fixture for the ground
		createChainFixture(groundshape, "ground");
		createChainFixture(topShape, "top");
		createChainFixture(leftShape, "left");
		createChainFixture(rightShape, "right");		
	}

	public void createChainFixture(ChainShape cs, String name){
		FixtureDef fd = new FixtureDef();
		BodyDef bd = new BodyDef();
		
		//create ground
		bd.type = BodyType.StaticBody;
		bd.position.set(0, 0);
		
		fd.shape = cs;
		fd.friction = .5f;
		fd.restitution = 0;
		//add shape to the world
		//create shape body
		world.createBody(bd).createFixture(fd).setUserData(name);
		
	}
	
	//calls game over screen and brings you back to the main menu
	public void gameOver(){
		((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(game));
	}
	
	public void controls(){
		
		Gdx.input.setInputProcessor(new GestureDetector(new InputController()){	
			/** we instantiate this vector and the callback here so we don't irritate the GC **/
			Vector3 testPoint = new Vector3();
			QueryCallback callback = new QueryCallback() {
				@Override
				public boolean reportFixture (Fixture fixture) {
					// if the hit point is inside the fixture of the body
					// we report it
					if (fixture.testPoint(testPoint.x, testPoint.y)) {
						bomb = fixture.getBody();
						return false;
					} else
						return true;
				}
			};				
			
			@Override
			public boolean touchDown(float screenX, float screenY, int pointer,
					int button) {
				
				// translate the mouse coordinates to world coordinates
				camera.unproject(testPoint.set(screenX, screenY, 0));
				// ask the world which bodies are within the given
				// bounding box around the mouse pointer
				bomb = null;
				world.QueryAABB(callback, testPoint.x - 0.0001f, testPoint.y - 0.0001f, testPoint.x + 0.0001f, testPoint.y + 0.0001f);

				//if (bomb == world.createBody(bodyDef)) hopefully will not have to worry abotu this  
				//	bomb = null; because the bomb will never change body types

				// ignore kinematic bodies, they don't work with the mouse joint
				if (bomb != null && bomb.getType() == BodyType.KinematicBody) 
					return false;

				// if we hit the bomb we want to apply the impulse
				if (bomb != null) {
					tapSound.play();
					b.tap();
					if (b.getJumps() >= 10){
						b.createBombBody(world);
						bomb = b.getBombBody();
						b.setJumps(0);
					}
					maxSpeed.set((float) (-10 + (Math.random() * 10)+1), 150);
					bomb.applyLinearImpulse(maxSpeed, bomb.getPosition(), true);
				}				
				return false;
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
		//
	}

	@Override
	public void dispose() {
		// dispose of all the native resources
		explosion.dispose();
		tapSound.dispose();
		world.dispose();
		debugRenderer.dispose();
		sb.dispose();
		
	}
}
