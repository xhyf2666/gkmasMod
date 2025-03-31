package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.patches.AbstractMonsterPatch;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

public class SenaTeachPower extends AbstractPower {
    private static final String CLASSNAME = SenaTeachPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png","LikeStarsPower");
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png","LikeStarsPower");

    public SenaTeachPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;

        // 添加一大一小两张能力图
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        for(AbstractMonster mo: AbstractDungeon.getMonsters().monsters){
            if(!mo.isDeadOrEscaped()&& !AbstractMonsterPatch.friendlyField.friendly.get(mo)){
                int count = (int) (PlayerHelper.getPowerAmount(mo, StrengthPower.POWER_ID)*this.amount*1.0F/100);
                if(count>0){
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo,this.owner,new StrengthPower(mo,count),count));
                }
                count = (int) (PlayerHelper.getPowerAmount(mo, GoodTune.POWER_ID)*this.amount*1.0F/100);
                if(count>0){
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo,this.owner,new GoodTune(mo,count),count));
                }
            }
        }
    }
}
