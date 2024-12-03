package gkmasmod.utils;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RestartHelper {

    public static void restartRoom() {
        stopLingeringSounds();
        AbstractDungeon.closeCurrentScreen();
        AbstractDungeon.dungeonMapScreen.closeInstantly();
        AbstractDungeon.reset();
        CardCrawlGame.loadingSave = true;
        CardCrawlGame.mode = CardCrawlGame.GameMode.CHAR_SELECT;
    }

    public static void stopLingeringSounds() {
        CardCrawlGame.music.fadeAll();
        if (Settings.AMBIANCE_ON)
            CardCrawlGame.sound.stop("WIND");
        if (AbstractDungeon.scene != null)
            AbstractDungeon.scene.fadeOutAmbiance();
    }
}
