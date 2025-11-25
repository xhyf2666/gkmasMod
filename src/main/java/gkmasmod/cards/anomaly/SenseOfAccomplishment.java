package gkmasmod.cards.anomaly;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cardCustomEffect.*;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.SenseOfAccomplishmentPower;
import gkmasmod.powers.SenseOfAccomplishmentSPPower;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.CustomHelper;
import gkmasmod.utils.ImageHelper;
import gkmasmod.utils.NameHelper;

import java.util.ArrayList;

public class SenseOfAccomplishment extends GkmasCard {
    private static final String CLASSNAME = SenseOfAccomplishment.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static String IMG_PATH = ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME);

    private static final int COST = 1;

    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorAnomaly;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public SenseOfAccomplishment() {
        super(ID, NAME, ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        IMG_PATH = ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME);
        this.updateShowImg = true;
        this.exhaust = true;
        this.tags.add(GkmasCardTag.PRESERVATION_TAG);
        this.customLimit = 1;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(EffectChangeCustom.growID,new int[]{0},new int[]{50},CustomHelper.CustomEffectType.EFFECT_CHANGE));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(EffectAddCustom.growID,new int[]{1},new int[]{60},CustomHelper.CustomEffectType.PRESERVATION_ADD));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(CustomHelper.hasCustom(this, EffectAddCustom.growID)){
            addToBot(new ChangeStanceAction(PreservationStance.STANCE_ID2));
        }
        else{
            addToBot(new ChangeStanceAction(PreservationStance.STANCE_ID));
        }
        if(CustomHelper.hasCustom(this, EffectChangeCustom.growID)){
            addToBot(new ApplyPowerAction(p,p,new SenseOfAccomplishmentSPPower(p)));
        }
        else{
            addToBot(new ApplyPowerAction(p,p,new SenseOfAccomplishmentPower(p)));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new SenseOfAccomplishment();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.tags.add(GkmasCardTag.MORE_ACTION_TAG);
            CustomHelper.custom(this, MoreActionCustom.growID,1);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
