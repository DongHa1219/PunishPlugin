package org.soonsal.punishplugin.util;

import org.bukkit.Sound;

public class SoundHolder {
    private final Sound sound;
    private final float volume;
    private final float pitch;

    public SoundHolder(Sound sound, float volume, float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public static void init() {

    }

    public Sound getSound() {
        return sound;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }
}
