package gkmasmod.utils;

import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.core.Settings;

public class SoundHelper {
    public static void playSound(String filename){
        new Sfx(filename, false).play(Settings.MUSIC_VOLUME * Settings.MASTER_VOLUME);
    }

    public static void playSoundLarge(String filename){
        new Sfx(filename, false).play(Settings.MUSIC_VOLUME * Settings.MASTER_VOLUME *1.5F);
    }

    public static void playSoundLittle(String filename){
        new Sfx(filename, false).play(Settings.MUSIC_VOLUME * Settings.MASTER_VOLUME *0.5F);
    }
}
