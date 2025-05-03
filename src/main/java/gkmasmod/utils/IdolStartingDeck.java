package gkmasmod.utils;

import gkmasmod.cards.anomaly.*;
import gkmasmod.cards.free.BaseAppeal;
import gkmasmod.cards.free.BasePerform;
import gkmasmod.cards.free.BasePose;
import gkmasmod.cards.logic.BaseAwareness;
import gkmasmod.cards.logic.BaseVision;
import gkmasmod.cards.logic.ChangeMood;
import gkmasmod.cards.logic.KawaiiGesture;
import gkmasmod.cards.sense.BaseBehave;
import gkmasmod.cards.sense.BaseExpression;
import gkmasmod.cards.sense.Challenge;
import gkmasmod.cards.sense.TryError;
import gkmasmod.utils.CommonEnum.IdolStyle;
import gkmasmod.utils.CommonEnum.IdolType;

import java.util.ArrayList;
import java.util.Arrays;

public class IdolStartingDeck {

    public static final String[] senseGoodTuneStartingDeck = {
            BaseAppeal.ID,
            BaseAppeal.ID,
            BaseAppeal.ID,
            BasePerform.ID,
            BasePerform.ID,
            BasePerform.ID,
            BasePose.ID,
            BasePose.ID,
            BaseBehave.ID,
            BaseBehave.ID,
            Challenge.ID
    };

    public static final String[] senseFocusStartingDeck = {
            BaseAppeal.ID,
            BaseAppeal.ID,
            BaseAppeal.ID,
            BasePerform.ID,
            BasePerform.ID,
            BasePerform.ID,
            BasePose.ID,
            BasePose.ID,
            BaseExpression.ID,
            BaseExpression.ID,
            TryError.ID
    };

    public static final String[] logicGoodImpressionStartingDeck = {
            BaseAppeal.ID,
            BaseAppeal.ID,
            BaseAppeal.ID,
            BasePerform.ID,
            BasePerform.ID,
            BasePerform.ID,
            BasePose.ID,
            BasePose.ID,
            BaseVision.ID,
            BaseVision.ID,
            KawaiiGesture.ID
    };

    public static final String[] logicYarukiStartingDeck = {
            BaseAppeal.ID,
            BaseAppeal.ID,
            BaseAppeal.ID,
            BasePerform.ID,
            BasePerform.ID,
            BasePerform.ID,
            BasePose.ID,
            BasePose.ID,
            BaseAwareness.ID,
            BaseAwareness.ID,
            ChangeMood.ID
    };

    public static final String[] anomalyFullPowerStartingDeck = {
            BaseAppeal.ID,
            BaseAppeal.ID,
            BaseAppeal.ID,
            BasePerform.ID,
            BasePerform.ID,
            BasePerform.ID,
            BaseImage.ID,
            BaseImage.ID,
            BaseMental.ID,
            BaseMental.ID,
            FinalSpurt.ID
    };

    public static final String[] anomalyConcentrationStartingDeck = {
            BaseAppeal.ID,
            BaseAppeal.ID,
            BaseAppeal.ID,
            BasePerform.ID,
            BasePerform.ID,
            BasePerform.ID,
            BaseImage.ID,
            BaseImage.ID,
            BaseAppeal.ID,
            BaseGreeting.ID,
            Intensely.ID
    };

    public static String[] getSenseGoodTuneStartingDeck() {
        return senseGoodTuneStartingDeck.clone();
    }

    public static String[] getSenseFocusStartingDeck() {
        return senseFocusStartingDeck.clone();
    }

    public static String[] getLogicGoodImpressionStartingDeck() {
        return logicGoodImpressionStartingDeck.clone();
    }

    public static String[] getLogicYarukiStartingDeck() {
        return logicYarukiStartingDeck.clone();
    }

    public static String getSpecialCard(String idolName, String skinName){
        return IdolData.getIdol(idolName).getCard(skinName);
    }

    public static String getSpecialCard(int idolIndex, int skinIndex){
        return IdolData.getIdol(idolIndex).getCard(skinIndex);
    }

    public static String getSpecialRelic(String idolName, String skinName){
        return IdolData.getIdol(idolName).getRelic(skinName);
    }

    public static String getSpecialRelic(int idolIndex, int skinIndex){
        return IdolData.getIdol(idolIndex).getRelic(skinIndex);
    }

    public static String getOtherSpecialRelic(int idolIndex, int skinIndex){
        return IdolData.getIdol(idolIndex).getRelic(skinIndex);
    }

    public static ArrayList<String> getStartingDeck(String idolName, String skinName){
        IdolType type = IdolData.getIdol(idolName).getType(skinName);
        IdolStyle style = IdolData.getIdol(idolName).getStyle(skinName);
        String specialCard = getSpecialCard(idolName, skinName);

        // 创建一个变长数组
        ArrayList<String> startingDeck = new ArrayList<>();

        switch (style){
            case GOOD_TUNE:
                startingDeck = new ArrayList<>(Arrays.asList(senseGoodTuneStartingDeck));
                break;
            case FOCUS:
                startingDeck = new ArrayList<>(Arrays.asList(senseFocusStartingDeck));
                break;
            case GOOD_IMPRESSION:
                startingDeck = new ArrayList<>(Arrays.asList(logicGoodImpressionStartingDeck));
                break;
            case YARUKI:
                startingDeck = new ArrayList<>(Arrays.asList(logicYarukiStartingDeck));
                break;
            case FULL_POWER:
                startingDeck = new ArrayList<>(Arrays.asList(anomalyFullPowerStartingDeck));
                break;
            case CONCENTRATION:
                startingDeck = new ArrayList<>(Arrays.asList(anomalyConcentrationStartingDeck));
                break;
            default:
                break;
        }
        startingDeck.add(specialCard);

        return startingDeck;

    }

    public static ArrayList<String> getStartingDeck(int idolIndex, int skinIndex){
        IdolType type = IdolData.getIdol(idolIndex).getType(skinIndex);
        IdolStyle style = IdolData.getIdol(idolIndex).getStyle(skinIndex);
        String specialCard = getSpecialCard(idolIndex, skinIndex);
        String replaceCard = IdolData.getIdol(idolIndex).getReplaceCard(skinIndex);

        // 创建一个变长数组
        ArrayList<String> startingDeck = new ArrayList<>();

        switch (style){
            case GOOD_TUNE:
                startingDeck = new ArrayList<>(Arrays.asList(senseGoodTuneStartingDeck));
                break;
            case FOCUS:
                startingDeck = new ArrayList<>(Arrays.asList(senseFocusStartingDeck));
                break;
            case GOOD_IMPRESSION:
                startingDeck = new ArrayList<>(Arrays.asList(logicGoodImpressionStartingDeck));
                break;
            case YARUKI:
                startingDeck = new ArrayList<>(Arrays.asList(logicYarukiStartingDeck));
                break;
            case FULL_POWER:
                startingDeck = new ArrayList<>(Arrays.asList(anomalyFullPowerStartingDeck));
                break;
            case CONCENTRATION:
                startingDeck = new ArrayList<>(Arrays.asList(anomalyConcentrationStartingDeck));
                break;
            default:
                break;
        }
        startingDeck.add(specialCard);
        if(!replaceCard.equals("")){
            switch (style){
                case YARUKI:
                case GOOD_IMPRESSION:
                    startingDeck.set(0, replaceCard);
                    break;
                default:
                    startingDeck.set(3,replaceCard);
            }
        }

        return startingDeck;

    }

}
