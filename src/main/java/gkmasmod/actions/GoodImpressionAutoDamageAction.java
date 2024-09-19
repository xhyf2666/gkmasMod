package gkmasmod.actions;


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.powers.AllEffort;
import gkmasmod.powers.GoodImpression;
import gkmasmod.powers.OccupyTheWorldPower;
import gkmasmod.utils.PlayerHelper;

import java.util.Iterator;

public class GoodImpressionAutoDamageAction extends AbstractGameAction {

    private AbstractCreature p;

    public GoodImpressionAutoDamageAction(AbstractCreature player) {
        this.p = player;
    }


    public void update() {
        int damage = this.p.getPower(GoodImpression.POWER_ID)==null?0:this.p.getPower(GoodImpression.POWER_ID).amount;
        if(AbstractDungeon.player instanceof IdolCharacter && AbstractDungeon.player.hasPower(AllEffort.POWER_ID)){
            IdolCharacter idol = (IdolCharacter) AbstractDungeon.player;
            if(idol.finalCircleRound.size()>0){
                damage = (int) (1.0F*damage*idol.finalDamageRate);
            }
        }
        if(damage>0){
            addToBot(new ReducePowerAction(p, p, GoodImpression.POWER_ID, 1));
            if(AbstractDungeon.player.hasPower(OccupyTheWorldPower.POWER_ID)){
                addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(damage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.POISON));
            }
            else{
                addToBot(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.POISON));
            }
        }
        else
            addToBot(new ReducePowerAction(p, p, GoodImpression.POWER_ID, 1));

        this.isDone = true;
    }

}
