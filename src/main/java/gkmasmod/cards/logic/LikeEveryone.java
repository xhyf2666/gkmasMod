package gkmasmod.cards.logic;

import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.actions.GoodImpressionDamageAction;
import gkmasmod.cards.AbstractDefaultCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.GoodImpression;
import gkmasmod.powers.GoodTune;
import gkmasmod.ui.SkinSelectScreen;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

public class LikeEveryone extends AbstractDefaultCard {
    private static final String CLASSNAME = LikeEveryone.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static String IMG_PATH = String.format("img/idol/%s/cards/%s.png", SkinSelectScreen.Inst.idolName, CLASSNAME);

    private static final int COST = 1;

    private static final int BASE_MAGIC = 3;
    private static final int UPGRADE_PLUS_MAGIC = -1;
    private static final int BASE_MAGIC2 = 90;
    private static final int UPGRADE_PLUS_MAGIC2 = 30;



    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private String flavor = "";

    public LikeEveryone() {
        super(ID, NAME, String.format("img/idol/%s/cards/%s.png", SkinSelectScreen.Inst.idolName, CLASSNAME), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        IMG_PATH = String.format("img/idol/%s/cards/%s.png", SkinSelectScreen.Inst.idolName, CLASSNAME);
        this.updateShowImg = true;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, CardHelper.getColor(73, 224, 254));
        flavor = FlavorText.CardStringsFlavorField.flavor.get(CARD_STRINGS);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, -this.magicNumber), -this.magicNumber));
        addToBot(new GoodImpressionDamageAction(1.0F * secondMagicNumber / 100, 0, p, m));
    }

    public void applyPowers() {
        super.applyPowers();
        int count = PlayerHelper.getPowerAmount(AbstractDungeon.player, GoodImpression.POWER_ID);
        int damage_ = (int) (1.0F * this.secondMagicNumber * (count + 0) / 100);
        FlavorText.AbstractCardFlavorFields.flavor.set(this, String.format(flavor, calculateDamage(damage_)));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        int count = PlayerHelper.getPowerAmount(p, DexterityPower.POWER_ID);
        if (count >= this.magicNumber)
            return true;
        // TODO 本地化
        this.cantUseMessage = "力量还不够";
        return false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new LikeEveryone();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            upgradeSecondMagicNumber(UPGRADE_PLUS_MAGIC2);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
