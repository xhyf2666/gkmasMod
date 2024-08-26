package gkmasmod.cards.sense;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;
import gkmasmod.cards.AbstractDefaultCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.GoodTune;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

public class FirstRamune extends AbstractDefaultCard {
    private static final String CLASSNAME = FirstRamune.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 2;
    private static final int ATTACK_DMG = 9;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int BASE_MAGIC = 200;
    private static final int UPGRADE_PLUS_MAGIC = 100;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorSense;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public FirstRamune() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.baseDamage = ATTACK_DMG;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null)
            addToBot(new VFXAction(new VerticalImpactEffect(m.hb.cX + m.hb.width / 4.0F, m.hb.cY - m.hb.height / 4.0F)));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        int count = PlayerHelper.getPowerAmount(p, GoodTune.POWER_ID);
        int damage_ = (int) (1.0F * count * this.magicNumber / 100);
        addToBot(new DamageAction(m, new DamageInfo(p, damage_, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player.hasPower(GoodTune.POWER_ID)){
            return true;
        }
        // TODO 本地化
        this.cantUseMessage = "好调还不够";
        return false;
    }


    @Override
    public AbstractCard makeCopy() {
        return new FirstRamune();
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
