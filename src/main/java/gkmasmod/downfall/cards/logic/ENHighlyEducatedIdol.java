package gkmasmod.downfall.cards.logic;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;
import gkmasmod.utils.SoundHelper;

public class ENHighlyEducatedIdol extends GkmasBossCard {
    private static final String CLASSNAME = ENHighlyEducatedIdol.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENHighlyEducatedIdol.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME2);

    private static final int COST = 0;
    private static final int BASE_MAGIC = 3;

    private static final int BLOCK_AMT = 2;
    private static final int UPGRADE_PLUS_BLOCK = 2;

    private static final int BLOCK_AMT2 = 3;
    private static final int UPGRADE_PLUS_BLOCK2 = 2;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ENHighlyEducatedIdol() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, "blue");
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseBlock = BLOCK_AMT;
        this.baseSecondBlock = BLOCK_AMT2;
        this.exhaust = true;
        this.backGroundColor = IdolData.shro;
        updateBackgroundImg();
        this.intent = AbstractMonster.Intent.DEFEND_BUFF;
        this.tags.add(GkmasCardTag.YARUKI_TAG);
        this.tags.add(GkmasCardTag.IDOL_CARD_TAG);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(m, m, this.block));
        int count = PlayerHelper.getPowerAmount(m, DexterityPower.POWER_ID);
        if (count > magicNumber) {
            addToBot(new GainBlockAction(m, m, this.secondBlock));
        }
        SoundHelper.playSound("gkmasModResource/audio/voice/skillcard/cidol_shro_1_000_produce_skillcard_01.ogg");

    }

    @Override
    public AbstractCard makeCopy() {
        return new ENHighlyEducatedIdol();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeSecondBlock(UPGRADE_PLUS_BLOCK2);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
