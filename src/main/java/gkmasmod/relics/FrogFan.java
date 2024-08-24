package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class FrogFan extends CustomRelic {

    private static final String CLASSNAME = FrogFan.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("img/relics/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int HP_COST = 2;
    private static final int BASE_MAGIC = 270;

    private static final int YARUKI = 11;
    private float magicNumber;
    private static int playTimes = 4;

    public FrogFan() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
        magicNumber = 1.0F*BASE_MAGIC /100;
    }


    @Override
    public void onVictory() {
        this.counter = playTimes;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],YARUKI,HP_COST,BASE_MAGIC,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new FrogFan();
    }

    public void onUseCard(AbstractCard card, UseCardAction useCardAction) {
        if (this.counter > 0) {
            int amount = AbstractDungeon.player.getPower(DexterityPower.POWER_ID)==null?0:AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;

            if (card.type == AbstractCard.CardType.ATTACK && amount>YARUKI) {
                int damage = (int)(1.0F*amount*magicNumber);
                this.flash();
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, HP_COST));
                addToBot(new DamageAction(useCardAction.target, new DamageInfo(AbstractDungeon.player, damage, useCardAction.damageType), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                this.counter--;
                if (this.counter == 0) {
                    this.grayscale = true;
                }
            }
        }

    }

    public void atBattleStart() {
        this.counter = playTimes;
    }


    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }
}
