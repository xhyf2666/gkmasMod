package gkmasmod.downfall.cards.logic;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.SoundHelper;

public class ENBigOnigiri extends GkmasBossCard {
    private static final String CLASSNAME = ENBigOnigiri.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENBigOnigiri.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME2);

    private static final int COST = 0;

    private static final int BASE_MAGIC = 2;
    private static final int UPGRADE_PLUS_MAGIC = 2;

    private static final int BASE_BLOCK = 2;

    private static final int BASE_HP = 4;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    private String flavor = "";

    public ENBigOnigiri() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, "color");
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseHPMagicNumber = BASE_HP;
        this.HPMagicNumber = this.baseHPMagicNumber;
        this.baseBlock = BASE_BLOCK;
        this.block = this.baseBlock;
        this.intent = AbstractMonster.Intent.DEFEND;
        this.exhaust = true;
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, CardHelper.getColor(73, 224, 254));
        flavor = FlavorText.CardStringsFlavorField.flavor.get(CARD_STRINGS);
        this.tags.add(GkmasCardTag.IDOL_CARD_TAG);
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(m, m, this.HPMagicNumber));
        if(m instanceof AbstractCharBoss){
            int count = ((AbstractCharBoss) m).cardsPlayedThisCombat - 1;
            addToBot(new GainBlockAction(m, m, this.block + this.magicNumber * count));
            java.util.Random random = new java.util.Random();
            int index = random.nextInt(2);
            String[] voices = new String[]{
                    "gkmasModResource/audio/voice/skillcard/cidol_hume_3_000_produce_skillcard_01.ogg",
                    "gkmasModResource/audio/voice/skillcard/cidol_hume_3_000_produce_skillcard_02.ogg"
            };
            SoundHelper.playSound(voices[index]);
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new ENBigOnigiri();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
