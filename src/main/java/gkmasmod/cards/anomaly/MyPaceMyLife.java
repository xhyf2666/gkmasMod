package gkmasmod.cards.anomaly;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.cardCustomEffect.BlockCustom;
import gkmasmod.cardCustomEffect.DamageCustom;
import gkmasmod.cards.GkmasCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.stances.FullPowerStance;
import gkmasmod.utils.CustomHelper;
import gkmasmod.utils.NameHelper;

import java.util.ArrayList;

public class MyPaceMyLife extends GkmasCard {
    private static final String CLASSNAME = MyPaceMyLife.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 0;

    private static final int BASE_MAGIC = 2;
    private static final int BASE_MAGIC2 = 2;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorAnomaly;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public MyPaceMyLife() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.isEthereal = true;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.customLimit = 1;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(DamageCustom.growID,new int[]{2},new int[]{40},CustomHelper.CustomEffectType.DAMAGE_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(BlockCustom.growID, new int[]{2}, new int[]{40}, CustomHelper.CustomEffectType.BLOCK_ADD));
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p.stance.ID.equals(FullPowerStance.STANCE_ID)){
            addToBot(new DrawCardAction(this.secondMagicNumber));
        }
    }

    @Override
    public void customTrigger() {
        addToBot(new GainTrainRoundPowerAction(AbstractDungeon.player,this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new MyPaceMyLife();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.isEthereal = false;
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
