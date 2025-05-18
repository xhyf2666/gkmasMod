package gkmasmod.powers;

import gkmasmod.downfall.charbosses.actions.common.EnemyMakeTempCardInHandAction;
import gkmasmod.downfall.charbosses.actions.common.EnemyMakeTempCardInHandBottomAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.cards.logic.LetMeBeYourDream;
import gkmasmod.downfall.cards.logic.ENLetMeBeYourDream;
import gkmasmod.utils.NameHelper;
import org.lwjgl.Sys;

public class EvenIfDreamNotRealizePower extends AbstractPower {
    private static final String CLASSNAME = EvenIfDreamNotRealizePower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public int limit = 4;
    public int count = 0;
    public boolean flag = false;

    public EvenIfDreamNotRealizePower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.flag = true;
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],1);
    }

    public void atStartOfTurnPostDraw() {
        this.flag = false;
        this.count = 0;
    }

    @Override
    public void wasHPLost(DamageInfo info, int damageAmount) {
        if (damageAmount > 0) {
            this.flash();
            if(this.owner.isPlayer){
                addToBot(new MakeTempCardInHandAction(new LetMeBeYourDream()));
            }
            else if(this.owner instanceof AbstractCharBoss){
//                System.out.println(flag+" "+count+" "+limit);
                if(flag){
                    if(count<limit){
                        count++;
                        addToBot(new EnemyMakeTempCardInHandAction(new ENLetMeBeYourDream()));
                    }
                    else{
                        addToBot(new EnemyMakeTempCardInHandBottomAction(new ENLetMeBeYourDream()));
                    }
                }
                else{
                    addToBot(new EnemyMakeTempCardInHandAction(new ENLetMeBeYourDream()));
                }
            }
        }
    }

    public void atStartOfTurn() {
        this.flag = true;
    }

}
