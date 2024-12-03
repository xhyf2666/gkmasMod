package gkmasmod.cards.logic;

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
import gkmasmod.monster.LittleGundam;
import gkmasmod.powers.SteelSoul;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;

public class FutureTrajectory extends GkmasCard {
    private static final String CLASSNAME = FutureTrajectory.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 0;
    private static final int BASE_MAGIC = 1;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    public FutureTrajectory() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.selfRetain = true;
        this.exhaust = true;
        this.tags.add(GkmasCardTag.OUTSIDE_TAG);
        this.backGroundColor = IdolData.kllj;
        updateBackgroundImg();
        this.cardHeader = CardCrawlGame.languagePack.getUIString("gkmasMod:FutureTrajectory").TEXT[0];
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = 0;
        for(AbstractMonster monster: AbstractDungeon.getMonsters().monsters){
            if(monster.id.equals(LittleGundam.ID)){
                LittleGundam littleGundam = (LittleGundam) monster;
                if(littleGundam.owner.isPlayer){
                    count += littleGundam.currentBlock;
                    littleGundam.currentBlock = 0;
                    littleGundam.hideHealthBar();
                    littleGundam.die();
                }
            }
        }
        if(count > 0){
            addToBot(new GainBlockAction(p, p, count));
        }
        addToBot(new RemoveSpecificPowerAction(p,p, SteelSoul.POWER_ID));
    }

    @Override
    public AbstractCard makeCopy() {
        return new FutureTrajectory();
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
