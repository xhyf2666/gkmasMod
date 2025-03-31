package gkmasmod.monster.friend;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.downfall.bosses.AbstractIdolBoss;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.patches.AbstractMonsterPatch;
import gkmasmod.powers.OnigiriGift;
import gkmasmod.powers.SteelSoul;

public class FriendOnigiri extends CustomMonster {

    public static final String ID = "FriendOnigiri";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:FriendOnigiri");

    public static final String NAME = monsterStrings.NAME;

    public AbstractCreature owner;

    private static int MAX_HEALTH = 5;

    public FriendOnigiri() {
        this(000.0F, 0.0F);
    }

    public FriendOnigiri(float x, float y) {
        this(x, y,AbstractDungeon.player);

    }

    public FriendOnigiri(float x, float y, AbstractCreature owner) {
        super(NAME, ID, MAX_HEALTH, -8.0F, 0.0F, 120.0F, 120.0F, null, x, y);
        this.owner = owner;
        if(this.owner.isPlayer){
            this.flipHorizontal = true;
            this.drawX = (float) Settings.WIDTH * 0.25F+ x;
            AbstractMonsterPatch.friendlyField.friendly.set(this,true);
        }
        else{
            this.drawX -= (float) Settings.WIDTH *0.25F;
            int rate = 10000;
            this.maxHealth = MAX_HEALTH*rate;
            this.currentHealth = this.maxHealth;
            addToBot(new ApplyPowerAction(this,this,new OnigiriGift(this)));
        }
//        this.drawY = y;
        this.img = new Texture("gkmasModResource/img/monsters/other/FriendOnigiri.png");
    }

    @Override
    public void takeTurn() {
        if(this.owner.isPlayer){
            addToBot(new LoseHPAction(this, this, 1));
            addToBot(new GainBlockWithPowerAction(AbstractDungeon.player,2));
            addToBot(new HealAction(AbstractDungeon.player,AbstractDungeon.player,1));
        }
        else{
            addToBot(new LoseHPAction(this, this, 1000));
            if(AbstractCharBoss.boss!=null){
                addToBot(new GainBlockWithPowerAction(AbstractCharBoss.boss,2));
                addToBot(new HealAction(AbstractCharBoss.boss,AbstractCharBoss.boss,100));
            }
        }
    }

    @Override
    protected void getMove(int i) {
        setMove((byte) 0, Intent.BUFF);
    }

    @Override
    public void damage(DamageInfo info) {
        if(info.owner!=null&&info.owner.isPlayer&&AbstractMonsterPatch.friendlyField.friendly.get(this)){
            return;
        }
        super.damage(info);
    }

    @Override
    public void die() {
        super.die();
    }
}
