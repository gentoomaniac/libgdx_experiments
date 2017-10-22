package com.marco.ai.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.marco.ai.MyGdxGame;

/**
 * Created by marco on 22/10/17.
 */
public class Hud {

    private Stage stage;
    private Viewport vp;

    private Label someLabel;
    private Label someother;
    private Label somemore;

    public Hud(SpriteBatch sb){
        vp = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(vp, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        someLabel = new Label("Hello World!", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        someother = new Label("Hello World! 2", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        somemore = new Label("Hello World! 3", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(someLabel).expandX().padTop(10);
        table.add(someother).expandX().padTop(10);
        table.add(somemore).expandX().padTop(10);
        table.row();

        stage.addActor(table);
    }

    public Stage getStage() {
        return stage;
    }
}
