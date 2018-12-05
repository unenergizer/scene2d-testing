package com.valenguard.test.ui.actors.settings;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.Focusable;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPane;
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPaneAdapter;
import com.valenguard.test.ui.Buildable;
import com.valenguard.test.ui.HideableVisWindow;

public class MainSettingsWindow extends HideableVisWindow implements Buildable, Focusable {

    public MainSettingsWindow() {
        super("Client Settings");
    }

    @Override
    public Actor build() {
        TableUtils.setSpacingDefaults(this);
        addCloseButton();
        setResizable(true);

        final VisTable mainTable = new VisTable();
        mainTable.pad(3);

//        TabbedPane.TabbedPaneStyle style = VisUI.getSkin().get("default", TabbedPane.TabbedPaneStyle.class);
        TabbedPane tabbedPane = new TabbedPane();
        tabbedPane.addListener(new TabbedPaneAdapter() {
            @Override
            public void switchedTab(Tab tab) {
                mainTable.clearChildren();
                mainTable.add(tab.getContentTable()).expand().fill();
            }
        });

        add(tabbedPane.getTable()).expandX().fillX();
        row();
        add(mainTable).expand().fill();

        tabbedPane.add(new TestTab("Game Mechanics"));
        tabbedPane.add(new TestTab("Graphics"));
        tabbedPane.add(new TestTab("Audio"));
        tabbedPane.add(new TestTab("Controls"));
        tabbedPane.add(new TestTab("Social"));

//		debugAll();
        setSize(350, 200);
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

    private class TestTab extends Tab {
        private String title;
        private Table content;

        public TestTab(String title) {
            super(false, false);
            this.title = title;
            content = new VisTable();
            content.add(new VisLabel(title));
        }

        @Override
        public String getTabTitle() {
            return title;
        }

        @Override
        public Table getContentTable() {
            return content;
        }
    }
}
