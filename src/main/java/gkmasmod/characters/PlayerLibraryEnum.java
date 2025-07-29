package gkmasmod.characters;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;

public class PlayerLibraryEnum {
    @SpireEnum(name = "gkmasModLogic")
    public static CardLibrary.LibraryType gkmasModColorLogic;

    @SpireEnum(name = "gkmasModFree")
    public static CardLibrary.LibraryType gkmasModColor;

    @SpireEnum(name = "gkmasModSense")
    public static CardLibrary.LibraryType gkmasModColorSense;

    @SpireEnum(name = "gkmasModAnomaly")
    public static CardLibrary.LibraryType gkmasModColorAnomaly;

    @SpireEnum(name = "gkmasModMisuzu")
    public static CardLibrary.LibraryType gkmasModColorMisuzu;

    @SpireEnum(name = "gkmasModMoon")
    public static CardLibrary.LibraryType gkmasModColorMoon;

    @SpireEnum(name = "gkmasModOther")
    public static CardLibrary.LibraryType gkmasModColorOther;
}