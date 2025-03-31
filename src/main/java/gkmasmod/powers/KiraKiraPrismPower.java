package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.growEffect.DamageGrow;
import gkmasmod.stances.ConcentrationStance;
import gkmasmod.utils.GrowHelper;
import gkmasmod.utils.NameHelper;

public class KiraKiraPrismPower extends AbstractPower {
    private static final String CLASSNAME = KiraKiraPrismPower.class.getSimpleName();
    // 能力的ID
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);;
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);;

    public KiraKiraPrismPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.priority = 90;

        // 添加一大一小两张能力图
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],amount);
    }


    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if(!(this.owner instanceof AbstractCharBoss)&&card instanceof AbstractBossCard)
            return;
        if(this.owner instanceof AbstractCharBoss&&(!(card instanceof AbstractBossCard)))
            return;
        boolean flag= false;
        if(card.type== AbstractCard.CardType.SKILL){
            if(AbstractDungeon.player.stance.ID.equals(ConcentrationStance.STANCE_ID)){
                ConcentrationStance concentrationStance = (ConcentrationStance) AbstractDungeon.player.stance;
                if(concentrationStance.stage==1){
                    flag=true;
                }
            }
            if(!flag)
                return;
            for(AbstractCard c:AbstractDungeon.player.hand.group){
                if(c.type== AbstractCard.CardType.ATTACK&&c.hasTag(GkmasCardTag.CONCENTRATION_TAG)){
                    GrowHelper.grow(c,DamageGrow.growID,amount);
                }
            }
            for(AbstractCard c:AbstractDungeon.player.drawPile.group){
                if(c.type== AbstractCard.CardType.ATTACK&&c.hasTag(GkmasCardTag.CONCENTRATION_TAG)){
                    GrowHelper.grow(c,DamageGrow.growID,amount);
                }
            }
            for(AbstractCard c:AbstractDungeon.player.discardPile.group){
                if(c.type== AbstractCard.CardType.ATTACK&&c.hasTag(GkmasCardTag.CONCENTRATION_TAG)){
                    GrowHelper.grow(c,DamageGrow.growID,amount);
                }
            }
            for(AbstractCard c:AbstractDungeon.player.exhaustPile.group) {
                if(c.type== AbstractCard.CardType.ATTACK&&c.hasTag(GkmasCardTag.CONCENTRATION_TAG)){
                    GrowHelper.grow(c,DamageGrow.growID,amount);
                }
            }
            if(AbstractDungeon.player.hasPower(TempSavePower.POWER_ID)){
                TempSavePower tempSavePower = (TempSavePower) AbstractDungeon.player.getPower(TempSavePower.POWER_ID);
                for(AbstractCard c:tempSavePower.getCards()){
                    if(c.type== AbstractCard.CardType.ATTACK&&c.hasTag(GkmasCardTag.CONCENTRATION_TAG)){
                        GrowHelper.grow(c,DamageGrow.growID,amount);
                    }
                }
            }
        }
    }
}
