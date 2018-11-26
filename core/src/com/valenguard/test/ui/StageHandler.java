package com.valenguard.test.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.valenguard.test.file.FileManager;
import com.valenguard.test.file.GameAtlas;
import com.valenguard.test.file.GameSkin;
import com.valenguard.test.ui.actors.ChatWindow;
import com.valenguard.test.ui.actors.EscapeTable;
import com.valenguard.test.ui.actors.InventoryWindow;

public class StageHandler implements Disposable {

    private static final boolean PRINT_DEBUG = true;

    private final FileManager fileManager = new FileManager();
    private final Stage stage = new Stage();

    private Skin skin;

    public void init() {
        stage.setDebugAll(PRINT_DEBUG);

        fileManager.loadSkin(GameSkin.DEFAULT);
        fileManager.loadAtlas(GameAtlas.ITEM_TEXTURES);
        skin = fileManager.getSkin(GameSkin.DEFAULT);


        stage.addActor(new ChatWindow(this).build());
        stage.addActor(new InventoryWindow(this).build());
        stage.addActor(new EscapeTable(this).build());

        Actor focusedActor = stage.getKeyboardFocus();

        System.out.println("The initial focused actor: " + focusedActor);

    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    public Skin getSkin() {
        return skin;
    }

    public Stage getStage() {
        return stage;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
