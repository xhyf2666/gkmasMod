package gkmasmod.cards.anomaly;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
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
import gkmasmod.growEffect.DamageGrow;
import gkmasmod.growEffect.EnergyGrow;
import gkmasmod.stances.ConcentrationStance;
import gkmasmod.utils.GrowHelper;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.SoundHelper;

public class TopStar extends GkmasCard {
    private static final String CLASSNAME = TopStar.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 2;

    private static final int BASE_DAMAGE = 2;
    private static final int UPGRADE_DMG_PLUS = 1;
    private static final int BASE_MAGIC = 2;
    private static final int BASE_MAGIC2 = 2;
    private static final int UPGRADE_MAGIC2_PLUS = 1;
    private static final int BASE_MAGIC3 = 1;
    private static final int BASE_GROW = 2;


    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorAnomaly;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public TopStar() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.tags.add(GkmasCardTag.CONCENTRATION_TAG);
        this.tags.add(GkmasCardTag.IDOL_CARD_TAG);
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
        this.backGroundColor = IdolData.jsna;
        updateBackgroundImg();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChangeStanceAction(ConcentrationStance.STANCE_ID));
        addToBot(new ModifyDamageAction(m, new DamageInfo(p, this.baseDamage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL,this));
        addToBot(new ModifyDamageAction(m, new DamageInfo(p, this.baseDamage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL,this));
        SoundHelper.playSound("gkmasModResource/audio/voice/skillcard/cidol_jsna_3_000_produce_skillcard_01.ogg");
    }

    @Override
    public void triggerOnCardPlayed(AbstractCard cardPlayed) {
        if(cardPlayed.hasTag(GkmasCardTag.CONCENTRATION_TAG)){
            if(this.growMagicNumber > 0){
                if(this.growMagicNumber == 1){
                    GrowHelper.grow(this,EnergyGrow.growID,this.thirdMagicNumber);
                }
                upgradeGrowMagicNumber(-1);
                this.initializeDescription();
                GrowHelper.grow(this,DamageGrow.growID,this.secondMagicNumber);
            }

        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new TopStar();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DMG_PLUS);
            upgradeSecondMagicNumber(UPGRADE_MAGIC2_PLUS);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
