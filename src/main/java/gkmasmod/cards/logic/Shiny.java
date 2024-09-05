package gkmasmod.cards.logic;

import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.BlockDamageAction;
import gkmasmod.actions.GoodImpressionDamageAction;
import gkmasmod.cards.AbstractDefaultCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.GoodImpression;
import gkmasmod.ui.SkinSelectScreen;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

public class Shiny extends AbstractDefaultCard {
    private static final String CLASSNAME = Shiny.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static String IMG_PATH = String.format("img/idol/%s/cards/%s.png", SkinSelectScreen.Inst.idolName, CLASSNAME);

    private static final int COST = 1;

    private static final int BASE_MAGIC = 200;
    private static final int UPGRADE_PLUS_MAGIC = 50;
    private static final int BASE_MAGIC2 = 2;

    private static final int BASE_BLOCK = 5;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private String flavor = "";

    public Shiny() {
        super(ID, NAME, String.format("img/idol/%s/cards/%s.png", SkinSelectScreen.Inst.idolName, CLASSNAME), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        IMG_PATH = String.format("img/idol/%s/cards/%s.png", SkinSelectScreen.Inst.idolName, CLASSNAME);
        this.updateShowImg = true;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseBlock = BASE_BLOCK;
        this.block = this.baseBlock;
        this.exhaust = true;
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, CardHelper.getColor(73, 224, 254));
        flavor = FlavorText.CardStringsFlavorField.flavor.get(CARD_STRINGS);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new GoodImpressionDamageAction(1.0F * magicNumber / 100, 0, p, m));
    }

    public void applyPowers() {
        super.applyPowers();
        int count = PlayerHelper.getPowerAmount(AbstractDungeon.player, GoodImpression.POWER_ID);
        int damage_ = (int) (1.0F * this.magicNumber * (count + 0) / 100);
        FlavorText.AbstractCardFlavorFields.flavor.set(this, String.format(flavor, calculateDamage(damage_)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Shiny();
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
