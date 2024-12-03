package gkmasmod.cards.free;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.HuHuAction;
import gkmasmod.cards.GkmasCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;

import java.util.Iterator;

public class SpecialHuHu extends GkmasCard {
    private static final String CLASSNAME = SpecialHuHu.class.getSimpleName();
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

    public SpecialHuHu() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"yellow");

        if(SkinSelectScreen.Inst.idolName== IdolData.hski || SkinSelectScreen.Inst.idolName== IdolData.hume){
            this.baseMagicNumber = 6;
            this.magicNumber = this.baseMagicNumber;
            this.baseSecondMagicNumber = 0;
            this.secondMagicNumber = this.baseSecondMagicNumber;
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings("gkmasMod:SpecialHuHuHanami").DESCRIPTION;
        }
        else if(SkinSelectScreen.Inst.idolName == IdolData.ttmr){
            this.baseMagicNumber = 8;
            this.magicNumber = this.baseMagicNumber;
            this.baseSecondMagicNumber = 2;
            this.secondMagicNumber = this.baseSecondMagicNumber;
        }
        else{
            this.baseMagicNumber = 8;
            this.magicNumber = this.baseMagicNumber;
            this.baseSecondMagicNumber = 1;
            this.secondMagicNumber = this.baseSecondMagicNumber;
        }
        this.initializeDescription();
        this.misc = this.magicNumber;
        this.exhaust = true;
        this.tags.add(CardTags.HEALING);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.misc > 0){
            addToBot(new HealAction(p,p,this.misc));
            if(this.misc-this.secondMagicNumber>0){
                addToBot(new HuHuAction(this.uuid, this.misc, -this.secondMagicNumber));
            }
            else{
                addToBot(new HuHuAction(this.uuid, this.misc, -this.misc));
                if(SkinSelectScreen.Inst.idolName == IdolData.ttmr){
                    this.name = CardCrawlGame.languagePack.getCardStrings("gkmasMod:SpecialHuHuAngry").NAME;
                    this.rawDescription = CardCrawlGame.languagePack.getCardStrings("gkmasMod:SpecialHuHuAngry").DESCRIPTION;
                    this.initializeDescription();

                    Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();
                    AbstractCard c;
                    while(var1.hasNext()) {
                        c = (AbstractCard)var1.next();
                        if (c.uuid.equals(this.uuid)) {
                            c.name = CardCrawlGame.languagePack.getCardStrings("gkmasMod:SpecialHuHuAngry").NAME;
                            c.rawDescription = CardCrawlGame.languagePack.getCardStrings("gkmasMod:SpecialHuHuAngry").DESCRIPTION;
                            c.initializeDescription();
                        }
                    }
                }
            }
        }
        if(this.misc <= 0 && SkinSelectScreen.Inst.idolName == IdolData.ttmr){
            addToBot(new ChangeStanceAction("Wrath"));
            // TODO 播放语音
        }
    }

    public void atTurnStart(){
        this.baseMagicNumber = this.misc;
        this.magicNumber = this.baseMagicNumber;
        if(SkinSelectScreen.Inst.idolName == IdolData.ttmr && this.misc <= 0){
            this.name = CardCrawlGame.languagePack.getCardStrings("gkmasMod:SpecialHuHuAngry").NAME;
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings("gkmasMod:SpecialHuHuAngry").DESCRIPTION;
            this.initializeDescription();

            Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();
            AbstractCard c;
            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                if (c.uuid.equals(this.uuid)) {
                    c.name = CardCrawlGame.languagePack.getCardStrings("gkmasMod:SpecialHuHuAngry").NAME;
                    c.rawDescription = CardCrawlGame.languagePack.getCardStrings("gkmasMod:SpecialHuHuAngry").DESCRIPTION;
                    c.initializeDescription();
                }
            }
        }
    }


    public void applyPowers() {
        this.baseMagicNumber = this.misc;
        this.magicNumber = this.baseMagicNumber;
        if(SkinSelectScreen.Inst.idolName == IdolData.ttmr && this.misc <= 0){
            this.name = CardCrawlGame.languagePack.getCardStrings("gkmasMod:SpecialHuHuAngry").NAME;
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings("gkmasMod:SpecialHuHuAngry").DESCRIPTION;
            this.initializeDescription();

            Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();
            AbstractCard c;
            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                if (c.uuid.equals(this.uuid)) {
                    c.name = CardCrawlGame.languagePack.getCardStrings("gkmasMod:SpecialHuHuAngry").NAME;
                    c.rawDescription = CardCrawlGame.languagePack.getCardStrings("gkmasMod:SpecialHuHuAngry").DESCRIPTION;
                    c.initializeDescription();
                }
            }
        }
        super.applyPowers();
    }

    @Override
    public AbstractCard makeCopy() {
        return new SpecialHuHu();
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
