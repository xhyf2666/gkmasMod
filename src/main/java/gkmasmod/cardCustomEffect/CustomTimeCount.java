package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public class CustomTimeCount extends AbstractCardCustomEffect {

    public static String growID = "CustomTimeCount";
    public ArrayList<Integer> count = new ArrayList<>();

    public CustomTimeCount(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    public int increaseCount(int index){
        this.amount+=1*Math.pow(10,index);
        return this.amount;
    }

    public ArrayList<Integer> getCount(){
        ArrayList<Integer> res = new ArrayList<>();
        int temp = this.amount;
        for (int i = 0; i < 3; i++) {
            res.add(temp%10);
            temp/=10;
        }
        return res;
    }

    public int getTotalCount(){
        int res=0;
        int temp = this.amount;
        for (int i = 0; i < 3; i++) {
            res+=temp%10;
            temp/=10;
        }
        return res;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new CustomTimeCount(this.amount);
    }

}
