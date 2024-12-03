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

public class MasterRelic extends CustomRelic {

    private static final String CLASSNAME = MasterRelic.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.SPECIAL;

    public int threeSizeIncrease = 0;

    public float healthRate = 0.0f;

    public MasterRelic(String ID, String IMG,int threeSizeIncrease,float healthRate) {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG), RARITY, LandingSound.CLINK);
        this.counter = -1;
        this.threeSizeIncrease = threeSizeIncrease;
        this.healthRate = healthRate;
    }

}
