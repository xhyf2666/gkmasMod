package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cardGrowEffect.EnergyGrow;
import gkmasmod.cardGrowEffect.ExhaustRemoveGrow;
import gkmasmod.utils.CardHelper;
import gkmasmod.utils.GrowHelper;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.SoundHelper;

import java.util.ArrayList;

public class IdolPhone extends CustomRelic {

    private static final String CLASSNAME = IdolPhone.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static int playTimes = 1;

    private static final String AUDIO = "gkmasModResource/audio/voice/phone/phone_%s_%03d.ogg";

    private boolean RclickStart = false;

    private boolean thisBattle = false;

    public IdolPhone() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
        this.counter = 0;
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
            if (AbstractDungeon.actionManager.turnHasEnded &&
                    (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT){
                return;
            }
            if(AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT)
                return;
            if (this.hb.hovered) {
                this.counter++;
                if(!this.thisBattle){
                    ArrayList<AbstractCard> cards = CardHelper.getAllIdolCards();
                    AbstractCard card = cards.get(AbstractDungeon.cardRandomRng.random(cards.size() - 1));
                    GrowHelper.grow(card, ExhaustRemoveGrow.growID,1);
                    GrowHelper.grow(card, EnergyGrow.growID,-1);
                    addToBot(new MakeTempCardInHandAction(card, 1, true));
                    this.thisBattle = true;
                    playVoice(((GkmasCard)card).backGroundColor);
                }
                else{
                    playVoice();
                }
                CInputActionSet.select.unpress();
            }
            this.RclickStart = false;
        }
        if (this.hb != null && this.hb.hovered && InputHelper.justClickedRight)
            this.RclickStart = true;
    }

    private void playVoice(String idolName) {
        java.util.Random random = new java.util.Random();
        int index = random.nextInt(12)+1;
        SoundHelper.playSound(String.format(AUDIO,idolName,index));
    }

    private void playVoice(){
        java.util.Random random = new java.util.Random();
        String idolName = IdolData.idolNames[random.nextInt(IdolData.idolNames.length)];
        playVoice(idolName);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new IdolPhone();
    }


    public void onEquip() {}

    @Override
    public void atBattleStart() {
        this.thisBattle = false;
    }

    @Override
    public void atPreBattle() {
    }

    public  void  onPlayerEndTurn(){
    }

    public void onVictory() {
        this.thisBattle = false;
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }
}
