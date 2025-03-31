package gkmasmod.cards.anomaly;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.BattlePracticeAction;
import gkmasmod.cardCustomEffect.EffectAddCustom;
import gkmasmod.cardCustomEffect.EffectChangeCustom;
import gkmasmod.cardCustomEffect.MagicCustom;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.*;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.CustomHelper;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.SoundHelper;
import javafx.collections.SetChangeListener;

import java.util.ArrayList;

public class CanYouAccept extends GkmasCard {
    private static final String CLASSNAME = CanYouAccept.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 1;

    private static final int BASE_MAGIC = 1;
    private static final int UPGRADE_MAGIC_PLUS = 1;


    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorAnomaly;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    public CanYouAccept() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.tags.add(GkmasCardTag.PRESERVATION_TAG);
        this.tags.add(GkmasCardTag.IDOL_CARD_TAG);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.backGroundColor = IdolData.hrnm;
        updateBackgroundImg();
        this.customLimit = 1;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(EffectChangeCustom.growID, new int[]{1}, new int[]{70}, CustomHelper.CustomEffectType.PRESERVATION_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(EffectAddCustom.growID,new int[]{0},new int[]{80},CustomHelper.CustomEffectType.EFFECT_ADD));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(CustomHelper.hasCustom(this, EffectChangeCustom.growID)){
            addToBot(new ChangeStanceAction(PreservationStance.STANCE_ID2));
        }
        else {
            addToBot(new ChangeStanceAction(PreservationStance.STANCE_ID));
        }
        if(CustomHelper.hasCustom(this, EffectAddCustom.growID)){
            addToBot(new ApplyPowerAction(p,p,new CanYouAcceptSPPower(p,this.magicNumber),this.magicNumber));
        }
        else{
            addToBot(new ApplyPowerAction(p,p,new CanYouAcceptPower(p,this.magicNumber),this.magicNumber));
        }
        SoundHelper.playSound("gkmasModResource/audio/voice/skillcard/cidol_hrnm_3_013_produce_skillcard_01.ogg");
    }


    @Override
    public AbstractCard makeCopy() {
        return new CanYouAccept();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_PLUS);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
