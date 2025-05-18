package gkmasmod.downfall.cards.anomaly;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.downfall.cards.free.ENBasePose;
import gkmasmod.downfall.charbosses.actions.common.EnemyMakeTempCardInHandAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.cardGrowEffect.AbstractGrowEffect;
import gkmasmod.cardGrowEffect.CanNotPlayGrow;
import gkmasmod.utils.GrowHelper;
import gkmasmod.utils.NameHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ENProducingIsChallenging extends GkmasBossCard {
    private static final String CLASSNAME = ENProducingIsChallenging.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENProducingIsChallenging.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME2);

    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorAnomaly;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ENProducingIsChallenging() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.intent = AbstractMonster.Intent.MAGIC;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard c1=null;
        AbstractCard c2=null;
        for(AbstractCard card : AbstractCharBoss.boss.hand.group){
            if(card instanceof ENTakeFlight)
                c1 = card;
            if(card instanceof ENBasePose)
                c2 = card;
        }
        if(c1 == null || c2 == null){
            return;
        }
        ArrayList<AbstractCardModifier> c1mods = new ArrayList<>();
        ArrayList<AbstractCardModifier> c2mods = new ArrayList<>();
        for(AbstractCardModifier mod : CardModifierManager.modifiers(c1)){
            if(mod instanceof AbstractGrowEffect){
                c1mods.add(mod.makeCopy());
            }
        }
        for(AbstractCardModifier mod : CardModifierManager.modifiers(c2)){
            if(mod instanceof AbstractGrowEffect){
                c2mods.add(mod.makeCopy());
            }
        }
        List<AbstractCardModifier> modifiersC1 = CardModifierManager.modifiers(c1);
        if (modifiersC1 != null) {
            Iterator<AbstractCardModifier> iteratorC1 = modifiersC1.iterator();
            while (iteratorC1.hasNext()) {
                AbstractCardModifier mod = iteratorC1.next();
                if (mod instanceof AbstractGrowEffect) {
                    iteratorC1.remove();  // 使用 Iterator 的 remove 方法安全地移除元素
                }
            }
        }

        for(AbstractCardModifier mod : c1mods){
            AbstractGrowEffect effect = (AbstractGrowEffect)mod;
            GrowHelper.grow(c2,effect.growEffectID,effect.amount);
        }
        CardModifierManager.addModifier(c1,new CanNotPlayGrow());
        AbstractCharBoss.boss.hand.removeCard(ENTakeFlight.ID);
        AbstractCharBoss.boss.hand.removeCard(ENBasePose.ID);
        addToBot(new EnemyMakeTempCardInHandAction(c1));
        addToBot(new EnemyMakeTempCardInHandAction(c2));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ENProducingIsChallenging();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
