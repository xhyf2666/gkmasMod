package gkmasmod.cards.anomaly;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.cardCustomEffect.*;
import gkmasmod.cardGrowEffect.AbstractGrowEffect;
import gkmasmod.cardGrowEffect.BlockGrow;
import gkmasmod.cardGrowEffect.DamageGrow;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.AchievementPower;
import gkmasmod.powers.FullPowerValue;
import gkmasmod.powers.TempSavePower;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.*;

import java.util.ArrayList;

public class NextTimeMust extends GkmasCard {
    private static final String CLASSNAME = NextTimeMust.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 0;

    private static final int BASE_HP = 5;
    private static final int UPGRADE_PLUS_HP = -2;

    private static final int BASE_MAGIC = 4;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorAnomaly;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    public NextTimeMust() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.tags.add(GkmasCardTag.PRESERVATION_TAG);
        this.tags.add(GkmasCardTag.IDOL_CARD_TAG);
        this.tags.add(GkmasCardTag.COST_HP_TAG);
        this.baseHPMagicNumber = BASE_HP;
        this.HPMagicNumber = this.baseHPMagicNumber;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        this.isInnate = true;
        this.backGroundColor = IdolData.kcna;
        updateBackgroundImg();
        this.customLimit = 1;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(HPMagicCustom.growID,new int[]{-2},new int[]{60},CustomHelper.CustomEffectType.HP_REDUCE));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(EffectAddCustom.growID,new int[]{100},new int[]{60},CustomHelper.CustomEffectType.PRESERVATION_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(MagicCustom.growID,new int[]{1},new int[]{60},CustomHelper.CustomEffectType.GROW_EFFECT_ADD));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.HPMagicNumber > 0){
            addToBot(new LoseHPAction(p,p,this.HPMagicNumber));
        }
        if(CustomHelper.hasCustom(this,EffectAddCustom.growID)){
            addToBot(new ChangeStanceAction(PreservationStance.STANCE_ID2));
        }
        else{
            addToBot(new ChangeStanceAction(PreservationStance.STANCE_ID));
        }
        for(AbstractCard c: AbstractDungeon.player.hand.group){
            if(c.type==CardType.SKILL){
                GrowHelper.grow(c, BlockGrow.growID,this.magicNumber);
            }
        }
        for(AbstractCard c:AbstractDungeon.player.drawPile.group){
            if(c.type==CardType.SKILL){
                GrowHelper.grow(c, BlockGrow.growID,this.magicNumber);
            }
        }
        for(AbstractCard c:AbstractDungeon.player.discardPile.group){
            if(c.type==CardType.SKILL){
                GrowHelper.grow(c, BlockGrow.growID,this.magicNumber);
            }
        }
        if(AbstractDungeon.player.hasPower(TempSavePower.POWER_ID)){
            TempSavePower tempSavePower = (TempSavePower) AbstractDungeon.player.getPower(TempSavePower.POWER_ID);
            for(AbstractCard c:tempSavePower.getCards()){
                if(c.type==CardType.SKILL){
                    GrowHelper.grow(c, BlockGrow.growID,this.magicNumber);
                }
            }
        }
        SoundHelper.playSound("gkmasModResource/audio/voice/skillcard/cidol_kcna_3_017_produce_skillcard_01.ogg");

    }

    @Override
    public AbstractCard makeCopy() {
        return new NextTimeMust();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeHPMagicNumber(UPGRADE_PLUS_HP);
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
