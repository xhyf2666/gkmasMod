package gkmasmod.downfall.cards.anomaly;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.BattlePracticeAction;
import gkmasmod.actions.GrowAction;
import gkmasmod.cardCustomEffect.MagicCustom;
import gkmasmod.cardCustomEffect.MoreActionCustom;
import gkmasmod.cardCustomEffect.SecondMagicCustom;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.bosses.IdolBoss_hume;
import gkmasmod.downfall.bosses.IdolBoss_shro;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.downfall.cards.logic.ENSeriousHobby;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.growEffect.DamageGrow;
import gkmasmod.powers.FullPowerValue;
import gkmasmod.powers.TempSavePower;
import gkmasmod.utils.CustomHelper;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.SoundHelper;

import java.util.ArrayList;

public class ENNewStage extends GkmasBossCard {
    private static final String CLASSNAME = ENNewStage.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENNewStage.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME2);

    private static final int COST = 1;

    private static final int BASE_MAGIC = 1;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    private static final int BASE_MAGIC2 = 3;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorAnomaly;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ENNewStage() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, "color");
        this.tags.add(GkmasCardTag.FULL_POWER_TAG);
        this.tags.add(GkmasCardTag.IDOL_CARD_TAG);
        this.tags.add(GkmasCardTag.MORE_ACTION_TAG);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.intent = AbstractMonster.Intent.MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m,m,new FullPowerValue(m,this.magicNumber),this.magicNumber));
        if(m instanceof IdolBoss_hume){
            AbstractCard card;
            if(AbstractCharBoss.boss.drawPile.size()>0) {
                card = AbstractCharBoss.boss.drawPile.getBottomCard();
                AbstractCharBoss.boss.drawPile.removeCard(card);
            }
            else
                card = new ENShineBright();
            card.upgrade();
            TempSavePower.addCard(m,card);
        }
        if(this.upgraded){
            addToBot(new GrowAction(DamageGrow.growID, GrowAction.GrowType.allTempSave,this.secondMagicNumber,true));
        }
        SoundHelper.playSound("gkmasModResource/audio/voice/skillcard/cidol_hume_3_010_produce_skillcard_01.ogg");
    }


    @Override
    public AbstractCard makeCopy() {
        return new ENNewStage();
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
