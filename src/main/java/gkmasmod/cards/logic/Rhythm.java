package gkmasmod.cards.logic;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cardCustomEffect.*;
import gkmasmod.cards.GkmasCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.HalfDamageReceive;
import gkmasmod.utils.CustomHelper;
import gkmasmod.utils.NameHelper;

import java.util.ArrayList;

public class Rhythm extends GkmasCard {
    private static final String CLASSNAME = Rhythm.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 0;

    private static final int BLOCK_AMT = 6;
    private static final int UPGRADE_PLUS_BLOCK = 2;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Rhythm() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK_AMT;
        this.exhaust = true;
        this.isInnate = true;
        this.customLimit = 3;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(GoodImpressionCustom.growID,new int[]{1,1,2},new int[]{50,50,80},CustomHelper.CustomEffectType.GOOD_IMPRESSION_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(DexterityCustom.growID,new int[]{1,1},new int[]{60,60},CustomHelper.CustomEffectType.DEXTERITY_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(HalfDamageReceiveCustom.growID,new int[]{1},new int[]{60},CustomHelper.CustomEffectType.HALF_DAMAGE_RECEIVE_ADD));
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Rhythm();
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
