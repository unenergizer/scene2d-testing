package com.valenguard.test.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.kotcrab.vis.ui.FocusManager;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.PopupMenu;
import com.valenguard.test.file.FileManager;
import com.valenguard.test.file.GameAtlas;
import com.valenguard.test.ui.actors.ChatWindow;
import com.valenguard.test.ui.actors.CreditsWindow;
import com.valenguard.test.ui.actors.EscapeWindow;
import com.valenguard.test.ui.actors.HelpWindow;
import com.valenguard.test.ui.actors.InventoryWindow;
import com.valenguard.test.ui.actors.settings.MainSettingsWindow;

import lombok.Getter;

@Getter
public class StageHandler implements Disposable {

    private final Stage stage = new Stage();
    private final FileManager fileManager = new FileManager();
    private final PreStageEvent preStageEvent = new PreStageEvent(this);
    private final PostStageEvent postStageEvent = new PostStageEvent(this);

    private HelpWindow helpWindow;
    private CreditsWindow creditsWindow;
    private EscapeWindow escapeWindow;
    private ChatWindow chatWindow;
    private InventoryWindow inventoryWindow;
    private MainSettingsWindow mainSettingsWindow;

    public void init() {
        VisUI.load(Gdx.files.internal("skin/tixel/x1/tixel.json")); //TODO USE FILE MANAGER
        fileManager.loadAtlas(GameAtlas.ITEM_TEXTURES); //TODO REMOVE IN MAIN SOURCE

        helpWindow = new HelpWindow();
        creditsWindow = new CreditsWindow();
        escapeWindow = new EscapeWindow(this);
        chatWindow = new ChatWindow(this);
        inventoryWindow = new InventoryWindow(this);
        mainSettingsWindow = new MainSettingsWindow();

        stage.addActor(helpWindow.build());
        stage.addActor(creditsWindow.build());
        stage.addActor(chatWindow.build());
        stage.addActor(inventoryWindow.build());
        stage.addActor(escapeWindow.build());
        stage.addActor(mainSettingsWindow.build());

        FocusManager.resetFocus(stage); // Clear focus after building windows
    }

    public void render(float delta) {
        stage.act(Math.min(delta, 1 / 30f));
        stage.draw();
    }

    public void resize(int width, int height) {
        // See https://github.com/libgdx/libgdx/issues/3673#issuecomment-177606278
        if (width == 0 && height == 0) return;
        stage.getViewport().update(width, height, true);
        PopupMenu.removeEveryMenu(stage);
        WindowResizeEvent resizeEvent = new WindowResizeEvent();
        for (Actor actor : stage.getActors()) actor.fire(resizeEvent);
    }

    @Override
    public void dispose() {
        VisUI.dispose();
        stage.dispose();
    }
}
