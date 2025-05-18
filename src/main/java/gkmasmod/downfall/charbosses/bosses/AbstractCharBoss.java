package gkmasmod.downfall.charbosses.bosses;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.helpers.PowerTip;
import gkmasmod.downfall.bosses.AbstractIdolBoss;
import gkmasmod.downfall.bosses.ProducerBoss;
import gkmasmod.downfall.charbosses.actions.common.EnemyDiscardAtEndOfTurnAction;
import gkmasmod.downfall.charbosses.actions.common.EnemyUseCardAction;
import gkmasmod.downfall.charbosses.actions.orb.EnemyAnimateOrbAction;
import gkmasmod.downfall.charbosses.actions.orb.EnemyChannelAction;
import gkmasmod.downfall.charbosses.actions.orb.EnemyEvokeOrbAction;
import gkmasmod.downfall.charbosses.actions.orb.EnemyTriggerEndOfTurnOrbActions;
import gkmasmod.downfall.charbosses.actions.util.CharbossDoNextCardAction;
import gkmasmod.downfall.charbosses.actions.util.CharbossMakePlayAction;
import gkmasmod.downfall.charbosses.actions.util.CharbossTurnstartDrawAction;
import gkmasmod.downfall.charbosses.actions.util.DelayedActionAction;

import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.downfall.charbosses.cards.EnemyCardGroup;
import gkmasmod.downfall.charbosses.cards.blue.EnThunderStrike;
import gkmasmod.downfall.charbosses.cards.green.EnBladeDance;
import gkmasmod.downfall.charbosses.cards.green.EnCloakAndDagger;
import gkmasmod.downfall.charbosses.cards.red.EnBodySlam;
import gkmasmod.downfall.charbosses.cards.red.EnSecondWind;
import gkmasmod.downfall.charbosses.core.EnemyEnergyManager;
import gkmasmod.downfall.charbosses.orbs.AbstractEnemyOrb;
import gkmasmod.downfall.charbosses.orbs.EnemyDark;
import gkmasmod.downfall.charbosses.orbs.EnemyEmptyOrbSlot;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import gkmasmod.downfall.charbosses.relics.CBR_LizardTail;
import gkmasmod.downfall.charbosses.relics.CBR_PenNib;
import gkmasmod.downfall.charbosses.relics.CBR_Shuriken;
import gkmasmod.downfall.charbosses.stances.AbstractEnemyStance;
import gkmasmod.downfall.charbosses.stances.ENFullPowerStance;
import gkmasmod.downfall.charbosses.stances.EnNeutralStance;
import gkmasmod.downfall.charbosses.ui.EnemyEnergyPanel;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.RunicDome;
import com.megacrit.cardcrawl.relics.SlaversCollar;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.ui.buttons.PeekButton;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import com.megacrit.cardcrawl.vfx.cardManip.CardDisappearEffect;
import com.megacrit.cardcrawl.vfx.combat.BlockedWordEffect;
import com.megacrit.cardcrawl.vfx.combat.DeckPoofEffect;
import com.megacrit.cardcrawl.vfx.combat.HbBlockBrokenEffect;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;
import gkmasmod.powers.*;
import gkmasmod.stances.FullPowerStance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;


public abstract class AbstractCharBoss extends AbstractMonster {

    public static AbstractCharBoss boss;
    public static boolean finishedSetup;

    public ArrayList<AbstractCharbossRelic> relics;
    public AbstractStance stance;
    public ArrayList<AbstractOrb> orbs;

    public static HashMap<String,AbstractBossCard> cardInBattle;

    public int maxOrbs;
    public int masterMaxOrbs;
    public int masterHandSize;
    public int gameHandSize;

    public static String theIdolName = "shro";

    public int mantraGained = 0;

    public boolean LizardTailActive = false;

    public boolean powerhouseTurn;

    public EnemyEnergyManager energy;
    public EnergyOrbInterface energyOrb;
    public EnemyEnergyPanel energyPanel;

    //public CardGroup masterDeck;
    public CardGroup drawPile;
    public CardGroup hand;
    //public CardGroup discardPile;
    //public CardGroup exhaustPile;
    public CardGroup limbo;
    public AbstractCard cardInUse;

    public int damagedThisCombat;
    public int cardsPlayedThisTurn;
    public int cardsPlayedThisCombat = 0;
    public int attacksPlayedThisTurn;

    public AbstractPlayer.PlayerClass chosenClass;
    public AbstractBossDeckArchetype chosenArchetype = null;

    public boolean onSetupTurn = true;

    private static final boolean debugLog = false;

    private static int attacksDrawnForAttackPhase = 0;
    private static int setupsDrawnForSetupPhase = 0;


    public String energyString = "[E]";

    public AbstractCharBoss(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY, PlayerClass playerClass) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY);
        AbstractCharBoss.finishedSetup = false;
//        this.drawX = (float) Settings.WIDTH * 0.75F - 150F * Settings.xScale;
        this.drawX = (float) Settings.WIDTH * 0.7F;

        this.type = EnemyType.BOSS;
        this.chosenClass = playerClass;
        this.energyPanel = new EnemyEnergyPanel(this);
        this.hand = new EnemyCardGroup(CardGroupType.HAND, this);
        this.limbo = new EnemyCardGroup(CardGroupType.UNSPECIFIED, this);
        this.drawPile = new EnemyCardGroup(CardGroupType.UNSPECIFIED, this);
        this.masterHandSize = 3;
        this.gameHandSize = 3;
        this.masterMaxOrbs = this.maxOrbs = 0;
        this.stance = new EnNeutralStance();
        this.orbs = new ArrayList<>();
        this.relics = new ArrayList<>();
    }

    @Override
    public void init() {
        AbstractCharBoss.boss = this;
        this.setHp(this.maxHealth);
        if(AbstractCharBoss.boss instanceof ProducerBoss){
            this.energy.energyMaster = 999;
        }
        else
            this.energy.energyMaster = 3;
        this.generateAll();
        super.init();
        this.preBattlePrep();
        AbstractCharBoss.finishedSetup = true;
        cardInBattle = new HashMap<>();
    }

    @Override
    public void getMove(int num) {
        this.setMove((byte) 0, Intent.NONE);
    }


    public void generateDeck() {
    }


    public void generateAll() {
        this.generateDeck();
        if(!(AbstractCharBoss.boss instanceof ProducerBoss)){
            maxHealth += chosenArchetype.maxHPModifier;
            if (AbstractDungeon.ascensionLevel >= 9) {
                maxHealth = Math.round(maxHealth * 1.2F);
            }
            currentHealth = maxHealth;
        }
        updateHealthBar();
    }

    public void usePreBattleAction() {
        this.energy.recharge();
        for (AbstractCharbossRelic r : this.relics) {
            r.atBattleStartPreDraw();
        }
        addToBot(new DelayedActionAction(new CharbossTurnstartDrawAction()));
        for (AbstractCharbossRelic r : this.relics) {
            r.atBattleStart();
        }
        playMusic();

        if(AbstractCharBoss.boss instanceof AbstractIdolBoss){
            chosenArchetype.addedPreBattle();

        }
    }

    public void preBattlePrep() {
        this.damagedThisCombat = 0;
        this.cardsPlayedThisTurn = 0;
        this.attacksPlayedThisTurn = 0;
        this.maxOrbs = 0;
        this.orbs.clear();
        this.increaseMaxOrbSlots(this.masterMaxOrbs, false);
        this.isBloodied = (this.currentHealth <= this.maxHealth / 2);
        AbstractPlayer.poisonKillCount = 0;
        this.gameHandSize = this.masterHandSize;
        this.cardInUse = null;

//        AbstractDungeon.overlayMenu.endTurnButton.enabled = false;
        this.hand.clear();
        this.drawPile.clear();
        if (this.hasRelic("SlaversCollar")) {
            ((SlaversCollar) this.getRelic("SlaversCollar")).beforeEnergyPrep();
        }
        this.energy.prep();
        this.powers.clear();
        this.healthBarUpdatedEvent();
        this.applyPreCombatLogic();
    }

    public void playMusic() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();

        String musicKey;
        if (AbstractDungeon.actNum == 0) musicKey = "BOSS_BOTTOM";
        else if (AbstractDungeon.actNum == 1) musicKey = "BOSS_CITY";
        else if (AbstractDungeon.actNum == 2) musicKey = "BOSS_BEYOND";
        else musicKey = "MINDBLOOM";

        AbstractDungeon.getCurrRoom().playBgmInstantly(musicKey);
    }

    @Override
    public void takeTurn() {
        attacksDrawnForAttackPhase = 0;
        setupsDrawnForSetupPhase = 0;
        this.startTurn();
        addToBot(new CharbossMakePlayAction());
        this.onSetupTurn = !this.onSetupTurn;
    }

    public void makePlay() {
        for (AbstractCard _c : this.hand.group) {
            AbstractBossCard c = (AbstractBossCard) _c;
            if (c.canUse(AbstractDungeon.player, this) && c.getPriority(this.hand.group) > -100) {
                this.useCard(c, this, EnemyEnergyPanel.totalCount);
                if(AbstractCharBoss.boss instanceof ProducerBoss &&cardsPlayedThisTurn>2){
                    return;
                }
                this.addToBot(new DelayedActionAction(new CharbossDoNextCardAction()));
                return;
            }

        }

        //doing this here instead of end turn allows it to still be before player loses Block
        addToBot(new EnemyTriggerEndOfTurnOrbActions());
    }

    @Override
    public void update() {
        super.update();
        for (AbstractRelic r : this.relics) {
            r.update();
        }

        for (AbstractOrb o : this.orbs) {
            o.update();
            o.updateAnimation();
        }

        this.combatUpdate();
    }

    @Override
    public void applyEndOfTurnTriggers() {
        if(AbstractCharBoss.boss instanceof ProducerBoss){

        }
        else if (hasPower(StunMonsterPower.POWER_ID)) chosenArchetype.turn--;

        this.energy.recharge();

        for (final AbstractPower p : AbstractCharBoss.boss.powers) {
            p.onEnergyRecharge();
        }

        for (final AbstractCard c : this.hand.group) {
            c.triggerOnEndOfTurnForPlayingCard();
        }
        this.stance.onEndOfTurn();
        if(AbstractCharBoss.boss instanceof ProducerBoss){

        }
        else {
            addToBot(new EnemyDiscardAtEndOfTurnAction());
            for (final AbstractCard c : this.hand.group) {
                c.resetAttributes();
            }
            addToBot(new DelayedActionAction(new CharbossTurnstartDrawAction()));
        }
    }

    public void startTurn() {
        this.cardsPlayedThisTurn = 0;
        this.attacksPlayedThisTurn = 0;
        for (AbstractCard c : hand.group) {
            ((AbstractBossCard) c).lockIntentValues = true;
        }
        this.drawPile.clear();
        this.applyStartOfTurnRelics();
        this.applyStartOfTurnPreDrawCards();
        this.applyStartOfTurnCards();
        this.applyStartOfTurnPowers2();
        //this.applyStartOfTurnPowers();
        this.applyStartOfTurnOrbs();

    }

    public ArrayList<AbstractCard> getThisTurnCards() {
        return chosenArchetype.getThisTurnCards();
    }

    class sortByNewPrio implements Comparator<AbstractBossCard> {
        public int compare(AbstractBossCard a, AbstractBossCard b) {
            return a.newPrio - b.newPrio;
        }
    }


    public void endTurnStartTurn() {
        if (!AbstractDungeon.getCurrRoom().isBattleOver) {
            if(AbstractCharBoss.boss instanceof ProducerBoss){
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        isDone = true;
                        int handSize = AbstractCharBoss.boss.hand.group.size();
                        while(handSize<12){
                            handSize++;
                            AbstractCard c = ProducerBoss.getProducerCard();
                            AbstractCharBoss.boss.hand.addToTop(c);
                            if (c instanceof AbstractBossCard) ((AbstractBossCard) c).bossDarken();
                            c.current_y = Settings.HEIGHT / 2F;
                            c.current_x = Settings.WIDTH;
                        }
                        AbstractCharBoss.boss.hand.refreshHandLayout();
                        applyPowers();
                    }
                });
            }
            else{
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        isDone = true;
                        for (AbstractCard q : getThisTurnCards()) {
                            AbstractCharBoss.boss.hand.addToTop(q);
                            if (q instanceof AbstractBossCard) ((AbstractBossCard) q).bossDarken();
                            q.current_y = Settings.HEIGHT / 2F;
                            q.current_x = Settings.WIDTH;
                        }

                        ArrayList<AbstractBossCard> handAsBoss = new ArrayList<>();
                        for (AbstractCard c : AbstractCharBoss.boss.hand.group) {
                            handAsBoss.add((AbstractBossCard) c);
                        }

                        Collections.sort(handAsBoss, new sortByNewPrio());

                        ArrayList<AbstractCard> newHand = new ArrayList<>();
                        for (AbstractCard c : handAsBoss) {
                            newHand.add(c);
                            c.applyPowers();
                        }

                        AbstractCharBoss.boss.hand.group = newHand;

                        AbstractCharBoss.boss.hand.refreshHandLayout();
                        applyPowers();
                    }
                });
            }
            addToBot(new WaitAction(0.2f));
            this.applyStartOfTurnPostDrawRelics();
            this.applyStartOfTurnPostDrawPowers();
            if (!AbstractDungeon.player.hasRelic(RunicDome.ID)) {
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        isDone = true;
                        int budget = energyPanel.getCurrentEnergy();
                        for (AbstractCard c : AbstractCharBoss.boss.hand.group) {
                            if (c.costForTurn <= budget && c.costForTurn != -2 && c instanceof AbstractBossCard) {
                                ((AbstractBossCard) c).createIntent();
                                ((AbstractBossCard) c).bossLighten();
                                budget -= c.costForTurn;
                                budget += ((AbstractBossCard) c).energyGeneratedIfPlayed;
                                if (budget < 0) budget = 0;
                            } else if (c.costForTurn == -2 && c.type == AbstractCard.CardType.CURSE && c.color == AbstractCard.CardColor.CURSE) {
                                ((AbstractBossCard) c).bossLighten();
                            }
                        }
                        for (AbstractCard c : AbstractCharBoss.boss.hand.group) {
                            AbstractBossCard cB = (AbstractBossCard) c;
                            cB.refreshIntentHbLocation();
                        }
                    }
                });
            }

            this.cardsPlayedThisTurn = 0;
            this.attacksPlayedThisTurn = 0;
        }
    }


    public void preApplyIntentCalculations() {
        boolean hasShuriken = hasRelic(CBR_Shuriken.ID);
        int attackCount = 0;
        int artifactCount = 0;

        if (AbstractDungeon.player.hasPower(ArtifactPower.POWER_ID)) {
            artifactCount = AbstractDungeon.player.getPower(ArtifactPower.POWER_ID).amount;
        }

        //Reset all custom modifiers back to 0
        for (AbstractCard c : hand.group) {
            ((AbstractBossCard) c).manualCustomDamageModifier = 0;
            ((AbstractBossCard) c).manualCustomDamageModifierMult = 1;
        }

        int foundAttacks = 0; //For pen nib
        for (int i = 0; i < hand.size(); i++) {
            AbstractBossCard c = (AbstractBossCard) hand.group.get(i);

            if (!c.lockIntentValues) {

                //Artifact Checks - calculates if any Artifact will be left
                if (c.artifactConsumedIfPlayed > 0) {
                    artifactCount -= c.artifactConsumedIfPlayed;
                }

                //Vulnerable Check - knows to check if any Artifact will be left
                if (c.vulnGeneratedIfPlayed > 0) {
                    if (artifactCount <= 0) {
                        for (int j = i + 1; j < hand.size(); j++) {
                            AbstractBossCard c2 = (AbstractBossCard) hand.group.get(j);
                            c2.manualCustomVulnModifier = true;
                        }
                    }
                }

                //Shuriken Checks for Act 1 Silent
                if (hasShuriken) {
                    AbstractBossCard c3 = (AbstractBossCard) hand.group.get(i);
                    if (c.type == AbstractCard.CardType.ATTACK || c3 instanceof EnCloakAndDagger) {
                        attackCount++;
                        if (attackCount == 3) {
                            for (int j = i + 1; j < hand.size(); j++) {
                                AbstractBossCard c2 = (AbstractBossCard) hand.group.get(j);
                                c2.manualCustomDamageModifier += 1;
                            }
                            attackCount = 0;
                        }
                    }
                    if (c3 instanceof EnBladeDance) {
                        for (int j = i + 1; j < hand.size(); j++) {
                            AbstractBossCard c2 = (AbstractBossCard) hand.group.get(j);
                            c2.manualCustomDamageModifier += 1;
                        }
                    }
                }

                //Strength check for Wish in Act 2 Watcher, Act 2 Defect's Reprogram
                if (c.strengthGeneratedIfPlayed > 0) {
                    for (int j = i + 1; j < hand.size(); j++) {
                        AbstractBossCard c2 = (AbstractBossCard) hand.group.get(j);
                        if (c2.type == AbstractCard.CardType.ATTACK)
                            c2.manualCustomDamageModifier += c.strengthGeneratedIfPlayed;
                    }
                }

                //Block checks for Act 3 Ironclad's Body Slams
                if (c.block > 0) {
                    //Special case for Second Wind, always exhausts 2 wounds with Feel No Pain up
                    int tmpBlock = c.block;
                    if (c instanceof EnSecondWind) {
                        tmpBlock = 2 * (c.block + 3);
                    }
                    for (int j = i + 1; j < hand.size(); j++) {
                        AbstractBossCard c2 = (AbstractBossCard) hand.group.get(j);
                        if (c2 instanceof EnBodySlam) {
                            c2.manualCustomDamageModifier += tmpBlock;
                        }
                    }
                }

                //Minion block checks for Act 3 Ironclad's Body Slams
                if (c instanceof EnBodySlam) {
                    // and Self-Forming Clay
                    AbstractPower p = getPower(NextTurnBlockPower.POWER_ID);
                    if (p != null) {
                        c.manualCustomDamageModifier += p.amount;
                    }
                }

                //Divinity Check for Act 2 Watcher
                if (c.damageMultGeneratedIfPlayed > 1) {
                    for (int j = i + 1; j < hand.size(); j++) {
                        AbstractBossCard c2 = (AbstractBossCard) hand.group.get(j);
                        c2.manualCustomDamageModifierMult = c.damageMultGeneratedIfPlayed;
                    }
                }

                c.manualCustomDamageModifierMult *= c.damageMultIfPlayed;

                //Pen Nib

                if (c.type == AbstractCard.CardType.ATTACK) {
                    foundAttacks += 1;
                    if (hasRelic(CBR_PenNib.ID)) {
                        AbstractRelic r = getRelic(CBR_PenNib.ID);
                        if (r.counter + foundAttacks == 10) {
                            c.manualCustomDamageModifierMult = 2;
                        }
                    }
                }


            }
        }
        for (AbstractCard c : hand.group) {
            if (!((AbstractBossCard) c).bossDarkened) {
                ((AbstractBossCard) c).createIntent();
            }
        }

    }

    public void applyPowers() {
        super.applyPowers();

        preApplyIntentCalculations();

        this.hand.applyPowers();
    }

    public int getIntentDmg() {
        int totalIntentDmg = -1;
        for (AbstractCard c : this.hand.group) {
            AbstractBossCard cB = (AbstractBossCard) c;
            if (cB.intentDmg > 0 && (!cB.bossDarkened || AbstractDungeon.player.hasRelic(RunicDome.ID))) {
                if (totalIntentDmg == -1) {
                    totalIntentDmg = 0;
                }
                totalIntentDmg += cB.intentDmg;
            }
        }
        return totalIntentDmg;
    }

    public int getIntentBaseDmg() {
        return getIntentDmg();
    }

    public boolean hasRelic(final String targetID) {
        for (final AbstractRelic r : this.relics) {
            if (r.relicId.equals(targetID)) {
                return true;
            }
        }
        return false;
    }

    public AbstractRelic getRelic(final String targetID) {
        for (final AbstractRelic r : this.relics) {
            if (r.relicId.equals(targetID)) {
                return r;
            }
        }
        return null;
    }

    public void gainEnergy(final int e) {
        EnemyEnergyPanel.addEnergy(e);
        this.hand.glowCheck();

    }

    public void loseEnergy(final int e) {
        EnemyEnergyPanel.useEnergy(e);
    }


    private AbstractCard findReplacementCardInDraw(ArrayList<AbstractCard> drawPile, boolean attack, boolean setup) {
        for (AbstractCard c : drawPile) {
        }

        return null;
    }

    private AbstractCard performCardSearch(ArrayList<AbstractCard> drawPile, DrawTypes firstPriority, DrawTypes secondPriority, DrawTypes thirdPriority) {
        AbstractCard replacementCard = null;

        if (firstPriority == secondPriority || firstPriority == thirdPriority || secondPriority == thirdPriority) {
            return null;
        }

        if (firstPriority == DrawTypes.Setup) {
            replacementCard = findReplacementCardInDraw(drawPile, false, true);
        } else if (firstPriority == DrawTypes.Attack) {
            replacementCard = findReplacementCardInDraw(drawPile, true, false);
        } else {
            replacementCard = findReplacementCardInDraw(drawPile, false, false);
        }

        if (replacementCard != null) {
            return replacementCard;
        }

        if (secondPriority == DrawTypes.Setup) {
            replacementCard = findReplacementCardInDraw(drawPile, false, true);
        } else if (secondPriority == DrawTypes.Attack) {
            replacementCard = findReplacementCardInDraw(drawPile, true, false);
        } else {
            replacementCard = findReplacementCardInDraw(drawPile, false, false);
        }

        if (replacementCard != null) {
            return replacementCard;
        }

        if (thirdPriority == DrawTypes.Setup) {
            replacementCard = findReplacementCardInDraw(drawPile, false, true);
        } else if (thirdPriority == DrawTypes.Attack) {
            replacementCard = findReplacementCardInDraw(drawPile, true, false);
        } else {
            replacementCard = findReplacementCardInDraw(drawPile, false, false);
        }
        return replacementCard;
    }


    private AbstractCard chooseCardToDraw(ArrayList<AbstractCard> drawPile) {

        AbstractBossCard c = (AbstractBossCard) drawPile.get(0);
        AbstractCard replacementCard = null;

        if (drawPile.size() > 1) {

            if (c.forceDraw) {
                return c;
            }

            if (this.onSetupTurn) {
                if (setupsDrawnForSetupPhase < 1) {

                } else {
                    replacementCard = performCardSearch(drawPile, DrawTypes.EitherPhase, DrawTypes.Setup, DrawTypes.Attack);
                    if (replacementCard != null) {
                        return replacementCard;
                    } else {
                    }
                }
            }

            if (!this.onSetupTurn) {
                if (attacksDrawnForAttackPhase < 1) {

                } else {
                    replacementCard = performCardSearch(drawPile, DrawTypes.EitherPhase, DrawTypes.Attack, DrawTypes.Setup);
                    if (replacementCard != null) {
                        return replacementCard;
                    } else {
                    }
                }
            }
            return c;
        } else {
            return c;
        }

    }

    public void onCardDrawOrDiscard() {
        for (final AbstractPower p : this.powers) {
            p.onDrawOrDiscard();
        }
        for (final AbstractRelic r : this.relics) {
            r.onDrawOrDiscard();
        }
        if (this.hasPower("Corruption")) {
            for (final AbstractCard c : this.hand.group) {
                if (c.type == AbstractCard.CardType.SKILL && c.costForTurn != 0) {
                    c.modifyCostForCombat(-9);
                }
            }
        }
        this.hand.applyPowers();
        this.hand.glowCheck();
    }

    public void useCard(final AbstractCard c, AbstractMonster monster, final int energyOnUse) {
        if (monster == null) {
            monster = this;
        }
        if (c.type == AbstractCard.CardType.ATTACK) {
            this.attacksPlayedThisTurn++;
            this.useFastAttackAnimation();

            if (c.damage > MathUtils.random(20)) {
                this.onPlayAttackCardSound();
            }
        }
        if(AbstractCharBoss.boss instanceof ProducerBoss){
            if (AbstractDungeon.actionManager.turnHasEnded &&(AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT){
                cardsPlayedThisTurn++;
            }
            else{

            }
        }
        else{
            cardsPlayedThisTurn++;
        }
        this.cardsPlayedThisCombat++;
        c.calculateCardDamage(monster);
        if (c.cost == -1 && EnemyEnergyPanel.totalCount < energyOnUse && !c.ignoreEnergyOnUse) {
            c.energyOnUse = EnemyEnergyPanel.totalCount;
        }
        if (c.cost == -1 && c.isInAutoplay) {
            c.freeToPlayOnce = true;
        }
        c.use(AbstractDungeon.player, monster);
        addToBot(new EnemyUseCardAction(c, monster));
        if (!c.dontTriggerOnUseCard) {
            this.hand.triggerOnOtherCardPlayed(c);
        }
        this.hand.removeCard(c);
        this.cardInUse = c;
        c.target_x = (float) (Settings.WIDTH / 2);
        c.target_y = (float) (Settings.HEIGHT / 2);
        if (c.costForTurn > 0 && !c.freeToPlay() && !c.isInAutoplay && (!this.hasPower("Corruption") || c.type != AbstractCard.CardType.SKILL)) {
            this.energy.use(c.costForTurn);
        }

        AbstractBossCard cB = (AbstractBossCard) c;
        cB.showIntent = false;
    }

    public void combatUpdate() {
        if (this.cardInUse != null) {
            this.cardInUse.update();
        }
        this.energyPanel.update();
        this.limbo.update();
        this.hand.update();
        this.hand.updateHoverLogic();
        for (final AbstractPower p : this.powers) {
            p.updateParticles();
        }
        for (final AbstractOrb o : this.orbs) {
            o.update();
        }
        this.stance.update();
    }

    public void onPlayAttackCardSound() {
    }


    @Override
    public void damage(final DamageInfo info) {
        int damageAmount = info.output;
        boolean hadBlock = this.currentBlock != 0;
        if (damageAmount < 0) {
            damageAmount = 0;
        }
        if (damageAmount > 1 && this.hasPower(IntangiblePower.POWER_ID)) {
            damageAmount = 1;
        }
        final boolean weakenedToZero = damageAmount == 0;
        damageAmount = this.decrementBlock(info, damageAmount);
        if (info.owner == this) {
            for (final AbstractRelic r : this.relics) {
                damageAmount = r.onAttackToChangeDamage(info, damageAmount);
            }
        }
        if (info.owner == AbstractDungeon.player) {
            for (final AbstractRelic r : AbstractDungeon.player.relics) {
                damageAmount = r.onAttackToChangeDamage(info, damageAmount);
            }
        }
        if (info.owner != null) {
            for (final AbstractPower p : info.owner.powers) {
                damageAmount = p.onAttackToChangeDamage(info, damageAmount);
            }
        }
        for (final AbstractRelic r : this.relics) {
            damageAmount = r.onAttackedToChangeDamage(info, damageAmount);
        }
        for (final AbstractPower p : this.powers) {
            damageAmount = p.onAttackedToChangeDamage(info, damageAmount);
        }
        if (info.owner == this) {
            for (final AbstractRelic r : this.relics) {
                r.onAttack(info, damageAmount, this);
            }
        }
        if (info.owner == AbstractDungeon.player) {
            for (final AbstractRelic r : AbstractDungeon.player.relics) {
                r.onAttack(info, damageAmount, this);
            }
        }
        if (info.owner != null) {
            for (final AbstractPower p : info.owner.powers) {
                p.onAttack(info, damageAmount, this);
            }
            for (final AbstractPower p : this.powers) {
                damageAmount = p.onAttacked(info, damageAmount);
            }
            for (final AbstractRelic r : this.relics) {
                damageAmount = r.onAttacked(info, damageAmount);
            }
        }
        for (final AbstractRelic r : this.relics) {
            damageAmount = r.onLoseHpLast(damageAmount);
        }
        this.lastDamageTaken = Math.min(damageAmount, this.currentHealth);
        final boolean probablyInstantKill = this.currentHealth == 0;
        if (damageAmount > 0 || probablyInstantKill) {
            for (final AbstractPower p : this.powers) {
                damageAmount = p.onLoseHp(damageAmount);
            }
            for (final AbstractPower p : this.powers) {
                p.wasHPLost(info, damageAmount);
            }
            for (final AbstractRelic r : this.relics) {
                r.wasHPLost(damageAmount);
            }
            if (info.owner != null) {
                for (final AbstractPower p : info.owner.powers) {
                    p.onInflictDamage(info, damageAmount, this);
                }
            }
            if (info.owner != this) {
                this.useStaggerAnimation();
            }
            if (damageAmount > 0) {
                for (final AbstractRelic r : this.relics) {
                    r.onLoseHp(damageAmount);
                }
                if (info.owner != this) {
                    this.useStaggerAnimation();
                }
                if (damageAmount >= 99 && !CardCrawlGame.overkill) {
                    CardCrawlGame.overkill = true;
                }
                this.currentHealth -= damageAmount;
                if (!probablyInstantKill) {
                    AbstractDungeon.effectList.add(new StrikeEffect(this, this.hb.cX, this.hb.cY, damageAmount));
                }
                if (this.currentHealth < 0) {
                    this.currentHealth = 0;
                }
                this.healthBarUpdatedEvent();
                this.updateCardsOnDamage();
            }
            if (this.currentHealth <= this.maxHealth / 2.0f && !this.isBloodied) {
                this.isBloodied = true;
                for (final AbstractRelic r : this.relics) {
                    if (r != null) {
                        r.onBloodied();
                    }
                }
            }
            if (this.currentHealth < 1) {
                if (!this.hasRelic("Mark of the Bloom")) {
                    for (final AbstractPower p : this.powers) {
                        if (p instanceof RebirthPower) {
                            this.currentHealth = 0;
                            if(boss instanceof AbstractIdolBoss){
                                ((AbstractIdolBoss) boss).changeSong = true;
                            }
                            p.onSpecificTrigger();
                            return;
                        }
                    }
                    if (this.hasRelic(CBR_LizardTail.ID) && this.getRelic(CBR_LizardTail.ID).counter == -1) {
//                        System.out.println("Lizard Tail triggered.");
                        this.currentHealth = 0;
                        this.getRelic(CBR_LizardTail.ID).onTrigger();
                        LizardTailActive=true;
                        return;
                    }
                }
                this.die();
                if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.cleanCardQueue();
                    AbstractDungeon.effectList.add(new DeckPoofEffect(64.0f * Settings.scale, 64.0f * Settings.scale, true));
                    AbstractDungeon.effectList.add(new DeckPoofEffect(Settings.WIDTH - 64.0f * Settings.scale, 64.0f * Settings.scale, false));
                    AbstractDungeon.overlayMenu.hideCombatPanels();
                }
                if (this.currentBlock > 0) {
                    this.loseBlock();
                    AbstractDungeon.effectList.add(new HbBlockBrokenEffect(this.hb.cX - this.hb.width / 2.0f + AbstractMonster.BLOCK_ICON_X, this.hb.cY - this.hb.height / 2.0f + AbstractMonster.BLOCK_ICON_Y));
                }

                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if ((!m.isDead) && (!m.isDying) && m.hasPower(MinionPower.POWER_ID)) {
                        AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.utility.HideHealthBarAction(m));
                        AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.SuicideAction(m));
                    }
                }
            }
        } else if (!probablyInstantKill) {
            if (weakenedToZero && this.currentBlock == 0) {
                if (hadBlock) {
                    AbstractDungeon.effectList.add(new BlockedWordEffect(this, this.hb.cX, this.hb.cY, AbstractMonster.TEXT[30]));
                } else {
                    AbstractDungeon.effectList.add(new StrikeEffect(this, this.hb.cX, this.hb.cY, 0));
                }
            } else if (Settings.SHOW_DMG_BLOCK) {
                AbstractDungeon.effectList.add(new BlockedWordEffect(this, this.hb.cX, this.hb.cY, AbstractMonster.TEXT[30]));
            }
        }
    }

    @Override
    public void die() {
        if (this.currentHealth <= 0) {
            useFastShakeAnimation(5.0F);
            CardCrawlGame.screenShake.rumble(4.0F);
            if (true) {
                if (hasPower(MinionPower.POWER_ID)) {
                    if (Settings.FAST_MODE) {
                        this.deathTimer += 0.7F;
                    } else {
                        ++this.deathTimer;
                    }
                } else {

                }
            }

        }


        AbstractCharBoss.boss = null;
        AbstractCharBoss.finishedSetup = false;
        relics.clear();
        hand.clear();
        limbo.clear();
        orbs.clear();
        stance.onExitStance();
        stance = AbstractEnemyStance.getStanceFromName("Neutral");
        stance.onEnterStance();

        super.die();
        chosenArchetype = null;

    }


    private void updateCardsOnDamage() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            for (final AbstractCard c : this.hand.group) {
                c.tookDamage();
            }
        }
    }


    public void updateCardsOnDiscard() {
        for (final AbstractCard c : this.hand.group) {
            c.didDiscard();
        }
    }

    @Override
    public void heal(final int healAmount) {
        int amt = healAmount;
        for (final AbstractRelic r : this.relics) {
            amt = r.onPlayerHeal(amt);
        }
        super.heal(amt);
        if (this.currentHealth > this.maxHealth / 2.0f && this.isBloodied) {
            this.isBloodied = false;
            for (final AbstractRelic r : this.relics) {
                r.onNotBloodied();
            }
        }
    }

    public ArrayList<String> getRelicNames() {
        final ArrayList<String> arr = new ArrayList<String>();
        for (final AbstractRelic relic : this.relics) {
            arr.add(relic.relicId);
        }
        return arr;
    }

    public void applyPreCombatLogic() {
        for (final AbstractRelic r : this.relics) {
            if (r != null) {
                r.atPreBattle();
            }
        }
    }

    public void applyStartOfCombatLogic() {

        for (final AbstractRelic r : this.relics) {
            if (r != null) {
                r.atBattleStart();
            }
        }
    }

    public void applyStartOfCombatPreDrawLogic() {
        for (final AbstractRelic r : this.relics) {
            if (r != null) {
                r.atBattleStartPreDraw();
            }
        }
    }

    public void applyStartOfTurnRelics() {
        this.stance.atStartOfTurn();
        for (final AbstractRelic r : this.relics) {
            if (r != null) {
                r.atTurnStart();
            }
        }
    }

    public void applyStartOfTurnPostDrawRelics() {
        for (final AbstractRelic r : this.relics) {
            if (r != null) {
                r.atTurnStartPostDraw();
            }
        }
    }

    public void applyStartOfTurnPreDrawCards() {
        for (final AbstractCard c : this.hand.group) {
            if (c != null) {
                c.atTurnStartPreDraw();
            }
        }
    }

    public void applyStartOfTurnPowers2(){
        for (final AbstractPower p : this.powers) {
//            System.out.println(p.name);
            if(p instanceof FullPowerValue){
                p.onSpecificTrigger();
            }
        }
    }

    public void applyStartOfTurnCards() {
        for (final AbstractCard c : this.hand.group) {
            if (c != null) {
                c.atTurnStart();
                c.triggerWhenDrawn();
            }
        }
    }

    public boolean relicsDoneAnimating() {
        for (final AbstractRelic r : this.relics) {
            if (!r.isDone) {
                return false;
            }
        }
        return true;
    }

    public void switchedStance() {
        for (final AbstractCard c : this.hand.group) {
            c.switchedStance();
        }
        if(stance.ID.equals(ENFullPowerStance.STANCE_ID)){
            if(hasPower(TempSavePower.POWER_ID)){
                TempSavePower power = (TempSavePower) getPower(TempSavePower.POWER_ID);
                power.getInHand();
            }
        }
    }

    public void onStanceChange(final String id) {
    }

    public void addBlock(final int blockAmount) {
        float tmp = (float) blockAmount;
        for (final AbstractRelic r : this.relics) {
            tmp = (float) r.onPlayerGainedBlock(tmp);
        }
        if (tmp > 0.0f) {
            for (final AbstractPower p : this.powers) {
                p.onGainedBlock(tmp);
            }
        }
        super.addBlock((int) Math.floor(tmp));
    }

    public void triggerEvokeAnimation(final int slot) {
        if (this.maxOrbs <= 0) {
            return;
        }
        this.orbs.get(slot).triggerEvokeAnimation();
    }

//    @Override
//    public void renderTip(SpriteBatch sb) {
//        super.renderTip(sb);
//        if (!this.stance.ID.equals("Neutral"))
//            tips.add(new PowerTip(this.stance.name, this.stance.description));
//    }

    @Override
    public void renderPowerTips(SpriteBatch sb) {
        super.renderPowerTips(sb);
    }

    public void evokeOrb() {
        if (!this.orbs.isEmpty() && !(this.orbs.get(0) instanceof EnemyEmptyOrbSlot)) {
            this.orbs.get(0).onEvoke();
            final AbstractEnemyOrb orbSlot = new EnemyEmptyOrbSlot();
            for (int i = 1; i < this.orbs.size(); ++i) {
                Collections.swap(this.orbs, i, i - 1);
            }
            this.orbs.set(this.orbs.size() - 1, orbSlot);
            for (int i = 0; i < this.orbs.size(); ++i) {
                this.orbs.get(i).setSlot(i, this.maxOrbs);
            }
        }
    }

    public ArrayList<AbstractEnemyOrb> orbsAsEn() {
        ArrayList<AbstractEnemyOrb> orbies = new ArrayList<AbstractEnemyOrb>();
        for (AbstractOrb o : orbs) {
            if (o instanceof AbstractEnemyOrb) {
                orbies.add((AbstractEnemyOrb) o);
            }
        }
        return orbies;
    }

    public void evokeNewestOrb() {
        if (!this.orbs.isEmpty() && !(this.orbs.get(this.orbs.size() - 1) instanceof EnemyEmptyOrbSlot)) {
            this.orbs.get(this.orbs.size() - 1).onEvoke();
        }
    }

    public void evokeWithoutLosingOrb() {
        if (!this.orbs.isEmpty() && !(this.orbs.get(0) instanceof EnemyEmptyOrbSlot)) {
            this.orbs.get(0).onEvoke();
        }
    }

    public void removeNextOrb() {
        if (!this.orbs.isEmpty() && !(this.orbs.get(0) instanceof EnemyEmptyOrbSlot)) {
            final AbstractEnemyOrb orbSlot = new EnemyEmptyOrbSlot(this.orbs.get(0).cX, this.orbs.get(0).cY);
            for (int i = 1; i < this.orbs.size(); ++i) {
                Collections.swap(this.orbs, i, i - 1);
            }
            this.orbs.set(this.orbs.size() - 1, orbSlot);
            for (int i = 0; i < this.orbs.size(); ++i) {
                this.orbs.get(i).setSlot(i, this.maxOrbs);
            }
        }
    }

    public boolean hasEmptyOrb() {
        if (this.orbs.isEmpty()) {
            return false;
        }
        for (final AbstractOrb o : this.orbs) {
            if (o instanceof EnemyEmptyOrbSlot) {
                return true;
            }
        }
        return false;
    }

    public boolean hasOrb() {
        return !this.orbs.isEmpty() && !(this.orbs.get(0) instanceof EnemyEmptyOrbSlot);
    }

    public int filledOrbCount() {
        int orbCount = 0;
        for (final AbstractOrb o : this.orbs) {
            if (!(o instanceof EnemyEmptyOrbSlot)) {
                ++orbCount;
            }
        }
        return orbCount;
    }

    public void channelOrb(AbstractOrb orbToSet) {
        if (this.maxOrbs <= 0) {
            AbstractDungeon.effectList.add(new ThoughtBubble(this.dialogX, this.dialogY, 3.0f, AbstractPlayer.MSG[4], true));
            return;
        }
        if (this.hasRelic("Dark Core") && !(orbToSet instanceof EnemyDark)) {
            orbToSet = new EnemyDark();
        }
        int index = -1;
        for (int i = 0; i < this.orbs.size(); ++i) {
            if (this.orbs.get(i) instanceof EnemyEmptyOrbSlot) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            orbToSet.cX = this.orbs.get(index).cX;
            orbToSet.cY = this.orbs.get(index).cY;
            this.orbs.set(index, orbToSet);
            this.orbs.get(index).setSlot(index, this.maxOrbs);
            orbToSet.playChannelSFX();
            for (final AbstractPower p : this.powers) {
                p.onChannel(orbToSet);
            }
            AbstractDungeon.actionManager.orbsChanneledThisCombat.add(orbToSet);
            AbstractDungeon.actionManager.orbsChanneledThisTurn.add(orbToSet);
            orbToSet.applyFocus();
        } else {
            AbstractDungeon.actionManager.addToTop(new EnemyChannelAction(orbToSet));
            AbstractDungeon.actionManager.addToTop(new EnemyEvokeOrbAction(1));
            AbstractDungeon.actionManager.addToTop(new EnemyAnimateOrbAction(1));
        }
        // Need to update Thunderstrike intent if a lightning orb is played
        for (AbstractCard card : boss.hand.group) {
            if (card instanceof EnThunderStrike) { // Condition maybe not needed?
                card.applyPowers();
                ((AbstractBossCard) card).createIntent();
            }
        }
    }

    public void increaseMaxOrbSlots(final int amount, final boolean playSfx) {
        if (this.maxOrbs == 10) {
            AbstractDungeon.effectList.add(new ThoughtBubble(this.dialogX, this.dialogY, 3.0f, AbstractPlayer.MSG[3], true));
            return;
        }
        if (playSfx) {
            CardCrawlGame.sound.play("ORB_SLOT_GAIN", 0.1f);
        }
        this.maxOrbs += amount;
        for (int i = 0; i < amount; ++i) {
            this.orbs.add(new EnemyEmptyOrbSlot());
        }
        for (int i = 0; i < this.orbs.size(); ++i) {
            this.orbs.get(i).setSlot(i, this.maxOrbs);
        }
    }

    public void decreaseMaxOrbSlots(final int amount) {
        if (this.maxOrbs <= 0) {
            return;
        }
        this.maxOrbs -= amount;
        if (this.maxOrbs < 0) {
            this.maxOrbs = 0;
        }
        if (!this.orbs.isEmpty()) {
            this.orbs.remove(this.orbs.size() - 1);
        }
        for (int i = 0; i < this.orbs.size(); ++i) {
            this.orbs.get(i).setSlot(i, this.maxOrbs);
        }
    }

    public void applyStartOfTurnOrbs() {
        if (!this.orbs.isEmpty()) {
            for (final AbstractOrb o : this.orbs) {
                o.onStartOfTurn();
            }
            if (this.hasRelic("Cables") && !(this.orbs.get(0) instanceof EnemyEmptyOrbSlot)) {
                this.orbs.get(0).onStartOfTurn();
            }
        }
    }

    @Override
    public void render(final SpriteBatch sb) {
        super.render(sb);
        if (!this.isDead) {
            this.renderHand(sb);
            this.stance.render(sb);
            for (AbstractRelic r : this.relics) {
                r.render(sb);
            }
            if (!this.orbs.isEmpty()) {

                for (AbstractOrb o : this.orbs) {
                    o.render(sb);
                }
            }
            this.energyPanel.render(sb);
        }
    }

    public void renderHand(final SpriteBatch sb) {
        this.hand.renderHand(sb, this.cardInUse);
        if (this.cardInUse != null && AbstractDungeon.screen != AbstractDungeon.CurrentScreen.HAND_SELECT && !PeekButton.isPeeking) {
            this.cardInUse.render(sb);
            if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
                AbstractDungeon.effectList.add(new CardDisappearEffect(this.cardInUse.makeCopy(), this.cardInUse.current_x, this.cardInUse.current_y));
                this.cardInUse = null;
            }
        }
        this.limbo.render(sb);
    }

    private enum DrawTypes {
        Attack,
        Setup,
        EitherPhase;

        DrawTypes() {
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
