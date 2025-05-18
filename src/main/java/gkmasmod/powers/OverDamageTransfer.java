package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.OverDamageTransferAction;
import gkmasmod.utils.NameHelper;
import org.lwjgl.Sys;

public class OverDamageTransfer extends AbstractPower {
    private static final String CLASSNAME = OverDamageTransfer.class.getSimpleName();
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public OverDamageTransfer(AbstractCreature owner) {
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
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(damageAmount >= this.owner.currentHealth){
            int count=0;
            for(AbstractMonster m:AbstractDungeon.getMonsters().monsters){
                if(!m.isDeadOrEscaped())
                    count++;
            }
            if(count<=1)
                return damageAmount;

            AbstractCreature target = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster) this.owner, true, AbstractDungeon.cardRandomRng);
            if(target != null){
                addToBot(new ApplyPowerAction(target, this.owner, new OverDamageTransfer(target)));
                addToBot(new OverDamageTransferAction(target,damageAmount-this.owner.currentHealth));
//                addToBot(new DamageAction(target, new DamageInfo(AbstractDungeon.player, damageAmount-this.owner.currentHealth, DamageInfo.DamageType.NORMAL)));
            }
        }
        return damageAmount;
    }
}
