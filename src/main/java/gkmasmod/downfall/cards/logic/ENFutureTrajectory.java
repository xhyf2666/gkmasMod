package gkmasmod.downfall.cards.logic;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.monster.LittleGundam;
import gkmasmod.powers.SteelSoul;
import gkmasmod.utils.NameHelper;

public class ENFutureTrajectory extends GkmasBossCard {
    private static final String CLASSNAME = ENFutureTrajectory.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENFutureTrajectory.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME2);

    private static final int COST = 0;
    private static final int BASE_MAGIC = 1;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ENFutureTrajectory() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.selfRetain = true;
        this.exhaust = true;
        this.intent = AbstractMonster.Intent.MAGIC;
        this.tags.add(GkmasCardTag.OUTSIDE_TAG);
        this.cardHeader = CardCrawlGame.languagePack.getUIString("gkmasMod:FutureTrajectory").TEXT[0];
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = 0;
        for(AbstractMonster monster: AbstractDungeon.getMonsters().monsters){
            if(monster.id.equals(LittleGundam.ID)){
                LittleGundam littleGundam = (LittleGundam) monster;
                if(!littleGundam.owner.isPlayer){
                    count += littleGundam.currentBlock;
                    littleGundam.currentBlock = 0;
                    littleGundam.hideHealthBar();
                    littleGundam.die();
                }
            }
        }
        if(count > 0){
            addToBot(new GainBlockAction(m, m, count));
        }
        addToBot(new RemoveSpecificPowerAction(m,m, SteelSoul.POWER_ID));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ENFutureTrajectory();
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
