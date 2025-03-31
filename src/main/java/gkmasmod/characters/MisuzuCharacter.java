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
import gkmasmod.cards.free.*;
import gkmasmod.cards.hmsz.BasePace;
import gkmasmod.cards.hmsz.Stress;
import gkmasmod.cards.hmsz.YawnAttack;
import gkmasmod.cards.logic.BaseAwareness;
import gkmasmod.cards.logic.BaseVision;
import gkmasmod.cards.logic.ChangeMood;
import gkmasmod.cards.logic.KawaiiGesture;
import gkmasmod.cards.sense.BaseBehave;
import gkmasmod.cards.sense.BaseExpression;
import gkmasmod.cards.sense.Challenge;
import gkmasmod.cards.sense.TryError;
import gkmasmod.cards.special.ResultWillNotChange;
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
        this.initializeClass(
                null,
                String.format("gkmasModResource/img/idol/%s/stand/fire.png",this.idolName),
                String.format("gkmasModResource/img/idol/%s/stand/fire.png",this.idolName),
                String.format("gkmasModResource/img/idol/%s/stand/sleep_skin10.png",this.idolName),
                this.getLoadout(),
                0.0F, 0.0F,
                200.0F, 220.0F,
                new EnergyManager(3)
        );
    }

    public void refreshSkin(int step){
        int index=10;
        if(step == 0){
            index = 10;
        }else if(step == 1){
            index = 13;
        }else if(step == 2){
            index = 23;
        }

        String path = String.format("gkmasModResource/img/idol/%s/stand/stand_skin%d.scml",this.idolName,index);
        this.animation = new SpriterAnimation(path);
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
//        addColorToCardPool(PlayerColorEnum.gkmasModColorSense, tmpPool);

        return tmpPool;
    }

    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(BaseAppeal.ID);
        retVal.add(YawnAttack.ID);
        retVal.add(YawnAttack.ID);
        retVal.add(YawnAttack.ID);
        retVal.add(BasePose.ID);
        retVal.add(BasePace.ID);
        retVal.add(BasePace.ID);
        retVal.add(BasePace.ID);
        retVal.add(BasePerform.ID);
        retVal.add(Stress.ID);
        retVal.add(ResultWillNotChange.ID);
        return retVal;
    }

    @Override
    public void initializeStarterDeck() {
        super.initializeStarterDeck();
        ArrayList<AbstractCard> cards = new ArrayList<>();
        int first = IdolData.hmszData.getFirstThreeType();
        int second = IdolData.hmszData.getSecondThreeType();
        int third = IdolData.hmszData.getThirdThreeType();
        int index_attack =0;
        int index_defend =0;

        for (AbstractCard c : this.masterDeck.group){
            if(c.rarity == AbstractCard.CardRarity.BASIC&&c.canUpgrade())
                cards.add(c);
            if(c.cardID.equals(YawnAttack.ID)){
                if(index_attack==0)
                    AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,third);
                else if(index_attack==1)
                    AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,second);
                else if(index_attack==2)
                    AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,first);
                index_attack++;
            }
            else if(c.cardID.equals(BasePace.ID)){
                if(index_defend==0)
                    AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,third);
                else if(index_defend==1)
                    AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,second);
                else if(index_defend==2)
                    AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,first);
                AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,index_defend);
                index_defend++;
            }
            else if(c.cardID.equals(BaseAppeal.ID)){
                AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,first);
            }
            else if(c.cardID.equals(BasePose.ID)){
                AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,second);
            }
            else if(c.cardID.equals(BasePerform.ID)){
                AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,third);
            }
            else if(c.cardID.equals(Stress.ID)){
                AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,first);
            }

        }

    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(SyngUpRelic.ID);
        retVal.add(PocketBook.ID);
        retVal.add(MisuzuNatureRelic.ID);
        retVal.add(DreamCatcher.ID);
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
                "秦谷美铃",
                "悠闲自在、我行我素的女孩子，状态经常在 #y困倦 、 #y睡眠 和 #y清醒 之间波动。 NL 对自己和他人都温柔友善，喜欢制作点心、照顾和宠爱别人。 NL 能够召唤昔日的 #y好友 辅助战斗。",
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
        return "秦谷美铃";
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

    public void doCharSelectScreenSelectEffect() {
        SoundHelper.playSound("gkmasModResource/audio/voice/shro_click.ogg");
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, true);
    }

    @Override
    public ArrayList<CutscenePanel> getCutscenePanels() {
        ArrayList<CutscenePanel> panels = new ArrayList<>();
        panels.add(new CutscenePanel(String.format("gkmasModResource/img/UI/end/end_%s_001_00.png", this.idolName)));
        panels.add(new CutscenePanel(String.format("gkmasModResource/img/UI/end/end_%s_001_01.png", this.idolName)));
        panels.add(new CutscenePanel(String.format("gkmasModResource/img/UI/end/end_%s_001_02.png", this.idolName)));
        panels.add(new CutscenePanel(String.format("gkmasModResource/img/UI/end/end_%s_001_03.png", this.idolName)));
        return panels;
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return String.format("%s_click", this.idolName);
    }

    @Override
    public String getLocalizedCharacterName() {
        return "秦谷美铃";
    }

    @Override
    public AbstractPlayer newInstance() {
        return new MisuzuCharacter(this.name);
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

    @Override
    public void applyStartOfCombatLogic() {
        super.applyStartOfCombatLogic();
        AbstractDungeon.actionManager.addToTop(new ChangeStanceAction(new SleepyStance().ID));
    }
}
