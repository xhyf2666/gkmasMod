package gkmasmod.cards.hmsz;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.cards.free.Sleepy;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.monster.friend.FriendTemari;
import gkmasmod.powers.WakeUpMisuzuPower;
import gkmasmod.powers.WinterSleepyPower;
import gkmasmod.stances.WakeStance;
import gkmasmod.utils.ImageHelper;
import gkmasmod.utils.NameHelper;

public class WakeUpMisuzu extends GkmasCard {
    private static final String CLASSNAME = WakeUpMisuzu.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 1;


    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorMisuzu;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public WakeUpMisuzu() {
        super(ID, NAME, ImageHelper.getCardImgPath(CLASSNAME,TYPE), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(GkmasCardTag.ONLY_ONE_TAG);
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractMonster mo: AbstractDungeon.getMonsters().monsters){
            if(!mo.isDeadOrEscaped()&&mo instanceof FriendTemari){
                addToBot(new ApplyPowerAction(mo,mo,new WakeUpMisuzuPower(mo)));
                break;
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new WakeUpMisuzu();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.isInnate = true;
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
