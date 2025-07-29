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

    @SpireEnum(name = "gkmasModLogic")
    public static AbstractCard.CardColor gkmasModColorLogic;

    @SpireEnum(name = "gkmasModFree")
    public static AbstractCard.CardColor gkmasModColor;

    @SpireEnum(name = "gkmasModSense")
    public static AbstractCard.CardColor gkmasModColorSense;

    @SpireEnum(name = "gkmasModAnomaly")
    public static AbstractCard.CardColor gkmasModColorAnomaly;

    @SpireEnum(name = "gkmasModMisuzu")
    public static AbstractCard.CardColor gkmasModColorMisuzu;

    @SpireEnum(name = "gkmasModMoon")
    public static AbstractCard.CardColor gkmasModColorMoon;

    @SpireEnum(name = "gkmasModOther")
    public static AbstractCard.CardColor gkmasModColorOther;
}