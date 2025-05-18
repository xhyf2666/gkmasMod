package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import gkmasmod.cards.special.ChocolateForProducer;
import gkmasmod.utils.NameHelper;

public class AsariChocolatePower extends AbstractPower {
    private static final String CLASSNAME = AsariChocolatePower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private boolean flag = false;

    private int MAGIC = 1;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public AsariChocolatePower(AbstractCreature owner,int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.priority = 4;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public AsariChocolatePower setMagic(int magic){
        this.MAGIC = magic;
        this.updateDescription();
        return this;
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.MAGIC,this.amount);
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        flashWithoutSound();
        if(!flag){
            addToBot(new MakeTempCardInDrawPileAction(new ChocolateForProducer(), this.MAGIC, true, true));
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new AnxietyPower(AbstractDungeon.player,this.amount),this.amount));
            AbstractDungeon.effectList.add(new SpeechBubble(this.owner.hb.cX + this.owner.dialogX - 50, this.owner.hb.cY + this.owner.dialogY + 50, 3.0F, String.format(CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:MonsterAsari").DIALOG[0],CardCrawlGame.playerName), false));
            flag = true;
        }
    }

//    @Override
//    public void atEndOfRound() {
//        this.flag = false;
//    }
}
