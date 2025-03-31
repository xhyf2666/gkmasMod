package gkmasmod.patches;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.MockMusic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.map.DungeonMap;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.BlockReturnPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.downfall.bosses.AbstractIdolBoss;
import gkmasmod.monster.friend.FriendRinha;
import gkmasmod.powers.*;
import gkmasmod.relics.PledgePetal;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

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

    @SpirePatch(clz = GameActionManager.class, method = "getNextAction")
    public static class GameActionManagerInsertPatch_getNextAction {
        @SpireInsertPatch(rloc = 433-210)
        public static void Insert(GameActionManager __instance) {
            if ((AbstractDungeon.getCurrRoom()).skipMonsterTurn){
                if(AbstractDungeon.player.hasPower(TrainRoundLogicPower.POWER_ID)){
                    AbstractDungeon.player.getPower(TrainRoundLogicPower.POWER_ID).atEndOfRound();
                }
            }
        }
    }

    @SpirePatch(clz = GameActionManager.class, method = "getNextAction")
    public static class GameActionManagerInsertPatch_getNextAction2 {
        @SpireInsertPatch(rloc = 451-210)
        public static void Insert(GameActionManager __instance) {
            for(AbstractMonster m:AbstractDungeon.getMonsters().monsters){
                if(!m.isDeadOrEscaped()&&m.hasPower(FriendKotonePower1.POWER_ID)){
                    m.getPower(FriendKotonePower1.POWER_ID).atStartOfTurn();
                }
                if(!m.isDeadOrEscaped()&&m.hasPower(TrueLateBloomerPower.POWER_ID)&&!(m instanceof AbstractIdolBoss)){
                    m.getPower(TrueLateBloomerPower.POWER_ID).onSpecificTrigger();
                }
            }
        }
    }

    @SpirePatch(clz = GameActionManager.class, method = "getNextAction")
    public static class GameActionManagerInsertPatch_getNextAction3 {
        @SpireInsertPatch(rloc = 458-210)
        public static void Insert(GameActionManager __instance) {
        }
    }

//    @SpirePatch(clz = GameActionManager.class, method = "getNextAction")
//    public static class GetNextAction {
//        public static ExprEditor Instrument() {
//            return new ExprEditor() {
//                public void edit(MethodCall m) throws CannotCompileException {
//                    if (m.getClassName().equals(AbstractCreature.class.getName()) && m
//                            .getMethodName().equals("loseBlock"))
//                        m.replace("if (AbstractDungeon.player.hasPower(gkmasmod.powers.TrainRoundLogicPower.POWER_ID)) {$_ = $proceed($$);}");
//                }
//            };
//        }
//    }

    @SpirePatch(clz = GameActionManager.class,method = "clear")
    public static class GameActionManagerPostPatch_clear {
        public static void Postfix(GameActionManager __instance) {
            FullPowerValueThisCombatField.fullPowerValueThisCombat.set(0);
        }
    }
}

