package gkmasmod.utils;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.patches.CardGroupPatch;
import gkmasmod.patches.MapRoomNodePatch;
import gkmasmod.powers.DaSpPower;
import gkmasmod.powers.ViSpPower;
import gkmasmod.powers.VoSpPower;
import gkmasmod.relics.PocketBook;
import gkmasmod.screen.ThreeSizeChangeScreen;
import gkmasmod.vfx.effect.DelayedEffect;
import gkmasmod.vfx.effect.GainThreeSizeSpEffect;
import gkmasmod.vfx.effect.ThreeSizeChangeEffect;

import java.util.ArrayList;
import java.util.Random;

public class ThreeSizeHelper {
    public static float spRate = 0.7f;

    public static enum ThreeType{
        VO,DA,VI
    }

    public static void addThreeSize(boolean drawInstantly){
        ArrayList<Float> res = new ArrayList<>();
        ArrayList<AbstractMonster.EnemyType> types = new ArrayList<>();
        ArrayList<Integer> SPs = new ArrayList<>();
        getMonsterInfo(res, types, SPs);
        int size = res.size();
        IdolCharacter idol = (IdolCharacter) AbstractDungeon.player;
        for(int i = 0; i < size; i++){
            float rate = res.get(i);
            AbstractMonster.EnemyType type = types.get(i);
            int sp = SPs.get(i);
            int score = getThreeSizeAppend(rate,type);
            idol.changeVo((int)(0.5F+1.0F*score/3));
            idol.changeDa((int)(0.5F+1.0F*score/3));
            idol.changeVi((int)(0.5F+1.0F*score/3));
        }
        int[] preThreeSize = idol.getPreThreeSize();
        int[] currentThreeSize = idol.getThreeSize();
        if(drawInstantly){
            ThreeSizeChangeScreen.VoInst = null;
            ThreeSizeChangeScreen.DaInst = null;
            ThreeSizeChangeScreen.ViInst = null;
            AbstractDungeon.effectList.add(new ThreeSizeChangeEffect(0,preThreeSize[0],currentThreeSize[0],Settings.WIDTH/2-750*Settings.xScale,Settings.HEIGHT/2+200*Settings.scale,1.5F));
            AbstractDungeon.effectList.add(new ThreeSizeChangeEffect(1,preThreeSize[1],currentThreeSize[1],Settings.WIDTH/2-650*Settings.xScale,Settings.HEIGHT/2+250*Settings.scale,1.5F));
            AbstractDungeon.effectList.add(new ThreeSizeChangeEffect(2,preThreeSize[2],currentThreeSize[2],Settings.WIDTH/2-550*Settings.xScale,Settings.HEIGHT/2+200*Settings.scale,1.5F));
        }
        else{
            ThreeSizeChangeScreen.VoInst = new ThreeSizeChangeScreen(0,preThreeSize[0],currentThreeSize[0],Settings.WIDTH/2-750*Settings.xScale,Settings.HEIGHT/2+200*Settings.scale,1.5F);
            ThreeSizeChangeScreen.DaInst = new ThreeSizeChangeScreen(1,preThreeSize[1],currentThreeSize[1],Settings.WIDTH/2-650*Settings.xScale,Settings.HEIGHT/2+250*Settings.scale,1.5F);
            ThreeSizeChangeScreen.ViInst = new ThreeSizeChangeScreen(2,preThreeSize[2],currentThreeSize[2],Settings.WIDTH/2-550*Settings.xScale,Settings.HEIGHT/2+200*Settings.scale,1.5F);
        }
    }

    private static void getMonsterInfo(ArrayList<Float> res, ArrayList<AbstractMonster.EnemyType> types, ArrayList<Integer> SPs){
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            String monsterID = monster.id;
            String monsterName = monster.name;
            AbstractMonster.EnemyType type_ = monster.type;
            int currentHP = monster.currentHealth;
            int maxHP = monster.maxHealth;
            if(monster.hasPower(MinionPower.POWER_ID))
                continue;
            float rate = 1.0F - 1.0F *(currentHP) / maxHP;
            int sp = 0;
            if(monster.hasPower(VoSpPower.POWER_ID))
                sp = 1;
            else if(monster.hasPower(DaSpPower.POWER_ID))
                sp = 2;
            else if(monster.hasPower(ViSpPower.POWER_ID))
                sp = 3;
            res.add(rate);
            types.add(type_);
            SPs.add(sp);

            System.out.println("Monster ID: " + monsterID);
            System.out.println("Monster Name: " + monsterName);
            System.out.println("Current HP: " + currentHP);
            System.out.println("Max HP: " + maxHP);
            System.out.println("type: " + type_);
            System.out.println("SP: " + sp);
        }
    }

    public static int getThreeSizeAppend(float rate,AbstractMonster.EnemyType type){
        int actNum = AbstractDungeon.actNum;
        int floorNum = AbstractDungeon.floorNum;

        int base = 20;
        if (actNum == 2)
            base += 20;
        else if (actNum == 3)
            base += 40;
        else if (actNum == 4)
            base += 60;

        if (actNum < 4 && floorNum%17 > 8)
            base += 20;

        if(type==AbstractMonster.EnemyType.ELITE)
            base = (int)(base * 1.5F);
        else if(type==AbstractMonster.EnemyType.BOSS)
            base = (int)(base * 2.5F);

        base = (int)(base * rate);
        return base;
    }

}
