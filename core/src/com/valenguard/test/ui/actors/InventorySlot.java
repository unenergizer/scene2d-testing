package com.valenguard.test.ui.actors;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.valenguard.test.file.GameAtlas;
import com.valenguard.test.ui.Buildable;
import com.valenguard.test.ui.StageHandler;

public class InventorySlot extends Stack implements Buildable {

    private final StageHandler stageHandler;

    public InventorySlot(StageHandler stageHandler) {
        this.stageHandler = stageHandler;
    }

    @Override
    public Actor build() {
        TextureAtlas textureAtlas = stageHandler.getFileManager().getAtlas(GameAtlas.ITEM_TEXTURES);
        Image image = new Image(textureAtlas.findRegion("potion_01"));
//        image.setSize(16, 16);
        add(image);
        return this;
    }
}
