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
public class KeyboardTurningActor implements ActorInterface {
    // To slow down the Actor when moving
    private static final float LINEAR_DAMPING = 10;
    // max velocity of the Actor
    private static final float MAX_VELOCITY = 2f;
    // steps at which velocity of the actor is increasing: acceleration
    private static final float VELOCITY_STEPS = 0.2f;
    // orientation angle steps
    private static final double ORIENTATION_ANGLE_STEPS = .07;

    private Logger log;

    private Actor actor;

    private double facingAngle;


    public KeyboardTurningActor() {
        log = LoggerFactory.getLogger(KeyboardTurningActor.class);
        actor = new Actor();
        facingAngle = 0;
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
    }

    public BodyDef getBdef(float posX, float posY) {
        return actor.getBdef(posX, posY, LINEAR_DAMPING);
    }
    public void setBody(Body body, float radius) {
        actor.setBody(body, radius);
    }

    private void keyboardAction() {
        if ((Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.DPAD_UP))
                && actor.body.getLinearVelocity().y <= MAX_VELOCITY) {
            actor.body.applyLinearImpulse(transformImpulseVector(new Vector2(VELOCITY_STEPS, 0f)), actor.body.getWorldCenter(), true);
        } else if ((Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN))
                && actor.body.getLinearVelocity().y >= -MAX_VELOCITY) {
            actor.body.applyLinearImpulse(transformImpulseVector(new Vector2(-VELOCITY_STEPS, -0f)), actor.body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if(facingAngle - ORIENTATION_ANGLE_STEPS < -180)
                facingAngle = 360 + (facingAngle - ORIENTATION_ANGLE_STEPS);
            else
                facingAngle -= ORIENTATION_ANGLE_STEPS;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if(facingAngle + ORIENTATION_ANGLE_STEPS > 180)
                facingAngle = (facingAngle + ORIENTATION_ANGLE_STEPS) - 360;
            else
                facingAngle += ORIENTATION_ANGLE_STEPS;
        }
    }

    /**
     * Transforms an impulse vector to consider the orientation of teh Actor
     *
     * @param   v     Impulse vector that defines the strength of the movement
     * @return        Transformed impulse vector which now considers the orientation
     */
    private Vector2 transformImpulseVector(Vector2 v) {
        Vector2 vo = new Vector2();
        vo.x = ((float) (v.x*Math.cos(facingAngle) - v.y*Math.sin(facingAngle)));
        vo.y = ((float) (v.x*Math.sin(facingAngle) - v.y*Math.cos(facingAngle)));
        log.info("Transformed vector v1:" + v.toString() + " with an angle of " + String.format("%.2f",facingAngle) + " to new vector vo:" + vo.toString());
        return vo;
    }
}
