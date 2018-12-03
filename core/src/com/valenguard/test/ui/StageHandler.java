package com.valenguard.test.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.kotcrab.vis.ui.FocusManager;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.PopupMenu;
import com.valenguard.test.file.FileManager;
import com.valenguard.test.file.GameAtlas;
import com.valenguard.test.ui.actors.ChatWindow;
import com.valenguard.test.ui.actors.EscapeTable;
import com.valenguard.test.ui.actors.InventoryWindow;

public class StageHandler implements Disposable, InputProcessor {

    private static final boolean PRINT_DEBUG = false;

    private final FileManager fileManager = new FileManager();
    private final Stage stage = new Stage();

    private EscapeTable escapeTable;
    private ChatWindow chatWindow;
    private InventoryWindow inventoryWindow;


    public void init() {
        final Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        root.add().expand().fill();

        stage.setDebugAll(PRINT_DEBUG);
        VisUI.load(Gdx.files.internal("skin/tixel/x1/tixel.json"));

        fileManager.loadAtlas(GameAtlas.ITEM_TEXTURES);

        escapeTable = new EscapeTable();
        chatWindow = new ChatWindow(this);
        inventoryWindow = new InventoryWindow(this);

        stage.addActor(chatWindow.build());
        stage.addActor(inventoryWindow.build());
        stage.addActor(escapeTable.build());

//        stage.setKeyboardFocus(stage.getRoot());
        stage.addListener(new InputListener() {
            boolean debug = false;

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.F12) {
                    debug = !debug;
                    root.setDebug(debug, true);
                    for (Actor actor : stage.getActors()) {
                        if (actor instanceof Group) {
                            Group group = (Group) actor;
                            group.setDebug(debug, true);
                        }
                    }
                    return true;
                }

                return false;
            }
        });
    }

    public void render(float delta) {
        stage.act(Math.min(delta, 1 / 30f));
        stage.draw();
    }

    public void resize(int width, int height) {
        if (width == 0 && height == 0)
            return; //see https://github.com/libgdx/libgdx/issues/3673#issuecomment-177606278
        stage.getViewport().update(width, height, true);
        PopupMenu.removeEveryMenu(stage);
        WindowResizeEvent resizeEvent = new WindowResizeEvent();
        for (Actor actor : stage.getActors()) {
            actor.fire(resizeEvent);
        }
    }

    public Stage getStage() {
        return stage;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    @Override
    public void dispose() {
        VisUI.dispose();
        stage.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {


        if (keycode == Input.Keys.ESCAPE) {
            if (!escapeTable.isVisible()) {
                escapeTable.fadeIn().setVisible(true);
                FocusManager.switchFocus(stage, escapeTable);
            } else {
                escapeTable.fadeOut();
            }
            return true;
        }

        if (keycode == Input.Keys.ENTER) {
            if (!chatWindow.isChatToggled()) {
                FocusManager.switchFocus(stage, chatWindow.getMessageInput());
                stage.setKeyboardFocus(chatWindow.getMessageInput());
                chatWindow.setChatToggled(true);
                return true;
            }
        }

        if (keycode == Input.Keys.I) {
            if (!chatWindow.isChatToggled()) {
                System.out.println("I is set focus to the inventory");
                if (!inventoryWindow.isVisible()) {
                    inventoryWindow.fadeIn().setVisible(true);
                    FocusManager.switchFocus(stage, inventoryWindow);
                } else {
                    inventoryWindow.fadeOut();
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
