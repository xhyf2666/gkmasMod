package gkmasmod.characters;


import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class PlayerColorEnum {
    @SpireEnum
    public static AbstractPlayer.PlayerClass gkmasMod_character;

    @SpireEnum
    public static AbstractPlayer.PlayerClass gkmasModMisuzu_character;

    @SpireEnum
    public static AbstractPlayer.PlayerClass gkmasModOther_character;

    @SpireEnum(name = "gkmasMod:logic")
    public static AbstractCard.CardColor gkmasModColorLogic;

    @SpireEnum(name = "gkmasMod:free")
    public static AbstractCard.CardColor gkmasModColor;

    @SpireEnum(name = "gkmasMod:sense")
    public static AbstractCard.CardColor gkmasModColorSense;

    @SpireEnum(name = "gkmasMod:anomaly")
    public static AbstractCard.CardColor gkmasModColorAnomaly;

    @SpireEnum(name = "gkmasMod:misuzu")
    public static AbstractCard.CardColor gkmasModColorMisuzu;

    @SpireEnum(name = "gkmasMod:moon")
    public static AbstractCard.CardColor gkmasModColorMoon;

    @SpireEnum(name = "gkmasMod:other")
    public static AbstractCard.CardColor gkmasModColorOther;
}