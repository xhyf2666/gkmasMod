package gkmasmod.cards.idol;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.common.GainTrainRoundPowerAction;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.special.*;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;

public class Sunfaded extends GkmasCard {
    private static final String CLASSNAME = Sunfaded.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);
    private static final String[] IMG_PATHS = new String[]{
            "gkmasModResource/img/cards/dynamic/Sunfaded_1.png",
            "gkmasModResource/img/cards/dynamic/Sunfaded_2.png",
            "gkmasModResource/img/cards/dynamic/Sunfaded_3.png",
            "gkmasModResource/img/cards/dynamic/Sunfaded_4.png",
            "gkmasModResource/img/cards/dynamic/Sunfaded_5.png",
            "gkmasModResource/img/cards/dynamic/Sunfaded_6.png"
    };

    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorOther;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    private float currentTime = 0F;
    private int frameIndex = 0;
    private float FrameDuring = 0.30F;

    public Sunfaded() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.backGroundColor = IdolData.shro;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeTempCardInHandAction(new SunfadedJudge()));
//        ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
//        stanceChoices.add(new Sunfaded1());
//        stanceChoices.add(new Sunfaded2());
//        stanceChoices.add(new Sunfaded3());
//        stanceChoices.add(new Sunfaded4());
//        stanceChoices.add(new Sunfaded5());
//        stanceChoices.add(new Sunfaded6());
//        addToBot(new ChooseOneAction(stanceChoices));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Sunfaded();
    }

    @Override
    public void triggerOnExhaust() {
        addToBot(new GainTrainRoundPowerAction(AbstractDungeon.player,1));
        addToBot(new MakeTempCardInDrawPileAction(new Sunfaded1(), 1, true, false, false, Settings.WIDTH * 0.1F, Settings.HEIGHT / 2.0F));
        addToBot(new MakeTempCardInDrawPileAction(new Sunfaded2(), 1, true, false, false, Settings.WIDTH * 0.25F, Settings.HEIGHT / 2.0F));
        addToBot(new MakeTempCardInDrawPileAction(new Sunfaded3(), 1, true, false, false, Settings.WIDTH * 0.4F, Settings.HEIGHT / 2.0F));
        addToBot(new MakeTempCardInDrawPileAction(new Sunfaded4(), 1, true, false, false, Settings.WIDTH * 0.55F, Settings.HEIGHT / 2.0F));
        addToBot(new MakeTempCardInDrawPileAction(new Sunfaded5(), 1, true, false, false, Settings.WIDTH * 0.7F, Settings.HEIGHT / 2.0F));
        addToBot(new MakeTempCardInDrawPileAction(new Sunfaded6(), 1, true, false, false, Settings.WIDTH * 0.85F, Settings.HEIGHT / 2.0F));
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

    public void update() {
        super.update();
        this.currentTime += Gdx.graphics.getDeltaTime();
        if (this.currentTime >= this.FrameDuring) {
            this.currentTime -= this.FrameDuring;
            this.frameIndex = (this.frameIndex + 1) % IMG_PATHS.length;
            this.textureImg = IMG_PATHS[this.frameIndex];
            loadCardImage(this.textureImg);
        }
    }


}
