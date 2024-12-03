//package gkmasmod.downfall.charbosses.actions.unique;
//
//
//import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
//import gkmasmod.downfall.charbosses.orbs.EnemyDark;
//import com.megacrit.cardcrawl.actions.AbstractGameAction;
//import com.megacrit.cardcrawl.core.Settings;
//import com.megacrit.cardcrawl.orbs.AbstractOrb;
//import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
//
//import java.util.Iterator;
//
//public class EnemyRedoAction extends AbstractGameAction {
//    private AbstractOrb orb;
//
//    public void update() {
//        if (!AbstractCharBoss.boss.orbs.isEmpty()) {
//            this.orb = AbstractCharBoss.boss.orbs.get(0);
//            if (this.orb instanceof com.megacrit.cardcrawl.orbs.EmptyOrbSlot) {
//                this.isDone = true;
//            } else {
//                addToTop(new ChannelAction(this.orb, false));
//                addToTop(new EvokeOrbAction(1));
//            }
//        }
//        this.isDone = true;
//    }
//}
