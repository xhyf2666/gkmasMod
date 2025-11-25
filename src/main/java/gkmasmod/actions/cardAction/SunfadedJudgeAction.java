package gkmasmod.actions.cardAction;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.patches.AbstractMonsterPatch;

import java.util.ArrayList;

public class SunfadedJudgeAction extends AbstractGameAction {
    public SunfadedJudgeAction() {
    }

    public void update() {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        for(AbstractCard c:AbstractDungeon.player.drawPile.group){
            if(c.hasTag(GkmasCardTag.COSTUME_TAG)){
                cards.add(c);
            }
        }
        for(AbstractCard c:cards){
            AbstractDungeon.player.drawPile.moveToExhaustPile(c);
        }
        this.isDone = true;
    }
}
