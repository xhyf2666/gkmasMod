package gkmasmod.downfall.bosses;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.ClearCardQueueAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import gkmasmod.characters.IdolEnergyOrb;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.downfall.cards.anomaly.*;
import gkmasmod.downfall.cards.free.ENBaseAppeal;
import gkmasmod.downfall.cards.free.ENLoveMyselfCool;
import gkmasmod.downfall.cards.free.ENLoveMyselfCute;
import gkmasmod.downfall.cards.logic.*;
import gkmasmod.downfall.cards.sense.*;
import gkmasmod.downfall.charbosses.actions.util.CharbossTurnstartDrawAction;
import gkmasmod.downfall.charbosses.actions.util.DelayedActionAction;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.downfall.charbosses.cards.red.EnBodySlam;
import gkmasmod.downfall.charbosses.core.EnemyEnergyManager;
import gkmasmod.downfall.charbosses.orbs.EnemyEmptyOrbSlot;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import gkmasmod.downfall.charbosses.relics.CBR_LizardTail;
import gkmasmod.downfall.charbosses.stances.AbstractEnemyStance;
import gkmasmod.monster.ChangeScene;
import gkmasmod.monster.LatterEffect;
import gkmasmod.powers.*;
import gkmasmod.relics.PocketBook;
import gkmasmod.room.GkmasBossRoom;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.PlayerHelper;

import java.util.ArrayList;

import static gkmasmod.characters.PlayerColorEnum.gkmasMod_character;

public class ProducerBoss extends AbstractCharBoss {
    public int maxStage = 7;
    public static int stage = 0;
    public static String idolName = IdolData.prod;
    public static String ID = String.format("IdolBoss_%s",idolName);
    public AbstractBossDeckArchetype[] archetypes=null;
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static int baseHP = 300;
    public static int HPIncrease = 300;
    public int maxHP = 300;
    public static int gold = 200;
    private static final String bgFormat = "gkmasModResource/img/bg/bg_%s_00%s.png";

    public boolean rebirth=false;

    public boolean shroFlag = true;

    public boolean changeSong = true;

    public boolean hasChange = false;

    public static int skipTurn = 0;

    public static int producerCardIndex = 0;

    public static ArrayList<AbstractCard> producerCards = null;

    public static void addToList(ArrayList<AbstractCard> c, AbstractCard q, boolean upgraded) {
        if (upgraded) q.upgrade();
        c.add(q);
    }

    public static AbstractCard getProducerCard(){
        if(producerCards == null) {
            producerCards = getProducerCards();
        }
        if (producerCardIndex >= producerCards.size()) {
            producerCardIndex = producerCardIndex % producerCards.size();
        }
        AbstractCard card = producerCards.get(producerCardIndex);
        producerCardIndex++;
        return card.makeCopy();
    }

    public static ArrayList<AbstractCard> getProducerCards(){
            ArrayList<AbstractCard> cardsList = new ArrayList<>();
            boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 5;
            switch (stage) {
                case 0:
                    addToList(cardsList, new ENReservedGirl(),extraUpgrades);
                    addToList(cardsList, new ENArbeiter(),extraUpgrades);
                    addToList(cardsList, new ENHighlyEducatedIdol(),extraUpgrades);
                    addToList(cardsList, new ENFullOfEnergy(),extraUpgrades);
                    addToList(cardsList, new ENUntappedPotential(),extraUpgrades);
                    addToList(cardsList, new ENTopIdolInSchool(),extraUpgrades);
                    addToList(cardsList, new ENStubborn(),extraUpgrades);
                    addToList(cardsList, new ENAccommodating(),extraUpgrades);
                    addToList(cardsList, new ENFriendly(),extraUpgrades);
                    addToList(cardsList, new ENLittlePrince(),extraUpgrades);
                    addToList(cardsList, new ENRisingStar(),extraUpgrades);
                    addToList(cardsList, new ENFreeWoman(),extraUpgrades);

                    addToList(cardsList, new ENFirstPlace(),extraUpgrades);
                    addToList(cardsList, new ENFirstCrystal(),extraUpgrades);
                    addToList(cardsList, new ENFirstMiss(),extraUpgrades);
                    addToList(cardsList, new ENFirstFun(),extraUpgrades);
                    addToList(cardsList, new ENFirstColor(),extraUpgrades);
                    addToList(cardsList, new ENFirstFuture(),extraUpgrades);
                    addToList(cardsList, new ENFirstReward(),extraUpgrades);
                    addToList(cardsList, new ENFirstFriend(),extraUpgrades);
                    addToList(cardsList, new ENFirstGround(),extraUpgrades);
                    break;
                case 1:
                    addToList(cardsList, new ENKingAppear(),extraUpgrades);
                    addToList(cardsList, new ENPureWhiteFairy(),extraUpgrades);
                    addToList(cardsList, new ENWholeheartedly(),extraUpgrades);
                    addToList(cardsList, new ENNeverYieldFirst(),extraUpgrades);
                    addToList(cardsList, new ENLoneWolf(),extraUpgrades);
                    addToList(cardsList, new ENRestAndWalk(),extraUpgrades);
                    addToList(cardsList, new ENDefeatBigSister(),extraUpgrades);
                    addToList(cardsList, new ENColorfulCute(),extraUpgrades);
                    addToList(cardsList, new ENSupportiveFeelings(),extraUpgrades);
                    addToList(cardsList, new ENAuthenticity(),extraUpgrades);
                    addToList(cardsList, new ENBraveStep(),extraUpgrades);
                    addToList(cardsList, new ENLovesTheStruggle(),extraUpgrades);
                    break;
                case 2:
                    addToList(cardsList, new ENSceneryOnHouse(),extraUpgrades);
                    addToList(cardsList, new ENNoDistractions(),extraUpgrades);
                    addToList(cardsList, new ENNotAfraidAnymore(),extraUpgrades);
                    addToList(cardsList, new ENEachPath(),extraUpgrades);
                    addToList(cardsList, new ENSenseOfDistance(),extraUpgrades);
                    addToList(cardsList, new ENNeverLose(),extraUpgrades);
                    addToList(cardsList, new ENTopStar(),extraUpgrades);
                    addToList(cardsList, new ENBigOnigiri(),extraUpgrades);
                    addToList(cardsList, new ENSeriousHobby(),extraUpgrades);
                    addToList(cardsList, new ENDebutStageForTheLady(),extraUpgrades);
                    addToList(cardsList, new ENOneMoreStep(),extraUpgrades);
                    addToList(cardsList, new ENDressedUpInStyle(),extraUpgrades);
                    break;
                case 3:
                    addToList(cardsList, new ENAfternoonBreeze(),extraUpgrades);
                    addToList(cardsList, new ENCumulusCloudsAndYou(),extraUpgrades);
                    addToList(cardsList, new ENChillyBreak(),extraUpgrades);
                    addToList(cardsList, new ENSummerEveningSparklers(),extraUpgrades);
                    addToList(cardsList, new ENFirstRamune(),extraUpgrades);
                    addToList(cardsList, new ENGoldfishScoopingChallenge(),extraUpgrades);
                    addToList(cardsList, new ENRefreshingBreak(),extraUpgrades);
                    addToList(cardsList, new ENAQuickSip(),extraUpgrades);
                    addToList(cardsList, new ENMakeExactSignboard(),extraUpgrades);
                    addToList(cardsList, new ENStruggleHandmade(),extraUpgrades);
                    addToList(cardsList, new ENGonnaTrickYou(),extraUpgrades);
                    break;
                case 4:
                    addToList(cardsList, new ENMoonlitRunway(),extraUpgrades);
                    addToList(cardsList, new ENSurgingEmotion(),extraUpgrades);
                    addToList(cardsList, new ENTangledFeelings(),extraUpgrades);
                    addToList(cardsList, new ENSeeYouTomorrow(),extraUpgrades);
                    addToList(cardsList, new ENPow(),extraUpgrades);
                    addToList(cardsList, new ENFullAdrenaline(),extraUpgrades);
                    addToList(cardsList, new ENWelcomeToTeaParty(),extraUpgrades);
                    addToList(cardsList, new ENKiraKiraPrism(),extraUpgrades);
                    addToList(cardsList, new ENSwayingOnTheBus(),extraUpgrades);
                    addToList(cardsList, new ENBeyondTheCrossing(),extraUpgrades);
                    break;
                case 5:
                    addToList(cardsList, new ENHappyChristmas(),extraUpgrades);
                    addToList(cardsList, new ENShareSomethingWithYou(),extraUpgrades);
                    addToList(cardsList, new ENWithLove(),extraUpgrades);
                    addToList(cardsList, new ENGiveYou(),extraUpgrades);
                    addToList(cardsList, new ENWorkHard(),extraUpgrades);
                    addToList(cardsList, new ENCanYouAccept(),extraUpgrades);
                    addToList(cardsList, new ENLittleSun(),extraUpgrades);
                    addToList(cardsList, new ENCareCard(),extraUpgrades);
                    addToList(cardsList, new ENEnjoyThreeColor(),extraUpgrades);
                    addToList(cardsList, new ENEncounterWithHero(),extraUpgrades);
                    addToList(cardsList, new ENAfterSchoolChat(),extraUpgrades);
                    addToList(cardsList, new ENThatDayHere(),extraUpgrades);
                    break;
                case 6:
                    addToList(cardsList, new ENMyPrideBigSister(),extraUpgrades);
                    addToList(cardsList, new ENFlyAgain(),extraUpgrades);
                    addToList(cardsList, new ENEureka(),extraUpgrades);
                    addToList(cardsList, new ENContinuousExpandWorld(),extraUpgrades);
                    addToList(cardsList, new ENSayGoodbyeToDislikeMyself(),extraUpgrades);
                    addToList(cardsList, new ENTheScenerySawSomeday(),extraUpgrades);
                    addToList(cardsList, new ENNewStage(),extraUpgrades);
                    addToList(cardsList, new ENDreamStillContinue(),extraUpgrades);
                    addToList(cardsList, new ENGetAnswer(),extraUpgrades);
                    addToList(cardsList, new ENLetMeBecomeIdol(),extraUpgrades);
                    addToList(cardsList, new ENUntilNowAndFromNow(),extraUpgrades);
                    break;
            }
            return cardsList;
    }

    public ProducerBoss() {
        super(CardCrawlGame.languagePack.getCharacterString(String.format("IdolName:%s",idolName)).NAMES[0], ID, baseHP, 00.0f, 0.0f, 220.0f, 500.0f, String.format("gkmasModResource/img/idol/othe/%s/stand/stand_%s.png",idolName,"skin10"), 00.0f, 00.0f, gkmasMod_character);
        maxHP = baseHP;
        AbstractCharBoss.theIdolName = idolName;
        this.energyOrb = new IdolEnergyOrb();
        this.energy = new EnemyEnergyManager(999);
//        this.flipHorizontal = true;
        AbstractCharBoss.theIdolName = idolName;
        this.energyString = "[E]";
        type = EnemyType.BOSS;
        skipTurn = 0;
        stage = 0;
        producerCardIndex = 0;
    }

    @Override
    public void generateDeck() {
    }

    @Override
    public void preBattlePrep() {
        super.preBattlePrep();
//        AbstractGameEffect effect =  new ChangeScene(ImageMaster.loadImage(String.format(bgFormat,this.idolName,stage+1)));
//        AbstractDungeon.effectList.add(new LatterEffect(() -> {
//            AbstractDungeon.effectsQueue.add(effect);
//        }));
        if(maxStage>1){
            (AbstractDungeon.getCurrRoom()).cannotLose = true;
        }
        CardCrawlGame.music.dispose();
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        String song = "gkmasModResource/audio/song/schoolSong.ogg";
        CardCrawlGame.music.playTempBgmInstantly(song,true);
        addToBot(new ApplyPowerAction(this,this,new ProducerIdolPower(this)));
    }

    @Override
    public void loseBlock(int amount) {
        super.loseBlock(amount);
        for (AbstractCard c:hand.group){
            if (c instanceof EnBodySlam){
                c.applyPowers();
            }
        }
    }

    @Override
    public void takeTurn() {
        if(rebirth){
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_AWAKENEDONE_1"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new IntenseZoomEffect(this.hb.cX, this.hb.cY, true), 0.05F, true));
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "REBIRTH"));
            hasChange=true;
            return;
        }
        super.takeTurn();
    }

    @Override
    public void playMusic() {

    }

    public void endTurnStartTurn() {
        if(rebirth){
            if(hasChange==false)
                return;
            rebirth=false;
            addToBot(new WaitAction(0.2f));
            this.applyStartOfTurnPostDrawRelics();
            this.applyStartOfTurnPostDrawPowers();
            this.cardsPlayedThisTurn = 0;
            this.attacksPlayedThisTurn = 0;
            return;
        }
        super.endTurnStartTurn();
    }

    public void changeState(String key) {
        switch (key) {
            case "REBIRTH":
                this.energy.recharge();
                this.changeSong = true;
                if(idolName.equals(IdolData.hrnm)){
                    for (int i = 0; i < this.maxOrbs; ++i) {
                        this.orbs.add(new EnemyEmptyOrbSlot());
                    }
                }
                maxHP+=HPIncrease;
                if (AbstractDungeon.ascensionLevel >= 9) {
                    maxHP = Math.round(maxHP * 1.2F);
                }
                this.maxHealth = maxHP;
                skipTurn = 0;
                producerCardIndex = 0;
                if(stage<maxStage-1)
                    AbstractDungeon.getCurrRoom().cannotLose = true;
                else{
                    AbstractDungeon.getCurrRoom().cannotLose = false;
                }
                addToBot(new DelayedActionAction(new CharbossTurnstartDrawAction()));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BarricadePower(this)));
                if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
                    PocketBook book = (PocketBook)AbstractDungeon.player.getRelic(PocketBook.ID);
                    this.maxHealth = (int)(this.maxHealth*book.healthRate);
                }
                this.halfDead = false;
                if (Settings.isEndless && AbstractDungeon.player.hasBlight("ToughEnemies")) {
                    float mod = AbstractDungeon.player.getBlight("ToughEnemies").effectFloat();
                    this.maxHealth = (int)(this.maxHealth * mod);
                }
                if (ModHelper.isModEnabled("MonsterHunter"))
                    this.maxHealth = (int)(this.maxHealth * 1.5F);
//                AbstractGameEffect effect =  new ChangeScene(ImageMaster.loadImage(String.format(bgFormat,this.idolName,stage+1)));
//                AbstractDungeon.effectList.add(new LatterEffect(() -> {
//                    AbstractDungeon.effectsQueue.add(effect);
//                }));
                AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, this.maxHealth));
                
                setMove((byte) 0, Intent.NONE);
//                CardCrawlGame.music.dispose();
//                CardCrawlGame.music.unsilenceBGM();
//                AbstractDungeon.scene.fadeOutAmbiance();
//                String song = String.format("gkmasModResource/audio/song/%s_%s.ogg", this.idolName, IdolData.getIdol(this.idolName).getBossSong(stage));
//                if(!Gdx.files.internal(song).exists()){
//                    song = String.format("gkmasModResource/audio/song/%s_%s.ogg", this.idolName, "002");
//                }
//                CardCrawlGame.music.playTempBgmInstantly(song,true);
                break;
        }
    }

    @Override
    public void onPlayAttackCardSound() {

        switch (MathUtils.random(2)) {
        }
    }

    public void damage(DamageInfo info) {
        super.damage(info);

        float healthRatio = (float)this.currentHealth / (float)this.maxHealth;
//        if(healthRatio<=0.5F&&changeSong){
//            if(this.shroFlag&&this.idolName.equals(IdolData.shro)&&this.stage==1){
//                this.shroFlag=false;
//                changeSong = false;
//            }
//            else{
//                this.changeSong=false;
//                CardCrawlGame.music.dispose();
//                String song = String.format("gkmasModResource/audio/song/%s_%s.ogg", SkinSelectScreen.Inst.idolName, IdolData.getIdol(SkinSelectScreen.Inst.idolName).getBossSong(stage));
//                if(AbstractDungeon.player instanceof MisuzuCharacter){
//                    song = String.format("gkmasModResource/audio/song/%s_%s.ogg", IdolData.hmsz, IdolData.hmszData.getBossSong(stage));
//                }
//                if(!Gdx.files.internal(song).exists()){
//                    song = String.format("gkmasModResource/audio/song/%s_%s.ogg", SkinSelectScreen.Inst.idolName, "002");
//                }
//                CardCrawlGame.music.playTempBgmInstantly(song,true);
//            }
//        }

        if(AbstractCharBoss.boss!=null&&AbstractCharBoss.boss.hasRelic(CBR_LizardTail.ID)&&this.LizardTailActive){
            this.LizardTailActive=false;
            return;
        }

        if (stage<maxStage-1&&this.currentHealth <= 0 && !this.halfDead) {
            if ((AbstractDungeon.getCurrRoom()).cannotLose == true)
                this.halfDead = true;
            for (AbstractCharbossRelic r : this.relics) {
                r.onUnequip();
            }
            relics.clear();
            hand.clear();
            limbo.clear();
//            stance.onExitStance();
//            stance = AbstractEnemyStance.getStanceFromName("Neutral");
//            stance.onEnterStance();
            for (AbstractPower p : this.powers)
                p.onDeath();
            for (AbstractRelic r : AbstractDungeon.player.relics)
                r.onMonsterDeath(this);
            addToTop(new ClearCardQueueAction());
            PlayerHelper.removeAllNegativePower(this);
//            this.powers.clear();
            producerCards = null;
            stage++;
            hasChange = false;
            rebirth=true;
//            CardCrawlGame.music.dispose();
//            CardCrawlGame.music.playTempBgmInstantly("gkmasModResource/audio/bgm/gasha_002.ogg",true);
//            AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX - 50, this.hb.cY + this.dialogY + 50, 5.0F, DIALOG[0], false));
            setMove((byte) 0, Intent.UNKNOWN);
            createIntent();
            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)0, Intent.UNKNOWN));

            applyPowers();
        }
        else if(this.currentHealth <= 0){
            die();
        }
    }

    @Override
    public void die() {
        if (!(AbstractDungeon.getCurrRoom()).cannotLose) {
            super.die();
            if(AbstractDungeon.getCurrRoom() instanceof GkmasBossRoom){
                onBossVictoryLogic();
                onFinalBossVictoryLogic();
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);

    }

    public int getGold(){
        return gold;
    }

    public void addGold(int gold){
        this.gold+=gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}


