package gkmasmod.characters;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;

public class PlayerLibraryEnum {
    @SpireEnum(name = "gkmasMod:logic")
    public static CardLibrary.LibraryType gkmasModColorLogic;

    @SpireEnum(name = "gkmasMod:free")
    public static CardLibrary.LibraryType gkmasModColor;

    @SpireEnum(name = "gkmasMod:sense")
    public static CardLibrary.LibraryType gkmasModColorSense;

    @SpireEnum(name = "gkmasMod:anomaly")
    public static CardLibrary.LibraryType gkmasModColorAnomaly;

    @SpireEnum(name = "gkmasMod:misuzu")
    public static CardLibrary.LibraryType gkmasModColorMisuzu;

    @SpireEnum(name = "gkmasMod:moon")
    public static CardLibrary.LibraryType gkmasModColorMoon;

    @SpireEnum(name = "gkmasMod:other")
    public static CardLibrary.LibraryType gkmasModColorOther;
}