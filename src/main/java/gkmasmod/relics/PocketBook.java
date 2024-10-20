package gkmasmod.relics;
import basemod.BaseMod;
import basemod.CustomEventRoom;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import basemod.eventUtil.EventUtils;
import basemod.interfaces.ISubscriber;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.TheGuardian;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.powers.ModeShiftPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import gkmasmod.actions.GainTrainRoundPowerWithoutEnergyAction;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.monster.ending.MisuzuBoss;
import gkmasmod.patches.AbstractCardPatch;
import gkmasmod.patches.MapRoomNodePatch;
import gkmasmod.powers.*;
import gkmasmod.room.shop.AnotherShopOption;
import gkmasmod.screen.PocketBookViewScreen;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PocketBook extends CustomRelic  implements ISubscriber, CustomSavable<PocketBook.PocketBookSaveData> {

    private static final String CLASSNAME = PocketBook.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private String IMG = String.format("gkmasModResource/img/idol/%s/relics/%s.png", SkinSelectScreen.Inst.idolName,CLASSNAME);
    private String IMG_OTL = String.format("gkmasModResource/img/idol/%s/relics/%s.png", SkinSelectScreen.Inst.idolName,CLASSNAME);
    private String IMG_LARGE = String.format("gkmasModResource/img/idol/%s/relics/large/%s.png", SkinSelectScreen.Inst.idolName,CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 2;

    public static CommonEnum.IdolType type;

    private boolean RclickStart = false;

    private int maxRound = 8;

    private int currentRound = 8;

    public boolean startFinalCircle = false;

    public boolean merchant = true;

    private int planStep = 0;

    private boolean hajime = false;

    private int turnCount = 0;


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
            if(AbstractDungeon.currMapNode==null){
                return;
            }
            if (this.hb.hovered) {
                if (AbstractDungeon.screen == PocketBookViewScreen.Enum.PocketBookView_Screen){
                    AbstractDungeon.closeCurrentScreen();
                    AbstractDungeon.overlayMenu.cancelButton.hide();
                }
                else {
                    // TODO 支持其他人物
                    BaseMod.openCustomScreen(PocketBookViewScreen.Enum.PocketBookView_Screen, SkinSelectScreen.Inst.idolName, RankHelper.getStep(),planStep);
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
        IdolCharacter player = (IdolCharacter) AbstractDungeon.player;
        player.IsRenderFinalCircle = false;
        startFinalCircle = false;
        player.finalDamageRate = 1.0f;
        java.util.Random random = new java.util.Random();
        int index = random.nextInt(9)+1;
        SoundHelper.playSound(String.format("gkmasModResource/audio/voice/victory/%s_produce_result_%02d.ogg",SkinSelectScreen.Inst.idolName,index));
    }

    public void onUsePotion() {
        java.util.Random random = new java.util.Random();
        int index = random.nextInt(2)+1;
        SoundHelper.playSound(String.format("gkmasModResource/audio/voice/drink/%s_produce_p_drink_%02d.ogg",SkinSelectScreen.Inst.idolName,index));
    }

    public void wasHPLost(int damageAmount) {
        java.util.Random random = new java.util.Random();
        int index = random.nextInt(2)+1;
        SoundHelper.playSound(String.format("gkmasModResource/audio/voice/debuff/%s_produce_debuff_%02d.ogg",SkinSelectScreen.Inst.idolName,index));
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber);
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

    public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {
        if(AbstractDungeon.floorNum==15||AbstractDungeon.floorNum==32||AbstractDungeon.floorNum==49) {
            options.add(new AnotherShopOption(true));
        }
    }

    public void atTurnStart() {
        if(!(AbstractDungeon.player instanceof IdolCharacter)){
            return;
        }
        this.turnCount++;
        if(this.turnCount>1){
            java.util.Random random = new java.util.Random();
            int index = random.nextInt(19)+1;
            SoundHelper.playSound(String.format("gkmasModResource/audio/voice/turnStart/%s_produce_schedule_%02d.ogg",SkinSelectScreen.Inst.idolName,index));
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
                player.finalDamageRate = 1.0f;
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
        this.counter = RankHelper.getStep();
        this.turnCount = 0;
        int trainRoundNum = (int) (RankHelper.getStep()*1.5f+16);
        if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite){
            trainRoundNum += 5;
        }
        if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss){
            trainRoundNum += 10;
        }

        java.util.Random random = new java.util.Random();
        int index = random.nextInt(14)+1;
        SoundHelper.playSound(String.format("gkmasModResource/audio/voice/battleStart/%s_produce_lesson_cmmn_%02d.ogg",SkinSelectScreen.Inst.idolName,index));

        if(type==CommonEnum.IdolType.SENSE){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TrainRoundSensePower(AbstractDungeon.player, trainRoundNum,false), trainRoundNum));
        }
        else if (type==CommonEnum.IdolType.LOGIC){
            AbstractCreature target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            addToBot(new ApplyPowerAction(target,AbstractDungeon.player,new OverDamageTransfer(target)));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TrainRoundLogicPower(AbstractDungeon.player, trainRoundNum,false), trainRoundNum));
        }

        IdolCharacter player = (IdolCharacter) AbstractDungeon.player;

        if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss){
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if(monster.id.equals(MisuzuBoss.ID)){
                    return;
                }
            }
            currentRound = maxRound + 1;
            player.IsRenderFinalCircle = true;
            player.generateCircle(currentRound);
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new AllEffort(AbstractDungeon.player)));
            buffBoss();
        }

        if(MapRoomNodePatch.SPField.isSP.get(AbstractDungeon.getCurrMapNode())){
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

    private void buffBoss(){
        int act = AbstractDungeon.actNum;
        int rate = ThreeSizeHelper.getHealthRate(act);
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            String monsterID = monster.id;
            String monsterName = monster.name;
            AbstractMonster.EnemyType type_ = monster.type;
            int currentHP = monster.currentHealth;
            int maxHP = monster.maxHealth;
            monster.maxHealth = monster.maxHealth*rate;
            monster.currentHealth = monster.currentHealth*rate;
            int dmgThreshold = 30;
            if(monsterID.equals(TheGuardian.ID)) {
                if (AbstractDungeon.ascensionLevel >= 19) {
                    dmgThreshold = 40;
                } else if (AbstractDungeon.ascensionLevel >= 9) {
                    dmgThreshold = 35;
                } else {
                    dmgThreshold = 30;
                }
                dmgThreshold = dmgThreshold * rate;

                addToBot(new RemoveSpecificPowerAction(monster, monster, ModeShiftPower.POWER_ID));
                addToBot(new ApplyPowerAction(monster, monster, new ModeShiftPower(monster, dmgThreshold), dmgThreshold));
            }
            }
    }

    private void checkPlan(){
        if(planStep >2)
            return;
        if(AbstractDungeon.player instanceof IdolCharacter){
            IdolCharacter player = (IdolCharacter) AbstractDungeon.player;
            int[] planTypes = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getPlanTypes();
            int currentValue = player.getThreeSize()[planTypes[planStep]];
            int targetValue = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getPlanRequires()[planStep];
            System.out.println(currentValue+";"+targetValue);
            if(currentValue>=targetValue){
                String eventID = String.format("Plan_%s%d",SkinSelectScreen.Inst.idolName,planStep+1);
                System.out.println(eventID);
                AbstractEvent event =  EventUtils.getEvent(eventID);
                if(event!=null){
                    AbstractDungeon.eventList.add(0, eventID);
                    RoomEventDialog.optionList.clear();
                    MapRoomNode cur = AbstractDungeon.currMapNode;
                    MapRoomNode mapRoomNode2 = new MapRoomNode(cur.x, cur.y);
                    CustomEventRoom cer = new CustomEventRoom();
                    mapRoomNode2.room = cer;
//                    ArrayList<MapEdge> curEdges = cur.getEdges();
//                    for (MapEdge edge : curEdges)
//                        mapRoomNode2.addEdge(edge);
                    AbstractDungeon.player.releaseCard();
                    AbstractDungeon.overlayMenu.hideCombatPanels();
                    AbstractDungeon.previousScreen = null;
                    AbstractDungeon.dynamicBanner.hide();
                    AbstractDungeon.dungeonMapScreen.closeInstantly();
                    AbstractDungeon.closeCurrentScreen();
                    AbstractDungeon.topPanel.unhoverHitboxes();
                    AbstractDungeon.fadeIn();
                    AbstractDungeon.effectList.clear();
                    AbstractDungeon.topLevelEffects.clear();
                    AbstractDungeon.topLevelEffectsQueue.clear();
                    AbstractDungeon.effectsQueue.clear();
                    AbstractDungeon.dungeonMapScreen.dismissable = true;
                    //AbstractDungeon.nextRoom = mapRoomNode2;
                    AbstractDungeon.setCurrMapNode(mapRoomNode2);

                    try {
                        AbstractDungeon.overlayMenu.proceedButton.hide();
                        GkmasMod.node = cur;
                        //event.onEnterRoom();
                    } catch (Exception e) {
                    }
                }
                planStep++;
            }
        }
    }

    private void getHajimeReward(){
        if(this.hajime == true)
            return;
        if(AbstractDungeon.player instanceof IdolCharacter){
                String eventID = "HajimeReward";
                AbstractEvent event =  EventUtils.getEvent(eventID);
                System.out.println(eventID);
                if(event!=null){
                    this.hajime = true;
                    System.out.println(eventID);
                    AbstractDungeon.eventList.add(0, eventID);
                    RoomEventDialog.optionList.clear();
                    MapRoomNode cur = AbstractDungeon.currMapNode;
                    MapRoomNode mapRoomNode2 = new MapRoomNode(cur.x, cur.y);
                    CustomEventRoom cer = new CustomEventRoom();
                    mapRoomNode2.room = cer;
                    AbstractDungeon.player.releaseCard();
                    AbstractDungeon.overlayMenu.hideCombatPanels();
                    AbstractDungeon.previousScreen = null;
                    AbstractDungeon.dynamicBanner.hide();
                    AbstractDungeon.dungeonMapScreen.closeInstantly();
                    AbstractDungeon.closeCurrentScreen();
                    AbstractDungeon.topPanel.unhoverHitboxes();
                    AbstractDungeon.fadeIn();
                    AbstractDungeon.effectList.clear();
                    AbstractDungeon.topLevelEffects.clear();
                    AbstractDungeon.topLevelEffectsQueue.clear();
                    AbstractDungeon.effectsQueue.clear();
                    AbstractDungeon.dungeonMapScreen.dismissable = true;
                    //AbstractDungeon.nextRoom = mapRoomNode2;
                    AbstractDungeon.setCurrMapNode(mapRoomNode2);

                    try {
                        AbstractDungeon.overlayMenu.proceedButton.hide();
                        GkmasMod.node = cur;
                        event.onEnterRoom();
                    } catch (Exception e) {
                    }
                }
        }
    }


    public void justEnteredRoom(AbstractRoom room) {

        this.grayscale = false;
        System.out.println(AbstractDungeon.floorNum);
        if(AbstractDungeon.floorNum==17){
            getHajimeReward();
            return;
        }
        if(room instanceof MonsterRoom || room instanceof MonsterRoomElite || room instanceof MonsterRoomBoss){
        }
        else{
            checkPlan();
        }
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }

    public void onLoad(PocketBookSaveData args) {
        if(args != null){
            if(AbstractDungeon.player instanceof IdolCharacter){
                IdolCharacter player = (IdolCharacter) AbstractDungeon.player;
                player.setThreeSize(args.threeSize);
                player.setThreeSizeRate(args.threeSizeRate);
                this.planStep = args.planStep;
                GkmasMod.threeSizeTagRng = new Random(Settings.seed, args.threeSizeTagRng);
                setMasterCardTags(args.masterCardTags);
            }
        }
    }

    @Override
    public PocketBookSaveData onSave() {

        if(AbstractDungeon.player instanceof IdolCharacter){
            IdolCharacter player = (IdolCharacter) AbstractDungeon.player;

            return new PocketBookSaveData(
                    player.getThreeSize(),
                    player.getThreeSizeRate(),
                    this.planStep,
                    GkmasMod.threeSizeTagRng.counter,
                    getMasterCardTags()
            );
        }
        return null;
    }

        private ArrayList<Integer> getMasterCardTags(){
        ArrayList<Integer> masterCardTags = new ArrayList<>();
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            int tag = AbstractCardPatch.ThreeSizeTagField.threeSizeTag.get(card);
            if(tag!=-1) {
                masterCardTags.add(tag);
            }
        }
        return masterCardTags;
    }

    private void setMasterCardTags(ArrayList<Integer> tags){
        int index=0;
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            System.out.println(card.cardID);
            if(index < tags.size()){
                AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(card,tags.get(index));
                index++;
            }
        }
    }

    public static class PocketBookSaveData {
        public int[] threeSize;
        public float[] threeSizeRate;
        public int planStep;
        public int threeSizeTagRng;
        public ArrayList<Integer> masterCardTags;

        public PocketBookSaveData(int[] threeSize, float[] threeSizeRate,int planStep, int threeSizeTagRng, ArrayList<Integer> masterCardTags) {
            this.threeSize = threeSize;
            this.threeSizeRate = threeSizeRate;
            this.planStep = planStep;
            this.threeSizeTagRng = threeSizeTagRng;
            this.masterCardTags = masterCardTags;
        }
    }

}
