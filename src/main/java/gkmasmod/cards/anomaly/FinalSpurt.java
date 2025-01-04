package gkmasmod.cards.anomaly;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.FullPowerValue;
import gkmasmod.powers.TempSavePower;
import gkmasmod.stances.ConcentrationStance;
import gkmasmod.stances.FullPowerStance;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.NameHelper;

public class FinalSpurt extends GkmasCard {
    private static final String CLASSNAME = FinalSpurt.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 1;

    private static final int UPGRADE_COST = 0;

    private static final int BASE_DAMAGE = 14;

    private static final int BASE_MAGIC = 1;


    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorAnomaly;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public FinalSpurt() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(GkmasCardTag.FULL_POWER_TAG);
        this.baseDamage = BASE_DAMAGE;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new FullPowerValue(p,this.magicNumber),this.magicNumber));
        if(p.stance instanceof PreservationStance){
            TempSavePower.addCard(p,this);
        }
        else if(p.stance instanceof FullPowerStance){
            addToBot(new DamageAction(m,new DamageInfo(p,this.damage), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new FinalSpurt();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
