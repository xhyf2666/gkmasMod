package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.powers.HalfDamageReceive;
import gkmasmod.room.EventMonsterRoom;
import gkmasmod.room.FixedMonsterRoom;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.PlayerHelper;
import gkmasmod.utils.RestartHelper;
import gkmasmod.utils.SoundHelper;

import java.io.IOException;
import java.util.Properties;

public class ReChallenge extends CustomRelic implements CustomSavable<Integer>{

    private static final String CLASSNAME = ReChallenge.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.SPECIAL;

    private static int playTimes = 1;

    private boolean RclickStart = false;

    private SaveFile saveFile;

    public ReChallenge() {
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
            if(AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT)
                return;
            if((!(AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite))&&(!(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss))&&(!(AbstractDungeon.getCurrRoom() instanceof FixedMonsterRoom))&&(!(AbstractDungeon.getCurrRoom() instanceof EventMonsterRoom)))
                return;
            if (this.hb.hovered) {
                if(this.counter>0){
                    SpireConfig config = null;
                    try {
                        config = new SpireConfig("GkmasMod", "config");
                        // 读取配置
                        config.setFloat("cardRate", PlayerHelper.getCardRate());
                        config.setInt("beat_hmsz",GkmasMod.beat_hmsz);
                        config.setInt("ReChallenge",this.counter);
                        config.save();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    GkmasMod.restart=true;
                    this.counter--;
                }
                CInputActionSet.select.unpress();
            }
            this.RclickStart = false;
        }
        if (this.hb != null && this.hb.hovered && InputHelper.justClickedRight)
            this.RclickStart = true;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0]);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ReChallenge();
    }


    public void onEquip() {
        this.counter = 1;
        SpireConfig config = null;
        try {
            config = new SpireConfig("GkmasMod", "config");
            // 读取配置
            config.setFloat("cardRate", PlayerHelper.getCardRate());
            config.setInt("beat_hmsz",GkmasMod.beat_hmsz);
            config.setInt("ReChallenge",this.counter);
            config.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public  void  onPlayerEndTurn(){
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }

    @Override
    public void onVictory() {
        SpireConfig config = null;
        try {
            config = new SpireConfig("GkmasMod", "config");
            // 读取配置
            config.setFloat("cardRate", PlayerHelper.getCardRate());
            config.setInt("beat_hmsz",GkmasMod.beat_hmsz);
            config.setInt("ReChallenge",0);
            config.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer onSave() {
        return this.counter;
    }

    @Override
    public void onLoad(Integer integer) {
        this.counter = integer;
        // 设置默认值
        Properties defaults = new Properties();
        defaults.setProperty("cardRate", String.valueOf(PlayerHelper.getCardRate()));
        defaults.setProperty("beat_hmsz", "0");
        defaults.setProperty("ReChallenge", "1");
        SpireConfig config = null;
        try {
            config = new SpireConfig("GkmasMod", "config", defaults);
            int tmp = config.getInt("ReChallenge");
            if(tmp>0)
                this.counter = config.getInt("ReChallenge")-1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
