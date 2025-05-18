package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import gkmasmod.actions.GrowAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.stances.ENConcentrationStance;
import gkmasmod.downfall.charbosses.stances.ENPreservationStance;
import gkmasmod.cardGrowEffect.BlockGrow;
import gkmasmod.cardGrowEffect.DamageGrow;
import gkmasmod.stances.ConcentrationStance;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.GrowHelper;
import gkmasmod.utils.NameHelper;

public class UnpredictablePower extends AbstractPower {
    private static final String CLASSNAME = UnpredictablePower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    AbstractCard needCheckCard = null;

    public UnpredictablePower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = Amount;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount,this.amount);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        needCheckCard = card;
    }

    @Override
    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance) {
        if(this.owner instanceof AbstractPlayer){
            if(newStance.ID.equals(PreservationStance.STANCE_ID)){
                if(needCheckCard != null){
                    if(AbstractDungeon.player.hand.contains(needCheckCard)||AbstractDungeon.player.discardPile.contains(needCheckCard)||AbstractDungeon.player.drawPile.contains(needCheckCard)){
                    }
                    else{
                        GrowHelper.grow(needCheckCard, BlockGrow.growID,this.amount);
                    }
                    needCheckCard = null;
                }
                addToTop(new GrowAction(BlockGrow.growID, GrowAction.GrowType.allHand, this.amount));
                addToTop(new GrowAction(BlockGrow.growID, GrowAction.GrowType.allDiscard, this.amount));
            }
            if(newStance.ID.equals(ConcentrationStance.STANCE_ID)){
                if(needCheckCard != null){
                    if(AbstractDungeon.player.hand.contains(needCheckCard)||AbstractDungeon.player.discardPile.contains(needCheckCard)||AbstractDungeon.player.drawPile.contains(needCheckCard)){
                    }
                    else{
                        GrowHelper.grow(needCheckCard, DamageGrow.growID,this.amount);
                    }
                    needCheckCard = null;
                }
                addToTop(new GrowAction(DamageGrow.growID, GrowAction.GrowType.allHand, this.amount));
                addToTop(new GrowAction(DamageGrow.growID, GrowAction.GrowType.allDiscard, this.amount));
            }
        }
        else if(this.owner instanceof AbstractCharBoss){
            if(newStance.ID.equals(ENPreservationStance.STANCE_ID)){
//                if(needCheckCard != null){
//                    if(AbstractDungeon.player.hand.contains(needCheckCard)||AbstractDungeon.player.discardPile.contains(needCheckCard)||AbstractDungeon.player.drawPile.contains(needCheckCard)){
//                    }
//                    else{
//                        GrowHelper.grow(needCheckCard, BlockGrow.growID,this.amount);
//                    }
//                    needCheckCard = null;
//                }
                addToTop(new GrowAction(BlockGrow.growID, GrowAction.GrowType.allHand, this.amount,true));
                addToTop(new GrowAction(BlockGrow.growID, GrowAction.GrowType.allDiscard, this.amount,true));
            }
            if(newStance.ID.equals(ENConcentrationStance.STANCE_ID)){
//                if(needCheckCard != null){
//                    if(AbstractDungeon.player.hand.contains(needCheckCard)||AbstractDungeon.player.discardPile.contains(needCheckCard)||AbstractDungeon.player.drawPile.contains(needCheckCard)){
//                    }
//                    else{
//                        GrowHelper.grow(needCheckCard, DamageGrow.growID,this.amount);
//                    }
//                    needCheckCard = null;
//                }
                addToTop(new GrowAction(DamageGrow.growID, GrowAction.GrowType.allHand, this.amount,true));
                addToTop(new GrowAction(DamageGrow.growID, GrowAction.GrowType.allDiscard, this.amount,true));
            }
        }
    }

}
