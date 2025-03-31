package gkmasmod.cards.anomaly;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.GetAnswerGrowAction;
import gkmasmod.actions.GetAnswerSelectAction;
import gkmasmod.cardCustomEffect.DamageCustom;
import gkmasmod.cardCustomEffect.ExhaustRemoveCustom;
import gkmasmod.cardCustomEffect.GoodTuneCustom;
import gkmasmod.cardCustomEffect.MoreActionCustom;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.growEffect.AttackTimeGrow;
import gkmasmod.growEffect.DamageGrow;
import gkmasmod.powers.GoodTune;
import gkmasmod.stances.FullPowerStance;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.*;

import java.util.ArrayList;

public class LetMeBecomeIdol extends GkmasCard {
    private static final String CLASSNAME = LetMeBecomeIdol.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 1;

    private static final int BASE_DMG = 10;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int BASE_MAGIC = 1;
    private static final int BASE_GROW = 4;


    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorAnomaly;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private int fullPowerTime = 0;

    public LetMeBecomeIdol() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.tags.add(GkmasCardTag.IDOL_CARD_TAG);
        this.baseDamage = BASE_DMG;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseGrowMagicNumber = BASE_GROW;
        this.growMagicNumber = this.baseGrowMagicNumber;
        this.exhaust = true;
        this.backGroundColor = IdolData.hrnm;
        updateBackgroundImg();
        this.customLimit = 1;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(DamageCustom.growID, new int[]{3}, new int[]{50}, CustomHelper.CustomEffectType.DAMAGE_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(ExhaustRemoveCustom.growID,new int[]{0},new int[]{100},CustomHelper.CustomEffectType.EXHAUST_REMOVE));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        SoundHelper.playSound("gkmasModResource/audio/voice/skillcard/cidol_hrnm_3_010_produce_skillcard_01.ogg");
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(p.stance.ID.equals(FullPowerStance.STANCE_ID)){
            return true;
        }
        if (this.fullPowerTime > 0)
            return true;
        this.cantUseMessage = CardCrawlGame.languagePack.getUIString("gkmasMod:NotEnoughFullPowerStanceTime").TEXT[0];
        return false;
    }


    @Override
    public AbstractCard makeCopy() {
        return new LetMeBecomeIdol();
    }

    @Override
    public void switchedStance() {
        if(AbstractDungeon.player.stance.ID.equals(FullPowerStance.STANCE_ID)){
            this.fullPowerTime++;
            if(this.growMagicNumber > 0){
                upgradeGrowMagicNumber(-1);
                this.initializeDescription();
                GrowHelper.grow(this, AttackTimeGrow.growID,this.magicNumber);
            }
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }




}
