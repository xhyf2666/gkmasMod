package gkmasmod.cards.free;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.TriggerMarksAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;
import com.megacrit.cardcrawl.vfx.combat.PressurePointEffect;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;

public class DefensiveSkills extends GkmasCard {
    private static final String CLASSNAME = DefensiveSkills.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 0;

    private static final int magic = 80;
    private static final int UPGRADE_MAGIC_PLUS = 20;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColor;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;


    public DefensiveSkills() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.baseMagicNumber = this.magic;
        this.magicNumber = this.baseMagicNumber;
        this.backGroundColor = IdolData.amao;
        updateBackgroundImg();
        this.exhaust = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = 0;
        for(AbstractMonster mo: AbstractDungeon.getMonsters().monsters){
            if(!mo.isDeadOrEscaped()&&mo.hasPower(MarkPower.POWER_ID)){
                count+=mo.getPower(MarkPower.POWER_ID).amount;
            }
        }
        int temp = (int) (count*this.magicNumber*1.0F/100);
        if(temp>0){
            addToBot(new GainBlockWithPowerAction(p,p,temp));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new DefensiveSkills();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_PLUS);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
