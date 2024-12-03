package gkmasmod.actions;


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.patches.AbstractCreaturePatch;
import gkmasmod.patches.AbstractPlayerPatch;

import java.util.Iterator;

public class SpecialBlockDamageAction extends AbstractGameAction {
    private AbstractCreature m;

    private AbstractCreature p;

    private int blockAdd;

    private float rate;

    private float yarukiRate;

    private boolean damageAll;

    private AbstractCard card;

    public SpecialBlockDamageAction(float rate, int blockAdd, AbstractCreature p, AbstractCreature m, AbstractCard card) {
        this(rate, blockAdd, p, m,card, false);
    }

    public SpecialBlockDamageAction(float rate, int blockAdd, AbstractCreature p, AbstractCreature m, AbstractCard card, boolean damageAll) {
        this(rate, blockAdd, p, m,card,damageAll,1.0f);
    }

    public SpecialBlockDamageAction(float rate, int blockAdd, AbstractCreature p, AbstractCreature m, AbstractCard card, boolean damageAll, float yarukiRate) {
        this.m = m;
        this.p = p;
        this.blockAdd = blockAdd;
        this.rate = rate;
        this.damageAll = damageAll;
        this.card = card;
        this.yarukiRate = yarukiRate;
    }

    public void update() {
        if(yarukiRate>1.0f){
            this.blockAdd += (yarukiRate-1.0f)*this.blockAdd;
        }
        if(this.blockAdd > 0)
            p.addBlock(this.blockAdd);
        int damage = (int)(this.rate* AbstractCreaturePatch.BlockField.ThisCombatBlock.get(p));
        if(damageAll){
            addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(damage, true), DamageInfo.DamageType.NORMAL, AttackEffect.SLASH_HORIZONTAL));
        }
        else{
            addToBot(new ModifyDamageAction(m, new DamageInfo(p, damage , DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_HORIZONTAL,this.card));
        }

        this.isDone = true;
    }

}
