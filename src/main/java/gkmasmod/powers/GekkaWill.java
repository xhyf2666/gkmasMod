package gkmasmod.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect;
import gkmasmod.actions.ReduceBuffAction;
import gkmasmod.actions.ReduceBuffAction2;
import gkmasmod.actions.RemoveBuffAction;
import gkmasmod.utils.NameHelper;

public class GekkaWill extends AbstractPower {
    private static final String CLASSNAME = GekkaWill.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final int MAGIC1 = 300;
    private static final int MAGIC2 = 2;
    private static final int MAGIC3 = 80;
    private static final int MAGIC4 = 400;
    private static final int MAGIC6 = 3;
    private static final int MAGIC7 = 10;
    private static final int MAGIC8 = 1;
    private static final int MAGIC9 = 30;
    private static final int MAGIC10 = 1;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    private boolean[] hasPlayed = new boolean[11];

    public GekkaWill(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        if(this.amount>0){
            for (int i = 1; i <= Math.min(this.amount,9); i++) {
                if(!hasPlayed[i]){
                    hasPlayed[i] = true;
                    switch (i){
                        case 1:
                            addToBot(new GainBlockAction(this.owner, this.owner, MAGIC1));
                            break;
                        case 2:
//                            addToBot(new RemoveBuffAction(AbstractDungeon.player,MAGIC2));
                            addToBot(new ReduceBuffAction(AbstractDungeon.player,MAGIC2));
                            break;
                        case 3:
                            addToBot(new ApplyPowerAction(this.owner, this.owner, new MalleablePower(this.owner, MAGIC3), MAGIC3));
                            break;
                        case 4:
                            addToBot(new ApplyPowerAction(this.owner, this.owner, new RegenerateMonsterPower((AbstractMonster) this.owner, MAGIC4), MAGIC4));
                            break;
                        case 5:
                        case 9:
                            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new CanNotPlayCardPower(AbstractDungeon.player,1),1));
//                            AbstractDungeon.actionManager.callEndTurnEarlySequence();
                            CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
                            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLD, true));
                            AbstractDungeon.topLevelEffectsQueue.add(new TimeWarpTurnEndEffect());
                            break;
                        case 6:
                            addToBot(new ApplyPowerAction(this.owner, this.owner, new GreatGoodTune(this.owner, MAGIC6), MAGIC6));
                            break;
                        case 7:
                            addToBot(new ApplyPowerAction(this.owner, this.owner, new GekkaChase(this.owner)));
                            break;
                        case 8:
                            addToBot(new ReduceBuffAction2(AbstractDungeon.player,MAGIC7,MAGIC8,1.0F*MAGIC9/100));
//                            addToBot(new RemoveBuffAction(AbstractDungeon.player,MAGIC7));
                            break;
                    }
                }
            }
            if(this.amount>=10){
                addToBot(new ApplyPowerAction(this.owner, this.owner, new BufferPower(this.owner, MAGIC10), MAGIC10));
            }
        }
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],MAGIC1,MAGIC2,MAGIC3,MAGIC4,MAGIC6,MAGIC7,MAGIC8,MAGIC9,MAGIC10);
    }

}
