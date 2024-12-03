package gkmasmod.characters;

// 以下为原版人物枚举、卡牌颜色枚举扩展的枚举，需要写，接下来要用

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

// 注意此处是在 MyCharacter 类内部的静态嵌套类中定义的新枚举值
// 不可将该定义放在外部的 MyCharacter 类中，具体原因见《高级技巧 / 01 - Patch / SpireEnum》
public class PlayerColorEnum {
    @SpireEnum
    public static AbstractPlayer.PlayerClass gkmasMod_character;

    // ***将CardColor和LibraryType的变量名改为你的角色的颜色名称，确保不会与其他mod冲突***
    // ***并且名称需要一致！***
    @SpireEnum(name = "gkmasMod:logic")
    public static AbstractCard.CardColor gkmasModColorLogic;

    @SpireEnum(name = "gkmasMod:free")
    public static AbstractCard.CardColor gkmasModColor;

    @SpireEnum(name = "gkmasMod:sense")
    public static AbstractCard.CardColor gkmasModColorSense;

    @SpireEnum(name = "gkmasMod:anomaly")
    public static AbstractCard.CardColor gkmasModColorAnomaly;
}