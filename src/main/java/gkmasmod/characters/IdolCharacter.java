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
import gkmasmod.modcore.GkmasMod;
import gkmasmod.patches.AbstractCardPatch;
import gkmasmod.relics.*;
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


    private static final int STARTING_HP = 80;
    private static final int MAX_HP = 80;
    private static final int STARTING_GOLD = 99;
    private static final int HAND_SIZE = 0;
    private static final int ASCENSION_MAX_HP_LOSS = 5;

    public static String SELES_STAND = null;
    public static String filepath = "gkmasModResource/img/idol/shro/stand/stand_skin10.scml";

    public static String idolName= IdolData.shro;

    public Idol idolData;

    public int skinIndex = 0;

    public IdolCharacter(String name) {
        super(name, PlayerColorEnum.gkmasMod_character,new IdolEnergyOrb(), new SpriterAnimation(filepath));


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
        addColorToCardPool(PlayerColorEnum.gkmasModColorMoon, tmpPool);
        CommonEnum.IdolType type = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getType(SkinSelectScreen.Inst.skinIndex);
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

        return IdolStartingDeck.getStartingDeck(SkinSelectScreen.Inst.idolIndex, SkinSelectScreen.Inst.skinIndex);
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(IdolStartingDeck.getSpecailRelic(SkinSelectScreen.Inst.idolIndex, SkinSelectScreen.Inst.skinIndex));
        retVal.add(PocketBook.ID);
        retVal.add(ProducerPhone.ID);
        if(SkinSelectScreen.Inst.updateIndex==1){
            retVal.add(BalanceLogicAndSense.ID);
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
        else if(SkinSelectScreen.Inst.idolName==IdolData.amao){
            retVal.add(ProducerGlass.ID);
        }
        else if(SkinSelectScreen.Inst.idolName==IdolData.hume){
            retVal.add(Omamori.ID);
        }
        else if(SkinSelectScreen.Inst.idolName==IdolData.jsna){
            retVal.add(FrozenEye.ID);
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
                SkinSelectScreen.Inst.curName,
                "来自初星学园的偶像团体。每位偶像有各自的初始卡组、专属遗物和成长倾向。",
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
        panels.add(new CutscenePanel(String.format("gkmasModResource/img/UI/end/end_%s_001_00.png", this.idolName)));
        panels.add(new CutscenePanel(String.format("gkmasModResource/img/UI/end/end_%s_001_01.png", this.idolName)));
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
        return new IdolCharacter(this.name);
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

    @Override
    public void initializeStarterDeck() {
        super.initializeStarterDeck();
        int num = 0;
        if(GkmasMod.cardRate>0.4f)
            num = 1;
        if(GkmasMod.cardRate>0.7f)
            num = 2;

        ArrayList<AbstractCard> cards = new ArrayList<>();
        AbstractCard specialCard = null;
        AbstractCard replaceCard = null;
        this.idolData = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex);
        String specialCardID = this.idolData.getCard(SkinSelectScreen.Inst.skinIndex);
        String replaceCardID = this.idolData.getReplaceCard(SkinSelectScreen.Inst.skinIndex);
        int first = idolData.getFirstThreeType();
        int second = idolData.getSecondThreeType();
        int third = idolData.getThirdThreeType();
        int index_attack =0;
        int index_defend =0;
        int index_basepose =0;
        int index_style =0;
        int index_image =0;
        int index_mental =0;
        ArrayList<AbstractCard> needToSet = new ArrayList<>();
        for (AbstractCard c : this.masterDeck.group){
            if(c.rarity == AbstractCard.CardRarity.BASIC&&c.canUpgrade()&&!c.cardID.equals(specialCardID))
                cards.add(c);
            if(c.cardID.equals(BaseAppeal.ID)){
                if(index_attack==0)
                    AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,third);
                else if(index_attack==1)
                    AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,second);
                else if(index_attack==2)
                    AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,first);
                if(index_attack>2){
                    needToSet.add(c);
                    continue;
                }
                index_attack++;
            }
            else if(c.cardID.equals(BasePerform.ID)){
                if(index_defend==0)
                    AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,third);
                else if(index_defend==1)
                    AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,second);
                else if(index_defend==2)
                    AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,first);
                if(index_defend>2){
                    needToSet.add(c);
                    continue;
                }
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
            else if(c.cardID.equals(BaseImage.ID)){
                if(index_image==0)
                    AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,first);
                else if(index_image==1)
                    AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,second);
                index_image++;
            }
            else if(c.cardID.equals(BaseMental.ID)){
                if(index_mental==0)
                    AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,second);
                else if(index_mental==1)
                    AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,third);
                index_mental++;
            }
            else if(c.cardID.equals(BaseGreeting.ID)){
                AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,second);
            }
            else if(c.cardID.equals(Challenge.ID)||c.cardID.equals(TryError.ID)||c.cardID.equals(KawaiiGesture.ID)||c.cardID.equals(ChangeMood.ID)||c.cardID.equals(Intensely.ID)||c.cardID.equals(FinalSpurt.ID)){
                AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,first);
            }
            else if(c.cardID.equals(specialCardID)){
                specialCard = c;
                AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,first);
            }
            else if(c.cardID.equals(replaceCardID)){
                replaceCard = c;
                AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,first);
            }

        }

        for(AbstractCard c : needToSet){
            if(c.cardID.equals(BaseAppeal.ID)){
                AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,third);
            }
            else if(c.cardID.equals(BasePerform.ID)){
                AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,third);
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
            if(specialCard!=null){
                specialCard.upgrade();
            }
        }
        if(replaceCard!=null){
            replaceCard.upgrade();
        }


    }
}
