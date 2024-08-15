package gkmasmod.cards.logic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.GoodImpressionDamageAction;
import gkmasmod.cards.AbstractDefaultCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.utils.NameHelper;

public class KawaiiGesture extends AbstractDefaultCard {
    private static final String CLASSNAME = KawaiiGesture.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(CLASSNAME);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 1;
    private static final int ATTACK_DMG = 2;
    private static final int UPGRADE_PLUS_DMG = 1;
    private static final int BASE_MAGIC = 2;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    private static float MAGIC2 = 1;
    private static float MAGIC3 = 1.2f;

    private static final int BASE_MAGIC2 = 100;
    private static final int UPGRADE_PLUS_MAGIC2 = 20;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public KawaiiGesture() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = ATTACK_DMG;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.exhaust = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower goodImpression = AbstractDungeon.player.getPower(NameHelper.makePath("GoodImpression"));
        int amount = goodImpression != null ? goodImpression.amount : 0;
        amount += this.magicNumber;
        if(this.upgraded){
            addToBot((AbstractGameAction)new GoodImpressionDamageAction(MAGIC3,this.magicNumber,amount,p, m,new DamageInfo((AbstractCreature)p, this.damage, this.damageTypeForTurn)));
        }
        else
            addToBot((AbstractGameAction)new GoodImpressionDamageAction(MAGIC2,this.magicNumber,amount,p, m,new DamageInfo((AbstractCreature)p, this.damage, this.damageTypeForTurn)));

    }

//    public void applyPowers() {
//
//        if (goodImpression != null){
//            System.out.println("goodImpression.amount: "+goodImpression.amount);
//        }
//        else {
//            System.out.println("goodImpression is null");
//        }
//        if (goodImpression != null){
//            System.out.println("goodImpression.amount: "+goodImpression.amount);
//            if (this.upgraded)
//                this.baseDamage = (int)((ATTACK_DMG + goodImpression.amount)*MAGIC3);
//            else
//                this.baseDamage = (int)((ATTACK_DMG + goodImpression.amount)*MAGIC2);
//            System.out.println("baseDamage: "+this.baseDamage);
//        }
//
//    }


    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return (AbstractCard)new KawaiiGesture();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            upgradeSecondMagicNumber(UPGRADE_PLUS_MAGIC2);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
