package com.marco.ai.Actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.marco.ai.MyGdxGame;

import java.util.UUID;

/**
 * Created by marco on 23/10/17.
 */
public class Actor {

    Body body;
    private BodyDef bdef;
    private Fixture fixture;

    private Sprite sprite;
    private TextureRegion tr;

    private String name;
    private float radius;

    private TextureRegion runTexture;

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

    public void update(float dt) {

    }

    void setTextureRegion(TextureRegion t) { tr = t; }

    public boolean testPoint(Vector3 v) { return testPoint(v.x, v.y); }
    public boolean testPoint(Vector2 v) { return fixture.testPoint(v); }
    public boolean testPoint(int x, int y) { return testPoint(((float)x), ((float)y)); }
    public boolean testPoint(float x, float y) { return fixture.testPoint(x, y); }

    public float getRadius() { return radius; }
    public String getName() { return name; }

    public void draw(SpriteBatch sb) {
        if(sprite == null){
            sprite = new Sprite(tr);
            runTexture = new TextureRegion(sprite.getTexture(),334, 110,37,37);
            sprite.setBounds(0,0, 24/MyGdxGame.PPM, 24/MyGdxGame.PPM);
            sprite.setRegion(runTexture);
        }
        sprite.setPosition(body.getPosition().x-radius, body.getPosition().y-radius);
        sprite.draw(sb);
    }
}
