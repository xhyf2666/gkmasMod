package gkmasmod.utils;

import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class PlayerHelper {
    public static int getPowerAmount(AbstractPlayer p,String powerID) {
        return p.getPower(powerID)==null?0:p.getPower(powerID).amount;
    }

}
