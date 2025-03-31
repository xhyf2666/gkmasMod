package gkmasmod.monster.friend;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import gkmasmod.patches.AbstractMonsterPatch;
import gkmasmod.powers.FriendChinaPower1;
import gkmasmod.powers.FriendChinaPower2;
import gkmasmod.powers.FriendUmePower1;
import gkmasmod.powers.FriendUmePower2;

public class FriendUme extends CustomMonster {

    public static final String ID = "FriendUme";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:FriendUme");

    public static final String NAME = monsterStrings.NAME;

    private static int MAX_HEALTH = 10;

    public FriendUme() {
        this(000.0F, 0.0F);
    }

    public FriendUme(float x, float y) {
        super(NAME, ID, MAX_HEALTH, -8.0F, 0.0F, 120.0F, 120.0F, null, x, y);
        this.drawX = (float) Settings.WIDTH * 0.30F+ x;
        this.drawY = (float)Settings.HEIGHT * 0.35F + y;
        AbstractMonsterPatch.friendlyField.friendly.set(this,true);
        addToBot(new ApplyPowerAction(this,this,new FriendUmePower1(this)));
        addToBot(new ApplyPowerAction(this,this,new FriendUmePower2(this)));
        this.img = new Texture("gkmasModResource/img/monsters/other/FriendUme.png");
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
