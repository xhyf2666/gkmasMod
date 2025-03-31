package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.GrowAction;
import gkmasmod.actions.ShowCardToDiscardEffect;
import gkmasmod.cards.GkmasCard;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.downfall.charbosses.actions.common.EnemyMakeTempCardInHandAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.growEffect.BlockGrow;
import gkmasmod.growEffect.DamageGrow;
import gkmasmod.utils.GrowHelper;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class TempSavePower extends AbstractPower {
    private static final String CLASSNAME = TempSavePower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private Queue<AbstractCard> cards = new ArrayDeque<>();

    public int CARD_LIMIT = 2;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public TempSavePower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = 0;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        String tmp="";
        for(AbstractCard c:cards){
            tmp+=c.name+",";
        }
        int limit = 2;
        if(CARD_LIMIT>0)
            limit = CARD_LIMIT;
        if(tmp.length()>0)
            tmp = tmp.substring(0,tmp.length()-1);
        this.description = String.format(DESCRIPTIONS[0],tmp,limit);
    }

    public static void addCard(AbstractCreature p,AbstractCard card){
        TempSavePower power = (TempSavePower) p.getPower(POWER_ID);
        if(power == null){
            power = new TempSavePower(p);
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,power));
        }
        power.cards.offer(card);
        if(p instanceof AbstractPlayer){
            AbstractDungeon.player.discardPile.removeCard(card);
            AbstractDungeon.player.drawPile.removeCard(card);
            AbstractDungeon.player.hand.removeCard(card);
        }
        if(power.cards.size() > power.CARD_LIMIT){
            AbstractCard c = power.cards.poll();
            AbstractDungeon.effectList.add(new ShowCardToDiscardEffect(c));
            if(p instanceof AbstractPlayer){
                AbstractDungeon.player.discardPile.addToTop(c);
            }
            if(c instanceof GkmasCard){
                ((GkmasCard) c).customTrigger();
            }
            if(c instanceof GkmasBossCard){
                ((GkmasBossCard) c).customTrigger();
            }
        }
        int count = PlayerHelper.getPowerAmount(p,LikeStarsPower.POWER_ID);
        if(count>0){
            AbstractDungeon.actionManager.addToBottom(new GrowAction(DamageGrow.growID, GrowAction.GrowType.allHand,count,p instanceof AbstractCharBoss));
            AbstractDungeon.actionManager.addToBottom(new GrowAction(BlockGrow.growID, GrowAction.GrowType.allHand,count,p instanceof AbstractCharBoss));
        }

        count = PlayerHelper.getPowerAmount(p,LikeStarsSPPower.POWER_ID);
        if(count>0){
            AbstractDungeon.actionManager.addToBottom(new GrowAction(DamageGrow.growID, GrowAction.GrowType.allHand,count,p instanceof AbstractCharBoss));
            AbstractDungeon.actionManager.addToBottom(new GrowAction(BlockGrow.growID, GrowAction.GrowType.allHand,count,p instanceof AbstractCharBoss));
            AbstractDungeon.actionManager.addToBottom(new GrowAction(DamageGrow.growID, GrowAction.GrowType.allTempSave,count,p instanceof AbstractCharBoss));
            AbstractDungeon.actionManager.addToBottom(new GrowAction(BlockGrow.growID, GrowAction.GrowType.allTempSave,count,p instanceof AbstractCharBoss));
        }

        power.updateDescription();
        power.amount= power.cards.size();
    }

    public static void addCard(AbstractCreature p,ArrayList<AbstractCard> cards){
        TempSavePower power = (TempSavePower) p.getPower(POWER_ID);
        if(power == null){
            power = new TempSavePower(p);
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,power));
        }
        for(AbstractCard card:cards){
            power.cards.offer(card);
            if(p instanceof AbstractPlayer){
                AbstractDungeon.player.discardPile.removeCard(card);
                AbstractDungeon.player.drawPile.removeCard(card);
                AbstractDungeon.player.hand.removeCard(card);
            }
        }
        int diff = power.cards.size() - power.CARD_LIMIT;
        int i =0;

        while (power.cards.size() > power.CARD_LIMIT){
            AbstractCard c = power.cards.poll();
            if(diff == 2){
                AbstractDungeon.effectList.add(new ShowCardToDiscardEffect(c, Settings.WIDTH /2.0F*(0.8F+i*0.4F),Settings.HEIGHT/2.0F));
            }
            else{
                AbstractDungeon.effectList.add(new ShowCardToDiscardEffect(c));
            }
            if(p instanceof AbstractPlayer){
                AbstractDungeon.player.discardPile.addToTop(c);
            }
            if(c instanceof GkmasCard){
                ((GkmasCard) c).customTrigger();
            }
            if(c instanceof GkmasBossCard){
                ((GkmasBossCard) c).customTrigger();
            }

            i++;
        }
        int count = PlayerHelper.getPowerAmount(p,LikeStarsPower.POWER_ID);
        if(count>0){
            AbstractDungeon.actionManager.addToBottom(new GrowAction(DamageGrow.growID, GrowAction.GrowType.allHand,count));
            AbstractDungeon.actionManager.addToBottom(new GrowAction(BlockGrow.growID, GrowAction.GrowType.allHand,count));
        }
        count = PlayerHelper.getPowerAmount(p,LikeStarsSPPower.POWER_ID);
        if(count>0){
            AbstractDungeon.actionManager.addToBottom(new GrowAction(DamageGrow.growID, GrowAction.GrowType.allHand,count));
            AbstractDungeon.actionManager.addToBottom(new GrowAction(BlockGrow.growID, GrowAction.GrowType.allHand,count));
            AbstractDungeon.actionManager.addToBottom(new GrowAction(DamageGrow.growID, GrowAction.GrowType.allTempSave,count));
            AbstractDungeon.actionManager.addToBottom(new GrowAction(BlockGrow.growID, GrowAction.GrowType.allTempSave,count));
        }
        power.updateDescription();
        power.amount= power.cards.size();
    }

    public static void changeLimit(AbstractCreature p,int change){
        TempSavePower power = (TempSavePower) p.getPower(POWER_ID);
        if(power == null){
            power = new TempSavePower(p);
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,power));
        }
        power.CARD_LIMIT += change;
        while(power.cards.size()>power.CARD_LIMIT){
            AbstractCard c = power.cards.poll();
            AbstractDungeon.effectList.add(new ShowCardToDiscardEffect(c));
            if(p instanceof AbstractPlayer){
                AbstractDungeon.player.discardPile.addToTop(c);
            }
            if(c instanceof GkmasCard){
                ((GkmasCard) c).customTrigger();
            }
            if(c instanceof GkmasBossCard){
                ((GkmasBossCard) c).customTrigger();
            }
        }
        power.updateDescription();
    }

    public Queue<AbstractCard> getCards() {
        return cards;
    }

    public void getInHand(){
        while(!cards.isEmpty()){
            AbstractCard c = cards.poll();
            if(this.owner instanceof AbstractPlayer){
                addToTop(new MakeTempCardInHandAction(c));
            }
            else if(this.owner instanceof AbstractCharBoss&&AbstractCharBoss.boss!=null){
                addToTop(new EnemyMakeTempCardInHandAction(c));
            }
            if(c instanceof GkmasCard){
                ((GkmasCard) c).customTrigger();
            }
            if(c instanceof GkmasBossCard){
                ((GkmasBossCard) c).customTrigger();
            }
        }
        this.amount= this.cards.size();
        updateDescription();
    }
}
