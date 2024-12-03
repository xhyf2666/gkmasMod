package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.utils.CommonEnum;
import gkmasmod.utils.PlayerHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class EnjoyAfterHotSpringAction extends AbstractGameAction {
    private AbstractCreature p;
    private float rate ;

    public EnjoyAfterHotSpringAction(AbstractCreature p, float rate) {
        this.p = p;
        this.rate = rate;
    }

    public void update() {
        int count = PlayerHelper.getPowerAmount(p, StrengthPower.POWER_ID);
        int amount = (int)(count * rate);
        if(amount>0)
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, amount), amount));
        this.isDone = true;
    }

}
