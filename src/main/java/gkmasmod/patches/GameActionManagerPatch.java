package gkmasmod.patches;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.MockMusic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.map.DungeonMap;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.monster.friend.FriendRinha;
import gkmasmod.powers.FriendRinhaPower1;
import gkmasmod.powers.SleepingPower;
import gkmasmod.powers.WantToSleepEnemy;
import gkmasmod.relics.PledgePetal;

public class GameActionManagerPatch {

    @SpirePatch(clz = GameActionManager.class,method = SpirePatch.CLASS)
    public static class FullPowerValueThisCombatField {
        public static StaticSpireField<Integer> fullPowerValueThisCombat = new StaticSpireField<>(() -> 0);
    }

    @SpirePatch(clz= GameActionManager.class, method="callEndOfTurnActions")
    public static class GameActionManagerPostPatch_callEndOfTurnActions {
        public static void Postfix(GameActionManager __instance) {
            for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
                if(!m.isDying && !m.isEscaping&&!AbstractMonsterPatch.friendlyField.friendly.get(m)){
                    if(m.hasPower(WantToSleepEnemy.POWER_ID)){
                        m.getPower(WantToSleepEnemy.POWER_ID).onSpecificTrigger();
                    }
                }
                if(AbstractMonsterPatch.friendlyField.friendly.get(m)&&m.hasPower(FriendRinhaPower1.POWER_ID)){
                    m.getPower(FriendRinhaPower1.POWER_ID).onSpecificTrigger();
                }
            }
        }
    }

    @SpirePatch(clz = GameActionManager.class,method = "clear")
    public static class GameActionManagerPostPatch_clear {
        public static void Postfix(GameActionManager __instance) {
            FullPowerValueThisCombatField.fullPowerValueThisCombat.set(0);
        }
    }
}

