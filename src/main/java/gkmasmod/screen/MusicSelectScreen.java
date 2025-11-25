package gkmasmod.screen;

import basemod.abstracts.CustomScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.controller.CInputHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBar;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBarListener;
import com.megacrit.cardcrawl.ui.buttons.GridSelectConfirmButton;
import com.megacrit.cardcrawl.ui.buttons.PeekButton;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.music.AbstractMusicCard;
import gkmasmod.music.MusicGroup;

import java.util.ArrayList;

public class MusicSelectScreen extends CustomScreen implements ScrollBarListener {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("gkmasMod:SpecialTeachScreen");

    public static final String[] TEXT = uiStrings.TEXT;

    private static float drawStartX;

    private static float drawStartY;

    private static float padX;

    private static float padY;

    private static final float SCROLL_BAR_THRESHOLD = 500.0F * Settings.scale;

    private float grabStartY = 0.0F;

    private float currentDiffY = 0.0F;

    public ArrayList<AbstractMusicCard> selectedCards = new ArrayList<>();

    public MusicGroup targetGroup;

    private AbstractMusicCard hoveredCard = null;

    private int numCards = 0;

    private int cardSelectAmount = 0;

    private float scrollLowerBound = -Settings.DEFAULT_SCROLL_LIMIT;

    private float scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT/2;

    private boolean grabbedScreen = false;

    public boolean confirmScreenUp = false;

    public boolean isJustForConfirming = false;

    public GridSelectConfirmButton confirmButton = new GridSelectConfirmButton(TEXT[0]);

    public PeekButton peekButton = new PeekButton();

    private String tipMsg = "";

    private String lastTip = "";

    private int prevDeckSize = 0;

    public boolean cancelWasOn = false;

    public String cancelText;

    private ScrollBar scrollBar;

    private static Texture background = ImageMaster.loadImage("gkmasModResource/img/bg/musicBg.png");

    private AbstractCard controllerCard = null;

    private float arrowScale1 = 1.0F, arrowScale2 = 1.0F, arrowScale3 = 1.0F, arrowTimer = 0.0F;

    private Hitbox hb1;
    private Hitbox hb2;
    private Hitbox hb3;

    public boolean usedCustomEffect = false;

    private boolean[] isClick = new boolean[3];

    public MusicSelectScreen() {
        drawStartX = Settings.WIDTH;
        drawStartX -= 5.0F * AbstractCard.IMG_WIDTH * 0.75F;
        drawStartX -= 4.0F * Settings.CARD_VIEW_PAD_X;
        drawStartX /= 2.0F;
        drawStartX += AbstractCard.IMG_WIDTH * 0.75F / 2.0F;
        padX = 256 * 0.75F + Settings.CARD_VIEW_PAD_X;
        padY = 256 * 0.75F + Settings.CARD_VIEW_PAD_Y;
        this.scrollBar = new ScrollBar(this);
        this.scrollBar.move(0.0F, -30.0F * Settings.scale);
        this.hb1 = new Hitbox(300.0F * Settings.scale, 150.0F * Settings.scale);
        this.hb1.move(500.0F * Settings.scale, 200.0F * Settings.scale);
        this.hb2 = new Hitbox(300.0F * Settings.scale, 150.0F * Settings.scale);
        this.hb2.move(900.0F * Settings.scale, 200.0F * Settings.scale);
        this.hb3 = new Hitbox(300.0F * Settings.scale, 150.0F * Settings.scale);
        this.hb3.move(1300.0F * Settings.scale, 200.0F * Settings.scale);
    }

    public static class Enum
    {
        @SpireEnum
        public static AbstractDungeon.CurrentScreen MusicPlay_Screen;
    }

    @Override
    public AbstractDungeon.CurrentScreen curScreen() {
        return Enum.MusicPlay_Screen;
    }

    public void update() {
//        updateControllerInput();
//        updatePeekButton();
        this.hb1.update();
        this.hb2.update();
        this.hb3.update();
        if (PeekButton.isPeeking)
            return;
        if (Settings.isControllerMode && this.controllerCard != null && !CardCrawlGame.isPopupOpen)
            if (Gdx.input.getY() > Settings.HEIGHT * 0.75F) {
                this.currentDiffY += Settings.SCROLL_SPEED;
            } else if (Gdx.input.getY() < Settings.HEIGHT * 0.25F) {
                this.currentDiffY -= Settings.SCROLL_SPEED;
            }
        boolean isDraggingScrollBar = false;
        if (shouldShowScrollBar())
            isDraggingScrollBar = this.scrollBar.update();
        if (!isDraggingScrollBar)
            updateScrolling();
        this.confirmButton.update();
        if (this.isJustForConfirming) {
            updateCardPositionsAndHoverLogic();
            if (this.confirmButton.hb.clicked || CInputActionSet.topPanel.isJustPressed()) {
                CInputActionSet.select.unpress();
                this.confirmButton.hb.clicked = false;
                AbstractDungeon.overlayMenu.cancelButton.hide();
                AbstractDungeon.dynamicBanner.hide();
                this.confirmScreenUp = false;
//                for (AbstractCard c : this.targetGroup.group)
//                    AbstractDungeon.topLevelEffects.add(new FastCardObtainEffect(c, c.current_x, c.current_y));
                AbstractDungeon.closeCurrentScreen();
            }
            return;
        }
        if (!this.confirmScreenUp) {
            updateCardPositionsAndHoverLogic();
            if (this.hoveredCard != null && InputHelper.justClickedLeft)
                this.hoveredCard.hb.clickStarted = true;
            if (this.hoveredCard != null && (this.hoveredCard.hb.clicked || CInputActionSet.select.isJustPressed())) {
                this.hoveredCard.hb.clicked = false;
                if (!this.selectedCards.contains(this.hoveredCard)) {
                    if (this.selectedCards.size() > 0) {
                        this.selectedCards.clear();
                        this.cardSelectAmount--;
                    }
                    this.selectedCards.add(this.hoveredCard);
                    this.hoveredCard.targetDrawScale = 0.75F;
                    this.hoveredCard.drawScale = 0.875F;
                    this.cardSelectAmount++;
                    CardCrawlGame.sound.play("CARD_SELECT");
                } else if (this.selectedCards.contains(this.hoveredCard)) {
                    this.selectedCards.remove(this.hoveredCard);
                    this.cardSelectAmount--;
                }
                return;
            }
        } else {

            if (this.confirmButton.hb.clicked || CInputActionSet.topPanel.isJustPressed()) {
                CInputActionSet.select.unpress();
                this.confirmButton.hb.clicked = false;
//                AbstractDungeon.overlayMenu.cancelButton.hide();
//                this.confirmScreenUp = false;
//                this.selectedCards.add(this.hoveredCard);
//                AbstractDungeon.closeCurrentScreen();
            }
        }
        if (InputHelper.justClickedLeft) {
            if (this.hb1.hovered) {
                this.hb1.clickStarted = true;
            }
            if (this.hb2.hovered) {
                this.hb2.clickStarted = true;
            }
            if (this.hb3.hovered) {
                this.hb3.clickStarted = true;
            }
        }
    }

    private void updatePeekButton() {
        this.peekButton.update();
    }

//    private void updateControllerInput() {
//        if (!Settings.isControllerMode || this.showCard != null)
//            return;
//        boolean anyHovered = false;
//        int index = 0;
//        for (AbstractCard c : this.targetGroup.group) {
//            if (c.hb.hovered) {
//                anyHovered = true;
//                break;
//            }
//            index++;
//        }
//        if (!anyHovered && this.controllerCard == null) {
//            CInputHelper.setCursor((this.targetGroup.group.get(0)).hb);
//            this.controllerCard = this.targetGroup.group.get(0);
//        } else if ((CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed()) && this.targetGroup
//                .size() > 5) {
//            if (index < 5) {
//                index = this.targetGroup.size() + 2 - 4 - index;
//                if (index > this.targetGroup.size() - 1)
//                    index -= 5;
//                if (index > this.targetGroup.size() - 1 || index < 0)
//                    index = 0;
//            } else {
//                index -= 5;
//            }
//            CInputHelper.setCursor((this.targetGroup.group.get(index)).hb);
//            this.controllerCard = this.targetGroup.group.get(index);
//        } else if ((CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed()) && this.targetGroup
//                .size() > 5) {
//            if (index < this.targetGroup.size() - 5) {
//                index += 5;
//            } else {
//                index %= 5;
//            }
//            CInputHelper.setCursor((this.targetGroup.group.get(index)).hb);
//            this.controllerCard = this.targetGroup.group.get(index);
//        } else if (CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed()) {
//            if (index % 5 > 0) {
//                index--;
//            } else {
//                index += 4;
//                if (index > this.targetGroup.size() - 1)
//                    index = this.targetGroup.size() - 1;
//            }
//            CInputHelper.setCursor((this.targetGroup.group.get(index)).hb);
//            this.controllerCard = this.targetGroup.group.get(index);
//        } else if (CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed()) {
//            if (index % 5 < 4) {
//                index++;
//                if (index > this.targetGroup.size() - 1)
//                    index -= this.targetGroup.size() % 5;
//            } else {
//                index -= 4;
//                if (index < 0)
//                    index = 0;
//            }
//            if (index > this.targetGroup.group.size() - 1)
//                index = 0;
//            CInputHelper.setCursor((this.targetGroup.group.get(index)).hb);
//            this.controllerCard = this.targetGroup.group.get(index);
//        }
//    }

    private void updateCardPositionsAndHoverLogic() {
        int lineNum = 0;
        ArrayList<AbstractMusicCard> cards = this.targetGroup.group;
        for (int i = 0; i < cards.size(); i++) {
            int mod = i % 5;
            if (mod == 0 && i != 0)
                lineNum++;
            (cards.get(i)).target_x = drawStartX + mod * padX;
            (cards.get(i)).target_y = drawStartY + this.currentDiffY - lineNum * padY;
            (cards.get(i)).current_x = drawStartX + mod * padX;
            (cards.get(i)).current_y = drawStartY + this.currentDiffY - lineNum * padY;
            (cards.get(i)).update();
            (cards.get(i)).updateHoverLogic();
            this.hoveredCard = null;
            for (AbstractMusicCard c : cards) {
                if (c.hb.hovered)
                    this.hoveredCard = c;
            }
        }
    }

    public void open() {
        this.targetGroup = new MusicGroup();
        AbstractDungeon.overlayMenu.showBlackScreen(0.75F);
        reopen();
        this.tipMsg = tipMsg;
        this.numCards = numCards;
        AbstractDungeon.overlayMenu.cancelButton.show(TEXT[1]);
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
            this.peekButton.hideInstantly();
            this.peekButton.show();
        }
        calculateScrollBounds();

        this.confirmButton.hideInstantly();
        this.confirmButton.show();
        this.confirmButton.updateText(TEXT[0]);
    }

    private void callOnOpen() {
    }

    @Override
    public void reopen() {
        if (Settings.isControllerMode) {
            Gdx.input.setCursorPosition(10, Settings.HEIGHT / 2);
            this.controllerCard = null;
        }
        this.usedCustomEffect = false;
        this.confirmScreenUp = false;
        this.isJustForConfirming = false;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        this.controllerCard = null;
        this.hoveredCard = null;
        this.selectedCards.clear();
        AbstractDungeon.topPanel.unhoverHitboxes();
        this.cardSelectAmount = 0;
        this.currentDiffY = 0.0F;
        this.grabStartY = 0.0F;
        this.grabbedScreen = false;
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = curScreen();
//        GkmasMod.screenIndex = 2;
//        AbstractDungeon.overlayMenu.showBlackScreen(0.75F);
        this.confirmButton.hideInstantly();
        this.peekButton.hideInstantly();
        if (this.targetGroup.group.size() <= 5) {
            drawStartY = Settings.HEIGHT * 0.5F;
        } else {
            drawStartY = Settings.HEIGHT * 0.66F;
        }
        AbstractDungeon.topPanel.unhoverHitboxes();
        if (this.cancelWasOn && !this.isJustForConfirming)
            AbstractDungeon.overlayMenu.cancelButton.show(this.cancelText);
        for (AbstractMusicCard c : this.targetGroup.group) {
            c.targetDrawScale = 0.75F;
            c.drawScale = 0.75F;
//            c.lighten(false);
        }
        this.scrollBar.reset();
    }

    @Override
    public void close() {
//        this.isOpen = false;
        if (AbstractDungeon.previousScreen == null || AbstractDungeon.previousScreen == AbstractDungeon.CurrentScreen.NONE) {
            AbstractDungeon.isScreenUp = false;
        } else {
            AbstractDungeon.screen = AbstractDungeon.previousScreen;
        }
    }

    public void hide() {
        if (!AbstractDungeon.overlayMenu.cancelButton.isHidden) {
            this.cancelWasOn = true;
            this.cancelText = AbstractDungeon.overlayMenu.cancelButton.buttonText;
        }
    }

    private void updateScrolling() {
        if (PeekButton.isPeeking)
            return;
        if (this.isJustForConfirming && this.targetGroup.size() <= 5) {
            this.currentDiffY = -64.0F * Settings.scale;
            return;
        }
        int y = InputHelper.mY;
        boolean isDraggingScrollBar = this.scrollBar.update();
        if (!isDraggingScrollBar)
            if (!this.grabbedScreen) {
                if (InputHelper.scrolledDown) {
                    this.currentDiffY += Settings.SCROLL_SPEED;
                } else if (InputHelper.scrolledUp) {
                    this.currentDiffY -= Settings.SCROLL_SPEED;
                }
                if (InputHelper.justClickedLeft) {
                    this.grabbedScreen = true;
                    this.grabStartY = y - this.currentDiffY;
                }
            } else if (InputHelper.isMouseDown) {
                this.currentDiffY = y - this.grabStartY;
            } else {
                this.grabbedScreen = false;
            }
        if (this.prevDeckSize != this.targetGroup.size())
            calculateScrollBounds();
        resetScrolling();
        updateBarPosition();
    }

    private void calculateScrollBounds() {
        int scrollTmp = 0;
        if (this.targetGroup.size() > 10) {
            scrollTmp = this.targetGroup.size() / 5 - 2;
            if (this.targetGroup.size() % 5 != 0)
                scrollTmp++;
            scrollTmp-=2;
            this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT + scrollTmp * padY;
        } else {
            this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
        }
        this.prevDeckSize = this.targetGroup.size();
    }

    private void resetScrolling() {
        if (this.currentDiffY < this.scrollLowerBound) {
            this.currentDiffY = MathHelper.scrollSnapLerpSpeed(this.currentDiffY, this.scrollLowerBound);
        } else if (this.currentDiffY > this.scrollUpperBound) {
            this.currentDiffY = MathHelper.scrollSnapLerpSpeed(this.currentDiffY, this.scrollUpperBound);
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(this.background, 0.0F, 0.0F);
        this.targetGroup.render(sb);
        if (shouldShowScrollBar())
            this.scrollBar.render(sb);
        if (this.confirmScreenUp) {
            sb.setColor(new Color(0.0F, 0.0F, 0.0F, 0.6F));
//            sb.draw(this.background, 0.0F, 0.0F);
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT - 64.0F * Settings.scale);
            sb.setColor(new Color(0.0F, 0.0F, 0.0F, 0.8F));
            renderArrows(sb);
            this.hoveredCard.current_x = Settings.WIDTH * 0.36F;
            this.hoveredCard.current_y = Settings.HEIGHT / 2.0F;
            this.hoveredCard.target_x = Settings.WIDTH * 0.36F;
            this.hoveredCard.target_y = Settings.HEIGHT / 2.0F;
            this.hoveredCard.render(sb);
            this.hoveredCard.updateHoverLogic();
        }
        if (!PeekButton.isPeeking)
            this.confirmButton.render(sb);
//        this.peekButton.render(sb);
    }

    @Override
    public void openingSettings() {
        AbstractDungeon.previousScreen = curScreen();
    }

    @Override
    public void openingMap() {
        AbstractDungeon.previousScreen = curScreen();
    }

    @Override
    public void openingDeck() {
        AbstractDungeon.previousScreen = curScreen();
    }

    @Override
    public boolean allowOpenDeck() {
        return true;
    }

    @Override
    public boolean allowOpenMap() {
        return true;
    }

    private void renderArrows(SpriteBatch sb) {
        float x = Settings.WIDTH / 2.0F - 73.0F * Settings.scale - 32.0F;
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.UPGRADE_ARROW, x, Settings.HEIGHT / 2.0F - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.arrowScale1 * Settings.scale, this.arrowScale1 * Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        x += 64.0F * Settings.scale;
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.UPGRADE_ARROW, x, Settings.HEIGHT / 2.0F - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.arrowScale2 * Settings.scale, this.arrowScale2 * Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        x += 64.0F * Settings.scale;
        sb.draw(ImageMaster.UPGRADE_ARROW, x, Settings.HEIGHT / 2.0F - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.arrowScale3 * Settings.scale, this.arrowScale3 * Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        this.arrowTimer += Gdx.graphics.getDeltaTime() * 2.0F;
        this.arrowScale1 = 0.8F + (MathUtils.cos(this.arrowTimer) + 1.0F) / 8.0F;
        this.arrowScale2 = 0.8F + (MathUtils.cos(this.arrowTimer - 0.8F) + 1.0F) / 8.0F;
        this.arrowScale3 = 0.8F + (MathUtils.cos(this.arrowTimer - 1.6F) + 1.0F) / 8.0F;
    }

    public void scrolledUsingBar(float newPercent) {
        this.currentDiffY = MathHelper.valueFromPercentBetween(this.scrollLowerBound, this.scrollUpperBound, newPercent);
        updateBarPosition();
    }

    private void updateBarPosition() {
        float percent = MathHelper.percentFromValueBetween(this.scrollLowerBound, this.scrollUpperBound, this.currentDiffY);
        this.scrollBar.parentScrolledToPercent(percent);
    }

    private boolean shouldShowScrollBar() {
        return (!this.confirmScreenUp && this.scrollUpperBound > SCROLL_BAR_THRESHOLD && !PeekButton.isPeeking);
    }
}
