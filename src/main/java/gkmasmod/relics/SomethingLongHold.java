package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.GrowAction;
import gkmasmod.actions.ModifyDamageRandomEnemyAction;
import gkmasmod.cardGrowEffect.DamageGrow;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.stances.FullPowerStance;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.GrowHelper;

public class SomethingLongHold extends CustomRelic {

    private static final String CLASSNAME = SomethingLongHold.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int MAGIC = 100;

    public SomethingLongHold() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],MAGIC);
    }

    @Override
    public void onEquip() {
        this.counter = 0;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SomethingLongHold();
    }

    public void onUseCard(AbstractCard card, UseCardAction useCardAction) {
        if(AbstractDungeon.player.stance.ID.equals(FullPowerStance.STANCE_ID)){
            if(card.hasTag(GkmasCardTag.PRESERVATION_TAG)){
                addToBot(new ModifyDamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player,this.counter), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }
        }
    }

    public void onFullPowerIncrease(int add){
        this.counter+= add;
    }


    public void atBattleStart() {
        this.counter = 0;
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }
}
