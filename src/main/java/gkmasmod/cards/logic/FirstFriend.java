package gkmasmod.cards.logic;

import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.ReduceDamageReceive;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.SoundHelper;

public class FirstFriend extends GkmasCard {
    private static final String CLASSNAME = FirstFriend.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 0;

    private static final int BASE_BLOCK = 2;

    private static final int BASE_MAGIC = 150;
    private static final int BASE_MAGIC2 = 10;
    private static final int UPGRADE_PLUS_MAGIC2 = 10;
    private static final int BASE_MAGIC3 = 1;

    private static final int BASE_HP = 5;
    private static final int BASE_HP_UPGRADE = -1;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private String flavor = "";

    public FirstFriend() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, "blue");
        this.baseHPMagicNumber = BASE_HP;
        this.HPMagicNumber = this.baseHPMagicNumber;
        this.baseBlock = BASE_BLOCK;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseThirdMagicNumber = BASE_MAGIC3;
        this.thirdMagicNumber = this.baseThirdMagicNumber;
        this.tags.add(GkmasCardTag.YARUKI_TAG);
        this.tags.add(GkmasCardTag.IDOL_CARD_TAG);
        this.exhaust = true;
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, CardHelper.getColor(73, 224, 254));
        flavor = FlavorText.CardStringsFlavorField.flavor.get(CARD_STRINGS);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, this.HPMagicNumber));
        addToBot(new ApplyPowerAction(p, p, new ReduceDamageReceive(p, this.thirdMagicNumber), this.thirdMagicNumber));
        addToBot(new BlockDamageAction(1.0f*this.secondMagicNumber/100,this.block,p,m,this,false,0));
        SoundHelper.playSound("gkmasModResource/audio/voice/skillcard/cidol_hrnm_1_001_produce_skillcard_01.ogg");

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

    }

    public void applyPowersToBlock() {
        super.applyPowersToBlock();
        int count = AbstractDungeon.player.currentBlock + block;
        int damage_ = (int) (1.0F * count * this.secondMagicNumber / 100);
        FlavorText.AbstractCardFlavorFields.flavor.set(this, String.format(flavor, calculateDamage(damage_)));
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
        return new FirstFriend();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeHPMagicNumber(BASE_HP_UPGRADE);
            upgradeSecondMagicNumber(UPGRADE_PLUS_MAGIC2);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
