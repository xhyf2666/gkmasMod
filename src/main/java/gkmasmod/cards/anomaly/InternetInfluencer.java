package gkmasmod.cards.anomaly;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cardCustomEffect.*;
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

public class InternetInfluencer extends GkmasCard {
    private static final String CLASSNAME = InternetInfluencer.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static String IMG_PATH = ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME);

    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;

    private static final int BASE_MAGIC = 3;

    private static final int BASE_MAGIC2 = 1;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorAnomaly;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public InternetInfluencer() {
        super(ID, NAME, ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        IMG_PATH = ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME);
        this.updateShowImg = true;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.tags.add(GkmasCardTag.MORE_ACTION_TAG);
        CardModifierManager.addModifier(this,new MoreActionCustom(1));
        this.exhaust = true;
        this.customLimit = 1;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(EffectChangeCustom.growID,new int[]{1},new int[]{50},CustomHelper.CustomEffectType.CONCENTRATION_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(EffectAddCustom.growID,new int[]{1},new int[]{50},CustomHelper.CustomEffectType.PRESERVATION_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(ExhaustRemoveCustom.growID, new int[]{0}, new int[]{70}, CustomHelper.CustomEffectType.EXHAUST_REMOVE));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        GrowHelper.grow(this, BlockCustom.growID,this.magicNumber);
        for(AbstractCard c: AbstractDungeon.player.hand.group){
            if(c.type==CardType.SKILL){
                GrowHelper.grow(c, BlockCustom.growID,this.magicNumber);
            }
        }
        for(AbstractCard c:AbstractDungeon.player.drawPile.group){
            if(c.type==CardType.SKILL){
                GrowHelper.grow(c, BlockCustom.growID,this.magicNumber);
            }
        }
        for(AbstractCard c:AbstractDungeon.player.discardPile.group){
            if(c.type==CardType.SKILL){
                GrowHelper.grow(c, BlockCustom.growID,this.magicNumber);
            }
        }
        if(AbstractDungeon.player.hasPower(TempSavePower.POWER_ID)){
            TempSavePower tempSavePower = (TempSavePower) AbstractDungeon.player.getPower(TempSavePower.POWER_ID);
            for(AbstractCard c:tempSavePower.getCards()){
                if(c.type==CardType.SKILL){
                    GrowHelper.grow(c, BlockCustom.growID,this.magicNumber);
                }
            }
        }
        if(CustomHelper.hasCustom(this, EffectChangeCustom.growID)){
            addToBot(new ChangeStanceAction(ConcentrationStance.STANCE_ID));
        }
        if(CustomHelper.hasCustom(this, EffectAddCustom.growID)){
            addToBot(new ChangeStanceAction(PreservationStance.STANCE_ID));
        }
        addToBot(new DrawCardAction(this.secondMagicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new InternetInfluencer();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

}
