package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import gkmasmod.monster.friend.FriendNunu;
import gkmasmod.powers.PinkGirlPower;
import gkmasmod.utils.SoundHelper;
import gkmasmod.vfx.effect.GakuenLinkMasterEffect;

public class GakuenLinkMasterlAction extends AbstractGameAction {
    AbstractCreature owner;
    private int count = 0;
    private int delay = 0;

    private static int TIMES = 20;
    public GakuenLinkMasterlAction(AbstractCreature owner) {
        this.owner = owner;
    }

    public void update() {
        //30 * 10
        if(delay==0&&count<TIMES){
            AbstractDungeon.effectList.add(new GakuenLinkMasterEffect());
            count++;
        }
        delay++;
        if(delay==10){
            delay = 0;
        }

        if(count==TIMES)
            this.isDone = true;
    }
}