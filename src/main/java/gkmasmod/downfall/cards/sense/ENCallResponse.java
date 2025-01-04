package gkmasmod.downfall.cards.sense;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.utils.ImageHelper;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

import java.util.ArrayList;
import java.util.Iterator;

public class ENCallResponse extends GkmasBossCard {
    private static final String CLASSNAME = ENCallResponse.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENCallResponse.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static String IMG_PATH = ImageHelper.idolImgPath(AbstractCharBoss.theIdolName, CLASSNAME2);

    private static final int COST = 2;

    private static final int ATTACK_DMG = 10;
    private static final int ATTACK_DMG2 = 7;
    private static final int UPGRADE_PLUS_DMG2 = 5;
    private static final int BASE_MAGIC = 2;
    private static final int BASE_MAGIC2 = 150;


    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorSense;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public ENCallResponse() {
        super(ID, NAME, ImageHelper.idolImgPath(AbstractCharBoss.theIdolName, CLASSNAME2), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        IMG_PATH = ImageHelper.idolImgPath(AbstractCharBoss.theIdolName, CLASSNAME2);
        this.updateShowImg = true;
        updateImg();
        this.baseDamage = ATTACK_DMG;
        this.baseSecondDamage = ATTACK_DMG2;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.intent = AbstractMonster.Intent.ATTACK;
        this.exhaust = true;
        this.tags.add(GkmasCardTag.FOCUS_TAG);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        int count = PlayerHelper.getPowerAmount(m, StrengthPower.POWER_ID);
        if(count > this.magicNumber){
            addToBot(new DamageAction(p, new DamageInfo(m, this.secondDamage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }

    public void applyPowers() {
        this.applyPowersToBlock();
        AbstractCharBoss player = AbstractCharBoss.boss;
        this.isDamageModified = false;
        if (!this.isMultiDamage) {
            float tmp = (float)this.baseDamage;
            Iterator var3 = player.relics.iterator();

            while(var3.hasNext()) {
                AbstractRelic r = (AbstractRelic)var3.next();
                tmp = r.atDamageModify(tmp, this);
                if (this.baseDamage != (int)tmp) {
                    this.isDamageModified = true;
                }
            }

            AbstractPower p;
            for(var3 = player.powers.iterator(); var3.hasNext(); tmp = p.atDamageGive(tmp, this.damageTypeForTurn, this)) {
                p = (AbstractPower)var3.next();
            }

            tmp = player.stance.atDamageGive(tmp, this.damageTypeForTurn, this);
            if (this.baseDamage != (int)tmp) {
                this.isDamageModified = true;
            }

            for(var3 = player.powers.iterator(); var3.hasNext(); tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this)) {
                p = (AbstractPower)var3.next();
            }

            if (tmp < 0.0F) {
                tmp = 0.0F;
            }

            if (this.baseDamage != MathUtils.floor(tmp)) {
                this.isDamageModified = true;
            }

            this.damage = MathUtils.floor(tmp);
        }

        this.isSecondDamageModified = false;
        if (!this.isMultiDamage) {
            float tmp = (float)this.baseSecondDamage;
            int count = PlayerHelper.getPowerAmount(AbstractCharBoss.boss, StrengthPower.POWER_ID);
            if(this.upgraded&&count > this.magicNumber){
                int damageAppend = (int) (1.0F *(this.secondMagicNumber/100.0F - 1) * count);
                tmp += damageAppend;
            }
            Iterator var3 = player.relics.iterator();

            while(var3.hasNext()) {
                AbstractRelic r = (AbstractRelic)var3.next();
                tmp = r.atDamageModify(tmp, this);
                if (this.baseSecondDamage != (int)tmp) {
                    this.isSecondDamageModified = true;
                }
            }

            AbstractPower p;
            for(var3 = player.powers.iterator(); var3.hasNext(); tmp = p.atDamageGive(tmp, this.damageTypeForTurn, this)) {
                p = (AbstractPower)var3.next();
            }

            tmp = player.stance.atDamageGive(tmp, this.damageTypeForTurn, this);
            if (this.baseSecondDamage != (int)tmp) {
                this.isSecondDamageModified = true;
            }

            for(var3 = player.powers.iterator(); var3.hasNext(); tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this)) {
                p = (AbstractPower)var3.next();
            }

            if (tmp < 0.0F) {
                tmp = 0.0F;
            }

            if (this.baseSecondDamage != MathUtils.floor(tmp)) {
                this.isSecondDamageModified = true;
            }

            this.secondDamage = MathUtils.floor(tmp);
        }

    }

    public void calculateCardDamage(AbstractMonster mo) {
        this.applyPowersToBlock();
        AbstractCharBoss player = AbstractCharBoss.boss;
        // TODO 需要重写
        this.isDamageModified = false;
        if (!this.isMultiDamage && mo != null) {
            float tmp = (float)this.baseDamage;
            Iterator var9 = player.relics.iterator();

            while(var9.hasNext()) {
                AbstractRelic r = (AbstractRelic)var9.next();
                tmp = r.atDamageModify(tmp, this);
                if (this.baseDamage != (int)tmp) {
                    this.isDamageModified = true;
                }
            }

            AbstractPower p;
            for(var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageGive(tmp, this.damageTypeForTurn, this)) {
                p = (AbstractPower)var9.next();
            }

            tmp = player.stance.atDamageGive(tmp, this.damageTypeForTurn, this);
            if (this.baseDamage != (int)tmp) {
                this.isDamageModified = true;
            }

            for(var9 = mo.powers.iterator(); var9.hasNext(); tmp = p.atDamageReceive(tmp, this.damageTypeForTurn, this)) {
                p = (AbstractPower)var9.next();
            }

            for(var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this)) {
                p = (AbstractPower)var9.next();
            }

            for(var9 = mo.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalReceive(tmp, this.damageTypeForTurn, this)) {
                p = (AbstractPower)var9.next();
            }

            if (tmp < 0.0F) {
                tmp = 0.0F;
            }

            if (this.baseDamage != MathUtils.floor(tmp)) {
                this.isDamageModified = true;
            }

            this.damage = MathUtils.floor(tmp);
        } else {
            ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;
            float[] tmp = new float[m.size()];

            int i;
            for(i = 0; i < tmp.length; ++i) {
                tmp[i] = (float)this.baseDamage;
            }

            Iterator var6;
            AbstractPower p;
            for(i = 0; i < tmp.length; ++i) {
                var6 = player.relics.iterator();

                while(var6.hasNext()) {
                    AbstractRelic r = (AbstractRelic)var6.next();
                    tmp[i] = r.atDamageModify(tmp[i], this);
                    if (this.baseDamage != (int)tmp[i]) {
                        this.isDamageModified = true;
                    }
                }

                for(var6 = player.powers.iterator(); var6.hasNext(); tmp[i] = p.atDamageGive(tmp[i], this.damageTypeForTurn, this)) {
                    p = (AbstractPower)var6.next();
                }

                tmp[i] = player.stance.atDamageGive(tmp[i], this.damageTypeForTurn, this);
                if (this.baseDamage != (int)tmp[i]) {
                    this.isDamageModified = true;
                }
            }

            for(i = 0; i < tmp.length; ++i) {
                var6 = ((AbstractMonster)m.get(i)).powers.iterator();

                while(var6.hasNext()) {
                    p = (AbstractPower)var6.next();
                    if (!((AbstractMonster)m.get(i)).isDying && !((AbstractMonster)m.get(i)).isEscaping) {
                        tmp[i] = p.atDamageReceive(tmp[i], this.damageTypeForTurn, this);
                    }
                }
            }

            for(i = 0; i < tmp.length; ++i) {
                for(var6 = player.powers.iterator(); var6.hasNext(); tmp[i] = p.atDamageFinalGive(tmp[i], this.damageTypeForTurn, this)) {
                    p = (AbstractPower)var6.next();
                }
            }

            for(i = 0; i < tmp.length; ++i) {
                var6 = ((AbstractMonster)m.get(i)).powers.iterator();

                while(var6.hasNext()) {
                    p = (AbstractPower)var6.next();
                    if (!((AbstractMonster)m.get(i)).isDying && !((AbstractMonster)m.get(i)).isEscaping) {
                        tmp[i] = p.atDamageFinalReceive(tmp[i], this.damageTypeForTurn, this);
                    }
                }
            }

            for(i = 0; i < tmp.length; ++i) {
                if (tmp[i] < 0.0F) {
                    tmp[i] = 0.0F;
                }
            }

            this.multiDamage = new int[tmp.length];

            for(i = 0; i < tmp.length; ++i) {
                if (this.baseDamage != MathUtils.floor(tmp[i])) {
                    this.isDamageModified = true;
                }

                this.multiDamage[i] = MathUtils.floor(tmp[i]);
            }

            this.damage = this.multiDamage[0];
        }
        this.isSecondDamageModified = false;
        if (!this.isMultiDamage && mo != null) {
            float tmp = (float)this.baseSecondDamage;

            int count = PlayerHelper.getPowerAmount(AbstractDungeon.player, StrengthPower.POWER_ID);
            if(this.upgraded&&count > this.magicNumber){
                int damageAppend = (int) (1.0F *(this.secondMagicNumber/100.0F - 1) * count);
                tmp += damageAppend;
            }

            Iterator var9 = player.relics.iterator();

            while(var9.hasNext()) {
                AbstractRelic r = (AbstractRelic)var9.next();
                tmp = r.atDamageModify(tmp, this);
                if (this.baseSecondDamage != (int)tmp) {
                    this.isSecondDamageModified = true;
                }
            }

            AbstractPower p;
            for(var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageGive(tmp, this.damageTypeForTurn, this)) {
                p = (AbstractPower)var9.next();
            }

            tmp = player.stance.atDamageGive(tmp, this.damageTypeForTurn, this);
            if (this.baseSecondDamage != (int)tmp) {
                this.isSecondDamageModified = true;
            }

            for(var9 = mo.powers.iterator(); var9.hasNext(); tmp = p.atDamageReceive(tmp, this.damageTypeForTurn, this)) {
                p = (AbstractPower)var9.next();
            }

            for(var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this)) {
                p = (AbstractPower)var9.next();
            }

            for(var9 = mo.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalReceive(tmp, this.damageTypeForTurn, this)) {
                p = (AbstractPower)var9.next();
            }

            if (tmp < 0.0F) {
                tmp = 0.0F;
            }

            if (this.baseSecondDamage != MathUtils.floor(tmp)) {
                this.isSecondDamageModified = true;
            }

            this.secondDamage = MathUtils.floor(tmp);
        }

    }



    @Override
    public AbstractCard makeCopy() {
        return new ENCallResponse();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeSecondDamage(UPGRADE_PLUS_DMG2);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
