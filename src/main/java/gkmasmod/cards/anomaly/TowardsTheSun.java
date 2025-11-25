package gkmasmod.cards.anomaly;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cardCustomEffect.EffectReduceCustom;
import gkmasmod.cardCustomEffect.MagicCustom;
import gkmasmod.cardGrowEffect.LoseHPGrow;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.increaseModifyPower.EnthusiasticAddPower;
import gkmasmod.stances.ConcentrationStance;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.*;

import java.util.ArrayList;

public class TowardsTheSun extends GkmasCard {
    private static final String CLASSNAME = TowardsTheSun.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 0;

    private static final int BASE_HP = 2;
    private static final int UPGRADE_HP_PLUS = -1;
    private static final int BASE_BLOCK = 5;
    private static final int BASE_MAGIC = 2;
    private static final int UPGRADE_MAGIC_PLUS = 1;
    private static final int BASE_MAGIC2 = 1;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorAnomaly;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    public TowardsTheSun() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.tags.add(GkmasCardTag.PRESERVATION_TAG);
        this.tags.add(GkmasCardTag.COST_HP_TAG);
        this.tags.add(GkmasCardTag.IDOL_CARD_TAG);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseHPMagicNumber = BASE_HP;
        this.HPMagicNumber = this.baseHPMagicNumber;
        this.baseBlock = BASE_BLOCK;
        this.backGroundColor = IdolData.shro;
        updateBackgroundImg();
        this.customLimit = 1;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(MagicCustom.growID, new int[]{1}, new int[]{70}, CustomHelper.CustomEffectType.EFFECT_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(EffectReduceCustom.growID,new int[]{0},new int[]{60},CustomHelper.CustomEffectType.EFFECT_REDUCE));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.HPMagicNumber>0){
            addToBot(new LoseHPAction(p,p,this.HPMagicNumber));
        }
        addToBot(new ChangeStanceAction(PreservationStance.STANCE_ID));
        addToBot(new GainBlockAction(p,p,this.block));
        addToBot(new ApplyPowerAction(p,p,new EnthusiasticAddPower(p,this.magicNumber),this.magicNumber));
        GrowHelper.grow(this, LoseHPGrow.growID,this.magicNumber);
        SoundHelper.playSound("gkmasModResource/audio/voice/skillcard/cidol_shro_3_002_produce_skillcard_01.ogg");
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(CustomHelper.hasCustom(this, EffectReduceCustom.growID)){
            return super.canUse(p,m);
        }
        else{
            if(p.stance.ID.equals(ConcentrationStance.STANCE_ID)){
                return super.canUse(p,m);
            }
        }
        this.cantUseMessage = CardCrawlGame.languagePack.getUIString("gkmasMod:NotConcentrationStance").TEXT[0];
        return CardHelper.containsMasterKey();
    }

    @Override
    public AbstractCard makeCopy() {
        return new TowardsTheSun();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeHPMagicNumber(UPGRADE_HP_PLUS);
            upgradeMagicNumber(UPGRADE_MAGIC_PLUS);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
