package gkmasmod.modcore;

import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.Keyword;
import com.megacrit.cardcrawl.localization.PowerStrings;
import gkmasmod.cards.*;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.variables.SecondMagicNumber;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static gkmasmod.characters.PlayerColorEnum.gkmasModColor;
import static gkmasmod.characters.PlayerColorEnum.gkmasMod_character;

@SpireInitializer
public class GkmasMod implements EditCardsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber, EditCharactersSubscriber, AddAudioSubscriber {

    //攻击、技能、能力牌的背景图片(512)
    private static final String ATTACK_CC = "img/pink/512/bg_attack.png";
    private static final String SKILL_CC = "img/pink/512/bg_skill.png";
    private static final String POWER_CC = "img/pink/512/bg_power.png";
    private static final String ENERGY_ORB_CC = "img/UI/star.png";
    //攻击、技能、能力牌的背景图片(1024)
    private static final String ATTACK_CC_PORTRAIT = "img/pink/1024/bg_attack.png";
    private static final String SKILL_CC_PORTRAIT = "img/pink/1024/bg_skill.png";
    private static final String POWER_CC_PORTRAIT = "img/pink/1024/bg_power.png";
    private static final String ENERGY_ORB_CC_PORTRAIT = "img/UI/star_164.png";
    public static final String CARD_ENERGY_ORB = "img/UI/star_22.png";
    //选英雄界面的角色图标、选英雄时的背景图片
    private static final String MY_CHARACTER_BUTTON = "img/charSelect/SelesButtongita.png";
    private static final String MARISA_PORTRAIT = "img/charSelect/background.png";
    public static final Color gkmasMod_color = CardHelper.getColor(100, 200, 200);
    public static boolean limitedSLOption;
    private ArrayList<AbstractCard> cardsToAdd = new ArrayList<>();
    public static ArrayList<AbstractCard> recyclecards = new ArrayList<>();
    private static SpireConfig config;
    public static final String MOD_NAME = "gkmasMod";


    public GkmasMod(){
        BaseMod.subscribe(this);
        BaseMod.addColor(gkmasModColor, gkmasMod_color, gkmasMod_color, gkmasMod_color, gkmasMod_color, gkmasMod_color, gkmasMod_color, gkmasMod_color, ATTACK_CC, SKILL_CC, POWER_CC, ENERGY_ORB_CC,
                ATTACK_CC_PORTRAIT,SKILL_CC_PORTRAIT ,POWER_CC_PORTRAIT , ENERGY_ORB_CC_PORTRAIT,CARD_ENERGY_ORB );
    }

    public static void initialize(){
        new GkmasMod();
    }

    @Override
    public void receiveEditCards(){
        BaseMod.addDynamicVariable(new SecondMagicNumber());
        List<Object> instances = getCardsToAdd();
        for (Object instance : instances) {
            BaseMod.addCard((AbstractCard) instance);
        }
    }

    public void receiveAddAudio() {
        BaseMod.addAudio("click", "audio/click.ogg");
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
        return instances;
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new IdolCharacter(CardCrawlGame.playerName), MY_CHARACTER_BUTTON, MARISA_PORTRAIT, gkmasMod_character);
    }
}
