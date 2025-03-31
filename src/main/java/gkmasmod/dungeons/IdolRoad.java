package gkmasmod.dungeons;

import actlikeit.dungeons.CustomDungeon;
import basemod.CustomEventRoom;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.map.DungeonMap;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.downfall.bosses.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import com.megacrit.cardcrawl.scenes.TheEndingScene;
import gkmasmod.event.FirstStarHotSpring;
import gkmasmod.room.EventMonsterRoom;
import gkmasmod.room.FixedMonsterRoom;
import gkmasmod.room.GkmasBossRoom;
import gkmasmod.room.GkmasEventRoom;
import gkmasmod.screen.SkinSelectScreen;

import java.util.ArrayList;
import java.util.Collections;

public class IdolRoad extends CustomDungeon {
    public static String ID = "IdolRoad";

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    public static String NAME = eventStrings.NAME;

    public IdolRoad() {
        super(NAME, ID,"images/ui/event/panel.png",false,0,0,1);
        this.setMainMusic("gkmasModResource/audio/bgm/bgm_idolRoad.ogg");
    }


    public IdolRoad(CustomDungeon cd, AbstractPlayer p, ArrayList<String> emptyList) {
        super(cd, p, emptyList);
        this.setMainMusic("gkmasModResource/audio/bgm/bgm_idolRoad.ogg");
    }

    public IdolRoad(CustomDungeon cd, AbstractPlayer p, SaveFile saveFile) {
        super(cd, p, saveFile);
        this.setMainMusic("gkmasModResource/audio/bgm/bgm_idolRoad.ogg");
    }

    @Override
    public AbstractScene DungeonScene() {
        return new TheEndingScene();
    }

    @Override
    public void Ending() {
        CardCrawlGame.music.fadeOutBGM();
//        MapRoomNode node = new MapRoomNode(3, 5);
        MapRoomNode node = new MapRoomNode(3, 6);
        node.room = new TrueVictoryRoom();
        AbstractDungeon.nextRoom = node;
        AbstractDungeon.closeCurrentScreen();
        AbstractDungeon.nextRoomTransitionStart();
        AbstractDungeon.overlayMenu.proceedButton.hide();
    }

    public String getOptionText() {
        return "前往更广阔的舞台(难度较高)";
    }

    @Override
    protected void makeMap() {
        AbstractDungeon.levelNum = "偶像之间的战斗，不是■就是被■";
        ArrayList<MonsterRoomCreator> tmpBossList = new ArrayList<>();
        tmpBossList.add(new MonsterRoomCreator("gkmasModResource/img/map/amao_BossIcon.png", IdolBoss_amao.ID));
        tmpBossList.add(new MonsterRoomCreator("gkmasModResource/img/map/fktn_BossIcon.png", IdolBoss_fktn.ID));
        tmpBossList.add(new MonsterRoomCreator("gkmasModResource/img/map/hrnm_BossIcon.png", IdolBoss_hrnm.ID));
        tmpBossList.add(new MonsterRoomCreator("gkmasModResource/img/map/hski_BossIcon.png", IdolBoss_hski.ID));
        tmpBossList.add(new MonsterRoomCreator("gkmasModResource/img/map/hume_BossIcon.png", IdolBoss_hume.ID));
        tmpBossList.add(new MonsterRoomCreator("gkmasModResource/img/map/shro_BossIcon.png", IdolBoss_shro.ID));
        tmpBossList.add(new MonsterRoomCreator("gkmasModResource/img/map/kcna_BossIcon.png", IdolBoss_kcna.ID));
        tmpBossList.add(new MonsterRoomCreator("gkmasModResource/img/map/kllj_BossIcon.png", IdolBoss_kllj.ID));
        tmpBossList.add(new MonsterRoomCreator("gkmasModResource/img/map/ssmk_BossIcon.png", IdolBoss_ssmk.ID));
        tmpBossList.add(new MonsterRoomCreator("gkmasModResource/img/map/ttmr_BossIcon.png", IdolBoss_ttmr.ID));
        tmpBossList.add(new MonsterRoomCreator("gkmasModResource/img/map/jsna_BossIcon.png", IdolBoss_jsna.ID));

        String curIdolName = "";
        if(AbstractDungeon.player instanceof IdolCharacter){
            curIdolName = SkinSelectScreen.Inst.idolName;
            curIdolName = String.format("IdolBoss_%s",curIdolName);
        }
        if(!curIdolName.equals("")){
            for(int i=0;i<tmpBossList.size();i++){
                if(tmpBossList.get(i).encounterID.equals(curIdolName)){
                    tmpBossList.remove(i);
                    break;
                }
            }
        }

        //使用Setting.seed作为随机数，打乱bosslist的顺序
        java.util.Random rng = new java.util.Random(Settings.seed);
        Collections.shuffle(tmpBossList, rng);

        // 8进4的3位胜者
        int battle1_1 = rng.nextInt(2);
        int battle1_2 = rng.nextInt(2)+2;
        int battle1_3 = rng.nextInt(2)+5;

        // 4进2的1位胜者
        int battle2_1 = rng.nextInt(2);
        if(battle2_1 == 0){
            battle2_1 = battle1_1;
        }else{
            battle2_1 = battle1_2;
        }


        MapRoomNode eventNode = new MapRoomNode(4, 1);
        eventNode.room = new GkmasEventRoom(FirstStarHotSpring.ID);

        MapRoomNode restNode = new MapRoomNode(4, 2);
        restNode.room = new RestRoom();

        MapRoomNode battleRoom2 = new MapRoomNode(4, 3);

        MapRoomNode finalBattleRoom = new MapRoomNode(3, 4);

        MapRoomNode victoryNode = new MapRoomNode(3, 5);
        victoryNode.room = new TrueVictoryRoom();
        map = new ArrayList();



        //8进4
        MapRoomNode one = new MapRoomNode(0, 1);
        one.room = (tmpBossList.get(0)).get();
        MapRoomNode two = new MapRoomNode(1, 1);
        two.room = (tmpBossList.get(1)).get();
        MapRoomNode three = new MapRoomNode(2, 1);
        three.room = (tmpBossList.get(2)).get();
        MapRoomNode four = new MapRoomNode(3, 1);
        four.room = (tmpBossList.get(3)).get();
        MapRoomNode five = new MapRoomNode(4, 0);
        five.room = (tmpBossList.get(4)).get();
        MapRoomNode six = new MapRoomNode(5, 1);
        six.room = (tmpBossList.get(5)).get();
        MapRoomNode seven = new MapRoomNode(6, 1);
        seven.room = (tmpBossList.get(6)).get();


        //4进2
        MapRoomNode eight = new MapRoomNode(1, 2);
        eight.room = (tmpBossList.get(battle1_1)).get();
        MapRoomNode nine = new MapRoomNode(2, 2);
        nine.room = (tmpBossList.get(battle1_2)).get();
        MapRoomNode ten = new MapRoomNode(5, 2);
        ten.room = (tmpBossList.get(battle1_3)).get();
//        battleRoom2.room = (tmpBossList.get(battle1_3)).get();
        battleRoom2.room = new EventMonsterRoom(tmpBossList.get(battle1_3).encounterID);

        //2进1
        MapRoomNode eleven = new MapRoomNode(2, 3);
        eleven.room = (tmpBossList.get(battle2_1)).get();
        finalBattleRoom.room = new GkmasBossRoom(tmpBossList.get(battle2_1).encounterID,"gkmasModResource/img/map/TrueEndIcon.png");

        if (DungeonMap.boss != null && DungeonMap.bossOutline != null) {
            DungeonMap.boss.dispose();
            DungeonMap.bossOutline.dispose();
        }
        DungeonMap.boss = ImageMaster.loadImage("gkmasModResource/img/map/TrueEndIcon.png");
        DungeonMap.bossOutline = ImageMaster.loadImage("gkmasModResource/img/map/TrueEndIcon.png");

        connectNode(one,eight);
        connectNode(two,eight);
        connectNode(three,nine);
        connectNode(four,nine);
        connectNode(five,eventNode);
        connectNode(six,ten);
        connectNode(seven,ten);
        connectNode(eight,eleven);
        connectNode(nine,eleven);
        connectNode(ten,battleRoom2);
        connectNode(eleven,finalBattleRoom);
        connectNode(eventNode,restNode);
        connectNode(restNode,battleRoom2);
        connectNode(battleRoom2,finalBattleRoom);
//        connectNode(finalBattleRoom,victoryNode);
//        eventNode.addEdge(new MapEdge(eventNode.x, eventNode.y, eventNode.offsetX, eventNode.offsetY, bossNode.x, bossNode.y, bossNode.offsetX, bossNode.offsetY, false));
        ArrayList<MapRoomNode> row1 = new ArrayList<>();
        row1.add(new MapRoomNode(0, 0));
        row1.add(new MapRoomNode(1, 0));
        row1.add(new MapRoomNode(2, 0));
        row1.add(new MapRoomNode(3, 0));
        row1.add(five);
        row1.add(new MapRoomNode(5, 0));
        row1.add(new MapRoomNode(6, 0));

        ArrayList<MapRoomNode> row2 = new ArrayList<>();
        row2.add(one);
        row2.add(two);
        row2.add(three);
        row2.add(four);
        row2.add(eventNode);
        row2.add(six);
        row2.add(seven);

        ArrayList<MapRoomNode> row3 = new ArrayList<>();
        row3.add(new MapRoomNode(0, 2));
        row3.add(eight);
        row3.add(nine);
        row3.add(new MapRoomNode(3, 2));
        row3.add(restNode);
        row3.add(ten);
        row3.add(new MapRoomNode(6, 2));


        ArrayList<MapRoomNode> row4 = new ArrayList<>();
        row4.add(new MapRoomNode(0, 3));
        row4.add(new MapRoomNode(1, 3));
        row4.add(eleven);
        row4.add(new MapRoomNode(3, 3));
        row4.add(battleRoom2);
        row4.add(new MapRoomNode(5, 3));
        row4.add(new MapRoomNode(6, 3));

        ArrayList<MapRoomNode> row5 = new ArrayList<>();
        row5.add(new MapRoomNode(0, 4));
        row5.add(new MapRoomNode(1, 4));
        row5.add(new MapRoomNode(2, 4));
        row5.add(finalBattleRoom);
        row5.add(new MapRoomNode(4, 4));
        row5.add(new MapRoomNode(5, 4));
        row5.add(new MapRoomNode(6, 4));

        ArrayList<MapRoomNode> row6 = new ArrayList<>();
        row6.add(new MapRoomNode(0, 5));
        row6.add(new MapRoomNode(1, 5));
        row6.add(new MapRoomNode(2, 5));
        row6.add(victoryNode);
        row6.add(new MapRoomNode(4, 5));
        row6.add(new MapRoomNode(5, 5));
        row6.add(new MapRoomNode(6, 5));

        map.add(row1);
        map.add(row2);
        map.add(row3);
        map.add(row4);
        map.add(row5);
        map.add(row6);
        firstRoomChosen = false;
        fadeIn();
    }

    private void connectNode(MapRoomNode src, MapRoomNode dst) {
        src.addEdge(new MapEdge(src.x, src.y, src.offsetX, src.offsetY, dst.x, dst.y, dst.offsetX, dst.offsetY, false));
    }

    protected void generateMonsters() {
        monsterList = new ArrayList();
        monsterList.add(IdolBoss_ttmr.ID);
        monsterList.add(IdolBoss_fktn.ID);
        monsterList.add(IdolBoss_hski.ID);
        monsterList.add(IdolBoss_ttmr.ID);
        monsterList.add(IdolBoss_fktn.ID);
        monsterList.add(IdolBoss_hski.ID);
        monsterList.add(IdolBoss_ttmr.ID);
        monsterList.add(IdolBoss_fktn.ID);
        monsterList.add(IdolBoss_hski.ID);
        monsterList.add(IdolBoss_ttmr.ID);
        monsterList.add(IdolBoss_fktn.ID);
        monsterList.add(IdolBoss_hski.ID);
        eliteMonsterList = new ArrayList();
        eliteMonsterList.add(IdolBoss_shro.ID);
        eliteMonsterList.add(IdolBoss_kcna.ID);
        eliteMonsterList.add(IdolBoss_hume.ID);
        eliteMonsterList.add(IdolBoss_shro.ID);
        eliteMonsterList.add(IdolBoss_kcna.ID);
        eliteMonsterList.add(IdolBoss_hume.ID);
        eliteMonsterList.add(IdolBoss_shro.ID);
        eliteMonsterList.add(IdolBoss_kcna.ID);
        eliteMonsterList.add(IdolBoss_hume.ID);
        eliteMonsterList.add(IdolBoss_shro.ID);
        eliteMonsterList.add(IdolBoss_kcna.ID);
        eliteMonsterList.add(IdolBoss_hume.ID);
        eliteMonsterList.add(IdolBoss_shro.ID);
        eliteMonsterList.add(IdolBoss_kcna.ID);
        eliteMonsterList.add(IdolBoss_hume.ID);
    }

    @Override
    public void initializeBoss() {
        ArrayList<MonsterRoomCreator> tmpBossList = new ArrayList<>();
        tmpBossList.add(new MonsterRoomCreator("gkmasModResource/img/map/amao_BossIcon.png", IdolBoss_amao.ID));
        tmpBossList.add(new MonsterRoomCreator("gkmasModResource/img/map/fktn_BossIcon.png", IdolBoss_fktn.ID));
        tmpBossList.add(new MonsterRoomCreator("gkmasModResource/img/map/hrnm_BossIcon.png", IdolBoss_hrnm.ID));
        tmpBossList.add(new MonsterRoomCreator("gkmasModResource/img/map/hski_BossIcon.png", IdolBoss_hski.ID));
        tmpBossList.add(new MonsterRoomCreator("gkmasModResource/img/map/hume_BossIcon.png", IdolBoss_hume.ID));
        tmpBossList.add(new MonsterRoomCreator("gkmasModResource/img/map/shro_BossIcon.png", IdolBoss_shro.ID));
        tmpBossList.add(new MonsterRoomCreator("gkmasModResource/img/map/kcna_BossIcon.png", IdolBoss_kcna.ID));
        tmpBossList.add(new MonsterRoomCreator("gkmasModResource/img/map/kllj_BossIcon.png", IdolBoss_kllj.ID));
        tmpBossList.add(new MonsterRoomCreator("gkmasModResource/img/map/ssmk_BossIcon.png", IdolBoss_ssmk.ID));
        tmpBossList.add(new MonsterRoomCreator("gkmasModResource/img/map/ttmr_BossIcon.png", IdolBoss_ttmr.ID));
        tmpBossList.add(new MonsterRoomCreator("gkmasModResource/img/map/jsna_BossIcon.png", IdolBoss_jsna.ID));
        String curIdolName = "";
        if(AbstractDungeon.player instanceof IdolCharacter){
            curIdolName = SkinSelectScreen.Inst.idolName;
            curIdolName = String.format("IdolBoss_%s",curIdolName);
        }
        if(!curIdolName.equals("")){
            for(int i=0;i<tmpBossList.size();i++){
                if(tmpBossList.get(i).encounterID.equals(curIdolName)){
                    tmpBossList.remove(i);
                    break;
                }
            }
        }

        //使用Setting.seed作为随机数，打乱bosslist的顺序
        java.util.Random rng = new java.util.Random(Settings.seed);
        Collections.shuffle(tmpBossList, rng);

        // 8进4的3位胜者
        int battle1_1 = rng.nextInt(2);
        int battle1_2 = rng.nextInt(2)+2;
        int battle1_3 = rng.nextInt(2)+5;

        // 4进2的1位胜者
        int battle2_1 = rng.nextInt(2);
        if(battle2_1 == 0){
            battle2_1 = battle1_1;
        }else{
            battle2_1 = battle1_2;
        }
        bossKey = tmpBossList.get(battle2_1).encounterID;
        this.bossList.add(tmpBossList.get(battle2_1).encounterID);
        this.bossList.add(tmpBossList.get(battle2_1).encounterID);
        this.bossList.add(tmpBossList.get(battle2_1).encounterID);
//        this.tmpBossList.add(MisuzuBoss.ID);
//        this.tmpBossList.add(MisuzuBoss.ID);
//        this.tmpBossList.add(MisuzuBoss.ID);
    }


    public class MonsterRoomCreator {
        String image;

        String outline;

        String encounterID;

        public MonsterRoomCreator(String image, String encounterID) {
            this.image = image;
            this.outline = image;
            this.encounterID = encounterID;
        }

        public MonsterRoom get() {
            return new FixedMonsterRoom(this.encounterID, this.image, this.outline);
        }
    }
}
