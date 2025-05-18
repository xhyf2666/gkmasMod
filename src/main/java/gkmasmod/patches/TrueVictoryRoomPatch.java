package gkmasmod.patches;

import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.TrueVictoryRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import gkmasmod.actions.CanStopMediaPlayerAction;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;
import gkmasmod.vfx.SimplePlayVideoEffect;

public class TrueVictoryRoomPatch {
    public static boolean notShowPlayerPowerTip = false;

    @SpirePatch2(clz = TrueVictoryRoom.class, method = "onPlayerEntry")
    public static class PrePatchTrueVictoryRoom_onPlayerEntry {
        public static SpireReturn<Void> Prefix() {
            if (AbstractDungeon.player instanceof IdolCharacter) {
                if(!existsVideo()){
                    return SpireReturn.Continue();
                }
                String videoPath = String.format("gkmasModResource/video/live/%s_%s.webm", SkinSelectScreen.Inst.idolName, IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getLive(SkinSelectScreen.Inst.skinIndex));

                AbstractDungeon.isScreenUp = true;
                AbstractDungeon.overlayMenu.proceedButton.hide();
                CardCrawlGame.fadeIn(1.0F);
                CardCrawlGame.music.dispose();
                if(Gdx.files.local(videoPath).exists()){
                    AbstractDungeon.actionManager.addToBottom(new CanStopMediaPlayerAction(new SimplePlayVideoEffect(videoPath,true)));
                }

                notShowPlayerPowerTip = true;
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(clz = TrueVictoryRoom.class, method = "update")
    public static class PrePatchTrueVictoryRoom_update {
        public static SpireReturn<Void> Prefix() {
            if (AbstractDungeon.player instanceof IdolCharacter){
                if(!existsVideo()){
                    return SpireReturn.Continue();
                }
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(clz = TrueVictoryRoom.class, method = "render")
    public static class PrePatchTrueVictoryRoom_render {
        public static SpireReturn<Void> Prefix() {
            if (AbstractDungeon.player instanceof IdolCharacter){
                if(!existsVideo()){
                    return SpireReturn.Continue();
                }
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(clz = TrueVictoryRoom.class, method = "renderAboveTopPanel")
    public static class PrePatchTrueVictoryRoom_renderAboveTopPanel {
        public static SpireReturn<Void> Prefix() {
            if (AbstractDungeon.player instanceof IdolCharacter){
                if(!existsVideo()){
                    return SpireReturn.Continue();
                }
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    public static boolean existsVideo(){
        String videoPath = String.format("gkmasModResource\\video\\live\\%s_%s.webm", SkinSelectScreen.Inst.idolName, IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getLive(SkinSelectScreen.Inst.skinIndex));
        if(Gdx.files.local(videoPath).exists()){
            return true;
        }
        return false;
    }
}
