package gkmasmod.room.specialTeach;

import basemod.abstracts.AbstractCardModifier;
import basemod.abstracts.CustomScreen;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.controller.CInputHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBar;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBarListener;
import com.megacrit.cardcrawl.ui.buttons.GridSelectConfirmButton;
import com.megacrit.cardcrawl.ui.buttons.PeekButton;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import gkmasmod.cardCustomEffect.CustomTimeCount;
import gkmasmod.cardCustomEffect.DamageCustom;
import gkmasmod.cards.CustomEffect;
import gkmasmod.cards.GkmasCard;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.utils.CustomHelper;

import java.util.ArrayList;

public class SpecialTeachScreen extends CustomScreen implements ScrollBarListener {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("GridCardSelectScreen");

    public static final String[] TEXT = uiStrings.TEXT;

    private static float drawStartX;

    private static float drawStartY;

    private static float padX;

    private static float padY;

    private static final int CARDS_PER_LINE = 5;

    private static final float SCROLL_BAR_THRESHOLD = 500.0F * Settings.scale;

    private float grabStartY = 0.0F;

    private float currentDiffY = 0.0F;

    public ArrayList<AbstractCard> selectedCards = new ArrayList<>();

    public CardGroup targetGroup;

    private AbstractCard hoveredCard = null;

    public AbstractCard selectCard = null;

    private int numCards = 0;

    private int cardSelectAmount = 0;

    private float scrollLowerBound = -Settings.DEFAULT_SCROLL_LIMIT;

    private float scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;

    private boolean grabbedScreen = false;

    public boolean confirmScreenUp = false;

    public boolean isJustForConfirming = false;

    public GridSelectConfirmButton confirmButton = new GridSelectConfirmButton(TEXT[0]);

    public PeekButton peekButton = new PeekButton();

    private String tipMsg = "";

    private String lastTip = "";

    private float ritualAnimTimer = 0.0F;

    private static final float RITUAL_ANIM_INTERVAL = 0.1F;

    private int prevDeckSize = 0;

    public boolean cancelWasOn = false;

    public String cancelText;

    private ScrollBar scrollBar;

    private AbstractCard controllerCard = null;

    private static final int ARROW_W = 64;

    private float arrowScale1 = 1.0F, arrowScale2 = 1.0F, arrowScale3 = 1.0F, arrowTimer = 0.0F;

    private ArrayList<Integer> customCount;
    private ArrayList<Integer> customLimit;
    private ArrayList<ArrayList<Integer>> customPrice;
    private ArrayList<ArrayList<AbstractCardModifier>> customModifier;
    private ArrayList<String> customDescription;
    private int customEffectLength;
    private int currentEffectIndex;

    private Hitbox hb1;
    private Hitbox hb2;
    private Hitbox hb3;

    private boolean usedCustomEffect = false;

    public SpecialTeachScreen() {
        drawStartX = Settings.WIDTH;
        drawStartX -= 5.0F * AbstractCard.IMG_WIDTH * 0.75F;
        drawStartX -= 4.0F * Settings.CARD_VIEW_PAD_X;
        drawStartX /= 2.0F;
        drawStartX += AbstractCard.IMG_WIDTH * 0.75F / 2.0F;
        padX = AbstractCard.IMG_WIDTH * 0.75F + Settings.CARD_VIEW_PAD_X;
        padY = AbstractCard.IMG_HEIGHT * 0.75F + Settings.CARD_VIEW_PAD_Y;
        this.scrollBar = new ScrollBar(this);
        this.scrollBar.move(0.0F, -30.0F * Settings.scale);
        this.hb1 = new Hitbox(100.0F * Settings.scale, 70.0F * Settings.scale);
        this.hb1.move(200.0F * Settings.scale, 100.0F * Settings.scale);
        this.hb2 = new Hitbox(100.0F * Settings.scale, 70.0F * Settings.scale);
        this.hb2.move(500.0F * Settings.scale, 100.0F * Settings.scale);
        this.hb3 = new Hitbox(100.0F * Settings.scale, 70.0F * Settings.scale);
        this.hb3.move(800.0F * Settings.scale, 100.0F * Settings.scale);
    }

    public static class Enum
    {
        @SpireEnum
        public static AbstractDungeon.CurrentScreen SpecialTeach_Screen;
    }

    @Override
    public AbstractDungeon.CurrentScreen curScreen() {
        return Enum.SpecialTeach_Screen;
    }

    public void update() {
        updateControllerInput();
        updatePeekButton();
        if (PeekButton.isPeeking)
            return;
        if (Settings.isControllerMode && this.controllerCard != null && !CardCrawlGame.isPopupOpen && this.selectCard == null)
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
                for (AbstractCard c : this.targetGroup.group)
                    AbstractDungeon.topLevelEffects.add(new FastCardObtainEffect(c, c.current_x, c.current_y));
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
                        (this.selectedCards.get(0)).stopGlowing();
                        this.selectedCards.clear();
                        this.cardSelectAmount--;
                    }
                    this.selectedCards.add(this.hoveredCard);
                    this.hoveredCard.beginGlowing();
                    this.hoveredCard.targetDrawScale = 0.75F;
                    this.hoveredCard.drawScale = 0.875F;
                    this.cardSelectAmount++;
                    CardCrawlGame.sound.play("CARD_SELECT");
                    if (this.numCards == this.cardSelectAmount) {
                        this.hoveredCard.untip();
                        this.confirmScreenUp = true;
                        this.selectCard = this.hoveredCard.makeStatEquivalentCopy();
                        if(this.selectCard instanceof GkmasCard){
                            GkmasCard card = (GkmasCard)this.selectCard;
                            this.customEffectLength=card.customEffectList.size();
                            ArrayList<Integer> count = new ArrayList<>();
                            ArrayList<Integer> limit = new ArrayList<>();
                            ArrayList<ArrayList<Integer>> price = new ArrayList<>();
                            ArrayList<ArrayList<AbstractCardModifier>> modifier = new ArrayList<>();
                            ArrayList<String> description = new ArrayList<>();
                            for (ArrayList<CustomEffect> list : card.customEffectList) {
                                ArrayList<Integer> tmpPrice = new ArrayList<>();
                                ArrayList<String> desc = new ArrayList<>();
                                ArrayList<AbstractCardModifier> tmpModifier = new ArrayList<>();
                                for (CustomEffect effect : list) {
                                    tmpPrice.add(effect.getPrice());
                                    desc.add(effect.getDescription());
                                    tmpModifier.add(CustomHelper.getCardModifierByID(effect.getType(),effect.getAmount()));
                                }
                                price.add(tmpPrice);
                                limit.add(list.size());
                                description.add(desc.get(0));
                                modifier.add(tmpModifier);
                            }
                            this.customCount = (ArrayList<Integer>) count.clone();
                            for(AbstractCardModifier mod: CardModifierManager.modifiers(card)){
                                if (mod instanceof CustomTimeCount){
                                    CustomTimeCount tmp = (CustomTimeCount)mod;
                                    this.customCount = (ArrayList<Integer>) tmp.getCount().clone();
                                }
                            }
                            this.customLimit = (ArrayList<Integer>) limit.clone();
                            this.customPrice = (ArrayList<ArrayList<Integer>>) price.clone();
                            this.customModifier = (ArrayList<ArrayList<AbstractCardModifier>>) modifier.clone();
                            this.customDescription = (ArrayList<String>) description.clone();
                        }
                        this.selectCard.drawScale = 0.875F;
                        this.hoveredCard.stopGlowing();
                        this.selectedCards.clear();
                        AbstractDungeon.overlayMenu.cancelButton.show(TEXT[1]);
                        this.confirmButton.show();
                        this.confirmButton.isDisabled = false;
                        GkmasMod.screenIndex = 3;
                        this.lastTip = this.tipMsg;
                        this.tipMsg = TEXT[2];
                        return;
//                        if (this.cardSelectAmount < this.targetGroup.size()) {
//                            AbstractDungeon.closeCurrentScreen();
//                            if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.SHOP) {
//                                AbstractDungeon.overlayMenu.cancelButton.hide();
//                            } else {
//                                AbstractDungeon.overlayMenu.cancelButton.show(TEXT[3]);
//                            }
//                            for (AbstractCard c : this.selectedCards)
//                                c.stopGlowing();
//                            if (this.targetGroup.type == CardGroup.CardGroupType.DISCARD_PILE)
//                                for (AbstractCard c : this.targetGroup.group) {
//                                    c.drawScale = 0.12F;
//                                    c.targetDrawScale = 0.12F;
//                                    c.teleportToDiscardPile();
//                                    c.lighten(true);
//                                }
//                        }
                    }
                } else if (this.selectedCards.contains(this.hoveredCard)) {
                    this.hoveredCard.stopGlowing();
                    this.selectedCards.remove(this.hoveredCard);
                    this.cardSelectAmount--;
                }
                return;
            }
        } else {

            if (true)
                this.selectCard.update();
            if (true) {
                this.selectCard.drawScale = 1.0F;
                this.hoveredCard.update();
                this.hoveredCard.drawScale = 1.0F;
            }
            if (this.confirmButton.hb.clicked || CInputActionSet.topPanel.isJustPressed()) {
                CInputActionSet.select.unpress();
                this.confirmButton.hb.clicked = false;
                AbstractDungeon.overlayMenu.cancelButton.hide();
                CustomHelper.custom(this.hoveredCard, DamageCustom.growID,20);
                this.confirmScreenUp = false;
                this.selectedCards.add(this.hoveredCard);
                AbstractDungeon.closeCurrentScreen();
            }
        }
        if (Settings.isControllerMode)
            if (this.selectCard != null) {
                CInputHelper.setCursor(this.selectCard.hb);
            } else if (this.controllerCard != null) {
                CInputHelper.setCursor(this.controllerCard.hb);
            }
    }

    private void updatePeekButton() {
        this.peekButton.update();
    }

    private void updateControllerInput() {
        if (!Settings.isControllerMode || this.selectCard != null)
            return;
        boolean anyHovered = false;
        int index = 0;
        for (AbstractCard c : this.targetGroup.group) {
            if (c.hb.hovered) {
                anyHovered = true;
                break;
            }
            index++;
        }
        if (!anyHovered && this.controllerCard == null) {
            CInputHelper.setCursor(((AbstractCard)this.targetGroup.group.get(0)).hb);
            this.controllerCard = this.targetGroup.group.get(0);
        } else if ((CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed()) && this.targetGroup
                .size() > 5) {
            if (index < 5) {
                index = this.targetGroup.size() + 2 - 4 - index;
                if (index > this.targetGroup.size() - 1)
                    index -= 5;
                if (index > this.targetGroup.size() - 1 || index < 0)
                    index = 0;
            } else {
                index -= 5;
            }
            CInputHelper.setCursor(((AbstractCard)this.targetGroup.group.get(index)).hb);
            this.controllerCard = this.targetGroup.group.get(index);
        } else if ((CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed()) && this.targetGroup
                .size() > 5) {
            if (index < this.targetGroup.size() - 5) {
                index += 5;
            } else {
                index %= 5;
            }
            CInputHelper.setCursor(((AbstractCard)this.targetGroup.group.get(index)).hb);
            this.controllerCard = this.targetGroup.group.get(index);
        } else if (CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed()) {
            if (index % 5 > 0) {
                index--;
            } else {
                index += 4;
                if (index > this.targetGroup.size() - 1)
                    index = this.targetGroup.size() - 1;
            }
            CInputHelper.setCursor(((AbstractCard)this.targetGroup.group.get(index)).hb);
            this.controllerCard = this.targetGroup.group.get(index);
        } else if (CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed()) {
            if (index % 5 < 4) {
                index++;
                if (index > this.targetGroup.size() - 1)
                    index -= this.targetGroup.size() % 5;
            } else {
                index -= 4;
                if (index < 0)
                    index = 0;
            }
            if (index > this.targetGroup.group.size() - 1)
                index = 0;
            CInputHelper.setCursor(((AbstractCard)this.targetGroup.group.get(index)).hb);
            this.controllerCard = this.targetGroup.group.get(index);
        }
    }

    private void updateCardPositionsAndHoverLogic() {
        if (this.isJustForConfirming && this.targetGroup.size() <= 4) {
            switch (this.targetGroup.size()) {
                case 1:
                    (this.targetGroup.getBottomCard()).current_x = Settings.WIDTH / 2.0F;
                    (this.targetGroup.getBottomCard()).target_x = Settings.WIDTH / 2.0F;
                    break;
                case 2:
                    ((AbstractCard)this.targetGroup.group.get(0)).current_x = Settings.WIDTH / 2.0F - padX / 2.0F;
                    ((AbstractCard)this.targetGroup.group.get(0)).target_x = Settings.WIDTH / 2.0F - padX / 2.0F;
                    ((AbstractCard)this.targetGroup.group.get(1)).current_x = Settings.WIDTH / 2.0F + padX / 2.0F;
                    ((AbstractCard)this.targetGroup.group.get(1)).target_x = Settings.WIDTH / 2.0F + padX / 2.0F;
                    break;
                case 3:
                    ((AbstractCard)this.targetGroup.group.get(0)).current_x = drawStartX + padX;
                    ((AbstractCard)this.targetGroup.group.get(1)).current_x = drawStartX + padX * 2.0F;
                    ((AbstractCard)this.targetGroup.group.get(2)).current_x = drawStartX + padX * 3.0F;
                    ((AbstractCard)this.targetGroup.group.get(0)).target_x = drawStartX + padX;
                    ((AbstractCard)this.targetGroup.group.get(1)).target_x = drawStartX + padX * 2.0F;
                    ((AbstractCard)this.targetGroup.group.get(2)).target_x = drawStartX + padX * 3.0F;
                    break;
                case 4:
                    ((AbstractCard)this.targetGroup.group.get(0)).current_x = Settings.WIDTH / 2.0F - padX / 2.0F - padX;
                    ((AbstractCard)this.targetGroup.group.get(0)).target_x = Settings.WIDTH / 2.0F - padX / 2.0F - padX;
                    ((AbstractCard)this.targetGroup.group.get(1)).current_x = Settings.WIDTH / 2.0F - padX / 2.0F;
                    ((AbstractCard)this.targetGroup.group.get(1)).target_x = Settings.WIDTH / 2.0F - padX / 2.0F;
                    ((AbstractCard)this.targetGroup.group.get(2)).current_x = Settings.WIDTH / 2.0F + padX / 2.0F;
                    ((AbstractCard)this.targetGroup.group.get(2)).target_x = Settings.WIDTH / 2.0F + padX / 2.0F;
                    ((AbstractCard)this.targetGroup.group.get(3)).current_x = Settings.WIDTH / 2.0F + padX / 2.0F + padX;
                    ((AbstractCard)this.targetGroup.group.get(3)).target_x = Settings.WIDTH / 2.0F + padX / 2.0F + padX;
                    break;
            }
            ArrayList<AbstractCard> c2 = this.targetGroup.group;
            for (int j = 0; j < c2.size(); j++) {
                ((AbstractCard)c2.get(j)).target_y = drawStartY + this.currentDiffY;
                ((AbstractCard)c2.get(j)).fadingOut = false;
                ((AbstractCard)c2.get(j)).update();
                ((AbstractCard)c2.get(j)).updateHoverLogic();
                this.hoveredCard = null;
                for (AbstractCard c : c2) {
                    if (c.hb.hovered)
                        this.hoveredCard = c;
                }
            }
            return;
        }
        int lineNum = 0;
        ArrayList<AbstractCard> cards = this.targetGroup.group;
        for (int i = 0; i < cards.size(); i++) {
            int mod = i % 5;
            if (mod == 0 && i != 0)
                lineNum++;
            ((AbstractCard)cards.get(i)).target_x = drawStartX + mod * padX;
            ((AbstractCard)cards.get(i)).target_y = drawStartY + this.currentDiffY - lineNum * padY;
            ((AbstractCard)cards.get(i)).fadingOut = false;
            ((AbstractCard)cards.get(i)).update();
            ((AbstractCard)cards.get(i)).updateHoverLogic();
            this.hoveredCard = null;
            for (AbstractCard c : cards) {
                if (c.hb.hovered)
                    this.hoveredCard = c;
            }
        }
    }

    public void open(CardGroup group, int numCards, String tipMsg) {
        this.targetGroup = group;
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

    public void openConfirmationGrid(CardGroup group, String tipMsg) {
        this.targetGroup = group;
        callOnOpen();
        this.isJustForConfirming = true;
        this.tipMsg = tipMsg;
        AbstractDungeon.overlayMenu.cancelButton.hideInstantly();
        this.confirmButton.show();
        this.confirmButton.updateText(TEXT[0]);
        this.confirmButton.isDisabled = false;
        if (group.size() <= 5)
            AbstractDungeon.dynamicBanner.appear(tipMsg);
    }

    private void callOnOpen() {
    }

    @Override
    public void reopen() {
        System.out.println("Reopen");
        if (Settings.isControllerMode) {
            Gdx.input.setCursorPosition(10, Settings.HEIGHT / 2);
            this.controllerCard = null;
        }
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
        hideCards();
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = curScreen();
        GkmasMod.screenIndex = 2;
        AbstractDungeon.overlayMenu.showBlackScreen(0.75F);
        this.confirmButton.hideInstantly();
        this.peekButton.hideInstantly();
        if (this.targetGroup.group.size() <= 5) {
            drawStartY = Settings.HEIGHT * 0.5F;
        } else {
            drawStartY = Settings.HEIGHT * 0.66F;
        }
//        AbstractDungeon.overlayMenu.showBlackScreen(0.75F);
//        AbstractDungeon.isScreenUp = true;
//        AbstractDungeon.screen = curScreen();
//        GkmasMod.screenIndex = 2;
        AbstractDungeon.topPanel.unhoverHitboxes();
        if (this.cancelWasOn && !this.isJustForConfirming)
            AbstractDungeon.overlayMenu.cancelButton.show(this.cancelText);
        for (AbstractCard c : this.targetGroup.group) {
            c.targetDrawScale = 0.75F;
            c.drawScale = 0.75F;
            c.lighten(false);
        }
        this.scrollBar.reset();
    }

    @Override
    public void close() {
        RestRoom r = (RestRoom)AbstractDungeon.getCurrRoom();
        AbstractDungeon.isScreenUp = false;
        GkmasMod.screenIndex = 0;
        AbstractDungeon.overlayMenu.hideBlackScreen();
        r.campfireUI.reopen();
        (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
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

    private void hideCards() {
        int lineNum = 0;
        ArrayList<AbstractCard> cards = this.targetGroup.group;
        for (int i = 0; i < cards.size(); i++) {
            ((AbstractCard)cards.get(i)).setAngle(0.0F, true);
            int mod = i % 5;
            if (mod == 0 && i != 0)
                lineNum++;
            ((AbstractCard)cards.get(i)).lighten(true);
            ((AbstractCard)cards.get(i)).current_x = drawStartX + mod * padX;
            ((AbstractCard)cards.get(i)).current_y = drawStartY + this.currentDiffY - lineNum * padY - MathUtils.random(100.0F * Settings.scale, 200.0F * Settings.scale);
            ((AbstractCard)cards.get(i)).targetDrawScale = 0.75F;
            ((AbstractCard)cards.get(i)).drawScale = 0.75F;
        }
    }

    public void cancelUpgrade() {
        GkmasMod.screenIndex = 2;
        this.cardSelectAmount = 0;
        this.confirmScreenUp = false;
        this.confirmButton.hide();
        this.confirmButton.isDisabled = true;
        this.hoveredCard = null;
        this.selectCard = null;
        if (Settings.isControllerMode && this.controllerCard != null) {
            this.hoveredCard = this.controllerCard;
            CInputHelper.setCursor(this.hoveredCard.hb);
        }
        AbstractDungeon.overlayMenu.cancelButton.show(TEXT[1]);
        int lineNum = 0;
        ArrayList<AbstractCard> cards = this.targetGroup.group;
        for (int i = 0; i < cards.size(); i++) {
            int mod = i % 5;
            if (mod == 0 && i != 0)
                lineNum++;
            ((AbstractCard)cards.get(i)).current_x = drawStartX + mod * padX;
            ((AbstractCard)cards.get(i)).current_y = drawStartY + this.currentDiffY - lineNum * padY;
        }
        this.tipMsg = this.lastTip;
    }

    public void renderCustomOption(SpriteBatch sb){
        sb.draw(ImageMaster.CHECKBOX, this.hb1.cX - 32.0F, this.hb1.cY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale * 0.9F, Settings.scale * 0.9F, 0.0F, 0, 0, 64, 64, false, false);
        sb.draw(ImageMaster.CHECKBOX, this.hb2.cX - 32.0F, this.hb2.cY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale * 0.9F, Settings.scale * 0.9F, 0.0F, 0, 0, 64, 64, false, false);
        sb.draw(ImageMaster.CHECKBOX, this.hb3.cX - 32.0F, this.hb3.cY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale * 0.9F, Settings.scale * 0.9F, 0.0F, 0, 0, 64, 64, false, false);

    }

    public void render(SpriteBatch sb) {
        if (shouldShowScrollBar())
            this.scrollBar.render(sb);
        if (!PeekButton.isPeeking)
            if (this.hoveredCard != null) {
                if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
                    this.targetGroup.renderExceptOneCard(sb, this.hoveredCard);
                } else {
                    this.targetGroup.renderExceptOneCardShowBottled(sb, this.hoveredCard);
                }
                this.hoveredCard.renderHoverShadow(sb);
                this.hoveredCard.render(sb);
                if ((AbstractDungeon.getCurrRoom()).phase != AbstractRoom.RoomPhase.COMBAT)
                    if (this.hoveredCard.inBottleFlame) {
                        AbstractRelic tmp = RelicLibrary.getRelic("Bottled Flame");
                        float prevX = tmp.currentX;
                        float prevY = tmp.currentY;
                        tmp.currentX = this.hoveredCard.current_x + 130.0F * Settings.scale;
                        tmp.currentY = this.hoveredCard.current_y + 182.0F * Settings.scale;
                        tmp.scale = this.hoveredCard.drawScale * Settings.scale * 1.5F;
                        tmp.render(sb);
                        tmp.currentX = prevX;
                        tmp.currentY = prevY;
                        tmp = null;
                    } else if (this.hoveredCard.inBottleLightning) {
                        AbstractRelic tmp = RelicLibrary.getRelic("Bottled Lightning");
                        float prevX = tmp.currentX;
                        float prevY = tmp.currentY;
                        tmp.currentX = this.hoveredCard.current_x + 130.0F * Settings.scale;
                        tmp.currentY = this.hoveredCard.current_y + 182.0F * Settings.scale;
                        tmp.scale = this.hoveredCard.drawScale * Settings.scale * 1.5F;
                        tmp.render(sb);
                        tmp.currentX = prevX;
                        tmp.currentY = prevY;
                        tmp = null;
                    } else if (this.hoveredCard.inBottleTornado) {
                        AbstractRelic tmp = RelicLibrary.getRelic("Bottled Tornado");
                        float prevX = tmp.currentX;
                        float prevY = tmp.currentY;
                        tmp.currentX = this.hoveredCard.current_x + 130.0F * Settings.scale;
                        tmp.currentY = this.hoveredCard.current_y + 182.0F * Settings.scale;
                        tmp.scale = this.hoveredCard.drawScale * Settings.scale * 1.5F;
                        tmp.render(sb);
                        tmp.currentX = prevX;
                        tmp.currentY = prevY;
                        tmp = null;
                    }
                this.hoveredCard.renderCardTip(sb);
            } else if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
                this.targetGroup.render(sb);
            } else {
                this.targetGroup.renderShowBottled(sb);
            }
        if (this.confirmScreenUp) {
            sb.setColor(new Color(0.0F, 0.0F, 0.0F, 0.8F));
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT - 64.0F * Settings.scale);
            renderArrows(sb);
            this.hoveredCard.current_x = Settings.WIDTH * 0.36F;
            this.hoveredCard.current_y = Settings.HEIGHT / 2.0F;
            this.hoveredCard.target_x = Settings.WIDTH * 0.36F;
            this.hoveredCard.target_y = Settings.HEIGHT / 2.0F;
            this.hoveredCard.render(sb);
            this.hoveredCard.updateHoverLogic();
            this.hoveredCard.renderCardTip(sb);
            this.selectCard.current_x = Settings.WIDTH * 0.63F;
            this.selectCard.current_y = Settings.HEIGHT / 2.0F;
            this.selectCard.target_x = Settings.WIDTH * 0.63F;
            this.selectCard.target_y = Settings.HEIGHT / 2.0F;
            this.selectCard.render(sb);
            this.selectCard.updateHoverLogic();
            this.selectCard.renderCardTip(sb);
            this.renderCustomOption(sb);
        }
        if (!PeekButton.isPeeking)
            this.confirmButton.render(sb);
        this.peekButton.render(sb);
        if ((!this.isJustForConfirming || this.targetGroup.size() > 5) && !PeekButton.isPeeking)
            FontHelper.renderDeckViewTip(sb, this.tipMsg, 96.0F * Settings.scale, Settings.CREAM_COLOR);
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
