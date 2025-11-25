package gkmasmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;

public class ModifyDamageRandomEnemyAction extends AbstractGameAction {
    private DamageInfo info;
    private AbstractCard card;
    private AbstractCreature owner;
    private boolean countTime = false;
    private float goodTuneAffectRate = 1.0F;
    private float strengthAffectRate = 1.0F;

    public ModifyDamageRandomEnemyAction(DamageInfo info, AttackEffect effect) {
        this(info, effect, null, false, 1.0F, 1.0F);
    }

    public ModifyDamageRandomEnemyAction(DamageInfo info, AttackEffect effect, AbstractCard card) {
        this(info, effect, card, false, 1.0F, 1.0F);
    }

    public ModifyDamageRandomEnemyAction(DamageInfo info, AttackEffect effect, AbstractCard card, boolean countTime) {
        this(info, effect, card, countTime, 1.0F, 1.0F);
    }

    public ModifyDamageRandomEnemyAction(DamageInfo info, AttackEffect effect, AbstractCard card, boolean countTime, float strengthAffectRate, float goodTuneAffectRate) {
        this.info = info;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        this.card = card;
        this.countTime = countTime;
        this.strengthAffectRate = strengthAffectRate;
        this.goodTuneAffectRate = goodTuneAffectRate;
    }

    @Override
    public void update() {
        if(this.info.owner instanceof AbstractCharBoss){
            this.target = AbstractDungeon.player;
        }
        else{
            this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        }
        if (this.target != null) {
            addToTop(new ModifyDamageAction(this.target, this.info, this.attackEffect,this.card,this.countTime, this.strengthAffectRate, this.goodTuneAffectRate));
        }
        this.isDone = true;
    }
}
