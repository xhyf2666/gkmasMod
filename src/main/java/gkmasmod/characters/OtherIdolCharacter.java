package gkmasmod.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Defend_Blue;
import com.megacrit.cardcrawl.cards.blue.Dualcast;
import com.megacrit.cardcrawl.cards.blue.Strike_Blue;
import com.megacrit.cardcrawl.cards.blue.Zap;
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
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.cards.anomaly.BaseImage;
import gkmasmod.cards.anomaly.BaseMental;
import gkmasmod.cards.anomaly.FinalSpurt;
import gkmasmod.cards.free.*;
import gkmasmod.cards.hmsz.*;
import gkmasmod.cards.logic.BaseAwareness;
import gkmasmod.cards.logic.ChangeMood;
import gkmasmod.cards.othe.*;
import gkmasmod.cards.sense.*;
import gkmasmod.cards.special.FightWill;
import gkmasmod.cards.othe.SenseOfDistanceBlue;
import gkmasmod.cards.othe.SupportiveFeelingsBlue;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.patches.AbstractCardPatch;
import gkmasmod.relics.*;
import gkmasmod.screen.OtherSkinSelectScreen;
import gkmasmod.utils.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


public class OtherIdolCharacter extends CustomPlayer {

    private static final String CORPSE_IMAGE = "gkmasModResource/img/idol/othe/prod/sleep_skin10.png";

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

        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 150.0F * Settings.scale);
        initializeData();

        refreshSkin();
    }

    public void initializeData(){
        this.initializeClass(
                null,
                String.format("gkmasModResource/img/idol/othe/%s/stand/fire.png",this.idolName),
                String.format("gkmasModResource/img/idol/othe/%s/stand/fire.png",this.idolName),
                String.format("gkmasModResource/img/idol/othe/%s/stand/sleep_skin10.png",this.idolName),
                this.getLoadout(),
                0.0F, 0.0F,
                200.0F, 500.0F,
                new EnergyManager(3)
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

    public ArrayList<AbstractCard> addIdolCardToCardPool(ArrayList<AbstractCard> tmpPool) {
        Iterator<Map.Entry<String, AbstractCard>> cardLib = CardLibrary.cards.entrySet().iterator();
        while (true) {
            if (!cardLib.hasNext())
                return tmpPool;
            Map.Entry c = cardLib.next();
            AbstractCard card = (AbstractCard)c.getValue();
            if (card instanceof GkmasCard&&card.hasTag(GkmasCardTag.IDOL_CARD_TAG) && (
                    !UnlockTracker.isCardLocked((String)c.getKey()) || Settings.isDailyRun)){
                tmpPool.add(card);
            }
        }
    }

    public ArrayList<AbstractCard> getCardPool(ArrayList<AbstractCard> tmpPool) {
        if(OtherSkinSelectScreen.Inst.idolName.equals(IdolData.arnm)){
            addColorToCardPool(AbstractCard.CardColor.BLUE,tmpPool);
            tmpPool.add(new SenseOfDistanceBlue());
            tmpPool.add(new SupportiveFeelingsBlue());
            return tmpPool;
        }
        addColorToCardPool(PlayerColorEnum.gkmasModColor, tmpPool);
        addColorToCardPool(PlayerColorEnum.gkmasModColorMoon, tmpPool);
        CommonEnum.IdolType type = IdolData.getOtherIdol(OtherSkinSelectScreen.Inst.idolIndex).getType(OtherSkinSelectScreen.Inst.skinIndex);
        if (type == CommonEnum.IdolType.LOGIC) {
            addColorToCardPool(PlayerColorEnum.gkmasModColorLogic, tmpPool);
        } else if (type == CommonEnum.IdolType.SENSE) {
            addColorToCardPool(PlayerColorEnum.gkmasModColorSense, tmpPool);
        }else if(type == CommonEnum.IdolType.ANOMALY){
            addColorToCardPool(PlayerColorEnum.gkmasModColorAnomaly, tmpPool);
        }
        else if(type == CommonEnum.IdolType.PRODUCE){
            tmpPool.add(new AsYourFan());
            tmpPool.add(new CatchTheLight());
            tmpPool.add(new Fluffy());
            tmpPool.add(new FormalContract());
            tmpPool.add(new FromNowOn());
            tmpPool.add(new HeavyLove());
            tmpPool.add(new IDontRemember());
            tmpPool.add(new IRemember());
            tmpPool.add(new PleaseTakeCare());
            tmpPool.add(new ProducerAlsoIdol());
            tmpPool.add(new ThirtySeconds());
            tmpPool.add(new UniverseFirstHappiness());
            tmpPool.add(new FarewellFlower());
        }
        else{
            addColorToCardPool(PlayerColorEnum.gkmasModColorSense, tmpPool);
            addColorToCardPool(PlayerColorEnum.gkmasModColorLogic, tmpPool);
            addColorToCardPool(PlayerColorEnum.gkmasModColorAnomaly, tmpPool);
        }
        if(OtherSkinSelectScreen.Inst.idolName.equals(IdolData.andk)){
            tmpPool.add(new WarmUp());
            tmpPool.add(new DeepBreath());
            tmpPool.add(new TrueDeepBreath());
            tmpPool.add(new WonderfulPerformance());
            tmpPool.add(new EyePower());
            tmpPool.add(new WishPower());
            tmpPool.add(new SteadyWill());
            tmpPool.add(new Disposition());
            tmpPool.add(new TopEntertainment());
            tmpPool.add(new EndlessApplause());
            tmpPool.add(new Innocence());
            tmpPool.add(new JustOneMore());
        }
        if(OtherSkinSelectScreen.Inst.idolName.equals(IdolData.sson)){
            tmpPool.add(new SmallHappiness());
            tmpPool.add(new WindChimeWish());
        }

        return tmpPool;
    }

    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        if(OtherSkinSelectScreen.Inst.idolName.equals(IdolData.prod)){
            retVal.add(BaseAppeal.ID);
            retVal.add(BaseAppeal.ID);
            retVal.add(BaseAppeal.ID);
            retVal.add(BasePerform.ID);
            retVal.add(BasePerform.ID);
            retVal.add(BasePerform.ID);
            retVal.add(BasePose.ID);
            retVal.add(BasePose.ID);
            retVal.add(GachaAgain.ID);
            retVal.add(GachaAgain.ID);
            retVal.add(Gacha.ID);
        }
        else if(OtherSkinSelectScreen.Inst.idolName.equals(IdolData.andk)){
            retVal.add(BaseAppeal.ID);
            retVal.add(BaseAppeal.ID);
            retVal.add(BaseAppeal.ID);
            retVal.add(BasePerform.ID);
            retVal.add(BasePerform.ID);
            retVal.add(BasePerform.ID);
            retVal.add(BasePose.ID);
            retVal.add(BasePose.ID);
            retVal.add(BaseAwareness.ID);
            retVal.add(BaseExpression.ID);
            retVal.add(ChangeMood.ID);
        }
        else if(OtherSkinSelectScreen.Inst.idolName.equals(IdolData.sson)){
            retVal.add(BaseAppeal.ID);
            retVal.add(BaseAppeal.ID);
            retVal.add(BaseAppeal.ID);
            retVal.add(BasePerform.ID);
            retVal.add(BasePerform.ID);
            retVal.add(BasePerform.ID);
            retVal.add(BaseProvocation.ID);
            retVal.add(BaseBehave.ID);
            retVal.add(BaseBehave.ID);
            retVal.add(Challenge.ID);
        }
        else if(OtherSkinSelectScreen.Inst.idolName.equals(IdolData.sgka)){
            retVal.add(BaseAppeal.ID);
            retVal.add(BaseAppeal.ID);
            retVal.add(BaseAppeal.ID);
            retVal.add(BasePerform.ID);
            retVal.add(BasePerform.ID);
            retVal.add(BaseImage.ID);
            retVal.add(BaseImage.ID);
            retVal.add(BaseMental.ID);
            retVal.add(BaseMental.ID);
            retVal.add(FinalSpurt.ID);
            retVal.add(FightWill.ID);
        }
        else if(OtherSkinSelectScreen.Inst.idolName.equals(IdolData.arnm)){
            retVal.add(Strike_Blue.ID);
            retVal.add(Strike_Blue.ID);
            retVal.add(Strike_Blue.ID);
            retVal.add(Strike_Blue.ID);
            retVal.add(Defend_Blue.ID);
            retVal.add(Defend_Blue.ID);
            retVal.add(Defend_Blue.ID);
            retVal.add(Defend_Blue.ID);
            retVal.add(Dualcast.ID);
            retVal.add(Zap.ID);
        }
        else{
            retVal.add(BaseAppeal.ID);
            retVal.add(BaseAppeal.ID);
            retVal.add(BaseAppeal.ID);
            retVal.add(BasePerform.ID);
            retVal.add(BasePerform.ID);
            retVal.add(BasePerform.ID);
            retVal.add(BasePose.ID);
            retVal.add(BasePose.ID);
            retVal.add(GachaAgain.ID);
            retVal.add(GachaAgain.ID);
        }

        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(IdolStartingDeck.getOtherSpecialRelic(OtherSkinSelectScreen.Inst.idolIndex, OtherSkinSelectScreen.Inst.skinIndex));
        if(!OtherSkinSelectScreen.Inst.idolName.equals(IdolData.arnm)){
            retVal.add(PocketBook.ID);
            if(OtherSkinSelectScreen.Inst.idolName.equals(IdolData.prod)){
                retVal.add(IdolPhone.ID);
            }
            else{
                retVal.add(ProducerPhone.ID);
            }
            if(OtherSkinSelectScreen.Inst.updateIndex==1){
                retVal.add(BalanceLogicAndSense.ID);
            }
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
        if(OtherSkinSelectScreen.Inst.idolName.equals(IdolData.arnm)){
            return 3;
        }
        return 0;
    }

    public String getStory(){
        try{
            return CardCrawlGame.languagePack.getCharacterString(String.format("IdolStory:%s", OtherSkinSelectScreen.Inst.idolName)).TEXT[0];
        }
        catch (Exception e){
            return "不同背景的偶像团体。每位偶像拥有各自的初始卡组、专属遗物和成长倾向。";
        }
    }

    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(
                OtherSkinSelectScreen.Inst.curName,
                getStory(),
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
        return PlayerColorEnum.gkmasModColorOther;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return CardLibrary.getCard(this.chosenClass, this.idolData.getCard(this.skinIndex)).makeCopy();
    }

    @Override
    public Color getCardTrailColor() {
        return GkmasMod.gkmasMod_colorOther;
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

    @Override
    public void initializeStarterDeck() {
        super.initializeStarterDeck();
        ArrayList<AbstractCard> cards = new ArrayList<>();
        int first = IdolData.hmszData.getFirstThreeType();
        int second = IdolData.hmszData.getSecondThreeType();
        int third = IdolData.hmszData.getThirdThreeType();
        int count = 0;

        for (AbstractCard c : this.masterDeck.group){
            if(c.type!= AbstractCard.CardType.CURSE|| c.type!= AbstractCard.CardType.STATUS){
                switch (count){
                    case 0:
                        AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,first);
                        break;
                    case 1:
                        AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,second);
                        break;
                    case 2:
                        AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(c,third);
                        break;
                }
                count++;
                count = count % 3;
            }

        }

    }

}
