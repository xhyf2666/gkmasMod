package gkmasmod.cards.free;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;
import com.megacrit.cardcrawl.vfx.combat.PressurePointEffect;
import gkmasmod.actions.MyTriggerMarksAction;
import gkmasmod.cards.GkmasCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.relics.*;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;

public class TriggerRelic extends GkmasCard {
    private static final String CLASSNAME = TriggerRelic.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 0;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColor;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;


    public TriggerRelic() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.exhaust = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p.hasRelic(SidewalkResearchNotes.ID)){
            ((SidewalkResearchNotes)p.getRelic(SidewalkResearchNotes.ID)).onTrainRoundRemove();
        }
        if(p.hasRelic(LifeSizeLadyLip.ID)){
            ((LifeSizeLadyLip)p.getRelic(LifeSizeLadyLip.ID)).onTrainRoundRemove();
        }
        if(p.hasRelic(UltimateSleepMask.ID)){
            ((UltimateSleepMask)p.getRelic(UltimateSleepMask.ID)).onTrainRoundRemove();
        }
        if(p.hasRelic(HeartFlutteringCup.ID)){
            ((HeartFlutteringCup)p.getRelic(HeartFlutteringCup.ID)).onTrainRoundRemove();
        }
        if(p.hasRelic(FirstHeartProofChina.ID)){
            ((FirstHeartProofChina)p.getRelic(FirstHeartProofChina.ID)).onTrainRoundRemove();
        }
        if(p.hasRelic(ChristmasLion.ID)){
            ((ChristmasLion)p.getRelic(ChristmasLion.ID)).onTrainRoundRemove();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new TriggerRelic();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
