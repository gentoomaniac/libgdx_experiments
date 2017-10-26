package com.marco.ai.Actors;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by marco on 24/10/17.
 */
public interface ActorInterface {
    void action();
    Vector2 getLinearVelocity();
    Vector2 getPosition();
    boolean testPoint(float x, float y);
    Actor getActor();
}
