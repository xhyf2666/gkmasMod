package gkmasmod.downfall.cards.free;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.*;
import gkmasmod.downfall.relics.*;
import gkmasmod.utils.NameHelper;

import java.util.ArrayList;

public class ENEatFruit extends GkmasBossCard {
    private static final String CLASSNAME = ENEatFruit.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENEatFruit.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME2);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("gkmasMod:EatFruit");

    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColor;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ENEatFruit() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.exhaust = true;
        this.tags.add(GkmasCardTag.OUTSIDE_TAG);
        this.intent = AbstractMonster.Intent.MAGIC;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCharbossRelic> res = new ArrayList<>();
        res.add(new CBR_Mango());
        res.add(new CBR_Pear());
        res.add(new CBR_Strawberry());
        res.add(new CBR_FoodCarrot());
        res.add(new CBR_FoodIris());
        res.add(new CBR_FoodTonkatsu());
        res.add(new CBR_FoodTonkatsuSP());
        res.add(new CBR_FoodOddMushroom());
        res.add(new CBR_FoodHuhu());
        res.add(new CBR_FoodGinger());
        res.add(new CBR_IceCream());

        AbstractDungeon.cardRandomRng.random(0,res.size()-1);
        AbstractCharbossRelic relic = res.get(AbstractDungeon.cardRandomRng.random(0,res.size()-1));
        relic.instantObtain(AbstractCharBoss.boss);
        String text="";
        if(relic instanceof CBR_Mango)
            text = uiStrings.TEXT[0];
        else if(relic instanceof CBR_Pear)
            text = uiStrings.TEXT[1];
        else if(relic instanceof CBR_Strawberry)
            text = uiStrings.TEXT[2];
        else if(relic instanceof CBR_FoodTonkatsu)
            text = uiStrings.TEXT[3];
        else if(relic instanceof CBR_FoodIris)
            text = uiStrings.TEXT[4];
        else
            text = relic.flavorText;
        AbstractDungeon.effectList.add(new SpeechBubble(this.owner.hb.cX + this.owner.dialogX - 50, this.owner.hb.cY + this.owner.dialogY + 50, 5.0F, text, false));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ENEatFruit();
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
