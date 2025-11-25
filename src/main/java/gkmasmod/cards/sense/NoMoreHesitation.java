package gkmasmod.cards.sense;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cardCustomEffect.GoodTuneCustom;
import gkmasmod.cardCustomEffect.InnateCustom;
import gkmasmod.cardCustomEffect.StrengthCustom;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.NoMoreHesitationPower;
import gkmasmod.powers.PracticeAgainPower;
import gkmasmod.utils.CustomHelper;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.SoundHelper;

import java.util.ArrayList;

public class NoMoreHesitation extends GkmasCard {
    private static final String CLASSNAME = NoMoreHesitation.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 0;

    private static int BASE_HP = 4;
    private static int UPGRADE_PLUS_HP = -2;
    private static int BASE_MAGIC = 3;
    private static int BASE_MAGIC2 = 6;
    private static int BASE_MAGIC3 = 200;


    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorSense;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    public NoMoreHesitation() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.baseHPMagicNumber = BASE_HP;
        this.HPMagicNumber = this.baseHPMagicNumber;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseThirdMagicNumber = BASE_MAGIC3;
        this.thirdMagicNumber = this.baseThirdMagicNumber;
        this.tags.add(GkmasCardTag.COST_HP_TAG);
        this.tags.add(GkmasCardTag.IDOL_CARD_TAG);
        this.backGroundColor = IdolData.ssmk;
        updateBackgroundImg();
        this.customLimit = 1;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(InnateCustom.growID,new int[]{0},new int[]{60},CustomHelper.CustomEffectType.INNATE_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(GoodTuneCustom.growID,new int[]{1},new int[]{50},CustomHelper.CustomEffectType.GOOD_TUNE_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(StrengthCustom.growID,new int[]{1},new int[]{60},CustomHelper.CustomEffectType.STRENGTH_ADD));
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.HPMagicNumber>0){
            addToBot(new LoseHPAction(p,p,this.HPMagicNumber));
        }
        addToBot(new ApplyPowerAction(p,p,new NoMoreHesitationPower(p,1),1));
        SoundHelper.playSound("gkmasModResource/audio/voice/skillcard/cidol_ssmk_3_002_produce_skillcard_01.ogg");
    }

    @Override
    public AbstractCard makeCopy() {
        return new NoMoreHesitation();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeHPMagicNumber(UPGRADE_PLUS_HP);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
