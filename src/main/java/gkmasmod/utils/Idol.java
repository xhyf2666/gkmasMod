package gkmasmod.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import gkmasmod.utils.CommonEnum.IdolType;
import gkmasmod.utils.CommonEnum.IdolStyle;

public class Idol {
    public String idolName;
    public String[] skins;
    public String[] skinImg;
    private HashMap<String,Integer> skinToNum = new HashMap<>();
    public String[] cards;
    public String[] relics;
    public IdolType[] types;
    public IdolStyle[] styles;
    public ArrayList<String[]> comments;
    public String[] texts;
    public String[] songs;
    public String[] bgms;
    public String[] videos;
    public int hp;
    public int gold;
    public int[] baseThreeSize;
    public float[] threeSizeRate;
    public int[] threeSizeRequires;
    public int[] anotherThreeSizeRequires;
    private int firstThreeType;
    private int secondThreeType;
    public int[] planTypes;
    public int[] planRequires;
    public int[] spVoiceNum;
    public String[] lives;
    public String[] bossSongs;
    public String[] bossReward;
    public String[] bossSkins;
    public String[] replaceCards;
    public String[] fireImgs;

    /**
     *
     * @param idolName 偶像名称
     * @param skins 皮肤名称[]
     * @param skinImg 皮肤图片[]
     * @param cards 初始卡[]
     * @param relics 初始遗物[]
     * @param types 职业类型[]
     * @param styles 职业风格[]
     * @param comments 手账图片
     * @param texts 手账文本
     * @param songs 歌曲[]
     * @param bgms BGM[]
     * @param videos 预览视频[]
     * @param hp 初始HP
     * @param gold 初始金币
     * @param baseThreeSize 初始三围
     * @param threeSizeRate 三围成长率
     * @param threeSizeRequires 三围考核要求
     * @param planTypes 每次考核的类型
     * @param planRequires 考核要求
     * @param spVoiceNum SP语音数量
     * @param lives live[]
     * @param bossSongs 偶像之路Boss战歌曲
     * @param bossReward 偶像之路温泉事件卡牌奖励
     * @param bossSkins 偶像之路Boss战皮肤
     * @param replaceCards FES初始卡牌替换[]
     * @param fireImgs 火堆皮肤
     */
    public Idol(String idolName,String[] skins,String[] skinImg,String[] cards,String[] relics,IdolType[] types,IdolStyle[] styles,ArrayList<String[]> comments,String[] texts,String[] songs,String[] bgms,String[] videos,int hp,int gold,int[] baseThreeSize,float[] threeSizeRate,int[] threeSizeRequires,int[] planTypes,int[] planRequires,int[] spVoiceNum,String[] lives,String[] bossSongs,String[] bossReward,String[] bossSkins,String[] replaceCards,String[] fireImgs){
        this.idolName = idolName;
        this.skins = skins.clone();
        this.skinImg = skinImg.clone();
        for (int i = 0; i < skins.length; i++) {
            skinToNum.put(skins[i], i);
        }
        this.cards = cards.clone();
        this.relics = relics.clone();
        this.types = types.clone();
        this.styles = styles.clone();
        this.comments = (ArrayList<String[]>) comments.clone();
        this.texts = texts.clone();
        this.songs = songs.clone();
        this.bgms = bgms.clone();
        this.videos = videos.clone();
        this.hp = hp;
        this.gold = gold;
        this.baseThreeSize = baseThreeSize.clone();
        this.threeSizeRate = threeSizeRate.clone();
        this.threeSizeRequires = threeSizeRequires.clone();
        //将threeSizeRequires中的每项增加200
        for(int i = 0; i < threeSizeRequires.length; i++){
            threeSizeRequires[i] += 200;
        }
        this.planTypes = planTypes.clone();
        this.planRequires = planRequires.clone();
        this.spVoiceNum = spVoiceNum.clone();
        this.lives = lives.clone();
        this.bossSongs = bossSongs.clone();
        this.bossReward = bossReward.clone();
        this.bossSkins = bossSkins.clone();
        this.replaceCards = replaceCards.clone();
        this.fireImgs = fireImgs.clone();

        //得到threeSizeRequires中最大值和次大值的索引
        int maxIndex = 0;
        int secondIndex = -1;  // 初始化为 -1，表示还没有找到次大值

        for (int i = 1; i < threeSizeRequires.length; i++) {
            if (threeSizeRequires[i] > threeSizeRequires[maxIndex]) {
                secondIndex = maxIndex;
                maxIndex = i;
            } else if (secondIndex == -1 || threeSizeRequires[i] > threeSizeRequires[secondIndex]) {
                // 更新次大值索引，只有在找到的值大于当前次大值时才更新
                secondIndex = i;
            }
        }

        firstThreeType = maxIndex;
        secondThreeType = secondIndex;

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

    public String getSkinImg(int num) {
        return skinImg[num];
    }

    public String getCard(String skin){
        return cards[skinToNum.get(skin)];
    }

    public String getCard(int num){
        return cards[num];
    }

    public ArrayList<String> getCardList(){
        ArrayList<String> cardList = new ArrayList<>();
        for(String card : this.cards){
            if(cardList.contains(card)){
                continue;
            }
            cardList.add(card);
        }
        return cardList;
    }

    public ArrayList<String> getCardListExcept(String card){
        ArrayList<String> cardList = new ArrayList<>();
        for(String c : this.cards){
            if(cardList.contains(c) || c.equals(card)){
                continue;
            }
            cardList.add(c);
        }
        return cardList;
    }

    public String getRelic(String skin){
        return relics[skinToNum.get(skin)];
    }

    public String getRelic(int num){
        return relics[num];
    }

    public ArrayList<String> getRelicList(){
        ArrayList<String> relicList = new ArrayList<>();
        for(String relic : this.relics){
            if(relicList.contains(relic)){
                continue;
            }
            relicList.add(relic);
        }
        return relicList;
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

    public String getSong(int num){
        return songs[num];
    }

    public String getBgm(int num){
        return bgms[num];
    }

    public String getVideo(int num){
        return videos[num];
    }

    public String getLive(int num){
        return lives[num];
    }

    public String getBossSong(int num){
        return bossSongs[num];
    }

    public String[] getBossRewards(){
        return bossReward;
    }

    public String getBossReward(int num){
        return bossReward[num];
    }

    public String getBossSkin(int num){
        return bossSkins[num];
    }

    public String getReplaceCard(int num){
        return replaceCards[num];
    }

    public String getRandomFireImg(){
        return fireImgs[(int)(Math.random()*fireImgs.length)];
    }

    public int getHp(){
        return hp;
    }

    public int getGold(){
        return gold;
    }

    public int[] getBaseThreeSize(){
        return baseThreeSize;
    }

    public float[] getThreeSizeRate(){
        return threeSizeRate;
    }

    public int[] getThreeSizeRequires(){
        return threeSizeRequires;
    }

    public int getThreeSizeRequire(int index){
        return threeSizeRequires[index];
    }

    public int getFirstThreeType(){
        return firstThreeType;
    }

    public int getSecondThreeType(){
        return secondThreeType;
    }

    public int getThirdThreeType(){
        for(int i = 0; i < 3; i++){
            if(i != firstThreeType && i != secondThreeType)
                return i;
        }
        return 0;
    }

    public int getBaseDamageRate(int index){
        if(index == firstThreeType)
            return 16;
        else if(index == secondThreeType)
            return 12;
        else
            return 9;
    }

    public int getSpVoiceNum(int index){
        return spVoiceNum[index];
    }

    public int[] getPlanTypes(){
        return planTypes;
    }

    public int[] getPlanRequires(){
        return planRequires;
    }

    public void setAnotherThreeSizeRequires(int[] anotherThreeSizeRequires){
        this.anotherThreeSizeRequires = anotherThreeSizeRequires.clone();
    }

    public int[] getAnotherThreeSizeRequires() {
        return anotherThreeSizeRequires;
    }

    public int getAnotherThreeSizeRequire(int index) {
        return anotherThreeSizeRequires[index];
    }
}
