package com.valenguard.test.ui.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.kotcrab.vis.ui.Focusable;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.valenguard.test.ui.Buildable;
import com.valenguard.test.ui.HideableVisWindow;

public class EscapeTable extends HideableVisWindow implements Buildable, Focusable {

    public EscapeTable() {
        super("");
    }

    @Override
    public Actor build() {
        setMovable(false);
        TableUtils.setSpacingDefaults(this);
        VisTable table = new VisTable();

        VisTextButton help = new VisTextButton("Help");
        VisTextButton credits = new VisTextButton("Credits");
        VisTextButton settings = new VisTextButton("Settings");
        VisTextButton logout = new VisTextButton("Logout");
        VisTextButton exitGame = new VisTextButton("Exit Game");
        VisTextButton returnToGame = new VisTextButton("Return to Game");
        returnToGame.setColor(Color.GREEN);

        pad(3);

        table.add(help).fill().pad(0, 0, 3, 0);
        table.row();
        table.add(credits).fill().pad(0, 0, 3, 0);
        table.row();
        table.add(settings).fill().pad(0, 0, 3, 0);
        table.row();
        table.add(logout).fill().pad(0, 0, 3, 0);
        table.row();
        table.add(exitGame).fill().pad(0, 0, 6, 0);
        table.row();
        table.add(returnToGame).fill();
        table.row();

        add(table);

        pack();
        centerWindow();

        setVisible(false);
        return this;
    }

    @Override
    public void focusLost() {

    }

    @Override
    public void focusGained() {

    }
}
