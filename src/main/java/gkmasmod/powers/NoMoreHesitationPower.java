package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.common.ModifyDamageRandomEnemyAction;
import gkmasmod.utils.NameHelper;

public class NoMoreHesitationPower extends AbstractPower {
    private static final String CLASSNAME = NoMoreHesitationPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final int magic = 3;

    private static final int magic2 = 6;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public NoMoreHesitationPower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = Amount;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],magic,magic2,this.amount);
    }

    public void onStrengthIncrease(int amount) {
        if (amount >= magic) {
            this.flash();
            for (int i = 0; i < this.amount; i++) {
                addToBot(new ModifyDamageRandomEnemyAction(new DamageInfo(this.owner, magic2, DamageInfo.DamageType.NORMAL),
                        AbstractGameAction.AttackEffect.SLASH_VERTICAL, null,false,2.0F,1.0F));
            }
        }
    }
}
