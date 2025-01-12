package com.badlogic.UniSim2.resources;

public class SoundManager {
    /**
     * this is a utility class for managing game music.
     * Provides static methods to play, stop, and adjust volume for the game music.
     */

    private SoundManager() {
    };

    public static void playMusic() {
        Assets.music.setVolume(0.5f); // annoying me while testing
        Assets.music.setLooping(true);
        Assets.music.play();
    }

    public static float getVolume() {
        return Assets.music.getVolume();
    }

    public static void setVolume(float vol) {
        Assets.music.setVolume(vol);
    }

    public static void stopMusic() {
        Assets.music.stop();
    }

    public static void playClick() {
        Assets.click.play();
    }
}
