package gkmasmod.cards.free;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cardCustomEffect.BlockCustom;
import gkmasmod.cardCustomEffect.DamageCustom;
import gkmasmod.cardCustomEffect.InnateCustom;
import gkmasmod.cardCustomEffect.MagicCustom;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.ReduceDamageReceive;
import gkmasmod.utils.CustomHelper;
import gkmasmod.utils.NameHelper;

import java.util.ArrayList;

public class LightStep extends GkmasCard {
    private static final String CLASSNAME = LightStep.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 0;
    private static final int BASE_MAGIC = 50;
    private static final int BASE_MAGIC2 = 1;

    private static final int BLOCK_AMT = 2;
    private static final int UPGRADE_PLUS_BLOCK = 2;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColor;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public LightStep() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseBlock = BLOCK_AMT;
        this.exhaust = true;
        this.tags.add(GkmasCardTag.OUTSIDE_TAG);
        this.customLimit = 3;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(BlockCustom.growID,new int[]{2,2,4},new int[]{40,40,70},CustomHelper.CustomEffectType.BLOCK_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(DamageCustom.growID,new int[]{4},new int[]{60},CustomHelper.CustomEffectType.DAMAGE_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(InnateCustom.growID,new int[]{0},new int[]{80},CustomHelper.CustomEffectType.INNATE_ADD));
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((new GainBlockAction(p, p, this.block)));
        float amount = 1.0F * AbstractDungeon.player.currentHealth / AbstractDungeon.player.maxHealth;
        float HP_ = BASE_MAGIC * 1.0F / 100;
        addToBot(new ApplyPowerAction(p, p, new ReduceDamageReceive(p, this.secondMagicNumber), this.secondMagicNumber));

//        if (amount >= HP_) {
//            addToBot(new ApplyPowerAction(p, p, new ReduceDamageReceive(p, this.secondMagicNumber), this.secondMagicNumber));
//        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new LightStep();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
