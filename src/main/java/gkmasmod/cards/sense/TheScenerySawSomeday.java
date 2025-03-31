package gkmasmod.cards.sense;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.actions.GoodTuneDamageAction;
import gkmasmod.cardCustomEffect.*;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.GoodTune;
import gkmasmod.powers.TheScenerySawSomedayPower;
import gkmasmod.utils.*;

import java.util.ArrayList;

public class TheScenerySawSomeday extends GkmasCard {
    private static final String CLASSNAME = TheScenerySawSomeday.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 2;
    private static final int BASE_MAGIC = 5;
    private static final int UPGRADE_PLUS_MAGIC = -1;
    private static final int BASE_MAGIC2 = 50;
    private static final int BASE_MAGIC3 = 3;
    private static final int BASE_DMG = 3;
    private static final int UPGRADE_PLUS_DMG = 2;

    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorSense;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public TheScenerySawSomeday() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, "color");
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseDamage = BASE_DMG;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseThirdMagicNumber = BASE_MAGIC3;
        this.thirdMagicNumber = this.baseThirdMagicNumber;
        this.tags.add(GkmasCardTag.IDOL_CARD_TAG);
        this.tags.add(GkmasCardTag.FOCUS_TAG);
        this.backGroundColor = IdolData.kllj;
        updateBackgroundImg();
        this.customLimit = 1;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(MagicCustom.growID, new int[]{-2}, new int[]{60}, CustomHelper.CustomEffectType.STRENGTH_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(CostCustom.growID, new int[]{-1}, new int[]{80}, CustomHelper.CustomEffectType.ENERGY_COST_REDUCE));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(SecondMagicCustom.growID,new int[]{25},new int[]{100},CustomHelper.CustomEffectType.RATE_ADD));
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int amount = PlayerHelper.getPowerAmount(p, StrengthPower.POWER_ID);
        amount = (int) (1.0F*amount*this.secondMagicNumber/100);
        if(amount>0){
            addToBot(new ApplyPowerAction(p,p,new StrengthPower(p,amount),amount));
        }
        addToBot(new ApplyPowerAction(p,p,new TheScenerySawSomedayPower(p,this.baseDamage),this.baseDamage));
        SoundHelper.playSound("gkmasModResource/audio/voice/skillcard/cidol_kllj_3_010_produce_skillcard_01.ogg");
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        int count = PlayerHelper.getPowerAmount(p, StrengthPower.POWER_ID);
        if (count > this.magicNumber)
            return true;
        this.cantUseMessage = CardCrawlGame.languagePack.getUIString("gkmasMod:NotEnoughStrengthPower").TEXT[0];
        return false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new TheScenerySawSomeday();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
