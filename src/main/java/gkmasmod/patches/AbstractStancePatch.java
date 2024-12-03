package gkmasmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.stances.AbstractStance;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.relics.PledgePetal;
import gkmasmod.relics.PocketBook;
import gkmasmod.stances.ConcentrationStance;
import gkmasmod.stances.FullPowerStance;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.ImageHelper;


public class AbstractStancePatch
{

    @SpirePatch(clz = AbstractStance.class,method = "getStanceFromName")
    public static class AbstractStancePrePatch_ {
        public static SpireReturn<AbstractStance> Prefix(String name) {
            if(name.equals(PreservationStance.STANCE_ID))
                return SpireReturn.Return(new PreservationStance());
            else if(name.equals(ConcentrationStance.STANCE_ID))
                return SpireReturn.Return(new ConcentrationStance());
            else if(name.equals(FullPowerStance.STANCE_ID))
                return SpireReturn.Return(new FullPowerStance());
            else if(name.equals(PreservationStance.STANCE_ID2))
                return SpireReturn.Return(new PreservationStance(1));
            else if(name.equals(ConcentrationStance.STANCE_ID2))
                return SpireReturn.Return(new ConcentrationStance(1));
            return SpireReturn.Continue();
        }
    }

}

