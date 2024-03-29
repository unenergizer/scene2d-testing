package com.valenguard.test.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.kotcrab.vis.ui.FocusManager;
import com.valenguard.test.util.Log;

public class PreStageEvent implements InputProcessor {

    private final StageHandler stageHandler;
    private boolean userInterfaceDebug = false;

    PreStageEvent(StageHandler stageHandler) {
        this.stageHandler = stageHandler;
    }

    @Override
    public boolean keyDown(int keycode) {
        /*
         * Toggle Chat Box Focus
         */
        if (keycode == Input.Keys.ENTER) {
            if (!stageHandler.getChatWindow().isChatToggled()) {
                Log.println(getClass(), "Enter hit");
                FocusManager.switchFocus(stageHandler.getStage(), stageHandler.getChatWindow().getMessageInput());
                stageHandler.getStage().setKeyboardFocus(stageHandler.getChatWindow().getMessageInput());
                stageHandler.getChatWindow().setChatToggled(true);
                return true;
            }
        }

        /*
         * Open Player Inventory
         */
        if (keycode == Input.Keys.I) {
            if (!stageHandler.getChatWindow().isChatToggled()
                    && !stageHandler.getMainSettingsWindow().isVisible()
                    && !stageHandler.getEscapeWindow().isVisible()) {
                if (!stageHandler.getInventoryWindow().isVisible()) {
                    stageHandler.getInventoryWindow().fadeIn().setVisible(true);
                    FocusManager.switchFocus(stageHandler.getStage(), stageHandler.getInventoryWindow());
                } else {
                    stageHandler.getInventoryWindow().fadeOut();
                }
                return true;
            }
        }

        /*
         * Toggle UI Debug
         */
        if (keycode == Input.Keys.F12) {
            userInterfaceDebug = !userInterfaceDebug;
            for (Actor actor : stageHandler.getStage().getActors()) {
                if (actor instanceof Group) {
                    Group group = (Group) actor;
                    group.setDebug(userInterfaceDebug, true);
                }
            }
            return true;
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
