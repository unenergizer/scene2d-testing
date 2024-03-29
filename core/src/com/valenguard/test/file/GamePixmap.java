package com.valenguard.test.file;

@SuppressWarnings({"SameParameterValue", "SpellCheckingInspection"})
public enum GamePixmap {

    CURSOR_1("cursor1.png");

    private final String filePath;

    GamePixmap(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return "pixmap/" + filePath;
    }
}
