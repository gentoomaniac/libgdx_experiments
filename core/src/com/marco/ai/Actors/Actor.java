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
    Body body;
    private BodyDef bdef;

    private float radius;

    public BodyDef getBdef(float posX, float posY) {
        bdef = new BodyDef();
        bdef.position.set(posX, posY);
        bdef.type = BodyDef.BodyType.DynamicBody;

        return bdef;
    }

    public void setBody(Body body, float radius) {
        this.body = body;
        this.radius = radius;

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        shape.setRadius(radius);
        fdef.shape = shape;
        this.body.createFixture(fdef);
    }
}
