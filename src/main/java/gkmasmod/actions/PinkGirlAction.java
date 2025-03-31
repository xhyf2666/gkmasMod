package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import gkmasmod.monster.friend.FriendNunu;
import gkmasmod.patches.AbstractMonsterPatch;
import gkmasmod.powers.IntangibleSpecialPower;
import gkmasmod.powers.PinkGirlPower;
import gkmasmod.utils.SoundHelper;

public class PinkGirlAction extends AbstractGameAction {
    AbstractCreature owner;

    public PinkGirlAction(AbstractCreature owner) {
        this.owner = owner;
    }

    public void update() {
        int nunuCount=0;
        for (AbstractMonster monster: AbstractDungeon.getMonsters().monsters){
            if(monster instanceof FriendNunu && !monster.isDeadOrEscaped() && AbstractMonsterPatch.friendlyField.friendly.get(monster)==false){
                nunuCount++;
            }
        }
        if(nunuCount==0){
            if(this.owner.hasPower(PinkGirlPower.POWER_ID)){
                this.owner.getPower(PinkGirlPower.POWER_ID).flash();
            }
            SoundHelper.playSound("gkmasModResource/audio/voice/monster/nadeshiko/adv_dear_jsna_015_andk_004.ogg");
            addToBot(new RemoveSpecificPowerAction(this.owner,this.owner, IntangibleSpecialPower.POWER_ID));
        }
        this.isDone = true;
    }
}