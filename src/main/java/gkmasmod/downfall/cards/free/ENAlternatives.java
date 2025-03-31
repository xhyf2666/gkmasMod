package gkmasmod.downfall.cards.free;

import gkmasmod.downfall.bosses.IdolBoss_jsna;
import gkmasmod.downfall.cards.anomaly.ENTakeFlight;
import gkmasmod.downfall.cards.sense.ENTheScenerySawSomeday;
import gkmasmod.downfall.charbosses.actions.common.EnemyMakeTempCardInHandAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.cards.curses.EnNormality;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.bosses.IdolBoss_kllj;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.downfall.cards.sense.ENPopPhrase;
import gkmasmod.utils.NameHelper;

public class ENAlternatives extends GkmasBossCard {
    private static final String CLASSNAME = ENAlternatives.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENAlternatives.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME2);

    private static final int COST = 1;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColor;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ENAlternatives() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.intent = AbstractMonster.Intent.MAGIC;
        this.tags.add(GkmasCardTag.OUTSIDE_TAG);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(m instanceof IdolBoss_kllj){
            AbstractCharBoss.boss.hand.removeCard(EnNormality.ID);
            AbstractCard card = new ENTheScenerySawSomeday();
            card.upgrade();
            addToBot(new EnemyMakeTempCardInHandAction(card));
        }
        if(m instanceof IdolBoss_jsna){
            AbstractCard card=null;
            for(AbstractCard c:AbstractCharBoss.boss.hand.group){
                if(c instanceof ENBasePose){
                    card=c;
                    break;
                }
            }
            AbstractCharBoss.boss.hand.removeCard(ENTakeFlight.ID);
            if(card!=null)
                addToBot(new EnemyMakeTempCardInHandAction(card));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ENAlternatives();
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
