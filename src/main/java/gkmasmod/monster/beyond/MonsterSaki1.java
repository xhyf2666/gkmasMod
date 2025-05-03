package gkmasmod.monster.beyond;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import com.megacrit.cardcrawl.vfx.combat.ViceCrushEffect;
import gkmasmod.monster.ChangeScene;
import gkmasmod.monster.LatterEffect;
import gkmasmod.patches.AbstractMonsterPatch;
import gkmasmod.powers.*;
import gkmasmod.relics.PocketBook;
import gkmasmod.utils.PlayerHelper;

public class MonsterSaki1 extends CustomMonster {
    public static final String ID = "MonsterSaki1";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:MonsterSaki1");

    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    public static final String[] DIALOG = monsterStrings.DIALOG;

    private static int MAX_HEALTH = 350000;

    private int bloodHitCount;

    private boolean isFirstMove = true;

    private int moveCount = 0, buffCount = 0;

    private int moneyAmt;

    private boolean isOutTriggered = false;

    private int stage = 0;

    private boolean flag = true;

    public MonsterSaki1() {
        this(000.0F, 0.0F);
    }

    public MonsterSaki1(float x, float y) {
        super(NAME, ID, MAX_HEALTH, -8.0F, 0.0F, 200.0F, 240.0F, null, x, y);
        this.img = new Texture("gkmasModResource/img/monsters/Idol/MonsterSaki1.png");
        this.type = EnemyType.BOSS;
        if (AbstractDungeon.ascensionLevel >= 10) {
            setHp(220);
        } else {
            setHp(170);
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 8));
            this.damage.add(new DamageInfo(this, 15));
            this.bloodHitCount = 2;
        } else {
            this.damage.add(new DamageInfo(this, 6));
            this.damage.add(new DamageInfo(this, 10));
            this.bloodHitCount = 2;
        }
    }

    public void usePreBattleAction() {
        (AbstractDungeon.getCurrRoom()).cannotLose = true;
        if (AbstractDungeon.ascensionLevel >= 5) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SakiCarePower(this,10),10));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SakiPracticePower(this,2),2));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SakiGoodTunePower(this,2),2));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SakiCarePower(this,5),5));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SakiPracticePower(this,1),1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SakiGoodTunePower(this,1),1));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SakiRebirthPower(this)));
    }

    public void takeTurn() {
        int i;
        switch (this.nextMove) {
            case 0:
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ViceCrushEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.4F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                        .get(1), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                break;
            case 1:
                for (AbstractMonster mo:AbstractDungeon.getCurrRoom().monsters.monsters){
                    if(!mo.isDeadOrEscaped()&& !AbstractMonsterPatch.friendlyField.friendly.get(mo)){
                        addToBot(new GainBlockAction(mo,this,500));
                        if(flag){
                            addToBot(new ApplyPowerAction(mo,this,new SSDSecretPower(mo,1),1));
                        }
                        addToBot(new ApplyPowerAction(mo,this,new GreatGoodTune(mo,3),3));
                    }
                }
                flag = false;
                break;
            case 8:
                AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_AWAKENEDONE_1"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new IntenseZoomEffect(this.hb.cX, this.hb.cY, true), 0.05F, true));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "REBIRTH"));
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void changeState(String key) {
        switch (key) {
            case "REBIRTH":
                this.maxHealth = 4000;
                this.stage =1;
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

                AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, this.maxHealth));
                AbstractDungeon.actionManager.addToBottom(new CanLoseAction());
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new GreatGoodTune(this, 5), 5));
                int count = PlayerHelper.getPowerAmount(this,GoodTune.POWER_ID);
                if(count>0){
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new GoodTune(this, count), count));
                }
                count = PlayerHelper.getPowerAmount(this,StrengthPower.POWER_ID);
                if(count>0){
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, count), count));
                }
                this.moveCount = 0;
                this.nextMove = 0;
                break;
        }
    }


    protected void getMove(int num) {
        if(this.stage==1){
            setMove((byte)0, Intent.ATTACK, (this.damage.get(1)).base);
            return;
        }
        switch (moveCount){
            case 0:
            case 2:
                setMove((byte)1, Intent.BUFF);
                break;
            case 1:
                setMove((byte)0, Intent.ATTACK, (this.damage.get(1)).base);

                break;
        }
        moveCount=(moveCount+1)%3;
    }

    public void die() {
        if (!(AbstractDungeon.getCurrRoom()).cannotLose) {
            super.die();
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                onBossVictoryLogic();
                AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX - 50, this.hb.cY + this.dialogY + 50, 2.0F, DIALOG[1], false));
            }
            else{
                AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX - 50, this.hb.cY + this.dialogY + 50, 2.0F, DIALOG[0], false));
            }
        }
        else{
            AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX - 50, this.hb.cY + this.dialogY + 50, 4.0F, DIALOG[2], false));
        }
    }

    @Override
    public void damage(DamageInfo info) {
        super.damage(info);
        if (this.currentHealth <= 0 && !this.halfDead) {
            this.halfDead = true;
            this.currentHealth = 1;
            setMove((byte)8, Intent.UNKNOWN);
            createIntent();
            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)8, Intent.UNKNOWN));
            addToBot(new RemoveSpecificPowerAction(this,this,SakiRebirthPower.POWER_ID));
            addToBot(new RemoveSpecificPowerAction(this,this,GoodImpression.POWER_ID));
        }
    }
}
