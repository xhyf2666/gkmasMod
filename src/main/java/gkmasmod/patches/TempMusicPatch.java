package gkmasmod.patches;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.MockMusic;
import com.megacrit.cardcrawl.audio.TempMusic;
import org.lwjgl.Sys;

@SpirePatch(clz = TempMusic.class, method = "getSong")
public class TempMusicPatch {
    @SpirePrefixPatch
    public static SpireReturn<Music> Prefix(TempMusic __instance, String key) {
        if (key.startsWith("gkmasModResource")) {
//            System.out.println("Starting custom music: " + key);
            return SpireReturn.Return(newMusic(key));
        }
        return SpireReturn.Continue();
    }

    public static Music newMusic(String path) {
        if (Gdx.audio == null) {
            return new MockMusic();
        } else
        {
            if(Gdx.files.internal(path).exists()){
                return Gdx.audio.newMusic(Gdx.files.internal(path));
            } else {
                return Gdx.audio.newMusic(Gdx.files.local(path));
            }
        }
    }
}

