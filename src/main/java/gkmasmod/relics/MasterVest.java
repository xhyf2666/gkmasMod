package gkmasmod.relics;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.cards.logic.BaseAwareness;
import gkmasmod.cards.logic.BaseVision;
import gkmasmod.cards.logic.ChangeMood;
import gkmasmod.cards.logic.KawaiiGesture;
import gkmasmod.cards.sense.BaseBehave;
import gkmasmod.cards.sense.BaseExpression;
import gkmasmod.cards.sense.Challenge;
import gkmasmod.cards.sense.TryError;

import javax.smartcardio.Card;
import java.util.ArrayList;

public class MasterVest extends MasterRelic {

    private static final String CLASSNAME = MasterVest.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);

    private static final int magic1 = 10;

    private static final int magic2 = 5;

    private static final int magic3 = 2;


    private static final int playTimes = 2;

    public MasterVest() {
        super(ID, IMG, magic1, magic2);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magic3,magic1,magic2);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new MasterVest();
    }

    public void onEquip() {
        if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
            PocketBook book = (PocketBook)AbstractDungeon.player.getRelic(PocketBook.ID);
            book.healthRate += 1.0f*magic1/100;
            book.threeSizeIncrease += magic2;

            CardGroup group = AbstractDungeon.player.masterDeck.getPurgeableCards();
            ArrayList<AbstractCard> cards = new ArrayList<>();

            for(AbstractCard card:group.group){
                if(card.rarity == AbstractCard.CardRarity.BASIC&&!card.tags.contains(GkmasCardTag.IDOL_CARD_TAG)){
                    if(card.cardID.equals(Challenge.ID)||card.cardID.equals(TryError.ID)||card.cardID.equals(KawaiiGesture.ID)||card.cardID.equals(ChangeMood.ID)){
                        continue;
                    }
                    cards.add(card);
                }
            }

            if(cards.size() <=playTimes){
                for (AbstractCard card:cards){
                    AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(card, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                    AbstractDungeon.player.masterDeck.removeCard(card);
                }
                this.grayscale = true;
                return;
            }
            //随机从cards中选取playTimes张卡牌
            Random rng = new Random(Settings.seed);
            for(int i = 0;i<playTimes;i++){
                int index = rng.random(cards.size()-1);
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(cards.get(index), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.player.masterDeck.removeCard(cards.get(index));
                cards.remove(index);
            }
            this.grayscale = true;
        }
    }

}
