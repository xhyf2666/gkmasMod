package gkmasmod.utils;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.core.Settings;

public class SoundHelper {
    public static void playSound(String filename){
        if(!Gdx.files.internal(filename).exists()){
            System.out.println("Sound file not found: " + filename);
            return;
        }
        try{
            new Sfx(filename, false).play(Settings.MUSIC_VOLUME * Settings.MASTER_VOLUME);
        }
        catch (Exception e) {
            System.out.println("Error playing sound: " + filename);
            e.printStackTrace();
        }
    }

    public static void playSoundLarge(String filename){
        new Sfx(filename, false).play(Settings.MUSIC_VOLUME * Settings.MASTER_VOLUME *1.5F);
    }

    public static void playSoundLittle(String filename){
        new Sfx(filename, false).play(Settings.MUSIC_VOLUME * Settings.MASTER_VOLUME *0.5F);
    }
}
