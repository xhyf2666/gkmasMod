package gkmasmod.relics;
import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.ISubscriber;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.powers.TrainRoundLogicPower;
import gkmasmod.powers.TrainRoundSensePower;
import gkmasmod.ui.PocketBookViewScreen;
import gkmasmod.ui.SkinSelectScreen;
import gkmasmod.utils.CommonEnum;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.RankHelper;

public class PocketBook extends CustomRelic  implements ISubscriber, CustomSavable<int[]> {

    private static final String CLASSNAME = PocketBook.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private String IMG = String.format("img/idol/%s/relics/%s.png", SkinSelectScreen.Inst.idolName,CLASSNAME);
    private String IMG_OTL = String.format("img/idol/%s/relics/%s.png", SkinSelectScreen.Inst.idolName,CLASSNAME);
    private String IMG_LARGE = String.format("img/idol/%s/relics/large/%s.png", SkinSelectScreen.Inst.idolName,CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 2;

    private static final  int playTimes = 2;

    public static int vo = 100;
    public static int da = 120;
    public static int vi = 70;
    public static float vo_rate = 0.33f;
    public static float da_rate = 0.41f;
    public static float vi_rate = 0.21f;
    private static int vo_change = 0;
    private static int da_change = 0;
    private static int vi_change = 0;
    public static CommonEnum.IdolType type;

    private boolean RclickStart = false;


    public PocketBook() {
        super(ID, ImageMaster.loadImage(String.format("img/idol/%s/relics/%s.png", SkinSelectScreen.Inst.idolName,CLASSNAME)),
                ImageMaster.loadImage(String.format("img/idol/%s/relics/%s.png", SkinSelectScreen.Inst.idolName,CLASSNAME)), RARITY, LandingSound.CLINK);
        type  = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getType(SkinSelectScreen.Inst.skinIndex);
        BaseMod.subscribe(this);
        BaseMod.addSaveField(NameHelper.makePath("threeSize"), this);
    }

    @Override
    public void update() {
        super.update();
        updateRelicRightClick();
    }

    private void updateRelicRightClick() {
        if (this.RclickStart && InputHelper.justReleasedClickRight) {
            if (this.hb.hovered) {
                if (AbstractDungeon.screen == PocketBookViewScreen.Enum.PocketBookView_Screen){
                    AbstractDungeon.closeCurrentScreen();
                }
                else {
                    BaseMod.openCustomScreen(PocketBookViewScreen.Enum.PocketBookView_Screen, SkinSelectScreen.Inst.idolName, RankHelper.getStep());
                }
                CInputActionSet.select.unpress();
            }
            this.RclickStart = false;
        }
        if (this.hb != null && this.hb.hovered && InputHelper.justClickedRight)
            this.RclickStart = true;
    }


    @Override
    public void onVictory() {
        this.counter = playTimes;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PocketBook();
    }


    public void onEquip() {}

    public void atTurnStart() {
    }

    public void onPlayCard(AbstractCard c, AbstractMonster m) {

    }

    public void atBattleStart() {
        this.counter = playTimes;
        int trainRoundNum = RankHelper.getStep()+12;

        if(type==CommonEnum.IdolType.SENSE){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TrainRoundSensePower(AbstractDungeon.player, trainRoundNum), trainRoundNum));
        }
        else if (type==CommonEnum.IdolType.LOGIC){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TrainRoundLogicPower(AbstractDungeon.player, trainRoundNum), trainRoundNum));
        }
    }

    public  void  onPlayerEndTurn(){
    }

    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }

    public static void changeVo(int add){
        vo_change =  add + (int)(add*vo_rate);
        vo = vo +vo_change;

    }

    public static void changeDa(int add){
        da_change =  add + (int)(add*da_rate);
        da = da +da_change;
    }

    public static void changeVi(int add){
        vi_change =  add + (int)(add*vi_rate);
        vi = vi +vi_change;
    }

    public void onLoad(int[] args) {
        if(args != null && args.length == 3){
            this.vo = args[0];
            this.da = args[1];
            this.vi = args[2];
        }
    }

    public int[] onSave() {
        return new int[]{this.vo,this.da,this.vi};
    }

}
