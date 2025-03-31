package gkmasmod.modcore;

import actlikeit.ActLikeIt;
import actlikeit.dungeons.CustomDungeon;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.ModToggleButton;
import basemod.eventUtil.AddEventParams;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import gkmasmod.cards.hmsz.*;
import gkmasmod.cards.moon.BloodySacrifice;
import gkmasmod.cards.moon.CatchNunu;
import gkmasmod.cards.moon.StartingLine;
import gkmasmod.cards.moon.TimeLoop;
import gkmasmod.cards.special.*;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.downfall.bosses.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.Listener.CardImgUpdateListener;
import gkmasmod.cards.anomaly.*;
import gkmasmod.cards.free.*;
import gkmasmod.cards.logic.*;
import gkmasmod.cards.sense.*;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.dungeons.Gokugetsu;
import gkmasmod.dungeons.IdolRoad;
import gkmasmod.event.*;
import gkmasmod.growEffect.BaseDamageGrow;
import gkmasmod.monster.beyond.*;
import gkmasmod.monster.ending.MisuzuBoss;
import gkmasmod.monster.ending.MonsterRinha;
import gkmasmod.monster.exordium.MonsterAsari;
import gkmasmod.monster.exordium.MonsterGekka;
import gkmasmod.monster.exordium.MonsterNadeshiko;
import gkmasmod.monster.exordium.MonsterShion;
import gkmasmod.potion.*;
import gkmasmod.relics.*;
import gkmasmod.room.shop.AnotherShopScreen;
import gkmasmod.room.specialTeach.SpecialTeachScreen;
import gkmasmod.screen.PocketBookViewScreen;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.*;
import gkmasmod.variables.*;
import gkmasmod.vfx.SimplePlayVideoEffect2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static gkmasmod.characters.PlayerColorEnum.*;

@SpireInitializer
public class GkmasMod implements EditCardsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber, EditCharactersSubscriber, AddAudioSubscriber,EditRelicsSubscriber,PostInitializeSubscriber , StartGameSubscriber,PostCreateStartingDeckSubscriber,PostRenderSubscriber,PostBattleSubscriber {

    public static List<CardImgUpdateListener> listeners = new ArrayList<>();
    //攻击、技能、能力牌的背景图片(512)

    static String idolName = "shro";

    private static final String carduiImgFormat = "gkmasModResource/img/idol/%s/cardui/%s/%sbg_%s.png";
    private static final String ATTACK_CC = String.format("gkmasModResource/img/idol/%s/cardui/512/bg_attack.png",idolName);
    private static final String SKILL_CC = String.format("gkmasModResource/img/idol/%s/cardui/512/bg_skill.png",idolName);
    private static final String POWER_CC = String.format("gkmasModResource/img/idol/%s/cardui/512/bg_power.png",idolName);
    private static final String ENERGY_ORB_CC = "gkmasModResource/img/UI/energy.png";
    //攻击、技能、能力牌的背景图片(1024)
    private static final String ATTACK_CC_PORTRAIT = String.format("gkmasModResource/img/idol/%s/cardui/1024/bg_attack.png",idolName);
    private static final String SKILL_CC_PORTRAIT = String.format("gkmasModResource/img/idol/%s/cardui/1024/bg_skill.png",idolName);
    private static final String POWER_CC_PORTRAIT = String.format("gkmasModResource/img/idol/%s/cardui/1024/bg_power.png",idolName);
    private static final String ENERGY_ORB_CC_PORTRAIT = "gkmasModResource/img/UI/energy_164.png";
    public static final String CARD_ENERGY_ORB = "gkmasModResource/img/UI/energy_22.png";

    private static final String MY_CHARACTER_BUTTON = "gkmasModResource/img/charSelect/selectButton.png";
    private static final String MY_CHARACTER_BUTTON2 = "gkmasModResource/img/charSelect/selectButton3.png";
    private static final String MARISA_PORTRAIT = "gkmasModResource/img/charSelect/background_init.png";
    private static final String MARISA_PORTRAIT2 = "gkmasModResource/img/charSelect/background_hmsz.png";
    public static final Color gkmasMod_color = CardHelper.getColor(80, 80, 50);

    public static final Color gkmasMod_colorLogic = CardHelper.getColor(50, 70, 200);

    public static final Color gkmasMod_colorSense = CardHelper.getColor(200, 150, 100);

    public static final Color gkmasMod_colorAnomaly = CardHelper.getColor(100, 100, 100);

    public static final Color gkmasMod_colorMisuzu = CardHelper.getColor(147, 134, 226);

    public static final Color gkmasMod_colorMoon = CardHelper.getColor(20, 20, 20);

    public static boolean onlyModBoss;
    public static boolean hasNewYear;
    private ArrayList<AbstractCard> cardsToAdd = new ArrayList<>();
    public static ArrayList<AbstractCard> recyclecards = new ArrayList<>();
    public static SpireConfig config;
    public static final String modID = "gkmasmod";
    public static int beat_hmsz = 0;
    public static float cardRate = 0.0F;
    public static AnotherShopScreen shopScreen;
    public static MapRoomNode node=null;
    public static boolean loseBlock = false;

    public static int masterEventCount = 0;

    public static int screenIndex = 0;

    public static Random threeSizeTagRng = new Random(0L);

    public static boolean restart =false;

    public static boolean playVideo = false;

    public static boolean renderScene = true;

    public static AbstractCard needCheckCard = null;

    public GkmasMod(){
        BaseMod.subscribe(this);
        String[] suffix = new String[]{"","logic_","sense_","anomaly_"};
        addColor(gkmasModColor,gkmasMod_color,idolName,suffix[0]);
        addColor(gkmasModColorLogic,gkmasMod_colorLogic,idolName,suffix[1]);
        addColor(gkmasModColorSense,gkmasMod_colorSense,idolName,suffix[2]);
        addColor(gkmasModColorAnomaly,gkmasMod_colorAnomaly,idolName,suffix[3]);
        addColor(gkmasModColorMisuzu,gkmasMod_colorMisuzu,IdolData.hmsz,"hmsz_");
        addColor(gkmasModColorMoon,gkmasMod_colorMoon,"moon","");
    }

    private void addColor(AbstractCard.CardColor cc,Color c,String idolName,String suffix){
        String[] size = new String[]{"512","1024"};
        String[] s= new String[]{"attack","skill","power"};

        BaseMod.addColor(cc,c,c,c,c,c,c,c,
                String.format(carduiImgFormat,idolName,size[0],suffix,s[0]),
                String.format(carduiImgFormat,idolName,size[0],suffix,s[1]),
                String.format(carduiImgFormat,idolName,size[0],suffix,s[2]),
                ENERGY_ORB_CC,
                String.format(carduiImgFormat,idolName,size[1],suffix,s[0]),
                String.format(carduiImgFormat,idolName,size[1],suffix,s[1]),
                String.format(carduiImgFormat,idolName,size[1],suffix,s[2]),
                ENERGY_ORB_CC_PORTRAIT,CARD_ENERGY_ORB);
    }

    public static void initialize(){
        new GkmasMod();
    }

    @Override
    public void receiveEditCards(){
        BaseMod.addDynamicVariable(new SecondMagicNumber());
        BaseMod.addDynamicVariable(new ThirdMagicNumber());
        BaseMod.addDynamicVariable(new HPMagicNumber());
        BaseMod.addDynamicVariable(new SecondDamage());
        BaseMod.addDynamicVariable(new SecondBlock());
        BaseMod.addDynamicVariable(new GrowMagicNumber());
        List<Object> instances = getCardsToAdd();
        for (Object instance : instances) {
            BaseMod.addCard((AbstractCard) instance);
        }
    }

    public void receiveAddAudio() {
        BaseMod.addAudio("shro_click", "gkmasModResource/audio/voice/shro_click.ogg");
        BaseMod.addAudio("kllj_click", "gkmasModResource/audio/voice/kllj_click.ogg");
    }

    @Override
    public void receiveEditStrings() {
        String lang;
        if (Settings.language == Settings.GameLanguage.ZHS) {
            lang = "chs";
        } else {
            lang = "chs";
        }
        BaseMod.loadCustomStringsFile(CardStrings.class, "gkmasModResource/localization/gkmas_cards_"+lang+".json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "gkmasModResource/localization/gkmas_powers_" + lang + ".json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, "gkmasModResource/localization/gkmas_characters_" + lang + ".json");
        BaseMod.loadCustomStringsFile(UIStrings.class, "gkmasModResource/localization/gkmas_ui_" + lang + ".json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, "gkmasModResource/localization/gkmas_relics_" + lang + ".json");
        BaseMod.loadCustomStringsFile(MonsterStrings.class, "gkmasModResource/localization/gkmas_monsters_" + lang + ".json");
        BaseMod.loadCustomStringsFile(EventStrings.class, "gkmasModResource/localization/gkmas_events_" + lang + ".json");
        BaseMod.loadCustomStringsFile(PotionStrings.class, "gkmasModResource/localization/gkmas_potions_" + lang + ".json");
        BaseMod.loadCustomStringsFile(StanceStrings.class, "gkmasModResource/localization/gkmas_stances_" + lang + ".json");
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String lang = "eng";
        if (Settings.language == Settings.GameLanguage.ZHS) {
            lang = "chs";
        } else {
            lang = "chs";
        }

        String json = Gdx.files.internal("gkmasModResource/localization/gkmas_keywords_" + lang + ".json")
                .readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID,keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }


    public static List<Object> getCardsToAdd(){
        List<Object> instances = new ArrayList<>();
        // 初始
        instances.add(new BaseAppeal());
        instances.add(new BasePose());
        instances.add(new Challenge());
        instances.add(new KawaiiGesture());
        instances.add(new BaseAwareness());
        instances.add(new BaseBehave());
        instances.add(new BasePerform());
        instances.add(new BaseVision());
        instances.add(new ChangeMood());
        instances.add(new TryError());
        instances.add(new BaseExpression());

        // 广
        instances.add(new HighlyEducatedIdol());
        instances.add(new FirstFun());
        instances.add(new LovesTheStruggle());
        instances.add(new SeriousHobby());
        instances.add(new SwayingOnTheBus());
        instances.add(new MakeExactSignboard());
        instances.add(new Eureka());
        instances.add(new WorkHard());
        // 莉莉娅
        instances.add(new ReservedGirl());
        instances.add(new FirstGround());
        instances.add(new PureWhiteFairy());
        instances.add(new NotAfraidAnymore());
        instances.add(new FirstRamune());
        instances.add(new WithLove());
        instances.add(new TheScenerySawSomeday());
        instances.add(new KiraKiraPrism());
        instances.add(new AfterSchoolChat());
        // 千奈
        instances.add(new FullOfEnergy());
        instances.add(new FirstColor());
        instances.add(new Wholeheartedly());
        instances.add(new DebutStageForTheLady());
        instances.add(new WelcomeToTeaParty());
        instances.add(new AQuickSip());
        instances.add(new GonnaTrickYou());
        instances.add(new ContinuousExpandWorld());
        instances.add(new LittleSun());
        // 佑芽
        instances.add(new UntappedPotential());
        instances.add(new DefeatBigSister());
        instances.add(new BigOnigiri());
        instances.add(new ShareSomethingWithYou());
        instances.add(new NewStage());
        instances.add(new SecretMeeting());
        instances.add(new LikeYouVeryMuch());
        // 咲季
        instances.add(new RisingStar());
        instances.add(new FirstFuture());
        instances.add(new NeverYieldFirst());
        instances.add(new NeverLose());
        instances.add(new Pow());
        instances.add(new GoldfishScoopingChallenge());
        instances.add(new VeryEasy());
        instances.add(new UntilNowAndFromNow());
        // 莉波
        instances.add(new Accommodating());
        instances.add(new FirstFriend());
        instances.add(new SupportiveFeelings());
        instances.add(new SenseOfDistance());
        instances.add(new SeeYouTomorrow());
        instances.add(new CumulusCloudsAndYou());
        instances.add(new RefreshingBreak());
        instances.add(new LetMeBecomeIdol());
        instances.add(new CanYouAccept());
        // 琴音
        instances.add(new Arbeiter());
        instances.add(new FirstReward());
        instances.add(new ColorfulCute());
        instances.add(new NoDistractions());
        instances.add(new FullAdrenaline());
        instances.add(new SummerEveningSparklers());
        instances.add(new HappyChristmas());
        instances.add(new MyPrideBigSister());
        // 麻央
        instances.add(new LittlePrince());
        instances.add(new FirstCrystal());
        instances.add(new Authenticity());
        instances.add(new DressedUpInStyle());
        instances.add(new ChillyBreak());
        instances.add(new MoonlitRunway());
        instances.add(new GetAnswer());
        instances.add(new EnjoyThreeColor());
        // 清夏
        instances.add(new Friendly());
        instances.add(new FirstMiss());
        instances.add(new BraveStep());
        instances.add(new OneMoreStep());
        instances.add(new AfternoonBreeze());
        instances.add(new BeyondTheCrossing());
        instances.add(new FlyAgain());
        instances.add(new EncounterWithHero());
        // 手毬
        instances.add(new Stubborn());
        instances.add(new FirstPlace());
        instances.add(new LoneWolf());
        instances.add(new EachPath());
        instances.add(new TangledFeelings());
        instances.add(new StruggleHandmade());
        instances.add(new SayGoodbyeToDislikeMyself());
        instances.add(new CareCard());
        // 星南
        instances.add(new TopIdolInSchool());
        instances.add(new KingAppear());
        instances.add(new TopStar());
        instances.add(new DreamStillContinue());
        instances.add(new GiveYou());

        // 普通
        instances.add(new LightGait());
        instances.add(new AiJiao());
        instances.add(new ServiceSpirit());
        instances.add(new HighFive());
        instances.add(new GoWithTheFlow());
        instances.add(new WarmUp());
        instances.add(new TalkTime());
        instances.add(new GoodMorning());
        instances.add(new TeaChat());
        instances.add(new WaitALittleLonger());
        instances.add(new Clap());
        instances.add(new CheerfulGreeting());
        instances.add(new FullOfPower());
        instances.add(new LightStep());
        instances.add(new Balance());
        instances.add(new Optimistic());
        instances.add(new DeepBreath());
        instances.add(new Restart());
        instances.add(new Yeah());
        instances.add(new Rhythm());
        instances.add(new Smile());
        instances.add(new LightColorMood());
        instances.add(new Inspire());
        instances.add(new HappyCurse());
        instances.add(new TrueDeepBreath());
        instances.add(new DayDream());

        // 罕见
        instances.add(new BrightFuture());
        instances.add(new ClassicPose());
        instances.add(new Improvise());
        instances.add(new PassionateReturn());
        instances.add(new Leap());
        instances.add(new Bless());
        instances.add(new StartDash());
        instances.add(new WonderfulPerformance());
        instances.add(new EncoreCall());
        instances.add(new SweetWink());
        instances.add(new ThankYou());
        instances.add(new FingerHeart());
        instances.add(new Shiny());
        instances.add(new LikeEveryone());
        instances.add(new IdolDeclaration());
        instances.add(new HighSpirits());
        instances.add(new EyePower());
        instances.add(new GreatCheer());
        instances.add(new PerformancePlan());
        instances.add(new WishPower());
//        instances.add(new WishPowerStrength());
//        instances.add(new WishPowerGoodTune());
        instances.add(new SteadyWill());
        instances.add(new StartSignal());
        instances.add(new Disposition());
        instances.add(new Existence());
        instances.add(new RoadToSuccess());
        instances.add(new OverflowMemory());
        instances.add(new Contact());
        instances.add(new HappyTime());
        instances.add(new FantasyCharm());
        instances.add(new WakuWaku());
        instances.add(new BeforeThePerformance());
        instances.add(new Sunbathing());
        instances.add(new ImaginaryTraining());
        instances.add(new EnergyIsFull());
        instances.add(new FlowerBouquet());
        instances.add(new UnstoppableThoughts());
        instances.add(new CheckPosition());
        instances.add(new Spotlight());
        instances.add(new GirlHeart());

        // 稀有
        instances.add(new CallResponse());
        instances.add(new PopPhrase());
        instances.add(new TopEntertainment());
        instances.add(new Awakening());
        instances.add(new NationalIdol());
        instances.add(new CharmGaze());
        instances.add(new Achievement());
        instances.add(new CharmPerformance());
        instances.add(new EndlessApplause());
        instances.add(new Innocence());
        instances.add(new Smile200());
        instances.add(new Flowering());
        instances.add(new Todoku());
        instances.add(new OnTV());
        instances.add(new DreamToAchieve());
        instances.add(new IdolSoul());
        instances.add(new Repartitioning());
        instances.add(new IamBigStar());
        instances.add(new StarDust());
        instances.add(new NotebookDetermination());
        instances.add(new HandwrittenLetter());
        instances.add(new Heartbeat());
        instances.add(new RainbowDreamer());
        instances.add(new PromiseThatTime());
        instances.add(new ForShiningYou());
        instances.add(new DreamColorLipstick());

        instances.add(new Gacha());
        instances.add(new JustOneMore());
        instances.add(new OccupyTheWorld());
        instances.add(new SpecialHuHu());
        instances.add(new Sleepy());
        instances.add(new SleepLate());
        instances.add(new WakeUp());
        instances.add(new ProudStudent());
        instances.add(new KawaiiKawaiiKawaii());
        instances.add(new DeliciousFace());
        instances.add(new TurnBack());
        instances.add(new WeAreSoStrong());
        instances.add(new PushingTooHardAgain());
        instances.add(new SplendidSusuki());
        instances.add(new SkipWater());
        instances.add(new GradualDisappearance());
        instances.add(new NewStudentCouncil());
        //instances.add(new StrikePose());
        instances.add(new WhatDoesSheDo());
        instances.add(new MyJudgeNotWrong());
        instances.add(new Grip());
        instances.add(new WarmCare());
        instances.add(new HardStretching());
        instances.add(new MadeOneForYou());
        instances.add(new AngelAndDemon());
        instances.add(new JustAngel());
        instances.add(new JustDemon());
        instances.add(new TopWisdom());
        instances.add(new ClearUp());
        instances.add(new EnjoySummer());
        instances.add(new NotLeftAnyone());
        instances.add(new TemporaryTruce());
        instances.add(new WantToGoThere());
        instances.add(new EatEmptyYourRefrigerator());
        instances.add(new SSDSecret());
        instances.add(new FullSupport());
        instances.add(new BlossomingSeason());
        instances.add(new Alternatives());
        instances.add(new GachaAgain());

        instances.add(new IsENotA());
        instances.add(new AutumnOfAppetite());

        instances.add(new YoungDream());
        instances.add(new FutureTrajectory());
        instances.add(new GrowthProcess());

        instances.add(new SaySomethingToYou());
        instances.add(new BlueSenpaiHelp());

        instances.add(new CallMeAnyTime());
        instances.add(new ListenToMyTrueHeart());
        instances.add(new ReallyNotBad());

        instances.add(new DanceWithYou());
        instances.add(new OnePersonBallet());

        instances.add(new MotherAI());
        instances.add(new TrueLateBloomer());

        instances.add(new EvenIfDreamNotRealize());
        instances.add(new LetMeBeYourDream());
        instances.add(new NecessaryContrast());

        instances.add(new SpecialTreasure());
        instances.add(new SlowGrowth());

        instances.add(new WorldFirstCute());
        instances.add(new TenThousandVolts());

        instances.add(new LoveMyself());
        instances.add(new LoveMyselfCool());
        instances.add(new LoveMyselfCute());
        instances.add(new FightSkills());
        instances.add(new DefensiveSkills());
        instances.add(new CreateYourStyle());

        instances.add(new SisterHelp());
        instances.add(new OverwhelmingNumbers());
        instances.add(new GodArrival());

        instances.add(new NotLimit());
        instances.add(new ProduceCompetitor());

        instances.add(new Intensely());
        instances.add(new BaseGreeting());
        instances.add(new FinalSpurt());
        instances.add(new BaseMental());
        instances.add(new BaseImage());
        instances.add(new JustAppeal());
        instances.add(new Starlight());
        instances.add(new OneStep());
        instances.add(new Lucky());
        instances.add(new StepByStep());
        instances.add(new DoYourBest());
        instances.add(new Happy());
        instances.add(new SurpriseMiss());
        instances.add(new TearfulMemories());
        instances.add(new ReadyToGo());
        instances.add(new AccelerateLand());
        instances.add(new BurstingPassion());
        instances.add(new SweatAndGrowth());
        instances.add(new OpeningAct());
        instances.add(new StartSmile());
        instances.add(new IdealTempo());
        instances.add(new TrainingResult());
        instances.add(new PotentialAbility());
        instances.add(new Countdown());
        instances.add(new Pride());
        instances.add(new Endurance());
        instances.add(new BattlePractice());
        instances.add(new TakeFlight());
        instances.add(new ComprehensiveArt());
        instances.add(new MindSkillBody());
        instances.add(new ShineBright());
        instances.add(new Climax());
        instances.add(new HeartAndSoul());
        instances.add(new BecomeIdol());
        instances.add(new ToTheTop());
        instances.add(new Determination());
        instances.add(new MiracleMagic());
        instances.add(new Excite());
        instances.add(new SparklingConfetti());
        instances.add(new DiggingTalent());
        instances.add(new PacificSaury());
        instances.add(new IrresistibleClassmate());
        instances.add(new MyPaceMyLife());
        instances.add(new CatchingDragonflies());
        instances.add(new EverythingIsReady());
        instances.add(new ProducingIsChallenging());
        instances.add(new MayRain());
        instances.add(new ThunderWillStop());
        instances.add(new BeyondTheSky());
        instances.add(new StepOnStage());
        instances.add(new LikeStars());
        instances.add(new StarPicking());
        instances.add(new StarPickingConcentration());
        instances.add(new StarPickingPreservation());
        instances.add(new WhereDreamsAre());
        instances.add(new HoldBack());
        instances.add(new Unpredictable());
        instances.add(new LikeUsual());
        instances.add(new EyesOfTheScenery());
        instances.add(new WarmMemories());
        instances.add(new AllMembersGather());
        instances.add(new AllMembersGatherLogic());
        instances.add(new AllMembersGatherSense());
        instances.add(new DoYouLikeIt());
        instances.add(new Resilience());

        instances.add(new StartingLine());
        instances.add(new TimeLoop());
        instances.add(new OurThreeYear());

        instances.add(new BasePace());
        instances.add(new YawnAttack());
        instances.add(new Stress());
        instances.add(new SleepWall());
        instances.add(new SleepyForever());
        instances.add(new SleepyTogether());
        instances.add(new SunshineIsJustRight());
        instances.add(new SleepyKiller());
        instances.add(new SleepyEnchantment());
        instances.add(new SleepyInfection());
        instances.add(new SleepyReflex());
        instances.add(new ShockWakeUp());
        instances.add(new AttackRush());
        instances.add(new PillowBlock());
        instances.add(new LittleTired());
        instances.add(new Assimilation());
        instances.add(new TeachRecipe());
        instances.add(new MorningFour());
        instances.add(new SleepySong());
        instances.add(new RechargeBattery());
        instances.add(new SleepyLullaby());
        instances.add(new Materialization());
        instances.add(new SleeplessNight());
        instances.add(new GoToDream());
        instances.add(new DarknessForever());
        instances.add(new AnotherDream());
        instances.add(new Nightmare());
        instances.add(new SleepyCardPower());
        instances.add(new SpringSleepy());
        instances.add(new SummerSleepy());
        instances.add(new AutumnSleepy());
        instances.add(new WinterSleepy());
        instances.add(new MyStep());
        instances.add(new SmallHappiness());
        instances.add(new WingsOfDream());
        instances.add(new WindChimeWish());
        instances.add(new DreamPlace());
        instances.add(new TotalAttack());
        instances.add(new IWillNotSpareYou());
        instances.add(new WakeUpMisuzu());
        instances.add(new FateCommunity());
        instances.add(new EvilSource());
        instances.add(new FirstWantToRun());
        instances.add(new CoolIdol());
        instances.add(new LunaSay());
        instances.add(new ThatTimeOurs());
        instances.add(new ConfusedThings());
        instances.add(new DoAsYouPlease());
        instances.add(new OurSun());
        instances.add(new WakeUpTime());
        instances.add(new WantToRun());
        instances.add(new PromiseInMoon());
        instances.add(new NewFriends());
        instances.add(new LittleLight());

        instances.add(new ProducerTrumpCard());
        instances.add(new GakuenLinkMaster());
        instances.add(new Rumor());
        instances.add(new InformationMaster());
        instances.add(new CatchNunu());
        instances.add(new FightWill());
        instances.add(new PracticeAgain());
        instances.add(new BloodySacrifice());
        instances.add(new SSC());
        instances.add(new RedLeaf());
        instances.add(new ResultWillNotChange());
        instances.add(new Kiss());
        instances.add(new WorkFighter());
        instances.add(new BentoForProducer());
        instances.add(new ChocolateForProducer());
        instances.add(new DoNotGo());

        // 遍历instances的所有元素，将其添加到listener中
        for (Object instance : instances) {
            listeners.add((CardImgUpdateListener) instance);
        }
        return instances;
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new IdolCharacter(CardCrawlGame.playerName), MY_CHARACTER_BUTTON, MARISA_PORTRAIT, gkmasMod_character);
        BaseMod.addCharacter(new MisuzuCharacter(CardCrawlGame.playerName), MY_CHARACTER_BUTTON2, MARISA_PORTRAIT2, gkmasModMisuzu_character);
    }

    @Override
    public void receiveEditRelics() {
        //List<Object> instances = new ArrayList<>();

        BaseMod.addRelicToCustomPool(new PocketBook(), gkmasModColor);

        // 广
        BaseMod.addRelicToCustomPool(new UltimateSleepMask(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FirstLoveProofHiro(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new BeginnerGuideForEveryone(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new SidewalkResearchNotes(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new TowardsAnUnseenWorld(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new NaughtyPuppet(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new SelectedPassion(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new TheMarkOfPractice(), gkmasModColor);
        // 莉莉娅
        BaseMod.addRelicToCustomPool(new GreenUniformBracelet(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FirstHeartProofLilja(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new MemoryBot(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new DreamLifeLog(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new SparklingInTheBottle(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new BeyondTheSea(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new TheDreamSawSomeday(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new SmallGalaxy(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new ContinuousEffort(), gkmasModColor);
        // 千奈
        BaseMod.addRelicToCustomPool(new WishFulfillmentAmulet(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FirstHeartProofChina(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new CheerfulHandkerchief(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new SecretTrainingCardigan(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new HeartFlutteringCup(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new EnjoyAfterHotSpring(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new CursedSpirit(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new DreamOfTheDescription(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new SpringIsComing(), gkmasModColor);
        // 佑芽
        BaseMod.addRelicToCustomPool(new TechnoDog(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new ShibaInuPochette(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new RollingSourceOfEnergy(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new ChristmasLion(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new AchieveDreamAwakening(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new GiftForYou(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new ProofOfFriendship(), gkmasModColor);
        // 咲季
        BaseMod.addRelicToCustomPool(new RoaringLion(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FirstVoiceProofSaki(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new SakiCompleteMealRecipe(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new TogetherInBattleTowel(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new WinningDetermination(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new UndefeatedPoi(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new AnimateEquipment(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FaceTheAwakening(), gkmasModColor);
        // 莉波
        BaseMod.addRelicToCustomPool(new RegularMakeupPouch(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FirstHeartProofRinami(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new TreatForYou(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new LifeSizeLadyLip(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new ItsPerfect(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new SummerToShareWithYou(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new ClapClapFan(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new YouFindMe(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new SweetTaste(), gkmasModColor);
        // 琴音
        BaseMod.addRelicToCustomPool(new HandmadeMedal(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FirstVoiceProofKotone(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FavoriteSneakers(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new PigDreamPiggyBank(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new UltimateSourceOfHappiness(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new CracklingSparkler(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FreeLoveMax(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new OnlyMyFirstStar(), gkmasModColor);
        // 麻央
        BaseMod.addRelicToCustomPool(new GentlemanHandkerchief(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FirstLoveProofMao(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new DearLittlePrince(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new InnerLightEarrings(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new LastSummerMemory(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FashionMode(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new MyPart(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new RoundSpring(), gkmasModColor);
        // 清夏
        BaseMod.addRelicToCustomPool(new PinkUniformBracelet(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FirstLoveProofSumika(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new AfterSchoolDoodles(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new ArcadeLoot(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FrogFan(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new PlasticUmbrellaThatDay(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new TheWing(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new TheP(), gkmasModColor);
        // 手毬
        BaseMod.addRelicToCustomPool(new EssentialStainlessSteelBottle(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FirstVoiceProofTemari(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new MyFirstSheetMusic(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new ProtectiveEarphones(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new ThisIsMe(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new ClumsyBat(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new SongToTheSun(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new CardOfVictory(), gkmasModColor);
        // 星南
        BaseMod.addRelicToCustomPool(new TeaCaddy(),gkmasModColor);
        BaseMod.addRelicToCustomPool(new EveryoneDream(),gkmasModColor);
        BaseMod.addRelicToCustomPool(new AbsoluteNewSelf(),gkmasModColor);
        BaseMod.addRelicToCustomPool(new PastLittleStar(),gkmasModColor);
        BaseMod.addRelicToCustomPool(new DeepLoveForYou(),gkmasModColor);

        BaseMod.addRelicToCustomPool(new SyngUpRelic(), gkmasModColorMisuzu);
        BaseMod.addRelicToCustomPool(new SyngUpRelicBroken(), gkmasModColorMisuzu);
        BaseMod.addRelicToCustomPool(new FriendChinaRelic(), gkmasModColorMisuzu);
        BaseMod.addRelicToCustomPool(new FriendHiroRelic(), gkmasModColorMisuzu);
        BaseMod.addRelicToCustomPool(new FriendUmeRelic(), gkmasModColorMisuzu);
        BaseMod.addRelicToCustomPool(new FriendKotoneRelic(), gkmasModColorMisuzu);
        BaseMod.addRelicToCustomPool(new FriendSenaRelic(), gkmasModColorMisuzu);
        BaseMod.addRelicToCustomPool(new MisuzuNatureRelic(), gkmasModColorMisuzu);



        BaseMod.addRelicToCustomPool(new BalanceLogicAndSense(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FirstStarBracelet(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new MysteriousObject(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new OverpoweredBall(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new PledgePetal(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new AmakawaRamenTour(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new UnofficialMascot(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FirstStarNotebook(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FirstStarTshirt(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FirstStarKeychain(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new CrackedCoreNew(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new ProducerGlass(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new ProducerPhone(), gkmasModColor);

        BaseMod.addRelicToCustomPool(new StruggleRecord(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new MasterGirding(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new MasterHighHeels(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new MasterVest(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new MasterSupportFrame(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new MasterRoller(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new MasterBall(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new MasterTreadmill(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new MasterDumbbell(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FreeTicket(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new EveryoneTextbook(), gkmasModColor);
//        BaseMod.addRelicToCustomPool(new ReChallenge(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new IdolNoSoul(),gkmasModColor);

        BaseMod.addRelicToCustomPool(new FirstStarClock(),gkmasModColor);
        BaseMod.addRelicToCustomPool(new FirstStarModel(),gkmasModColor);

        BaseMod.addRelicToCustomPool(new FoodCarrot(),gkmasModColor);
        BaseMod.addRelicToCustomPool(new FoodGinger(),gkmasModColor);
        BaseMod.addRelicToCustomPool(new FoodHuhu(),gkmasModColor);
        BaseMod.addRelicToCustomPool(new FoodIris(),gkmasModColor);
        BaseMod.addRelicToCustomPool(new FoodOddMushroom(),gkmasModColor);
        BaseMod.addRelicToCustomPool(new FoodTonkatsu(),gkmasModColor);
        BaseMod.addRelicToCustomPool(new FoodTonkatsuSP(),gkmasModColor);

        BaseMod.addRelicToCustomPool(new NIABadge(),gkmasModColor);

    }

    private void addPotions(){
        BaseMod.addPotion(BoostExtract.class,BoostExtract.liquidColor,BoostExtract.hybridColor,BoostExtract.spotsColor,BoostExtract.ID, gkmasMod_character);
        BaseMod.addPotion(FirstStarSpecialAojiru.class,FirstStarSpecialAojiru.liquidColor,FirstStarSpecialAojiru.hybridColor,FirstStarSpecialAojiru.spotsColor,FirstStarSpecialAojiru.ID,gkmasMod_character);
        BaseMod.addPotion(FirstStarWater.class,FirstStarWater.liquidColor,FirstStarWater.hybridColor,FirstStarWater.spotsColor,FirstStarWater.ID,gkmasMod_character);
        BaseMod.addPotion(FirstStarWheyProtein.class,FirstStarWheyProtein.liquidColor,FirstStarWheyProtein.hybridColor,FirstStarWheyProtein.spotsColor,FirstStarWheyProtein.ID,gkmasMod_character);
        BaseMod.addPotion(FreshVinegar.class,FreshVinegar.liquidColor,FreshVinegar.hybridColor,FreshVinegar.spotsColor,FreshVinegar.ID,gkmasMod_character);
        //BaseMod.addPotion(HotCoffee.class,HotCoffee.liquidColor,HotCoffee.hybridColor,HotCoffee.spotsColor,HotCoffee.ID);
        //BaseMod.addPotion(IcedCoffee.class,IcedCoffee.liquidColor,IcedCoffee.hybridColor,IcedCoffee.spotsColor,IcedCoffee.ID);
        BaseMod.addPotion(MixedSmoothie.class,MixedSmoothie.liquidColor,MixedSmoothie.hybridColor,MixedSmoothie.spotsColor,MixedSmoothie.ID,gkmasMod_character);
        BaseMod.addPotion(OolongTea.class,OolongTea.liquidColor,OolongTea.hybridColor,OolongTea.spotsColor,OolongTea.ID,gkmasMod_character);
        BaseMod.addPotion(RecoveryDrink.class,RecoveryDrink.liquidColor,RecoveryDrink.hybridColor,RecoveryDrink.spotsColor,RecoveryDrink.ID,gkmasMod_character);
        //BaseMod.addPotion(RooibosTea.class,RooibosTea.liquidColor,RooibosTea.hybridColor,RooibosTea.spotsColor,RooibosTea.ID);
        //BaseMod.addPotion(SelectFirstStarBlend.class,SelectFirstStarBlend.liquidColor,SelectFirstStarBlend.hybridColor,SelectFirstStarBlend.spotsColor,SelectFirstStarBlend.ID);
        //BaseMod.addPotion(SelectFirstStarMacchiato.class,SelectFirstStarMacchiato.liquidColor,SelectFirstStarMacchiato.hybridColor,SelectFirstStarMacchiato.spotsColor,SelectFirstStarMacchiato.ID);
        //BaseMod.addPotion(SelectFirstStarTea.class,SelectFirstStarTea.liquidColor,SelectFirstStarTea.hybridColor,SelectFirstStarTea.spotsColor,SelectFirstStarTea.ID);
        //BaseMod.addPotion(SpecialFirstStarExtract.class,SpecialFirstStarExtract.liquidColor,SpecialFirstStarExtract.hybridColor,SpecialFirstStarExtract.spotsColor,SpecialFirstStarExtract.ID);
        //BaseMod.addPotion(StaminaExplosionDrink.class,StaminaExplosionDrink.liquidColor,StaminaExplosionDrink.hybridColor,StaminaExplosionDrink.spotsColor,StaminaExplosionDrink.ID);
        //BaseMod.addPotion(StylishHerbalTea.class,StylishHerbalTea.liquidColor,StylishHerbalTea.hybridColor,StylishHerbalTea.spotsColor,StylishHerbalTea.ID);
        //BaseMod.addPotion(VitaminDrink.class,VitaminDrink.liquidColor,VitaminDrink.hybridColor,VitaminDrink.spotsColor,VitaminDrink.ID);
        //BaseMod.addPotion(FirstStarBoostEnergy.class,FirstStarBoostEnergy.liquidColor, FirstStarBoostEnergy.hybridColor,FirstStarBoostEnergy.spotsColor,FirstStarBoostEnergy.ID);
        BaseMod.addPotion(PowerfulHerbalDrink.class,PowerfulHerbalDrink.liquidColor,PowerfulHerbalDrink.hybridColor,PowerfulHerbalDrink.spotsColor,PowerfulHerbalDrink.ID,gkmasMod_character);
        BaseMod.addPotion(FirstStarSpecialSPAojiru.class,FirstStarSpecialSPAojiru.liquidColor,FirstStarSpecialSPAojiru.hybridColor,FirstStarSpecialSPAojiru.spotsColor,FirstStarSpecialSPAojiru.ID,gkmasMod_character);
        BaseMod.addPotion(SenburiSoda.class,SenburiSoda.liquidColor,SenburiSoda.hybridColor,SenburiSoda.spotsColor,SenburiSoda.ID,gkmasMod_character);

    }

    @Override
    public void receivePostInitialize() {
        SkinSelectScreen.Inst.flag = true;
        SkinSelectScreen.Inst.specialCard = CardLibrary.getCard(gkmasMod_character, IdolData.getIdol(SkinSelectScreen.Inst.idolName).getCard(SkinSelectScreen.Inst.skinIndex)).makeCopy();
        BaseMod.addCustomScreen(new PocketBookViewScreen());
        BaseMod.addCustomScreen(new AnotherShopScreen());
        BaseMod.addCustomScreen(new SpecialTeachScreen());

        BaseMod.addEvent(new AddEventParams.Builder(TogetherTrain.ID,TogetherTrain.class).spawnCondition(ConditionHelper.Condition_ttmr).dungeonID(Exordium.ID).create());
        BaseMod.addEvent(new AddEventParams.Builder(KakaSong.ID,KakaSong.class).playerClass(gkmasMod_character).dungeonID(TheCity.ID).dungeonID(TheBeyond.ID).create());
        BaseMod.addEvent(new AddEventParams.Builder(HajimeReward.ID,HajimeReward.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(NIAReward.ID,NIAReward.class).spawnCondition(ConditionHelper.Condition_never).create());

        BaseMod.addEvent(new AddEventParams.Builder(Plan_shro1.ID,Plan_shro1.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_shro2.ID,Plan_shro2.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_shro3.ID,Plan_shro3.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_kllj1.ID,Plan_kllj1.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_kllj2.ID,Plan_kllj2.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_kllj3.ID,Plan_kllj3.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_kcna1.ID,Plan_kcna1.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_kcna2.ID,Plan_kcna2.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_kcna3.ID,Plan_kcna3.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_hume1.ID,Plan_hume1.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_hume2.ID,Plan_hume2.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_hume3.ID,Plan_hume3.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_hski1.ID,Plan_hski1.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_hski2.ID,Plan_hski2.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_hski3.ID,Plan_hski3.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_hrnm1.ID,Plan_hrnm1.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_hrnm2.ID,Plan_hrnm2.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_hrnm3.ID,Plan_hrnm3.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_fktn1.ID,Plan_fktn1.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_fktn2.ID,Plan_fktn2.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_fktn3.ID,Plan_fktn3.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_amao1.ID,Plan_amao1.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_amao2.ID,Plan_amao2.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_amao3.ID,Plan_amao3.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_ssmk1.ID,Plan_ssmk1.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_ssmk2.ID,Plan_ssmk2.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_ssmk3.ID,Plan_ssmk3.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_ttmr1.ID,Plan_ttmr1.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_ttmr2.ID,Plan_ttmr2.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_ttmr3.ID,Plan_ttmr3.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_jsna1.ID,Plan_jsna1.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_jsna2.ID,Plan_jsna2.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Plan_jsna3.ID,Plan_jsna3.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(MasterEvent1.ID,MasterEvent1.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(MasterEvent2.ID,MasterEvent2.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(MasterEvent3.ID,MasterEvent3.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(Live_jsna.ID,Live_jsna.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(NewTrans.ID,NewTrans.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(FirstStarHotSpring.ID,FirstStarHotSpring.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(HappyNewYear.ID,HappyNewYear.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(SupplyEvent.ID,SupplyEvent.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(SyngUpEvent1.ID,SyngUpEvent1.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(SyngUpEvent2.ID,SyngUpEvent2.class).spawnCondition(ConditionHelper.Condition_never).create());
        BaseMod.addEvent(new AddEventParams.Builder(SelectBossEvent.ID,SelectBossEvent.class).spawnCondition(ConditionHelper.Condition_never).create());

        BaseMod.addMonster(MisuzuBoss.ID, () -> new MisuzuBoss());
        BaseMod.addMonster(MonsterNadeshiko.ID, () -> new MonsterNadeshiko());
        BaseMod.addMonster(MonsterShion.ID, () -> new MonsterShion());
        BaseMod.addMonster(MonsterAsari.ID, () -> new MonsterAsari());
        BaseMod.addMonster(MonsterGekka.ID, () -> new MonsterGekka());
        BaseMod.addMonster(MonsterRinha.ID, () -> new MonsterRinha());
//        BaseMod.addMonster(HajimeBoss.ID, () -> new HajimeBoss());
        BaseMod.addMonster(IdolBoss_hski.ID, () -> new IdolBoss_hski());
        BaseMod.addMonster(IdolBoss_hume.ID, () -> new IdolBoss_hume());
        BaseMod.addMonster(IdolBoss_shro.ID, () -> new IdolBoss_shro());
        BaseMod.addMonster(IdolBoss_hrnm.ID, () -> new IdolBoss_hrnm());
        BaseMod.addMonster(IdolBoss_fktn.ID, () -> new IdolBoss_fktn());
        BaseMod.addMonster(IdolBoss_amao.ID, () -> new IdolBoss_amao());
        BaseMod.addMonster(IdolBoss_ssmk.ID, () -> new IdolBoss_ssmk());
        BaseMod.addMonster(IdolBoss_ttmr.ID, () -> new IdolBoss_ttmr());
        BaseMod.addMonster(IdolBoss_kcna.ID, () -> new IdolBoss_kcna());
        BaseMod.addMonster(IdolBoss_kllj.ID, () -> new IdolBoss_kllj());
        BaseMod.addMonster(IdolBoss_jsna.ID, () -> new IdolBoss_jsna());
        BaseMod.addMonster(MonsterSena2.ID, () -> new MonsterSena2());
        BaseMod.addMonster("reiris", () -> {
            AbstractMonster[] Reiris = new AbstractMonster[3];
            Reiris[0] = new MonsterTemari1(-250.0F, 20.0F);
            Reiris[1] = new MonsterSaki1(000.0F, 20.0F);
            Reiris[2] = new MonsterKotone1(250.0F, 20.0F);
            return new MonsterGroup(Reiris);
        });
        BaseMod.addMonster("begrazia", () -> {
            AbstractMonster[] Begrazia = new AbstractMonster[3];
            Begrazia[0] = new MonsterMisuzu1(-250.0F, 20.0F);
            Begrazia[1] = new MonsterUme1(000.0F, 20.0F);
            Begrazia[2] = new MonsterSena1(250.0F, 20.0F);
            return new MonsterGroup(Begrazia);
        });

        shopScreen = new AnotherShopScreen();

        BaseMod.addBoss(Gokugetsu.id, MonsterNadeshiko.ID, "gkmasModResource/img/monsters/Nadeshiko/icon.png", "gkmasModResource/img/monsters/Nadeshiko/icon.png");
        BaseMod.addBoss(Gokugetsu.id, MonsterShion.ID, "gkmasModResource/img/monsters/Shion/icon.png", "gkmasModResource/img/monsters/Shion/icon.png");
        BaseMod.addBoss(Gokugetsu.id, MonsterGekka.ID, "gkmasModResource/img/monsters/Gekka/icon.png", "gkmasModResource/img/monsters/Gekka/icon.png");
        BaseMod.addBoss(Gokugetsu.id, MonsterRinha.ID, "gkmasModResource/img/monsters/Rinha/icon.png", "gkmasModResource/img/monsters/Rinha/icon.png");
        BaseMod.addBoss(Gokugetsu.id, MonsterAsari.ID, "gkmasModResource/img/monsters/Asari/icon.png", "gkmasModResource/img/monsters/Asari/icon.png");
        BaseMod.addBoss(Gokugetsu.id, "reiris", "gkmasModResource/img/monsters/Idol/reiris_icon.png", "gkmasModResource/img/monsters/Idol/reiris_icon.png");
        BaseMod.addBoss(Gokugetsu.id, "begrazia", "gkmasModResource/img/monsters/Idol/begrazia_icon.png", "gkmasModResource/img/monsters/Idol/begrazia_icon.png");

        //BaseMod.addBoss(Exordium.ID, HajimeBoss.ID, "gkmasModResource/img/monsters/Hajime/icon.png", "gkmasModResource/img/monsters/Hajime/icon.png");

//        BaseMod.addBoss(TheEnding.ID, MisuzuBoss.ID, "gkmasModResource/img/monster/Misuzu/icon.png", "gkmasModResource/img/monster/Misuzu/icon.png");

        CustomDungeon.addAct(4, new IdolRoad());

        CardCrawlGame.languagePack.getEventString(ActLikeIt.makeID("ForkInTheRoad")).OPTIONS[4]="挑战美铃或燐羽（这里有两个未实装的小孩）";

        if(CardCrawlGame.languagePack.getEventString(ActLikeIt.makeID("ForkInTheRoad")).DESCRIPTIONS.length>6){
            CardCrawlGame.languagePack.getEventString(ActLikeIt.makeID("ForkInTheRoad")).DESCRIPTIONS[6]="你的旅途即将进入下一阶段 NL 选择你接下来的 #b计划 ";
        }

        CardCrawlGame.languagePack.getEventString(ActLikeIt.makeID("ForkInTheRoad")).DESCRIPTIONS[3]="趁着没人来，先睡一会吧……";

        try {
            // 设置默认值
            Properties defaults = new Properties();
            defaults.setProperty("cardRate", String.valueOf(PlayerHelper.getCardRate()));
            defaults.setProperty("beat_hmsz", "0");
            defaults.setProperty("onlyModBoss", Boolean.toString(true));
            defaults.setProperty("ReChallenge", "0");
            defaults.setProperty("hasNewYear", Boolean.toString(false));
            config = new SpireConfig("GkmasMod", "config", defaults);
            config.load();
            // 读取配置
            cardRate = config.getFloat("cardRate");
            beat_hmsz = config.getInt("beat_hmsz");
            onlyModBoss = config.getBool("onlyModBoss");
            hasNewYear = config.getBool("hasNewYear");
            ModPanel panel = new ModPanel();
            BaseMod.registerModBadge(new Texture("gkmasModResource/img/UI/badge.png"), "GkmasMod", "虚怀盈风", "学园偶像大师Mod", panel);
            ModLabeledToggleButton onlyModBossButton = new ModLabeledToggleButton("优先出现Mod Boss", 400.0F, 500.0F, Color.WHITE, FontHelper.buttonLabelFont, onlyModBoss, panel, l -> {}, GkmasMod::onlyModBossTrigger);
            panel.addUIElement(onlyModBossButton);
            if(beat_hmsz>0)
                SkinSelectScreen.Inst.specialCard.upgrade();
//            config.save();
        } catch (IOException var2) {
            var2.printStackTrace();
        }
        addPotions();

        AbstractPlayer idol =  CardCrawlGame.characterManager.recreateCharacter(gkmasMod_character);
        Prefs pref = idol.getPrefs();
        int idolMaxAscensionLevel= 1;
        if (pref != null) {
            idolMaxAscensionLevel = pref.getInteger("ASCENSION_LEVEL", 1);
        }
        AbstractPlayer misuzuPlayer = CardCrawlGame.characterManager.recreateCharacter(gkmasModMisuzu_character);
        pref = misuzuPlayer.getPrefs();
        if (pref.getInteger("WIN_COUNT", 0) == 0)
            pref.putInteger("WIN_COUNT", 1);
        int misuzuMaxAscensionLevel = pref.getInteger("ASCENSION_LEVEL", 1);
        if (misuzuMaxAscensionLevel < idolMaxAscensionLevel){
            pref.putInteger("ASCENSION_LEVEL", idolMaxAscensionLevel);
            pref.putInteger("LAST_ASCENSION_LEVEL", idolMaxAscensionLevel);
        }
        pref.flush();
    }

    private static void onlyModBossTrigger(ModToggleButton button) {
        onlyModBoss = button.enabled;
        try {
            config.setBool("onlyModBoss", onlyModBoss);
            config.save();
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
//        if(abstractRoom instanceof FixedMonsterRoom){
//            FixedMonsterRoom room = (FixedMonsterRoom)abstractRoom;
//            room.rewards.clear();
//            String tmp = room.encounterID;
//            if(tmp.startsWith("IdolBoss_")){
//                tmp = tmp.substring(9);
//            }
//            ArrayList<String> cards = IdolData.getIdol(tmp).getCardList();
//            //从cards中随机选取3张卡牌,需要考虑不足3张的情况
//            ArrayList<String> cardList = new ArrayList<>();
//            java.util.Random random = new java.util.Random(Settings.seed);
//            for(int i=0;i<3;i++){
//                if(cards.size()>0){
//                    int index = random.nextInt(cards.size());
//                    cardList.add(cards.get(index));
//                    cards.remove(index);
//                }
//            }
//            room.addCardReward(cardList);
//        }
        }

    @Override
    public void receiveStartGame() {
        screenIndex = 0;
        try {
            config.setFloat("cardRate",PlayerHelper.getCardRate());
            config.setInt("beat_hmsz",GkmasMod.beat_hmsz);
            config.setBool("onlyModBoss", GkmasMod.onlyModBoss);
            config.setBool("hasNewYear", hasNewYear);
            config.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void receivePostCreateStartingDeck(AbstractPlayer.PlayerClass playerClass, CardGroup cardGroup) {
        AnotherShopScreen.purgeTimes = 0;
        hasNewYear = false;
        if(playerClass == gkmasMod_character){
            String videoPath = "gkmasModResource/video/other/op1.webm";
            String[] videoList = {
                    "gkmasModResource/video/other/op1.webm",
                    "gkmasModResource/video/other/op2.webm",
            };
            java.util.Random random = new java.util.Random();
            int index = random.nextInt(videoList.length);
            videoPath = videoList[index];

//        AbstractDungeon.isScreenUp = true;
            CardCrawlGame.fadeIn(1.0F);
            CardCrawlGame.music.dispose();
            if(Gdx.files.local(videoPath).exists()){
                AbstractDungeon.topLevelEffectsQueue.add(new SimplePlayVideoEffect2(videoPath,true));
            }
        }

    }

    @Override
    public void receivePostRender(SpriteBatch spriteBatch) {
        if(restart){
            restart = false;
            RestartHelper.restartRoom();
        }
    }
}
