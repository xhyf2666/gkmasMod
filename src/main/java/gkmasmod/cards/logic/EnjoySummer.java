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
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import gkmasmod.actions.SpecialBlockDamageAction;
import gkmasmod.cards.GkmasCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.patches.AbstractPlayerPatch;
import gkmasmod.utils.NameHelper;

public class EnjoySummer extends GkmasCard {
    private static final String CLASSNAME = EnjoySummer.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 1;

    private static final int BASE_MAGIC = 80;
    private static final int BASE_MAGIC2 = 160;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    private String flavor = "";

    public EnjoySummer() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, CardHelper.getColor(73, 224, 254));
        flavor = FlavorText.CardStringsFlavorField.flavor.get(CARD_STRINGS);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = p.currentBlock;
        int lost = (int) (1.0f*this.magicNumber*count/100);
        p.currentBlock = count- lost;
        addToBot(new ApplyPowerAction(p, p, new NextTurnBlockPower(p, (int) (1.0f*this.secondMagicNumber*lost/100))));
    }

    public void applyPowers() {
        super.applyPowers();
        int count = AbstractDungeon.player.currentBlock;
        int lost = (int) (1.0f*this.magicNumber*count/100);
        int left = count - lost;
        FlavorText.AbstractCardFlavorFields.flavor.set(this, String.format(flavor, left,(int) (1.0f*this.secondMagicNumber*lost/100)));
    }

    public void triggerOnCardPlayed(AbstractCard cardPlayed) {
        int count = AbstractDungeon.player.currentBlock;
        int lost = (int) (1.0f*this.magicNumber*count/100);
        int left = count - lost;
        FlavorText.AbstractCardFlavorFields.flavor.set(this, String.format(flavor, left,(int) (1.0f*this.secondMagicNumber*lost/100)));
    }

    @Override
    public AbstractCard makeCopy() {
        return  new EnjoySummer();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.selfRetain = true;
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
