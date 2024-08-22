package gkmasmod.characters;

import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.relics.Vajra;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import gkmasmod.cards.free.BaseAppeal;
import gkmasmod.cards.logic.BaseAwareness;
import gkmasmod.cards.logic.BaseVision;
import gkmasmod.cards.logic.ChangeMood;
import gkmasmod.cards.logic.KawaiiGesture;
import gkmasmod.cards.sense.BaseBehave;
import gkmasmod.cards.sense.Challenge;
import gkmasmod.cards.sense.TryError;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.ui.SkinSelectScreen;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.IdolStartingDeck;
import gkmasmod.utils.NameHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


// 继承CustomPlayer类
public class IdolCharacter extends CustomPlayer {
    // 火堆的人物立绘（行动前）
    private static final String MY_CHARACTER_SHOULDER_1 = "img/char/anon_1700.png";
    // 火堆的人物立绘（行动后）
    private static final String MY_CHARACTER_SHOULDER_2 = "img/char/anon_1700.png";
    // 人物死亡图像
    private static final String CORPSE_IMAGE = "img/char/anon_fall.png";
    // 战斗界面左下角能量图标的每个图层
    private static final String[] ORB_TEXTURES = new String[] {
            "img/UI/urgreen_129.png",
            "img/UI/layer_5_127.png",
            "img/UI/layer_3.png",
            "img/UI/layer2.png",
            "img/UI/threeButterfly_127.png",
            "img/UI/fivestar_129.png",
            "img/UI/urgreen_129.png",
            "img/UI/layer_5_d.png",
            "img/UI/layer_3_d.png",
            "img/UI/layer2d.png",
            "img/UI/threeButterfly_127.png" };

    private static final String ORB_VFX = "img/UI/vfx.png";

    private static boolean firstLoad = true;

    // 每个图层的旋转速度
    private static final float[] LAYER_SPEED = new float[]{-40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F, -5.0F, 0.0F};

    //初始生命，最大生命，初始金币,初始的充能球栏位（机器人）,最后一个应该是进阶14时的最大生命值下降
    private static final int STARTING_HP = 80;
    private static final int MAX_HP = 80;
    private static final int STARTING_GOLD = 99;
    private static final int HAND_SIZE = 0;
    private static final int ASCENSION_MAX_HP_LOSS = 5;

    public static String SELES_STAND = null;
    public static String filepath = "img/idol/shro/stand/stand_skin1.scml";

    public static String idolName= IdolData.shro;

    public IdolCharacter(String name) {
        super(name, PlayerColorEnum.gkmasMod_character,new CustomEnergyOrb(ORB_TEXTURES, ORB_VFX, LAYER_SPEED), new SpriterAnimation(filepath));


        // 人物对话气泡的大小，如果游戏中尺寸不对在这里修改（libgdx的坐标轴左下为原点）
        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 150.0F * Settings.scale);


        // 初始化你的人物，如果你的人物只有一张图，那么第一个参数填写你人物图片的路径。
        this.initializeClass(
                null,
                MY_CHARACTER_SHOULDER_2, MY_CHARACTER_SHOULDER_1,
                CORPSE_IMAGE, // 人物死亡图像
                this.getLoadout(),
                0.0F, 0.0F,
                200.0F, 220.0F, // 人物碰撞箱大小，越大的人物模型这个越大
                new EnergyManager(3) // 初始每回合的能量
        );
        refreshSkin();

    }

    public void refreshSkin() {
        this.idolName = getIdolName();

        String idolName_display = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolName",this.idolName)).TEXT[0];
        String skinName = IdolData.getIdol(this.idolName).getSkin(SkinSelectScreen.Inst.skinIndex);
        //String path = String.format("img/idol/stand/%s_%s.scml",this.idolName,skinName);
        skinName = "skin10";
        String path = String.format("img/idol/%s/stand/stand_%s.scml",this.idolName,skinName);
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
        addColorToCardPool(PlayerColorEnum.gkmasModColorSense, tmpPool);
        addColorToCardPool(PlayerColorEnum.gkmasModColorLogic, tmpPool);
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
        String s= "来自初星学园的偶像团体。每位偶像有各自的初始卡组、专属遗物和成长倾向。";
        return retVal;
    }

    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(
                "", // 人物名字
                "", // 人物介绍
                75, // 当前血量
                75, // 最大血量
                0, // 初始充能球栏位
                99, // 初始携带金币
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
        return new BaseAppeal();
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
        CardCrawlGame.sound.play(String.format("%s_click", this.idolName));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, true);
    }

    // 碎心图片
    @Override
    public ArrayList<CutscenePanel> getCutscenePanels() {
        ArrayList<CutscenePanel> panels = new ArrayList<>();
        // 有两个参数的，第二个参数表示出现图片时播放的音效
        panels.add(new CutscenePanel("ExampleModResources/img/char/Victory1.png", "ATTACK_MAGIC_FAST_1"));
        panels.add(new CutscenePanel("ExampleModResources/img/char/Victory2.png"));
        panels.add(new CutscenePanel("ExampleModResources/img/char/Victory3.png"));
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
        return"篠泽 广";
    }

    // 打心脏的颜色，不是很明显
    @Override
    public Color getSlashAttackColor() {
        return GkmasMod.gkmasMod_color;
    }

    // 吸血鬼事件文本，主要是他（索引为0）和她（索引为1）的区别（机器人另外）
    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[0];
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

    public static String getIdolName() {
        return IdolData.idolNames[SkinSelectScreen.Inst.idolIndex];
    }
}
