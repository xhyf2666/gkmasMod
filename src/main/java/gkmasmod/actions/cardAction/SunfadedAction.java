package gkmasmod.actions.cardAction;


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.GrandFinale;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import gkmasmod.cardGrowEffect.BlockGrow;
import gkmasmod.cardGrowEffect.DamageGrow;
import gkmasmod.cardGrowEffect.LoseBlockGrow;
import gkmasmod.cards.special.Sunfaded7_sp;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.monster.ChangeScene;
import gkmasmod.monster.LatterEffect;
import gkmasmod.powers.*;
import gkmasmod.utils.GrowHelper;
import gkmasmod.utils.IdolData;
import gkmasmod.vfx.effect.ManySakuraFallEffect;

public class SunfadedAction extends AbstractGameAction {
    int index;
    private static final int limit = 6;

    public SunfadedAction(int index) {
        this.index = index;
    }

    public void update() {
        String s = "gkmasMod:SunfadedPower%d";
        boolean[] hasPower = new boolean[limit+1];
        AbstractPlayer p = AbstractDungeon.player;
        int count = 0;
        for (int i = 1;i <= limit; i++) {
            if(p.hasPower(String.format(s,i))) {
                hasPower[i] = true;
                count++;
            }
        }
        switch (index){
            case 1:
                addToBot(new ApplyPowerAction(p,p,new SunfadedPower1(p,2),2));
                break;
            case 2:
                addToBot(new ApplyPowerAction(p,p,new SunfadedPower2(p,1),1));
                break;
            case 3:
                addToBot(new ApplyPowerAction(p,p,new SunfadedPower3(p,1),1));
                break;
            case 4:
                addToBot(new ApplyPowerAction(p,p,new SunfadedPower4(p,1),1));
                break;
            case 5:
                addToBot(new ApplyPowerAction(p,p,new SunfadedPower5(p,2),2));
                break;
            case 6:
                addToBot(new ApplyPowerAction(p,p,new SunfadedPower6(p,2),2));
                break;
        }
        String skinPath = String.format("gkmasModResource/img/idol/%s/stand/stand_skin%d.scml", IdolData.shro,50+index);
        if(p instanceof IdolCharacter){
            this.addToBot(new VFXAction(new ManySakuraFallEffect()));
            ((IdolCharacter) p).refreshSkin(skinPath);
        }
        if(!hasPower[index]) {
            hasPower[index] = true;
            count++;
            if(count>=limit){
                GkmasMod.renderScene = false;
                addToBot(new VFXAction(new WhirlwindEffect(new Color(1.0F, 0.9F, 0.4F, 1.0F), true)));
                AbstractGameEffect effect = new ChangeScene(ImageMaster.loadImage("gkmasModResource/img/bg/bg_shro_001.png"));
                AbstractDungeon.effectList.add(new LatterEffect(() -> {
                    AbstractDungeon.effectsQueue.add(effect);
                },0.7F));
                addToBot(new MakeTempCardInHandAction(new Sunfaded7_sp()));
            }
        }
        this.isDone = true;
    }


}
