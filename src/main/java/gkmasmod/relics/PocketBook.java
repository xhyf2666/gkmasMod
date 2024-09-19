package gkmasmod.relics;
import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.ISubscriber;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import gkmasmod.actions.GainTrainRoundPowerWithoutEnergyAction;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.patches.AbstractDungeonPatch;
import gkmasmod.patches.MapRoomNodePatch;
import gkmasmod.powers.*;
import gkmasmod.screen.PocketBookViewScreen;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.CommonEnum;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.RankHelper;

import java.util.ArrayList;

public class PocketBook extends CustomRelic  implements ISubscriber, CustomSavable<float[]> {

    private static final String CLASSNAME = PocketBook.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private String IMG = String.format("gkmasModResource/img/idol/%s/relics/%s.png", SkinSelectScreen.Inst.idolName,CLASSNAME);
    private String IMG_OTL = String.format("gkmasModResource/img/idol/%s/relics/%s.png", SkinSelectScreen.Inst.idolName,CLASSNAME);
    private String IMG_LARGE = String.format("gkmasModResource/img/idol/%s/relics/large/%s.png", SkinSelectScreen.Inst.idolName,CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 2;

    private static final  int playTimes = 2;

    public static CommonEnum.IdolType type;

    private boolean RclickStart = false;

    private int maxRound = 8;

    private int currentRound = 8;

    public boolean startFinalCircle = false;


    public PocketBook() {
        super(ID, ImageMaster.loadImage(String.format("gkmasModResource/img/idol/%s/relics/%s.png", SkinSelectScreen.Inst.idolName,CLASSNAME)),
                ImageMaster.loadImage(String.format("gkmasModResource/img/idol/%s/relics/%s.png", SkinSelectScreen.Inst.idolName,CLASSNAME)), RARITY, LandingSound.CLINK);
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
                    // TODO 支持其他人物
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
        IdolCharacter player = (IdolCharacter) AbstractDungeon.player;
        player.IsRenderFinalCircle = false;
        startFinalCircle = false;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PocketBook();
    }


    public void onEquip() {
        if(AbstractDungeon.player instanceof IdolCharacter){
            IdolCharacter player = (IdolCharacter) AbstractDungeon.player;
            int[] threeSize = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getBaseThreeSize();
            float[] threeSizeRate = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getThreeSizeRate();
            player.setThreeSize(threeSize);
            player.setThreeSizeRate(threeSizeRate);
        }

    }

    public void atTurnStart() {
        if(!(AbstractDungeon.player instanceof IdolCharacter)){
            return;
        }

        if(startFinalCircle){
            currentRound = maxRound;
            IdolCharacter player = (IdolCharacter) AbstractDungeon.player;
            player.IsRenderFinalCircle = true;
            player.generateCircle(currentRound);
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new AllEffort(AbstractDungeon.player)));
            addToBot(new GainTrainRoundPowerWithoutEnergyAction(AbstractDungeon.player,15));
            startFinalCircle = false;
            return;
        }

        IdolCharacter player = (IdolCharacter) AbstractDungeon.player;
        if(player.IsRenderFinalCircle){
            if(currentRound>0){
                currentRound--;
                if(currentRound!=0){
                    player.generateCircle(currentRound);

                }

            }
            if(currentRound==0){
                player.IsRenderFinalCircle = false;
                player.finalCircleRound = new ArrayList<>();
            }

        }
    }

    public  void  onPlayerEndTurn(){

    }

    public void onPlayCard(AbstractCard c, AbstractMonster m) {

    }

    public void startFinalCircle(){
        startFinalCircle = true;
    }

    public void atBattleStart() {
        this.counter = playTimes;
        int trainRoundNum = (int) (RankHelper.getStep()*1.0f+12);
        if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss){
            trainRoundNum += AbstractDungeon.actNum*6;
        }

        if(type==CommonEnum.IdolType.SENSE){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TrainRoundSensePower(AbstractDungeon.player, trainRoundNum,false), trainRoundNum));
        }
        else if (type==CommonEnum.IdolType.LOGIC){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TrainRoundLogicPower(AbstractDungeon.player, trainRoundNum,false), trainRoundNum));
        }

        IdolCharacter player = (IdolCharacter) AbstractDungeon.player;


        if(MapRoomNodePatch.SPField.isSP.get(AbstractDungeon.getCurrMapNode())){
            System.out.println("SP");
            Random spRng = new Random(Settings.seed, AbstractDungeon.floorNum*20);
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                String monsterID = monster.id;
                String monsterName = monster.name;
                AbstractMonster.EnemyType type_ = monster.type;
                monster.maxHealth = (int)(monster.maxHealth*1.5f);
                monster.currentHealth = monster.maxHealth;
                if(monster.hasPower(MinionPower.POWER_ID)){
                    continue;
                }
                int spType =  spRng.random(0,2);
                switch (spType){
                    case 0:
                        monster.addPower(new VoSpPower(monster));
                        break;
                    case 1:
                        monster.addPower(new DaSpPower(monster));
                        break;
                    case 2:
                        monster.addPower(new ViSpPower(monster));
                        break;
                }
            }
        }

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

    public void onLoad(float[] args) {
        if(args != null && args.length == 6){
            if(AbstractDungeon.player instanceof IdolCharacter){
                IdolCharacter player = (IdolCharacter) AbstractDungeon.player;
                player.setThreeSizeAndRate(args);
            }
        }
    }

    public float[] onSave() {
        if(AbstractDungeon.player instanceof IdolCharacter){
            IdolCharacter player = (IdolCharacter) AbstractDungeon.player;
            return player.getThreeSizeAndRate();
        }
        return new float[]{0,0,0,0,0,0};
    }

}
