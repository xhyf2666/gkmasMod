package gkmasmod.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import gkmasmod.utils.CommonEnum.IdolType;
import gkmasmod.utils.CommonEnum.IdolStyle;

public class Idol {
    public String idolName;
    public String[] skins;
    private HashMap<String,Integer> skinToNum = new HashMap<>();
    public String[] cards;
    public String[] relics;
    public IdolType[] types;
    public IdolStyle[] styles;
    public ArrayList<String[]> comments;
    public String[] texts;

    public Idol(String idolName,String[] skins) {
        this.idolName = idolName;
        this.skins = skins.clone();
        for (int i = 0; i < skins.length; i++) {
            skinToNum.put(skins[i], i);
        }
    }

    public Idol(String idolName,String[] skins,String[] cards,String[] relics,IdolType[] types,IdolStyle[] styles,ArrayList<String[]> comments,String[] texts) {
        this.idolName = idolName;
        this.skins = skins.clone();
        for (int i = 0; i < skins.length; i++) {
            skinToNum.put(skins[i], i);
        }
        this.cards = cards.clone();
        this.relics = relics.clone();
        this.types = types.clone();
        this.styles = styles.clone();
        this.comments = (ArrayList<String[]>) comments.clone();
        this.texts = texts.clone();

        assert cards.length == skins.length && relics.length == skins.length && types.length == skins.length && styles.length == skins.length;
    }

    public int getSkinNum() {
        return skins.length;
    }

    public void setCards(String[] cards) {
        this.cards = cards.clone();
    }

    public void setRelics(String[] relics) {
        this.relics = relics.clone();
    }

    public String getSkin(int num) {
        return skins[num];
    }

    public String getCard(String skin){
        return cards[skinToNum.get(skin)];
    }

    public String getCard(int num){
        return cards[num];
    }

    public String getRelic(String skin){
        return relics[skinToNum.get(skin)];
    }

    public String getRelic(int num){
        return relics[num];
    }

    public IdolType getType(String skin){
        return types[skinToNum.get(skin)];
    }

    public IdolType getType(int num){
        return types[num];
    }

    public IdolStyle getStyle(String skin){
        return styles[skinToNum.get(skin)];
    }

    public IdolStyle getStyle(int num){
        return styles[num];
    }

    public void setComments(ArrayList<String > a){
        this.comments = (ArrayList<String[]>) a.clone();
    }

    public String[] getComments(int step){
        return comments.get(step-1);
    }

    public String getText(int step){
        return texts[step-1];
    }
}
