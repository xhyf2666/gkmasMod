package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.AbsoluteNewSelfAction;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.actions.ModifyDamageAction;
import gkmasmod.stances.FullPowerStance;

public class EveryoneDream extends CustomRelic {

    private static final String CLASSNAME = EveryoneDream.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int BLOCK = 9;
    private static final int BASE_DAMAGE = 9;
    private static int playTimes = 1;

    public EveryoneDream() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }


    @Override
    public void onVictory() {
        this.counter = playTimes;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],BASE_DAMAGE,BLOCK,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new EveryoneDream();
    }

    public void onUseCard(AbstractCard card, UseCardAction useCardAction) {
        if (AbstractDungeon.player.stance.ID.equals(FullPowerStance.STANCE_ID) &&this.counter > 0) {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.flash();
            AbstractCreature target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            addToBot(new ModifyDamageAction(target, new DamageInfo(AbstractDungeon.player, BASE_DAMAGE, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            addToBot(new GainBlockWithPowerAction(AbstractDungeon.player, BLOCK));
            this.counter--;
            if (this.counter == 0) {
                this.grayscale = true;
            }
        }

    }

    public void atBattleStart() {
        this.counter = playTimes;
    }


    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }
}
