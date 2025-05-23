package gkmasmod.room.shop;

import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AnimatedNpc;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.shop.Merchant;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.screen.PocketBookViewScreen;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.RankHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class AnotherMerchant{
    private static final CharacterStrings characterStrings;
    public static final String[] NAMES;
    public static final String[] TEXT;
    public static final String[] ENDING_TEXT;
    public Texture img;
    public static final float DRAW_X;
    public static final float DRAW_Y;
    public Hitbox hb;
    private ArrayList<AbstractCard> cards1;
    private ArrayList<AbstractCard> cards2;
    private ArrayList<String> idleMessages;
    private float speechTimer;
    private boolean saidWelcome;
    private static final float MIN_IDLE_MSG_TIME = 40.0F;
    private static final float MAX_IDLE_MSG_TIME = 60.0F;
    private static final float SPEECH_DURATION = 3.0F;
    private int shopScreen;
    protected float modX;
    protected float modY;

    public AnotherMerchant() {
        this(0.0F, 0.0F, 1);
    }

    public AnotherMerchant(float x, float y, int newShopScreen) {
        this.hb = new Hitbox(360.0F * Settings.scale, 300.0F * Settings.scale);
        this.cards1 = new ArrayList();
        this.cards2 = new ArrayList();
        this.idleMessages = new ArrayList();
        this.speechTimer = 1.5F;
        this.saidWelcome = false;
        this.shopScreen = 1;
        this.img = ImageMaster.loadImage("gkmasModResource/img/room/shop/Sensei.png");
        //this.img = new AnimatedNpc(DRAW_X + 256.0F * Settings.scale, AbstractDungeon.floorY + 30.0F * Settings.scale, "images/npcs/merchant/skeleton.atlas", "images/npcs/merchant/skeleton.json", "idle");

        AbstractCard c;
        for(c = AbstractDungeon.getCardFromPool(AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardType.ATTACK, true).makeCopy(); c.color == AbstractCard.CardColor.COLORLESS; c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.ATTACK, true).makeCopy()) {
        }

        this.cards1.add(c);

        for(c = AbstractDungeon.getCardFromPool(AbstractCard.CardRarity.RARE, AbstractCard.CardType.ATTACK, true).makeCopy(); Objects.equals(c.cardID, ((AbstractCard)this.cards1.get(this.cards1.size() - 1)).cardID) || c.color == AbstractCard.CardColor.COLORLESS; c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.ATTACK, true).makeCopy()) {
        }

        this.cards1.add(c);

        for(c = AbstractDungeon.getCardFromPool(AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardType.SKILL, true).makeCopy(); c.color == AbstractCard.CardColor.COLORLESS; c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.SKILL, true).makeCopy()) {
        }

        this.cards1.add(c);

        for(c = AbstractDungeon.getCardFromPool(AbstractCard.CardRarity.RARE, AbstractCard.CardType.SKILL, true).makeCopy(); Objects.equals(c.cardID, ((AbstractCard)this.cards1.get(this.cards1.size() - 1)).cardID) || c.color == AbstractCard.CardColor.COLORLESS; c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.SKILL, true).makeCopy()) {
        }

        this.cards1.add(c);

        for(c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.POWER, true).makeCopy(); c.color == AbstractCard.CardColor.COLORLESS; c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.POWER, true).makeCopy()) {
        }

        this.cards1.add(c);
        this.cards2.add(AbstractDungeon.getColorlessCardFromPool(AbstractCard.CardRarity.UNCOMMON).makeCopy());
        this.cards2.add(AbstractDungeon.getColorlessCardFromPool(AbstractCard.CardRarity.RARE).makeCopy());
        if (AbstractDungeon.id.equals("TheEnding")) {
            Collections.addAll(this.idleMessages, ENDING_TEXT);
        } else {
            Collections.addAll(this.idleMessages, TEXT);
        }

        this.speechTimer = 1.5F;
        this.modX = x;
        this.modY = y;
        this.hb.move(DRAW_X + (250.0F + x) * Settings.scale, DRAW_Y + (130.0F + y) * Settings.scale);
        this.shopScreen = newShopScreen;
        BaseMod.openCustomScreen(AnotherShopScreen.Enum.AnotherShop_Screen,this.cards1, this.cards2);

    }

    public void update() {
//        this.hb.update();
//        if ((this.hb.hovered && InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed()) && !AbstractDungeon.isScreenUp && !AbstractDungeon.isFadingOut && !AbstractDungeon.player.viewingRelics) {
//            AbstractDungeon.overlayMenu.proceedButton.setLabel(NAMES[0]);
//            this.saidWelcome = true;
//            this.hb.hovered = false;
//        }
//
//        this.speechTimer -= Gdx.graphics.getDeltaTime();
//        if (this.speechTimer < 0.0F && this.shopScreen == 1) {
//            String msg = this.idleMessages.get(MathUtils.random(0, this.idleMessages.size() - 1));
//            if (!this.saidWelcome) {
//                this.saidWelcome = true;
//                this.welcomeSfx();
//                msg = NAMES[1];
//            } else {
//                this.playMiscSfx();
//            }
//
////            if (MathUtils.randomBoolean()) {
////                AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX - 50.0F * Settings.xScale, this.hb.cY + 70.0F * Settings.yScale, 3.0F, msg, false));
////            } else {
////                AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX - 50.0F * Settings.xScale, this.hb.cY + 70.0F * Settings.yScale, 3.0F, msg, true));
////            }
//
//            this.speechTimer = MathUtils.random(40.0F, 60.0F);
//        }

    }

    private void welcomeSfx() {
        int roll = MathUtils.random(2);
        if (roll == 0) {
            CardCrawlGame.sound.play("VO_MERCHANT_3A");
        } else if (roll == 1) {
            CardCrawlGame.sound.play("VO_MERCHANT_3B");
        } else {
            CardCrawlGame.sound.play("VO_MERCHANT_3C");
        }

    }

    private void playMiscSfx() {
        int roll = MathUtils.random(5);
        if (roll == 0) {
            CardCrawlGame.sound.play("VO_MERCHANT_MA");
        } else if (roll == 1) {
            CardCrawlGame.sound.play("VO_MERCHANT_MB");
        } else if (roll == 2) {
            CardCrawlGame.sound.play("VO_MERCHANT_MC");
        } else if (roll == 3) {
            CardCrawlGame.sound.play("VO_MERCHANT_3A");
        } else if (roll == 4) {
            CardCrawlGame.sound.play("VO_MERCHANT_3B");
        } else {
            CardCrawlGame.sound.play("VO_MERCHANT_3C");
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.MERCHANT_RUG_IMG, DRAW_X + this.modX, DRAW_Y + this.modY, 512.0F * Settings.scale, 512.0F * Settings.scale);
        if (this.hb.hovered) {
            sb.setBlendFunction(770, 1);
            sb.setColor(Settings.HALF_TRANSPARENT_WHITE_COLOR);
            sb.draw(ImageMaster.MERCHANT_RUG_IMG, DRAW_X + this.modX, DRAW_Y + this.modY, 512.0F * Settings.scale, 512.0F * Settings.scale);
            sb.setBlendFunction(770, 771);
        }

        if(this.img != null) {
            sb.draw(this.img, DRAW_X + this.modX - 256.0F, DRAW_Y + this.modY - 256.0F, 256.0F, 256.0F, 512.0F, 512.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 512, false, false);
        }

        if (Settings.isControllerMode) {
            sb.setColor(Color.WHITE);
            sb.draw(CInputActionSet.select.getKeyImg(), DRAW_X - 32.0F + 150.0F * Settings.scale, DRAW_Y - 32.0F + 100.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }

        this.hb.render(sb);
    }


    static {
        characterStrings = CardCrawlGame.languagePack.getCharacterString("Sensei:Merchant");
        NAMES = characterStrings.NAMES;
        TEXT = characterStrings.TEXT;
        ENDING_TEXT = characterStrings.OPTIONS;
        DRAW_X = (float)Settings.WIDTH * 0.5F + 34.0F * Settings.xScale;
        DRAW_Y = AbstractDungeon.floorY - 109.0F * Settings.scale;
    }
}
