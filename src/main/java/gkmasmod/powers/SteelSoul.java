package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.monster.friend.LittleGundam;
import gkmasmod.utils.NameHelper;

import java.util.ArrayList;

public class SteelSoul extends AbstractPower {
    private static final String CLASSNAME = SteelSoul.class.getSimpleName();
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

    public SteelSoul(AbstractCreature owner) {
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

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        ArrayList<LittleGundam> littleGundams = new ArrayList<>();

        for (int i = 0; i < AbstractDungeon.getMonsters().monsters.size(); i++) {
            AbstractCreature monster = AbstractDungeon.getMonsters().monsters.get(i);
            if(monster instanceof LittleGundam && !monster.isDeadOrEscaped()){
                LittleGundam littleGundam = (LittleGundam) monster;
                if(littleGundam.owner.isPlayer==this.owner.isPlayer)
                    littleGundams.add((LittleGundam) monster);
            }
        }

        if(littleGundams.size()>0){
            //随机选择一个高达
            LittleGundam littleGundam = littleGundams.get(AbstractDungeon.cardRandomRng.random(littleGundams.size()-1));
            littleGundam.damage(new DamageInfo(info.owner, damageAmount, DamageInfo.DamageType.NORMAL));
            return 0;
        }
        else{
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            return damageAmount;
        }
    }

    public void onAddBlock(float blockAmount) {
        ArrayList<LittleGundam> littleGundams = new ArrayList<>();
        for (int i = 0; i < AbstractDungeon.getMonsters().monsters.size(); i++) {
            AbstractCreature monster = AbstractDungeon.getMonsters().monsters.get(i);
            if(monster instanceof LittleGundam && !monster.isDeadOrEscaped()){
                LittleGundam littleGundam = (LittleGundam) monster;
                if(littleGundam.owner.isPlayer==this.owner.isPlayer)
                    littleGundams.add((LittleGundam) monster);
            }
        }
        if(littleGundams.size()>0){
            LittleGundam littleGundam = littleGundams.get(AbstractDungeon.cardRandomRng.random(littleGundams.size()-1));
            addToBot(new GainBlockAction(littleGundam, (int) blockAmount));
        }
        else{
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            addToBot(new GainBlockAction(this.owner, (int) blockAmount));
        }
    }
}
