package com.marco.ai.Screens;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.marco.ai.Actors.ActorInterface;
import com.marco.ai.Actors.KeyboardActor;
import com.marco.ai.Actors.KeyboardTurningActor;
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

    private ArrayList<ActorInterface> actors;
    private ActorInterface selectedActor;

    public WorldScreen(SpriteBatch sb) {
        batch = sb;

        log = LoggerFactory.getLogger(WorldScreen.class);

        cam = new OrthographicCamera();
        vp = new FitViewport(MyGdxGame.scaleToPPM(MyGdxGame.V_WIDTH), MyGdxGame.scaleToPPM(MyGdxGame.V_HEIGHT), cam);
        hud = new Hud(batch);

        map = new GameMap("map.tmx");

        // set camera initially to the lower left corner
        cam.position.set(vp.getWorldWidth()/2, vp.getWorldHeight()/2, 0);

        map.setupColisionObjects();

        actors = new ArrayList<>();
        KeyboardTurningActor keyboardActor = new KeyboardTurningActor();
        keyboardActor.setBody(map.getNewBody(keyboardActor.getBdef(MyGdxGame.scaleToPPM(100f), MyGdxGame.scaleToPPM(100f))), MyGdxGame.scaleToPPM(10f));
        actors.add(keyboardActor);
        selectedActor = keyboardActor;
    }

    @Override
    public void show() {

    }

    public void update(float dt) {
        handleInput(dt);
        for(ActorInterface a : actors) {
            a.action();
        }

        if (selectedActor != null ) {
            cam.position.set(selectedActor.getPosition(), 0);
            hud.setSelectedActorVelocity(selectedActor.getLinearVelocity());
            hud.updateSelectedActorName(selectedActor.getActor().getName());
        } else {
            hud.updateSelectedActorName("");
        }
        cam.update();

        map.setView(cam);
        map.update();

        hud.updateCamPosition(cam.position.x, cam.position.y);
        hud.update();
    }

//    private void setupInputProcessor() {
//        Gdx.input.setInputProcessor(new InputProcessor() {
//            @Override
//            public boolean keyDown(int keycode) {
//                super
//                return false;
//            }
//
//            @Override
//            public boolean keyUp(int keycode) {
//                return false;
//            }
//
//            @Override
//            public boolean keyTyped(char character) {
//                return false;
//            }
//
//            @Override
//            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//                return false;
//            }
//
//            @Override
//            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//                return false;
//            }
//
//            @Override
//            public boolean touchDragged(int screenX, int screenY, int pointer) {
//                return false;
//            }
//
//            @Override
//            public boolean mouseMoved(int screenX, int screenY) {
//                return false;
//            }
//
//            @Override
//            public boolean scrolled(int amount) {
//                return false;
//            }
//        });
//    }

    public void handleInput(float dt) {
        float diff = MyGdxGame.SCROLL_VELOCITY * dt;

        // if no Actor is set for tracking
        if (selectedActor == null) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
                if (cam.position.x + diff > MyGdxGame.scaleToPPM(map.getWidth()) - vp.getWorldWidth()/2 + MyGdxGame.SCROLLOVER)
                    cam.position.x = MyGdxGame.scaleToPPM(map.getWidth()) - vp.getWorldWidth()/2 + MyGdxGame.SCROLLOVER;
                else
                    cam.position.x += diff;
            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
                if (cam.position.x - diff < vp.getWorldWidth() / 2 - MyGdxGame.SCROLLOVER)
                    cam.position.x = vp.getWorldWidth() / 2 - MyGdxGame.SCROLLOVER;
                else
                    cam.position.x -= diff;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
                if (cam.position.y + diff > MyGdxGame.scaleToPPM(map.getHeight()) - vp.getWorldHeight()/2 + MyGdxGame.SCROLLOVER)
                    cam.position.y = MyGdxGame.scaleToPPM(map.getHeight()) - vp.getWorldHeight()/2 + MyGdxGame.SCROLLOVER;
                else
                    cam.position.y += diff;
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
                if (cam.position.y - diff < vp.getWorldHeight()/2 - MyGdxGame.SCROLLOVER)
                    cam.position.y = vp.getWorldHeight()/2 - MyGdxGame.SCROLLOVER;
                else
                    cam.position.y -= diff;
            }
        }

        // update hud with cursor position
        hud.updateCurserPosition(Gdx.input.getX(), Gdx.input.getY());

        // on touch check if an actor was touched and set/unset selectedActor
        if(Gdx.input.justTouched()) {
            boolean wasUpdated = false;
            Vector3 v3 = cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0));
            Vector2 v2 = new Vector2(v3.x, v3.y);
            log.info("Click detected at " + Gdx.input.getX() + "," + Gdx.input.getY() + " mapping to world coordinates " + v2.x + "," + v2.y);
            for(ActorInterface act : actors) {
                if(v2.dst(act.getPosition()) <= act.getActor().getRadius()) {
                    log.info("Found actor " + act.getActor().getName() + " at " + act.getPosition().x + "," + act.getPosition().y);
                    selectedActor = act;
                    wasUpdated = true;
                    break;
                }
            }
            if(!wasUpdated)
                selectedActor = null;

        }
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
