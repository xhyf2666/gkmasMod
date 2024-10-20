package gkmasmod.potion;

import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.AojiruAction;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.actions.GoodImpressionDamageAction;
import gkmasmod.cards.free.BasePerform;
import gkmasmod.cards.logic.GoodMorning;
import gkmasmod.utils.NameHelper;

import java.util.Iterator;

public class StylishHerbalTea extends CustomPotion {
    private static final String CLASSNAME = StylishHerbalTea.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);

    private static final PotionRarity rarity = PotionRarity.UNCOMMON;

    private static final PotionSize size = PotionSize.M;

    private static final PotionColor color = PotionColor.BLUE;

    public static Color liquidColor = new Color(1.0F, 0.003921569F, 0.07450981F, 1.0F);

    public static Color hybridColor = new Color(0.99215686F, 0.007843138F, 0.08627451F, 1.0F);

    public static Color spotsColor = new Color(0.99607843F, 0.003921569F, 0.07450981F, 1.0F);

    private static final int magic = 100;

    private static final String IMG = String.format("gkmasModResource/img/potions/%s.png",CLASSNAME);

    private static final Texture texture = ImageMaster.loadImage(IMG);


    public StylishHerbalTea() {
        super(potionStrings.NAME, ID, rarity, size, color);
        this.isThrown = true;
        this.targetRequired = true;
    }

    @Override
    public void use(AbstractCreature target) {
        int amount = getPotency();
        addToBot(new GainBlockWithPowerAction(AbstractDungeon.player, AbstractDungeon.player, amount));
        addToBot(new GoodImpressionDamageAction(1.0F*magic/100,0,AbstractDungeon.player, (AbstractMonster) target));
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("SacredBark")) {
            addToBot(new GoodImpressionDamageAction(1.0F*magic/100,0,AbstractDungeon.player, (AbstractMonster) target));
        }
    }

    public void initializeData() {
        this.potency = getPotency();
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("SacredBark")) {
            this.description = String.format(potionStrings.DESCRIPTIONS[0], potency,100,2);
        }
        else{
            this.description = String.format(potionStrings.DESCRIPTIONS[0], potency,100,1);
        }
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }



    public void render(SpriteBatch sb) {
        sb.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        sb.draw(texture, this.posX - 32.0F, this.posY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, 0.0F, 0, 0, 64, 64, false, false);
    }

    @Override
    public int getPotency(int i) {
        return 3;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new StylishHerbalTea();
    }

    public void shopRender(SpriteBatch sb) {
        generateSparkles(0.0F, 0.0F, false);
        if (this.hb.hovered) {
            TipHelper.queuePowerTips(InputHelper.mX + 50.0F * Settings.scale, InputHelper.mY + 50.0F * Settings.scale, this.tips);
            this.scale = 1.5F * Settings.scale;
        } else {
            this.scale = MathHelper.scaleLerpSnap(this.scale, 1.2F * Settings.scale);
        }
        render(sb);
        if (this.hb != null)
            this.hb.render(sb);
    }

    public void labRender(SpriteBatch sb) {
        render(sb);
        if (this.hb.hovered) {
            TipHelper.queuePowerTips(150.0F * Settings.scale, 800.0F * Settings.scale, this.tips);
            this.scale = 1.5F * Settings.scale;
        } else {
            this.scale = MathHelper.scaleLerpSnap(this.scale, 1.2F * Settings.scale);
        }
        if (this.hb != null)
            this.hb.render(sb);
    }


    public void renderOutline(SpriteBatch sb, Color c) {}

    public void renderOutline(SpriteBatch sb) {}

    public void renderLightOutline(SpriteBatch sb) {}

    public void renderShiny(SpriteBatch sb) {}
}
