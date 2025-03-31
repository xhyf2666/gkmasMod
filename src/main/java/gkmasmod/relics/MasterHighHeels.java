package gkmasmod.relics;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.powers.GoodImpression;
import gkmasmod.powers.GoodTune;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.CommonEnum;
import gkmasmod.utils.IdolData;
import org.lwjgl.Sys;

public class MasterHighHeels extends MasterRelic {

    private static final String CLASSNAME = MasterHighHeels.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = "gkmasModResource/img/relics/%s%s.png";

    private static final String IMG_LARGE = "gkmasModResource/img/relics/large/%s%s.png";

    private static final int magic1 = 0;

    private static final int magic2 = 15;

    private static final int BLOCK = 4;

    private static final int MAGIC = 15;
    private static final int MAGIC2 = 5;

    private static final int playTimes = 5;

    public static CommonEnum.IdolStyle style;

    private static String unit1 = CardCrawlGame.languagePack.getUIString("gkmasMod:UnitName:One").TEXT[0];
    private static String unit2 = CardCrawlGame.languagePack.getUIString("gkmasMod:UnitName:Two").TEXT[0];
    private static String goodTuneString = CardCrawlGame.languagePack.getUIString("styleHintName:goodTune").TEXT[0];
    private static String focusString = CardCrawlGame.languagePack.getUIString("styleHintName:focus").TEXT[0];
    private static String goodImpressionString = CardCrawlGame.languagePack.getUIString("styleHintName:goodImpression").TEXT[0];
    private static String yarukiString = CardCrawlGame.languagePack.getUIString("styleHintName:yaruki").TEXT[0];
    private static String fullPowerString = CardCrawlGame.languagePack.getUIString("styleHintName:fullPower").TEXT[0];
    private static String concentrationString = CardCrawlGame.languagePack.getUIString("styleHintName:concentration").TEXT[0];

    public MasterHighHeels() {
        super(ID, getTextureName(), magic1, magic2);
        this.counter = 0;
        if(AbstractDungeon.player instanceof MisuzuCharacter){
            style = CommonEnum.IdolStyle.FOCUS;
        }
        else{
            style = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getStyle(SkinSelectScreen.Inst.skinIndex);
        }

    }

    @Override
    public String getUpdatedDescription() {
        if(style == CommonEnum.IdolStyle.GOOD_TUNE)
            return String.format(this.DESCRIPTIONS[0],BLOCK,goodTuneString,MAGIC,MAGIC2,playTimes,magic2);
        else if(style == CommonEnum.IdolStyle.FOCUS)
            return String.format(this.DESCRIPTIONS[0],BLOCK,focusString,MAGIC,MAGIC2,playTimes,magic2);
        else if(style == CommonEnum.IdolStyle.GOOD_IMPRESSION)
            return String.format(this.DESCRIPTIONS[0],BLOCK,goodImpressionString,MAGIC,MAGIC2,playTimes,magic2);
        else if(style == CommonEnum.IdolStyle.YARUKI)
            return String.format(this.DESCRIPTIONS[0],BLOCK,yarukiString,MAGIC,MAGIC2,playTimes,magic2);
        else if(style == CommonEnum.IdolStyle.FULL_POWER)
            return String.format(this.DESCRIPTIONS[0],BLOCK,fullPowerString,MAGIC,MAGIC2,playTimes,magic2);
        else if(style == CommonEnum.IdolStyle.CONCENTRATION)
            return String.format(this.DESCRIPTIONS[0],BLOCK,concentrationString,MAGIC,MAGIC2,playTimes,magic2);
        else
            return String.format(this.DESCRIPTIONS[0],BLOCK,focusString,MAGIC,MAGIC2,playTimes,magic2);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new MasterHighHeels();
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
        addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, BLOCK));
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        if(this.counter< playTimes){
            if(style == CommonEnum.IdolStyle.GOOD_TUNE){
                if(c.tags.contains(GkmasCardTag.GOOD_TUNE_TAG)){
                    if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
                        PocketBook book = (PocketBook)AbstractDungeon.player.getRelic(PocketBook.ID);
                        book.healthRate += 1.0f*MAGIC2/100;
                        AbstractDungeon.player.loseGold(MAGIC);
                        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                        this.flash();
                        this.counter++;
                        if(this.counter == playTimes){
                            this.grayscale = true;
                        }
                    }
                }
            }
            else if(style == CommonEnum.IdolStyle.FOCUS){
                if(c.tags.contains(GkmasCardTag.FOCUS_TAG)){
                    if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
                        PocketBook book = (PocketBook)AbstractDungeon.player.getRelic(PocketBook.ID);
                        book.healthRate += 1.0f*MAGIC2/100;
                        AbstractDungeon.player.loseGold(MAGIC);
                        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                        this.flash();
                        this.counter++;
                        if(this.counter == playTimes){
                            this.grayscale = true;
                        }
                    }
                }
            }
            else if(style == CommonEnum.IdolStyle.GOOD_IMPRESSION){
                if(c.tags.contains(GkmasCardTag.GOOD_IMPRESSION_TAG)){
                    if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
                        PocketBook book = (PocketBook)AbstractDungeon.player.getRelic(PocketBook.ID);
                        book.healthRate += 1.0f*MAGIC2/100;
                        AbstractDungeon.player.loseGold(MAGIC);
                        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                        this.flash();
                        this.counter++;
                        if(this.counter == playTimes){
                            this.grayscale = true;
                        }
                    }
                }
            }
            else if(style == CommonEnum.IdolStyle.YARUKI){
                if(c.tags.contains(GkmasCardTag.YARUKI_TAG)){
                    if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
                        PocketBook book = (PocketBook)AbstractDungeon.player.getRelic(PocketBook.ID);
                        book.healthRate += 1.0f*MAGIC2/100;
                        AbstractDungeon.player.loseGold(MAGIC);
                        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                        this.flash();
                        this.counter++;
                        if(this.counter == playTimes){
                            this.grayscale = true;
                        }
                    }
                }
            }
            else if(style == CommonEnum.IdolStyle.FULL_POWER){
                if(c.tags.contains(GkmasCardTag.FULL_POWER_TAG)){
                    if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
                        PocketBook book = (PocketBook)AbstractDungeon.player.getRelic(PocketBook.ID);
                        book.healthRate += 1.0f*MAGIC2/100;
                        AbstractDungeon.player.loseGold(MAGIC);
                        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                        this.flash();
                        this.counter++;
                        if(this.counter == playTimes){
                            this.grayscale = true;
                        }
                    }
                }
            }
            else if(style == CommonEnum.IdolStyle.CONCENTRATION){
                if(c.tags.contains(GkmasCardTag.CONCENTRATION_TAG)){
                    if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
                        PocketBook book = (PocketBook)AbstractDungeon.player.getRelic(PocketBook.ID);
                        book.healthRate += 1.0f*MAGIC2/100;
                        AbstractDungeon.player.loseGold(MAGIC);
                        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                        this.flash();
                        this.counter++;
                        if(this.counter == playTimes){
                            this.grayscale = true;
                        }
                    }
                }
            }
        }
    }

    static public String getTextureName() {
        if(AbstractDungeon.player instanceof MisuzuCharacter){
            style = CommonEnum.IdolStyle.FOCUS;
        }
        else{
            style = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getStyle(SkinSelectScreen.Inst.skinIndex);
        }
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
        if(AbstractDungeon.player instanceof MisuzuCharacter){
            style = CommonEnum.IdolStyle.FOCUS;
        }
        else{
            style = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getStyle(SkinSelectScreen.Inst.skinIndex);
        }
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
