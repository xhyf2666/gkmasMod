package gkmasmod.actions;


import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import java.util.HashMap;

public class NotLeftAnyoneAction extends AbstractGameAction {
    private AbstractCard card;
    private int minAttackTimes;
    private HashMap<AbstractMonster,Integer> attackMonster = new HashMap<>();
    private AbstractCreature owner;

    public NotLeftAnyoneAction(AbstractCreature owner,int minAttackTimes, AbstractCard card) {
        this.owner = owner;
        this.minAttackTimes = minAttackTimes;
        this.card = card;
    }

    public void update() {
        int count =0;
        AbstractCreature target;
        if(this.owner instanceof AbstractCharBoss){
            target = AbstractDungeon.player;
            for (int i = 0; i < this.minAttackTimes; i++) {
                this.addToBot(new VFXAction(new LightningEffect(target.hb.cX, target.hb.cY)));
                this.addToBot(new SFXAction("ORB_LIGHTNING_EVOKE", 0.1F));
                this.addToBot(new DamageAction(target, new DamageInfo(this.owner, this.card.damage, this.card.damageTypeForTurn), AttackEffect.NONE));
            }
            this.isDone = true;
            return;
        }
        for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
            if(!m.isDeadOrEscaped()){
                count++;
            }
        }
        int attackTimes = 0;
        while((attackTimes<minAttackTimes||attackMonster.size()<count)&&attackTimes<99){
            target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            if (target != null) {
                this.card.calculateCardDamage((AbstractMonster) target);
                this.addToBot(new VFXAction(new LightningEffect(target.hb.cX, target.hb.cY)));
                this.addToBot(new SFXAction("ORB_LIGHTNING_EVOKE", 0.1F));
                this.addToBot(new DamageAction(target, new DamageInfo(AbstractDungeon.player, this.card.damage, this.card.damageTypeForTurn), AttackEffect.NONE));
                attackTimes++;
                if (!attackMonster.containsKey(target)) {
                    attackMonster.put((AbstractMonster) target, 1);
                }
            }
            else{
                break;
            }
        }

        this.isDone = true;
    }

}
