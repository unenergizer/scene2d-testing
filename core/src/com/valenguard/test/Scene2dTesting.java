package com.valenguard.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.valenguard.test.ui.StageHandler;
import com.valenguard.test.util.Log;

public class Scene2dTesting extends ApplicationAdapter implements InputProcessor {

    private StageHandler stageHandler;

    @Override
    public void create() {
        stageHandler = new StageHandler();

        InputMultiplexer multiplexer = new InputMultiplexer();

        multiplexer.addProcessor(stageHandler);
        multiplexer.addProcessor(stageHandler.getStage());
        multiplexer.addProcessor(this);

        Gdx.input.setInputProcessor(multiplexer);

        stageHandler.init();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stageHandler.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize(int width, int height) {
        stageHandler.resize(width, height);
    }

    @Override
    public void dispose() {
        stageHandler.dispose();
    }

    /*
     ***  Input Multiplexer Handling  ***************************************************
     */

    @Override
    public boolean keyDown(int keycode) {
        Log.println(getClass(), "keyDown: " + keycode, true);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        Log.println(getClass(), "keyUp: " + keycode, true);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        Log.println(getClass(), "keyTyped: " + character, true);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Log.println(getClass(), "touchDown", true);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Log.println(getClass(), "touchUp", true);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Log.println(getClass(), "touchDragged", true);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
//        Log.println(getClass(), "mouseMoved", true);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        Log.println(getClass(), "scrolled: " + amount, true);
        return false;
    }
}
