package gkmasmod.cards.anomaly;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.FullPowerValue;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.ImageHelper;
import gkmasmod.utils.NameHelper;

public class Endurance extends GkmasCard {
    private static final String CLASSNAME = Endurance.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static String IMG_PATH = ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME);

    private static final int COST = 1;

    private static final int BASE_BLOCK = 4;
    private static final int BASE_MAGIC = 2;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorAnomaly;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Endurance() {
        super(ID, NAME, ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        IMG_PATH = ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME);
        this.updateShowImg = true;
        this.baseBlock = BASE_BLOCK;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(GkmasCardTag.FULL_POWER_TAG);
        this.tags.add(GkmasCardTag.PRESERVATION_TAG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,p,this.block));
        addToBot(new ApplyPowerAction(p,p,new FullPowerValue(p,this.magicNumber),this.magicNumber));
        if(this.upgraded){
            addToBot(new ChangeStanceAction(PreservationStance.STANCE_ID2));
        }
        else{
            addToBot(new ChangeStanceAction(PreservationStance.STANCE_ID));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Endurance();
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
