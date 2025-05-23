package gkmasmod.cards.logic;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cardCustomEffect.MagicCustom;
import gkmasmod.cardCustomEffect.SecondMagicCustom;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.GoodImpression;
import gkmasmod.powers.ReservedGirlPower;
import gkmasmod.utils.CustomHelper;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.SoundHelper;

import java.util.ArrayList;

public class ReservedGirl extends GkmasCard {
    private static final String CLASSNAME = ReservedGirl.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 1;

    private static final int BASE_MAGIC = 1;
    private static final int UPGRADE_PLUS_MAGIC = 1;
    private static final int BASE_MAGIC2 = 1;

    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ReservedGirl() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, "blue");
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.tags.add(GkmasCardTag.GOOD_IMPRESSION_TAG);
        this.tags.add(GkmasCardTag.IDOL_CARD_TAG);
        this.backGroundColor = IdolData.kllj;
        updateBackgroundImg();
        this.customLimit = 3;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(MagicCustom.growID, new int[]{2,2}, new int[]{60,60}, CustomHelper.CustomEffectType.GOOD_IMPRESSION_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(SecondMagicCustom.growID, new int[]{1}, new int[]{60}, CustomHelper.CustomEffectType.GOOD_IMPRESSION_ADD));
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new GoodImpression(p, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new ReservedGirlPower(p, this.secondMagicNumber), this.secondMagicNumber));
        SoundHelper.playSound("gkmasModResource/audio/voice/skillcard/cidol_kllj_1_000_produce_skillcard_01.ogg");

    }

    @Override
    public AbstractCard makeCopy() {
        return (AbstractCard) new ReservedGirl();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
