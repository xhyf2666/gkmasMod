package gkmasmod.event;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import gkmasmod.cards.free.HighSpirits;
import gkmasmod.cards.free.SpecialHuHu;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.characters.OtherIdolCharacter;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.relics.*;
import gkmasmod.room.shop.AnotherShopPotions;
import gkmasmod.screen.OtherSkinSelectScreen;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.CommonEnum;
import gkmasmod.utils.IdolData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class IdolMakeFood extends AbstractImageEvent {
    public static final String ID = IdolMakeFood.class.getSimpleName();
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;
    private static final int HP_LOST = 6;
    private static final int MONEY = 90;
    private int screenNum = 0;
    public CommonEnum.IdolType type;

    private AbstractCard card=null;
    private AbstractPotion potion=null;

    private int[] selects = {0,0,0};

    private static ArrayList<AbstractCard.CardColor> cardColors = new ArrayList(){
        {
            add(PlayerColorEnum.gkmasModColorSense);
            add(PlayerColorEnum.gkmasModColorLogic);
            add(PlayerColorEnum.gkmasModColorAnomaly);
        }
    };

    @Override
    public void onEnterRoom() {
        CardCrawlGame.music.playTempBgmInstantly("gkmasModResource/audio/bgm/bgm_adv_comical_002.ogg",true);

    }

    private int colorIndex=0;

    public IdolMakeFood() {
        super(NAME, DESCRIPTIONS[0], String.format("gkmasModResource/img/event/%s.png",ID));
        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1]);
        this.imageEventText.setDialogOption(OPTIONS[2]);
        this.imageEventText.setDialogOption(OPTIONS[11]);
        type  = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getType(SkinSelectScreen.Inst.skinIndex);
        if(AbstractDungeon.player instanceof MisuzuCharacter){
            type = CommonEnum.IdolType.SENSE;
            colorIndex=0;
        }
        if(AbstractDungeon.player instanceof OtherIdolCharacter){
            type  = IdolData.getOtherIdol(OtherSkinSelectScreen.Inst.idolIndex).getType(OtherSkinSelectScreen.Inst.skinIndex);
            if(OtherSkinSelectScreen.Inst.idolName.equals(IdolData.prod)){
                type = CommonEnum.IdolType.SENSE;
            }
        }
        if(type==CommonEnum.IdolType.SENSE){
            colorIndex=0;
        }
        else if (type==CommonEnum.IdolType.LOGIC){
            colorIndex=1;
        }
        else if(type==CommonEnum.IdolType.ANOMALY){
            colorIndex=2;
        }
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum){
            case 0 :
                switch (i) {
                    case 0:
                    case 1:
                    case 2:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        this.imageEventText.setDialogOption(OPTIONS[4]);
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        selects[screenNum] = i;
                        screenNum++;
                        return;
                    case 3:
                        screenNum=3;
                        openMap();
                        return;
                }
                break;
            case 1:
                switch (i) {
                    case 0:
                    case 1:
                    case 2:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[6]);
                        this.imageEventText.setDialogOption(OPTIONS[7]);
                        this.imageEventText.setDialogOption(OPTIONS[8]);
                        selects[screenNum] = i;
                        screenNum++;
                        return;
                }
                break;
            case 2:
                switch (i) {
                    case 0:
                    case 1:
                    case 2:
                        this.imageEventText.clearAllDialogs();
                        selects[screenNum] = i;
                        generateReward();
                        screenNum++;
                        return;
                }
                break;
            case 3:
                switch (i) {
                    case 0:
                        if(potion!=null){
                            AbstractDungeon.player.obtainPotion(potion);
                            screenNum++;
                            break;
                        }
                        if(card!=null){
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card, (Settings.WIDTH / 2), (Settings.HEIGHT / 2)));
                            screenNum++;
                            break;
                        }
                }
                break;
        }
        openMap();
    }

    private void generateReward(){
        switch (selects[2]){
            case 0:
                //糊糊
                card = new SpecialHuHu();
                this.imageEventText.setDialogOption(String.format(OPTIONS[9],card.name),card);
                break;
            case 1:
                //药水
                potion = generatePotion();
                this.imageEventText.setDialogOption(String.format(OPTIONS[10],potion.name));
                break;
            case 2:
                //卡牌
                card = generateCard();
                this.imageEventText.setDialogOption(String.format(OPTIONS[9],card.name),card);
                break;
        }
    }

    private AbstractPotion generatePotion(){
        switch (selects[1]){
            case 0:
                switch (selects[0]){
                    case 0:
                        return AbstractDungeon.returnRandomPotion(AbstractPotion.PotionRarity.COMMON,false);
                    case 1:
                        return AbstractDungeon.returnRandomPotion(AbstractPotion.PotionRarity.UNCOMMON,false);
                    case 2:
                        return AbstractDungeon.returnRandomPotion(AbstractPotion.PotionRarity.RARE,false);
                }
            case 1:
            case 2:
                switch (selects[0]){
                    case 0:
                        return AnotherShopPotions.returnRandomPotion(AbstractPotion.PotionRarity.COMMON,false);
                    case 1:
                        return AnotherShopPotions.returnRandomPotion(AbstractPotion.PotionRarity.UNCOMMON,false);
                    case 2:
                        return AnotherShopPotions.returnRandomPotion(AbstractPotion.PotionRarity.RARE,false);
                }
        }
        return AbstractDungeon.returnRandomPotion();
    }

    private AbstractCard generateCard(){
        AbstractCard.CardColor color = PlayerColorEnum.gkmasModColorSense;
        AbstractCard.CardRarity rarity = AbstractCard.CardRarity.COMMON;
        switch (selects[0]){
            case 0:
                rarity = AbstractCard.CardRarity.COMMON;
                break;
            case 1:
                rarity = AbstractCard.CardRarity.UNCOMMON;
                break;
            case 2:
                rarity = AbstractCard.CardRarity.RARE;
                break;
        }
        switch (selects[1]){
            case 0:
                colorIndex-=1;
                if(colorIndex<0){
                    colorIndex+=cardColors.size();
                }
                color = cardColors.get(colorIndex);
                break;
            case 1:
                colorIndex+=1;
                if(colorIndex>=cardColors.size()){
                    colorIndex-=cardColors.size();
                }
                color = cardColors.get(colorIndex);
                break;
            case 2:
                color = AbstractCard.CardColor.BLUE;
                break;
        }
        return getRandomCard(color,rarity);
    }

    public AbstractCard getRandomCard(AbstractCard.CardColor color, AbstractCard.CardRarity rarity) {
        Iterator<Map.Entry<String, AbstractCard>> cardLib = CardLibrary.cards.entrySet().iterator();
        ArrayList<AbstractCard> tmpPool = new ArrayList<>();
        while (true) {
            if (!cardLib.hasNext())
                break;
            Map.Entry c = cardLib.next();
            AbstractCard card = (AbstractCard)c.getValue();
            if (card.color.equals(color) &&card.rarity==rarity&& card.rarity != AbstractCard.CardRarity.BASIC &&card.rarity != AbstractCard.CardRarity.SPECIAL && card.rarity != AbstractCard.CardRarity.CURSE) {
                tmpPool.add(card);
            }
        }
        return tmpPool.get(AbstractDungeon.cardRandomRng.random(tmpPool.size() - 1));
    }

}