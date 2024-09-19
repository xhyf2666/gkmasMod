package gkmasmod.patches;

import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.TempMusic;
import org.lwjgl.Sys;

@SpirePatch(clz = TempMusic.class, method = "getSong")
public class TempMusicPatch {
    @SpirePostfixPatch
    public static SpireReturn<Music> Prefix(TempMusic __instance, String key) {
        if (key.startsWith("gkmasModResource")) {
            System.out.println("Starting custom music: " + key);
            return SpireReturn.Return(MainMusic.newMusic(key));
        }
        return SpireReturn.Continue();
    }
}

