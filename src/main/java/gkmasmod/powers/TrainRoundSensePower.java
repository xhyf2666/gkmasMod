package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.MinionPower;
import gkmasmod.relics.PocketBook;
import gkmasmod.utils.NameHelper;

import java.util.ArrayList;

public class TrainRoundSensePower extends AbstractPower {
    private static final String CLASSNAME = TrainRoundSensePower.class.getSimpleName();
    // 能力的ID
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    String path128 = String.format("img/powers/%s_84.png",CLASSNAME);;
    String path48 = String.format("img/powers/%s_32.png",CLASSNAME);;

    public TrainRoundSensePower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        this.amount = Amount;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount == 0)
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount,20);
    }

    public void atEndOfTurn(boolean isPlayer) {
        flash();

        if(this.amount > 0){
            addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        }
        else
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        flash();
        addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
    }

    public void onVictory() {
        ArrayList<Float> res = new ArrayList<>();
        ArrayList<AbstractMonster.EnemyType> types = new ArrayList<>();

        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            String monsterID = monster.id;
            String monsterName = monster.name;
            AbstractMonster.EnemyType type_ = monster.type;
            int currentHP = monster.currentHealth;
            int maxHP = monster.maxHealth;
            if(monster.hasPower(MinionPower.POWER_ID))
                continue;
            float rate = 1.0F - 1.0F *(currentHP) / maxHP;
            res.add(rate);
            types.add(type_);
            System.out.println("Monster ID: " + monsterID);
            System.out.println("Monster Name: " + monsterName);
            System.out.println("Current HP: " + currentHP);
            System.out.println("Max HP: " + maxHP);
            System.out.println("type: " + type_);
        }
        addThreeSize(res,types);
    }

    public void atStartOfTurn() {
    }

    public void onRemove() {
        ArrayList<Float> res = new ArrayList<>();
        ArrayList<AbstractMonster.EnemyType> types = new ArrayList<>();

        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            String monsterID = monster.id;
            String monsterName = monster.name;
            AbstractMonster.EnemyType type_ = monster.type;
            int currentHP = monster.currentHealth;
            int maxHP = monster.maxHealth;
            if(monster.hasPower(MinionPower.POWER_ID))
                continue;
            float rate = 1.0F - 1.0F *(currentHP) / maxHP;
            res.add(rate);
            types.add(type_);
            System.out.println("Monster ID: " + monsterID);
            System.out.println("Monster Name: " + monsterName);
            System.out.println("Current HP: " + currentHP);
            System.out.println("Max HP: " + maxHP);
            System.out.println("type: " + type_);
        }
        addThreeSize(res,types);

    }


    public void onInitialApplication() {
    }

    public void addThreeSize(ArrayList<Float> res,ArrayList<AbstractMonster.EnemyType> types){
        int monsterSize = res.size();
        ArrayList<Integer> scores = getThreeSizeAppend(types);
        int totolScore = 0;
        for (int i = 0; i < monsterSize; i++) {
            totolScore += (int)(scores.get(i) * res.get(i));
        }
        System.out.println("totolScore: " + totolScore);
        PocketBook.changeVo(totolScore/3);
        PocketBook.changeDa(totolScore/3);
        PocketBook.changeVi(totolScore/3);
    }

    public ArrayList<Integer> getThreeSizeAppend(ArrayList<AbstractMonster.EnemyType> types){
        ArrayList<Integer> scores = new ArrayList<>();
        int actNum = AbstractDungeon.actNum;
        int floorNum = AbstractDungeon.floorNum;

        for (int i = 0; i < types.size(); i++) {
            AbstractMonster.EnemyType type = types.get(i);
            int base = 20;
            if (actNum == 2)
                base += 20;
            else if (actNum == 3)
                base += 30;
            else if (actNum == 4)
                base += 40;

            if (actNum < 4 && floorNum%17 > 8)
                base += 10;

            if(type==AbstractMonster.EnemyType.ELITE)
                base = (int)(base * 1.5F);
            else if(type==AbstractMonster.EnemyType.BOSS)
                base = (int)(base * 2.0F);
            scores.add(base);
        }
        return scores;


    }


}
