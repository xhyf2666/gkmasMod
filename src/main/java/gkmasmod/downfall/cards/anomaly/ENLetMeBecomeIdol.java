package gkmasmod.downfall.cards.anomaly;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.stances.ENFullPowerStance;
import gkmasmod.cardGrowEffect.AttackTimeGrow;
import gkmasmod.stances.FullPowerStance;
import gkmasmod.utils.*;

public class ENLetMeBecomeIdol extends GkmasBossCard {
    private static final String CLASSNAME = ENLetMeBecomeIdol.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENLetMeBecomeIdol.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME2);

    private static final int COST = 1;

    private static final int BASE_DMG = 10;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int BASE_MAGIC = 1;
    private static final int BASE_GROW = 4;


    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorAnomaly;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private int fullPowerTime = 0;

    public ENLetMeBecomeIdol() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, "color");
        this.tags.add(GkmasCardTag.IDOL_CARD_TAG);
        this.baseDamage = BASE_DMG;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseGrowMagicNumber = BASE_GROW;
        this.growMagicNumber = this.baseGrowMagicNumber;
        this.exhaust = true;
        this.intent = AbstractMonster.Intent.ATTACK;
        this.backGroundColor = IdolData.hrnm;
        updateBackgroundImg();
    }

    public ENLetMeBecomeIdol(int fullPowerTime) {
        this();
        this.fullPowerTime = fullPowerTime;
        if(this.fullPowerTime>0)
            GrowHelper.grow(this, AttackTimeGrow.growID,this.fullPowerTime);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(p, new DamageInfo(m, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        SoundHelper.playSound("gkmasModResource/audio/voice/skillcard/cidol_hrnm_3_010_produce_skillcard_01.ogg");
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(AbstractCharBoss.boss.stance.ID.equals(FullPowerStance.STANCE_ID)){
            return super.canUse(p, m);
        }
        if (this.fullPowerTime > 0)
            return super.canUse(p, m);
        this.cantUseMessage = CardCrawlGame.languagePack.getUIString("gkmasMod:NotEnoughFullPowerStanceTime").TEXT[0];
        return false;
    }


    @Override
    public AbstractCard makeCopy() {
        return new ENLetMeBecomeIdol();
    }

    @Override
    public void switchedStance() {
        if(AbstractCharBoss.boss.stance.ID.equals(ENFullPowerStance.STANCE_ID)){
            this.fullPowerTime++;
            if(this.growMagicNumber > 0){
                upgradeGrowMagicNumber(-1);
                this.initializeDescription();
                GrowHelper.grow(this, AttackTimeGrow.growID,this.magicNumber);
            }
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }




}
