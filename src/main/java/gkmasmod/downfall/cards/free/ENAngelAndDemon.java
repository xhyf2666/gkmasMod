package gkmasmod.downfall.cards.free;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.free.JustAngel;
import gkmasmod.cards.free.JustDemon;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.downfall.cards.logic.ENBaseVision;
import gkmasmod.powers.AngelAndDemonPlusPower;
import gkmasmod.powers.AngelAndDemonPower;
import gkmasmod.utils.NameHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class ENAngelAndDemon extends GkmasBossCard {
    private static final String CLASSNAME = ENAngelAndDemon.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENAngelAndDemon.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME2);

    private static final int COST = 1;


    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColor;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ENAngelAndDemon() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.cardsToPreview = new ENJustAngel();
        this.cardPreviewList = new ArrayList<>(Arrays.asList(new ENJustAngel(), new ENJustDemon()));
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.upgraded) {
            addToBot(new ApplyPowerAction(m, m, new AngelAndDemonPlusPower(m)));
        }
        else{
            addToBot(new ApplyPowerAction(m,m,new AngelAndDemonPower(m)));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ENAngelAndDemon();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.cardsToPreview.upgrade();
            this.cardPreviewList.get(0).upgrade();
            this.cardPreviewList.get(1).upgrade();
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
