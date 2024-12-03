package gkmasmod.event;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import gkmasmod.cards.anomaly.BecomeIdol;
import gkmasmod.cards.anomaly.StepOnStage;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.potion.FirstStarSoup;
import gkmasmod.potion.FirstStarWater;
import gkmasmod.relics.*;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.CommonEnum;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.SoundHelper;

import java.util.ArrayList;

public class FirstStarHotSpring extends AbstractImageEvent {
    public static final String ID = FirstStarHotSpring.class.getSimpleName();
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;
    private int screenNum = 0;

    private int selectCount = 0;

    private int currentSelect = -1;

    private int HP_LOSS = 4;

    private ArrayList<Integer> selectList = new ArrayList<>();

    private ArrayList<AbstractRelic> lostRelic = new ArrayList<>();

    private ArrayList<AbstractCard> specialCards = new ArrayList<>();

    private int keepStage = 0;

    private int keepStep = 0;

    private int gold = 500;

    public FirstStarHotSpring() {
        super(NAME, DESCRIPTIONS[0], "gkmasModResource/img/event/FirstStarHotSpring.png");
        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1]);
        this.imageEventText.setDialogOption(OPTIONS[2]);
        this.imageEventText.setDialogOption(OPTIONS[3]);
        this.imageEventText.setDialogOption(OPTIONS[4]);
        this.imageEventText.setDialogOption(OPTIONS[5],true);
        this.lostRelic = generateLostRelic();
        HP_LOSS = AbstractDungeon.player.maxHealth/8;
        this.specialCards = new ArrayList<>();
        generateSpecialCards();
    }

    @Override
    protected void buttonEffect(int i) {
        if(currentSelect == -1){
            currentSelect = i;
            selectList.add(i);
            selectCount++;
            if(selectCount >= 3){
                this.imageEventText.updateBodyText(DESCRIPTIONS[9]);
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[14]);
                currentSelect = -1;
                openMap();
                return;
            }
            this.imageEventText.updateBodyText(DESCRIPTIONS[currentSelect+1]);
            this.imageEventText.clearAllDialogs();
            if(currentSelect==0||currentSelect==1){
                this.imageEventText.setDialogOption(OPTIONS[currentSelect+6]);
                return;
            }
            else if(currentSelect==2){
                this.imageEventText.setDialogOption(String.format(OPTIONS[11],HP_LOSS));
                this.imageEventText.setDialogOption(OPTIONS[12]);
                return;
            }
            else if(currentSelect==3){
                if(lostRelic.size()>=2){
                    this.imageEventText.setDialogOption(String.format(OPTIONS[9],lostRelic.get(0).name),lostRelic.get(0));
                    this.imageEventText.setDialogOption(String.format(OPTIONS[9],lostRelic.get(1).name),lostRelic.get(1));
                }
                else if(lostRelic.size()==1){
                    this.imageEventText.setDialogOption(String.format(OPTIONS[9],lostRelic.get(0).name),lostRelic.get(0));
                    this.imageEventText.setDialogOption(OPTIONS[14]);
                }
                else{
                    this.imageEventText.setDialogOption(OPTIONS[14]);
                }
                return;
            }
            else if(currentSelect==4){
                this.imageEventText.setDialogOption(String.format(OPTIONS[10],specialCards.get(0).name),specialCards.get(0));
                this.imageEventText.setDialogOption(String.format(OPTIONS[10],specialCards.get(1).name),specialCards.get(1));
                return;
            }
        }
        else if(currentSelect==0){
            AbstractDungeon.effectList.add(new RainingGoldEffect(this.gold));
            AbstractDungeon.player.gainGold(this.gold);
            reStart();
            return;
        }
        else if(currentSelect==1){
            AbstractDungeon.player.maxHealth = (int) (AbstractDungeon.player.maxHealth*1.5F);
            AbstractDungeon.player.heal((int) (AbstractDungeon.player.maxHealth*0.33F));
            reStart();
            return;
        }
        else if(currentSelect==2){
//            if(keepStage==1){
//                if(keepStep >1){
//                    this.imageEventText.updateBodyText(DESCRIPTIONS[8]);
//                    this.imageEventText.setDialogOption(String.format(OPTIONS[8],2));
//                }
//                else{
//                    this.imageEventText.updateBodyText(DESCRIPTIONS[7]);
//                    this.imageEventText.setDialogOption(String.format(OPTIONS[8],1));
//                }
//                keepStage++;
//                return;
//            }
            if(keepStage==1){
                if(keepStep >1){
                    AbstractDungeon.player.obtainPotion(new FirstStarSoup());
                    AbstractDungeon.player.obtainPotion(new FirstStarSoup());
                }
                else{
                    AbstractDungeon.player.obtainPotion(new FirstStarSoup());
                }
                keepStage=0;
                keepStep=0;
                reStart();
                return;
            }
            if(i==0){
                if(keepStep==0){
                    AbstractDungeon.player.damage(new DamageInfo(null, HP_LOSS));
                    this.imageEventText.updateBodyText(DESCRIPTIONS[6]);
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption(String.format(OPTIONS[11],HP_LOSS));
                    this.imageEventText.setDialogOption(OPTIONS[12]);
                    keepStep++;
                    return;
                }
                else if (keepStep==1){
                    AbstractDungeon.player.damage(new DamageInfo(null, HP_LOSS));
                    this.imageEventText.updateBodyText(DESCRIPTIONS[6]);
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption(String.format(OPTIONS[11],HP_LOSS));
                    this.imageEventText.setDialogOption(OPTIONS[12]);
                    keepStep++;
                    return;
                }
            }
            else if(i==1){
                keepStage = 1;
                if(keepStep >1){
                    this.imageEventText.updateBodyText(DESCRIPTIONS[8]);
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption(String.format(OPTIONS[8],2));
                }
                else{
                    this.imageEventText.updateBodyText(DESCRIPTIONS[7]);
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption(String.format(OPTIONS[8],1));
                }
                return;
            }

        }
        else if(currentSelect==3){
            if(i==0){
                if(lostRelic.size()>0){
                    AbstractRelic.RelicTier tier = lostRelic.get(0).tier;
                    AbstractDungeon.player.loseRelic(lostRelic.get(0).relicId);
                    AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(tier);
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), r);
                    r = AbstractDungeon.returnRandomScreenlessRelic(tier);
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), r);
                }
            }
            else if(i==1){
                if(lostRelic.size()>1){
                    AbstractRelic.RelicTier tier = lostRelic.get(1).tier;
                    AbstractDungeon.player.loseRelic(lostRelic.get(1).relicId);
                    AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(tier);
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), r);
                    r = AbstractDungeon.returnRandomScreenlessRelic(tier);
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), r);
                }
            }
            reStart();
            return;
        }
        else if(currentSelect==4){
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(specialCards.get(i), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            reStart();
            return;
        }
    }

    private void reStart(){
        this.imageEventText.clearAllDialogs();
        if(selectCount >= 2){
            this.imageEventText.updateBodyText(DESCRIPTIONS[9]);
            this.imageEventText.setDialogOption(OPTIONS[14]);
        }
        else{
            this.imageEventText.updateBodyText(DESCRIPTIONS[0]);
            this.imageEventText.setDialogOption(OPTIONS[0],selectList.contains(0));
            this.imageEventText.setDialogOption(OPTIONS[1],selectList.contains(1));
            this.imageEventText.setDialogOption(OPTIONS[2],selectList.contains(2));
            this.imageEventText.setDialogOption(OPTIONS[3],selectList.contains(3));
            this.imageEventText.setDialogOption(OPTIONS[4],selectList.contains(4));
            this.imageEventText.setDialogOption(OPTIONS[5],true);
        }
        currentSelect = -1;
    }


    @Override
    public void onEnterRoom() {
        CardCrawlGame.music.playTempBgmInstantly("gkmasModResource/audio/bgm/bgm_adv_end_001.ogg",true);
    }

    public ArrayList<AbstractRelic> generateLostRelic() {
        ArrayList<AbstractRelic> tmp = new ArrayList<>();
        for (AbstractRelic relic :AbstractDungeon.player.relics){
            if(relic.tier != AbstractRelic.RelicTier.STARTER &&relic.tier!= AbstractRelic.RelicTier.SPECIAL){
                tmp.add(relic);
            }
        }
        //从tmp中随机挑选2个，要考虑到tmp的大小小于2的情况
        if(tmp.size()<=2){
            lostRelic.addAll(tmp);
        }else{
            int index1 = AbstractDungeon.eventRng.random(0,tmp.size()-1);
            int index2 = AbstractDungeon.eventRng.random(0,tmp.size()-1);
            while(index1 == index2){
                index2 = AbstractDungeon.eventRng.random(0,tmp.size()-1);
            }
            lostRelic.add(tmp.get(index1));
            lostRelic.add(tmp.get(index2));
        }
        return lostRelic;
    }

    public void generateSpecialCards(){
        if(SkinSelectScreen.Inst.idolName.equals(IdolData.jsna)){
            AbstractCard card1 = new BecomeIdol();
            AbstractCard card2 = new StepOnStage();
            card1.upgrade();
            card2.upgrade();
            specialCards.add(card1);
            specialCards.add(card2);
        }
        else{
            String s1 = IdolData.getIdol(SkinSelectScreen.Inst.idolName).getBossReward(0);
            String s2 = IdolData.getIdol(SkinSelectScreen.Inst.idolName).getBossReward(1);
            AbstractCard card1 = CardLibrary.getCard(s1);
            AbstractCard card2 = CardLibrary.getCard(s2);
            card1.upgrade();
            card2.upgrade();
            specialCards.add(card1);
            specialCards.add(card2);
        }
    }


}