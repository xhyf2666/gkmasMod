package gkmasmod.utils;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;
import gkmasmod.downfall.charbosses.stances.AbstractEnemyStance;
import gkmasmod.downfall.charbosses.stances.ENPreservationStance;
import gkmasmod.stances.ConcentrationStance;
import gkmasmod.stances.PreservationStance;

public class StanceHelper {

    public static boolean isInStance(AbstractStance stance, String stanceID, int stage){
        if(stanceID.equals(PreservationStance.STANCE_ID)){
            if(stance instanceof PreservationStance){
                PreservationStance ps = (PreservationStance) stance;
                if(ps.stage == stage){
                    return true;
                }
            }
            return false;
        }
        else if(stanceID.equals(ConcentrationStance.STANCE_ID)){
            if(stance instanceof ConcentrationStance){
                ConcentrationStance cs = (ConcentrationStance) stance;
                if(cs.stage == stage){
                    return true;
                }
            }
            return false;
        }
        else{
            return stance.ID.equals(stanceID);
        }
    }

    public static boolean isInStance(AbstractStance stance,String stanceID){
        return isInStance(stance,stanceID,0);
    }

    public static boolean enemyIsInStance(AbstractEnemyStance stance, String stanceID, int stage){
        if(stanceID.equals(ENPreservationStance.STANCE_ID)){
            if(stance instanceof ENPreservationStance){
                ENPreservationStance ps = (ENPreservationStance) stance;
                if(ps.stage == stage){
                    return true;
                }
            }
            return false;
        }
        else if(stanceID.equals(ENPreservationStance.STANCE_ID2)){
            if(stance instanceof ENPreservationStance){
                ENPreservationStance ps = (ENPreservationStance) stance;
                if(ps.stage == stage){
                    return true;
                }
            }
            return false;
        }
        else{
            return stance.ID.equals(stanceID);
        }
    }

    public static boolean enemyIsInStance(AbstractEnemyStance stance,String stanceID){
        return enemyIsInStance(stance,stanceID,0);
    }
}
