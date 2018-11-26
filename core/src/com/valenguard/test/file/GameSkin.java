package com.valenguard.test.file;

@SuppressWarnings({"SameParameterValue", "SpellCheckingInspection"})
public enum GameSkin {

    DEFAULT("uiskin.json");

    private final String filePath;

    GameSkin(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return "skin/" + filePath;
    }
}
