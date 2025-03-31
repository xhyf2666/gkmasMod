package gkmasmod.monster.friend;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ExplosivePower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import gkmasmod.monster.exordium.MonsterNadeshiko;
import gkmasmod.patches.AbstractMonsterPatch;
import gkmasmod.powers.ExplosiveSpecialPower;
import gkmasmod.powers.FriendTemariPower1;
import gkmasmod.powers.GoodsMonopolyPower;
import gkmasmod.powers.IntangibleSpecialPower;
import gkmasmod.relics.PocketBook;
import gkmasmod.utils.ThreeSizeHelper;

import java.util.Random;

public class FriendNunu extends CustomMonster {

    public static final String ID = "FriendNunu";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:FriendNunu");

    public static final String NAME = monsterStrings.NAME;

    private static int MAX_HEALTH = 20;

    public AbstractCreature owner;

    public boolean left = false;

    public FriendNunu() {
        this(000.0F, 0.0F,null);
    }

    public FriendNunu(float x, float y,AbstractCreature owner,boolean left) {
        super(NAME, ID, getMaxHealth(), -8.0F, 0.0F, 120.0F, 120.0F, null, x, y);
        this.img = getImg();
        this.owner = owner;
        if(this.owner!=null&&this.owner.isPlayer){
            AbstractMonsterPatch.friendlyField.friendly.set(this,true);
            this.drawX = (float) Settings.WIDTH * 0.25F+ x;
            addToBot(new ApplyPowerAction(this, this, new ExplosiveSpecialPower(this, 2,true)));
        }
        else{
            addToBot(new ApplyPowerAction(this, this, new ExplosiveSpecialPower(this, 3)));
        }
        this.left = left;
    }

    public FriendNunu(float x, float y, AbstractCreature owner) {
        this(x, y, owner, false);
    }


    @Override
    public void takeTurn() {
    }

    @Override
    protected void getMove(int i) {
        setMove((byte) 0, Intent.NONE);
    }

    private static int getMaxHealth() {
        int rate = 1;
        if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
            rate = ThreeSizeHelper.getHealthRate(1);
        }
        return MAX_HEALTH * rate;
    }

    @Override
    public void die() {
        super.die();
        if(this.owner!=null&&this.owner.isPlayer){
        }
        else{
            AbstractMonster nadeshiko = null;
            for(AbstractMonster monster: AbstractDungeon.getMonsters().monsters){
                if(monster instanceof MonsterNadeshiko){
                    nadeshiko = monster;
                }
            }
            if(nadeshiko!=null){
                addToBot(new VFXAction(new ExplosionSmallEffect(this.hb.cX, this.hb.cY), 0.1F));
                nadeshiko.getPower(GoodsMonopolyPower.POWER_ID).onSpecificTrigger();
                if(nadeshiko.getPower(IntangibleSpecialPower.POWER_ID)!=null){
                    nadeshiko.getPower(IntangibleSpecialPower.POWER_ID).onSpecificTrigger();
                }
                addToBot(new DamageAction(nadeshiko, new DamageInfo(this, 30, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE, true));
            }
        }
    }

    private Texture getImg(){
        Random random = new Random();
        int index = random.nextInt(12)+1;
        return new Texture(String.format("gkmasModResource/img/monsters/Nadeshiko/FriendNunu%s.png",index));
    }

}
