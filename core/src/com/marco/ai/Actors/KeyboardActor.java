package com.marco.ai.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by marco on 23/10/17.
 */
public class KeyboardActor implements ActorInterface {
    // To slow down the Actor when moving
    private static final float LINEAR_DAMPING = 10;
    // max velocity of the Actor
    private static final float MAX_VELOCITY = 2f;
    // steps at which velocity of the actor is increasing: acceleration
    private static final float VELOCITY_STEPS = 0.2f;

    private Logger log;

    private Actor actor;

    public KeyboardActor(TextureRegion tr) {
        log = LoggerFactory.getLogger(KeyboardActor.class);
        actor = new Actor();
        actor.setTextureRegion(tr);
    }

    public BodyDef getBdef(float posX, float posY) {
        return actor.getBdef(posX, posY, LINEAR_DAMPING);
    }

    public void setBody(Body body, float radius) {
        actor.setBody(body, radius);
    }

    @Override
    public Vector2 getLinearVelocity() { return actor.body.getLinearVelocity(); }

    @Override
    public Vector2 getPosition() { return actor.body.getPosition(); }

    @Override
    public boolean testPoint(float x, float y) { return actor.testPoint(x, y); }

    @Override
    public Actor getActor() { return actor; }

    @Override
    public void update(float dt) {
        keyboardAction();
        actor.update(dt);
    }

    private void keyboardAction() {
        if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT))
                && actor.body.getLinearVelocity().x <= MAX_VELOCITY) {
            actor.body.applyLinearImpulse(new Vector2(VELOCITY_STEPS, 0f), actor.body.getWorldCenter(), true);
        } else if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT))
                && actor.body.getLinearVelocity().x >= -MAX_VELOCITY) {
            actor.body.applyLinearImpulse(new Vector2(-VELOCITY_STEPS, 0f), actor.body.getWorldCenter(), true);
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.DPAD_UP))
                && actor.body.getLinearVelocity().y <= MAX_VELOCITY) {
                actor.body.applyLinearImpulse(new Vector2(0f, VELOCITY_STEPS), actor.body.getWorldCenter(), true);
        } else if ((Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN))
                && actor.body.getLinearVelocity().y >= -MAX_VELOCITY) {
                actor.body.applyLinearImpulse(new Vector2(0f, -VELOCITY_STEPS), actor.body.getWorldCenter(), true);
        }
    }
}
