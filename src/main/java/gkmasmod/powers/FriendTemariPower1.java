package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.ModifyDamageAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.utils.NameHelper;

public class FriendTemariPower1 extends AbstractPower {
    private static final String CLASSNAME = FriendTemariPower1.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png","CareCardSPPower");
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png","CareCardSPPower");


    private static final int healthRequire = 3;
    private static final int healthReduce = 1;
    private static final int baseDamage = 3;
    private static final int healthRecover = 50;

    public FriendTemariPower1(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],healthRequire,healthReduce,baseDamage,healthRequire,healthRecover);
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if(card instanceof AbstractBossCard)
            return;
        if(card.type == AbstractCard.CardType.ATTACK&&this.owner.currentHealth>=healthRequire){
            this.flash();
            AbstractCreature target = action.target;
            addToBot(new DamageAction(this.owner, new DamageInfo(this.owner, healthReduce)));
            if(target==null||target.isDeadOrEscaped()){
                target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            }
            addToBot(new ModifyDamageAction(target, new DamageInfo(this.owner, baseDamage, action.damageType), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(this.owner.currentHealth<healthRequire){
            int recover = (int) (1.0f*this.owner.maxHealth*healthRecover/100);
            addToBot(new HealAction(this.owner, this.owner, recover));
        }
    }
}
