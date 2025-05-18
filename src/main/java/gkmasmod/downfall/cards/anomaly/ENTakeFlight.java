package gkmasmod.downfall.cards.anomaly;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.ModifyDamageAction;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.stances.ENFullPowerStance;
import gkmasmod.cardGrowEffect.CanNotPlayGrow;
import gkmasmod.cardGrowEffect.DamageGrow;
import gkmasmod.powers.FullPowerValue;
import gkmasmod.utils.GrowHelper;
import gkmasmod.utils.ImageHelper;
import gkmasmod.utils.NameHelper;

public class ENTakeFlight extends GkmasBossCard {
    private static final String CLASSNAME = ENTakeFlight.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENTakeFlight.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static String IMG_PATH = ImageHelper.idolImgPath(AbstractCharBoss.theIdolName, CLASSNAME2);

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

    public ENTakeFlight() {
        super(ID, NAME, ImageHelper.idolImgPath(AbstractCharBoss.theIdolName, CLASSNAME2), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        IMG_PATH = ImageHelper.idolImgPath(AbstractCharBoss.theIdolName, CLASSNAME2);
        this.updateShowImg = true;
        this.updateImg();
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
        this.exhaust = true;
        this.intent = AbstractMonster.Intent.ATTACK_BUFF;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m,m,new FullPowerValue(m,this.magicNumber),this.magicNumber));
        addToBot(new ModifyDamageAction(p,new DamageInfo(m,this.baseDamage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL,this,false));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        for(AbstractCardModifier mod: CardModifierManager.modifiers(this)){
            if(mod instanceof CanNotPlayGrow){
                return false;
            }
        }
        return super.canUse(p, m);
    }

    @Override
    public AbstractCard makeCopy() {
        return new ENTakeFlight();
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
            if(AbstractCharBoss.boss.stance.ID.equals(ENFullPowerStance.STANCE_ID)){
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
        if(amount>0){
            GrowHelper.grow(this,DamageGrow.growID,amount);
        }
    }
}
