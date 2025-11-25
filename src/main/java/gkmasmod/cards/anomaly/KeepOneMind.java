package gkmasmod.cards.anomaly;

import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cardCustomEffect.*;
import gkmasmod.cardGrowEffect.DamageGrow;
import gkmasmod.cardGrowEffect.LoseHPGrow;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.TempSavePower;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.stances.ConcentrationStance;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.*;

import java.util.ArrayList;

public class KeepOneMind extends GkmasCard {
    private static final String CLASSNAME = KeepOneMind.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static String IMG_PATH = ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME);

    private static final int COST = 1;

    private static final int BASE_MAGIC = 8;
    private static final int UPGRADE_PLUS = 2;

    private static final int BASE_MAGIC2 = 3;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorAnomaly;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public KeepOneMind() {
        super(ID, NAME, ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        IMG_PATH = ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME);
        this.updateShowImg = true;
        this.tags.add(GkmasCardTag.PRESERVATION_TAG);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.exhaust = true;
        this.customLimit = 1;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(SecondMagicCustom.growID,new int[]{-1},new int[]{60},CustomHelper.CustomEffectType.HP_REDUCE));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(EffectAddCustom.growID,new int[]{1},new int[]{50},CustomHelper.CustomEffectType.PRESERVATION_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(MagicCustom.growID, new int[]{2}, new int[]{60}, CustomHelper.CustomEffectType.GROW_EFFECT_ADD));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractCard c:AbstractDungeon.player.hand.group){
            if(c.hasTag(GkmasCardTag.CONCENTRATION_TAG)){
                GrowHelper.grow(c, DamageGrow.growID,this.magicNumber);
                GrowHelper.grow(c, LoseHPGrow.growID,this.secondMagicNumber);
            }
        }
        for(AbstractCard c:AbstractDungeon.player.drawPile.group){
            if(c.hasTag(GkmasCardTag.CONCENTRATION_TAG)){
                GrowHelper.grow(c, DamageGrow.growID,this.magicNumber);
                GrowHelper.grow(c, LoseHPGrow.growID,this.secondMagicNumber);
            }
        }
        for(AbstractCard c:AbstractDungeon.player.discardPile.group){
            if(c.hasTag(GkmasCardTag.CONCENTRATION_TAG)){
                GrowHelper.grow(c, DamageGrow.growID,this.magicNumber);
                GrowHelper.grow(c, LoseHPGrow.growID,this.secondMagicNumber);
            }
        }
        if(AbstractDungeon.player.hasPower(TempSavePower.POWER_ID)){
            TempSavePower tempSavePower = (TempSavePower) AbstractDungeon.player.getPower(TempSavePower.POWER_ID);
            for(AbstractCard c:tempSavePower.getCards()){
                if(c.hasTag(GkmasCardTag.CONCENTRATION_TAG)){
                    GrowHelper.grow(c, DamageGrow.growID,this.magicNumber);
                    GrowHelper.grow(c, LoseHPGrow.growID,this.secondMagicNumber);
                }
            }
        }
        if(CustomHelper.hasCustom(this, EffectAddCustom.growID)){
            addToBot(new ChangeStanceAction(PreservationStance.STANCE_ID2));
        }
        else{
            addToBot(new ChangeStanceAction(PreservationStance.STANCE_ID));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(p.stance.ID.equals(ConcentrationStance.STANCE_ID)){
            return super.canUse(p,m);
        }
        this.cantUseMessage = CardCrawlGame.languagePack.getUIString("gkmasMod:NotConcentrationStance").TEXT[0];
        return CardHelper.containsMasterKey();
    }

    @Override
    public AbstractCard makeCopy() {
        return new KeepOneMind();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

}
