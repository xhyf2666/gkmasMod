package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import gkmasmod.cards.special.ChocolateForProducer;
import gkmasmod.utils.NameHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class RinamiRobotPower extends AbstractPower {
    private static final String CLASSNAME = RinamiRobotPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private boolean flag = false;

    private int MAGIC = 1;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png","CanYouAcceptSPPower");
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png","CanYouAcceptSPPower");

    public RinamiRobotPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.amount);
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        flashWithoutSound();
        if(!flag){
            ArrayList<AbstractCard> tmpPool = new ArrayList<>();
            getRandomCard(AbstractCard.CardColor.BLUE,tmpPool);
            for (int i = 0; i < this.amount; i++) {
                AbstractCard tmpCard = tmpPool.get(AbstractDungeon.cardRandomRng.random(tmpPool.size() - 1));
                addToBot(new MakeTempCardInDrawPileAction(tmpCard, 1, true, true));
            }
            flag = true;
        }
    }

    public ArrayList<AbstractCard> getRandomCard(AbstractCard.CardColor color, ArrayList<AbstractCard> tmpPool) {
        Iterator<Map.Entry<String, AbstractCard>> cardLib = CardLibrary.cards.entrySet().iterator();
        while (true) {
            if (!cardLib.hasNext())
                return tmpPool;
            Map.Entry c = cardLib.next();
            AbstractCard card = (AbstractCard)c.getValue();
            if (card.color.equals(color) && card.rarity != AbstractCard.CardRarity.BASIC &&card.rarity != AbstractCard.CardRarity.SPECIAL && card.rarity != AbstractCard.CardRarity.CURSE) {
                tmpPool.add(card);
            }
        }
    }

}
