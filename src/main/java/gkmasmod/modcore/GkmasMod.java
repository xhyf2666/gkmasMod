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
import gkmasmod.Listener.CardImgUpdateListener;
import gkmasmod.cards.free.BaseAppeal;
import gkmasmod.cards.free.BasePerform;
import gkmasmod.cards.free.BasePose;
import gkmasmod.cards.free.Gacha;
import gkmasmod.cards.logic.*;
import gkmasmod.cards.sense.*;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.relics.*;
import gkmasmod.variables.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static gkmasmod.characters.PlayerColorEnum.*;

@SpireInitializer
public class GkmasMod implements EditCardsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber, EditCharactersSubscriber, AddAudioSubscriber,EditRelicsSubscriber {

    public static List<CardImgUpdateListener> listeners = new ArrayList<>();
    //攻击、技能、能力牌的背景图片(512)

    static String idolName = "shro";

    private static final String carduiImgFormat = "img/idol/%s/cardui/%s/%sbg_%s.png";
    private static final String ATTACK_CC = String.format("img/idol/%s/cardui/512/bg_attack.png",idolName);
    private static final String SKILL_CC = String.format("img/idol/%s/cardui/512/bg_skill.png",idolName);
    private static final String POWER_CC = String.format("img/idol/%s/cardui/512/bg_power.png",idolName);
    private static final String ENERGY_ORB_CC = "img/UI/star.png";
    //攻击、技能、能力牌的背景图片(1024)
    private static final String ATTACK_CC_PORTRAIT = String.format("img/idol/%s/cardui/1024/bg_attack.png",idolName);
    private static final String SKILL_CC_PORTRAIT = String.format("img/idol/%s/cardui/1024/bg_skill.png",idolName);
    private static final String POWER_CC_PORTRAIT = String.format("img/idol/%s/cardui/1024/bg_power.png",idolName);
    private static final String ENERGY_ORB_CC_PORTRAIT = "img/UI/star_164.png";
    public static final String CARD_ENERGY_ORB = "img/UI/star_22.png";
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
//        BaseMod.addColor(gkmasModColor, gkmasMod_color, gkmasMod_color, gkmasMod_color, gkmasMod_color, gkmasMod_color, gkmasMod_color, gkmasMod_color, ATTACK_CC, SKILL_CC, POWER_CC, ENERGY_ORB_CC,
//                ATTACK_CC_PORTRAIT,SKILL_CC_PORTRAIT ,POWER_CC_PORTRAIT , ENERGY_ORB_CC_PORTRAIT,CARD_ENERGY_ORB );
//        BaseMod.addColor(gkmasModColorLogic, gkmasMod_colorLogic, gkmasMod_colorLogic, gkmasMod_colorLogic, gkmasMod_colorLogic, gkmasMod_colorLogic, gkmasMod_colorLogic, gkmasMod_colorLogic, ATTACK_CC, SKILL_CC, POWER_CC, ENERGY_ORB_CC,
//                ATTACK_CC_PORTRAIT,SKILL_CC_PORTRAIT ,POWER_CC_PORTRAIT , ENERGY_ORB_CC_PORTRAIT,CARD_ENERGY_ORB );
//        BaseMod.addColor(gkmasModColorSense, gkmasMod_colorSense, gkmasMod_colorSense, gkmasMod_colorSense, gkmasMod_colorSense, gkmasMod_colorSense, gkmasMod_colorSense, gkmasMod_colorSense, ATTACK_CC, SKILL_CC, POWER_CC, ENERGY_ORB_CC,
//                ATTACK_CC_PORTRAIT,SKILL_CC_PORTRAIT ,POWER_CC_PORTRAIT , ENERGY_ORB_CC_PORTRAIT,CARD_ENERGY_ORB );
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
                // 这个id要全小写
                BaseMod.addKeyword(keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }


    public static List<Object> getCardsToAdd(){
        List<Object> instances = new ArrayList<>();
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
        instances.add(new StartDash());
        instances.add(new Gacha());
        instances.add(new JustOneMore());
        instances.add(new LightGait());
        instances.add(new AiJiao());
        instances.add(new ServiceSpirit());
        instances.add(new HighFive());
        instances.add(new GoWithTheFlow());
        instances.add(new WarmUp());
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

        // 广
        BaseMod.addRelicToCustomPool((AbstractRelic)new UltimateSleepMask(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new BeginnerGuideForEveryone(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new SidewalkResearchNotes(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new TowardsAnUnseenWorld(), gkmasModColor);
        // 莉莉娅
        BaseMod.addRelicToCustomPool((AbstractRelic)new GreenUniformBracelet(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new MemoryBot(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new DreamLifeLog(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new SparklingInTheBottle(), gkmasModColor);
        // 千奈
        BaseMod.addRelicToCustomPool((AbstractRelic)new WishFulfillmentAmulet(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new CheerfulHandkerchief(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new SecretTrainingCardigan(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new HeartFlutteringCup(), gkmasModColor);
        // 佑芽
        BaseMod.addRelicToCustomPool((AbstractRelic)new TechnoDog(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new ShibaInuPochette(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new RollingSourceOfEnergy(), gkmasModColor);
        // 咲季
        BaseMod.addRelicToCustomPool((AbstractRelic)new RoaringLion(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new FirstVoiceProofSaki(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new SakiCompleteMealRecipe(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new TogetherInBattleTowel(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new WinningDetermination(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new UndefeatedPoi(), gkmasModColor);
        // 莉波
        BaseMod.addRelicToCustomPool((AbstractRelic)new RegularMakeupPouch(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new TreatForYou(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new LifeSizeLadyLip(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new SummerToShareWithYou(), gkmasModColor);
        // 琴音
        BaseMod.addRelicToCustomPool((AbstractRelic)new HandmadeMedal(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new FirstVoiceProofKotone(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new FavoriteSneakers(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new PigDreamPiggyBank(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new UltimateSourceOfHappiness(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new CracklingSparkler(), gkmasModColor);
        // 麻央
        BaseMod.addRelicToCustomPool((AbstractRelic)new GentlemanHandkerchief(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new DearLittlePrince(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new InnerLightEarrings(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new LastSummerMemory(), gkmasModColor);
        // 清夏
        BaseMod.addRelicToCustomPool((AbstractRelic)new PinkUniformBracelet(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new AfterSchoolDoodles(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new ArcadeLoot(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new FrogFan(), gkmasModColor);
        // 手毬
        BaseMod.addRelicToCustomPool((AbstractRelic)new EssentialStainlessSteelBottle(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new FirstVoiceProofTemari(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new MyFirstSheetMusic(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new ProtectiveEarphones(), gkmasModColor);
        BaseMod.addRelicToCustomPool((AbstractRelic)new ThisIsMe(), gkmasModColor);
    }
}
