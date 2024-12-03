package gkmasmod.utils;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.random.Random;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.patches.AbstractCardPatch;
import gkmasmod.patches.AbstractPlayerPatch;
import gkmasmod.powers.DaSpPower;
import gkmasmod.powers.ViSpPower;
import gkmasmod.powers.VoSpPower;
import gkmasmod.relics.ChristmasLion;
import gkmasmod.relics.PocketBook;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.screen.ThreeSizeChangeScreen;
import gkmasmod.vfx.effect.ThreeSizeChangeEffect;

import java.util.ArrayList;
import java.util.Collections;

public class ThreeSizeHelper {
    public static float spRate = 0.7f;

    public static enum ThreeType{
        VO,DA,VI
    }

    public static void addFixedThreeSize(boolean drawInstantly,int[] value){
        if(!(AbstractDungeon.player.hasRelic(PocketBook.ID)))
            return;
        if(value.length ==3){
            changeFixedVo(value[0]);
            changeFixedDa(value[1]);
            changeFixedVi(value[2]);
        }
        int[] preThreeSize = getPreThreeSize();
        int[] currentThreeSize = getThreeSize();
        if(drawInstantly){
            ThreeSizeChangeScreen.VoInst = null;
            ThreeSizeChangeScreen.DaInst = null;
            ThreeSizeChangeScreen.ViInst = null;
            AbstractDungeon.topLevelEffects.add(new ThreeSizeChangeEffect(0,preThreeSize[0],currentThreeSize[0],Settings.WIDTH/2-750*Settings.xScale,Settings.HEIGHT/2+200*Settings.scale,1.0F));
            AbstractDungeon.topLevelEffects.add(new ThreeSizeChangeEffect(1,preThreeSize[1],currentThreeSize[1],Settings.WIDTH/2-650*Settings.xScale,Settings.HEIGHT/2+250*Settings.scale,1.0F));
            AbstractDungeon.topLevelEffects.add(new ThreeSizeChangeEffect(2,preThreeSize[2],currentThreeSize[2],Settings.WIDTH/2-550*Settings.xScale,Settings.HEIGHT/2+200*Settings.scale,1.0F));
        }
        else{
            ThreeSizeChangeScreen.VoInst = new ThreeSizeChangeScreen(0,preThreeSize[0],currentThreeSize[0],Settings.WIDTH/2-750*Settings.xScale,Settings.HEIGHT/2+200*Settings.scale,1.5F);
            ThreeSizeChangeScreen.DaInst = new ThreeSizeChangeScreen(1,preThreeSize[1],currentThreeSize[1],Settings.WIDTH/2-650*Settings.xScale,Settings.HEIGHT/2+250*Settings.scale,1.5F);
            ThreeSizeChangeScreen.ViInst = new ThreeSizeChangeScreen(2,preThreeSize[2],currentThreeSize[2],Settings.WIDTH/2-550*Settings.xScale,Settings.HEIGHT/2+200*Settings.scale,1.5F);
        }
    }

    public static void addThreeSize(boolean drawInstantly){
        ArrayList<Float> res = new ArrayList<>();
        ArrayList<AbstractMonster.EnemyType> types = new ArrayList<>();
        ArrayList<Integer> SPs = new ArrayList<>();
        getMonsterInfo(res, types, SPs);
        int size = res.size();
        int[] preThreeSize = getPreThreeSize();
        float[] scoreRate = getThreeSizeScoreRate();
        for(int i = 0; i < size; i++){
            float rate = res.get(i);
            AbstractMonster.EnemyType type = types.get(i);
            int score = getThreeSizeAppend(rate,type);
            changeVo((int)(0.5F+1.0F*score*scoreRate[0]));
            changeDa((int)(0.5F+1.0F*score*scoreRate[1]));
            changeVi((int)(0.5F+1.0F*score*scoreRate[2]));
        }

        int[] currentThreeSize = getThreeSize();
        System.out.println(preThreeSize);
        System.out.println(currentThreeSize);
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

        int base = 15;
        if (actNum == 2)
            base += 15;
        else if (actNum == 3)
            base += 30;
        else if (actNum == 4)
            base += 45;

        if (actNum < 4 && floorNum%17 > 8)
            base += 10;

        if(type==AbstractMonster.EnemyType.ELITE)
            base = (int)(base * 1.5F);
        else if(type==AbstractMonster.EnemyType.BOSS)
            base = (int)(base * 2.0F);

        if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
            PocketBook book = (PocketBook)AbstractDungeon.player.getRelic(PocketBook.ID);
            base +=book.threeSizeIncrease;
        }

        base = (int)(base * rate);
        return base;
    }

    public static int getHealthRate(int act){
        if(AbstractDungeon.player instanceof IdolCharacter){
            if(act==1)
                return 4;
            else if(act==2)
                return 11;
            else
                return 25;
        }
        else{
            if(act==1)
                return 2;
            else if(act==2)
                return 6;
            else
                return 10;
        }
    }

    public static void generateCircle(int roundNum){
        if(roundNum < 1)
            return;
        ArrayList<Integer> tmp = new ArrayList<>();
        int baseDamageRate;
        int currentThreeType;
        int require;
        int currentThreeSizeValue = 0;
        if(AbstractDungeon.player instanceof IdolCharacter){
            IdolCharacter idol = (IdolCharacter)AbstractDungeon.player;
            if(roundNum > 0)
                tmp.add(idol.idolData.getFirstThreeType());
            if(roundNum > 1)
                tmp.add(idol.idolData.getSecondThreeType());
            if(roundNum > 2)
                tmp.add(idol.idolData.getThirdThreeType());
            ArrayList<Integer> tmp2 = new ArrayList<>();
//            com.megacrit.cardcrawl.random.Random spRng = new Random(Settings.seed, AbstractDungeon.floorNum*20);
            for(int i = 0; i < roundNum - 3; i++){
                if(i%3==0)
                    tmp2.add(idol.idolData.getFirstThreeType());
                else if (i%3==1)
                    tmp2.add(idol.idolData.getSecondThreeType());
                else
                    tmp2.add(idol.idolData.getThirdThreeType());
            }
            Collections.shuffle(tmp2, new java.util.Random(Settings.seed+AbstractDungeon.floorNum*20));
            for(int i = 0; i < tmp2.size(); i++){
                tmp.add(tmp2.get(i));
            }
            currentThreeType = tmp.get(tmp.size()-1);
            baseDamageRate = idol.idolData.getBaseDamageRate(currentThreeType);
            require = idol.idolData.getThreeSizeRequire(currentThreeType);
            if(idol.idolData.idolName.equals(IdolData.hume)&&idol.idolData.getRelic(SkinSelectScreen.Inst.skinIndex).equals(ChristmasLion.ID)){
                require = idol.idolData.getAnotherThreeSizeRequire(currentThreeType);
            }
        }
        else{
            if(roundNum > 0)
                tmp.add(0);
            if(roundNum > 1)
                tmp.add(1);
            if(roundNum > 2)
                tmp.add(2);
            com.megacrit.cardcrawl.random.Random spRng = new Random(Settings.seed, AbstractDungeon.floorNum*20);
            for(int i = 0; i < roundNum - 3; i++){
                tmp.add(spRng.random(0,2));
            }
            currentThreeType = tmp.get(tmp.size()-1);
            baseDamageRate = 11;
            require = 1000;
        }
        currentThreeSizeValue = AbstractPlayerPatch.ThreeSizeField.threeSize.get(AbstractDungeon.player)[currentThreeType];

        AbstractPlayerPatch.FinalCircleRoundField.finalCircleRound.set(AbstractDungeon.player,tmp);

        AbstractPlayerPatch.IsRenderFinalCircleField.IsRenderFinalCircle.set(AbstractDungeon.player,true);

        double finalDamageRate = calculateDamageRate(baseDamageRate, currentThreeSizeValue,require);

        AbstractPlayerPatch.FinalDamageRateField.finalDamageRate.set(AbstractDungeon.player,finalDamageRate);
    }

    private static double calculateDamageRate(int baseRate, int v,int t){
        double rate = 1.0f*v/t;
        if(rate > 1.0f)
            return 1.0f*baseRate*(1+Math.log(rate)/3)+1;
        return (Math.pow(rate,2)+Math.exp(rate-1)+(rate-1)/Math.E)/2*baseRate+1;
    }

    public static double[] calculateDamageRates(){
        double[] rates = new double[3];
        if(AbstractDungeon.player instanceof IdolCharacter){
            IdolCharacter idol = (IdolCharacter)AbstractDungeon.player;
            for(int i = 0; i < 3; i++){
                int require = idol.idolData.getThreeSizeRequire(i);
                if(idol.idolData.idolName.equals(IdolData.hume)&&idol.idolData.getRelic(SkinSelectScreen.Inst.skinIndex).equals(ChristmasLion.ID)){
                    require = idol.idolData.getAnotherThreeSizeRequire(i);
                }
                rates[i] = calculateDamageRate(idol.idolData.getBaseDamageRate(i),AbstractPlayerPatch.ThreeSizeField.threeSize.get(AbstractDungeon.player)[i],require);
            }
        }
        else{
            for(int i = 0; i < 3; i++){
                rates[i] = calculateDamageRate(11,AbstractPlayerPatch.ThreeSizeField.threeSize.get(AbstractDungeon.player)[i],1000);
            }
        }

        return rates;
    }

    public static void setThreeSize(int[] threeSize){
        AbstractPlayerPatch.ThreeSizeField.threeSize.set(AbstractDungeon.player,threeSize);
        AbstractPlayerPatch.PreThreeSizeField.preThreeSize.set(AbstractDungeon.player,threeSize);
    }

    public static void setThreeSizeRate(float[] threeSizeRate){
        AbstractPlayerPatch.ThreeSizeRateField.threeSizeRate.set(AbstractDungeon.player,threeSizeRate);
    }

    public static int getVo(){
        return AbstractPlayerPatch.ThreeSizeField.threeSize.get(AbstractDungeon.player)[0];
    }

    public static int getDa(){
        return AbstractPlayerPatch.ThreeSizeField.threeSize.get(AbstractDungeon.player)[1];
    }

    public static int getVi(){
        return AbstractPlayerPatch.ThreeSizeField.threeSize.get(AbstractDungeon.player)[2];
    }

    public static int[] getThreeSize(){
        return AbstractPlayerPatch.ThreeSizeField.threeSize.get(AbstractDungeon.player);
    }

    public static float[] getThreeSizeRate(){
        return AbstractPlayerPatch.ThreeSizeRateField.threeSizeRate.get(AbstractDungeon.player);
    }

    public static int[] getPreThreeSize(){
        return AbstractPlayerPatch.PreThreeSizeField.preThreeSize.get(AbstractDungeon.player);
    }

    public static float getVoRate(){
        return AbstractPlayerPatch.ThreeSizeRateField.threeSizeRate.get(AbstractDungeon.player)[0];
    }

    public static float getDaRate(){
        return AbstractPlayerPatch.ThreeSizeRateField.threeSizeRate.get(AbstractDungeon.player)[1];
    }

    public static float getViRate(){
        return AbstractPlayerPatch.ThreeSizeRateField.threeSizeRate.get(AbstractDungeon.player)[2];
    }

    public static void changeVo(int vo){
        int[] preThreeSize = AbstractPlayerPatch.PreThreeSizeField.preThreeSize.get(AbstractDungeon.player).clone();
        int[] threeSize = AbstractPlayerPatch.ThreeSizeField.threeSize.get(AbstractDungeon.player).clone();
        float[] threeSizeRate = AbstractPlayerPatch.ThreeSizeRateField.threeSizeRate.get(AbstractDungeon.player);
        preThreeSize[0] = threeSize[0];
        threeSize[0] += vo+(int)((threeSizeRate[0]*vo)+0.5F);
        AbstractPlayerPatch.PreThreeSizeField.preThreeSize.set(AbstractDungeon.player,preThreeSize);
        AbstractPlayerPatch.ThreeSizeField.threeSize.set(AbstractDungeon.player,threeSize);
    }

    public static void changeDa(int da){
        int[] preThreeSize = AbstractPlayerPatch.PreThreeSizeField.preThreeSize.get(AbstractDungeon.player).clone();
        int[] threeSize = AbstractPlayerPatch.ThreeSizeField.threeSize.get(AbstractDungeon.player).clone();
        float[] threeSizeRate = AbstractPlayerPatch.ThreeSizeRateField.threeSizeRate.get(AbstractDungeon.player);
        preThreeSize[1] = threeSize[1];
        threeSize[1] += da+(int)((threeSizeRate[1]*da)+0.5F);
        AbstractPlayerPatch.PreThreeSizeField.preThreeSize.set(AbstractDungeon.player,preThreeSize);
        AbstractPlayerPatch.ThreeSizeField.threeSize.set(AbstractDungeon.player,threeSize);
    }

    public static void changeVi(int vi){
        int[] preThreeSize = AbstractPlayerPatch.PreThreeSizeField.preThreeSize.get(AbstractDungeon.player).clone();
        int[] threeSize = AbstractPlayerPatch.ThreeSizeField.threeSize.get(AbstractDungeon.player).clone();
        float[] threeSizeRate = AbstractPlayerPatch.ThreeSizeRateField.threeSizeRate.get(AbstractDungeon.player);
        preThreeSize[2] = threeSize[2];
        threeSize[2] += vi+(int)((threeSizeRate[2]*vi)+0.5F);
        AbstractPlayerPatch.PreThreeSizeField.preThreeSize.set(AbstractDungeon.player,preThreeSize);
        AbstractPlayerPatch.ThreeSizeField.threeSize.set(AbstractDungeon.player,threeSize);
    }

    public static void changeFixedVo(int vo){
        int[] preThreeSize = AbstractPlayerPatch.PreThreeSizeField.preThreeSize.get(AbstractDungeon.player).clone();
        int[] threeSize = AbstractPlayerPatch.ThreeSizeField.threeSize.get(AbstractDungeon.player).clone();
        preThreeSize[0] = threeSize[0];
        threeSize[0] += vo;
        AbstractPlayerPatch.PreThreeSizeField.preThreeSize.set(AbstractDungeon.player,preThreeSize);
        AbstractPlayerPatch.ThreeSizeField.threeSize.set(AbstractDungeon.player,threeSize);
    }

    public static void changeFixedDa(int da){
        int[] preThreeSize = AbstractPlayerPatch.PreThreeSizeField.preThreeSize.get(AbstractDungeon.player).clone();
        int[] threeSize = AbstractPlayerPatch.ThreeSizeField.threeSize.get(AbstractDungeon.player).clone();
        preThreeSize[1] = threeSize[1];
        threeSize[1] += da;
        AbstractPlayerPatch.PreThreeSizeField.preThreeSize.set(AbstractDungeon.player,preThreeSize);
        AbstractPlayerPatch.ThreeSizeField.threeSize.set(AbstractDungeon.player,threeSize);
    }

    public static void changeFixedVi(int vi){
        int[] preThreeSize = AbstractPlayerPatch.PreThreeSizeField.preThreeSize.get(AbstractDungeon.player).clone();
        int[] threeSize = AbstractPlayerPatch.ThreeSizeField.threeSize.get(AbstractDungeon.player).clone();
        preThreeSize[2] = threeSize[2];
        threeSize[2] += vi;
        AbstractPlayerPatch.PreThreeSizeField.preThreeSize.set(AbstractDungeon.player,preThreeSize);
        AbstractPlayerPatch.ThreeSizeField.threeSize.set(AbstractDungeon.player,threeSize);
    }

    public static void changeVoRate(float voRate){
        float[] threeSizeRate = AbstractPlayerPatch.ThreeSizeRateField.threeSizeRate.get(AbstractDungeon.player);
        threeSizeRate[0] += voRate;
        AbstractPlayerPatch.ThreeSizeRateField.threeSizeRate.set(AbstractDungeon.player,threeSizeRate);
    }

    public static void changeDaRate(float daRate){
        float[] threeSizeRate = AbstractPlayerPatch.ThreeSizeRateField.threeSizeRate.get(AbstractDungeon.player);
        threeSizeRate[1] += daRate;
        AbstractPlayerPatch.ThreeSizeRateField.threeSizeRate.set(AbstractDungeon.player,threeSizeRate);
    }

    public static void changeViRate(float viRate){
        float[] threeSizeRate = AbstractPlayerPatch.ThreeSizeRateField.threeSizeRate.get(AbstractDungeon.player);
        threeSizeRate[2] += viRate;
        AbstractPlayerPatch.ThreeSizeRateField.threeSizeRate.set(AbstractDungeon.player,threeSizeRate);
    }

    public static float[] getThreeSizeScoreRate(){

        int[] count={0,0,0};
        ArrayList<Integer> masterCardTags = new ArrayList<>();
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            int tag = AbstractCardPatch.ThreeSizeTagField.threeSizeTag.get(card);
            if(tag!=-1) {
                count[tag]++;
            }
        }
        int sum=count[0]+count[1]+count[2];
        return new float[]{1.0f*count[0]/sum,1.0f*count[1]/sum,1.0f*count[2]/sum};
    }



}
