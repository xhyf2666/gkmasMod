package gkmasmod.downfall.charbosses.relics;

import gkmasmod.downfall.charbosses.actions.common.EnemyGainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.ArtOfWar;

public class CBR_ArtOfWar extends AbstractCharbossRelic {

    public static final String ID = "Art of War";
    private boolean gainEnergyNext = false;
    private boolean firstTurn = false;

    public CBR_ArtOfWar() {
        super(new ArtOfWar());
    }

    public String getUpdatedDescription() {
        return this.owner != null ? this.setDescription(this.owner.chosenClass) : this.setDescription(null);
    }

    private String setDescription(AbstractPlayer.PlayerClass c) {
        return this.DESCRIPTIONS[0] + this.DESCRIPTIONS[1];
    }

    public void updateDescription(AbstractPlayer.PlayerClass c) {
        this.description = this.setDescription(c);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    public void atPreBattle() {
        this.flash();
        this.firstTurn = true;
        this.gainEnergyNext = true;


    }

    public void onTrigger() {
        if (this.gainEnergyNext && !this.firstTurn) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(this.owner, this));
            this.addToBot(new EnemyGainEnergyAction(1));
        }

        this.firstTurn = false;
        this.gainEnergyNext = true;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            this.gainEnergyNext = false;
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_ArtOfWar();
    }
}
