package com.valenguard.test.ui.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.valenguard.test.ui.Buildable;
import com.valenguard.test.ui.MovableWindow;
import com.valenguard.test.ui.StageHandler;

public class ChatWindow extends MovableWindow implements Buildable {

    private final StageHandler stageHandler;
    private TextArea messagesDisplay;
    private TextField messageInput;

    /**
     * Determines if the chat area should be listening to text input.
     */
    private boolean chatToggled = false;

    /**
     * Used to prevent the Window from crashing due to a "line return" being the first message drawn.
     * Issue: https://github.com/libgdx/libgdx/issues/5319
     * Note: The issue is marked as solved, but apparently still happens.
     */
    private boolean displayEmpty = true;

    public ChatWindow(StageHandler stageHandler) {
        super(stageHandler.getSkin());
        this.stageHandler = stageHandler;
    }

    @Override
    public Actor build() {
        final int innerPadding = 5;
        pad(innerPadding);
        setResizable(true);
        setColor(Color.BLUE);

//        setPosition(stageHandler.getStage().getViewport().getScreenWidth() - this.getWidth(), 0);

        Table table = new Table();
        table.setFillParent(true);
        table.setColor(0, 0, 0, .5f);

        addActor(table);
        setWidth(300);
        setHeight(200);

        messagesDisplay = new TextArea(null, getSkin());
        ScrollPane scrollPane = new ScrollPane(messagesDisplay, getSkin());
        messageInput = new TextField(null, getSkin());
        messageInput.setFocusTraversal(false);

        // Prevent client from typing in message area
        messagesDisplay.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                stageHandler.getStage().setKeyboardFocus(null);
                Gdx.input.setOnscreenKeyboardVisible(false);
                return true;
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                stageHandler.getStage().setKeyboardFocus(null);
                Gdx.input.setOnscreenKeyboardVisible(false);
                return true;
            }
        });

        // Toggled input via mouse
        messageInput.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                chatToggled = true;
                stageHandler.getStage().setKeyboardFocus(messageInput);
                return true;
            }
        });

        // This main listener. Check for the enter key (chat toggle) here.
        addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    if (chatToggled) {
                        String message = messageInput.getText();
                        chatToggled = false;
                        stageHandler.getStage().setKeyboardFocus(null);
                        if (!message.isEmpty()) appendChatMessage(message);
                        messageInput.setText("");
                        Gdx.input.setOnscreenKeyboardVisible(false);
                    } else {
                        chatToggled = true;
                        stageHandler.getStage().setKeyboardFocus(messageInput);
                    }
                    return true;
                }
                return false;
            }
        });

        table.add(scrollPane).expandX().expandY().fill().pad(0 + innerPadding, 1 + innerPadding, 5, 1 + innerPadding);
        table.row();
        table.add(messageInput).expandX().fill().pad(0 + innerPadding, 1 + innerPadding, 0 + innerPadding, 1 + innerPadding);
        return this;
    }

    private void appendChatMessage(String message) {
        if (displayEmpty) {
            displayEmpty = false;
            messagesDisplay.appendText(message);
        } else {
            // Put the "line return" BEFORE the message to make sure the window
            // does not have a blank line (line return) as the bottom message.
            messagesDisplay.appendText("\n" + message);
        }
    }
}
