package gkmasmod.cards.logic;

import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.BlockDamageAction;
import gkmasmod.cardCustomEffect.*;
import gkmasmod.cards.GkmasCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.utils.CustomHelper;
import gkmasmod.utils.NameHelper;

import java.util.ArrayList;

public class CheerfulGreeting extends GkmasCard {
    private static final String CLASSNAME = CheerfulGreeting.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 1;

    private static final int BASE_MAGIC = 70;
    private static final int UPGRADE_PLUS_MAGIC = 10;

    private static final int BASE_HP = 4;
    private static final int UPGRADE_PLUS_HP = -1;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private String flavor = "";

    public CheerfulGreeting() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseHPMagicNumber = BASE_HP;
        this.HPMagicNumber = this.baseHPMagicNumber;
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, CardHelper.getColor(73, 224, 254));
        flavor = FlavorText.CardStringsFlavorField.flavor.get(CARD_STRINGS);
        this.customLimit = 3;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(MagicCustom.growID,new int[]{20,20,40},new int[]{40,40,70},CustomHelper.CustomEffectType.RATE_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(HPMagicCustom.growID,new int[]{-1},new int[]{50},CustomHelper.CustomEffectType.HP_REDUCE));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(DexterityRateAttackCustom.growID,new int[]{70},new int[]{100},CustomHelper.CustomEffectType.DEXTERITY_RATE_ATTACK));
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, this.HPMagicNumber));
        addToBot(new BlockDamageAction(1.0F * this.magicNumber / 100, 0, p, m,this,true,0));
    }

    public void applyPowersToBlock() {
        super.applyPowersToBlock();
        int count = AbstractDungeon.player.currentBlock;
        int damage_ = (int) (1.0F * count * this.magicNumber / 100);
        FlavorText.AbstractCardFlavorFields.flavor.set(this, String.format(flavor, calculateDamage(damage_)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new CheerfulGreeting();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            upgradeHPMagicNumber(UPGRADE_PLUS_HP);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
