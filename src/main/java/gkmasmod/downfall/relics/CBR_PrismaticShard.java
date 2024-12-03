package gkmasmod.downfall.relics;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PrismaticShard;

public class CBR_PrismaticShard extends AbstractCharbossRelic {

    public CBR_PrismaticShard() {
        super(new PrismaticShard());
    }


    @Override
    public AbstractRelic makeCopy() {
        return new CBR_PrismaticShard();
    }

}
