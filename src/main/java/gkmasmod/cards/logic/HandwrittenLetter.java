package gkmasmod.cards.logic;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.HandwrittenLetterAction;
import gkmasmod.actions.HardStretchingAction;
import gkmasmod.cardCustomEffect.EffectChangeCustom;
import gkmasmod.cardCustomEffect.MagicCustom;
import gkmasmod.cardCustomEffect.MoreActionCustom;
import gkmasmod.cardCustomEffect.SecondMagicCustom;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.GoodImpression;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.CustomHelper;
import gkmasmod.utils.ImageHelper;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

import java.util.ArrayList;

public class HandwrittenLetter extends GkmasCard {
    private static final String CLASSNAME = HandwrittenLetter.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static String IMG_PATH = ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME);

    private static final int COST = 1;
    private static final int BASE_MAGIC = 2;
    private static final int UPGRADE_PLUS_MAGIC = -1;
    private static final int BASE_MAGIC2 = 2;
    private static final int BLOCK_AMT = 9;
    private static final int UPGRADE_PLUS_BLOCK = 1;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public HandwrittenLetter() {
        super(ID, NAME, ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        IMG_PATH = ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME);
        this.updateShowImg = true;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseBlock = BLOCK_AMT;
        this.block = this.baseBlock;
        this.tags.add(GkmasCardTag.COST_POWER_TAG);
        this.customLimit = 1;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(MagicCustom.growID,new int[]{-1},new int[]{50},CustomHelper.CustomEffectType.GOOD_IMPRESSION_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(EffectChangeCustom.growID,new int[]{0},new int[]{100},CustomHelper.CustomEffectType.EFFECT_CHANGE));
    }



    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.magicNumber>0)
            addToBot(new ApplyPowerAction(p, p, new GoodImpression(p, -this.magicNumber), -this.magicNumber));
        if(CustomHelper.hasCustom(this,EffectChangeCustom.growID)){
            addToBot(new HandwrittenLetterAction(p,this.block,this.freeToPlayOnce, this.energyOnUse+1));
        }
        else{
            addToBot(new GainBlockAction(p, p, this.block));
            addToBot(new GainBlockAction(p, p, this.block));
        }

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        int count = PlayerHelper.getPowerAmount(p, GoodImpression.POWER_ID);
        if (count >= this.magicNumber)
            return true;
        this.cantUseMessage = CardCrawlGame.languagePack.getUIString("gkmasMod:NotEnoughGoodImpression").TEXT[0];
        return false;
    }


    @Override
    public AbstractCard makeCopy() {
        return new HandwrittenLetter();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
