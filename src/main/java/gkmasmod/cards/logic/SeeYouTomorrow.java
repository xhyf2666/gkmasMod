package gkmasmod.cards.logic;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.cardCustomEffect.*;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.GoodImpression;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.*;

import java.util.ArrayList;

public class SeeYouTomorrow extends GkmasCard {
    private static final String CLASSNAME = SeeYouTomorrow.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 2;
    private static final int BASE_MAGIC = 10;
    private static final int BASE_MAGIC2 = 3;
    private static final int UPGRADE_PLUS_MAGIC2 = 2;
    private static final int BASE_BLOCK = 4;
    private static final int UPGRADE_PLUS_BLOCK = 2;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    public SeeYouTomorrow() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, "color");
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseBlock = BASE_BLOCK;
        this.exhaust = true;
        this.tags.add(GkmasCardTag.GOOD_IMPRESSION_TAG);
        this.tags.add(GkmasCardTag.IDOL_CARD_TAG);
        this.tags.add(CardTags.HEALING);
        this.tags.add(GkmasCardTag.MORE_ACTION_TAG);
        this.backGroundColor = IdolData.hrnm;
        updateBackgroundImg();
        CardModifierManager.addModifier(this,new MoreActionCustom(1));
        this.customLimit = 1;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(BlockCustom.growID, new int[]{3}, new int[]{70}, CustomHelper.CustomEffectType.BLOCK_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(SecondMagicCustom.growID, new int[]{3}, new int[]{70}, CustomHelper.CustomEffectType.GOOD_IMPRESSION_ADD));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int heal = (int) (p.maxHealth*1.0f*this.magicNumber/100);
        addToBot(new HealAction(p, p, heal));
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new ApplyPowerAction(p, p, new GoodImpression(p, this.secondMagicNumber), this.secondMagicNumber));
//        addToBot(new GainTrainRoundPowerAction(p,1));
        SoundHelper.playSound("gkmasModResource/audio/voice/skillcard/cidol_hrnm_3_001_produce_skillcard_01.ogg");

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        int count = PlayerHelper.getPowerAmount(p, GoodImpression.POWER_ID);
        if (count > 0)
            return true;
        this.cantUseMessage = CardCrawlGame.languagePack.getUIString("gkmasMod:NotEnoughGoodImpression").TEXT[0];
        return false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new SeeYouTomorrow();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeSecondMagicNumber(UPGRADE_PLUS_MAGIC2);
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
