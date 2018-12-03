package com.valenguard.test.ui.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.kotcrab.vis.ui.FocusManager;
import com.kotcrab.vis.ui.Focusable;
import com.kotcrab.vis.ui.widget.ScrollableTextArea;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.valenguard.test.ui.Buildable;
import com.valenguard.test.ui.StageHandler;

public class ChatWindow extends VisWindow implements Buildable, Focusable {

    private final StageHandler stageHandler;
    private VisScrollPane scrollPane;
    private ScrollableTextArea messagesDisplay;
    private VisTextField messageInput;

    /**
     * Determines if the chat area should be listening to text input.
     */
    private boolean chatToggled = false;

    /**
     * Used to prevent the Window from crashing due to a "line return" being the first message drawn.
     * Issue: https://github.com/libgdx/libgdx/issues/5319
     * Note: The issue is marked as solved, but apparently still happens.
     *
     * UPDATE: This may not be valid anymore as we are using VisWindow components. TODO: Need to retest.
     */
    private boolean displayEmpty = true;

    public ChatWindow(StageHandler stageHandler) {
        super("");
        this.stageHandler = stageHandler;
    }

    @Override
    public Actor build() {
        final int innerPadding = 5;
        pad(innerPadding);
        setResizable(true);
        setPosition(0, 0);
        setWidth(350);
        setHeight(150);

        messagesDisplay = new ScrollableTextArea(null);
        messageInput = new VisTextField(null);
        messageInput.setFocusTraversal(false);

        scrollPane = new VisScrollPane(messagesDisplay);
        scrollPane.setOverscroll(false, false);
        scrollPane.setFlickScroll(false);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollbarsOnTop(true);
        scrollPane.setScrollingDisabled(true, false);

        // Prevent client from typing in message area
        messagesDisplay.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                stageHandler.getStage().setKeyboardFocus(null);
                Gdx.input.setOnscreenKeyboardVisible(false);
                return false;
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
                        chatToggled = false;
                        String message = messageInput.getText();
                        stageHandler.getStage().setKeyboardFocus(null);
                        if (!message.isEmpty()) appendChatMessage("username: " + message);
                        messageInput.setText("");
                        Gdx.input.setOnscreenKeyboardVisible(false);
                        scrollPane.setScrollPercentY(scrollPane.getMaxY());
                        FocusManager.resetFocus(stageHandler.getStage());
                    } else {
                        chatToggled = true;
                        stageHandler.getStage().setKeyboardFocus(messageInput);
                    }
                    return true;
                }
                return false;
            }
        });

        add(messagesDisplay.createCompatibleScrollPane()).grow().expand().fill();
        row();
        add(messageInput).expandX().fillX().padTop(3);

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

    public boolean isChatToggled() {
        return chatToggled;
    }

    public void setChatToggled(boolean chatToggled) {
        this.chatToggled = chatToggled;
    }

    public VisTextField getMessageInput() {
        return messageInput;
    }

    @Override
    public void focusLost() {

    }

    @Override
    public void focusGained() {

    }
}
