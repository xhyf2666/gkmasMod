package gkmasmod.cards.logic;

import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import gkmasmod.actions.BlockDamageAction;
import gkmasmod.actions.DexterityPowerDamageAction;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.ReduceDamageReceive;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;
import gkmasmod.utils.SoundHelper;

public class WithLove extends GkmasCard {
    private static final String CLASSNAME = WithLove.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 2;

    private static final int BASE_BLOCK = 6;

    private static final int BASE_MAGIC = 200;
    private static final int BASE_MAGIC2 = 300;
    private static final int UPGRADE_PLUS_MAGIC2 = 100;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private String flavor = "";

    public WithLove() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, "color");
        this.baseBlock = BASE_BLOCK;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.tags.add(GkmasCardTag.YARUKI_TAG);
        this.tags.add(GkmasCardTag.IDOL_CARD_TAG);
        this.exhaust = true;
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, CardHelper.getColor(73, 224, 254));
        flavor = FlavorText.CardStringsFlavorField.flavor.get(CARD_STRINGS);
        this.backGroundColor = IdolData.kllj;
        updateBackgroundImg();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new DexterityPowerDamageAction(1.0f*this.secondMagicNumber/100,0,p,m,this));
        SoundHelper.playSound("gkmasModResource/audio/voice/skillcard/cidol_kllj_3_008_produce_skillcard_01.ogg");

    }

    public void applyPowers() {
        AbstractPower dexterity = AbstractDungeon.player.getPower(DexterityPower.POWER_ID);
        int amount = 0;
        if (dexterity != null) {
            amount = dexterity.amount;
            dexterity.amount = (int) (dexterity.amount * 1.0f*this.magicNumber/100);
        }

        super.applyPowers();
        if (dexterity != null) {
            dexterity.amount = amount;
        }

        int damage_ = (int) (1.0F * this.secondMagicNumber * (amount) / 100);
        FlavorText.AbstractCardFlavorFields.flavor.set(this, String.format(flavor, calculateDamage(damage_)));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        AbstractPower dexterity = AbstractDungeon.player.getPower(DexterityPower.POWER_ID);
        int amount = 0;
        if (dexterity != null) {
            amount = dexterity.amount;
            dexterity.amount = (int) (dexterity.amount * 1.0f*this.magicNumber/100);
        }
        super.calculateCardDamage(mo);
        if (dexterity != null) {
            dexterity.amount = amount;
        }
    }


    public int calculateBlock(int block) {
        AbstractPower dexterity = AbstractDungeon.player.getPower(DexterityPower.POWER_ID);
        int amount = 0;
        if (dexterity != null) {
            amount = dexterity.amount;
            dexterity.amount = (int) (dexterity.amount * 1.0f*this.magicNumber/100);
        }

        int res = super.calculateBlock(block);
        if (dexterity != null) {
            dexterity.amount = amount;
        }
        return res;
    }

    @Override
    public AbstractCard makeCopy() {
        return new WithLove();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeSecondMagicNumber(UPGRADE_PLUS_MAGIC2);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
