package gkmasmod.cards.anomaly;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.BattlePracticeAction;
import gkmasmod.cardCustomEffect.ExhaustRemoveCustom;
import gkmasmod.cardCustomEffect.HPMagicCustom;
import gkmasmod.cardCustomEffect.MagicCustom;
import gkmasmod.cardCustomEffect.SecondMagicCustom;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.growEffect.AbstractGrowEffect;
import gkmasmod.growEffect.DamageGrow;
import gkmasmod.powers.AchievementPower;
import gkmasmod.powers.FullPowerValue;
import gkmasmod.utils.*;

import java.util.ArrayList;

public class ThatDayHere extends GkmasCard {
    private static final String CLASSNAME = ThatDayHere.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 0;

    private static final int BASE_HP = 5;
    private static final int UPGRADE_PLUS_HP = -1;

    private static final int BASE_MAGIC = 8;
    private static final int UPGRADE_PLUS_MAGIC = 2;
    private static final int BASE_MAGIC2 = 2;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorAnomaly;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ThatDayHere() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.tags.add(GkmasCardTag.FULL_POWER_TAG);
        this.tags.add(GkmasCardTag.IDOL_CARD_TAG);
        this.baseHPMagicNumber = BASE_HP;
        this.HPMagicNumber = this.baseHPMagicNumber;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.exhaust = true;
        this.backGroundColor = IdolData.hski;
        updateBackgroundImg();
        this.customLimit = 1;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(MagicCustom.growID,new int[]{2},new int[]{60},CustomHelper.CustomEffectType.FULL_POWER_VALUE_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(HPMagicCustom.growID,new int[]{-2},new int[]{60},CustomHelper.CustomEffectType.HP_REDUCE));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(ExhaustRemoveCustom.growID,new int[]{0},new int[]{100},CustomHelper.CustomEffectType.EXHAUST_REMOVE));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p,p, this.HPMagicNumber));
        addToBot(new ApplyPowerAction(p,p,new FullPowerValue(p,this.magicNumber),this.magicNumber));
        addToBot(new BattlePracticeAction(this.secondMagicNumber));
        SoundHelper.playSound("gkmasModResource/audio/voice/skillcard/cidol_hski_3_015_produce_skillcard_01.ogg");
    }

    @Override
    public AbstractCard makeCopy() {
        return new ThatDayHere();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeHPMagicNumber(UPGRADE_PLUS_HP);
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
