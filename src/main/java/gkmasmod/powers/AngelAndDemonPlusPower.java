package gkmasmod.powers;

import gkmasmod.downfall.charbosses.actions.common.EnemyMakeTempCardInHandAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.cards.free.JustAngel;
import gkmasmod.cards.free.JustDemon;
import gkmasmod.downfall.cards.free.ENJustAngel;
import gkmasmod.downfall.cards.free.ENJustDemon;
import gkmasmod.utils.NameHelper;

public class AngelAndDemonPlusPower extends AbstractPower {
    private static final String CLASSNAME = AngelAndDemonPlusPower.class.getSimpleName();
    // 能力的ID
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);;
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);;

    public AngelAndDemonPlusPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        // 添加一大一小两张能力图
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0]);

    }

    public void atStartOfTurn() {
        if(this.owner.isPlayer){
            AbstractCard card1 = new JustAngel();
            card1.upgrade();
            AbstractCard card2 = new JustDemon();
            card2.upgrade();
            addToBot(new MakeTempCardInHandAction(card1));
            addToBot(new MakeTempCardInHandAction(card2));
        }
        else if(this.owner instanceof AbstractCharBoss){
            AbstractCard card1 = new ENJustAngel();
            card1.upgrade();
            AbstractCard card2 = new ENJustDemon();
            card2.upgrade();
            addToBot(new EnemyMakeTempCardInHandAction(card1));
            addToBot(new EnemyMakeTempCardInHandAction(card2));
        }

    }
}
