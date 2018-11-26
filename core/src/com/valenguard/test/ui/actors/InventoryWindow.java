package com.valenguard.test.ui.actors;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.valenguard.test.ui.Buildable;
import com.valenguard.test.ui.MovableWindow;
import com.valenguard.test.ui.StageHandler;

public class InventoryWindow extends MovableWindow implements Buildable {

    private static final int NUM_ROWS = 6;
    private static final int NUM_COLUMNS = 5;

    private final StageHandler stageHandler;

    public InventoryWindow(StageHandler stageHandler) {
        super(stageHandler.getSkin());
        this.stageHandler = stageHandler;
    }

    @Override
    public Actor build() {
        final int josephPaddingBullshit = 0;
        final int movableWindowPadding = 5;

        pad(movableWindowPadding);
        setResizable(true);
        setColor(Color.BLUE);

        final Table table = new Table();

        int columnCount = 0;
        for (int i = 0; i < NUM_ROWS * NUM_COLUMNS; i++) {
            InventorySlot inventorySlot = new InventorySlot(stageHandler);
            inventorySlot.build();
            table.add(inventorySlot).pad(josephPaddingBullshit).expand().fill();

            columnCount++;

            if (columnCount == NUM_COLUMNS) {
                table.row();
                columnCount = 0;
            }
        }

        table.setWidth(16 * NUM_COLUMNS + (josephPaddingBullshit * (NUM_COLUMNS - 2)));
        table.setHeight(16 * NUM_ROWS + (josephPaddingBullshit * (NUM_ROWS - 2)));

        this.setWidth(table.getWidth() + movableWindowPadding * 2);
        this.setHeight(table.getHeight() + movableWindowPadding * 2);

        add(table).expand().fill();
        table.setFillParent(false);

        setPosition(stageHandler.getStage().getViewport().getScreenWidth() - this.getWidth(), 0);


        addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode != Input.Keys.I) return false;
                setVisible(!isVisible());
                return true;
            }
        });

        return this;
    }
}
