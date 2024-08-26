package gkmasmod.utils;


import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.relics.Vajra;
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
                new IdolStyle[]{IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.FOCUS,IdolStyle.YARUKI,IdolStyle.YARUKI}
        ));
        idols.put(kllj, new Idol(kllj,
                new String[]{"skin10","skin20","skin30","skin33","skin34"},
                new String[]{ReservedGirl.ID, PureWhiteFairy.ID, NotAfraidAnymore.ID, NotAfraidAnymore.ID, FirstRamune.ID},
                new String[]{GreenUniformBracelet.ID, MemoryBot.ID, DreamLifeLog.ID, DreamLifeLog.ID, SparklingInTheBottle.ID},
                new IdolType[]{IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.SENSE},
                new IdolStyle[]{IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE}
        ));
        idols.put(kcna, new Idol(kcna,
                new String[]{"skin10","skin20","skin30","skin31","skin33","skin34"},
                new String[]{FullOfEnergy.ID, Wholeheartedly.ID, DebutStageForTheLady.ID, WelcomeToTeaParty.ID, DebutStageForTheLady.ID, DebutStageForTheLady.ID},
                new String[]{WishFulfillmentAmulet.ID,CheerfulHandkerchief.ID,SecretTrainingCardigan.ID,HeartFlutteringCup.ID,SecretTrainingCardigan.ID,SecretTrainingCardigan.ID},
                new IdolType[]{IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC,IdolType.LOGIC},
                new IdolStyle[]{IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.GOOD_TUNE,IdolStyle.YARUKI,IdolStyle.YARUKI}
        ));
        idols.put(hume, new Idol(hume,
                new String[]{"skin10","skin20","skin30","skin33","skin34"},
                new String[]{UntappedPotential.ID, DefeatBigSister.ID, BigOnigiri.ID, BigOnigiri.ID, BigOnigiri.ID},
                new String[]{TechnoDog.ID, ShibaInuPochette.ID, RollingSourceOfEnergy.ID, RollingSourceOfEnergy.ID, RollingSourceOfEnergy.ID},
                new IdolType[]{IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC,IdolType.LOGIC},
                new IdolStyle[]{IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.YARUKI,IdolStyle.YARUKI}
        ));
        idols.put(hski, new Idol(hski,
                new String[]{"skin10","skin11","skin20","skin30","skin31","skin33","skin34"},
                new String[]{RisingStar.ID, FirstFuture.ID, NeverYieldFirst.ID, NeverLose.ID, Pow.ID, NeverLose.ID, GoldfishScoopingChallenge.ID},
                new String[]{RoaringLion.ID,FirstVoiceProofSaki.ID,SakiCompleteMealRecipe.ID,TogetherInBattleTowel.ID,WinningDetermination.ID,TogetherInBattleTowel.ID,UndefeatedPoi.ID},
                new IdolType[]{IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC},
                new IdolStyle[]{IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION}
        ));
        idols.put(hrnm, new Idol(hrnm,
                new String[]{"skin10","skin20","skin30","skin33","skin34"},
                new String[]{Accommodating.ID, SupportiveFeelings.ID, SenseOfDistance.ID, CumulusCloudsAndYou.ID, SenseOfDistance.ID},
                new String[]{RegularMakeupPouch.ID, TreatForYou.ID, LifeSizeLadyLip.ID, SummerToShareWithYou.ID, LifeSizeLadyLip.ID},
                new IdolType[]{IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE},
                new IdolStyle[]{IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.GOOD_TUNE,IdolStyle.FOCUS}
        ));
        idols.put(fktn, new Idol(fktn,
                new String[]{"skin10","skin11","skin20","skin30","skin31","skin33","skin34"},
                new String[]{Arbeiter.ID, FirstReward.ID, ColorfulCute.ID, NoDistractions.ID, FullAdrenaline.ID, NoDistractions.ID, SummerEveningSparklers.ID},
                new String[]{HandmadeMedal.ID,FirstVoiceProofKotone.ID,FavoriteSneakers.ID,PigDreamPiggyBank.ID,UltimateSourceOfHappiness.ID,PigDreamPiggyBank.ID,CracklingSparkler.ID},
                new IdolType[]{IdolType.LOGIC,IdolType.SENSE,IdolType.LOGIC,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.LOGIC},
                new IdolStyle[]{IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_IMPRESSION,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_IMPRESSION,IdolStyle.YARUKI}
        ));
        idols.put(amao, new Idol(amao,
                new String[]{"skin10","skin20","skin30","skin33","skin34"},
                new String[]{LittlePrince.ID, Authenticity.ID, DressedUpInStyle.ID, ChillyBreak.ID, DressedUpInStyle.ID},
                new String[]{GentlemanHandkerchief.ID, DearLittlePrince.ID, InnerLightEarrings.ID, LastSummerMemory.ID, InnerLightEarrings.ID},
                new IdolType[]{IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.SENSE},
                new IdolStyle[]{IdolStyle.GOOD_TUNE,IdolStyle.GOOD_TUNE,IdolStyle.GOOD_TUNE,IdolStyle.FOCUS,IdolStyle.GOOD_TUNE}
        ));
        idols.put(ssmk, new Idol(ssmk,
                new String[]{"skin10","skin20","skin30","skin33","skin34"},
                new String[]{Friendly.ID, BraveStep.ID, OneMoreStep.ID, AfternoonBreeze.ID, OneMoreStep.ID},
                new String[]{PinkUniformBracelet.ID, AfterSchoolDoodles.ID, ArcadeLoot.ID, FrogFan.ID, ArcadeLoot.ID},
                new IdolType[]{IdolType.SENSE,IdolType.SENSE,IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE},
                new IdolStyle[]{IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.YARUKI,IdolStyle.FOCUS}
        ));
        idols.put(ttmr, new Idol(ttmr,
                new String[]{"skin10","skin11","skin20","skin30","skin31","skin33","skin34"},
                new String[]{Stubborn.ID, FirstPlace.ID, LoneWolf.ID, EachPath.ID, TangledFeelings.ID, EachPath.ID, EachPath.ID},
                new String[]{EssentialStainlessSteelBottle.ID,FirstVoiceProofTemari.ID,MyFirstSheetMusic.ID,ProtectiveEarphones.ID,ThisIsMe.ID,ProtectiveEarphones.ID,ProtectiveEarphones.ID},
                new IdolType[]{IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE,IdolType.LOGIC,IdolType.SENSE,IdolType.SENSE},
                new IdolStyle[]{IdolStyle.FOCUS,IdolStyle.GOOD_IMPRESSION,IdolStyle.FOCUS,IdolStyle.FOCUS,IdolStyle.GOOD_IMPRESSION,IdolStyle.FOCUS,IdolStyle.FOCUS}
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
