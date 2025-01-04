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
import gkmasmod.actions.ModifyDamageAction;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.*;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.stances.ConcentrationStance;
import gkmasmod.stances.FullPowerStance;
import gkmasmod.utils.ImageHelper;
import gkmasmod.utils.NameHelper;

public class Climax extends GkmasCard {
    private static final String CLASSNAME = Climax.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static String IMG_PATH = ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME);

    private static final int COST = 2;

    private static final int BASE_DAMAGE = 6;
    private static final int UPGRADE_DAMAGE_PLUS = 3;

    private static final int BASE_MAGIC = 2;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorAnomaly;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Climax() {
        super(ID, NAME, ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        IMG_PATH = ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME);
        this.updateShowImg = true;
        this.tags.add(GkmasCardTag.PRESERVATION_TAG);
        this.tags.add(GkmasCardTag.CONCENTRATION_TAG);
        this.baseDamage = BASE_DAMAGE;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChangeStanceAction(ConcentrationStance.STANCE_ID2));
        addToBot(new ApplyPowerAction(p,p,new EndOfTurnPreservationStancePlusPower(p,1),1));
        addToBot(new ModifyDamageAction(m,new DamageInfo(p,this.baseDamage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL,this,false));
        addToBot(new ModifyDamageAction(m,new DamageInfo(p,this.baseDamage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL,this,false));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Climax();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE_PLUS);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

}
