package gkmasmod.utils;


import gkmasmod.cards.anomaly.BeyondTheCrossing;
import gkmasmod.cards.anomaly.KingAppear;
import gkmasmod.cards.anomaly.TopIdolInSchool;
import gkmasmod.cards.anomaly.TopStar;
import gkmasmod.cards.free.*;
import gkmasmod.cards.logic.*;
import gkmasmod.cards.sense.*;
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
     * 秦谷美玲 Hataya Misuzu
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
     * 雨夜 燕 Amayo Tubame
     */
    public static final String atbm = "atbm";

    /**
     * 雨夜 燕 Kaya Rinha
     */
    public static final String krha = "krha";

    //public static final String[] idolNames = {shro, kllj, kcna, hume, hski, hrnm, fktn, amao, ssmk, ttmr, hmsz, jsna, nasr};
    public static final String[] idolNames = {shro, kllj, kcna,hume,hski,hrnm,fktn,amao,ssmk,ttmr,jsna};

    public static HashMap<String, Idol> idols = new HashMap<>();

    public static Idol getIdol(String name) {
        return idols.get(name);
    }

    public static Idol getIdol(int index) {
        return idols.get(idolNames[index]);
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
    // 37 Animate联动换皮
    // 38 圣诞换皮

    static {
        idols.put(shro, new Idol(shro,
                new String[]{"skin10","skin11","skin20","skin30","skin31","skin33","skin34","skin36","skin38"},
                new String[]{"skin10","skin11","skin12","skin14","skin13","skin15","skin16","skin17","skin18"},
                new String[]{HighlyEducatedIdol.ID,FirstFun.ID, LovesTheStruggle.ID, SeriousHobby.ID, SwayingOnTheBus.ID, SeriousHobby.ID, SeriousHobby.ID,MakeExactSignboard.ID, SeriousHobby.ID},
                new String[]{UltimateSleepMask.ID,FirstLoveProofHiro.ID, BeginnerGuideForEveryone.ID, SidewalkResearchNotes.ID, TowardsAnUnseenWorld.ID,SidewalkResearchNotes.ID,SidewalkResearchNotes.ID,NaughtyPuppet.ID,SidewalkResearchNotes.ID},
                new IdolType[]{IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC,IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC,IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC},
                new IdolStyle[]{IdolStyle.YARUKI,IdolStyle.FOCUS,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.FOCUS,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.GOOD_TUNE,IdolStyle.YARUKI},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{"1","2","3"}, new String[]{"4"}, new String[]{"4"}, new String[]{"4","5"}, new String[]{}, new String[]{"6"}, new String[]{})),
                new String[]{"1","1","1","1","2","2","2","3","3","3"},
                new String[]{"1","1","1","2","3","4","5","6","7"},
                new String[]{"all_001","all_001","all_001","shro_001","shro_002","all_003","all_004","all_005","all_006"},
                new String[]{"no","no","no","3_000","3_001","3_003","3_004","3_006","3_008"},
                66,99,new int[]{115,115,80},new float[]{0.27f,0.23f,0.10f},
                new int[]{1200,1000,800},
                new int[]{0,1,0},new int[]{300,450,1000},
                new int[]{9,11,13},
                new String[]{"1_000","1_001","2_000","3_000","3_001","3_003","3_004","3_006","3_008"},
                new String[]{"002","003","006"},
                new String[]{EvenIfDreamNotRealize.ID, NecessaryContrast.ID},
                new String[]{"skin10","skin12","skin17"}
        ));
        idols.put(kllj, new Idol(kllj,
                new String[]{"skin10","skin11","skin20","skin30","skin33","skin34","skin36","skin38"},
                new String[]{"skin10","skin11","skin12","skin14","skin13","skin15","skin17","skin18"},
                new String[]{ReservedGirl.ID, FirstGround.ID,PureWhiteFairy.ID, NotAfraidAnymore.ID, NotAfraidAnymore.ID, FirstRamune.ID,NotAfraidAnymore.ID,WithLove.ID},
                new String[]{GreenUniformBracelet.ID, FirstHeartProofLilja.ID,MemoryBot.ID, DreamLifeLog.ID, DreamLifeLog.ID, SparklingInTheBottle.ID, DreamLifeLog.ID,BeyondTheSea.ID},
                new IdolType[]{IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC,IdolType.LOGIC},
                new IdolStyle[]{IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.YARUKI},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{}, new String[]{"3"}, new String[]{"3","4"}, new String[]{"3","4","5"}, new String[]{}, new String[]{"6"}, new String[]{})),
                new String[]{"2","2","2","3","3","3","3","4","4","4"},
                new String[]{"1","1","1","2","4","5","6","7"},
                new String[]{"all_001","all_001","all_001","kllj_001","all_003","all_004","all_005","all_006"},
                new String[]{"no","no","no","3_000","3_003","3_004","3_006","3_008"},
                74,99,new int[]{75,75,105},new float[]{0.18f,0.25f,0.21f},
                new int[]{800,1000,1200},
                new int[]{1,0,2},new int[]{200,450,1000},
                new int[]{9,12,11},
                new String[]{"1_000","1_001","2_000","3_000","3_003","3_004","3_006","3_008"},
                new String[]{"002","005","009"},
                new String[]{YoungDream.ID, GrowthProcess.ID},
                new String[]{"skin10","skin12","skin17"}
        ));
        idols.put(kcna, new Idol(kcna,
                new String[]{"skin10","skin11","skin20","skin30","skin31","skin33","skin34","skin35","skin36","skin38"},
                new String[]{"skin10","skin11","skin12","skin14","skin13","skin15","skin20","skin17","skin18","skin19"},
                new String[]{FullOfEnergy.ID,FirstColor.ID, Wholeheartedly.ID, DebutStageForTheLady.ID, WelcomeToTeaParty.ID, DebutStageForTheLady.ID, DebutStageForTheLady.ID,AQuickSip.ID,GonnaTrickYou.ID,DebutStageForTheLady.ID},
                new String[]{WishFulfillmentAmulet.ID, FirstHeartProofChina.ID,CheerfulHandkerchief.ID,SecretTrainingCardigan.ID,HeartFlutteringCup.ID,SecretTrainingCardigan.ID,SecretTrainingCardigan.ID,EnjoyAfterHotSpring.ID,CursedSpirit.ID,SecretTrainingCardigan.ID},
                new IdolType[]{IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC,IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.LOGIC},
                new IdolStyle[]{IdolStyle.YARUKI,IdolStyle.GOOD_TUNE,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.GOOD_TUNE,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.YARUKI},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{"1","2"}, new String[]{"1","2","3"}, new String[]{}, new String[]{"4"}, new String[]{"4","5"}, new String[]{}, new String[]{})),
                new String[]{"2","2","2","2","2","3","3","3","4","4"},
                new String[]{"1","1","1","2","3","4","5","1","6","7"},
                new String[]{"all_001","all_001","all_001","kcna_001","kcna_002","all_003","all_004","all_001","all_005","all_007"},
                new String[]{"no","no","no","3_000","3_001","3_003","3_004","3_005","no","3_008"},
                68,200,new int[]{75,110,115},new float[]{0.10f,0.28f,0.22f},
                new int[]{700,1350,950},
                new int[]{0,2,1},new int[]{200,450,1000},
                new int[]{10,11,12},
                new String[]{"1_000","1_001","2_000","3_000","3_001","3_003","3_004","3_005","3_006","3_008"},
                new String[]{"002","003","009"},
                new String[]{SlowGrowth.ID, SpecialTreasure.ID},
                new String[]{"skin10","skin12","skin17"}

        ));
        idols.put(hume, new Idol(hume,
                new String[]{"skin10","skin20","skin30","skin33","skin34","skin36","skin38"},
                new String[]{"skin10","skin11","skin12","skin13","skin10","skin18","skin17"},
                new String[]{UntappedPotential.ID, DefeatBigSister.ID, BigOnigiri.ID, BigOnigiri.ID, BigOnigiri.ID,BigOnigiri.ID,ShareSomethingWithYou.ID},
                new String[]{TechnoDog.ID, ShibaInuPochette.ID, RollingSourceOfEnergy.ID, RollingSourceOfEnergy.ID, RollingSourceOfEnergy.ID,RollingSourceOfEnergy.ID,ChristmasLion.ID},
                new IdolType[]{IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.SENSE},
                new IdolStyle[]{IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.GOOD_TUNE},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{"1","2","3"}, new String[]{"1","2","3"}, new String[]{"1","2","3","4"}, new String[]{}, new String[]{"5"}, new String[]{"5","6"}, new String[]{})),
                new String[]{"2","2","2","2","2","2","3","3","3","3"},
                new String[]{"1","1","2","4","5","6","7"},
                new String[]{"all_001","all_001","hume_001","all_003","all_004","all_005","all_007"},
                new String[]{"no","no","3_000","3_003","3_004","3_006","3_008"},
                72,99,new int[]{75,85,85},new float[]{0.23f,0.28f,0.15f},
                new int[]{1000,1200,800},
                new int[]{1,0,1},new int[]{300,450,1000},
                new int[]{12,14,14},
                new String[]{"1_000","2_000","3_000","3_003","3_004","3_006","3_008"},
                new String[]{"002","005","007"},
                new String[]{SisterHelp.ID,OverwhelmingNumbers.ID},
                new String[]{"skin10","skin12","skin17"}
        ));
        idols.put(hski, new Idol(hski,
                new String[]{"skin10","skin11","skin20","skin30","skin31","skin33","skin34","skin36","skin37","skin38"},
                new String[]{"skin10","skin11","skin12","skin14","skin13","skin15","skin20","skin17","skin18","skin19"},
                new String[]{RisingStar.ID, FirstFuture.ID, NeverYieldFirst.ID, NeverLose.ID, Pow.ID, NeverLose.ID, GoldfishScoopingChallenge.ID,NeverLose.ID,VeryEasy.ID,NeverLose.ID},
                new String[]{RoaringLion.ID,FirstVoiceProofSaki.ID,SakiCompleteMealRecipe.ID,TogetherInBattleTowel.ID,WinningDetermination.ID,TogetherInBattleTowel.ID,UndefeatedPoi.ID,TogetherInBattleTowel.ID,AnimateEquipment.ID,TogetherInBattleTowel.ID},
                new IdolType[]{IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE},
                new IdolStyle[]{IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE,IdolStyle.FOCUS,IdolStyle.GOOD_TUNE},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{"1","2","3"}, new String[]{"1","2","3"}, new String[]{}, new String[]{"4"}, new String[]{}, new String[]{"5"}, new String[]{})),
                new String[]{"1","1","1","1","1","2","2","3","3","3"},
                new String[]{"1","1","1","2","3","4","5","6","8","7"},
                new String[]{"all_001","all_001","all_001","hski_001","hski_002","all_003","all_004","all_005","all_006","all_007"},
                new String[]{"no","no","no","3_000","3_001","3_003","no","3_006","3_007","3_008"},
                76,99,new int[]{100,100,105},new float[]{0.18f,0.18f,0.20f},
                new int[]{800,1000,1200},
                new int[]{1,0,2},new int[]{200,450,900},
                new int[]{10,13,12},
                new String[]{"1_000","1_001","2_000","3_000","3_001","3_003","3_004","3_006","3_007","3_008"},
                new String[]{"002","003","008"},
                new String[]{TrueLateBloomer.ID,MotherAI.ID},
                new String[]{"skin10","skin12","skin17"}
        ));
        idols.put(hrnm, new Idol(hrnm,
                new String[]{"skin10","skin11","skin20","skin30","skin31","skin33","skin34","skin35","skin36","skin38"},
                new String[]{"skin10","skin11","skin12","skin14","skin13","skin15","skin16","skin18","skin19","skin17"},
                new String[]{Accommodating.ID,FirstFriend.ID,SupportiveFeelings.ID, SenseOfDistance.ID,SeeYouTomorrow.ID, CumulusCloudsAndYou.ID, SenseOfDistance.ID,RefreshingBreak.ID,SenseOfDistance.ID,SenseOfDistance.ID},
                new String[]{RegularMakeupPouch.ID,FirstHeartProofRinami.ID,TreatForYou.ID, LifeSizeLadyLip.ID,ItsPerfect.ID, SummerToShareWithYou.ID, LifeSizeLadyLip.ID,ClapClapFan.ID,LifeSizeLadyLip.ID,LifeSizeLadyLip.ID},
                new IdolType[]{IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE},
                new IdolStyle[]{IdolStyle.FOCUS,IdolStyle.YARUKI,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE,IdolStyle.FOCUS,IdolStyle.YARUKI,IdolStyle.FOCUS,IdolStyle.FOCUS},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{"1","2"}, new String[]{"1","2","3"}, new String[]{}, new String[]{"4"}, new String[]{"4","5"}, new String[]{"4","5","6"}, new String[]{})),
                new String[]{"1","1","1","1","1","2","2","2","2","2"},
                new String[]{"1","1","1","2","3","4","5","1","6","7"},
                new String[]{"all_001","all_001","all_001","hrnm_001","hrnm_002","all_003","all_004","all_001","all_005","all_007"},
                new String[]{"no","no","no","3_000","3_001","3_003","3_004","3_005","3_006","3_008"},
                72,99,new int[]{85,95,85},new float[]{0.11f,0.245f,0.285f},
                new int[]{800,1000,1200},
                new int[]{0,1,2},new int[]{200,450,1000},
                new int[]{12,12,12},
                new String[]{"1_000","1_001","2_000","3_000","3_001","3_003","3_004","3_005","3_006","3_008"},
                new String[]{"002","006","003"},
                new String[]{SaySomethingToYou.ID, BlueSenpaiHelp.ID},
                new String[]{"skin10","skin12","skin17"}

        ));
        idols.put(fktn, new Idol(fktn,
                new String[]{"skin10","skin11","skin20","skin30","skin31","skin33","skin34","skin36","skin38"},
                new String[]{"skin10","skin11","skin12","skin14","skin13","skin15","skin19","skin18","skin17"},
                new String[]{Arbeiter.ID, FirstReward.ID, ColorfulCute.ID, NoDistractions.ID, FullAdrenaline.ID, NoDistractions.ID, SummerEveningSparklers.ID,NoDistractions.ID,HappyChristmas.ID},
                new String[]{HandmadeMedal.ID,FirstVoiceProofKotone.ID,FavoriteSneakers.ID,PigDreamPiggyBank.ID,UltimateSourceOfHappiness.ID,PigDreamPiggyBank.ID,CracklingSparkler.ID,PigDreamPiggyBank.ID,FreeLoveMax.ID},
                new IdolType[]{IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC,IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC},
                new IdolStyle[]{IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.YARUKI,IdolStyle.GOOD_IMPRESSION,IdolStyle.YARUKI},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{"1","2","3"}, new String[]{}, new String[]{"4"}, new String[]{"4","5"}, new String[]{"4","5","6"}, new String[]{}, new String[]{})),
                new String[]{"1","1","1","1","2","2","2","2","3","3"},
                new String[]{"1","1","1","2","3","4","5","6","7"},
                new String[]{"all_001","all_001","all_001","fktn_001","fktn_002","all_003","all_004","all_005","all_007"},
                new String[]{"no","no","no","3_000","3_001","3_003","3_004","3_006","3_008"},
                74,30,new int[]{90,80,110},new float[]{0.11f,0.27f,0.22f},
                new int[]{500,1300,1200},
                new int[]{1,2,1},new int[]{300,450,1000},
                new int[]{11,13,13},
                new String[]{"1_000","1_001","2_000","3_000","3_001","3_003","3_004","3_006","3_008"},
                new String[]{"002","003","006"},
                new String[]{WorldFirstCute.ID, TenThousandVolts.ID},
                new String[]{"skin10","skin12","skin17"}
        ));
        idols.put(amao, new Idol(amao,
                new String[]{"skin10","skin11","skin20","skin30","skin31","skin33","skin34","skin36","skin38"},
                new String[]{"skin10","skin11","skin12","skin14","skin13","skin15","skin16","skin18","skin17"},
                new String[]{LittlePrince.ID,FirstCrystal.ID, Authenticity.ID, DressedUpInStyle.ID,MoonlitRunway.ID, ChillyBreak.ID, DressedUpInStyle.ID,DressedUpInStyle.ID,DressedUpInStyle.ID},
                new String[]{GentlemanHandkerchief.ID, FirstLoveProofMao.ID,DearLittlePrince.ID, InnerLightEarrings.ID,FashionMode.ID, LastSummerMemory.ID, InnerLightEarrings.ID,InnerLightEarrings.ID,InnerLightEarrings.ID},
                new IdolType[]{IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE},
                new IdolStyle[]{IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.FOCUS,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_TUNE},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{"1","2","3"}, new String[]{}, new String[]{"4"}, new String[]{"4","5"}, new String[]{"4","5","6"}, new String[]{}, new String[]{})),
                new String[]{"1","1","1","1","2","2","2","2","3","3"},
                new String[]{"1","1","1","2","3","4","5","6","7"},
                new String[]{"all_001","all_001","all_001","amao_001","amao_002","all_003","all_004","all_005","all_007"},
                new String[]{"no","no","no","3_000","3_001","no","3_004","3_006","3_008"},
                74,99,new int[]{115,90,90},new float[]{0.25f,0.06f,0.28f},
                new int[]{1300,500,1200},
                new int[]{2,0,2},new int[]{300,450,1000},
                new int[]{13,11,11},
                new String[]{"1_000","1_001","2_000","3_000","3_001","3_003","3_004","3_006","3_008"},
                new String[]{"002","007","003"},
                new String[]{LoveMyself.ID, CreateYourStyle.ID},
                new String[]{"skin10","skin12","skin17"}
        ));
        idols.put(ssmk, new Idol(ssmk,
                new String[]{"skin10","skin11","skin20","skin30","skin31","skin33","skin34","skin36","skin38"},
                new String[]{"skin10","skin11","skin12","skin14","skin13","skin15","skin16","skin17","skin18"},
                new String[]{Friendly.ID,FirstMiss.ID, BraveStep.ID, OneMoreStep.ID, BeyondTheCrossing.ID, AfternoonBreeze.ID, OneMoreStep.ID, OneMoreStep.ID, OneMoreStep.ID},
                new String[]{PinkUniformBracelet.ID,FirstLoveProofSumika.ID, AfterSchoolDoodles.ID, ArcadeLoot.ID,PlasticUmbrellaThatDay.ID, FrogFan.ID, ArcadeLoot.ID, ArcadeLoot.ID, ArcadeLoot.ID},
                new IdolType[]{IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.ANOMALY,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE},
                new IdolStyle[]{IdolStyle.FOCUS,IdolStyle.YARUKI,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.FULL_POWER,IdolStyle.YARUKI,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.FOCUS},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{"1","2","3"}, new String[]{}, new String[]{"4"}, new String[]{"4","5"}, new String[]{}, new String[]{"6"}, new String[]{})),
                new String[]{"1","1","1","1","2","2","2","3","3","3"},
                new String[]{"1","1","1","2","3","4","5","6","7"},
                new String[]{"all_001","all_001","all_001","ssmk_001","ssmk_002","all_003","all_004","all_005","all_007"},
                new String[]{"no","no","no","3_000","3_001","3_003","3_004","3_006","3_008"},
                72,99,new int[]{90,85,90},new float[]{0.09f,0.28f,0.26f},
                new int[]{600,1500,800},
                new int[]{1,2,1},new int[]{300,450,1000},
                new int[]{12,12,12},
                new String[]{"1_000","1_001","2_000","3_000","3_001","3_003","3_004","3_006","3_008"},
                new String[]{"002","003","009"},
                new String[]{DanceWithYou.ID, OnePersonBallet.ID},
                new String[]{"skin10","skin12","skin17"}
        ));
        idols.put(ttmr, new Idol(ttmr,
                new String[]{"skin10","skin11","skin20","skin30","skin31","skin33","skin34","skin36","skin38"},
                new String[]{"skin10","skin11","skin12","skin14","skin13","skin15","skin20","skin17","skin18"},
                new String[]{Stubborn.ID, FirstPlace.ID, LoneWolf.ID, EachPath.ID, TangledFeelings.ID, EachPath.ID, EachPath.ID,StruggleHandmade.ID,EachPath.ID},
                new String[]{EssentialStainlessSteelBottle.ID,FirstVoiceProofTemari.ID,MyFirstSheetMusic.ID,ProtectiveEarphones.ID,ThisIsMe.ID,ProtectiveEarphones.ID,ProtectiveEarphones.ID,ClumsyBat.ID,ProtectiveEarphones.ID},
                new IdolType[]{IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE},
                new IdolStyle[]{IdolStyle.FOCUS,IdolStyle.GOOD_IMPRESSION,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.GOOD_IMPRESSION,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.YARUKI,IdolStyle.FOCUS},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{}, new String[]{"3"}, new String[]{"3","4"}, new String[]{"3","4","5"}, new String[]{}, new String[]{"6"}, new String[]{})),
                new String[]{"1","1","1","2","2","2","2","3","3","3"},
                new String[]{"1","1","1","2","3","4","5","6","7"},
                new String[]{"all_001","all_001","all_001","ttmr_001","ttmr_002","all_003","all_004","all_005","all_007"},
                new String[]{"no","no","no","3_000","3_001","3_003","3_004","3_006","3_008"},
                76,99,new int[]{110,90,80},new float[]{0.27f,0.26f,0.06f},
                new int[]{1350,950,700},
                new int[]{0,1,0},new int[]{200,450,1000},
                new int[]{13,13,14},
                new String[]{"1_000","1_001","2_000","3_000","3_001","3_003","3_004","3_006","3_008"},
                new String[]{"002","003","009"},
                new String[]{ListenToMyTrueHeart.ID, CallMeAnyTime.ID},
                new String[]{"skin10","skin12","skin17"}
        ));
        idols.put(jsna, new Idol(jsna,
                new String[]{"skin10","skin20","skin30"},
                new String[]{"skin10","skin18","skin13"},
                new String[]{TopIdolInSchool.ID, KingAppear.ID, TopStar.ID},//
                new String[]{TeaCaddy.ID,EveryoneDream.ID,AbsoluteNewSelf.ID},//
                new IdolType[]{IdolType.ANOMALY,IdolType.ANOMALY,IdolType.ANOMALY},
                new IdolStyle[]{IdolStyle.CONCENTRATION,IdolStyle.FULL_POWER,IdolStyle.CONCENTRATION},//
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{},new String[]{}, new String[]{"1"}, new String[]{"1"}, new String[]{}, new String[]{}, new String[]{"2"}, new String[]{}, new String[]{})),
                new String[]{"1","2","3","3","3","4","5","5","6","6"},//
                new String[]{"1","1","2"},//song
                new String[]{"all_001","all_001","jsna_001"},//bgm
                new String[]{"no","no","3_000"},//
                74,99,new int[]{175,125,140},new float[]{0.16f,0.06f,0.22f},
                new int[]{1000,800,1200},
                new int[]{1,0,2},new int[]{200,450,900},//PlanType PlanRequire
                new int[]{14,14,14},//SpVoicesNum
                new String[]{"1_000","2_000","3_000"},//lives
                new String[]{"002","002","009"},//bossSongs,
                new String[]{ListenToMyTrueHeart.ID, CallMeAnyTime.ID},
                new String[]{"skin10","skin12","skin17"}
        ));
//        idols.put(hmsz, new Idol(hmsz,
//                new String[]{"skin10","skin20","skin30"},
//                new String[]{"skin10","skin12","skin13"},
//                new String[]{TopIdolInSchool.ID, KingAppear.ID, TopStar.ID},//
//                new String[]{TeaCaddy.ID,EveryoneDream.ID,AbsoluteNewSelf.ID},//
//                new IdolType[]{IdolType.ANOMALY,IdolType.ANOMALY,IdolType.ANOMALY},
//                new IdolStyle[]{IdolStyle.CONCENTRATION,IdolStyle.FULL_POWER,IdolStyle.CONCENTRATION},//
//                new ArrayList<>(Arrays.asList(new String[]{},new String[]{},new String[]{}, new String[]{"1"}, new String[]{"1"}, new String[]{}, new String[]{}, new String[]{"2"}, new String[]{}, new String[]{})),
//                new String[]{"1","2","3","3","3","4","5","5","6","6"},//
//                new String[]{"1","1","2"},//song
//                new String[]{"all_001","all_001","jsna_001"},//bgm
//                new String[]{"no","no","3_000"},//
//                74,99,new int[]{90,80,120},new float[]{0.24f,0.1f,0.27f},
//                new int[]{1000,800,1200},
//                new int[]{1,0,2},new int[]{200,450,900},//PlanType PlanRequire
//                new int[]{14,14,14},//SpVoicesNum
//                new String[]{"1_000","2_000","3_000"},//lives
//                new String[]{"002","002","009"},//bossSongs,
//                new String[]{ListenToMyTrueHeart.ID, CallMeAnyTime.ID},
//                new String[]{"skin10","skin12","skin17"}
//        ));

        idols.get(hume).setAnotherThreeSizeRequires(new int[]{950,1350,700});


    }

}
