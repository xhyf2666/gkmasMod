package gkmasmod.cards.logic;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import gkmasmod.cardCustomEffect.*;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.SurgingEmotionPower;
import gkmasmod.utils.*;

import java.util.ArrayList;

public class SurgingEmotion extends GkmasCard {
    private static final String CLASSNAME = SurgingEmotion.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 1;

    private static final int BASE_MAGIC = 2;
    private static final int UPGRADE_PLUS_MAGIC = 1;
    private static final int BASE_MAGIC2 = 100;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    public SurgingEmotion() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, "color");
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.exhaust = true;
        this.tags.add(GkmasCardTag.GOOD_IMPRESSION_TAG);
        this.tags.add(GkmasCardTag.IDOL_CARD_TAG);
        this.backGroundColor = IdolData.jsna;
        updateBackgroundImg();
        this.customLimit = 1;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(MagicCustom.growID, new int[]{1}, new int[]{60}, CustomHelper.CustomEffectType.EFFECT_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(DrawCardCustom.growID, new int[]{1}, new int[]{70}, CustomHelper.CustomEffectType.DRAW_CARD_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(InnateCustom.growID, new int[]{0}, new int[]{50}, CustomHelper.CustomEffectType.INNATE_ADD));
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new SurgingEmotionPower(p, this.magicNumber), this.magicNumber));
        SoundHelper.playSound("gkmasModResource/audio/voice/skillcard/cidol_jsna_3_001_produce_skillcard_01.ogg");
    }

    @Override
    public AbstractCard makeCopy() {
        return new SurgingEmotion();
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
