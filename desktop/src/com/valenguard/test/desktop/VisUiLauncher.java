package com.valenguard.test.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kotcrabvisui.vis.ui.test.manual.TestApplication;

public class VisUiLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "VisUI test application";
        config.width = 1280;
        config.height = 720;
        new LwjglApplication(new TestApplication(), config);
    }
}
