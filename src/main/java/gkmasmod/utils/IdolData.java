package gkmasmod.utils;


import com.megacrit.cardcrawl.cards.blue.Zap;
import gkmasmod.cards.anomaly.*;
import gkmasmod.cards.free.*;
import gkmasmod.cards.hmsz.AnotherDream;
import gkmasmod.cards.hmsz.SummerSleepy;
import gkmasmod.cards.logic.*;
import gkmasmod.cards.othe.BaseProvocation;
import gkmasmod.cards.sense.*;
import gkmasmod.cards.special.FightWill;
import gkmasmod.relics.*;
import gkmasmod.utils.CommonEnum.IdolStyle;
import gkmasmod.utils.CommonEnum.IdolType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class IdolData {

    /**
     * 篠泽广 Shinosawa Hiro
     */
    public static final String shro = "shro";

    /**
     * 葛城莉莉娅 Katsuragi Lilja
     */
    public static final String kllj = "kllj";

    /**
     * 仓本千奈 Kuramoto China
     */
    public static final String kcna = "kcna";

    /**
     * 花海佑芽 Hanami Ume
     */
    public static final String hume = "hume";

    /**
     * 花海咲季 Hanami Saki
     */
    public static final String hski = "hski";

    /**
     * 姬崎莉波 Himesaki Rinami
     */
    public static final String hrnm = "hrnm";

    /**
     * 藤田琴音 Fujita Kotone
     */
    public static final String fktn = "fktn";

    /**
     * 有村麻央 Arimura Mao
     */
    public static final String amao = "amao";

    /**
     * 紫云清夏 Shiun Sumika
     */
    public static final String ssmk = "ssmk";

    /**
     * 月村手毬 Tsukimura Temari
     */
    public static final String ttmr = "ttmr";

    /**
     * 秦谷美铃 Hataya Misuzu
     */
    public static final String hmsz = "hmsz";

    /**
     * 十王星南 Juo Sena
     */
    public static final String jsna = "jsna";

    /**
     * 根绪亚纱里 Neo Asari
     */
    public static final String nasr = "nasr";

    /**
     * 雨夜燕 Amayo Tubame
     */
    public static final String atbm = "atbm";

    /**
     * 贺阳燐羽 Kaya Rinha
     */
    public static final String krnh = "krnh";

    /**
     * 蓝井抚子 Aoi Nadeshiko
     */
    public static final String andk = "andk";

    /**
     * 白草四音 Shirakusa Shion
     */
    public static final String sson = "sson";

    /**
     * 白草月花 Shirakusa Gekka
     */
    public static final String sgka = "sgka";

    /**
     * 真城忧 Mashiro Yu
     */
    public static final String mshy = "mshy";

    /**
     * 制作人 Producer
     */
    public static final String prod = "prod";

    /**
     * AI姬琦莉波 Himesaki Rinami
     */
    public static final String arnm = "arnm";

    /**
     * 透明背景
     */
    public static final String empty = "empty";

    public static final String[] idolNames = {shro, kllj, kcna,hume,hski,hrnm,fktn,amao,ssmk,ttmr,jsna,hmsz};

    public static final String[] otherIdolNames = {prod,arnm,andk,sson,sgka,krnh,mshy,nasr};

    public static HashMap<String, Idol> idols = new HashMap<>();

    public static HashMap<String,Idol> otherIdols = new HashMap<>();

    public static Idol hmszData;

    public static Idol getIdol(String name) {
        return idols.get(name);
    }

    public static Idol getIdol(int index) {
        return idols.get(idolNames[index]);
    }

    public static Idol getOtherIdol(String name) {
        return otherIdols.get(name);
    }

    public static Idol getOtherIdol(int index) {
        return otherIdols.get(otherIdolNames[index]);
    }

    // 10 初始R
    // 11 初演换皮
    // 20 初始SR
    // 30 初始SSR
    // 31 常驻换皮
    // 33 泳装换皮
    // 34 冠菊换皮
    // 35 温泉换皮
    // 36 万圣换皮
    // 37 古今东西换皮
    // 38 圣诞换皮
    // 40 FES换皮
    // 41 努努换皮1
    // 42 努努换皮2
    // 43 情人节换皮
    // 44 女儿节换皮
    // 45 樱花季换皮
    // 46 团体曲换皮

    static {
        idols.put(shro, new Idol(shro,
                new String[]{"skin10","skin11","skin20","skin30","skin31","skin33","skin34","skin36","skin37","skin38","skin40","skin41","skin43","skin44","skin45"},
                new String[]{"skin10","skin11","skin12","skin14","skin13","skin15","skin16","skin17","skin19","skin18","skin23","skin21","skin24","skin25","skin26"},
                new String[]{HighlyEducatedIdol.ID,FirstFun.ID, LovesTheStruggle.ID, SeriousHobby.ID, SwayingOnTheBus.ID, SeriousHobby.ID, SeriousHobby.ID,MakeExactSignboard.ID,SeriousHobby.ID, SeriousHobby.ID,Eureka.ID,SeriousHobby.ID,WorkHard.ID,SeriousHobby.ID,SeriousHobby.ID},
                new String[]{UltimateSleepMask.ID,FirstLoveProofHiro.ID, BeginnerGuideForEveryone.ID, SidewalkResearchNotes.ID, TowardsAnUnseenWorld.ID,SidewalkResearchNotes.ID,SidewalkResearchNotes.ID,NaughtyPuppet.ID,SidewalkResearchNotes.ID,SidewalkResearchNotes.ID,SelectedPassion.ID,SidewalkResearchNotes.ID,TheMarkOfPractice.ID,SidewalkResearchNotes.ID,SidewalkResearchNotes.ID},
                new IdolType[]{IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC,IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC,IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.ANOMALY,IdolType.LOGIC,IdolType.LOGIC},
                new IdolStyle[]{IdolStyle.YARUKI,IdolStyle.FOCUS,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.FOCUS,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.GOOD_TUNE,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.FULL_POWER,IdolStyle.YARUKI,IdolStyle.YARUKI},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{"1","2","3"}, new String[]{"4"}, new String[]{"4"}, new String[]{"4","5"}, new String[]{}, new String[]{"6"}, new String[]{})),
                new String[]{"1","1","1","1","2","2","2","3","3","3"},
                new String[]{"1","1","1","2","3","4","5","6","8","7","10","2","11","12","13"},
                new String[]{"all_001","all_001","all_001","shro_001","shro_002","all_003","all_004","all_005","all_006","all_007","all_002","shro_001","all_008","all_009","all_010"},
                new String[]{"no","no","no","3_000","3_001","3_003","3_004","3_006","3_007","3_008","3_010","3_000","no","3_012","3_013"},
                66,99,new int[]{115,115,80},new float[]{0.27f,0.23f,0.10f},
                new int[]{1200,1000,800},
                new int[]{0,1,0},new int[]{300,450,1000},
                new int[]{9,11,13},
                new String[]{"1_000","1_001","2_000","3_000","3_001","3_003","3_004","3_006","3_007","3_008","3_010","3_000","3_011","3_012","3_013"},
                new String[]{"002","003","009"},
                new String[]{EvenIfDreamNotRealize.ID, NecessaryContrast.ID},
                new String[]{"skin14","skin12","skin18"},
                new String[]{"","","","","","","","","","",Yeah.ID,"","","",""},
                new String[]{"fire","fire2","fire3"}
        ));
        idols.put(kllj, new Idol(kllj,
                new String[]{"skin10","skin11","skin20","skin30","skin31","skin33","skin34","skin36","skin37","skin38","skin40","skin41","skin42","skin43","skin44","skin45"},
                new String[]{"skin10","skin11","skin12","skin14","skin13","skin15","skin16","skin17","skin19","skin18","skin23","skin21","skin22","skin24","skin25","skin26"},
                new String[]{ReservedGirl.ID, FirstGround.ID,PureWhiteFairy.ID, NotAfraidAnymore.ID,KiraKiraPrism.ID, NotAfraidAnymore.ID, FirstRamune.ID,NotAfraidAnymore.ID,NotAfraidAnymore.ID,WithLove.ID,TheScenerySawSomeday.ID,NotAfraidAnymore.ID,NotAfraidAnymore.ID,NotAfraidAnymore.ID,NotAfraidAnymore.ID,AfterSchoolChat.ID},
                new String[]{GreenUniformBracelet.ID, FirstHeartProofLilja.ID,MemoryBot.ID, DreamLifeLog.ID,SmallGalaxy.ID, DreamLifeLog.ID, SparklingInTheBottle.ID, DreamLifeLog.ID,DreamLifeLog.ID,BeyondTheSea.ID,TheDreamSawSomeday.ID,DreamLifeLog.ID,DreamLifeLog.ID,DreamLifeLog.ID,DreamLifeLog.ID,ContinuousEffort.ID},
                new IdolType[]{IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC,IdolType.LOGIC,IdolType.ANOMALY,IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.ANOMALY},
                new IdolStyle[]{IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_IMPRESSION,IdolStyle.CONCENTRATION,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_IMPRESSION,IdolStyle.YARUKI,IdolStyle.FOCUS,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_IMPRESSION,IdolStyle.FULL_POWER},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{}, new String[]{"3"}, new String[]{"3","4"}, new String[]{"3","4","5"}, new String[]{}, new String[]{"6"}, new String[]{})),
                new String[]{"2","2","2","3","3","3","3","4","4","4"},
                new String[]{"1","1","1","2","3","4","5","6","8","7","10","2","2","11","12","13"},
                new String[]{"all_001","all_001","all_001","kllj_001","kllj_002","all_003","all_004","all_005","all_006","all_007","all_002","kllj_001","kllj_001","all_008","all_009","all_010"},
                new String[]{"no","no","no","3_000","3_001","3_003","3_004","3_006","3_007","3_008","3_010","3_000","3_000","3_011","3_012","3_013"},
                74,99,new int[]{75,75,105},new float[]{0.18f,0.25f,0.21f},
                new int[]{800,1000,1200},
                new int[]{1,0,2},new int[]{200,450,1000},
                new int[]{9,12,11},
                new String[]{"1_000","1_001","2_000","3_000","3_001","3_003","3_004","3_006","3_007","3_008","3_010","3_000","3_000","3_011","3_012","3_013"},
                new String[]{"002","003","009"},
                new String[]{YoungDream.ID, GrowthProcess.ID},
                new String[]{"skin14","skin12","skin18"},
                new String[]{"","","","","","","","","","","gkmasMod:WarmUp","","","","",""},
                new String[]{"fire","fire2","fire3"}
        ));
        idols.put(kcna, new Idol(kcna,
                new String[]{"skin10","skin11","skin20","skin30","skin31","skin33","skin34","skin35","skin36","skin37","skin38","skin40","skin41","skin42","skin43","skin44","skin45"},
                new String[]{"skin10","skin11","skin12","skin14","skin13","skin15","skin20","skin17","skin18","skin16","skin19","skin23","skin21","skin22","skin24","skin25","skin26"},
                new String[]{FullOfEnergy.ID,FirstColor.ID, Wholeheartedly.ID, DebutStageForTheLady.ID, WelcomeToTeaParty.ID, DebutStageForTheLady.ID, DebutStageForTheLady.ID,AQuickSip.ID,GonnaTrickYou.ID,DebutStageForTheLady.ID,DebutStageForTheLady.ID,ContinuousExpandWorld.ID,DebutStageForTheLady.ID,DebutStageForTheLady.ID,DebutStageForTheLady.ID,LittleSun.ID,DebutStageForTheLady.ID},
                new String[]{WishFulfillmentAmulet.ID, FirstHeartProofChina.ID,CheerfulHandkerchief.ID,SecretTrainingCardigan.ID,HeartFlutteringCup.ID,SecretTrainingCardigan.ID,SecretTrainingCardigan.ID,EnjoyAfterHotSpring.ID,CursedSpirit.ID,SecretTrainingCardigan.ID,SecretTrainingCardigan.ID,DreamOfTheDescription.ID,SecretTrainingCardigan.ID,SecretTrainingCardigan.ID,SecretTrainingCardigan.ID,SpringIsComing.ID,SecretTrainingCardigan.ID},
                new IdolType[]{IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC,IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC},
                new IdolStyle[]{IdolStyle.YARUKI,IdolStyle.GOOD_TUNE,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.GOOD_TUNE,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.GOOD_IMPRESSION,IdolStyle.YARUKI},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{"1","2"}, new String[]{"1","2","3"}, new String[]{}, new String[]{"4"}, new String[]{"4","5"}, new String[]{}, new String[]{})),
                new String[]{"2","2","2","2","2","3","3","3","4","4"},
                new String[]{"1","1","1","2","3","4","5","1","6","8","7","10","2","2","11","12","13"},
                new String[]{"all_001","all_001","all_001","kcna_001","kcna_002","all_003","all_004","all_001","all_005","all_006","all_007","all_002","kcna_001","kcna_001","all_008","all_009","all_010"},
                new String[]{"no","no","no","3_000","3_001","3_003","3_004","3_005","no","3_007","3_008","3_010","3_000","3_000","3_011","3_012","3_013"},
                68,200,new int[]{75,110,115},new float[]{0.10f,0.28f,0.22f},
                new int[]{700,1350,950},
                new int[]{0,2,1},new int[]{200,450,1000},
                new int[]{10,11,12},
                new String[]{"1_000","1_001","2_000","3_000","3_001","3_003","3_004","3_005","3_006","3_007","3_008","3_010","3_000","3_000","3_011","3_012","3_013"},
                new String[]{"002","003","009"},
                new String[]{SlowGrowth.ID, SpecialTreasure.ID},
                new String[]{"skin14","skin12","skin18"},
                new String[]{"","","","","","","","","","","",TeaChat.ID,"","","","",""},
                new String[]{"fire","fire2","fire3"}
        ));
        idols.put(hume, new Idol(hume,
                new String[]{"skin10","skin20","skin30","skin33","skin34","skin36","skin37","skin38","skin40","skin41","skin43","skin44"},
                new String[]{"skin10","skin11","skin12","skin13","skin10","skin18","skin19","skin17","skin23","skin21","skin24","skin25"},
                new String[]{UntappedPotential.ID, DefeatBigSister.ID, BigOnigiri.ID, BigOnigiri.ID, BigOnigiri.ID,BigOnigiri.ID,BigOnigiri.ID,ShareSomethingWithYou.ID, NewStage.ID, BigOnigiri.ID, SecretMeeting.ID, LikeYouVeryMuch.ID},
                new String[]{TechnoDog.ID, ShibaInuPochette.ID, RollingSourceOfEnergy.ID, RollingSourceOfEnergy.ID, RollingSourceOfEnergy.ID,RollingSourceOfEnergy.ID,RollingSourceOfEnergy.ID,ChristmasLion.ID,AchieveDreamAwakening.ID,RollingSourceOfEnergy.ID,GiftForYou.ID,ProofOfFriendship.ID},
                new IdolType[]{IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.SENSE,IdolType.ANOMALY,IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC},
                new IdolStyle[]{IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.GOOD_TUNE,IdolStyle.FULL_POWER,IdolStyle.YARUKI,IdolStyle.FOCUS,IdolStyle.GOOD_IMPRESSION},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{"1","2","3"}, new String[]{"1","2","3"}, new String[]{"1","2","3","4"}, new String[]{}, new String[]{"5"}, new String[]{"5","6"}, new String[]{})),
                new String[]{"2","2","2","2","2","2","3","3","3","3"},
                new String[]{"1","1","2","4","5","6","8","7","10","2","11","12"},
                new String[]{"all_001","all_001","hume_001","all_003","all_004","all_005","all_006","all_007","all_002","hume_001","all_008","all_009"},
                new String[]{"no","no","3_000","3_003","3_004","3_006","3_007","3_008","3_010","3_000","3_011","3_012"},
                72,99,new int[]{75,85,85},new float[]{0.23f,0.28f,0.15f},
                new int[]{1000,1200,800},
                new int[]{1,0,1},new int[]{300,450,1000},
                new int[]{12,14,14},
                new String[]{"1_000","2_000","3_000","3_003","3_004","3_006","3_007","3_008","3_010","3_000","3_011","3_012"},
                new String[]{"002","005","009"},
                new String[]{SisterHelp.ID,OverwhelmingNumbers.ID,GodArrival.ID},
                new String[]{"skin17","skin12","skin18"},
                new String[]{"","","","","","","","",JustAppeal.ID,"","",""},
                new String[]{"fire","fire2","fire3"}
        ));
        idols.put(hski, new Idol(hski,
                new String[]{"skin10","skin11","skin20","skin30","skin31","skin33","skin34","skin36","skin37","skin38","skin40","skin41","skin42","skin43","skin44","skin45","skin46"},
                new String[]{"skin10","skin11","skin12","skin14","skin13","skin15","skin20","skin17","skin18","skin19","skin23","skin21","skin22","skin24","skin25","skin26","skin27"},
                new String[]{RisingStar.ID, FirstFuture.ID, NeverYieldFirst.ID, NeverLose.ID, Pow.ID, NeverLose.ID, GoldfishScoopingChallenge.ID,NeverLose.ID,VeryEasy.ID,NeverLose.ID,UntilNowAndFromNow.ID,NeverLose.ID,NeverLose.ID,NeverLose.ID,NeverLose.ID,ThatDayHere.ID,MyColor.ID},
                new String[]{RoaringLion.ID,FirstVoiceProofSaki.ID,SakiCompleteMealRecipe.ID,TogetherInBattleTowel.ID,WinningDetermination.ID,TogetherInBattleTowel.ID,UndefeatedPoi.ID,TogetherInBattleTowel.ID,AnimateEquipment.ID,TogetherInBattleTowel.ID,FaceTheAwakening.ID,TogetherInBattleTowel.ID,TogetherInBattleTowel.ID,TogetherInBattleTowel.ID,TogetherInBattleTowel.ID,DestinyEncounter.ID,AfterRainFlower.ID},
                new IdolType[]{IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.ANOMALY,IdolType.LOGIC},
                new IdolStyle[]{IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE,IdolStyle.FOCUS,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_TUNE,IdolStyle.FULL_POWER,IdolStyle.YARUKI},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{"1","2","3"}, new String[]{"1","2","3"}, new String[]{}, new String[]{"4"}, new String[]{}, new String[]{"5"}, new String[]{})),
                new String[]{"1","1","1","1","1","2","2","3","3","3"},
                new String[]{"1","1","1","2","3","4","5","6","8","7","10","2","2","11","12","13","14"},
                new String[]{"all_001","all_001","all_001","hski_001","hski_002","all_003","all_004","all_005","all_006","all_007","all_002","hski_001","hski_001","all_008","all_009","all_010","unit_001"},
                new String[]{"no","no","no","3_000","3_001","3_003","no","3_006","3_007","3_008","3_010","3_000","3_000","3_011","3_012","3_013","3_014"},
                76,99,new int[]{100,100,105},new float[]{0.18f,0.18f,0.20f},
                new int[]{800,1000,1200},
                new int[]{1,0,2},new int[]{200,450,900},
                new int[]{10,13,12},
                new String[]{"1_000","1_001","2_000","3_000","3_001","3_003","3_004","3_006","3_007","3_008","3_010","3_000","3_000","3_011","3_012","3_013","3_014"},
                new String[]{"002","003","009"},
                new String[]{TrueLateBloomer.ID,MotherAI.ID},
                new String[]{"skin14","skin12","skin18"},
                new String[]{"","","","","","","","","","",LightGait.ID,"","","","","",""},
                new String[]{"fire","fire2","fire3"}
        ));
        idols.put(hrnm, new Idol(hrnm,
                new String[]{"skin10","skin11","skin20","skin30","skin31","skin33","skin34","skin35","skin36","skin37","skin38","skin40","skin41","skin43","skin44","skin45"},
                new String[]{"skin10","skin11","skin12","skin14","skin13","skin15","skin16","skin18","skin19","skin27","skin17","skin23","skin21","skin24","skin25","skin26"},
                new String[]{Accommodating.ID,FirstFriend.ID,SupportiveFeelings.ID, SenseOfDistance.ID,SeeYouTomorrow.ID, CumulusCloudsAndYou.ID, SenseOfDistance.ID,RefreshingBreak.ID,SenseOfDistance.ID,SenseOfDistance.ID,SenseOfDistance.ID,LetMeBecomeIdol.ID, SenseOfDistance.ID,CanYouAccept.ID, SenseOfDistance.ID,SenseOfDistance.ID},
                new String[]{RegularMakeupPouch.ID,FirstHeartProofRinami.ID,TreatForYou.ID, LifeSizeLadyLip.ID,ItsPerfect.ID, SummerToShareWithYou.ID, LifeSizeLadyLip.ID,ClapClapFan.ID,LifeSizeLadyLip.ID,LifeSizeLadyLip.ID,LifeSizeLadyLip.ID,YouFindMe.ID,LifeSizeLadyLip.ID,SweetTaste.ID,LifeSizeLadyLip.ID,LifeSizeLadyLip.ID},
                new IdolType[]{IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.ANOMALY,IdolType.SENSE,IdolType.ANOMALY,IdolType.SENSE,IdolType.SENSE},
                new IdolStyle[]{IdolStyle.FOCUS,IdolStyle.YARUKI,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE,IdolStyle.FOCUS,IdolStyle.YARUKI,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.FULL_POWER,IdolStyle.FOCUS,IdolStyle.CONCENTRATION,IdolStyle.FOCUS,IdolStyle.FOCUS},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{"1","2"}, new String[]{"1","2","3"}, new String[]{}, new String[]{"4"}, new String[]{"4","5"}, new String[]{"4","5","6"}, new String[]{})),
                new String[]{"1","1","1","1","1","2","2","2","2","2"},
                new String[]{"1","1","1","2","3","4","5","1","6","8","7","10","2","11","12","13"},
                new String[]{"all_001","all_001","all_001","hrnm_001","hrnm_002","all_003","all_004","all_001","all_005","all_006","all_007","all_002","hrnm_001","all_008","all_009","all_010"},
                new String[]{"no","no","no","3_000","3_001","3_003","3_004","3_005","3_006","3_007","3_008","3_010","3_000","3_011","3_012","3_013"},
                72,99,new int[]{85,95,85},new float[]{0.11f,0.245f,0.285f},
                new int[]{800,1000,1200},
                new int[]{0,1,2},new int[]{200,450,1000},
                new int[]{12,12,12},
                new String[]{"1_000","1_001","2_000","3_000","3_001","3_003","3_004","3_005","3_006","3_007","3_008","3_010","3_000","3_011","3_012","3_013"},
                new String[]{"002","003","009"},
                new String[]{SaySomethingToYou.ID, BlueSenpaiHelp.ID},
                new String[]{"skin14","skin14","skin18"},
                new String[]{"","","","","","","","","","","",SurpriseMiss.ID,"","","",""},
                new String[]{"fire","fire2","fire3"}
        ));
        idols.put(fktn, new Idol(fktn,
                new String[]{"skin10","skin11","skin20","skin30","skin31","skin33","skin34","skin36","skin37","skin38","skin40","skin41","skin42","skin43","skin44","skin45","skin46"},
                new String[]{"skin10","skin11","skin12","skin14","skin13","skin15","skin19","skin18","skin16","skin17","skin23","skin21","skin22","skin24","skin25","skin26","skin27"},
                new String[]{Arbeiter.ID, FirstReward.ID, ColorfulCute.ID, NoDistractions.ID, FullAdrenaline.ID, NoDistractions.ID, SummerEveningSparklers.ID,NoDistractions.ID,NoDistractions.ID,HappyChristmas.ID,MyPrideBigSister.ID, NoDistractions.ID, NoDistractions.ID, NoDistractions.ID, NoDistractions.ID,NoDistractions.ID,NewLight.ID},
                new String[]{HandmadeMedal.ID,FirstVoiceProofKotone.ID,FavoriteSneakers.ID,PigDreamPiggyBank.ID,UltimateSourceOfHappiness.ID,PigDreamPiggyBank.ID,CracklingSparkler.ID,PigDreamPiggyBank.ID,PigDreamPiggyBank.ID,FreeLoveMax.ID,OnlyMyFirstStar.ID,PigDreamPiggyBank.ID,PigDreamPiggyBank.ID,PigDreamPiggyBank.ID,PigDreamPiggyBank.ID,PigDreamPiggyBank.ID,AfterRainRainbow.ID},
                new IdolType[]{IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC,IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.ANOMALY},
                new IdolStyle[]{IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_IMPRESSION,IdolStyle.YARUKI,IdolStyle.GOOD_IMPRESSION,IdolStyle.YARUKI,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_IMPRESSION,IdolStyle.CONCENTRATION},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{"1","2","3"}, new String[]{}, new String[]{"4"}, new String[]{"4","5"}, new String[]{"4","5","6"}, new String[]{}, new String[]{})),
                new String[]{"1","1","1","1","2","2","2","2","3","3"},
                new String[]{"1","1","1","2","3","4","5","6","8","7","10","2","2","11","12","13","14"},
                new String[]{"all_001","all_001","all_001","fktn_001","fktn_002","all_003","all_004","all_005","all_006","all_007","all_002","fktn_001","fktn_001","all_008","all_009","all_010","unit_001"},
                new String[]{"no","no","no","3_000","3_001","3_003","3_004","3_006","3_007","3_008","3_010","3_000","3_000","3_011","3_012","3_013","3_014"},
                74,30,new int[]{90,80,110},new float[]{0.11f,0.27f,0.22f},
                new int[]{500,1300,1200},
                new int[]{1,2,1},new int[]{300,450,1000},
                new int[]{11,13,13},
                new String[]{"1_000","1_001","2_000","3_000","3_001","3_003","3_004","3_006","3_007","3_008","3_010","3_000","3_000","3_011","3_012","3_013","3_014"},
                new String[]{"002","003","009"},//TODO 琴音生日曲
                new String[]{WorldFirstCute.ID, TenThousandVolts.ID},
                new String[]{"skin14","skin12","skin18"},
                new String[]{"","","","","","","","","","",Smile.ID,"","","","","",""},
                new String[]{"fire","fire2","fire3"}
        ));
        idols.put(amao, new Idol(amao,
                new String[]{"skin10","skin11","skin20","skin30","skin31","skin33","skin34","skin36","skin37","skin38","skin40","skin41","skin42","skin43","skin44","skin45"},
                new String[]{"skin10","skin11","skin12","skin14","skin13","skin15","skin16","skin18","skin19","skin17","skin23","skin21","skin22","skin24","skin25","skin26"},
                new String[]{LittlePrince.ID,FirstCrystal.ID, Authenticity.ID, DressedUpInStyle.ID,MoonlitRunway.ID, ChillyBreak.ID, DressedUpInStyle.ID,DressedUpInStyle.ID,DressedUpInStyle.ID,DressedUpInStyle.ID,GetAnswer.ID, DressedUpInStyle.ID, DressedUpInStyle.ID, DressedUpInStyle.ID,EnjoyThreeColor.ID, DressedUpInStyle.ID},
                new String[]{GentlemanHandkerchief.ID, FirstLoveProofMao.ID,DearLittlePrince.ID, InnerLightEarrings.ID,FashionMode.ID, LastSummerMemory.ID, InnerLightEarrings.ID,InnerLightEarrings.ID,InnerLightEarrings.ID,InnerLightEarrings.ID,MyPart.ID, InnerLightEarrings.ID, InnerLightEarrings.ID, InnerLightEarrings.ID,RoundSpring.ID, InnerLightEarrings.ID},
                new IdolType[]{IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.ANOMALY,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.ANOMALY,IdolType.SENSE},
                new IdolStyle[]{IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.FOCUS,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_TUNE,IdolStyle.CONCENTRATION,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_TUNE,IdolStyle.FULL_POWER,IdolStyle.GOOD_TUNE},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{"1","2","3"}, new String[]{}, new String[]{"4"}, new String[]{"4","5"}, new String[]{"4","5","6"}, new String[]{}, new String[]{})),
                new String[]{"1","1","1","1","2","2","2","2","3","3"},
                new String[]{"1","1","1","2","3","4","5","6","8","7","10","2","2","11","12","13"},
                new String[]{"all_001","all_001","all_001","amao_001","amao_002","all_003","all_004","all_005","all_006","all_007","all_002","amao_001","amao_001","all_008","all_009","all_010"},
                new String[]{"no","no","no","3_000","3_001","no","3_004","3_006","3_007","3_008","3_010","3_000","3_000","3_011","3_012","3_013"},
                74,99,new int[]{115,90,90},new float[]{0.25f,0.06f,0.28f},
                new int[]{1300,500,1200},
                new int[]{2,0,2},new int[]{300,450,1000},
                new int[]{13,11,11},
                new String[]{"1_000","1_001","2_000","3_000","3_001","3_003","3_004","3_006","3_007","3_008","3_010","3_000","3_000","3_011","3_012","3_013"},
                new String[]{"002","007","003"},
                new String[]{LoveMyself.ID, CreateYourStyle.ID},
                new String[]{"skin14","skin12","skin18"},
                new String[]{"","","","","","","","","","",Starlight.ID,"","","","",""},
                new String[]{"fire","fire2","fire3"}
        ));
        idols.put(ssmk, new Idol(ssmk,
                new String[]{"skin10","skin11","skin20","skin30","skin31","skin33","skin34","skin36","skin37","skin38","skin40","skin41","skin43","skin44","skin45"},
                new String[]{"skin10","skin11","skin12","skin14","skin13","skin15","skin16","skin17","skin19","skin18","skin23","skin21","skin24","skin25","skin26"},
                new String[]{Friendly.ID,FirstMiss.ID, BraveStep.ID, OneMoreStep.ID, BeyondTheCrossing.ID, AfternoonBreeze.ID, OneMoreStep.ID, OneMoreStep.ID, OneMoreStep.ID,OneMoreStep.ID,FlyAgain.ID, OneMoreStep.ID,OneMoreStep.ID, OneMoreStep.ID,EncounterWithHero.ID},
                new String[]{PinkUniformBracelet.ID,FirstLoveProofSumika.ID, AfterSchoolDoodles.ID, ArcadeLoot.ID,PlasticUmbrellaThatDay.ID, FrogFan.ID, ArcadeLoot.ID, ArcadeLoot.ID, ArcadeLoot.ID,ArcadeLoot.ID,TheWing.ID, ArcadeLoot.ID, ArcadeLoot.ID, ArcadeLoot.ID,TheP.ID},
                new IdolType[]{IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.ANOMALY,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE},
                new IdolStyle[]{IdolStyle.FOCUS,IdolStyle.YARUKI,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.FULL_POWER,IdolStyle.YARUKI,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.GOOD_IMPRESSION,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.GOOD_TUNE},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{"1","2","3"}, new String[]{}, new String[]{"4"}, new String[]{"4","5"}, new String[]{}, new String[]{"6"}, new String[]{})),
                new String[]{"1","1","1","1","2","2","2","3","3","3"},
                new String[]{"1","1","1","2","3","4","5","6","8","7","10","2","11","12","13"},
                new String[]{"all_001","all_001","all_001","ssmk_001","ssmk_002","all_003","all_004","all_005","all_006","all_007","all_002","ssmk_001","all_008","all_009","all_010"},
                new String[]{"no","no","no","3_000","3_001","3_003","3_004","3_006","3_007","3_008","3_010","3_000","3_011","3_012","3_013"},
                72,99,new int[]{90,85,90},new float[]{0.09f,0.28f,0.26f},
                new int[]{600,1500,800},
                new int[]{1,2,1},new int[]{300,450,1000},
                new int[]{12,12,12},
                new String[]{"1_000","1_001","2_000","3_000","3_001","3_003","3_004","3_006","3_007","3_008","3_010","3_000","3_011","3_012","3_013"},
                new String[]{"002","003","009"},
                new String[]{DanceWithYou.ID, OnePersonBallet.ID},
                new String[]{"skin14","skin12","skin18"},
                new String[]{"","","","","","","","","","",Restart.ID,"","","",""},
                new String[]{"fire","fire2","fire3"}
        ));
        idols.put(ttmr, new Idol(ttmr,
                new String[]{"skin10","skin11","skin20","skin30","skin31","skin33","skin34","skin36","skin37","skin38","skin40","skin41","skin42","skin43","skin44","skin45","skin46"},
                new String[]{"skin10","skin11","skin12","skin14","skin13","skin15","skin20","skin17","skin19","skin18","skin23","skin21","skin22","skin24","skin25","skin26","skin27"},
                new String[]{Stubborn.ID, FirstPlace.ID, LoneWolf.ID, EachPath.ID, TangledFeelings.ID, EachPath.ID, EachPath.ID,StruggleHandmade.ID,EachPath.ID,EachPath.ID,SayGoodbyeToDislikeMyself.ID,EachPath.ID,EachPath.ID,EachPath.ID,CareCard.ID,EachPath.ID,UntilHopeReach.ID},
                new String[]{EssentialStainlessSteelBottle.ID,FirstVoiceProofTemari.ID,MyFirstSheetMusic.ID,ProtectiveEarphones.ID,ThisIsMe.ID,ProtectiveEarphones.ID,ProtectiveEarphones.ID,ClumsyBat.ID,ProtectiveEarphones.ID,ProtectiveEarphones.ID,SongToTheSun.ID,ProtectiveEarphones.ID,ProtectiveEarphones.ID,ProtectiveEarphones.ID,CardOfVictory.ID,ProtectiveEarphones.ID,AfterRainGoddess.ID},
                new IdolType[]{IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.ANOMALY,IdolType.SENSE,IdolType.SENSE},
                new IdolStyle[]{IdolStyle.FOCUS,IdolStyle.GOOD_IMPRESSION,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.GOOD_IMPRESSION,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.YARUKI,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.CONCENTRATION,IdolStyle.FOCUS,IdolStyle.GOOD_TUNE},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{}, new String[]{"3"}, new String[]{"3","4"}, new String[]{"3","4","5"}, new String[]{}, new String[]{"6"}, new String[]{})),
                new String[]{"1","1","1","2","2","2","2","3","3","3"},
                new String[]{"1","1","1","2","3","4","5","6","8","7","10","2","2","11","12","13","14"},
                new String[]{"all_001","all_001","all_001","ttmr_001","ttmr_002","all_003","all_004","all_005","all_006","all_007","all_002","ttmr_001","ttmr_001","all_008","all_009","all_010","unit_001"},
                new String[]{"no","no","no","3_000","3_001","3_003","3_004","3_006","3_007","3_008","3_010","3_000","3_000","3_011","3_012","3_013","3_014"},
                76,99,new int[]{110,90,80},new float[]{0.27f,0.26f,0.06f},
                new int[]{1350,950,700},
                new int[]{0,1,0},new int[]{200,450,1000},
                new int[]{13,13,14},
                new String[]{"1_000","1_001","2_000","3_000","3_001","3_003","3_004","3_006","3_007","3_008","3_010","3_000","3_000","3_011","3_012","3_013","3_014"},
                new String[]{"002","003","009"},
                new String[]{ListenToMyTrueHeart.ID, CallMeAnyTime.ID,ReallyNotBad.ID},
                new String[]{"skin14","skin12","skin18"},
                new String[]{"","","","","","","","","","",GoWithTheFlow.ID,"","","","","",""},
                new String[]{"fire","fire2","fire3"}
        ));
        idols.put(jsna, new Idol(jsna,
                new String[]{"skin10","skin20","skin30","skin31","skin37","skin40","skin41","skin43","skin44","skin45"},
                new String[]{"skin10","skin18","skin13","skin12","skin17","skin23","skin21","skin24","skin25","skin26"},
                new String[]{TopIdolInSchool.ID, KingAppear.ID, TopStar.ID,SurgingEmotion.ID,TopStar.ID,DreamStillContinue.ID,TopStar.ID,GiveYou.ID,TopStar.ID,TopStar.ID},//
                new String[]{TeaCaddy.ID,EveryoneDream.ID,AbsoluteNewSelf.ID,FirstStarChallenge.ID,AbsoluteNewSelf.ID,PastLittleStar.ID,AbsoluteNewSelf.ID,DeepLoveForYou.ID,AbsoluteNewSelf.ID,AbsoluteNewSelf.ID},//
                new IdolType[]{IdolType.ANOMALY,IdolType.ANOMALY,IdolType.ANOMALY,IdolType.LOGIC,IdolType.ANOMALY,IdolType.SENSE,IdolType.ANOMALY,IdolType.LOGIC,IdolType.ANOMALY,IdolType.ANOMALY},//
                new IdolStyle[]{IdolStyle.CONCENTRATION,IdolStyle.FULL_POWER,IdolStyle.CONCENTRATION,IdolStyle.GOOD_IMPRESSION,IdolStyle.CONCENTRATION,IdolStyle.GOOD_TUNE,IdolStyle.CONCENTRATION,IdolStyle.YARUKI,IdolStyle.CONCENTRATION,IdolStyle.CONCENTRATION},//
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{},new String[]{}, new String[]{"1"}, new String[]{"1"}, new String[]{}, new String[]{}, new String[]{"2"}, new String[]{}, new String[]{})),
                new String[]{"1","2","3","3","3","4","5","5","6","6"},//
                new String[]{"1","1","2","3","8","10","2","11","12","13"},//song
                new String[]{"all_001","all_001","jsna_001","jsna_002","all_006","all_002","jsna_001","all_008","all_009","all_010"},//bgm
                new String[]{"no","no","3_000","3_001","3_007","3_010","3_000","3_011","3_012","3_013"},//video
                74,99,new int[]{175,125,140},new float[]{0.16f,0.06f,0.22f},
                new int[]{1000,800,1200},
                new int[]{1,0,2},new int[]{200,450,900},//PlanType PlanRequire
                new int[]{14,14,14},//SpVoicesNum
                new String[]{"1_000","2_000","3_000","3_001","3_007","3_010","3_000","3_011","3_012","3_013"},//lives
                new String[]{"002","003","009"},//bossSongs,
                new String[]{ListenToMyTrueHeart.ID, CallMeAnyTime.ID},
                new String[]{"skin17","skin12","skin18"},
                new String[]{"","","","","",ServiceSpirit.ID,"","","",""},
                new String[]{"fire","fire2","fire3"}
        ));
        idols.put(hmsz, new Idol(hmsz,
                new String[]{"skin10","skin20","skin30"},
                new String[]{"skin10","skin17","skin27"},
                new String[]{FreeWoman.ID, RestAndWalk.ID, SceneryOnHouse.ID},
                new String[]{DreamOfTheDescription.ID,SwayWindHairpin.ID,SomethingLongHold.ID},
                new IdolType[]{IdolType.ANOMALY,IdolType.ANOMALY,IdolType.ANOMALY},
                new IdolStyle[]{IdolStyle.FULL_POWER,IdolStyle.CONCENTRATION,IdolStyle.FULL_POWER},//
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{},new String[]{"1"}, new String[]{"1","2"}, new String[]{"1","2","3"}, new String[]{"1","2","3"}, new String[]{"1","2","3","4"}, new String[]{"1","2","3","4","5"}, new String[]{"6"}, new String[]{})),//制作人评价的顺序
                new String[]{"1","2","2","2","2","2","2","2","3","3"},
                new String[]{"1","1","2"},//song
                new String[]{"all_001","all_001","hmsz_001"},//bgm
                new String[]{"no","no","3_000"},//video
                72,99,new int[]{85,115,125},new float[]{0.30f,0.10f,0.18f},
                new int[]{1350,700,950},
                new int[]{0,2,0},new int[]{300,450,1000},
                new int[]{14,14,14},//SpVoicesNum // TODO 更新
                new String[]{"1_000","2_000","3_000"},//lives
                new String[]{"002","001","009"},//bossSongs,
                new String[]{AnotherDream.ID, SummerSleepy.ID},
                new String[]{"skin17","skin10","skin27"},
                new String[]{"","",""},
                new String[]{"fire","fire2","fire3"}
        ));

        idols.get(hume).setAnotherThreeSizeRequires(new int[]{950,1350,700});

        hmszData = new Idol(hmsz,
                new String[]{"skin10","skin20","skin30","skin40","skin41"},
                new String[]{"skin10","skin18","skin13","skin23","skin21"},
                new String[]{TopIdolInSchool.ID, KingAppear.ID, TopStar.ID,DreamStillContinue.ID,TopStar.ID},//
                new String[]{TeaCaddy.ID,EveryoneDream.ID,AbsoluteNewSelf.ID,PastLittleStar.ID,AbsoluteNewSelf.ID},//
                new IdolType[]{IdolType.ANOMALY,IdolType.ANOMALY,IdolType.ANOMALY,IdolType.SENSE,IdolType.ANOMALY},//
                new IdolStyle[]{IdolStyle.CONCENTRATION,IdolStyle.FULL_POWER,IdolStyle.CONCENTRATION,IdolStyle.GOOD_TUNE,IdolStyle.CONCENTRATION},//
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{},new String[]{"1"}, new String[]{"1","2"}, new String[]{"1","2","3"}, new String[]{"1","2","3"}, new String[]{"1","2","3","4"}, new String[]{"1","2","3","4","5"}, new String[]{"6"}, new String[]{})),//制作人评价的顺序
                new String[]{"1","2","2","2","2","2","2","2","3","3"},
                new String[]{"1","1","2","10","2"},//song
                new String[]{"all_001","all_001","jsna_001","all_002","jsna_001"},//bgm
                new String[]{"no","no","3_000","3_010","3_000"},//video
                72,99,new int[]{85,115,125},new float[]{0.30f,0.10f,0.18f},
                new int[]{1350,700,950},
                new int[]{0,2,0},new int[]{300,450,1000},
                new int[]{14,14,14},//SpVoicesNum
                new String[]{"1_000","2_000","3_000","3_010","3_000"},//lives
                new String[]{"002","002","009"},//bossSongs,
                new String[]{"gkmasMod:NationalIdol", "gkmasMod:CharmGaze"},
                new String[]{"skin10","skin12","skin17"},
                new String[]{"","","",ServiceSpirit.ID,""},
                new String[]{"fire","fire2","fire3"}
        );

        otherIdols.put(prod,new Idol(prod,
                new String[]{"skin10"},
                new String[]{"skin10"},
                new String[]{GachaAgain.ID},//
                new String[]{FreeTicket.ID},//
                new IdolType[]{IdolType.PRODUCE},//
                new IdolStyle[]{IdolStyle.CONCENTRATION},//
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{},new String[]{}, new String[]{"1"}, new String[]{"1"}, new String[]{}, new String[]{}, new String[]{"2"}, new String[]{}, new String[]{})),
                new String[]{"1","2","3","3","3","4","5","5","6","6"},//
                new String[]{"1"},//song
                new String[]{"all_001"},//bgm
                new String[]{"no"},//video
                80,150,new int[]{120,110,120},new float[]{0.2f,0.2f,0.2f},
                new int[]{1200,800,1000},
                new int[]{2,0,1},new int[]{400,700,1000},//PlanType PlanRequire
                new int[]{14,14,14},//SpVoicesNum
                new String[]{"1_000"},//lives
                new String[]{"002","002","009"},//bossSongs,
                new String[]{"gkmasMod:NationalIdol", "gkmasMod:CharmGaze"},
                new String[]{"skin10","skin10","skin10"},
                new String[]{""},
                new String[]{"fire","fire","fire"}
        ));
        otherIdols.put(andk,new Idol(andk,
                new String[]{"skin10"},
                new String[]{"skin10"},
                new String[]{BaseExpression.ID},//
                new String[]{LadyDiary.ID},//
                new IdolType[]{IdolType.LOGIC},//
                new IdolStyle[]{IdolStyle.YARUKI},//
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{},new String[]{}, new String[]{"1"}, new String[]{"1"}, new String[]{}, new String[]{}, new String[]{"2"}, new String[]{}, new String[]{})),
                new String[]{"1","2","3","3","3","4","5","5","6","6"},//
                new String[]{"1"},//song
                new String[]{"all_001"},//bgm
                new String[]{"no"},//video
                68,179,new int[]{80,110,125},new float[]{0.08f,0.25f,0.20f},
                new int[]{700,1350,950},
                new int[]{0,2,1},new int[]{200,450,1000},
                new int[]{10,11,12},
                new String[]{"1_000"},//lives
                new String[]{"002","002","009"},//bossSongs,
                new String[]{"gkmasMod:NationalIdol", "gkmasMod:CharmGaze"},
                new String[]{"skin10","skin10","skin10"},
                new String[]{""},
                new String[]{"fire","fire","fire"}
        ));
        otherIdols.put(sson,new Idol(sson,
                new String[]{"skin10"},
                new String[]{"skin10"},
                new String[]{BaseProvocation.ID},//
                new String[]{SisterComplex.ID},//
                new IdolType[]{IdolType.SENSE},//
                new IdolStyle[]{IdolStyle.GOOD_TUNE},//
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{},new String[]{}, new String[]{"1"}, new String[]{"1"}, new String[]{}, new String[]{}, new String[]{"2"}, new String[]{}, new String[]{})),
                new String[]{"1","2","3","3","3","4","5","5","6","6"},//
                new String[]{"1"},//song
                new String[]{"all_001"},//bgm
                new String[]{"no"},//video
                72,129,new int[]{150,105,140},new float[]{0.3f,0.10f,0.26f},
                new int[]{1200,800,1000},
                new int[]{2,0,1},new int[]{200,450,1000},//PlanType PlanRequire
                new int[]{14,14,14},//SpVoicesNum
                new String[]{"1_000"},//lives
                new String[]{"002","002","009"},//bossSongs,
                new String[]{"gkmasMod:NationalIdol", "gkmasMod:CharmGaze"},
                new String[]{"skin10","skin10","skin10"},
                new String[]{""},
                new String[]{"fire","fire","fire"}
        ));
        otherIdols.put(sgka,new Idol(sgka,
                new String[]{"skin10"},
                new String[]{"skin10"},
                new String[]{FightWill.ID},//
                new String[]{TopIdolPride.ID},//
                new IdolType[]{IdolType.ANOMALY},//
                new IdolStyle[]{IdolStyle.FULL_POWER},//
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{},new String[]{}, new String[]{"1"}, new String[]{"1"}, new String[]{}, new String[]{}, new String[]{"2"}, new String[]{}, new String[]{})),
                new String[]{"1","2","3","3","3","4","5","5","6","6"},//
                new String[]{"1"},//song
                new String[]{"all_001"},//bgm
                new String[]{"no"},//video
                77,149,new int[]{150,105,160},new float[]{0.25f,0.10f,0.3f},
                new int[]{1000,800,1200},
                new int[]{1,0,2},new int[]{200,450,1000},//PlanType PlanRequire
                new int[]{14,14,14},//SpVoicesNum
                new String[]{"1_000"},//lives
                new String[]{"002","002","009"},//bossSongs,
                new String[]{"gkmasMod:NationalIdol", "gkmasMod:CharmGaze"},
                new String[]{"skin10","skin10","skin10"},
                new String[]{""},
                new String[]{"fire","fire","fire"}
        ));
        otherIdols.put(krnh,new Idol(krnh,
                new String[]{"skin10","skin11"},
                new String[]{"skin10","skin11"},
                new String[]{TopIdolInSchool.ID,TopIdolInSchool.ID},//
                new String[]{TeaCaddy.ID,TeaCaddy.ID},//
                new IdolType[]{IdolType.ANOMALY,IdolType.LOGIC},//
                new IdolStyle[]{IdolStyle.CONCENTRATION,IdolStyle.YARUKI},//
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{},new String[]{}, new String[]{"1"}, new String[]{"1"}, new String[]{}, new String[]{}, new String[]{"2"}, new String[]{}, new String[]{})),
                new String[]{"1","2","3","3","3","4","5","5","6","6"},//
                new String[]{"1","1"},//song
                new String[]{"all_001","all_001"},//bgm
                new String[]{"no","no"},//video
                72,99,new int[]{150,105,140},new float[]{0.3f,0.10f,0.26f},
                new int[]{1200,800,1000},
                new int[]{2,0,1},new int[]{200,450,1000},//PlanType PlanRequire
                new int[]{14,14,14},//SpVoicesNum
                new String[]{"1_000","1_000"},//lives
                new String[]{"002","002","009"},//bossSongs,
                new String[]{"gkmasMod:NationalIdol", "gkmasMod:CharmGaze"},
                new String[]{"skin10","skin10","skin10"},
                new String[]{"",""},
                new String[]{"fire","fire","fire"}
        ));
        otherIdols.put(mshy,new Idol(mshy,
                new String[]{"skin10"},
                new String[]{"skin10"},
                new String[]{TopIdolInSchool.ID},//
                new String[]{TeaCaddy.ID},//
                new IdolType[]{IdolType.SENSE},//
                new IdolStyle[]{IdolStyle.YARUKI},//
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{},new String[]{}, new String[]{"1"}, new String[]{"1"}, new String[]{}, new String[]{}, new String[]{"2"}, new String[]{}, new String[]{})),
                new String[]{"1","2","3","3","3","4","5","5","6","6"},//
                new String[]{"1"},//song
                new String[]{"all_001"},//bgm
                new String[]{"no"},//video
                72,99,new int[]{150,105,140},new float[]{0.3f,0.10f,0.26f},
                new int[]{1200,800,1000},
                new int[]{2,0,1},new int[]{200,450,1000},//PlanType PlanRequire
                new int[]{14,14,14},//SpVoicesNum
                new String[]{"1_000"},//lives
                new String[]{"002","002","009"},//bossSongs,
                new String[]{"gkmasMod:NationalIdol", "gkmasMod:CharmGaze"},
                new String[]{"skin10","skin10","skin10"},
                new String[]{""},
                new String[]{"fire","fire","fire"}
        ));
        otherIdols.put(nasr,new Idol(nasr,
                new String[]{"skin10"},
                new String[]{"skin10"},
                new String[]{TopIdolInSchool.ID},//
                new String[]{TeaCaddy.ID},//
                new IdolType[]{IdolType.LOGIC},//
                new IdolStyle[]{IdolStyle.GOOD_IMPRESSION},//
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{},new String[]{}, new String[]{"1"}, new String[]{"1"}, new String[]{}, new String[]{}, new String[]{"2"}, new String[]{}, new String[]{})),
                new String[]{"1","2","3","3","3","4","5","5","6","6"},//
                new String[]{"1"},//song
                new String[]{"all_001"},//bgm
                new String[]{"no"},//video
                72,99,new int[]{150,105,140},new float[]{0.3f,0.10f,0.26f},
                new int[]{1200,800,1000},
                new int[]{2,0,1},new int[]{200,450,1000},//PlanType PlanRequire
                new int[]{14,14,14},//SpVoicesNum
                new String[]{"1_000"},//lives
                new String[]{"002","002","009"},//bossSongs,
                new String[]{"gkmasMod:NationalIdol", "gkmasMod:CharmGaze"},
                new String[]{"skin10","skin10","skin10"},
                new String[]{""},
                new String[]{"fire","fire","fire"}
        ));
        otherIdols.put(arnm,new Idol(arnm,
                new String[]{"skin10"},
                new String[]{"skin10"},
                new String[]{Zap.ID},//
                new String[]{CrackedCoreNew.ID},//
                new IdolType[]{IdolType.LOGIC},//
                new IdolStyle[]{IdolStyle.GOOD_IMPRESSION},//
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{},new String[]{}, new String[]{"1"}, new String[]{"1"}, new String[]{}, new String[]{}, new String[]{"2"}, new String[]{}, new String[]{})),
                new String[]{"1","2","3","3","3","4","5","5","6","6"},//
                new String[]{"1"},//song
                new String[]{"all_001"},//bgm
                new String[]{"no"},//video
                75,99,new int[]{150,105,140},new float[]{0.3f,0.10f,0.26f},
                new int[]{1200,800,1000},
                new int[]{2,0,1},new int[]{200,450,1000},//PlanType PlanRequire
                new int[]{14,14,14},//SpVoicesNum
                new String[]{"1_000"},//lives
                new String[]{"002","002","009"},//bossSongs,
                new String[]{"gkmasMod:NationalIdol", "gkmasMod:CharmGaze"},
                new String[]{"skin10","skin10","skin10"},
                new String[]{""},
                new String[]{"fire","fire","fire"}
        ));

    }

}
