package gkmasmod.utils;


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

    //public static final String[] idolNames = {shro, kllj, kcna, hume, hski, hrnm, fktn, amao, ssmk, ttmr, hmsz, jsna, nasr};
    public static final String[] idolNames = {shro, kllj, kcna,hume,hski,hrnm,fktn,amao,ssmk,ttmr};

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
    // 34 浴衣换皮

    static {
        idols.put(shro, new Idol(shro,
                new String[]{"skin10","skin20","skin30","skin31","skin33","skin34"},
                new String[]{HighlyEducatedIdol.ID, LovesTheStruggle.ID, SeriousHobby.ID, SwayingOnTheBus.ID, SeriousHobby.ID, SeriousHobby.ID},
                new String[]{UltimateSleepMask.ID, BeginnerGuideForEveryone.ID, SidewalkResearchNotes.ID, TowardsAnUnseenWorld.ID,SidewalkResearchNotes.ID,SidewalkResearchNotes.ID},
                new IdolType[]{IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC,IdolType.LOGIC},
                new IdolStyle[]{IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.FOCUS,IdolStyle.YARUKI,IdolStyle.YARUKI},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{"1","2","3"}, new String[]{"4"}, new String[]{"4"}, new String[]{"4","5"}, new String[]{}, new String[]{"6"})),
                new String[]{"1","1","1","1","2","2","2","3","3"},
                new String[]{"1","1","2","3","4","5"},
                new String[]{"all_001","all_001","shro_001","shro_002","all_003","all_004"},
                66,99,new int[]{115,115,80},new float[]{0.27f,0.23f,0.10f},
                new int[]{1200,1000,800}
        ));
        idols.put(kllj, new Idol(kllj,
                new String[]{"skin10","skin11","skin20","skin30","skin33","skin34"},
                new String[]{ReservedGirl.ID, FirstGround.ID,PureWhiteFairy.ID, NotAfraidAnymore.ID, NotAfraidAnymore.ID, FirstRamune.ID},
                new String[]{GreenUniformBracelet.ID, FirstHeartProofLilja.ID,MemoryBot.ID, DreamLifeLog.ID, DreamLifeLog.ID, SparklingInTheBottle.ID},
                new IdolType[]{IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.SENSE},
                new IdolStyle[]{IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{}, new String[]{"3"}, new String[]{"3","4"}, new String[]{"3","4","5"}, new String[]{}, new String[]{"6"})),
                new String[]{"2","2","2","3","3","3","3","4","4"},
                new String[]{"1","1","1","2","4","5"},
                new String[]{"all_001","all_001","all_001","kllj_001","all_003","all_004"},
                74,99,new int[]{75,75,105},new float[]{0.18f,0.25f,0.21f},
                new int[]{800,1000,1200}
        ));
        idols.put(kcna, new Idol(kcna,
                new String[]{"skin10","skin11","skin20","skin30","skin31","skin33","skin34","skin35"},
                new String[]{FullOfEnergy.ID,FirstColor.ID, Wholeheartedly.ID, DebutStageForTheLady.ID, WelcomeToTeaParty.ID, DebutStageForTheLady.ID, DebutStageForTheLady.ID,AQuickSip.ID},
                new String[]{WishFulfillmentAmulet.ID, FirstHeartProofChina.ID,CheerfulHandkerchief.ID,SecretTrainingCardigan.ID,HeartFlutteringCup.ID,SecretTrainingCardigan.ID,SecretTrainingCardigan.ID,EnjoyAfterHotSpring.ID},
                new IdolType[]{IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC,IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC,IdolType.LOGIC,IdolType.SENSE},
                new IdolStyle[]{IdolStyle.YARUKI,IdolStyle.GOOD_TUNE,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.GOOD_TUNE,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.FOCUS},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{"1","2"}, new String[]{"1","2","3"}, new String[]{}, new String[]{"4"}, new String[]{"4","5"}, new String[]{})),
                new String[]{"2","2","2","2","2","3","3","3","4"},
                new String[]{"1","1","1","2","3","4","5","1"},
                new String[]{"all_001","all_001","all_001","kcna_001","kcna_002","all_003","all_004","all_001"},
                68,200,new int[]{75,110,115},new float[]{0.10f,0.28f,0.22f},
                new int[]{700,1350,950}

        ));
        idols.put(hume, new Idol(hume,
                new String[]{"skin10","skin20","skin30","skin33","skin34"},
                new String[]{UntappedPotential.ID, DefeatBigSister.ID, BigOnigiri.ID, BigOnigiri.ID, BigOnigiri.ID},
                new String[]{TechnoDog.ID, ShibaInuPochette.ID, RollingSourceOfEnergy.ID, RollingSourceOfEnergy.ID, RollingSourceOfEnergy.ID},
                new IdolType[]{IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC},
                new IdolStyle[]{IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.YARUKI},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{"1","2","3"}, new String[]{"1","2","3"}, new String[]{"1","2","3","4"}, new String[]{}, new String[]{"5"}, new String[]{"5","6"})),
                new String[]{"2","2","2","2","2","2","3","3","3"},
                new String[]{"1","1","2","4","5"},
                new String[]{"all_001","all_001","hume_001","all_003","all_004"},
                72,99,new int[]{75,85,85},new float[]{0.23f,0.28f,0.15f},
                new int[]{1000,1200,800}
        ));
        idols.put(hski, new Idol(hski,
                new String[]{"skin10","skin11","skin20","skin30","skin31","skin33","skin34"},
                new String[]{RisingStar.ID, FirstFuture.ID, NeverYieldFirst.ID, NeverLose.ID, Pow.ID, NeverLose.ID, GoldfishScoopingChallenge.ID},
                new String[]{RoaringLion.ID,FirstVoiceProofSaki.ID,SakiCompleteMealRecipe.ID,TogetherInBattleTowel.ID,WinningDetermination.ID,TogetherInBattleTowel.ID,UndefeatedPoi.ID},
                new IdolType[]{IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC},
                new IdolStyle[]{IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{"1","2","3"}, new String[]{"1","2","3"}, new String[]{}, new String[]{"4"}, new String[]{}, new String[]{"5"})),
                new String[]{"1","1","1","1","1","2","2","3","3"},
                new String[]{"1","1","1","2","3","4","5"},
                new String[]{"all_001","all_001","all_001","hski_001","hski_002","all_003","all_004"},
                76,99,new int[]{100,100,105},new float[]{0.18f,0.18f,0.20f},
                new int[]{800,1000,1200}
        ));
        idols.put(hrnm, new Idol(hrnm,
                new String[]{"skin10","skin11","skin20","skin30","skin33","skin34","skin35"},
                new String[]{Accommodating.ID,FirstFriend.ID,SupportiveFeelings.ID, SenseOfDistance.ID, CumulusCloudsAndYou.ID, SenseOfDistance.ID,RefreshingBreak.ID},
                new String[]{RegularMakeupPouch.ID,FirstHeartProofRinami.ID,TreatForYou.ID, LifeSizeLadyLip.ID, SummerToShareWithYou.ID, LifeSizeLadyLip.ID,ClapClapFan.ID},
                new IdolType[]{IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.LOGIC},
                new IdolStyle[]{IdolStyle.FOCUS,IdolStyle.YARUKI,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.GOOD_TUNE,IdolStyle.FOCUS,IdolStyle.YARUKI},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{"1","2"}, new String[]{"1","2","3"}, new String[]{}, new String[]{"4"}, new String[]{"4","5"}, new String[]{"4","5","6"})),
                new String[]{"1","1","1","1","1","2","2","2","2"},
                new String[]{"1","1","1","2","4","5","1"},
                new String[]{"all_001","all_001","all_001","hrnm_001","all_003","all_004","all_001"},
                72,99,new int[]{85,95,85},new float[]{0.11f,0.245f,0.285f},
                new int[]{800,1000,1200}

        ));
        idols.put(fktn, new Idol(fktn,
                new String[]{"skin10","skin11","skin20","skin30","skin31","skin33","skin34"},
                new String[]{Arbeiter.ID, FirstReward.ID, ColorfulCute.ID, NoDistractions.ID, FullAdrenaline.ID, NoDistractions.ID, SummerEveningSparklers.ID},
                new String[]{HandmadeMedal.ID,FirstVoiceProofKotone.ID,FavoriteSneakers.ID,PigDreamPiggyBank.ID,UltimateSourceOfHappiness.ID,PigDreamPiggyBank.ID,CracklingSparkler.ID},
                new IdolType[]{IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.LOGIC},
                new IdolStyle[]{IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.YARUKI},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{"1","2","3"}, new String[]{}, new String[]{"4"}, new String[]{"4","5"}, new String[]{"4","5","6"}, new String[]{})),
                new String[]{"1","1","1","1","2","2","2","2","3"},
                new String[]{"1","1","1","2","3","4","5"},
                new String[]{"all_001","all_001","all_001","fktn_001","fktn_002","all_003","all_004"},
                74,50,new int[]{90,80,110},new float[]{0.11f,0.27f,0.22f},
                new int[]{500,1300,1200}
        ));
        idols.put(amao, new Idol(amao,
                new String[]{"skin10","skin20","skin30","skin33","skin34"},
                new String[]{LittlePrince.ID, Authenticity.ID, DressedUpInStyle.ID, ChillyBreak.ID, DressedUpInStyle.ID},
                new String[]{GentlemanHandkerchief.ID, DearLittlePrince.ID, InnerLightEarrings.ID, LastSummerMemory.ID, InnerLightEarrings.ID},
                new IdolType[]{IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE},
                new IdolStyle[]{IdolStyle.GOOD_TUNE,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_TUNE,IdolStyle.FOCUS,IdolStyle.GOOD_TUNE},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{"1","2","3"}, new String[]{}, new String[]{"4"}, new String[]{"4","5"}, new String[]{"4","5","6"}, new String[]{})),
                new String[]{"1","1","1","1","2","2","2","2","3"},
                new String[]{"1","1","2","4","5"},
                new String[]{"all_001","all_001","amao_001","all_003","all_004"},
                74,99,new int[]{115,90,90},new float[]{0.25f,0.06f,0.28f},
                new int[]{1300,500,1200}
        ));
//        idols.put(amao, new Idol(amao,
//                new String[]{"skin10","skin20","skin30","skin31","skin33","skin34"},
//                new String[]{LittlePrince.ID, Authenticity.ID, DressedUpInStyle.ID,MoonlitRunway.ID, ChillyBreak.ID, DressedUpInStyle.ID},
//                new String[]{GentlemanHandkerchief.ID, DearLittlePrince.ID, InnerLightEarrings.ID,FashionMode.ID, LastSummerMemory.ID, InnerLightEarrings.ID},
//                new IdolType[]{IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE},
//                new IdolStyle[]{IdolStyle.GOOD_TUNE,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.FOCUS,IdolStyle.GOOD_TUNE},
//                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{"1","2","3"}, new String[]{}, new String[]{"4"}, new String[]{"4","5"}, new String[]{"4","5","6"}, new String[]{})),
//                new String[]{"1","1","1","1","2","2","2","2","3"},
//                new String[]{"1","1","2","3","4","5"},
//                new String[]{"all_001","all_001","amao_001","amao_002","all_003","all_004"},
//                74,99,new int[]{115,90,90},new float[]{0.25f,0.06f,0.28f},
//                new int[]{1300,500,1200}
//        ));
        idols.put(ssmk, new Idol(ssmk,
                new String[]{"skin10","skin20","skin30","skin33","skin34"},
                new String[]{Friendly.ID, BraveStep.ID, OneMoreStep.ID, AfternoonBreeze.ID, OneMoreStep.ID},
                new String[]{PinkUniformBracelet.ID, AfterSchoolDoodles.ID, ArcadeLoot.ID, FrogFan.ID, ArcadeLoot.ID},
                new IdolType[]{IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE},
                new IdolStyle[]{IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.YARUKI,IdolStyle.FOCUS},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{"1","2","3"}, new String[]{}, new String[]{"4"}, new String[]{"4","5"}, new String[]{}, new String[]{"6"})),
                new String[]{"1","1","1","1","2","2","2","3","3"},
                new String[]{"1","1","2","4","5"},
                new String[]{"all_001","all_001","ssmk_001","all_003","all_004"},
                72,99,new int[]{90,85,90},new float[]{0.09f,0.28f,0.26f},
                new int[]{600,1500,800}
        ));
        idols.put(ttmr, new Idol(ttmr,
                new String[]{"skin10","skin11","skin20","skin30","skin31","skin33","skin34"},
                new String[]{Stubborn.ID, FirstPlace.ID, LoneWolf.ID, EachPath.ID, TangledFeelings.ID, EachPath.ID, EachPath.ID},
                new String[]{EssentialStainlessSteelBottle.ID,FirstVoiceProofTemari.ID,MyFirstSheetMusic.ID,ProtectiveEarphones.ID,ThisIsMe.ID,ProtectiveEarphones.ID,ProtectiveEarphones.ID},
                new IdolType[]{IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE},
                new IdolStyle[]{IdolStyle.FOCUS,IdolStyle.GOOD_IMPRESSION,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.GOOD_IMPRESSION,IdolStyle.FOCUS,IdolStyle.FOCUS},
                new ArrayList<>(Arrays.asList(new String[]{},new String[]{"1"},new String[]{"1","2"}, new String[]{}, new String[]{"3"}, new String[]{"3","4"}, new String[]{"3","4","5"}, new String[]{}, new String[]{"6"})),
                new String[]{"1","1","1","2","2","2","2","3","3"},
                new String[]{"1","1","1","2","3","4","5"},
                new String[]{"all_001","all_001","all_001","ttmr_001","ttmr_002","all_003","all_004"},
                76,99,new int[]{110,90,80},new float[]{0.27f,0.26f,0.06f},
                new int[]{1350,950,700}
        ));


//        idols.put(jsna, new Idol(jsna,
//                new String[]{"skin10","skin20","skin30","skin31","skin33"},
//                new String[]{Gacha.ID, Gacha.ID, Gacha.ID, Gacha.ID, Gacha.ID},
//                new String[]{EssentialStainlessSteelBottle.ID,EssentialStainlessSteelBottle.ID,EssentialStainlessSteelBottle.ID,EssentialStainlessSteelBottle.ID,EssentialStainlessSteelBottle.ID},
//        ));
//        idols.put(hmsz, new Idol(hmsz,
//                new String[]{"skin10","skin20","skin30","skin31","skin33"},
//                new String[]{Gacha.ID, Gacha.ID, Gacha.ID, Gacha.ID, Gacha.ID},
//                new String[]{EssentialStainlessSteelBottle.ID,EssentialStainlessSteelBottle.ID,EssentialStainlessSteelBottle.ID,EssentialStainlessSteelBottle.ID,EssentialStainlessSteelBottle.ID},
//        ));
//        idols.put(nasr, new Idol(nasr,
//                new String[]{"skin10","skin20","skin30","skin31","skin33"},
//                new String[]{Gacha.ID, Gacha.ID, Gacha.ID, Gacha.ID, Gacha.ID},
//                new String[]{EssentialStainlessSteelBottle.ID,EssentialStainlessSteelBottle.ID,EssentialStainlessSteelBottle.ID,EssentialStainlessSteelBottle.ID,EssentialStainlessSteelBottle.ID},
//        ));



    }

}
