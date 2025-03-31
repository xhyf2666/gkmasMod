package gkmasmod.potion;

import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
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
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import gkmasmod.actions.SelectCardFromDiscardAndDrawAction;
import gkmasmod.powers.AnotherTurnPower;
import gkmasmod.powers.DoubleDamageReceive;
import gkmasmod.powers.HeartAndSoulPower;
import gkmasmod.utils.NameHelper;

import java.util.Iterator;

public class FirstStarBlackVinegar extends CustomPotion {
    private static final String CLASSNAME = FirstStarBlackVinegar.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);

    private static final PotionRarity rarity = PotionRarity.RARE;

    private static final PotionSize size = PotionSize.M;

    private static final PotionColor color = PotionColor.BLUE;

    public static Color liquidColor = new Color(1.0F, 0.003921569F, 0.07450981F, 1.0F);

    public static Color hybridColor = new Color(0.99215686F, 0.007843138F, 0.08627451F, 1.0F);

    public static Color spotsColor = new Color(0.99607843F, 0.003921569F, 0.07450981F, 1.0F);

    private static final String IMG = String.format("gkmasModResource/img/potions/%s.png",CLASSNAME);

    private static final Texture texture = ImageMaster.loadImage(IMG);

    private static final int HP_LOST = 2;
    private static final int MAGIC = 1;


    public FirstStarBlackVinegar() {
        super(potionStrings.NAME, ID, rarity, size, color);
        this.isThrown = false;
    }

    @Override
    public void use(AbstractCreature target) {
        addToBot(new VFXAction(new WhirlwindEffect(new Color(1.0F, 0.9F, 0.4F, 1.0F), true)));
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("SacredBark")) {
            addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, HP_LOST*2));
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new DoubleDamageReceive(AbstractDungeon.player,MAGIC&2),MAGIC*2));
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new HeartAndSoulPower(AbstractDungeon.player,4),4));
            addToBot(new SelectCardFromDiscardAndDrawAction(2));
        }
        else{
            addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, HP_LOST));
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new DoubleDamageReceive(AbstractDungeon.player,MAGIC),MAGIC));
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new HeartAndSoulPower(AbstractDungeon.player,2),2));
            addToBot(new SelectCardFromDiscardAndDrawAction(1));
        }
    }

    public void initializeData() {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("SacredBark")) {
            this.description = String.format(potionStrings.DESCRIPTIONS[0],HP_LOST*2,MAGIC*2,2,4);
        }
        else{
            this.description = String.format(potionStrings.DESCRIPTIONS[0],HP_LOST,MAGIC,1,2);
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
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new FirstStarBlackVinegar();
    }

    public int calculateDamage(int baseDamage, AbstractCreature m){
        AbstractPlayer player = AbstractDungeon.player;
        if (m != null) {
            float tmp = (float)baseDamage;
            Iterator var9;

            AbstractPower p;
            for(var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageGive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            tmp = player.stance.atDamageGive(tmp, DamageInfo.DamageType.NORMAL);

            for(var9 = m.powers.iterator(); var9.hasNext(); tmp = p.atDamageReceive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            for(var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalGive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            for(var9 = m.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalReceive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            if (tmp < 0.0F) {
                tmp = 0.0F;
            }

            return MathUtils.floor(tmp);
        }
        return baseDamage;
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
