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
        count.add(0);
        count.add(0);
        count.add(0);
    }

    public int increaseCount(int index){
        count.set(index, count.get(index) + 1);
        return count.get(index);
    }

    public ArrayList<Integer> getCount(){
        return count;
    }



    @Override
    public AbstractCardModifier makeCopy() {
        return new CustomTimeCount(this.amount);
    }

}
