package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.monster.friend.*;

public class SyngUpRelic extends CustomRelic {

    private static final String CLASSNAME = SyngUpRelic.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    public SyngUpRelic() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }


    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0]);
    }


    public void atBattleStart() {
        addToBot(new SpawnMonsterAction(new FriendTemari(AbstractDungeon.player.hb_x+100, AbstractDungeon.player.hb_y,1),false));
        addToBot(new SpawnMonsterAction(new FriendRinha(AbstractDungeon.player.hb_x+100, AbstractDungeon.player.hb_y,1),false));
        addToBot(new SpawnMonsterAction(new FriendBlackHole(AbstractDungeon.player.hb_x, AbstractDungeon.player.hb_y),false));
        addToBot(new SpawnMonsterAction(new FriendKnife(AbstractDungeon.player.hb_x, AbstractDungeon.player.hb_y),false));
    }

    public  void  onPlayerEndTurn(){
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }
}
