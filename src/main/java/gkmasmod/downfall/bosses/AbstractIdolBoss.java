package gkmasmod.downfall.bosses;



import gkmasmod.downfall.charbosses.actions.util.CharbossTurnstartDrawAction;
import gkmasmod.downfall.charbosses.actions.util.DelayedActionAction;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.cards.red.EnBodySlam;
import gkmasmod.downfall.charbosses.core.EnemyEnergyManager;
import gkmasmod.downfall.charbosses.orbs.EnemyEmptyOrbSlot;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import gkmasmod.downfall.charbosses.relics.CBR_LizardTail;
import gkmasmod.downfall.charbosses.stances.AbstractEnemyStance;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.ClearCardQueueAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import gkmasmod.characters.IdolEnergyOrb;
import gkmasmod.monster.ChangeScene;
import gkmasmod.monster.LatterEffect;
import gkmasmod.relics.PocketBook;
import gkmasmod.room.GkmasBossRoom;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;

import static gkmasmod.characters.PlayerColorEnum.gkmasMod_character;

public class AbstractIdolBoss extends AbstractCharBoss {
    public int maxStage = 3;
    public int stage = 0;
    public String idolName;
    public AbstractBossDeckArchetype[] archetypes=null;
    public static int baseHP = 600;
    public int maxHP = 600;
    public static int gold = 200;
    private static final String bgFormat = "gkmasModResource/img/bg/bg_%s_00%s.png";

    public boolean rebirth=false;

    public boolean shroFlag = true;

    public boolean changeSong = true;

    public AbstractIdolBoss(String idolName,String ID) {
        super(CardCrawlGame.languagePack.getCharacterString(String.format("IdolName:%s",idolName)).NAMES[0], ID, baseHP, 000.0f, -16.0f, 220.0f, 290.0f, String.format("gkmasModResource/img/idol/%s/stand/stand_%s.png",idolName,"skin10"), 0.0f, -20.0f, gkmasMod_character);
        this.idolName = idolName;
        maxHP = baseHP;
        AbstractCharBoss.theIdolName = idolName;
        this.energyOrb = new IdolEnergyOrb();
        this.energy = new EnemyEnergyManager(3);
        this.flipHorizontal = true;
        AbstractCharBoss.theIdolName = idolName;
        this.energyString = "[E]";
        type = EnemyType.BOSS;
    }

    @Override
    public void generateDeck() {
        AbstractBossDeckArchetype archetype= archetypes[stage];
        archetype.initialize();
        chosenArchetype = archetype;
        if (AbstractDungeon.ascensionLevel >= 19) {
            archetype.initializeBonusRelic();
        }
    }

    @Override
    public void preBattlePrep() {
        super.preBattlePrep();
        AbstractGameEffect effect =  new ChangeScene(ImageMaster.loadImage(String.format(bgFormat,this.idolName,stage+1)));
        AbstractDungeon.effectList.add(new LatterEffect(() -> {
            AbstractDungeon.effectsQueue.add(effect);
        }));
        if(maxStage>1){
            (AbstractDungeon.getCurrRoom()).cannotLose = true;
        }
        CardCrawlGame.music.dispose();
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        CardCrawlGame.music.playTempBgmInstantly(String.format("gkmasModResource/audio/song/%s_%s.ogg", this.idolName, IdolData.getIdol(this.idolName).getBossSong(stage),true));
    }

    @Override
    public void loseBlock(int amount) {
        super.loseBlock(amount);
        for (AbstractCard c:hand.group){
            if (c instanceof EnBodySlam){
                c.applyPowers();
            }
        }
    }

    @Override
    public void takeTurn() {
        if(rebirth){
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_AWAKENEDONE_1"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new IntenseZoomEffect(this.hb.cX, this.hb.cY, true), 0.05F, true));
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "REBIRTH"));
            return;
        }
        super.takeTurn();
    }

    @Override
    public void playMusic() {

    }

    public void endTurnStartTurn() {
        if(rebirth){
            rebirth=false;
            addToBot(new WaitAction(0.2f));
            this.applyStartOfTurnPostDrawRelics();
            this.applyStartOfTurnPostDrawPowers();
            this.cardsPlayedThisTurn = 0;
            this.attacksPlayedThisTurn = 0;
            return;
        }
        super.endTurnStartTurn();
    }

    public void changeState(String key) {
        switch (key) {
            case "REBIRTH":
                this.energy.recharge();
                this.changeSong = true;
                if(idolName.equals(IdolData.hrnm)){
                    for (int i = 0; i < this.maxOrbs; ++i) {
                        this.orbs.add(new EnemyEmptyOrbSlot());
                    }
                }
                AbstractBossDeckArchetype archetype= archetypes[stage];
                maxHP+=archetype.maxHPModifier;
                if (AbstractDungeon.ascensionLevel >= 9) {
                    maxHP = Math.round(maxHP * 1.2F);
                }
                this.maxHealth = maxHP;
                archetype.initialize();
                this.img = new Texture(String.format("gkmasModResource/img/idol/%s/stand/stand_%s.png",idolName,IdolData.getIdol(idolName).getBossSkin(stage)));
                chosenArchetype = archetype;
                if(stage<maxStage-1)
                    AbstractDungeon.getCurrRoom().cannotLose = true;
                else{
                    AbstractDungeon.getCurrRoom().cannotLose = false;
                }
                for (AbstractCharbossRelic r : this.relics) {
                    r.atBattleStartPreDraw();
                }
                addToBot(new DelayedActionAction(new CharbossTurnstartDrawAction()));
                for (AbstractCharbossRelic r : this.relics) {
                    r.atBattleStart();
                    r.atTurnStart();
                }
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BarricadePower(this)));
                AbstractCharBoss.boss.energy.energy=AbstractCharBoss.boss.energy.energyMaster;
                if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
                    PocketBook book = (PocketBook)AbstractDungeon.player.getRelic(PocketBook.ID);
                    this.maxHealth = (int)(this.maxHealth*book.healthRate);
                }
                this.halfDead = false;
                if (Settings.isEndless && AbstractDungeon.player.hasBlight("ToughEnemies")) {
                    float mod = AbstractDungeon.player.getBlight("ToughEnemies").effectFloat();
                    this.maxHealth = (int)(this.maxHealth * mod);
                }
                if (ModHelper.isModEnabled("MonsterHunter"))
                    this.currentHealth = (int)(this.currentHealth * 1.5F);
                AbstractGameEffect effect =  new ChangeScene(ImageMaster.loadImage(String.format(bgFormat,this.idolName,stage+1)));
                AbstractDungeon.effectList.add(new LatterEffect(() -> {
                    AbstractDungeon.effectsQueue.add(effect);
                }));
                AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, this.maxHealth));
                setMove((byte) 0, Intent.NONE);
                CardCrawlGame.music.dispose();
                CardCrawlGame.music.unsilenceBGM();
                AbstractDungeon.scene.fadeOutAmbiance();
                CardCrawlGame.music.playTempBgmInstantly(String.format("gkmasModResource/audio/song/%s_%s.ogg", this.idolName, IdolData.getIdol(this.idolName).getBossSong(stage),true));
                break;
        }
    }

    @Override
    public void onPlayAttackCardSound() {

        switch (MathUtils.random(2)) {
        }
    }

    public void damage(DamageInfo info) {
        super.damage(info);

        float healthRatio = (float)this.currentHealth / (float)this.maxHealth;
        if(healthRatio<=0.5F&&changeSong){
            if(this.shroFlag&&this.idolName.equals(IdolData.shro)&&this.stage==1){
                this.shroFlag=false;
                changeSong = false;
            }
            else{
                this.changeSong=false;
                CardCrawlGame.music.dispose();
                CardCrawlGame.music.playTempBgmInstantly(
                        String.format("gkmasModResource/audio/song/%s_%s.ogg",SkinSelectScreen.Inst.idolName,
                                IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getBossSong(stage)),
                        true);
            }
        }

        if(AbstractCharBoss.boss!=null&&AbstractCharBoss.boss.hasRelic(CBR_LizardTail.ID)&&this.LizardTailActive){
            this.LizardTailActive=false;
            return;
        }

        if (stage<maxStage-1&&this.currentHealth <= 0 && !this.halfDead) {
            if ((AbstractDungeon.getCurrRoom()).cannotLose == true)
                this.halfDead = true;
            for (AbstractCharbossRelic r : this.relics) {
                r.onUnequip();
            }
            relics.clear();
            hand.clear();
            limbo.clear();
            stance.onExitStance();
            stance = AbstractEnemyStance.getStanceFromName("Neutral");
            stance.onEnterStance();
            for (AbstractPower p : this.powers)
                p.onDeath();
            for (AbstractRelic r : AbstractDungeon.player.relics)
                r.onMonsterDeath(this);
            addToTop(new ClearCardQueueAction());
            this.powers.clear();
//            deathVoice();
            stage++;
            rebirth=true;
            CardCrawlGame.music.dispose();
            CardCrawlGame.music.playTempBgmInstantly("gkmasModResource/audio/bgm/gasha_002.ogg",true);
//            AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX - 50, this.hb.cY + this.dialogY + 50, 5.0F, DIALOG[0], false));
            setMove((byte) 0, AbstractMonster.Intent.UNKNOWN);
            createIntent();
            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)0, AbstractMonster.Intent.UNKNOWN));

            applyPowers();
        }
        else if(this.currentHealth <= 0){

            die();
        }
    }

    @Override
    public void die() {
        if (!(AbstractDungeon.getCurrRoom()).cannotLose) {
            super.die();
            System.out.println("boss die");
            if(AbstractDungeon.getCurrRoom() instanceof GkmasBossRoom){
                System.out.println("boss die");
                onBossVictoryLogic();
                onFinalBossVictoryLogic();
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);

    }

    public int getGold(){
        return gold;
    }

    public void addGold(int gold){
        this.gold+=gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}


