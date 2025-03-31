package gkmasmod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import gkmasmod.actions.ModifyDamageAction;
import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.utils.NameHelper;

public class FriendKnifePower extends AbstractPower {
    private static final String CLASSNAME = FriendKnifePower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final int healthRequire = 1;
    private static final int healthReduce = 1;
    private static final int baseDamage = 1;
    private static final int healthRecover = 50;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public FriendKnifePower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        loadRegion("infiniteBlades");

//        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
//        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],healthReduce,baseDamage,1);
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if(card instanceof AbstractBossCard)
            return;
        if(card.type == AbstractCard.CardType.ATTACK&&this.owner.currentHealth>=healthRequire){
            this.flash();
            AbstractCreature target = action.target;
            addToBot(new DamageAction(this.owner, new DamageInfo(this.owner, healthReduce)));
            if(target==null)
                return;
            if(target.isDeadOrEscaped()){
                target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            }
            addToBot(new DamageAction(target, new DamageInfo(this.owner, baseDamage, action.damageType), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            addToBot(new ApplyPowerAction(target, target, new VulnerablePower(target,1,false), 1));
        }
    }

}
