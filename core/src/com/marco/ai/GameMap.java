package com.marco.ai;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.marco.ai.Screens.WorldScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by marco on 22/10/17.
 */
public class GameMap {

    private Logger log;

    // Level
    private TiledMap map;
    private MapProperties mapProps;
    private int mapWidth;
    private int mapHeight;
    private OrthogonalTiledMapRenderer renderer;

    public GameMap(String path) {
        log = LoggerFactory.getLogger(WorldScreen.class);

        map = new TmxMapLoader().load(path);
        mapProps = map.getProperties();
        mapWidth = mapProps.get("width", Integer.class) * mapProps.get("tilewidth", Integer.class);
        mapHeight = mapProps.get("height", Integer.class) * mapProps.get("tileheight", Integer.class);
        log.info("Map dimensions: " + mapWidth + ", " + mapHeight);
        renderer = new OrthogonalTiledMapRenderer(map);
    }

    public TiledMap getMap() { return map; }
    public int getWidth() { return mapWidth; }
    public int getHeight() { return mapHeight; }
    public <T> T getProp (String key, Class clazz) { return (T)mapProps.get(key, clazz); }

    public OrthogonalTiledMapRenderer getRenderer() { return renderer; }

    public void setView(OrthographicCamera cam) { renderer.setView(cam);}

    public void render() { renderer.render(); }
}
