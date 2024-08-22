package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.lwjgl.Sys;

public class EssentialStainlessSteelBottle extends CustomRelic {

    private static final String CLASSNAME = EssentialStainlessSteelBottle.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("img/relics/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.COMMON;

    private static final int BASE_MAGIC = 2;

    public EssentialStainlessSteelBottle() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }


    @Override
    public void onVictory() {
        this.counter = 1;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],BASE_MAGIC,1);
    }

    @Override
    public AbstractRelic makeCopy() {
        return (AbstractRelic)new EssentialStainlessSteelBottle();
    }


    public void onEquip() {}

    public void atTurnStart() {
        if (this.counter > 0) {
            int count = AbstractDungeon.player.getPower(StrengthPower.POWER_ID)==null?0:AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
            if(count>2){
                addToBot((AbstractGameAction)new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, BASE_MAGIC), BASE_MAGIC));
                this.counter--;
            }
        }

    }

    public void atBattleStart() {
        this.counter = 1;
    }

    public  void  onPlayerEndTurn(){
    }
}
