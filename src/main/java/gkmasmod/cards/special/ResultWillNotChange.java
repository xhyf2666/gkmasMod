package gkmasmod.cards.special;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.cards.GkmasCard;
import gkmasmod.growEffect.BlockGrow;
import gkmasmod.powers.NotGoodTune;
import gkmasmod.utils.NameHelper;

public class ResultWillNotChange extends GkmasCard {
    private static final String CLASSNAME = ResultWillNotChange.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = -2;
    private static final int BASE_BLOCK = 3;

    private static final CardType TYPE = CardType.CURSE;
    private static final CardColor COLOR = CardColor.CURSE;
    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ResultWillNotChange() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BASE_BLOCK;
        this.cardHeader = CardCrawlGame.languagePack.getUIString("gkmasMod:ResultWillNotChangeHeader").TEXT[0];
    }

    @Override
    public void upgrade() {

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.dontTriggerOnUseCard){
            addToBot(new MakeTempCardInDrawPileAction(this,1,true,true));
        }
        else{
            addToBot(new GainBlockAction(p, p, this.block));
        }
    }

    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }

    @Override
    public void triggerWhenDrawn() {
        int count = this.baseBlock;
        for (AbstractCardModifier mod : CardModifierManager.modifiers(this)) {
            if(mod instanceof BlockGrow)
                count += ((BlockGrow)mod).getAmount();
        }
        addToBot(new GainBlockWithPowerAction(AbstractDungeon.player, AbstractDungeon.player, count));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ResultWillNotChange();
    }

}
