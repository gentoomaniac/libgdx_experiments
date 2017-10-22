package com.marco.ai.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.marco.ai.MyGdxGame;
import com.marco.ai.Scenes.Hud;

/**
 * Created by marco on 22/10/17.
 */
public class WorldScreen implements Screen{

    private SpriteBatch batch;
    private OrthographicCamera cam;
    private Viewport vp;

    private Hud hud;

    public WorldScreen(SpriteBatch sb) {
        batch = sb;

        cam = new OrthographicCamera();
        vp = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, cam);
        hud = new Hud(batch);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.getStage().draw();
    }

    @Override
    public void resize(int width, int height) {
        vp.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}
