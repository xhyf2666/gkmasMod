package gkmasmod.downfall.cards.anomaly;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cardCustomEffect.DamageCustom;
import gkmasmod.cardCustomEffect.MagicCustom;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.stances.ENFullPowerStance;
import gkmasmod.growEffect.AbstractGrowEffect;
import gkmasmod.growEffect.DamageGrow;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.stances.FullPowerStance;
import gkmasmod.utils.CustomHelper;
import gkmasmod.utils.GrowHelper;
import gkmasmod.utils.ImageHelper;
import gkmasmod.utils.NameHelper;

import java.util.ArrayList;

public class ENSweatAndGrowth extends GkmasBossCard {
    private static final String CLASSNAME = ENSweatAndGrowth.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENSweatAndGrowth.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static String IMG_PATH = ImageHelper.idolImgPath(AbstractCharBoss.theIdolName, CLASSNAME2);

    private static final int COST = 0;

    private static final int BASE_DAMAGE = 5;

    private static final int BASE_MAGIC = 50;


    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorAnomaly;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public ENSweatAndGrowth() {
        super(ID, NAME, ImageHelper.idolImgPath(AbstractCharBoss.theIdolName, CLASSNAME2), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        IMG_PATH = ImageHelper.idolImgPath(AbstractCharBoss.theIdolName, CLASSNAME2);
        this.updateShowImg = true;
        this.updateImg();
        this.baseDamage = BASE_DAMAGE;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(GkmasCardTag.FULL_POWER_TAG);
        CardModifierManager.addModifier(this, new DamageGrow(6));
        this.intent = AbstractMonster.Intent.ATTACK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(p, new DamageInfo(m, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (AbstractCharBoss.boss.stance instanceof ENFullPowerStance)
            return super.canUse(p, m);
        this.cantUseMessage = CardCrawlGame.languagePack.getUIString("gkmasMod:NotFullPowerStance").TEXT[0];
        return false;
    }

    @Override
    public void switchedStance() {
        if(AbstractCharBoss.boss.stance.ID.equals(ENFullPowerStance.STANCE_ID)){
            int count = 0;
            for(AbstractCardModifier modifier:CardModifierManager.modifiers(this)){
                if(modifier instanceof AbstractGrowEffect){
                    AbstractGrowEffect growEffect = (AbstractGrowEffect) modifier;
                    if(growEffect.growEffectID.equals(DamageGrow.growID)){
                        count=growEffect.amount;
                    }
                }
            }
            count = (int) (1.0F*count*this.magicNumber/100);
            GrowHelper.grow(this,DamageGrow.growID,count);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ENSweatAndGrowth();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            GrowHelper.grow(this,DamageGrow.growID,4);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
