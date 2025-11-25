package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.common.ModifyDamageAction;
import gkmasmod.actions.common.ModifyDamageAllEnemyAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.patches.AbstractMonsterPatch;
import gkmasmod.utils.NameHelper;

public class BambooCrushPower extends AbstractPower {
    private static final String CLASSNAME = BambooCrushPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    private static final int magic = 5;

    public BambooCrushPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],magic * this.amount, this.amount);
    }


    @Override
    public void atStartOfTurn() {
        if(this.owner.isPlayer){
            for(AbstractMonster mo: AbstractDungeon.getMonsters().monsters){
                if(!mo.isDeadOrEscaped()&&!AbstractMonsterPatch.friendlyField.friendly.get(mo)){
                    addToBot(new ApplyPowerAction(mo, this.owner, new NotGoodTune(mo, this.amount), this.amount));
                }
            }
            addToBot(new ModifyDamageAllEnemyAction(magic*amount, AbstractGameAction.AttackEffect.SLASH_VERTICAL,null));
        }
        else if(this.owner instanceof AbstractCharBoss){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, this.owner, new NotGoodTune(AbstractDungeon.player, this.amount), this.amount));
            addToBot(new ModifyDamageAction(this.owner, new DamageInfo(AbstractDungeon.player, magic*amount, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }

    }
}
