package com.marco.ai.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by marco on 23/10/17.
 */
public class KeyboardActor implements ActorInterface {

    private Logger log;

    private Actor actor;

    public KeyboardActor() {
        log = LoggerFactory.getLogger(KeyboardActor.class);
        actor = new Actor();
    }

    public BodyDef getBdef(float posX, float posY) {
        return actor.getBdef(posX, posY);
    }

    public void setBody(Body body, float radius) {
        actor.setBody(body, radius);
    }

    @Override
    public Vector2 getLinearVelocity() { return actor.getLinearVelocity(); }

    @Override
    public void action() {
        keyboardAction();
    }

    private void keyboardAction() {
        if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT))
                && actor.body.getLinearVelocity().x <= 2) {
            actor.body.applyLinearImpulse(new Vector2(1f, 0f), actor.body.getWorldCenter(), true);
            log.info("moving right with x velocity " + actor.body.getLinearVelocity().x);
        } else if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT))
                && actor.body.getLinearVelocity().x >= -2) {
            actor.body.applyLinearImpulse(new Vector2(-1f, 0f), actor.body.getWorldCenter(), true);
            log.info("moving right with x velocity " + actor.body.getLinearVelocity().x);
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.DPAD_UP))
                && actor.body.getLinearVelocity().y <= 2) {
                actor.body.applyLinearImpulse(new Vector2(0f, 1f), actor.body.getWorldCenter(), true);
        } else if ((Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN))
                && actor.body.getLinearVelocity().y >= -2) {
                actor.body.applyLinearImpulse(new Vector2(0f, -1f), actor.body.getWorldCenter(), true);
        }
    }
}
