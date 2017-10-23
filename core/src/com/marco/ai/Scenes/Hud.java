package com.marco.ai.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.marco.ai.MyGdxGame;

/**
 * Created by marco on 22/10/17.
 */
public class Hud {

    private Stage stage;
    private Viewport vp;

    private float[] camPositionValue;
    private float[] cursorPositionValue;

    private Label camPosition;
    private Label cursorPosition;
    private Label somemore;

    public Hud(SpriteBatch sb){
        vp = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(vp, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        camPositionValue = new float[] {0, 0};
        cursorPositionValue = new float[] {0, 0};

        camPosition = new Label("CamPosition: ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        cursorPosition = new Label("CamPosition: ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        somemore = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(camPosition).expandX().padTop(10);
        table.add(cursorPosition).expandX().padTop(10);
        table.add(somemore).expandX().padTop(10);
        table.row();

        stage.addActor(table);
    }

    public void updateCamPosition(float x, float y) {
        camPositionValue[0] = x;
        camPositionValue[1] = y;
    }

    public void updateCurserPosition(float x, float y) {
        cursorPositionValue[0] = x;
        cursorPositionValue[1] = y;
    }

    public void update() {
        camPosition.setText(String.format("CamPosition: %.2f,%.2f", camPositionValue[0],camPositionValue[1]));
        cursorPosition.setText(String.format("CursorPosition: %.2f,%.2f", cursorPositionValue[0],cursorPositionValue[1]));
        somemore.setText("World!");
    }

    public Stage getStage() {
        return stage;
    }

//    @Override
//    public void dispose() {
//        stage.dispose();
//    }
}
