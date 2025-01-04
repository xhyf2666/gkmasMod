package gkmasmod.monster.friend;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import gkmasmod.patches.AbstractMonsterPatch;
import gkmasmod.powers.FriendTemariPower1;

public class FriendBlackHole extends CustomMonster {

    public static final String ID = "FriendBlackHole";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:FriendBlackHole");

    public static final String NAME = monsterStrings.NAME;

    private static int MAX_HEALTH = 10;

    public FriendBlackHole() {
        this(000.0F, 0.0F);
    }

    public FriendBlackHole(float x, float y) {
        super(NAME, ID, MAX_HEALTH, -8.0F, 0.0F, 120.0F, 120.0F, null, x, y);
        this.drawX = (float) Settings.WIDTH * 0.25F+ x;
        this.drawY = (float) Settings.HEIGHT * 0.6F+ y;
        AbstractMonsterPatch.friendlyField.friendly.set(this,true);
//        addToBot(new ApplyPowerAction(this,this,new FriendBlackHolePower(this)));
        this.img = new Texture("gkmasModResource/img/monsters/other/FriendBlackHole.png");
    }

    @Override
    public void takeTurn() {
//        addToBot(new DamageRandomEnemyAction(new DamageInfo(this, 3), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    protected void getMove(int i) {
        this.hideHealthBar();
        setMove((byte) 0, Intent.NONE);
    }

    @Override
    public void damage(DamageInfo info) {
        return;
//        if(info.owner.isPlayer){
//            return;
//        }
//        super.damage(info);
    }
}
