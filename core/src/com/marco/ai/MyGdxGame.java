package com.marco.ai;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.marco.ai.Screens.WorldScreen;

public class MyGdxGame extends Game {
	private SpriteBatch batch;
	public static final int V_WIDTH = 800;
	public static final int V_HEIGHT = 480;
	public static final float PPM = 100;
	public static final float SCROLL_VELOCITY = 200/PPM;
	public static final float SCROLLOVER = 100/PPM;

	public static float scaleToPPM(int in) { return scaleToPPM(((float)in)); }
	public static float scaleToPPM(float in) { return in/PPM; }

	public MyGdxGame() {
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new WorldScreen(batch));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
