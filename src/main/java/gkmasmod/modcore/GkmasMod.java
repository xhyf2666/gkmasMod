package gkmasmod.modcore;

import basemod.BaseMod;
import basemod.eventUtil.AddEventParams;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
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
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.free.*;
import gkmasmod.cards.logic.*;
import gkmasmod.cards.sense.*;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.event.*;
import gkmasmod.monster.ending.MisuzuBoss;
import gkmasmod.monster.exordium.HajimeBoss;
import gkmasmod.potion.*;
import gkmasmod.relics.*;
import gkmasmod.room.shop.AnotherShopScreen;
import gkmasmod.screen.PocketBookViewScreen;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.ConditionHelper;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.PlayerHelper;
import gkmasmod.variables.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static gkmasmod.characters.PlayerColorEnum.*;

@SpireInitializer
public class GkmasMod implements EditCardsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber, EditCharactersSubscriber, AddAudioSubscriber,EditRelicsSubscriber,PostInitializeSubscriber ,PostBattleSubscriber {

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
    private static final String MARISA_PORTRAIT = "gkmasModResource/img/charSelect/background_init.png";
    public static final Color gkmasMod_color = CardHelper.getColor(80, 80, 50);

    public static final Color gkmasMod_colorLogic = CardHelper.getColor(50, 70, 200);

    public static final Color gkmasMod_colorSense = CardHelper.getColor(200, 150, 100);
    public static boolean limitedSLOption;
    private ArrayList<AbstractCard> cardsToAdd = new ArrayList<>();
    public static ArrayList<AbstractCard> recyclecards = new ArrayList<>();
    private static SpireConfig config;
    public static final String MOD_NAME = "gkmasMod";
    public static int beat_hmsz = 0;
    public static float cardRate = 0.0F;
    public static AnotherShopScreen shopScreen;
    public static MapRoomNode node=null;

    public static boolean AnotherShopUp = false;

    public static Random threeSizeTagRng = new Random(0L);


    public GkmasMod(){
        BaseMod.subscribe(this);
        String[] suffix = new String[]{"","logic_","sense_"};
        addColor(gkmasModColor,gkmasMod_color,idolName,suffix[0]);
        addColor(gkmasModColorLogic,gkmasMod_colorLogic,idolName,suffix[1]);
        addColor(gkmasModColorSense,gkmasMod_colorSense,idolName,suffix[2]);
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
            lang = "en";
        }
        BaseMod.loadCustomStringsFile(CardStrings.class, "gkmasModResource/localization/gkmas_cards_"+lang+".json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "gkmasModResource/localization/gkmas_powers_" + lang + ".json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, "gkmasModResource/localization/gkmas_characters_" + lang + ".json");
        BaseMod.loadCustomStringsFile(UIStrings.class, "gkmasModResource/localization/gkmas_ui_" + lang + ".json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, "gkmasModResource/localization/gkmas_relics_" + lang + ".json");
        BaseMod.loadCustomStringsFile(MonsterStrings.class, "gkmasModResource/localization/gkmas_monsters_" + lang + ".json");
        BaseMod.loadCustomStringsFile(EventStrings.class, "gkmasModResource/localization/gkmas_events_" + lang + ".json");
        BaseMod.loadCustomStringsFile(PotionStrings.class, "gkmasModResource/localization/gkmas_potions_" + lang + ".json");

    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String lang = "eng";
        if (Settings.language == Settings.GameLanguage.ZHS) {
            lang = "chs";
        }

        String json = Gdx.files.internal("gkmasModResource/localization/gkmas_keywords_" + lang + ".json")
                .readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
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
        // 莉莉娅
        instances.add(new ReservedGirl());
        instances.add(new FirstGround());
        instances.add(new PureWhiteFairy());
        instances.add(new NotAfraidAnymore());
        instances.add(new FirstRamune());
        // 千奈
        instances.add(new FullOfEnergy());
        instances.add(new FirstColor());
        instances.add(new Wholeheartedly());
        instances.add(new DebutStageForTheLady());
        instances.add(new WelcomeToTeaParty());
        instances.add(new AQuickSip());
        instances.add(new GonnaTrickYou());

        // 佑芽
        instances.add(new UntappedPotential());
        instances.add(new DefeatBigSister());
        instances.add(new BigOnigiri());
        // 咲季
        instances.add(new RisingStar());
        instances.add(new FirstFuture());
        instances.add(new NeverYieldFirst());
        instances.add(new NeverLose());
        instances.add(new Pow());
        instances.add(new GoldfishScoopingChallenge());
        // 莉波
        instances.add(new Accommodating());
        instances.add(new FirstFriend());
        instances.add(new SupportiveFeelings());
        instances.add(new SenseOfDistance());
        instances.add(new SeeYouTomorrow());
        instances.add(new CumulusCloudsAndYou());
        instances.add(new RefreshingBreak());
        // 琴音
        instances.add(new Arbeiter());
        instances.add(new FirstReward());
        instances.add(new ColorfulCute());
        instances.add(new NoDistractions());
        instances.add(new FullAdrenaline());
        instances.add(new SummerEveningSparklers());
        // 麻央
        instances.add(new LittlePrince());
        instances.add(new FirstCrystal());
        instances.add(new Authenticity());
        instances.add(new DressedUpInStyle());
        instances.add(new ChillyBreak());
        instances.add(new MoonlitRunway());
        // 清夏
        instances.add(new Friendly());
        instances.add(new FirstMiss());
        instances.add(new BraveStep());
        instances.add(new OneMoreStep());
        instances.add(new AfternoonBreeze());
        // 手毬
        instances.add(new Stubborn());
        instances.add(new FirstPlace());
        instances.add(new LoneWolf());
        instances.add(new EachPath());
        instances.add(new TangledFeelings());
        instances.add(new StruggleHandmade());

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

        // 遍历instances的所有元素，将其添加到listener中
        for (Object instance : instances) {
            listeners.add((CardImgUpdateListener) instance);
        }
        return instances;
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new IdolCharacter(CardCrawlGame.playerName), MY_CHARACTER_BUTTON, MARISA_PORTRAIT, gkmasMod_character);
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
        // 莉莉娅
        BaseMod.addRelicToCustomPool(new GreenUniformBracelet(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FirstHeartProofLilja(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new MemoryBot(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new DreamLifeLog(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new SparklingInTheBottle(), gkmasModColor);
        // 千奈
        BaseMod.addRelicToCustomPool(new WishFulfillmentAmulet(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FirstHeartProofChina(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new CheerfulHandkerchief(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new SecretTrainingCardigan(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new HeartFlutteringCup(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new EnjoyAfterHotSpring(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new CursedSpirit(), gkmasModColor);
        // 佑芽
        BaseMod.addRelicToCustomPool(new TechnoDog(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new ShibaInuPochette(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new RollingSourceOfEnergy(), gkmasModColor);
        // 咲季
        BaseMod.addRelicToCustomPool(new RoaringLion(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FirstVoiceProofSaki(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new SakiCompleteMealRecipe(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new TogetherInBattleTowel(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new WinningDetermination(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new UndefeatedPoi(), gkmasModColor);
        // 莉波
        BaseMod.addRelicToCustomPool(new RegularMakeupPouch(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FirstHeartProofRinami(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new TreatForYou(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new LifeSizeLadyLip(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new ItsPerfect(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new SummerToShareWithYou(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new ClapClapFan(), gkmasModColor);
        // 琴音
        BaseMod.addRelicToCustomPool(new HandmadeMedal(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FirstVoiceProofKotone(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FavoriteSneakers(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new PigDreamPiggyBank(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new UltimateSourceOfHappiness(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new CracklingSparkler(), gkmasModColor);
        // 麻央
        BaseMod.addRelicToCustomPool(new GentlemanHandkerchief(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FirstLoveProofMao(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new DearLittlePrince(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new InnerLightEarrings(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new LastSummerMemory(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FashionMode(), gkmasModColor);
        // 清夏
        BaseMod.addRelicToCustomPool(new PinkUniformBracelet(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FirstLoveProofSumika(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new AfterSchoolDoodles(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new ArcadeLoot(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FrogFan(), gkmasModColor);
        // 手毬
        BaseMod.addRelicToCustomPool(new EssentialStainlessSteelBottle(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FirstVoiceProofTemari(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new MyFirstSheetMusic(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new ProtectiveEarphones(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new ThisIsMe(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new ClumsyBat(), gkmasModColor);


        BaseMod.addRelic(new BalanceLogicAndSense(), RelicType.SHARED);
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
    }

    private void addPotions(){
        BaseMod.addPotion(BoostExtract.class,BoostExtract.liquidColor,BoostExtract.hybridColor,BoostExtract.spotsColor,BoostExtract.ID);
        BaseMod.addPotion(FirstStarSpecialAojiru.class,FirstStarSpecialAojiru.liquidColor,FirstStarSpecialAojiru.hybridColor,FirstStarSpecialAojiru.spotsColor,FirstStarSpecialAojiru.ID);
        BaseMod.addPotion(FirstStarWater.class,FirstStarWater.liquidColor,FirstStarWater.hybridColor,FirstStarWater.spotsColor,FirstStarWater.ID);
        BaseMod.addPotion(FirstStarWheyProtein.class,FirstStarWheyProtein.liquidColor,FirstStarWheyProtein.hybridColor,FirstStarWheyProtein.spotsColor,FirstStarWheyProtein.ID);
        BaseMod.addPotion(FreshVinegar.class,FreshVinegar.liquidColor,FreshVinegar.hybridColor,FreshVinegar.spotsColor,FreshVinegar.ID);
        //BaseMod.addPotion(HotCoffee.class,HotCoffee.liquidColor,HotCoffee.hybridColor,HotCoffee.spotsColor,HotCoffee.ID);
        //BaseMod.addPotion(IcedCoffee.class,IcedCoffee.liquidColor,IcedCoffee.hybridColor,IcedCoffee.spotsColor,IcedCoffee.ID);
        BaseMod.addPotion(MixedSmoothie.class,MixedSmoothie.liquidColor,MixedSmoothie.hybridColor,MixedSmoothie.spotsColor,MixedSmoothie.ID);
        BaseMod.addPotion(OolongTea.class,OolongTea.liquidColor,OolongTea.hybridColor,OolongTea.spotsColor,OolongTea.ID);
        BaseMod.addPotion(RecoveryDrink.class,RecoveryDrink.liquidColor,RecoveryDrink.hybridColor,RecoveryDrink.spotsColor,RecoveryDrink.ID);
        //BaseMod.addPotion(RooibosTea.class,RooibosTea.liquidColor,RooibosTea.hybridColor,RooibosTea.spotsColor,RooibosTea.ID);
        //BaseMod.addPotion(SelectFirstStarBlend.class,SelectFirstStarBlend.liquidColor,SelectFirstStarBlend.hybridColor,SelectFirstStarBlend.spotsColor,SelectFirstStarBlend.ID);
        //BaseMod.addPotion(SelectFirstStarMacchiato.class,SelectFirstStarMacchiato.liquidColor,SelectFirstStarMacchiato.hybridColor,SelectFirstStarMacchiato.spotsColor,SelectFirstStarMacchiato.ID);
        //BaseMod.addPotion(SelectFirstStarTea.class,SelectFirstStarTea.liquidColor,SelectFirstStarTea.hybridColor,SelectFirstStarTea.spotsColor,SelectFirstStarTea.ID);
        //BaseMod.addPotion(SpecialFirstStarExtract.class,SpecialFirstStarExtract.liquidColor,SpecialFirstStarExtract.hybridColor,SpecialFirstStarExtract.spotsColor,SpecialFirstStarExtract.ID);
        //BaseMod.addPotion(StaminaExplosionDrink.class,StaminaExplosionDrink.liquidColor,StaminaExplosionDrink.hybridColor,StaminaExplosionDrink.spotsColor,StaminaExplosionDrink.ID);
        //BaseMod.addPotion(StylishHerbalTea.class,StylishHerbalTea.liquidColor,StylishHerbalTea.hybridColor,StylishHerbalTea.spotsColor,StylishHerbalTea.ID);
        //BaseMod.addPotion(VitaminDrink.class,VitaminDrink.liquidColor,VitaminDrink.hybridColor,VitaminDrink.spotsColor,VitaminDrink.ID);
        //BaseMod.addPotion(FirstStarBoostEnergy.class,FirstStarBoostEnergy.liquidColor, FirstStarBoostEnergy.hybridColor,FirstStarBoostEnergy.spotsColor,FirstStarBoostEnergy.ID);
    }

    @Override
    public void receivePostInitialize() {
        SkinSelectScreen.Inst.flag = true;
        SkinSelectScreen.Inst.specialCard = CardLibrary.getCard(gkmasMod_character, IdolData.getIdol(SkinSelectScreen.Inst.idolName).getCard(SkinSelectScreen.Inst.skinIndex)).makeCopy();
        ((GkmasCard)SkinSelectScreen.Inst.specialCard).setIdolBackgroundTexture(idolName);
        BaseMod.addCustomScreen(new PocketBookViewScreen());
        BaseMod.addCustomScreen(new AnotherShopScreen());

        BaseMod.addEvent(new AddEventParams.Builder(TogetherTrain.ID,TogetherTrain.class).spawnCondition(ConditionHelper.Condition_ttmr).dungeonID(Exordium.ID).create());
        BaseMod.addEvent(new AddEventParams.Builder(KakaSong.ID,KakaSong.class).playerClass(gkmasMod_character).dungeonID(TheCity.ID).dungeonID(TheBeyond.ID).create());
        BaseMod.addEvent(new AddEventParams.Builder(HajimeReward.ID,HajimeReward.class).spawnCondition(ConditionHelper.Condition_never).create());

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

        BaseMod.addMonster(MisuzuBoss.ID, () -> new MisuzuBoss());
        BaseMod.addMonster(HajimeBoss.ID, () -> new HajimeBoss());
        shopScreen = new AnotherShopScreen();
        //BaseMod.addBoss(Exordium.ID, HajimeBoss.ID, "gkmasModResource/img/monsters/Hajime/icon.png", "gkmasModResource/img/monsters/Hajime/icon.png");

//        BaseMod.addBoss(TheEnding.ID, MisuzuBoss.ID, "gkmasModResource/img/monster/Misuzu/icon.png", "gkmasModResource/img/monster/Misuzu/icon.png");

        try {
            // 设置默认值
            Properties defaults = new Properties();
            defaults.setProperty("cardRate", String.valueOf(PlayerHelper.getCardRate()));
            defaults.setProperty("beat_hmsz", "0");
            SpireConfig config = new SpireConfig("GkmasMod", "config", defaults);
            // 读取配置
            cardRate = config.getFloat("cardRate");
            beat_hmsz = config.getInt("beat_hmsz");
            if(beat_hmsz>0)
                SkinSelectScreen.Inst.specialCard.upgrade();
            config.save();
        } catch (IOException var2) {
            var2.printStackTrace();
        }
        addPotions();
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
//        if(AbstractDungeon.player.hasRelic(FirstStarBracelet.ID)){
//            ((FirstStarBracelet)AbstractDungeon.player.getRelic(FirstStarBracelet.ID)).afterVictory();
//        }

        }
}
