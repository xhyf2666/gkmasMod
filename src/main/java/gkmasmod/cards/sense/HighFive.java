package gkmasmod.cards.sense;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;
import gkmasmod.cards.AbstractDefaultCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.utils.NameHelper;

public class HighFive extends AbstractDefaultCard {
    private static final String CLASSNAME = HighFive.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 2;
    private static final int ATTACK_DMG = 17;
    private static final int UPGRADE_PLUS_DMG = 6;

    private static final int BASE_MAGIC = 150;
    private static final int UPGRADE_PLUS_MAGIC = 50;


    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorSense;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public HighFive() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = ATTACK_DMG;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null)
            addToBot(new VFXAction(new VerticalImpactEffect(m.hb.cX + m.hb.width / 4.0F, m.hb.cY - m.hb.height / 4.0F)));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    public void applyPowers() {
        AbstractPower strength = AbstractDungeon.player.getPower("Strength");
        if (strength != null){
            strength.amount = (int) (strength.amount*(1.0*this.magicNumber/100));
            System.out.println("strength.amount: " + strength.amount);
        }

        super.applyPowers();
        if (strength != null) {
            strength.amount = (int) (strength.amount/(1.0*this.magicNumber/100));
            System.out.println("strength.amount2: " + strength.amount);
        }

    }

    public void calculateCardDamage(AbstractMonster mo) {
        AbstractPower strength = AbstractDungeon.player.getPower("Strength");
        if (strength != null){
            System.out.println("strength.amount3: " + strength.amount);
            strength.amount = (int) (strength.amount*(1.0*this.magicNumber/100));
        }

        super.calculateCardDamage(mo);
        if (strength != null){
            strength.amount = (int) (strength.amount/(1.0*this.magicNumber/100));
            System.out.println("strength.amount4: " + strength.amount);
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new HighFive();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }



}
