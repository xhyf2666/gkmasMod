package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.patches.MapRoomNodePatch;
import gkmasmod.powers.GoodTune;

public class StruggleRecord extends CustomRelic {

    private static final String CLASSNAME = StruggleRecord.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.SPECIAL;

    private static final int magicNumber = 10;

    public StruggleRecord() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }



    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new StruggleRecord();
    }


    public void onEquip() {
        if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
            GkmasMod.listeners.forEach(listener -> listener.onCardImgUpdate());
            Random spRng = new Random(Settings.seed, AbstractDungeon.actNum*100);
            float chance = 0.2f;
            if(AbstractDungeon.player.hasRelic(StruggleRecord.ID))
                chance += 0.1f;
            chance += AbstractDungeon.actNum * 0.1f;
            if(AbstractDungeon.actNum==2)
                chance += AbstractDungeon.ascensionLevel * 0.005f;
            if(AbstractDungeon.actNum==3)
                chance += AbstractDungeon.ascensionLevel * 0.01f;
            if(AbstractDungeon.floorNum %17 >7)
                chance += 0.05f;
            int row_num = AbstractDungeon.map.size() - 1;
            while(row_num >= 0) {
                for(MapRoomNode n : AbstractDungeon.map.get(row_num)){
                    if(n.room instanceof MonsterRoom){
                        if(spRng.randomBoolean(chance)){
                            MapRoomNodePatch.SPField.isSP.set(n, true);
                        }
                    }
                }
                row_num--;
            }

        }

    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }
}
