package gkmasmod.powers;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.ForShiningYouDamageAction;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.cards.logic.ForShiningYou;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

public class MoonlitRunwayPlusPower extends AbstractPower {
    private static final String CLASSNAME = MoonlitRunwayPlusPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final float rate = 0.5F;

    private boolean useCardFinished = false;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public MoonlitRunwayPlusPower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = Amount;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        useCardFinished = false;
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(!(this.owner instanceof AbstractCharBoss)&&card instanceof AbstractBossCard)
            return;
        if(this.owner instanceof AbstractCharBoss&&(!(card instanceof AbstractBossCard)))
            return;
        if(useCardFinished) {
            return;
        }
        if(card.tags.contains(GkmasCardTag.GOOD_IMPRESSION_TAG)) {
            int count = PlayerHelper.getPowerAmount(this.owner, GoodImpression.POWER_ID);
            int damage_ = (int) (1.0F*count * rate);
            for(int i = 0; i < this.amount; i++) {
                addToBot(new ForShiningYouDamageAction(new DamageInfo(this.owner, damage_, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.POISON, new ForShiningYou()));
            }
            useCardFinished = true;
        }
    }
}
