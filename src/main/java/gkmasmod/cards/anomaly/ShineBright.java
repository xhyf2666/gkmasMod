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
import gkmasmod.powers.*;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.stances.ConcentrationStance;
import gkmasmod.stances.FullPowerStance;
import gkmasmod.utils.ImageHelper;
import gkmasmod.utils.NameHelper;

public class ShineBright extends GkmasCard {
    private static final String CLASSNAME = ShineBright.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static String IMG_PATH = ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME);

    private static final int COST = 2;

    private static final int BASE_DAMAGE = 9;
    private static final int UPGRADE_DAMAGE_PLUS = 3;

    private static final int BASE_MAGIC = 2;
    private static final int BASE_MAGIC2 = 3;
    private static final int UPGRADE_MAGIC2_PLUS = 1;



    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorAnomaly;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public ShineBright() {
        super(ID, NAME, ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        IMG_PATH = ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME);
        this.updateShowImg = true;
        this.tags.add(GkmasCardTag.FULL_POWER_TAG);
        this.tags.add(GkmasCardTag.PRESERVATION_TAG);
        this.baseDamage = BASE_DAMAGE;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new FullPowerValue(p,this.secondMagicNumber),this.secondMagicNumber));
        if(this.upgraded){
            addToBot(new ApplyPowerAction(p,p,new EndOfTurnPreservationStancePlusPower(p,1),1));
        }
        else{
            addToBot(new ApplyPowerAction(p,p,new EndOfTurnPreservationStancePower(p,1),1));
        }

        if(p.stance instanceof FullPowerStance){
            addToBot(new DamageAction(m,new DamageInfo(p,this.damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            addToBot(new DamageAction(m,new DamageInfo(p,this.damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ShineBright();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE_PLUS);
            upgradeSecondMagicNumber(UPGRADE_MAGIC2_PLUS);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

}
