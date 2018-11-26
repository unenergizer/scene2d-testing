package com.valenguard.test.ui.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.valenguard.test.ui.Buildable;
import com.valenguard.test.ui.MovableWindow;
import com.valenguard.test.ui.StageHandler;

public class EscapeTable extends MovableWindow implements Buildable {

    private final StageHandler stageHandler;

    public EscapeTable(StageHandler stageHandler) {
        super(stageHandler.getSkin());
        this.stageHandler = stageHandler;
    }

    @Override
    public Actor build() {
        setWidth(Gdx.graphics.getWidth());
        setHeight(Gdx.graphics.getHeight());
        setBounds(0, 0, getWidth(), getHeight());
        setFillParent(true);

        Table outsideTable = new Table();
        Table innerTable = new Table();

        TextButton help = new TextButton("Help", stageHandler.getSkin());
        TextButton settings = new TextButton("Settings", stageHandler.getSkin());
        TextButton logout = new TextButton("Logout", stageHandler.getSkin());
        TextButton exitGame = new TextButton("Exit Game", stageHandler.getSkin());
        TextButton returnToGame = new TextButton("Return to Game", stageHandler.getSkin());
        returnToGame.setColor(Color.GREEN);

        innerTable.pad(3);
        innerTable.add(help).expand().fill().pad(0, 0, 3, 0);
        innerTable.row();
        innerTable.add(settings).expand().fill().pad(0, 0, 3, 0);
        innerTable.row();
        innerTable.add(logout).expand().fill().pad(0, 0, 3, 0);
        innerTable.row();
        innerTable.add(exitGame).expand().fill().pad(0, 0, 6, 0);
        innerTable.row();
        innerTable.add(returnToGame).expand().fill();
        innerTable.row();
        innerTable.center();

        outsideTable.setFillParent(true);
        outsideTable.add(innerTable);
        addActor(outsideTable);

        addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode != Input.Keys.ESCAPE) return false;
                setVisible(!isVisible());
                return true;
            }
        });

//        setVisible(true);
//        setVisible(false);

        return this;
    }
}
