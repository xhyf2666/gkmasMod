package gkmasmod.cards.sense;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.GoodTuneReturnAction;
import gkmasmod.actions.ModifyDamageAction;
import gkmasmod.cardCustomEffect.*;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.GoodTune;
import gkmasmod.utils.*;

import java.util.ArrayList;

public class DressedUpInStyle extends GkmasCard {
    private static final String CLASSNAME = DressedUpInStyle.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 2;
    private static final int ATTACK_DMG = 9;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int ATTACK_DMG2 = 3;
    private static final int UPGRADE_PLUS_DMG2 = 3;
    private static final int BASE_MAGIC = 15;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorSense;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public DressedUpInStyle() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, "color");
        this.baseDamage = ATTACK_DMG;
        this.baseSecondDamage = ATTACK_DMG2;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        this.tags.add(GkmasCardTag.GOOD_TUNE_TAG);
        this.tags.add(GkmasCardTag.IDOL_CARD_TAG);
        this.backGroundColor = IdolData.amao;
        updateBackgroundImg();
        this.customLimit = 1;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(MoreActionCustom.growID, new int[]{1}, new int[]{70}, CustomHelper.CustomEffectType.MORE_ACTION_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(EffectAddCustom.growID, new int[]{0}, new int[]{70}, CustomHelper.CustomEffectType.EFFECT_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(EffectChangeCustom.growID, new int[]{0}, new int[]{60}, CustomHelper.CustomEffectType.EFFECT_CHANGE));
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(CustomHelper.hasCustom(this,EffectChangeCustom.growID)){
            addToBot(new GoodTuneReturnAction(m,new DamageInfo(p,this.damage, DamageInfo.DamageType.NORMAL),1.0F*this.magicNumber/100));
        }
        else if(CustomHelper.hasCustom(this,EffectAddCustom.growID)){
            addToBot(new ModifyDamageAction(m, new DamageInfo(p, this.baseDamage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL,this,false,1.0F,2.0F));
            if (PlayerHelper.getPowerAmount(p, GoodTune.POWER_ID) > 0) {
                addToBot(new ModifyDamageAction(m, new DamageInfo(p, this.baseSecondDamage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL,this,false,3.0F,1.0F));
            }
        }
        else{
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            if (PlayerHelper.getPowerAmount(p, GoodTune.POWER_ID) > 0) {
                addToBot(new DamageAction(m, new DamageInfo(p, this.secondDamage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }
        }
        SoundHelper.playSound("gkmasModResource/audio/voice/skillcard/cidol_amao_3_000_produce_skillcard_01.ogg");
    }

    @Override
    public AbstractCard makeCopy() {
        return new DressedUpInStyle();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeSecondDamage(UPGRADE_PLUS_DMG2);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
