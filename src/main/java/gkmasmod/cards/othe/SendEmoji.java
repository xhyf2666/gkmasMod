package gkmasmod.cards.othe;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Tactician;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cards.GkmasCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.relics.TemariToy;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;

public class SendEmoji extends GkmasCard {
    private static final String CLASSNAME = SendEmoji.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);
    private static final String[] IMG_PATHS = new String[]{
            "gkmasModResource/img/cards/dynamic/SendEmoji_1.png",
            "gkmasModResource/img/cards/dynamic/SendEmoji_2.png",
            "gkmasModResource/img/cards/dynamic/SendEmoji_3.png",
            "gkmasModResource/img/cards/dynamic/SendEmoji_4.png",
            "gkmasModResource/img/cards/dynamic/SendEmoji_5.png"
    };

    private static final int COST = -2;

    private static final int BASE_MAGIC = 2;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorOther;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    private float currentTime = 0F;
    private int frameIndex = 0;
    private float FrameDuring = 0.06F;

    public SendEmoji() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.backGroundColor = IdolData.shro;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.selfRetain = true;
        updateBackgroundImg();
        this.cardHeader = CardCrawlGame.languagePack.getUIString("gkmasMod:SendEmojiHeader").TEXT[0];
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    public void triggerOnExhaust() {
        if(AbstractDungeon.player.hasRelic(TemariToy.ID))
            AbstractDungeon.player.getRelic(TemariToy.ID).flash();
        addToBot(new MakeTempCardInHandAction(makeCopy()));
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

    @Override
    public AbstractCard makeCopy() {
        return new SendEmoji();
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
