package gkmasmod.cards.sense;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.unique.SkewerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.cardCustomEffect.AttackTimeCustom;
import gkmasmod.cardCustomEffect.DamageCustom;
import gkmasmod.cards.GkmasCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.GoodTune;
import gkmasmod.powers.GreatNotGoodTune;
import gkmasmod.utils.CustomHelper;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

import java.util.ArrayList;

public class TemporaryTruce extends GkmasCard {
    private static final String CLASSNAME = TemporaryTruce.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = -1;
    private static final int ATTACK_DMG = 7;
    private static final int BASE_MAGIC = 1;


    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorSense;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public TemporaryTruce() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = ATTACK_DMG;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.customLimit = 1;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(AttackTimeCustom.growID,new int[]{1},new int[]{100},CustomHelper.CustomEffectType.ATTACK_TIME_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(DamageCustom.growID,new int[]{4},new int[]{60},CustomHelper.CustomEffectType.DAMAGE_ADD));
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.upgraded){
            addToBot(new SkewerAction(p, m, this.damage, this.damageTypeForTurn, this.freeToPlayOnce, this.energyOnUse+1));
        }
        else{
            addToBot(new SkewerAction(p, m, this.damage, this.damageTypeForTurn, this.freeToPlayOnce, this.energyOnUse));
        }
        addToBot(new ApplyPowerAction(p,p,new GreatNotGoodTune(p,this.magicNumber),this.magicNumber));
        for(AbstractMonster monster: AbstractDungeon.getMonsters().monsters){
            if(!monster.isDeadOrEscaped()){
                addToBot(new ApplyPowerAction(monster,p,new GreatNotGoodTune(monster,this.magicNumber),this.magicNumber));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return  new TemporaryTruce();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
