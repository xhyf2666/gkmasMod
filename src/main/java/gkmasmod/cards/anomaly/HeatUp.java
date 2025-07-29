package gkmasmod.cards.anomaly;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import gkmasmod.actions.BattlePracticeAction;
import gkmasmod.cardCustomEffect.*;
import gkmasmod.cardGrowEffect.LoseBlockGrow;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.TempSavePower;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.stances.ConcentrationStance;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.CustomHelper;
import gkmasmod.utils.GrowHelper;
import gkmasmod.utils.ImageHelper;
import gkmasmod.utils.NameHelper;

import java.util.ArrayList;

public class HeatUp extends GkmasCard {
    private static final String CLASSNAME = HeatUp.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static String IMG_PATH = ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME);

    private static final int COST = 0;

    private static final int BASE_MAGIC = 3;

    private static final int BASE_MAGIC2 = 2;

    private static final int BASE_MAGIC3 = 1;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorAnomaly;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public HeatUp() {
        super(ID, NAME, ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        IMG_PATH = ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME);
        this.updateShowImg = true;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseThirdMagicNumber = BASE_MAGIC3;
        this.thirdMagicNumber = this.baseThirdMagicNumber;
        this.exhaust = true;
        this.customLimit = 1;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(EffectChangeCustom.growID,new int[]{1},new int[]{50},CustomHelper.CustomEffectType.CONCENTRATION_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(EffectAddCustom.growID,new int[]{1},new int[]{50},CustomHelper.CustomEffectType.PRESERVATION_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(EffectReduceCustom.growID, new int[]{1}, new int[]{60}, CustomHelper.CustomEffectType.TEMP_SAVE_ADD));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractCard c: AbstractDungeon.player.hand.group){
            if(c.type==CardType.ATTACK){
                GrowHelper.grow(c, DamageCustom.growID,this.magicNumber);
                GrowHelper.grow(c, LoseBlockGrow.growID,this.secondMagicNumber);
            }
        }
        for(AbstractCard c:AbstractDungeon.player.drawPile.group){
            if(c.type==CardType.ATTACK){
                GrowHelper.grow(c, DamageCustom.growID,this.magicNumber);
                GrowHelper.grow(c, LoseBlockGrow.growID,this.secondMagicNumber);
            }
        }
        for(AbstractCard c:AbstractDungeon.player.discardPile.group){
            if(c.type==CardType.ATTACK){
                GrowHelper.grow(c, DamageCustom.growID,this.magicNumber);
                GrowHelper.grow(c, LoseBlockGrow.growID,this.secondMagicNumber);
            }
        }
        if(AbstractDungeon.player.hasPower(TempSavePower.POWER_ID)){
            TempSavePower tempSavePower = (TempSavePower) AbstractDungeon.player.getPower(TempSavePower.POWER_ID);
            for(AbstractCard c:tempSavePower.getCards()){
                if(c.type==CardType.ATTACK){
                    GrowHelper.grow(c, DamageCustom.growID,this.magicNumber);
                    GrowHelper.grow(c, LoseBlockGrow.growID,this.secondMagicNumber);
                }
            }
        }
        if(CustomHelper.hasCustom(this, EffectChangeCustom.growID)){
            addToBot(new ChangeStanceAction(ConcentrationStance.STANCE_ID));
        }
        if(CustomHelper.hasCustom(this, EffectAddCustom.growID)){
            addToBot(new ChangeStanceAction(PreservationStance.STANCE_ID));
        }
        if(CustomHelper.hasCustom(this, EffectReduceCustom.growID)){
            addToBot(new BattlePracticeAction(1));
        }
        addToBot(new ApplyPowerAction(p,p,new DrawCardNextTurnPower(p,this.thirdMagicNumber),this.thirdMagicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new HeatUp();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.exhaust = false;
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

}
