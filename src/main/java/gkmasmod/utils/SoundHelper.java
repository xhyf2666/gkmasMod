package gkmasmod.utils;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.screen.SkinSelectScreen;

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
            map.get(filename).play(Settings.MUSIC_VOLUME * Settings.MASTER_VOLUME*1.3F);
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

    public static String getSongBGM(){
        String bgm = String.format("gkmasModResource/audio/bgm/inst_%s.ogg",
                IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getBgm(SkinSelectScreen.Inst.skinIndex));
        if(AbstractDungeon.player instanceof MisuzuCharacter){
            bgm = String.format("gkmasModResource/audio/bgm/inst_%s_%03d.ogg", IdolData.hmsz,1);
        }
        if(!Gdx.files.internal(bgm).exists())
            bgm = String.format("gkmasModResource/audio/bgm/inst_%s_%03d.ogg",SkinSelectScreen.Inst.idolName,1);
        return bgm;
    }

    public static String getSong(){
        String tmp = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getBgm(SkinSelectScreen.Inst.skinIndex);
        String song;
        if(tmp.startsWith("unit")){
            song = String.format("gkmasModResource/audio/song/%s.ogg",tmp);
            return song;
        }
        song = String.format("gkmasModResource/audio/song/%s_%03d.ogg",SkinSelectScreen.Inst.idolName,
                Integer.parseInt(IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getSong(SkinSelectScreen.Inst.skinIndex)));
        if(AbstractDungeon.player instanceof MisuzuCharacter){
            song = String.format("gkmasModResource/audio/song/%s_%03d.ogg", IdolData.hmsz,2);
        }
        if(!Gdx.files.internal(song).exists()){
            song = String.format("gkmasModResource/audio/song/%s_%03d.ogg",SkinSelectScreen.Inst.idolName,2);
        }
        return song;
    }
}
