package com.marco.ai;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.marco.ai.Screens.WorldScreen;

public class MyGdxGame extends Game {
	private SpriteBatch batch;
	public static int V_WIDTH = 800;
	public static int V_HEIGHT = 480;
	public static int SCROLL_VELOCITY = 200;

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
