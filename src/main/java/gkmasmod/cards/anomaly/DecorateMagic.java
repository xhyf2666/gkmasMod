package gkmasmod.cards.anomaly;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.common.ModifyDamageAction;
import gkmasmod.cardCustomEffect.BlockCustom;
import gkmasmod.cardCustomEffect.MagicCustom;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.patches.GameActionManagerPatch;
import gkmasmod.powers.FullPowerValue;
import gkmasmod.utils.*;

import java.util.ArrayList;

public class DecorateMagic extends GkmasCard {
    private static final String CLASSNAME = DecorateMagic.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 0;

    private static final int BASE_HP = 2;
    private static final int UPGRADE_HP_PLUS = -1;
    private static final int BASE_BLOCK = 5;
    private static final int BASE_MAGIC = 1;
    private static final int UPGRADE_MAGIC_PLUS = 1;
    private static final int BASE_MAGIC2 = 100;
    private static final int UPGRADE_MAGIC2_PLUS = 50;


    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorAnomaly;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public DecorateMagic() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.tags.add(GkmasCardTag.FULL_POWER_TAG);
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
        this.exhaust = true;
        updateBackgroundImg();
        this.customLimit = 1;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(MagicCustom.growID, new int[]{1}, new int[]{40}, CustomHelper.CustomEffectType.FULL_POWER_VALUE_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(BlockCustom.growID,new int[]{4},new int[]{80},CustomHelper.CustomEffectType.BLOCK_ADD));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.HPMagicNumber>0){
            addToBot(new LoseHPAction(p,p,this.HPMagicNumber));
        }
        addToBot(new GainBlockAction(p,p,this.block));
        addToBot(new ApplyPowerAction(p,p,new FullPowerValue(p,this.magicNumber),this.magicNumber));
        int count = GameActionManagerPatch.FullPowerValueThisCombatField.fullPowerValueThisCombat.get();
        count = (int) (1.0F*count * this.secondMagicNumber / 100);
        if(count>0)
            addToBot(new ModifyDamageAction(m,new DamageInfo(p,count,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL,this,true));
        SoundHelper.playSound("gkmasModResource/audio/voice/skillcard/cidol_shro_3_018_produce_skillcard_01.ogg");
    }

    @Override
    public AbstractCard makeCopy() {
        return new DecorateMagic();
    }

    public void growValue(int fullPowerValue){
        this.baseMagicNumber += fullPowerValue;
        this.magicNumber += this.baseMagicNumber;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeHPMagicNumber(UPGRADE_HP_PLUS);
            upgradeMagicNumber(UPGRADE_MAGIC_PLUS);
            upgradeSecondMagicNumber(UPGRADE_MAGIC2_PLUS);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
