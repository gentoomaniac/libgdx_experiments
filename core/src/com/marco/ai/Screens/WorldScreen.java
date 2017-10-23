package com.marco.ai.Screens;

import com.marco.ai.Actors.Actor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.marco.ai.GameMap;
import com.marco.ai.MyGdxGame;
import com.marco.ai.Scenes.Hud;

import java.util.ArrayList;

/**
 * Created by marco on 22/10/17.
 */
public class WorldScreen implements Screen{

    private Logger log;

    private SpriteBatch batch;
    private OrthographicCamera cam;
    private Viewport vp;

    private Hud hud;

    // Level
    private GameMap map;

    private ArrayList<Actor> actors;


    public WorldScreen(SpriteBatch sb) {
        batch = sb;

        log = LoggerFactory.getLogger(WorldScreen.class);

        cam = new OrthographicCamera();
        vp = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, cam);
        hud = new Hud(batch);

        map = new GameMap("map.tmx");

        // set camera initially to the lower left corner
        cam.position.set(vp.getWorldWidth()/2, vp.getWorldHeight()/2, 0);

        map.setupColisionObjects();

        actors = new ArrayList<>();
        Actor actor = new Actor(100,100, 10);
        actor.setBody(map.getNewBody(actor.getBdef()));
    }

    @Override
    public void show() {

    }

    public void update(float dt) {
        handleInput(dt);

        cam.update();
        map.setView(cam);
        hud.updateCamPosition(cam.position.x, cam.position.y);
        hud.update();
    }

    // ToDO: Fix the scrolling limitations
    public void handleInput(float dt) {
        float diff = MyGdxGame.SCROLL_VELOCITY * dt;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            if (cam.position.x + diff > map.getWidth() - vp.getWorldWidth()/2 + 100)
                cam.position.x = map.getWidth() - vp.getWorldWidth()/2 + 100;
            else
                cam.position.x += diff;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
            if (cam.position.x - diff < vp.getWorldWidth() / 2 - 100)
                cam.position.x = vp.getWorldWidth() / 2 - 100;
            else
                cam.position.x -= diff;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
            if (cam.position.y + diff > map.getHeight() - vp.getWorldHeight()/2 + 100)
                cam.position.y = map.getHeight() - vp.getWorldHeight()/2 + 100;
            else
                cam.position.y += diff;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
            if (cam.position.y - diff < vp.getWorldHeight()/2 - 100)
                cam.position.y = vp.getWorldHeight()/2 - 100;
            else
                cam.position.y -= diff;
        }
        hud.updateCurserPosition(Gdx.input.getX(), Gdx.input.getY());
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        map.renderMap();

        map.renderBox2d(cam);

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
