package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.cards.special.FightWill;
import gkmasmod.utils.NameHelper;

public class GekkaInterest extends AbstractPower {
    private static final String CLASSNAME = GekkaInterest.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final int MAGIC = 20;

    private int loseHP = 0;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public GekkaInterest(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],MAGIC,1);
    }

    @Override
    public void wasHPLost(DamageInfo info, int damageAmount) {
        int tmp = damageAmount;
        if(tmp>this.owner.currentHealth)
            tmp = this.owner.currentHealth;
        this.loseHP += tmp;
        int count = (int) (this.owner.maxHealth *1.0F*MAGIC/100);
        while(this.loseHP >= count){
            this.loseHP -= count;
            this.flash();
            addToBot(new ApplyPowerAction(this.owner,this.owner,new GekkaWill(this.owner,1),1));
            addToBot(new MakeTempCardInDrawPileAction(new FightWill(),1,true,true));
            addToBot(new LoseEnergyAction(1));
        }
    }
}
