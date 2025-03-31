package gkmasmod.patches;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.utils.IdolData;


public class CampfireUIPatch
{

    private static final String s = "gkmasModResource/img/idol/%s/stand/%s.png";

    @SpirePatch(clz = CampfireUI.class,method = "initializeButtons")
    public static class CampfireUIPrefixPatch_initializeButtons{
        @SpirePrefixPatch
        public static void Prefix(CampfireUI __instance) {
            if(AbstractDungeon.player!=null&&(AbstractDungeon.player instanceof IdolCharacter||AbstractDungeon.player instanceof MisuzuCharacter)){
                if(AbstractDungeon.player instanceof IdolCharacter){
                    IdolCharacter idol = (IdolCharacter) AbstractDungeon.player;
                    String tmp = String.format(s,idol.idolName,idol.idolData.getRandomFireImg());
                    if(Gdx.files.internal(tmp).exists()){
                        idol.shoulderImg = new Texture(tmp);
                        idol.shoulder2Img = new Texture(tmp);
                    }
                }
                if(AbstractDungeon.player instanceof MisuzuCharacter){
                    MisuzuCharacter misuzu = (MisuzuCharacter) AbstractDungeon.player;
                    String tmp = String.format(s, IdolData.hmsz,IdolData.hmszData.getRandomFireImg());
                    if(Gdx.files.internal(tmp).exists()){
                        misuzu.shoulderImg = new Texture(tmp);
                        misuzu.shoulder2Img = new Texture(tmp);
                    }
                }
            }

        }
    }
}

