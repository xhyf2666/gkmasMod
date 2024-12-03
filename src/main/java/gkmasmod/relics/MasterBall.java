package gkmasmod.relics;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import gkmasmod.cards.GkmasCardTag;

import java.util.ArrayList;

public class MasterBall extends MasterRelic {

    private static final String CLASSNAME = MasterBall.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);

    private static final int magic1 = 10;

    private static final int magic2 = 5;

    private static final int magic3 = 150;


    public MasterBall() {
        super(ID, IMG, magic1, magic2);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magic3,magic1,magic2);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new MasterBall();
    }

    public void onEquip() {
        if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
            PocketBook book = (PocketBook)AbstractDungeon.player.getRelic(PocketBook.ID);
            book.healthRate += 1.0f*magic1/100;
            book.threeSizeIncrease += magic2;
            AbstractDungeon.player.gainGold(magic3);
        }
    }

}
