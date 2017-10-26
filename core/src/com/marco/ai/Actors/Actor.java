package com.marco.ai.Actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

import java.util.UUID;

/**
 * Created by marco on 23/10/17.
 */
public class Actor {

    Body body;
    private Sprite sprite;
    private BodyDef bdef;
    private Fixture fixture;

    private float radius;
    private String name;

    public Actor() {
        name = UUID.randomUUID().toString();
    }

    public Actor(String name) {
        this.name = name;
    }

    public BodyDef getBdef(float posX, float posY, float linearDamping) {
        bdef = new BodyDef();
        bdef.position.set(posX, posY);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearDamping = linearDamping;

        return bdef;
    }

    public void setBody(Body body, float radius) {
        this.body = body;
        this.radius = radius;

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        shape.setRadius(radius);
        fdef.shape = shape;
        fixture = this.body.createFixture(fdef);
    }

    public boolean testPoint(Vector3 v) { return testPoint(v.x, v.y); }
    public boolean testPoint(Vector2 v) { return fixture.testPoint(v); }
    public boolean testPoint(int x, int y) { return testPoint(((float)x), ((float)y)); }
    public boolean testPoint(float x, float y) { return fixture.testPoint(x, y); }

    public float getRadius() { return radius; }
    public String getName() { return name; }
}
