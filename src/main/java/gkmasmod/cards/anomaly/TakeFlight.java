package gkmasmod.cards.anomaly;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.ModifyDamageAction;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.growEffect.DamageGrow;
import gkmasmod.powers.FullPowerValue;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.stances.FullPowerStance;
import gkmasmod.utils.GrowHelper;
import gkmasmod.utils.NameHelper;

import java.util.HashMap;

public class TakeFlight extends GkmasCard {
    private static final String CLASSNAME = TakeFlight.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static String IMG_PATH = String.format("gkmasModResource/img/idol/%s/cards/%s.png", SkinSelectScreen.Inst.idolName, CLASSNAME);

    private static final int COST = 2;

    private static final int BASE_DAMAGE = 7;

    private static final int BASE_MAGIC = 3;
    private static final int UPGRADE_MAGIC_PLUS = 1;
    private static final int BASE_MAGIC2 = 100;
    private static final int UPGRADE_MAGIC2_PLUS = 50;
    private static final int BASE_MAGIC3 = 10;
    private static final int UPGRADE_MAGIC3_PLUS = 5;
    private static final int BASE_GROW = 1;



    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorAnomaly;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public AbstractPower lastPower=null;

    public TakeFlight() {
        super(ID, NAME, String.format("gkmasModResource/img/idol/%s/cards/%s.png", SkinSelectScreen.Inst.idolName, CLASSNAME), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        IMG_PATH = String.format("gkmasModResource/img/idol/%s/cards/%s.png", SkinSelectScreen.Inst.idolName, CLASSNAME);
        this.updateShowImg = true;
        this.tags.add(GkmasCardTag.FULL_POWER_TAG);
        this.baseDamage = BASE_DAMAGE;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseThirdMagicNumber = BASE_MAGIC3;
        this.thirdMagicNumber = this.baseThirdMagicNumber;
        this.baseGrowMagicNumber = BASE_GROW;
        this.growMagicNumber = this.baseGrowMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new FullPowerValue(p,this.magicNumber),this.magicNumber));
        addToBot(new ModifyDamageAction(m,new DamageInfo(p,this.baseDamage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL,this));

    }

    @Override
    public AbstractCard makeCopy() {
        return new TakeFlight();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_PLUS);
            upgradeSecondMagicNumber(UPGRADE_MAGIC2_PLUS);
            upgradeThirdMagicNumber(UPGRADE_MAGIC3_PLUS);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void switchedStance() {
        if(this.growMagicNumber > 0){
            if(AbstractDungeon.player.stance.ID.equals(FullPowerStance.STANCE_ID)){
                upgradeGrowMagicNumber(-1);
                this.initializeDescription();
                GrowHelper.grow(this,DamageGrow.growID,this.thirdMagicNumber);
            }
        }
    }

    public void onFullPowerValueIncrease(AbstractPower power){
        if(power == this.lastPower){
            return;
        }
        this.lastPower = power;
        int value = power.amount;
        int amount = (int) (1.0f *this.secondMagicNumber *value /100);
        System.out.println("amount:"+amount);
        System.out.println("value:"+value);
        if(amount>0){
            GrowHelper.grow(this,DamageGrow.growID,amount);
        }
    }
}
