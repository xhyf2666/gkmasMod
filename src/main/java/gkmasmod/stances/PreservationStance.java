package gkmasmod.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.CalmParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.WrathParticleEffect;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.actions.GrowAction;
import gkmasmod.actions.ModifyDamageRandomEnemyAction;
import gkmasmod.cardGrowEffect.BlockGrow;
import gkmasmod.cardGrowEffect.DamageGrow;
import gkmasmod.powers.EnthusiasticPower;

public class PreservationStance extends GkmasModStance {
    private static final String CLASSNAME = "PreservationStance1";
    private static final String CLASSNAME2 = "PreservationStance2";
    private static final String CLASSNAME3 = "PreservationStance3";

    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(CLASSNAME);

    private static final StanceStrings stanceString2 = CardCrawlGame.languagePack.getStanceString(CLASSNAME2);

    private static final StanceStrings stanceString3 = CardCrawlGame.languagePack.getStanceString(CLASSNAME3);

    public static final String STANCE_ID = "PreservationStance";
    public static final String STANCE_ID2 = "PreservationStance2";
    public static final String STANCE_ID3 = "PreservationStance3";

    public int stage = 0;

    private static long sfxId = -1L;

    private static int BLOCK = 5;
    private static int ENTHUSIASTIC1 = 2;
    private static int ENTHUSIASTIC2 = 3;
    private static int ENTHUSIASTIC3 = 5;

    private static int BASE_DMG = 6;

    public PreservationStance() {
        this(0);
    }

    public PreservationStance(int stage) {
        this.stage = stage;
        if(this.stage>2)
            this.stage = 2;
        this.ID = STANCE_ID;
        this.name = stanceString.NAME;
        updateDescription();
    }

    public void updateAnimation() {
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.04F;
                AbstractDungeon.effectsQueue.add(new CalmParticleEffect());
            }
        }
        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.45F, 0.55F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Calm"));
        }
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if(this.stage==2)
            return 0;
        else if(this.stage==1)
            return damage * 0.5F;
        else
            return damage * 0.7F;
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        if(this.stage>=1)
            return damage * 0.5F;
        else
            return damage * 0.7F;
    }

    @Override
    public void onEnterSameStance() {
        if(this.stage==0){
            this.stage = 1;
        }
        updateDescription();
    }

    public void onEnterSpecialStance(){
        if(this.stage!=2){
            this.stage=2;
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BufferPower(AbstractDungeon.player, 1), 1));
        }
        updateDescription();
    }

    @Override
    public void onEnterStance() {
        if (sfxId != -1L)
            stopIdleSfx();
        CardCrawlGame.sound.play("STANCE_ENTER_CALM");
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_CALM");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.SKY, true));
        if(this.stage==2){
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BufferPower(AbstractDungeon.player, 1), 1));
        }
    }

    public void onExitStance() {
        if(stage==1){
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnthusiasticPower(AbstractDungeon.player, ENTHUSIASTIC2), ENTHUSIASTIC2));
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, BLOCK));
            AbstractDungeon.actionManager.addToTop(new GainEnergyAction(1));
        }
        else if(stage==0){
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnthusiasticPower(AbstractDungeon.player, ENTHUSIASTIC1), ENTHUSIASTIC1));
        }
        AbstractDungeon.actionManager.addToTop(new GainTrainRoundPowerAction(AbstractDungeon.player, 1));
        stopIdleSfx();
    }

    public void onExitSpecialStance(AbstractStance stance){
        if(this.stage==2){
            if(stance instanceof ConcentrationStance){
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnthusiasticPower(AbstractDungeon.player, ENTHUSIASTIC3), ENTHUSIASTIC3));
            }
            else if(stance instanceof FullPowerStance){
                AbstractDungeon.actionManager.addToTop(new GrowAction(DamageGrow.growID, GrowAction.GrowType.all,2));
                AbstractDungeon.actionManager.addToTop(new GrowAction(BlockGrow.growID, GrowAction.GrowType.all,2));
            }
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, BLOCK));
            AbstractDungeon.actionManager.addToTop(new GainEnergyAction(1));
            AbstractDungeon.actionManager.addToTop(new GainTrainRoundPowerAction(AbstractDungeon.player, 1));
            stopIdleSfx();
        }
    }

    @Override
    public void updateDescription() {
        this.name = stanceString.NAME;
        this.description = stanceString.DESCRIPTION[0];
        if(this.stage==1){
            this.name = stanceString2.NAME;
            this.description = stanceString2.DESCRIPTION[0];
        }
        if(this.stage==2){
            this.name = stanceString3.NAME;
            this.description = stanceString3.DESCRIPTION[0];
        }
    }

    public void stopIdleSfx() {
        if (sfxId != -1L) {
            CardCrawlGame.sound.stop("STANCE_LOOP_CALM", sfxId);
            sfxId = -1L;
        }
    }
}
