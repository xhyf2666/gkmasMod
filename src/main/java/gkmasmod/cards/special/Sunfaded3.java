package gkmasmod.cards.special;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.cardAction.SunfadedAction;
import gkmasmod.cardCustomEffect.MoreActionCustom;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;

public class Sunfaded3 extends GkmasCard {
    private static final String CLASSNAME = Sunfaded3.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 2;

    private static final int BASE_MAGIC = 2;
    private static final int BASE_MAGIC2 = 2;
    private static final int BASE_MAGIC3 = 1;

    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColor;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Sunfaded3() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseThirdMagicNumber = BASE_MAGIC3;
        this.thirdMagicNumber = this.baseThirdMagicNumber;
        this.tags.add(GkmasCardTag.MORE_ACTION_TAG);
        this.tags.add(GkmasCardTag.COSTUME_TAG);
        CardModifierManager.addModifier(this,new MoreActionCustom(1));
        this.backGroundColor = IdolData.empty;
        updateBackgroundImg();
        this.cardsToPreview = new Sunfaded3_sp();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        p.maxHealth -= this.magicNumber;
        if(p.currentHealth > p.maxHealth) {
            p.currentHealth = p.maxHealth;
        }
        addToBot(new SunfadedAction(3));
    }

    @Override
    public void onChoseThisOption() {
    }

    @Override
    public AbstractCard makeCopy() {
        return new Sunfaded3();
    }

    @Override
    public void upgrade() {

    }


}
