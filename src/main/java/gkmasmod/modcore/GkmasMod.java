package gkmasmod.modcore;

import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import gkmasmod.Listener.CardImgUpdateListener;
import gkmasmod.cards.free.*;
import gkmasmod.cards.logic.*;
import gkmasmod.cards.sense.*;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.relics.*;
import gkmasmod.ui.PocketBookViewScreen;
import gkmasmod.ui.SkinSelectScreen;
import gkmasmod.variables.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static gkmasmod.characters.PlayerColorEnum.*;

@SpireInitializer
public class GkmasMod implements EditCardsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber, EditCharactersSubscriber, AddAudioSubscriber,EditRelicsSubscriber,PostInitializeSubscriber  {

    public static List<CardImgUpdateListener> listeners = new ArrayList<>();
    //攻击、技能、能力牌的背景图片(512)

    static String idolName = "shro";

    private static final String carduiImgFormat = "img/idol/%s/cardui/%s/%sbg_%s.png";
    private static final String ATTACK_CC = String.format("img/idol/%s/cardui/512/bg_attack.png",idolName);
    private static final String SKILL_CC = String.format("img/idol/%s/cardui/512/bg_skill.png",idolName);
    private static final String POWER_CC = String.format("img/idol/%s/cardui/512/bg_power.png",idolName);
    private static final String ENERGY_ORB_CC = "img/UI/energy.png";
    //攻击、技能、能力牌的背景图片(1024)
    private static final String ATTACK_CC_PORTRAIT = String.format("img/idol/%s/cardui/1024/bg_attack.png",idolName);
    private static final String SKILL_CC_PORTRAIT = String.format("img/idol/%s/cardui/1024/bg_skill.png",idolName);
    private static final String POWER_CC_PORTRAIT = String.format("img/idol/%s/cardui/1024/bg_power.png",idolName);
    private static final String ENERGY_ORB_CC_PORTRAIT = "img/UI/energy_164.png";
    public static final String CARD_ENERGY_ORB = "img/UI/energy_22.png";
    //选英雄界面的角色图标、选英雄时的背景图片
    private static final String MY_CHARACTER_BUTTON = "img/charSelect/selectButton.png";
    private static final String MARISA_PORTRAIT = "img/charSelect/background_init.png";
    public static final Color gkmasMod_color = CardHelper.getColor(100, 200, 200);

    public static final Color gkmasMod_colorLogic = CardHelper.getColor(50, 70, 200);

    public static final Color gkmasMod_colorSense = CardHelper.getColor(200, 150, 100);
    public static boolean limitedSLOption;
    private ArrayList<AbstractCard> cardsToAdd = new ArrayList<>();
    public static ArrayList<AbstractCard> recyclecards = new ArrayList<>();
    private static SpireConfig config;
    public static final String MOD_NAME = "gkmasMod";


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
        BaseMod.addAudio("shro_click", "audio/shro_click.ogg");
        BaseMod.addAudio("kllj_click", "audio/kllj_click.ogg");
    }

    @Override
    public void receiveEditStrings() {
        String lang;
        if (Settings.language == Settings.GameLanguage.ZHS) {
            lang = "chs";
        } else {
            lang = "en";
        }
        BaseMod.loadCustomStringsFile(CardStrings.class, "localization/gkmas_cards_"+lang+".json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "localization/gkmas_powers_" + lang + ".json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, "localization/gkmas_characters_" + lang + ".json");
        BaseMod.loadCustomStringsFile(UIStrings.class, "localization/gkmas_ui_" + lang + ".json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, "localization/gkmas_relics_" + lang + ".json");
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String lang = "eng";
        if (Settings.language == Settings.GameLanguage.ZHS) {
            lang = "chs";
        }

        String json = Gdx.files.internal("localization/gkmas_keywords_" + lang + ".json")
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

        instances.add(new Gacha());
        instances.add(new JustOneMore());

        // 广
        instances.add(new HighlyEducatedIdol());
        instances.add(new LovesTheStruggle());
        instances.add(new SeriousHobby());
        instances.add(new SwayingOnTheBus());
        // 莉莉娅
        instances.add(new ReservedGirl());
        instances.add(new PureWhiteFairy());
        instances.add(new NotAfraidAnymore());
        instances.add(new FirstRamune());
        // 千奈
        instances.add(new FullOfEnergy());
        instances.add(new Wholeheartedly());
        instances.add(new DebutStageForTheLady());
        instances.add(new WelcomeToTeaParty());
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
        instances.add(new SupportiveFeelings());
        instances.add(new SenseOfDistance());
        instances.add(new CumulusCloudsAndYou());
        // 琴音
        instances.add(new Arbeiter());
        instances.add(new FirstReward());
        instances.add(new ColorfulCute());
        instances.add(new NoDistractions());
        instances.add(new FullAdrenaline());
        instances.add(new SummerEveningSparklers());
        // 麻央
        instances.add(new LittlePrince());
        instances.add(new Authenticity());
        instances.add(new DressedUpInStyle());
        instances.add(new ChillyBreak());
        // 清夏
        instances.add(new Friendly());
        instances.add(new BraveStep());
        instances.add(new OneMoreStep());
        instances.add(new AfternoonBreeze());
        // 手毬
        instances.add(new Stubborn());
        instances.add(new FirstPlace());
        instances.add(new LoneWolf());
        instances.add(new EachPath());
        instances.add(new TangledFeelings());

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
        BaseMod.addRelicToCustomPool(new BeginnerGuideForEveryone(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new SidewalkResearchNotes(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new TowardsAnUnseenWorld(), gkmasModColor);
        // 莉莉娅
        BaseMod.addRelicToCustomPool(new GreenUniformBracelet(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new MemoryBot(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new DreamLifeLog(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new SparklingInTheBottle(), gkmasModColor);
        // 千奈
        BaseMod.addRelicToCustomPool(new WishFulfillmentAmulet(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new CheerfulHandkerchief(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new SecretTrainingCardigan(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new HeartFlutteringCup(), gkmasModColor);
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
        BaseMod.addRelicToCustomPool(new TreatForYou(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new LifeSizeLadyLip(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new SummerToShareWithYou(), gkmasModColor);
        // 琴音
        BaseMod.addRelicToCustomPool(new HandmadeMedal(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FirstVoiceProofKotone(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FavoriteSneakers(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new PigDreamPiggyBank(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new UltimateSourceOfHappiness(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new CracklingSparkler(), gkmasModColor);
        // 麻央
        BaseMod.addRelicToCustomPool(new GentlemanHandkerchief(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new DearLittlePrince(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new InnerLightEarrings(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new LastSummerMemory(), gkmasModColor);
        // 清夏
        BaseMod.addRelicToCustomPool(new PinkUniformBracelet(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new AfterSchoolDoodles(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new ArcadeLoot(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FrogFan(), gkmasModColor);
        // 手毬
        BaseMod.addRelicToCustomPool(new EssentialStainlessSteelBottle(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new FirstVoiceProofTemari(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new MyFirstSheetMusic(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new ProtectiveEarphones(), gkmasModColor);
        BaseMod.addRelicToCustomPool(new ThisIsMe(), gkmasModColor);
    }

    @Override
    public void receivePostInitialize() {
        BaseMod.addCustomScreen(new PocketBookViewScreen());
    }
}
