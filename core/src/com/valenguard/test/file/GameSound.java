package com.valenguard.test.file;

@SuppressWarnings({"SameParameterValue", "unused"})
public enum GameSound {
    EAT("17661_SFX_HumanEatingPotatoChips1.wav");

    private final String filePath;

    GameSound(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return "sounds/" + filePath;
    }
}
