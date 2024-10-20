package gkmasmod.utils;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RankHelper {
    public static String getRank(int value) {
        if(value>=2500)
            return "SS+";
        else if(value>=2200)
            return "SS";
        else if(value>=1800)
            return "S+";
        else if(value>=1500)
            return "S";
        else if(value>=1200)
            return "A+";
        else if(value>=1000)
            return "A";
        else if(value>=800)
            return "B+";
        else if(value>=600)
            return "B";
        else if(value>=450)
            return "C+";
        else if(value>=300)
            return "C";
        else if(value>=200)
            return "D";
        else if(value>=100)
            return "E";
        else
            return "F";
    }

    public static int[] getRankBoundary(int value){
        if(value>=4000)
            return new int[]{4000, Integer.MAX_VALUE};
        if (value >= 2500)
            return new int[]{2500, 4000};
        else if (value >= 2200)
            return new int[]{2200, 2500};
        else if (value >= 1800)
            return new int[]{1800, 2200};
        else if (value >= 1500)
            return new int[]{1500, 1800};
        else if (value >= 1200)
            return new int[]{1200, 1500};
        else if (value >= 1000)
            return new int[]{1000, 1200};
        else if (value >= 800)
            return new int[]{800, 1000};
        else if (value >= 600)
            return new int[]{600, 800};
        else if (value >= 450)
            return new int[]{450, 600};
        else if (value >= 300)
            return new int[]{300, 450};
        else if (value >= 200)
            return new int[]{200, 300};
        else if (value >= 100)
            return new int[]{100, 200};
        else
            return new int[]{0, 100};
    }

    public static String getFinalRank(int value) {
        if(value>=20000)
            return "SS+";
        else if(value>=16000)
            return "SS";
        else if(value>=14500)
            return "S+";
        else if(value>=13000)
            return "S";
        else if(value>=11500)
            return "A+";
        else if(value>=10000)
            return "A";
        else if(value>=8000)
            return "B+";
        else if(value>=6500)
            return "B";
        else if(value>=5000)
            return "C+";
        else if(value>=4000)
            return "C";
        else if(value>=2500)
            return "D";
        else if(value>=1000)
            return "E";
        else
            return "F";
    }

    public static int getThreeSizeScore(int vo,int da,int vi){
        return (int)(2.3F*(vo+da+vi));
    }

    public static int getFinalScore(int vo,int da,int vi,int append){
        return getThreeSizeScore(vo,da,vi)+append;
    }

    public static int getStep(){
        int floor = AbstractDungeon.floorNum;
        if(floor >= 56)
            return 10;
        else if(floor >= 50)
            return 9;
        else if(floor >= 44)
            return 8;
        else if(floor >= 38)
            return 7;
        else if(floor >= 33)
            return 6;
        else if(floor >= 27)
            return 5;
        else if(floor >= 21)
            return 4;
        else if(floor >= 16)
            return 3;
        else if(floor >= 8)
            return 2;
        else
            return 1;
    }

}
