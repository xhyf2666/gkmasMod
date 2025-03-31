package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.map.DungeonMap;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.GainBlockWithPowerAction;

public class NIABadge extends CustomRelic {

    private static final String CLASSNAME = NIABadge.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.SPECIAL;

    public NIABadge() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.bossKey = "MonsterRinha";
        if (DungeonMap.boss != null && DungeonMap.bossOutline != null) {
            DungeonMap.boss.dispose();
            DungeonMap.bossOutline.dispose();
        }
        DungeonMap.boss = ImageMaster.loadImage("gkmasModResource/img/monsters/Rinha/icon.png");
        DungeonMap.bossOutline = ImageMaster.loadImage("gkmasModResource/img/monsters/Rinha/icon.png");
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0]);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new NIABadge();
    }

    public void atTurnStart() {
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }
}
