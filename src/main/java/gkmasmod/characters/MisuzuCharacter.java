package gkmasmod.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
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
import gkmasmod.cards.anomaly.*;
import gkmasmod.cards.free.BaseAppeal;
import gkmasmod.cards.free.BasePerform;
import gkmasmod.cards.free.BasePose;
import gkmasmod.cards.free.GachaAgain;
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
import gkmasmod.relics.*;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.stances.SleepyStance;
import gkmasmod.utils.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


// 继承CustomPlayer类
public class MisuzuCharacter extends CustomPlayer {

    private static final String CORPSE_IMAGE = "gkmasModResource/img/idol/hmsz/sleep_skin10.png";

    public static String SELES_STAND = null;
    public static String filepath = "gkmasModResource/img/idol/hmsz/stand/stand_skin10.scml";

    public static String idolName= IdolData.hmsz;

    public int skinIndex = 0;

    public MisuzuCharacter(String name) {
        super(name, PlayerColorEnum.gkmasModMisuzu_character,new IdolEnergyOrb(), new SpriterAnimation(filepath));

        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 150.0F * Settings.scale);
        initializeData();

//        refreshSkin();
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


//    public void refreshSkin() {
//        if(this.idolData == null)
//            this.idolData = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex);
//        this.idolName = this.idolData.idolName;
//        String skinName = this.idolData.getSkinImg(SkinSelectScreen.Inst.skinIndex);
//        String path = String.format("gkmasModResource/img/idol/%s/stand/stand_%s.scml",SkinSelectScreen.Inst.idolName,skinName);
//        this.animation = new SpriterAnimation(path);
//    }

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
        addColorToCardPool(PlayerColorEnum.gkmasModColorMoon, tmpPool);
        addColorToCardPool(PlayerColorEnum.gkmasModColorMisuzu, tmpPool);
        addColorToCardPool(PlayerColorEnum.gkmasModColorSense, tmpPool);

        return tmpPool;
    }

    // 初始卡组的ID，可直接写或引用变量
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(BaseAppeal.ID);
        retVal.add(BasePose.ID);
        retVal.add(BasePerform.ID);
        retVal.add(BaseAwareness.ID);
        retVal.add(BaseVision.ID);
        retVal.add(BaseBehave.ID);
        retVal.add(BaseExpression.ID);
        retVal.add(ChangeMood.ID);
        retVal.add(KawaiiGesture.ID);
        retVal.add(Challenge.ID);
        retVal.add(TryError.ID);
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
//        retVal.add(PocketBook.ID);
        retVal.add(SyngUpRelic.ID);
        return retVal;
    }

    public int getHP(){
        return 76;
    }

    public int getGold(){
        return 99;
    }

    public int getMaxOrbs(){
        return 0;
    }

    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(
                "秦谷美玲", // 人物名字
                "悠闲自在、我行我素的女孩子，状态经常在 #y困倦 、 #y睡眠 和 #y清醒 之间波动。 NL 对自己和他人都温柔友善，喜欢制作 #y点心 、照顾和宠爱别人。 NL 能够召唤昔日的 #y好友 辅助战斗，或叠加 #y重力 召唤 #y黑洞 。", // 人物介绍
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
        return "秦谷美玲";
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return PlayerColorEnum.gkmasModColor;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new GachaAgain();
    }

    @Override
    public Color getCardTrailColor() {
        return GkmasMod.gkmasMod_color;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 5;
    }

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
        return "秦谷美玲";
    }

    // 创建人物实例，照抄
    @Override
    public AbstractPlayer newInstance() {
        return new MisuzuCharacter(this.name);
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

    @Override
    public void applyStartOfCombatLogic() {
        super.applyStartOfCombatLogic();
        AbstractDungeon.actionManager.addToTop(new ChangeStanceAction(new SleepyStance().ID));
    }
}
