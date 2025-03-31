package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.cards.logic.FlyAgain;
import gkmasmod.downfall.cards.logic.ENFlyAgain;
import gkmasmod.downfall.charbosses.actions.common.EnemyMakeTempCardInHandAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.utils.NameHelper;

public class TheWingPower extends AbstractPower {
    private static final String CLASSNAME = TheWingPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    AbstractCreature target;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);;
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);;

    public TheWingPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0]);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        if(this.owner instanceof AbstractPlayer){
            for (int i = AbstractDungeon.player.exhaustPile.group.size() - 1; i >= 0; i--) {
                AbstractCard card = AbstractDungeon.player.exhaustPile.group.get(i);
                if (card instanceof FlyAgain) {
                    AbstractDungeon.player.hand.addToTop(card);
                    AbstractDungeon.player.exhaustPile.removeCard(card);
                    card.unhover();
                    card.fadingOut = false;
                }
            }
        }
        else if(this.owner instanceof AbstractCharBoss){
            AbstractCard card =new ENFlyAgain();
            card.upgrade();
            addToBot(new EnemyMakeTempCardInHandAction(card));
        }

        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }
}
