package gkmasmod.relics;
import basemod.BaseMod;
import basemod.CustomEventRoom;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import basemod.eventUtil.EventUtils;
import basemod.interfaces.ISubscriber;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
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
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.powers.ModeShiftPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.relics.MealTicket;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import gkmasmod.actions.common.GainTrainRoundPowerAction;
import gkmasmod.actions.special.TrainRoundAnomalyFirstAction;
import gkmasmod.actions.special.TrainRoundProduceFirstAction;
import gkmasmod.cards.free.GakuenLinkMaster;
import gkmasmod.cards.free.ProducerTrumpCard;
import gkmasmod.cards.special.DoNotGo;
import gkmasmod.cards.special.InformationMaster;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.characters.OtherIdolCharacter;
import gkmasmod.downfall.bosses.AbstractIdolBoss;
import gkmasmod.dungeons.IdolRoad;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.monster.ending.MisuzuBoss;
import gkmasmod.monster.exordium.MonsterNadeshiko;
import gkmasmod.monster.exordium.MonsterShion;
import gkmasmod.monster.friend.FriendNunu;
import gkmasmod.patches.AbstractCardPatch;
import gkmasmod.patches.AbstractPlayerPatch;
import gkmasmod.patches.MapRoomNodePatch;
import gkmasmod.powers.*;
import gkmasmod.room.EventMonsterRoom;
import gkmasmod.room.FixedMonsterRoom;
import gkmasmod.room.selectBoss.SelectBossOption;
import gkmasmod.room.shop.AnotherShopOption;
import gkmasmod.room.shop.AnotherShopScreen;
import gkmasmod.room.specialTeach.SpecialTeachOption;
import gkmasmod.room.supply.SupplyOption;
import gkmasmod.screen.MusicSelectScreen;
import gkmasmod.screen.OtherSkinSelectScreen;
import gkmasmod.screen.PocketBookViewScreen;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class MusicPlayer extends CustomRelic{

    private static final String CLASSNAME = MusicPlayer.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png","NightStar");
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png","NightStar");
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png","NightStar");

    private static final RelicTier RARITY = RelicTier.STARTER;

    public static CommonEnum.IdolType type;

    private boolean RclickStart = false;



    public MusicPlayer() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
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
                if (AbstractDungeon.screen == MusicSelectScreen.Enum.MusicPlay_Screen){
                    AbstractDungeon.closeCurrentScreen();
                    AbstractDungeon.overlayMenu.cancelButton.hide();
                }
                else {
                    BaseMod.openCustomScreen(MusicSelectScreen.Enum.MusicPlay_Screen);
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
        return new MusicPlayer();
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }

}
