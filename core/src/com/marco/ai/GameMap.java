package com.marco.ai;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.marco.ai.Screens.WorldScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by marco on 22/10/17.
 */
public class GameMap {

    private static final int[] MAP_OBJECT_LAYERS = {4, 5, 6};

    private Logger log;

    // Level
    private TiledMap map;
    private MapProperties mapProps;
    private int mapWidth;
    private int mapHeight;
    private OrthogonalTiledMapRenderer renderer;

    // Colision detection
    private World box2dWorld;
    private Box2DDebugRenderer b2dr;

    public GameMap(String path) {
        log = LoggerFactory.getLogger(WorldScreen.class);

        map = new TmxMapLoader().load(path);
        mapProps = map.getProperties();
        mapWidth = mapProps.get("width", Integer.class) * mapProps.get("tilewidth", Integer.class);
        mapHeight = mapProps.get("height", Integer.class) * mapProps.get("tileheight", Integer.class);
        log.info("Map dimensions: " + mapWidth + ", " + mapHeight);
        renderer = new OrthogonalTiledMapRenderer(map);

        box2dWorld = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();
    }

    public TiledMap getMap() { return map; }
    public int getWidth() { return mapWidth; }
    public int getHeight() { return mapHeight; }
    public <T> T getProp (String key, Class clazz) { return (T)mapProps.get(key, clazz); }

    public OrthogonalTiledMapRenderer getRenderer() { return renderer; }

    public void setView(OrthographicCamera cam) { renderer.setView(cam);}

    public void renderMap() { renderer.render(); }

    public void renderBox2d(final OrthographicCamera cam) { b2dr.render(box2dWorld, cam.combined);}

    public void setupColisionObjects() {
        BodyDef bdef = new BodyDef();
        PolygonShape polyShape = new PolygonShape();
        CircleShape circleShape = new CircleShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        Rectangle rect;
        Ellipse ellipse;

        bdef.type = BodyDef.BodyType.StaticBody;

        for(int layer : GameMap.MAP_OBJECT_LAYERS) {
            log.info("Processing map layer " + layer + " with " + map.getLayers().get(layer).getObjects().getCount() + " objects.");
            for (MapObject object : map.getLayers().get(layer).getObjects()) {
                if (object.getClass() == RectangleMapObject.class) {
                    rect = ((RectangleMapObject)object).getRectangle();
                    bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);
                    polyShape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
                    fdef.shape = polyShape;
                    log.info("Created collision box at " + (rect.getX() + rect.getWidth() / 2) + "," + (rect.getY() + rect.getHeight() / 2));
                } else if (object.getClass() == EllipseMapObject.class) {
                    ellipse = ((EllipseMapObject)object).getEllipse();
                    bdef.position.set(ellipse.x + ellipse.width/2, ellipse.y + ellipse.width/2);
                    circleShape.setRadius(ellipse.width/2);
                    fdef.shape = circleShape;
                    log.info("Created collision circle at " + ellipse.x + "," + ellipse.y + " with radius " + ellipse.width/2);
                }


                body = box2dWorld.createBody(bdef);
                body.createFixture(fdef);
            }
        }
    }

    public Body getNewBody(BodyDef bdef) {
        return box2dWorld.createBody(bdef);
    }
}
