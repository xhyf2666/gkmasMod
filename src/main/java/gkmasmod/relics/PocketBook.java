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
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.TimeEater;
import com.megacrit.cardcrawl.monsters.exordium.TheGuardian;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.powers.ModeShiftPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.actions.TrainRoundAnomalyFirstAction;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.dungeons.IdolRoad;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.monster.ending.MisuzuBoss;
import gkmasmod.patches.AbstractCardPatch;
import gkmasmod.patches.AbstractPlayerPatch;
import gkmasmod.patches.MapRoomNodePatch;
import gkmasmod.powers.*;
import gkmasmod.room.EventMonsterRoom;
import gkmasmod.room.FixedMonsterRoom;
import gkmasmod.room.GkmasBossRoom;
import gkmasmod.room.shop.AnotherShopOption;
import gkmasmod.room.shop.AnotherShopScreen;
import gkmasmod.room.specialTeach.SpecialTeachOption;
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

    public int maxRound = 8;

    private int currentRound = 8;

    public boolean startFinalCircle = false;

    public boolean merchant = true;

    private int planStep = 0;

    private boolean hajime = false;

    private int turnCount = 0;

    public int purgeTimes = 0;

    public int threeSizeIncrease = 0;

    public float healthRate = 1.0f;

    public int masterEventCount = 0;

    public boolean isAttacked = false;


    public PocketBook() {
        super(ID, ImageMaster.loadImage(String.format("gkmasModResource/img/idol/%s/relics/%s.png", SkinSelectScreen.Inst.idolName,CLASSNAME)),
                ImageMaster.loadImage(String.format("gkmasModResource/img/idol/%s/relics/%s.png", SkinSelectScreen.Inst.idolName,CLASSNAME)), RARITY, LandingSound.CLINK);
        type = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getType(SkinSelectScreen.Inst.skinIndex);
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
        AbstractPlayerPatch.IsRenderFinalCircleField.IsRenderFinalCircle.set(AbstractDungeon.player, false);
//        player.IsRenderFinalCircle = false;
        isAttacked = false;
        startFinalCircle = false;
        AbstractPlayerPatch.FinalDamageRateField.finalDamageRate.set(AbstractDungeon.player, 1.0);
//        player.finalDamageRate = 1.0f;
        if(AbstractDungeon.player instanceof IdolCharacter){
            java.util.Random random = new java.util.Random();
            int index = random.nextInt(9)+1;
            SoundHelper.playSound(String.format("gkmasModResource/audio/voice/victory/%s_produce_result_%02d.ogg",SkinSelectScreen.Inst.idolName,index));
        }

    }

    public void onUsePotion() {
        if(AbstractDungeon.player instanceof IdolCharacter){
            java.util.Random random = new java.util.Random();
            int index = random.nextInt(2)+1;
            SoundHelper.playSound(String.format("gkmasModResource/audio/voice/drink/%s_produce_p_drink_%02d.ogg",SkinSelectScreen.Inst.idolName,index));
        }

    }

    public void wasHPLost(int damageAmount) {
        if(!isAttacked){
            if(AbstractDungeon.player instanceof IdolCharacter){
                java.util.Random random = new java.util.Random();
                int index = random.nextInt(2)+1;
                SoundHelper.playSound(String.format("gkmasModResource/audio/voice/debuff/%s_produce_debuff_%02d.ogg",SkinSelectScreen.Inst.idolName,index));
            }
            isAttacked = true;
        }
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
        int[] threeSize;
        float[] threeSizeRate;
        if(AbstractDungeon.player instanceof IdolCharacter){
            IdolCharacter player = (IdolCharacter) AbstractDungeon.player;
            threeSize = player.idolData.getBaseThreeSize();
            threeSizeRate = player.idolData.getThreeSizeRate();
        }
        else{
            threeSize = new int[]{70,70,70};
            threeSizeRate = new float[]{0.15f,0.15f,0.15f};
            Random spRng = new Random(Settings.seed, AbstractDungeon.actNum*100);
            float chance = 0.15f;
            chance += AbstractDungeon.actNum * 0.1f;
            if(AbstractDungeon.floorNum %17 >7)
                chance += 0.05f;
            int row_num = AbstractDungeon.map.size() - 1;
            while(row_num >= 0) {
                for(MapRoomNode n : AbstractDungeon.map.get(row_num)){
                    if(n.room instanceof MonsterRoom){
                        if(spRng.randomBoolean(chance)){
                            MapRoomNodePatch.SPField.isSP.set(n, true);
                        }
                        else {
                            MapRoomNodePatch.SPField.isSP.set(n, false);
                        }
                    }
                }
                row_num--;
            }
            for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
                if(card.rarity!= AbstractCard.CardRarity.CURSE){
                    int tag = GkmasMod.threeSizeTagRng.random(0, 2);
                    AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(card, tag);
                }
            }
        }
        ThreeSizeHelper.setThreeSize(threeSize);
        ThreeSizeHelper.setThreeSizeRate(threeSizeRate);
    }

    public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {
        if(AbstractDungeon.floorNum==15||AbstractDungeon.floorNum==32||AbstractDungeon.floorNum==49) {
            options.add(new AnotherShopOption(true));
            options.add(new SpecialTeachOption(true));
        }
    }

    public void atTurnStart() {
        this.turnCount++;
        if(this.turnCount>1){
            if(AbstractDungeon.player instanceof IdolCharacter){
                java.util.Random random = new java.util.Random();
                int index = random.nextInt(19)+1;
                SoundHelper.playSound(String.format("gkmasModResource/audio/voice/turnStart/%s_produce_schedule_%02d.ogg",SkinSelectScreen.Inst.idolName,index));
            }

        }

        if(startFinalCircle){
            currentRound = maxRound;
            if(AbstractDungeon.player.hasRelic(MasterSupportFrame.ID))
                currentRound--;
            AbstractPlayerPatch.IsRenderFinalCircleField.IsRenderFinalCircle.set(AbstractDungeon.player, true);
            ThreeSizeHelper.generateCircle(currentRound);
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new AllEffort(AbstractDungeon.player)));
            addToBot(new GainTrainRoundPowerAction(AbstractDungeon.player,15,false));
            startFinalCircle = false;
            return;
        }

        if(AbstractPlayerPatch.IsRenderFinalCircleField.IsRenderFinalCircle.get(AbstractDungeon.player)){
            if(currentRound>0){
                currentRound--;
                if(currentRound!=0){
                    ThreeSizeHelper.generateCircle(currentRound);
                }

            }
            if(currentRound==0){
                AbstractPlayerPatch.IsRenderFinalCircleField.IsRenderFinalCircle.set(AbstractDungeon.player, false);
                AbstractPlayerPatch.FinalCircleRoundField.finalCircleRound.set(AbstractDungeon.player,new ArrayList<>());
                AbstractPlayerPatch.FinalDamageRateField.finalDamageRate.set(AbstractDungeon.player, 1.0);

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
        isAttacked = false;
        this.counter = RankHelper.getStep();
        this.turnCount = 0;
        int trainRoundNum = (int) (RankHelper.getStep()*1.5f+16);
        if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite){
            trainRoundNum += 5;
        }
        if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss){
            trainRoundNum += 10;
        }
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if(monster.id.equals(MisuzuBoss.ID)){
                trainRoundNum += 20;
            }
        }

        if(AbstractDungeon.player instanceof IdolCharacter){
            java.util.Random random = new java.util.Random();
            int index = random.nextInt(14)+1;
            SoundHelper.playSound(String.format("gkmasModResource/audio/voice/battleStart/%s_produce_lesson_cmmn_%02d.ogg",SkinSelectScreen.Inst.idolName,index));
        }

        //TODO 这里要支持其他人物
        if(type==CommonEnum.IdolType.SENSE){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TrainRoundSensePower(AbstractDungeon.player, trainRoundNum,false), trainRoundNum));
        }
        else if (type==CommonEnum.IdolType.LOGIC){
            AbstractCreature target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            addToBot(new ApplyPowerAction(target,AbstractDungeon.player,new OverDamageTransfer(target)));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TrainRoundLogicPower(AbstractDungeon.player, trainRoundNum,false), trainRoundNum));
        }
        else if(type==CommonEnum.IdolType.ANOMALY){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TrainRoundAnomalyPower(AbstractDungeon.player, trainRoundNum,false), trainRoundNum));
            addToBot(new TrainRoundAnomalyFirstAction());
        }

        if(this.healthRate>1.0f){
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters){
                monster.maxHealth = (int)(monster.maxHealth*this.healthRate);
                monster.currentHealth = monster.maxHealth;
            }
        }

        if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss && !AbstractDungeon.id.equals(IdolRoad.ID)){
            int act = AbstractDungeon.actNum;
            if(act>3){
                return;
            }
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if(monster.id.equals(MisuzuBoss.ID)){
                    return;
                }
            }
            currentRound = maxRound + 1;
            if(AbstractDungeon.player.hasRelic(MasterSupportFrame.ID))
                currentRound--;
            AbstractPlayerPatch.IsRenderFinalCircleField.IsRenderFinalCircle.set(AbstractDungeon.player, true);
            ThreeSizeHelper.generateCircle(currentRound);
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
            if(monsterID.equals(TimeEater.ID)){
                monster.maxHealth = monster.maxHealth*(rate-3);
                monster.currentHealth = monster.currentHealth*(rate-3);
            }
            else{
                monster.maxHealth = monster.maxHealth*rate;
                monster.currentHealth = monster.currentHealth*rate;
            }
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
            int[] planTypes = player.idolData.getPlanTypes();
            int currentValue = AbstractPlayerPatch.ThreeSizeField.threeSize.get(player)[planTypes[planStep]];
            int targetValue = player.idolData.getPlanRequires()[planStep];
            if(currentValue>=targetValue){
                String eventID = String.format("Plan_%s%d",SkinSelectScreen.Inst.idolName,planStep+1);
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
                if(event!=null){
                    this.hajime = true;
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


    private void getMaster(){
        if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
            if(masterEventCount>2)
                return;
            String eventID = "MasterEvent2";
            if (masterEventCount==1)
                eventID = "MasterEvent2";
            else if(masterEventCount==2)
                eventID = "MasterEvent3";
            masterEventCount++;
            AbstractEvent event =  EventUtils.getEvent(eventID);
            if(event!=null){
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
        this.purgeTimes = AnotherShopScreen.purgeTimes;
        this.grayscale = false;


        if(room instanceof FixedMonsterRoom){
            String tmp = ((FixedMonsterRoom) room).encounterID;
            if (tmp.startsWith("IdolBoss_")) {
                tmp = tmp.substring(9);
            }
            java.util.Random random = new java.util.Random(Settings.seed);
            ArrayList<String> relics = IdolData.getIdol(tmp).getRelicList();
            AbstractRelic relic;
            relic = RelicLibrary.getRelic(relics.get(random.nextInt(relics.size()))).makeCopy();
            room.rewards.add(new RewardItem(relic));
        }
        if(room instanceof EventMonsterRoom){
            String tmp = ((EventMonsterRoom) room).encounterID;
            if (tmp.startsWith("IdolBoss_")) {
                tmp = tmp.substring(9);
            }
            java.util.Random random = new java.util.Random(Settings.seed);
            ArrayList<String> relics = IdolData.getIdol(tmp).getRelicList();
            AbstractRelic relic;
            relic = RelicLibrary.getRelic(relics.get(random.nextInt(relics.size()))).makeCopy();
            room.rewards.add(new RewardItem(relic));
        }

        if(room instanceof MonsterRoom || room instanceof MonsterRoomElite || room instanceof MonsterRoomBoss){
            return;
        }

        if(AbstractDungeon.floorNum>=9&&masterEventCount==1){
            getMaster();
            return;
        }
        if(AbstractDungeon.floorNum>=20&&masterEventCount==2){
            getMaster();
            return;
        }
        if(AbstractDungeon.floorNum==17){
            getHajimeReward();

            return;
        }
        checkPlan();

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
            if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
                ThreeSizeHelper.setThreeSize(args.threeSize);
                ThreeSizeHelper.setThreeSizeRate(args.threeSizeRate);
                this.planStep = args.planStep;
                GkmasMod.threeSizeTagRng = new Random(Settings.seed, args.threeSizeTagRng);
                setMasterCardTags(args.masterCardTags);
                this.purgeTimes = args.purgeTimes;
                AnotherShopScreen.purgeTimes = args.purgeTimes;
                this.threeSizeIncrease = args.threeSizeIncrease;
                this.healthRate = args.healthRate;
                this.masterEventCount = args.masterEventCount;
            }
        }
    }

    @Override
    public PocketBookSaveData onSave() {

        if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
            return new PocketBookSaveData(
                    ThreeSizeHelper.getThreeSize(),
                    ThreeSizeHelper.getThreeSizeRate(),
                    this.planStep,
                    GkmasMod.threeSizeTagRng.counter,
                    getMasterCardTags(),
                    this.purgeTimes,
                    this.threeSizeIncrease,
                    this.healthRate,
                    this.masterEventCount
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
        public int purgeTimes;
        public int threeSizeIncrease;
        public float healthRate;
        public int masterEventCount;

        public PocketBookSaveData(int[] threeSize, float[] threeSizeRate,int planStep, int threeSizeTagRng, ArrayList<Integer> masterCardTags,int purgeTimes,int threeSizeIncrease,float healthRate,int masterEventCount) {
            this.threeSize = threeSize;
            this.threeSizeRate = threeSizeRate;
            this.planStep = planStep;
            this.threeSizeTagRng = threeSizeTagRng;
            this.masterCardTags = masterCardTags;
            this.purgeTimes = purgeTimes;
            this.threeSizeIncrease = threeSizeIncrease;
            this.healthRate = healthRate;
            this.masterEventCount = masterEventCount;
        }
    }

}
