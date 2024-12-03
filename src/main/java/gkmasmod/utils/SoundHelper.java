package gkmasmod.utils;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;
import java.util.HashMap;

public class SoundHelper {

    private static HashMap<String, Sfx> map = new HashMap<>();

    public static void playSound(String filename){
        if(!Gdx.files.internal(filename).exists()){
            System.out.println("Sound file not found: " + filename);
            return;
        }
        try{
            if(!map.containsKey(filename))
                map.put(filename,new Sfx(filename, false));
            map.get(filename).play(Settings.MUSIC_VOLUME * Settings.MASTER_VOLUME*1.1F);
        }
        catch (Exception e) {
            System.out.println("Error playing sound: " + filename);
            e.printStackTrace();
        }
    }

    public static void clearSound(){
        for(Sfx sfx : map.values()){
            sfx.stop();
        }
        map.clear();
    }

    public static void playSoundLarge(String filename){
        new Sfx(filename, false).play(Settings.MUSIC_VOLUME * Settings.MASTER_VOLUME *1.5F);
    }

    public static void playSoundLittle(String filename){
        new Sfx(filename, false).play(Settings.MUSIC_VOLUME * Settings.MASTER_VOLUME *0.5F);
    }
}
