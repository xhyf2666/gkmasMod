package gkmasmod.monster.friend;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.patches.AbstractMonsterPatch;
import gkmasmod.powers.FriendRinhaPower1;
import gkmasmod.powers.FriendTemariPower1;
import gkmasmod.powers.FriendTemariPower2;

public class FriendTemari extends CustomMonster {

    public static final String ID = "FriendTemari";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:FriendTemari");

    public static final String NAME = monsterStrings.NAME;

    public int stage = 1;

    private static int MAX_HEALTH = 10;

    public FriendTemari() {
        this(000.0F, 0.0F);
    }

    public FriendTemari(float x, float y) {
        this(x, y,1);
    }

    public FriendTemari(float x, float y, int stage) {
        super(NAME, ID, MAX_HEALTH, -8.0F, 0.0F, 120.0F, 120.0F, null, x, y);
        this.stage = stage;
        this.drawX = (float) Settings.WIDTH * 0.10F+ x;
        this.drawY = (float)Settings.HEIGHT * 0.35F + y;
        AbstractMonsterPatch.friendlyField.friendly.set(this,true);
        addToBot(new ApplyPowerAction(this,this,new FriendTemariPower1(this)));
        addToBot(new ApplyPowerAction(this,this,new FriendTemariPower2(this)));
        this.img = new Texture(String.format("gkmasModResource/img/monsters/other/FriendTemari%s.png",stage));
    }

    @Override
    public void takeTurn() {
//        addToBot(new DamageRandomEnemyAction(new DamageInfo(this, 3), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    protected void getMove(int i) {
        setMove((byte) 0, Intent.NONE);
    }

    @Override
    public void damage(DamageInfo info) {
        if(info.owner!=null&&info.owner.isPlayer&&AbstractMonsterPatch.friendlyField.friendly.get(this)){
            return;
        }
        if(info.owner==null){
            return;
        }
        super.damage(info);
    }
}
