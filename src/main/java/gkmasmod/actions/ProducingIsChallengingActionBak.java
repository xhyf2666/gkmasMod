package gkmasmod.actions;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import gkmasmod.cardGrowEffect.AbstractGrowEffect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ProducingIsChallengingActionBak extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private int num;

    public ProducingIsChallengingActionBak(int numCards) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.num = numCards;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            if (this.p.hand.isEmpty()) {
                this.isDone = true;
            }

            if (this.p.hand.group.size() < this.num) {
                this.isDone = true;
                return;
            }

            if(p.hand.group.size()>=2)
            {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 2, false, false, false,false);
                this.tickDuration();
                return;
            }
        }
        else {
            Iterator var1;
            AbstractCard c1,c2;
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                    var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();
                    if(AbstractDungeon.handCardSelectScreen.selectedCards.group.size()==2){
                        c1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0);
                        c2 = AbstractDungeon.handCardSelectScreen.selectedCards.group.get(1);
                        ArrayList<AbstractCardModifier> c1mods = new ArrayList<>();
                        ArrayList<AbstractCardModifier> c2mods = new ArrayList<>();
                        for(AbstractCardModifier mod : CardModifierManager.modifiers(c1)){
                            if(mod instanceof AbstractGrowEffect){
                                c1mods.add(mod.makeCopy());
                            }
                        }
                        for(AbstractCardModifier mod : CardModifierManager.modifiers(c2)){
                            if(mod instanceof AbstractGrowEffect){
                                c2mods.add(mod.makeCopy());
                            }
                        }
                        List<AbstractCardModifier> modifiersC1 = CardModifierManager.modifiers(c1);
                        if (modifiersC1 != null) {
                            Iterator<AbstractCardModifier> iteratorC1 = modifiersC1.iterator();
                            while (iteratorC1.hasNext()) {
                                AbstractCardModifier mod = iteratorC1.next();
                                if (mod instanceof AbstractGrowEffect) {
                                    iteratorC1.remove();  // 使用 Iterator 的 remove 方法安全地移除元素
                                }
                            }
                        }

                        List<AbstractCardModifier> modifiersC2 = CardModifierManager.modifiers(c2);
                        if (modifiersC2 != null) {
                            Iterator<AbstractCardModifier> iteratorC2 = modifiersC2.iterator();
                            while (iteratorC2.hasNext()) {
                                AbstractCardModifier mod = iteratorC2.next();
                                if (mod instanceof AbstractGrowEffect) {
                                    iteratorC2.remove();  // 同样的方式安全地移除元素
                                }
                            }
                        }
//                        for(AbstractCardModifier mod : CardModifierManager.modifiers(c1)){
//                            if(mod instanceof AbstractGrowEffect){
//                                CardModifierManager.removeSpecificModifier(c1,mod,false);
//                            }
//                        }
//                        for(AbstractCardModifier mod : CardModifierManager.modifiers(c2)){
//                            if(mod instanceof AbstractGrowEffect){
//                                CardModifierManager.removeSpecificModifier(c2,mod,false);
//                            }
//                        }
                        for(AbstractCardModifier mod : c1mods){
                            CardModifierManager.addModifier(c2,mod);
                        }
                        for(AbstractCardModifier mod : c2mods){
                            CardModifierManager.addModifier(c1,mod);
                        }
                        AbstractDungeon.player.hand.addToTop(c1);
                        AbstractDungeon.player.hand.addToTop(c2);
                    }

                    this.returnCards();
                    AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                    AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
                    this.isDone = true;
                }

            this.tickDuration();
        }
    }


    private void returnCards() {
        this.p.hand.refreshHandLayout();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("gkmasMod:ProducingIsChallengingAction");
        TEXT = uiStrings.TEXT;
    }
}
