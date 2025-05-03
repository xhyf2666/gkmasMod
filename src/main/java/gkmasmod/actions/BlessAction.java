package gkmasmod.actions;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import gkmasmod.powers.*;

import java.util.ArrayList;

public class BlessAction extends AbstractGameAction {

    private DamageInfo info;

    private static final float DURATION = 0.1F;

    private ArrayList<AbstractPower> blessMap = new ArrayList<>();


    /**
     * 祝福Action：击杀时，获得随机buff
     * @param target 目标
     * @param info   伤害信息
     */
    public BlessAction(AbstractCreature target, DamageInfo info) {
        this.info = info;
        setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.1F;
        blessMap.add(new StrengthPower(AbstractDungeon.player,2));
        blessMap.add(new EnthusiasticPower(AbstractDungeon.player,4));
        blessMap.add(new GoodTune(AbstractDungeon.player,2));
        blessMap.add(new DexterityPower(AbstractDungeon.player,2));
        blessMap.add(new GreatGoodTune(AbstractDungeon.player,1));
        blessMap.add(new MalleablePower(AbstractDungeon.player,3));
        blessMap.add(new MetallicizePower(AbstractDungeon.player,3));
        blessMap.add(new IntangiblePower(AbstractDungeon.player,1));
        blessMap.add(new ThornsPower(AbstractDungeon.player,3));
        blessMap.add(new AfterImagePower(AbstractDungeon.player,1));
        blessMap.add(new DarkEmbracePower(AbstractDungeon.player,1));
        blessMap.add(new DrawPower(AbstractDungeon.player,1));
        blessMap.add(new WishPowerPower(AbstractDungeon.player,1));
        blessMap.add(new EyePowerPower(AbstractDungeon.player,1));
        blessMap.add(new InnocencePower(AbstractDungeon.player,1));
        blessMap.add(new TopEntertainmentPower(AbstractDungeon.player,1));
        blessMap.add(new TopWisdomPower(AbstractDungeon.player,1));
    }

    public void update() {
        if (this.duration == 0.1F &&
                this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.NONE));
            this.target.damage(this.info);
            if (((this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead) {
                int index = AbstractDungeon.cardRandomRng.random(0, blessMap.size()-1);
                if(!this.target.isPlayer)
                    addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,blessMap.get(index)));
                else
                    addToBot(new ApplyPowerAction(AbstractCharBoss.boss,AbstractCharBoss.boss,blessMap.get(index)));
            }
            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();
        }
        tickDuration();
    }
}
