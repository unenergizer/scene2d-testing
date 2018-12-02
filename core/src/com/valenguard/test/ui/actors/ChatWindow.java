package com.valenguard.test.ui.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.kotcrab.vis.ui.Focusable;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTextArea;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.valenguard.test.ui.Buildable;
import com.valenguard.test.ui.StageHandler;
import com.valenguard.test.util.Log;

public class ChatWindow extends VisWindow implements Buildable, Focusable {

    private final StageHandler stageHandler;
    private VisTextArea messagesDisplay;
    private VisTextField messageInput;

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
        super("");
        this.stageHandler = stageHandler;
    }

    @Override
    public Actor build() {
        final int innerPadding = 5;
        pad(innerPadding);
        setResizable(true);
        setPosition(0, 0);
        setWidth(300);
        setHeight(200);

        messagesDisplay = new VisTextArea(null);

        VisScrollPane scrollPane = new VisScrollPane(messagesDisplay);
        scrollPane.setOverscroll(false, true);
        scrollPane.setFlickScroll(false);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollbarsOnTop(true);
        scrollPane.setScrollingDisabled(true, false);

        messageInput = new VisTextField(null);
        messageInput.setFocusTraversal(false);

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
                Log.println(ChatWindow.class, "Listener ran");
                if (keycode == Input.Keys.ENTER) {

//                    if (chatToggled) {
//                        chatToggled = false;
//                        Log.println(ChatWindow.class, "chatToggled = false");
//                    } else {
//                        chatToggled = true;
//                        Log.println(ChatWindow.class, "chatToggled = false");
//                    }

                    if (!chatToggled) {
                        Log.println(ChatWindow.class, "chatToggled = false");
                        String message = messageInput.getText();
                        chatToggled = false;
                        stageHandler.getStage().setKeyboardFocus(null);
                        if (!message.isEmpty()) appendChatMessage(message);
                        messageInput.setText("");
                        Gdx.input.setOnscreenKeyboardVisible(false);
                    } else {
                        Log.println(ChatWindow.class, "chatToggled = true");
                        chatToggled = true;
                        stageHandler.getStage().setKeyboardFocus(messageInput);
                    }
                    return true;
                }
                return false;
            }
        });

        add(messagesDisplay).expand().fill().grow();
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
