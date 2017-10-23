package com.marco.ai.Actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

/**
 * Created by marco on 23/10/17.
 */
public class Actor {

    private Sprite sprite;
    private Body body;
    private BodyDef bdef;

    private float radius;

    public Actor(float posX, float posY, float radius) {
        bdef = new BodyDef();
        bdef.position.set(posX, posY);
        bdef.type = BodyDef.BodyType.DynamicBody;

        this.radius = radius;
    }

    public BodyDef getBdef() { return bdef; }

    public void setBody(Body b) {
        body = b;

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        shape.setRadius(radius);
        fdef.shape = shape;
        body.createFixture(fdef);
    }
}
