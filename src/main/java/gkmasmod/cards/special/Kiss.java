package gkmasmod.cards.special;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cards.GkmasCard;
import gkmasmod.monster.exordium.MonsterGekka;
import gkmasmod.patches.AbstractMonsterPatch;
import gkmasmod.powers.EnthusiasticPower;
import gkmasmod.powers.GekkaWill;
import gkmasmod.powers.GoodTune;
import gkmasmod.powers.NotGoodTune;
import gkmasmod.utils.NameHelper;

public class Kiss extends GkmasCard {
    private static final String CLASSNAME = Kiss.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = -2;
    private static final int BASE_MAGIC = 2;
    private static final int BASE_MAGIC2 = 1;

    private static final CardType TYPE = CardType.CURSE;
    private static final CardColor COLOR = CardColor.CURSE;
    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Kiss() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.selfRetain = true;
        this.cardHeader = CardCrawlGame.languagePack.getUIString("gkmasMod:KissHeader").TEXT[0];
    }

    @Override
    public void upgrade() {

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void triggerWhenDrawn() {
        for(AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters){
            if(!monster.isDeadOrEscaped()&&!AbstractMonsterPatch.friendlyField.friendly.get(monster)){
                addToBot(new ApplyPowerAction(monster, AbstractDungeon.player, new EnthusiasticPower(monster, this.magicNumber), this.magicNumber));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Kiss();
    }

}
