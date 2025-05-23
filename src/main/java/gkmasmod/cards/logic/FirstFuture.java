package gkmasmod.cards.logic;

import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.GoodImpressionDamageAction;
import gkmasmod.cardCustomEffect.*;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.GoodImpression;
import gkmasmod.utils.*;

import java.util.ArrayList;

public class FirstFuture extends GkmasCard {
    private static final String CLASSNAME = FirstFuture.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 0;
    private static final int BASE_MAGIC = 190;
    private static final int UPGRADE_PLUS_MAGIC = 10;
    private static final int BASE_HP = 4;
    private static final int UPGRADE_PLUS_HP = -1;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private String flavor = "";

    public FirstFuture() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, "blue");
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseHPMagicNumber = BASE_HP;
        this.HPMagicNumber = this.baseHPMagicNumber;
        this.exhaust = true;
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, CardHelper.getColor(73, 224, 254));
        flavor = FlavorText.CardStringsFlavorField.flavor.get(CARD_STRINGS);
        this.tags.add(GkmasCardTag.GOOD_IMPRESSION_TAG);
        this.tags.add(GkmasCardTag.IDOL_CARD_TAG);
        this.backGroundColor = IdolData.hski;
        updateBackgroundImg();
        this.customLimit = 3;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(GoodImpressionCustom.growID, new int[]{1,1,1}, new int[]{50,50,50}, CustomHelper.CustomEffectType.GOOD_IMPRESSION_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(MagicCustom.growID, new int[]{20,20}, new int[]{60,60}, CustomHelper.CustomEffectType.RATE_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(ExhaustRemoveCustom.growID,new int[]{0},new int[]{80},CustomHelper.CustomEffectType.EXHAUST_REMOVE));
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, this.HPMagicNumber));
        addToBot(new GoodImpressionDamageAction(1.0F * magicNumber / 100, 0, p, m,this));
        SoundHelper.playSound("gkmasModResource/audio/voice/skillcard/cidol_hski_1_001_produce_skillcard_01.ogg");

    }

    @Override
    public AbstractCard makeCopy() {
        return new FirstFuture();
    }

    public void applyPowers() {
        super.applyPowers();
        int count = PlayerHelper.getPowerAmount(AbstractDungeon.player, GoodImpression.POWER_ID);
        int damage_ = (int) (1.0F * count * this.magicNumber / 100);
        FlavorText.AbstractCardFlavorFields.flavor.set(this, String.format(flavor, damage_));
    }

    public void atTurnStart() {
        int count = PlayerHelper.getPowerAmount(AbstractDungeon.player, GoodImpression.POWER_ID);
        int damage_ = (int) (1.0F * count * this.magicNumber / 100);
        FlavorText.AbstractCardFlavorFields.flavor.set(this, String.format(flavor, damage_));
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            upgradeHPMagicNumber(UPGRADE_PLUS_HP);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
