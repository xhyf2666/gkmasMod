package gkmasmod.growEffect;

import basemod.abstracts.AbstractCardModifier;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Iterator;

public class DrawCardGrow extends AbstractGrowEffect {

    public static String growID = "DrawCardGrow";

    public DrawCardGrow(int time) {
        this.amount = time;
        growEffectID = growID;
        priority = 15;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " " + String.format(CardCrawlGame.languagePack.getUIString("growEffect:DrawCardGrow").TEXT[0], this.amount);
    }

    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToBot(new DrawCardAction(this.amount));
    }


    @Override
    public AbstractCardModifier makeCopy() {
        return new DrawCardGrow(this.amount);
    }

}
