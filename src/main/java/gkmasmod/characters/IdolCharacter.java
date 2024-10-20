package gkmasmod.characters;

import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
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
import gkmasmod.modcore.GkmasMod;
import gkmasmod.patches.AbstractCardPatch;
import gkmasmod.relics.CrackedCoreNew;
import gkmasmod.relics.PocketBook;
import gkmasmod.relics.ProducerPhone;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


// 继承CustomPlayer类
public class IdolCharacter extends CustomPlayer {
    // 人物死亡图像
    private static final String CORPSE_IMAGE = "gkmasModResource/img/idol/shro/sleep_skin10.png";
    // 战斗界面左下角能量图标的每个图层
    private static final String[] ORB_TEXTURES = new String[] {
            "gkmasModResource/img/UI/energy_bg.png",
            "gkmasModResource/img/UI/layer.png",
            "gkmasModResource/img/UI/star.png",
            "gkmasModResource/img/UI/energy_bg.png",
            "gkmasModResource/img/UI/energy_bg.png",
            "gkmasModResource/img/UI/layer_d.png",
            "gkmasModResource/img/UI/star_d.png" };

    private static final String ORB_VFX = "gkmasModResource/img/UI/vfx.png";

    // 每个图层的旋转速度
    private static final float[] LAYER_SPEED = new float[]{-40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F, -5.0F, 0.0F};

    private static final int STARTING_HP = 80;
    private static final int MAX_HP = 80;
    private static final int STARTING_GOLD = 99;
    private static final int HAND_SIZE = 0;
    private static final int ASCENSION_MAX_HP_LOSS = 5;

    public static String SELES_STAND = null;
    public static String filepath = "gkmasModResource/img/idol/shro/stand/stand_skin10.scml";

    public static String idolName= IdolData.shro;

    public int[] threeSize = new int[]{0,0,0};
    public int[] preThreeSize = new int[]{0,0,0};
    public float[] threeSizeRate = new float[]{0.0f,0.0f,0.0f};

    private Texture finalCircleBg;
    private Texture[] arcs= new Texture[3];

    public Idol idolData;

    public int skinIndex = 0;

    public ArrayList<Integer> finalCircleRound = new ArrayList<>();

    public boolean IsRenderFinalCircle = false;

    public double finalDamageRate=1.0F;

    public IdolCharacter(String name) {
        super(name, PlayerColorEnum.gkmasMod_character,new CustomEnergyOrb(ORB_TEXTURES, ORB_VFX, LAYER_SPEED), new SpriterAnimation(filepath));


        // 人物对话气泡的大小，如果游戏中尺寸不对在这里修改（libgdx的坐标轴左下为原点）
        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 150.0F * Settings.scale);
        initializeData();

        this.finalCircleBg = new Texture("gkmasModResource/img/UI/ThreeSize/finalCircle/bg.png");
        arcs[0] = new Texture("gkmasModResource/img/UI/ThreeSize/finalCircle/arc_vo.png");
        arcs[1] = new Texture("gkmasModResource/img/UI/ThreeSize/finalCircle/arc_da.png");
        arcs[2] = new Texture("gkmasModResource/img/UI/ThreeSize/finalCircle/arc_vi.png");
        refreshSkin();
    }

    public void initializeData(){
        // 初始化你的人物，如果你的人物只有一张图，那么第一个参数填写你人物图片的路径。
        this.initializeClass(
                null,
                String.format("gkmasModResource/img/idol/%s/stand/fire.png",this.idolName),
                String.format("gkmasModResource/img/idol/%s/stand/fire.png",this.idolName),
                String.format("gkmasModResource/img/idol/%s/stand/sleep_skin10.png",this.idolName),
                this.getLoadout(),
                0.0F, 0.0F,
                200.0F, 220.0F, // 人物碰撞箱大小，越大的人物模型这个越大
                new EnergyManager(3) // 初始每回合的能量
        );
    }

    public void renderHealth(SpriteBatch sb) {
        super.renderHealth(sb);
        if (this.IsRenderFinalCircle) {
            renderFinalCircle(sb);
        }
    }

    public void generateCircle(int roundNum){
        if(roundNum < 1)
            return;
        this.finalCircleRound = new ArrayList<>();
        if(roundNum > 0)
            this.finalCircleRound.add(this.idolData.getFirstThreeType());
        if(roundNum > 1)
            this.finalCircleRound.add(this.idolData.getSecondThreeType());
        if(roundNum > 2)
            this.finalCircleRound.add(this.idolData.getThirdThreeType());
        Random spRng = new Random(Settings.seed, AbstractDungeon.floorNum*20);
        for(int i = 0; i < roundNum - 3; i++){
            this.finalCircleRound.add(spRng.random(0,2));
        }
        this.IsRenderFinalCircle = true;
        int currentThreeType = this.finalCircleRound.get(this.finalCircleRound.size()-1);
        int baseDamageRate = this.idolData.getBaseDamageRate(currentThreeType);

        this.finalDamageRate = calculateDamageRate(baseDamageRate, this.threeSize[currentThreeType],this.idolData.getThreeSizeRequire(currentThreeType));

        System.out.println(this.finalCircleRound);
    }

    public void generateHajimeCircle(int roundNum){
        if(roundNum < 1)
            return;
        this.finalCircleRound = new ArrayList<>();
        if(roundNum > 0)
            this.finalCircleRound.add(this.idolData.getFirstThreeType());
        Random spRng = new Random(Settings.seed, AbstractDungeon.floorNum*20);
        for(int i = 0; i < roundNum - 2; i++){
            this.finalCircleRound.add(spRng.random(0,2));
        }
        this.IsRenderFinalCircle = true;
        int currentThreeType = this.finalCircleRound.get(this.finalCircleRound.size()-1);
        int baseDamageRate = this.idolData.getBaseDamageRate(currentThreeType);

        this.finalDamageRate = calculateDamageRate(baseDamageRate, this.threeSize[currentThreeType],this.idolData.getThreeSizeRequire(currentThreeType));

        System.out.println(this.finalCircleRound);
    }

    public void renderFinalCircle(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(this.finalCircleBg, Settings.WIDTH / 2.0F - 500.0F*Settings.xScale, Settings.HEIGHT / 2.0F + 000.0F*Settings.scale, 256.0F, 256.0F, 512, 512, Settings.scale*0.5F, Settings.scale*0.5F, 0.0F, 0, 0, 512, 512, false, false);
        for(int i = 0; i<this.finalCircleRound.size();i++){
            sb.draw(arcs[this.finalCircleRound.get(i)], Settings.WIDTH / 2.0F - 500.0F*Settings.xScale, Settings.HEIGHT / 2.0F + 000.0F*Settings.scale, 256.0F, 256.0F, 512, 512, Settings.scale*0.5F, Settings.scale*0.5F, 330.0F-30.F*i, 0, 0, 512, 512, false, false);
        }
        FontHelper.renderSmartText(sb, FontHelper.bannerNameFont, String.valueOf(this.finalCircleRound.size()), Settings.WIDTH / 2.0F - 275.0F*Settings.xScale, Settings.HEIGHT / 2.0F + 290.0F*Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR,1.5f);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, String.format("%.0f%%",this.finalDamageRate*100), Settings.WIDTH / 2.0F - 300.0F*Settings.xScale, Settings.HEIGHT / 2.0F + 325.0F*Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);

    }

    private double calculateDamageRate(int baseRate, int v,int t){
        double rate = 1.0f*v/t;
        if(rate > 1.0f)
            return 1.0f*baseRate*(1+Math.log(rate)/3)+1;
        return (Math.pow(rate,2)+Math.exp(rate-1)+(rate-1)/Math.E)/2*baseRate+1;
    }

    public double[] calculateDamageRates(){
        double[] rates = new double[3];
        for(int i = 0; i < 3; i++){
            rates[i] = calculateDamageRate(this.idolData.getBaseDamageRate(i),this.threeSize[i],this.idolData.getThreeSizeRequire(i));
        }
        return rates;
    }


    public void refreshSkin() {
        if(this.idolData == null)
            this.idolData = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex);
        this.idolName = this.idolData.idolName;
        String skinName = this.idolData.getSkinImg(SkinSelectScreen.Inst.skinIndex);
        String path = String.format("gkmasModResource/img/idol/%s/stand/stand_%s.scml",SkinSelectScreen.Inst.idolName,skinName);
        this.animation = new SpriterAnimation(path);
    }

    public ArrayList<AbstractCard> addColorToCardPool(AbstractCard.CardColor color, ArrayList<AbstractCard> tmpPool) {
        Iterator<Map.Entry<String, AbstractCard>> cardLib = CardLibrary.cards.entrySet().iterator();
        while (true) {
            if (!cardLib.hasNext())
                return tmpPool;
            Map.Entry c = cardLib.next();
            AbstractCard card = (AbstractCard)c.getValue();
            if (card.color.equals(color) &&
                    card.rarity != AbstractCard.CardRarity.BASIC && (
                    !UnlockTracker.isCardLocked((String)c.getKey()) || Settings.isDailyRun))
                tmpPool.add(card);
        }
    }

    public ArrayList<AbstractCard> getCardPool(ArrayList<AbstractCard> tmpPool) {
        addColorToCardPool(PlayerColorEnum.gkmasModColor, tmpPool);
        CommonEnum.IdolType type = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getType(SkinSelectScreen.Inst.skinIndex);
        if (type == CommonEnum.IdolType.LOGIC) {
            addColorToCardPool(PlayerColorEnum.gkmasModColorLogic, tmpPool);
        } else if (type == CommonEnum.IdolType.SENSE) {
            addColorToCardPool(PlayerColorEnum.gkmasModColorSense, tmpPool);
        } else{
            addColorToCardPool(PlayerColorEnum.gkmasModColorSense, tmpPool);
            addColorToCardPool(PlayerColorEnum.gkmasModColorLogic, tmpPool);
        }

        return tmpPool;
    }

    // 初始卡组的ID，可直接写或引用变量
    public ArrayList<String> getStartingDeck() {

        return IdolStartingDeck.getStartingDeck(SkinSelectScreen.Inst.idolIndex, SkinSelectScreen.Inst.skinIndex);
    }

    @Override
    // 初始遗物的ID，可以先写个原版遗物凑数
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(IdolStartingDeck.getSpecailRelic(SkinSelectScreen.Inst.idolIndex, SkinSelectScreen.Inst.skinIndex));
        retVal.add(PocketBook.ID);
        retVal.add(ProducerPhone.ID);
        if(SkinSelectScreen.Inst.updateIndex==1){
            return retVal;
        }
        if(SkinSelectScreen.Inst.idolName==IdolData.fktn){
            retVal.add(MawBank.ID);
        }
        else if(SkinSelectScreen.Inst.idolName==IdolData.hski){
            retVal.add(Girya.ID);
        }
        else if(SkinSelectScreen.Inst.idolName==IdolData.kcna){
            retVal.add(MembershipCard.ID);
        }
        else if(SkinSelectScreen.Inst.idolName==IdolData.ttmr){
            retVal.add(Pear.ID);
        }
        else if(SkinSelectScreen.Inst.idolName==IdolData.shro){
            retVal.add(MeatOnTheBone.ID);
        }
        else if(SkinSelectScreen.Inst.idolName==IdolData.hrnm){
            retVal.add(CrackedCoreNew.ID);
        }
        return retVal;
    }

    public int getHP(){
        return IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getHp();
    }

    public int getGold(){
        return IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getGold();
    }

    public int getMaxOrbs(){
        if(SkinSelectScreen.Inst.idolName.equals(IdolData.hrnm)){
            return 3;
        }
        return 0;
    }

    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(
                SkinSelectScreen.Inst.curName, // 人物名字
                "来自初星学园的偶像团体。每位偶像有各自的初始卡组、专属遗物和成长倾向。", // 人物介绍
                getHP(), // 当前血量
                getHP(), // 最大血量
                getMaxOrbs(), // 初始充能球栏位
                getGold(), // 初始携带金币
                5, // 每回合抽牌数量
                this, // 别动
                getStartingRelics(), // 初始遗物
                getStartingDeck(), // 初始卡组
                false // 别动
        );
    }

    // 人物名字（出现在游戏左上角）
    @Override
    public String getTitle(PlayerClass playerClass) {
        return "学园偶像大师";
    }

    // 你的卡牌颜色（这个枚举在最下方创建）
    @Override
    public AbstractCard.CardColor getCardColor() {
        return PlayerColorEnum.gkmasModColor;
    }

    // 翻牌事件出现的你的职业牌（一般设为打击）
    @Override
    public AbstractCard getStartCardForEvent() {
        return CardLibrary.getCard(this.chosenClass, this.idolData.getCard(this.skinIndex)).makeCopy();
    }

    // 卡牌轨迹颜色
    @Override
    public Color getCardTrailColor() {
        return GkmasMod.gkmasMod_color;
    }

    // 高进阶带来的生命值损失
    @Override
    public int getAscensionMaxHPLoss() {
        return 5;
    }

    // 卡牌的能量字体，没必要修改
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    // 人物选择界面点击你的人物按钮时触发的方法，这里为屏幕轻微震动
    public void doCharSelectScreenSelectEffect() {
        SoundHelper.playSound("gkmasModResource/audio/voice/shro_click.ogg");
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, true);
    }

    // 碎心图片
    @Override
    public ArrayList<CutscenePanel> getCutscenePanels() {
        ArrayList<CutscenePanel> panels = new ArrayList<>();
        // 有两个参数的，第二个参数表示出现图片时播放的音效
        panels.add(new CutscenePanel("gkmasModResource/img/UI/end/end1.png"));
        panels.add(new CutscenePanel(String.format("gkmasModResource/img/UI/end/end_%s_001_00.png", this.idolName)));
        panels.add(new CutscenePanel(String.format("gkmasModResource/img/UI/end/end_%s_001_01.png", this.idolName)));
        panels.add(new CutscenePanel("gkmasModResource/img/UI/end/end4.png"));
        return panels;
    }

    // 自定义模式选择你的人物时播放的音效
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return String.format("%s_click", this.idolName);
    }

    // 游戏中左上角显示在你的名字之后的人物名称
    @Override
    public String getLocalizedCharacterName() {
        return "学园偶像大师";
    }

    // 创建人物实例，照抄
    @Override
    public AbstractPlayer newInstance() {
        return new IdolCharacter(this.name);
    }

    // 第三章面对心脏说的话（例如战士是“你握紧了你的长刀……”之类的）
    @Override
    public String getSpireHeartText() {
        return"诶，这个大家伙是什么东西";
    }

    // 打心脏的颜色，不是很明显
    @Override
    public Color getSlashAttackColor() {
        return GkmasMod.gkmasMod_color;
    }

    // 吸血鬼事件文本，主要是他（索引为0）和她（索引为1）的区别（机器人另外）
    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[1];
    }

    // 卡牌选择界面选择该牌的颜色
    @Override
    public Color getCardRenderColor() {
        return GkmasMod.gkmasMod_color;
    }

    // 第三章面对心脏造成伤害时的特效
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL};
    }

    public String getIdolName() {
        return this.idolName;
    }

    public void setThreeSize(int[] threeSize){
        this.threeSize = threeSize.clone();
        this.preThreeSize = threeSize.clone();
    }

    public void setThreeSizeRate(float[] threeSizeRate){
        this.threeSizeRate = threeSizeRate.clone();
    }

    public void setThreeSizeAndRate(float[] data){
        this.threeSize = new int[]{(int)data[0], (int)data[1], (int)data[2]};
        this.threeSizeRate = new float[]{data[3], data[4], data[5]};
    }

    public int getVo(){
        return this.threeSize[0];
    }

    public int getDa(){
        return this.threeSize[1];
    }

    public int getVi(){
        return this.threeSize[2];
    }

    public int[] getThreeSize(){
        return this.threeSize.clone();
    }

    public float[] getThreeSizeRate(){
        return this.threeSizeRate.clone();
    }

    public int[] getPreThreeSize(){
        return this.preThreeSize.clone();
    }

    public float getVoRate(){
        return this.threeSizeRate[0];
    }

    public float getDaRate(){
        return this.threeSizeRate[1];
    }

    public float getViRate(){
        return this.threeSizeRate[2];
    }

    public void changeVo(int vo){
        this.preThreeSize[0] = this.threeSize[0];
        this.threeSize[0] += vo+(int)((this.threeSizeRate[0]*vo)+0.5F);
    }

    public void changeDa(int da){
        this.preThreeSize[1] = this.threeSize[1];
        this.threeSize[1] += da+(int)((this.threeSizeRate[1]*da)+0.5F);
    }

    public void changeVi(int vi){
        this.preThreeSize[2] = this.threeSize[2];
        this.threeSize[2] += vi+(int)((this.threeSizeRate[2]*vi)+0.5F);
    }

    public void changeFixedVo(int vo){
        this.preThreeSize[0] = this.threeSize[0];
        this.threeSize[0] += vo;
    }

    public void changeFixedDa(int da){
        this.preThreeSize[1] = this.threeSize[1];
        this.threeSize[1] += da;
    }

    public void changeFixedVi(int vi){
        this.preThreeSize[2] = this.threeSize[2];
        this.threeSize[2] += vi;
    }

    

    public void changeVoRate(float voRate){
        this.threeSizeRate[0] += voRate;
    }

    public void changeDaRate(float daRate){
        this.threeSizeRate[1] += daRate;
    }

    public void changeViRate(float viRate){
        this.threeSizeRate[2] += viRate;
    }

    public float[] getThreeSizeAndRate(){
        return new float[]{this.threeSize[0]*1.0f,this.threeSize[1]*1.0f,this.threeSize[2]*1.0f,this.threeSizeRate[0],this.threeSizeRate[1],this.threeSizeRate[2]};
    }

    public float[] getThreeSizeScoreRate(){

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

    @Override
    public void initializeStarterDeck() {
        super.initializeStarterDeck();
        int num = 0;
        if(GkmasMod.cardRate>0.4f)
            num = 1;
        if(GkmasMod.cardRate>0.7f)
            num = 2;

        ArrayList<AbstractCard> cards = new ArrayList<>();
        AbstractCard specailCard = null;
        this.idolData = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex);
        int first = idolData.getFirstThreeType();
        int second = idolData.getSecondThreeType();
        int third = idolData.getThirdThreeType();
        int index_attack =0;
        int index_defend =0;
        int index_basepose =0;
        int index_style =0;
        for (AbstractCard c : this.masterDeck.group){
            if(!c.cardID.equals(this.idolData.getCard(SkinSelectScreen.Inst.skinIndex))&&c.canUpgrade()){
                cards.add(c);
                if(c.cardID.equals(BaseAppeal.ID)){
                    AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,index_attack);
                    index_attack++;
                }
                else if(c.cardID.equals(BasePerform.ID)){
                    AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,index_defend);
                    index_defend++;
                }
                else if(c.cardID.equals(BasePose.ID)){
                    if(index_basepose==0)
                        AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,third);
                    else if(index_basepose==1)
                        AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,second);
                    else if(index_basepose==2)
                        AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,first);
                    index_basepose++;
                }
                else if(c.cardID.equals(BaseExpression.ID)||c.cardID.equals(BaseBehave.ID)||c.cardID.equals(BaseVision.ID)||c.cardID.equals(BaseAwareness.ID)){
                    if(index_style==0)
                        AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,first);
                    else if(index_style==1)
                        AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,second);
                    else if(index_style==2)
                        AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,third);
                    index_style++;
                }
                else if(c.cardID.equals(Challenge.ID)||c.cardID.equals(TryError.ID)||c.cardID.equals(KawaiiGesture.ID)||c.cardID.equals(ChangeMood.ID)){
                    AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,first);
                }
            }
            else{
                specailCard = c;
                AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,first);
            }
        }
        //从cards中选num张强化
        for(int i = 0; i < num; i++){
            if(cards.size() == 0)
                break;
            AbstractCard c = cards.get(new Random(Settings.seed).random(cards.size()-1));
            c.upgrade();
            cards.remove(c);
        }
        if(GkmasMod.beat_hmsz >0){
            specailCard.upgrade();
        }


    }
}
