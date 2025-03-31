package gkmasmod.cards.free;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.relics.Waffle;
import gkmasmod.cardCustomEffect.MoreActionCustom;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.charbosses.relics.CBR_LeesWaffle;
import gkmasmod.powers.BoostExtractPower;
import gkmasmod.powers.BurstAttackPower;
import gkmasmod.powers.DoubleDamageReceive;
import gkmasmod.powers.HalfDamageReceive;
import gkmasmod.relics.FirstStarBracelet;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;

import java.util.Iterator;

public class ReallyNotBad extends GkmasCard {
    private static final String CLASSNAME = ReallyNotBad.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColor;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ReallyNotBad() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.exhaust = true;
        this.tags.add(GkmasCardTag.OUTSIDE_TAG);
        this.backGroundColor = IdolData.ttmr;
        updateBackgroundImg();
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        for (Iterator<AbstractCard> it = p.masterDeck.group.iterator(); it.hasNext(); ) {
            AbstractCard c = it.next();
            if (c instanceof ReallyNotBad) {
                it.remove();
                break;
            }
        }
        if (!AbstractDungeon.player.hasRelic(Waffle.ID)) {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH/2,Settings.HEIGHT/2, new Waffle());
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new ReallyNotBad();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
