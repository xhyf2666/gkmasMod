package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class MasterGirding extends MasterRelic {

    private static final String CLASSNAME = MasterGirding.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);

    private static final int magic1 = 0;

    private static final int magic2 = 5;

    private static final int magic3 = 5;

    private static final int HP_RECOVER = 12;

    private static final int playTimes = 0;

    public MasterGirding() {
        super(ID, IMG, magic1, magic2);
        this.counter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],HP_RECOVER,magic3,magic3,magic2);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new MasterGirding();
    }

    public void onEquip() {
        if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
            PocketBook book = (PocketBook)AbstractDungeon.player.getRelic(PocketBook.ID);
            book.healthRate += 1.0f*magic1/100;
            book.threeSizeIncrease += magic2;
        }
    }

    public void onEnterSpecialShop(){
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.flash();
        if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
            AbstractDungeon.player.heal(HP_RECOVER);
            this.counter++;
            PocketBook book = (PocketBook)AbstractDungeon.player.getRelic(PocketBook.ID);
            book.healthRate += 1.0f*magic3/100;
        }
    }



}
