package gkmasmod.cards.idol;

import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.common.BlockDamageWallopAction;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;

public class SlowGrowth extends GkmasCard {
    private static final String CLASSNAME = SlowGrowth.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s_%d.png", CLASSNAME,3);

    private static final String[] IMG_PATHS = new String[]{
            "gkmasModResource/img/cards/common/SlowGrowth_0.png",
            "gkmasModResource/img/cards/common/SlowGrowth_1.png",
            "gkmasModResource/img/cards/common/SlowGrowth_2.png",
            "gkmasModResource/img/cards/common/SlowGrowth_3.png"
    };

    private static final int COST = 1;

    private static final int BASE_MAGIC = 20;
    private static final int UPGRADE_PLUS_MAGIC = 10;

    private static final int BASE_HP = 2;

    private static final int BASE_GROW = 4;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorOther;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private String flavor = "";

    private float currentTime = 0F;
    private int frameIndex = 0;
    private float FrameDuring = 1.5F;

    public SlowGrowth() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseHPMagicNumber = BASE_HP;
        this.HPMagicNumber = this.baseHPMagicNumber;
        this.baseGrowMagicNumber = BASE_GROW;
        this.growMagicNumber = this.baseGrowMagicNumber;
        this.tags.add(GkmasCardTag.COST_HP_TAG);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, CardHelper.getColor(73, 224, 254));
        flavor = FlavorText.CardStringsFlavorField.flavor.get(CARD_STRINGS);
        this.backGroundColor = IdolData.kcna;
        updateBackgroundImg();
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.HPMagicNumber > 0){
            addToBot(new LoseHPAction(p,p,this.HPMagicNumber));
        }
        addToBot(new BlockDamageWallopAction(1.0F * this.magicNumber / 100, 0, p, m,this));
    }

    public void applyPowersToBlock() {
        super.applyPowersToBlock();
        int count = AbstractDungeon.player.currentBlock;
        int damage_ = (int) (1.0F * count * this.magicNumber / 100);
        FlavorText.AbstractCardFlavorFields.flavor.set(this, String.format(flavor, calculateDamage(damage_)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new SlowGrowth();
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
