package gkmasmod.relics;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.patches.MapRoomNodePatch;
import gkmasmod.powers.FullPowerValue;
import gkmasmod.powers.GoodImpression;
import gkmasmod.powers.GoodTune;
import gkmasmod.powers.MasterRollerPower;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.CommonEnum;
import gkmasmod.utils.IdolData;
import sun.util.resources.cldr.es.CalendarData_es_HN;

public class MasterRoller extends MasterRelic {

    private static final String CLASSNAME = MasterRoller.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = "gkmasModResource/img/relics/%s%s.png";

    private static final String IMG_LARGE = "gkmasModResource/img/relics/large/%s%s.png";

    private static final int magic1 = 0;

    private static final int magic2 = 10;

    private static final int BLOCK = 3;

    private static final int MAGIC = 2;
    private static final int MAGIC2 = 4;

    public static CommonEnum.IdolStyle style;

    private static String unit1 = CardCrawlGame.languagePack.getUIString("gkmasMod:UnitName:One").TEXT[0];
    private static String unit2 = CardCrawlGame.languagePack.getUIString("gkmasMod:UnitName:Two").TEXT[0];
    private static String goodTuneString = CardCrawlGame.languagePack.getUIString("styleHintName:goodTune").TEXT[0];
    private static String focusString = CardCrawlGame.languagePack.getUIString("styleHintName:focus").TEXT[0];
    private static String goodImpressionString = CardCrawlGame.languagePack.getUIString("styleHintName:goodImpression").TEXT[0];
    private static String yarukiString = CardCrawlGame.languagePack.getUIString("styleHintName:yaruki").TEXT[0];
    private static String fullPowerString = CardCrawlGame.languagePack.getUIString("styleHintName:fullPower").TEXT[0];
    private static String concentrationString = CardCrawlGame.languagePack.getUIString("styleHintName:concentration").TEXT[0];

    public MasterRoller() {
        super(ID, getTextureName(), magic1, magic2);
        style = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getStyle(SkinSelectScreen.Inst.skinIndex);
    }

    @Override
    public String getUpdatedDescription() {
        if(style == CommonEnum.IdolStyle.GOOD_TUNE)
            return String.format(this.DESCRIPTIONS[0],BLOCK,MAGIC,unit2,goodTuneString,50,magic2);
        else if(style == CommonEnum.IdolStyle.FOCUS)
            return String.format(this.DESCRIPTIONS[0],BLOCK,MAGIC,unit1,focusString,50,magic2);
        else if(style == CommonEnum.IdolStyle.GOOD_IMPRESSION)
            return String.format(this.DESCRIPTIONS[0],BLOCK,MAGIC2,unit2,goodImpressionString,50,magic2);
        else if(style == CommonEnum.IdolStyle.YARUKI)
            return String.format(this.DESCRIPTIONS[0],BLOCK,MAGIC,unit1,yarukiString,50,magic2);
        else if(style == CommonEnum.IdolStyle.FULL_POWER)
            return String.format(this.DESCRIPTIONS[1],BLOCK,MAGIC,magic2);
        else if(style == CommonEnum.IdolStyle.CONCENTRATION)
            return String.format(this.DESCRIPTIONS[2],BLOCK,50,magic2);
        else
            return String.format(this.DESCRIPTIONS[0],BLOCK,MAGIC,unit1,focusString,50,magic2);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new MasterRoller();
    }

    public void onEquip() {
        if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
            PocketBook book = (PocketBook)AbstractDungeon.player.getRelic(PocketBook.ID);
            book.healthRate += 1.0f*magic1/100;
            book.threeSizeIncrease += magic2;
        }
    }

    @Override
    public void atBattleStart() {
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.flash();
        addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, BLOCK));
        if(style == CommonEnum.IdolStyle.GOOD_TUNE){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new GoodTune(AbstractDungeon.player, MAGIC), MAGIC));
        }
        else if(style == CommonEnum.IdolStyle.FOCUS){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, MAGIC), MAGIC));
        }
        else if(style == CommonEnum.IdolStyle.GOOD_IMPRESSION){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new GoodImpression(AbstractDungeon.player, MAGIC2), MAGIC2));
        }
        else if(style == CommonEnum.IdolStyle.YARUKI){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, MAGIC), MAGIC));
        }
        else if(style == CommonEnum.IdolStyle.FULL_POWER){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FullPowerValue(AbstractDungeon.player, MAGIC), MAGIC));
            addToBot(new ChangeStanceAction(PreservationStance.STANCE_ID));
        }
        else if(style == CommonEnum.IdolStyle.CONCENTRATION){
            addToBot(new ChangeStanceAction(PreservationStance.STANCE_ID2));
        }
        else{
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, MAGIC), MAGIC));
        }
        if(MapRoomNodePatch.SPField.isSP.get(AbstractDungeon.getCurrMapNode())){
            if(style == CommonEnum.IdolStyle.FULL_POWER){
                addToBot(new LoseEnergyAction(1));
            }
            else{
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MasterRollerPower(AbstractDungeon.player, 1), 1));
            }
        }

    }

    static public String getTextureName() {
        style = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getStyle(SkinSelectScreen.Inst.skinIndex);
        if(style == CommonEnum.IdolStyle.GOOD_TUNE)
            return String.format(IMG, CLASSNAME, "Purple");
        else if(style == CommonEnum.IdolStyle.FOCUS)
            return String.format(IMG, CLASSNAME, "Green");
        else if(style == CommonEnum.IdolStyle.GOOD_IMPRESSION)
            return String.format(IMG, CLASSNAME, "Yellow");
        else if(style == CommonEnum.IdolStyle.YARUKI)
            return String.format(IMG, CLASSNAME, "Red");
        else if(style == CommonEnum.IdolStyle.FULL_POWER)
            return String.format(IMG, CLASSNAME, "Cyan");
        else if(style == CommonEnum.IdolStyle.CONCENTRATION)
            return String.format(IMG, CLASSNAME, "Pink");
        else
            return String.format(IMG, CLASSNAME, "Green");
    }

    static public String getLargeTextureName() {
        style = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getStyle(SkinSelectScreen.Inst.skinIndex);
        if(style == CommonEnum.IdolStyle.GOOD_TUNE)
            return String.format(IMG_LARGE, CLASSNAME, "Purple");
        else if(style == CommonEnum.IdolStyle.FOCUS)
            return String.format(IMG_LARGE, CLASSNAME, "Green");
        else if(style == CommonEnum.IdolStyle.GOOD_IMPRESSION)
            return String.format(IMG_LARGE, CLASSNAME, "Yellow");
        else if(style == CommonEnum.IdolStyle.YARUKI)
            return String.format(IMG_LARGE, CLASSNAME, "Red");
        else if(style == CommonEnum.IdolStyle.FULL_POWER)
            return String.format(IMG_LARGE, CLASSNAME, "Cyan");
        else if(style == CommonEnum.IdolStyle.CONCENTRATION)
            return String.format(IMG_LARGE, CLASSNAME, "Pink");
        else
            return String.format(IMG_LARGE, CLASSNAME, "Green");
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(getLargeTextureName()).exists()) {
                this.largeImg = ImageMaster.loadImage(getLargeTextureName());
            }
        }
    }

}
