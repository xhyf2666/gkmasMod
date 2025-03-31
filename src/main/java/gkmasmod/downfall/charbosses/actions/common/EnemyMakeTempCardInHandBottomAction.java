package gkmasmod.downfall.charbosses.actions.common;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import gkmasmod.downfall.charbosses.actions.vfx.cardmanip.EnemyShowCardAndAddToDiscardEffect;
import gkmasmod.downfall.charbosses.actions.vfx.cardmanip.EnemyShowCardAndAddToHandBottomEffect;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.cards.AbstractBossCard;

public class EnemyMakeTempCardInHandBottomAction extends AbstractGameAction {
    private static final float PADDING;

    static {
        PADDING = 25.0f * Settings.scale;
    }

    private final AbstractCard c;
    private boolean isOtherCardInCenter;
    private boolean sameUUID;

    public EnemyMakeTempCardInHandBottomAction(final AbstractCard card, final boolean isOtherCardInCenter) {
        this.isOtherCardInCenter = true;
        this.sameUUID = false;
        this.amount = 1;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.c = card;
        AbstractBossCard cB = (AbstractBossCard) c;
        cB.createIntent();
        if (this.c.type != AbstractCard.CardType.CURSE && this.c.type != AbstractCard.CardType.STATUS && AbstractCharBoss.boss.hasPower("MasterRealityPower")) {
            this.c.upgrade();
        }
        this.isOtherCardInCenter = isOtherCardInCenter;
    }

    public EnemyMakeTempCardInHandBottomAction(final AbstractCard card) {
        this(card, 1);
    }

    public EnemyMakeTempCardInHandBottomAction(final AbstractCard card, final int amount) {
        this.isOtherCardInCenter = true;
        this.sameUUID = false;
        UnlockTracker.markCardAsSeen(card.cardID);
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.c = card;
        if (this.c.type != AbstractCard.CardType.CURSE && this.c.type != AbstractCard.CardType.STATUS && AbstractCharBoss.boss.hasPower("MasterRealityPower")) {
            this.c.upgrade();
        }
    }

    public EnemyMakeTempCardInHandBottomAction(final AbstractCard card, final int amount, final boolean isOtherCardInCenter) {
        this(card, amount);
        this.isOtherCardInCenter = isOtherCardInCenter;
    }

    public EnemyMakeTempCardInHandBottomAction(final AbstractCard card, final boolean isOtherCardInCenter, final boolean sameUUID) {
        this(card, 1);
        this.isOtherCardInCenter = isOtherCardInCenter;
        this.sameUUID = sameUUID;
    }

    @Override
    public void update() {
        if (AbstractCharBoss.boss == null || AbstractCharBoss.boss.isDead || AbstractCharBoss.boss.isDying) {
            isDone = true;
            return;
        }
        if (this.amount == 0) {
            this.isDone = true;
            return;
        }
        int discardAmount = 0;
        int handAmount = this.amount;
        if (this.amount + AbstractCharBoss.boss.hand.size() > 10) {
            discardAmount = this.amount + AbstractCharBoss.boss.hand.size() - 10;
            handAmount -= discardAmount;
        }
        this.addToHand(handAmount);
        this.addToDiscard(discardAmount);
        if (this.amount > 0) {
            this.addToTop(new WaitAction(0.8f));
        }
        AbstractCharBoss.boss.preApplyIntentCalculations();
        //AbstractDungeon.actionManager.addToBottom(new CharbossSortHandAction());
        this.isDone = true;
    }

    private void addToHand(final int handAmt) {
        switch (this.amount) {
            case 0: {
                break;
            }
            case 1: {
                if (handAmt != 1) {
                    break;
                }
                if (this.isOtherCardInCenter) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToHandBottomEffect(this.makeNewCard(), Settings.WIDTH / 2.0f - (EnemyMakeTempCardInHandBottomAction.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0f));
                    break;
                }
                AbstractDungeon.effectList.add(new EnemyShowCardAndAddToHandBottomEffect(this.makeNewCard()));
                break;
            }
            case 2: {
                if (handAmt == 1) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToHandBottomEffect(this.makeNewCard(), Settings.WIDTH / 2.0f - (EnemyMakeTempCardInHandBottomAction.PADDING + AbstractCard.IMG_WIDTH * 0.5f), Settings.HEIGHT / 2.0f));
                    break;
                }
                if (handAmt == 2) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToHandBottomEffect(this.makeNewCard(), Settings.WIDTH / 2.0f + (EnemyMakeTempCardInHandBottomAction.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0f));
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToHandBottomEffect(this.makeNewCard(), Settings.WIDTH / 2.0f - (EnemyMakeTempCardInHandBottomAction.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0f));
                    break;
                }
                break;
            }
            case 3: {
                if (handAmt == 1) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToHandBottomEffect(this.makeNewCard(), Settings.WIDTH / 2.0f - (EnemyMakeTempCardInHandBottomAction.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0f));
                    break;
                }
                if (handAmt == 2) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToHandBottomEffect(this.makeNewCard(), Settings.WIDTH / 2.0f + (EnemyMakeTempCardInHandBottomAction.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0f));
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToHandBottomEffect(this.makeNewCard(), Settings.WIDTH / 2.0f - (EnemyMakeTempCardInHandBottomAction.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0f));
                    break;
                }
                if (handAmt == 3) {
                    for (int i = 0; i < this.amount; ++i) {
                        AbstractDungeon.effectList.add(new EnemyShowCardAndAddToHandBottomEffect(this.makeNewCard()));
                    }
                    break;
                }
                break;
            }
            default: {
                for (int i = 0; i < handAmt; ++i) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToHandBottomEffect(this.makeNewCard(), MathUtils.random(Settings.WIDTH * 0.2f, Settings.WIDTH * 0.8f), MathUtils.random(Settings.HEIGHT * 0.3f, Settings.HEIGHT * 0.7f)));
                }
                break;
            }
        }
    }

    private void addToDiscard(final int discardAmt) {
        switch (this.amount) {
            case 0: {
                break;
            }
            case 1: {
                if (discardAmt == 1) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH / 2.0f + (EnemyMakeTempCardInHandBottomAction.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0f));
                    break;
                }
                break;
            }
            case 2: {
                if (discardAmt == 1) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH * 0.5f - (EnemyMakeTempCardInHandBottomAction.PADDING + AbstractCard.IMG_WIDTH * 0.5f), Settings.HEIGHT * 0.5f));
                    break;
                }
                if (discardAmt == 2) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH * 0.5f - (EnemyMakeTempCardInHandBottomAction.PADDING + AbstractCard.IMG_WIDTH * 0.5f), Settings.HEIGHT * 0.5f));
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH * 0.5f + (EnemyMakeTempCardInHandBottomAction.PADDING + AbstractCard.IMG_WIDTH * 0.5f), Settings.HEIGHT * 0.5f));
                    break;
                }
                break;
            }
            case 3: {
                if (discardAmt == 1) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH * 0.5f + (EnemyMakeTempCardInHandBottomAction.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT * 0.5f));
                    break;
                }
                if (discardAmt == 2) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH * 0.5f, Settings.HEIGHT * 0.5f));
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH * 0.5f + (EnemyMakeTempCardInHandBottomAction.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT * 0.5f));
                    break;
                }
                if (discardAmt == 3) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH * 0.5f, Settings.HEIGHT * 0.5f));
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH * 0.5f - (EnemyMakeTempCardInHandBottomAction.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT * 0.5f));
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH * 0.5f + (EnemyMakeTempCardInHandBottomAction.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT * 0.5f));
                    break;
                }
                break;
            }
            default: {
                for (int i = 0; i < discardAmt; ++i) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDiscardEffect(this.makeNewCard(), MathUtils.random(Settings.WIDTH * 0.2f, Settings.WIDTH * 0.8f), MathUtils.random(Settings.HEIGHT * 0.3f, Settings.HEIGHT * 0.7f)));
                }
                break;
            }
        }
    }

    private AbstractCard makeNewCard() {
        if (this.sameUUID) {
            return this.c.makeSameInstanceOf();
        }
        return this.c.makeStatEquivalentCopy();
    }
}
