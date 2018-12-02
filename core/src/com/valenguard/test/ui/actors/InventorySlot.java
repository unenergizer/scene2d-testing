package com.valenguard.test.ui.actors;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.kotcrab.vis.ui.widget.VisImage;
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
        VisImage image = new VisImage(textureAtlas.findRegion("potion_01"));
        add(image);
        return this;
    }
}
