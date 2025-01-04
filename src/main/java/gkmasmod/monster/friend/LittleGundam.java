package gkmasmod.monster.friend;

import basemod.abstracts.AbstractCardModifier;
import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.bosses.AbstractIdolBoss;
import gkmasmod.patches.AbstractMonsterPatch;
import gkmasmod.powers.SteelSoul;

public class LittleGundam extends CustomMonster {

    public static final String ID = "LittleGundam";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:LittleGundam");

    public static final String NAME = monsterStrings.NAME;

    public AbstractCreature owner;

    private static int MAX_HEALTH = 10;

    public LittleGundam() {
        this(000.0F, 0.0F);
    }

    public LittleGundam(float x, float y) {
        this(x, y,AbstractDungeon.player);

    }

    public LittleGundam(float x, float y, AbstractCreature owner) {
        super(NAME, ID, MAX_HEALTH, -8.0F, 0.0F, 120.0F, 120.0F, null, x, y);
        this.owner = owner;
        addToBot(new ApplyPowerAction(this,this,new BarricadePower(this)));
        if(this.owner.isPlayer){
            this.flipHorizontal = true;
            this.drawX = (float) Settings.WIDTH * 0.25F+ x;
            AbstractMonsterPatch.friendlyField.friendly.set(this,true);
        }
        else{
            this.drawX -= (float) Settings.WIDTH *0.2F;
            int rate = 30;
            if(AbstractCharBoss.boss!=null&&AbstractCharBoss.boss instanceof AbstractIdolBoss){
                AbstractIdolBoss idol = (AbstractIdolBoss) AbstractCharBoss.boss;
                if(idol.stage>1){
                    rate = 50;
                }
            }
            this.maxHealth = MAX_HEALTH*rate;
            this.currentHealth = this.maxHealth;
        }
//        this.drawY = y;
        this.img = new Texture("gkmasModResource/img/monsters/other/LittleGundam.png");
    }

    @Override
    public void takeTurn() {
        if(this.owner.isPlayer){
            addToBot(new DamageRandomEnemyAction(new DamageInfo(this, 1), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
        else{
            addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(this, 1), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
    }

    @Override
    protected void getMove(int i) {
        setMove((byte) 0, Intent.ATTACK,1);
    }

    @Override
    public void damage(DamageInfo info) {
        if(info.owner == this.owner){
            return;
        }
        super.damage(info);
    }

    @Override
    public void die() {
        super.die();
        if(this.owner.isPlayer){
            if(AbstractDungeon.player.hasPower(SteelSoul.POWER_ID)){
                addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player,this,SteelSoul.POWER_ID));
            }
        }
        else if(!this.owner.isPlayer&&AbstractCharBoss.boss!=null&&AbstractCharBoss.boss instanceof AbstractIdolBoss){
            if(AbstractCharBoss.boss.hasPower(SteelSoul.POWER_ID)){
                addToBot(new RemoveSpecificPowerAction(AbstractCharBoss.boss,this,SteelSoul.POWER_ID));
            }
        }
    }
}
