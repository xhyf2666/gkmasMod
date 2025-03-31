package gkmasmod.cards.anomaly;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.FullPowerValueAction;
import gkmasmod.cardCustomEffect.EffectAddCustom;
import gkmasmod.cardCustomEffect.EffectChangeCustom;
import gkmasmod.cardCustomEffect.FullPowerValueCustom;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.LikeUsualPower;
import gkmasmod.powers.LikeUsualSPPower;
import gkmasmod.powers.StepOnStagePower;
import gkmasmod.utils.CustomHelper;
import gkmasmod.utils.NameHelper;

import java.util.ArrayList;

public class LikeUsual extends GkmasCard {
    private static final String CLASSNAME = LikeUsual.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 1;
    private static final int UPGRADE_BASE_COST = 0;

    private static final int BASE_MAGIC = 1;

    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorAnomaly;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public LikeUsual() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(GkmasCardTag.CONCENTRATION_TAG);
        this.tags.add(GkmasCardTag.PRESERVATION_TAG);
        this.customLimit = 1;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(FullPowerValueCustom.growID,new int[]{1},new int[]{50},CustomHelper.CustomEffectType.FULL_POWER_VALUE_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(EffectAddCustom.growID, new int[]{0}, new int[]{80}, CustomHelper.CustomEffectType.EFFECT_ADD));
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(CustomHelper.hasCustom(this, EffectAddCustom.growID)){
            addToBot(new ApplyPowerAction(p,p,new LikeUsualSPPower(p,this.magicNumber),this.magicNumber));
        }
        else{
            addToBot(new ApplyPowerAction(p,p,new LikeUsualPower(p,this.magicNumber),this.magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new LikeUsual();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_BASE_COST);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
