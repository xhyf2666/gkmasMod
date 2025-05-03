package gkmasmod.characters;

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
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.relics.*;
import gkmasmod.screen.OtherSkinSelectScreen;
import gkmasmod.utils.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


// 继承CustomPlayer类
public class OtherIdolCharacter extends CustomPlayer {
    // 人物死亡图像
    private static final String CORPSE_IMAGE = "gkmasModResource/img/idol/othe/prod/sleep_skin10.png";
    // 战斗界面左下角能量图标的每个图层


    private static final int STARTING_HP = 80;
    private static final int MAX_HP = 80;
    private static final int STARTING_GOLD = 99;
    private static final int HAND_SIZE = 0;
    private static final int ASCENSION_MAX_HP_LOSS = 5;

    public static String SELES_STAND = null;
    public static String filepath = "gkmasModResource/img/idol/othe/prod/stand/stand_skin10.scml";

    public static String idolName= IdolData.prod;

    public Idol idolData;

    public int skinIndex = 0;

    public OtherIdolCharacter(String name) {
        super(name, PlayerColorEnum.gkmasModOther_character,new IdolEnergyOrb(), new SpriterAnimation(filepath));

        // 人物对话气泡的大小，如果游戏中尺寸不对在这里修改（libgdx的坐标轴左下为原点）
        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 150.0F * Settings.scale);
        initializeData();

        refreshSkin();
    }

    public void initializeData(){
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


    public void refreshSkin() {
        if(this.idolData == null)
            this.idolData = IdolData.getOtherIdol(OtherSkinSelectScreen.Inst.idolIndex);
        this.idolName = this.idolData.idolName;
        String skinName = this.idolData.getSkinImg(OtherSkinSelectScreen.Inst.skinIndex);
        String path = String.format("gkmasModResource/img/idol/othe/%s/stand/stand_%s.scml",OtherSkinSelectScreen.Inst.idolName,skinName);
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
        addColorToCardPool(PlayerColorEnum.gkmasModColorMoon, tmpPool);
        CommonEnum.IdolType type = IdolData.getOtherIdol(OtherSkinSelectScreen.Inst.idolIndex).getType(OtherSkinSelectScreen.Inst.skinIndex);
        if (type == CommonEnum.IdolType.LOGIC) {
            addColorToCardPool(PlayerColorEnum.gkmasModColorLogic, tmpPool);
        } else if (type == CommonEnum.IdolType.SENSE) {
            addColorToCardPool(PlayerColorEnum.gkmasModColorSense, tmpPool);
        }else if(type == CommonEnum.IdolType.ANOMALY){
            addColorToCardPool(PlayerColorEnum.gkmasModColorAnomaly, tmpPool);
        } else{
            addColorToCardPool(PlayerColorEnum.gkmasModColorSense, tmpPool);
            addColorToCardPool(PlayerColorEnum.gkmasModColorLogic, tmpPool);
            addColorToCardPool(PlayerColorEnum.gkmasModColorAnomaly, tmpPool);
        }

        return tmpPool;
    }

    public ArrayList<String> getStartingDeck() {

        return IdolStartingDeck.getStartingDeck(OtherSkinSelectScreen.Inst.idolIndex, OtherSkinSelectScreen.Inst.skinIndex);
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(IdolStartingDeck.getSpecialRelic(OtherSkinSelectScreen.Inst.idolIndex, OtherSkinSelectScreen.Inst.skinIndex));
        retVal.add(PocketBook.ID);
        retVal.add(ProducerPhone.ID);
        if(OtherSkinSelectScreen.Inst.updateIndex==1){
            retVal.add(BalanceLogicAndSense.ID);
        }
        return retVal;
    }

    public int getHP(){
        return IdolData.getOtherIdol(OtherSkinSelectScreen.Inst.idolIndex).getHp();
    }

    public int getGold(){
        return IdolData.getOtherIdol(OtherSkinSelectScreen.Inst.idolIndex).getGold();
    }

    public int getMaxOrbs(){
        if(OtherSkinSelectScreen.Inst.idolName.equals(IdolData.hrnm)){
            return 3;
        }
        return 0;
    }

    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(
                OtherSkinSelectScreen.Inst.curName,
                "来自？？的？？团体。每位？？拥有各自的初始卡组、专属遗物和成长倾向。",
                getHP(),
                getHP(),
                getMaxOrbs(),
                getGold(),
                5,
                this,
                getStartingRelics(),
                getStartingDeck(),
                false
        );
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return "学园偶像大师";
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return PlayerColorEnum.gkmasModColor;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return CardLibrary.getCard(this.chosenClass, this.idolData.getCard(this.skinIndex)).makeCopy();
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

    public void doCharSelectScreenSelectEffect() {
        SoundHelper.playSound("gkmasModResource/audio/voice/shro_click.ogg");
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, true);
    }

    // 碎心图片
    @Override
    public ArrayList<CutscenePanel> getCutscenePanels() {
        ArrayList<CutscenePanel> panels = new ArrayList<>();
        panels.add(new CutscenePanel("gkmasModResource/img/UI/end/end1.png"));
        panels.add(new CutscenePanel("gkmasModResource/img/UI/end/end4.png"));
        return panels;
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return String.format("%s_click", this.idolName);
    }

    @Override
    public String getLocalizedCharacterName() {
        return "学园偶像大师";
    }

    @Override
    public AbstractPlayer newInstance() {
        return new OtherIdolCharacter(this.name);
    }

    @Override
    public String getSpireHeartText() {
        return"到了……登上舞台的时候";
    }

    @Override
    public Color getSlashAttackColor() {
        return GkmasMod.gkmasMod_color;
    }

    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[1];
    }

    @Override
    public Color getCardRenderColor() {
        return GkmasMod.gkmasMod_color;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL};
    }

    public String getIdolName() {
        return this.idolName;
    }

}
