package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.NoBlockPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.ModifyDamageAction;

public class NaughtyPuppet extends CustomRelic {

    private static final String CLASSNAME = NaughtyPuppet.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int HP = 50;
    private static final int BASE_MAGIC = 100;
    private static final int BASE_MAGIC2 = 6;
    private static final int BASE_MAGIC3 = 2;
    private static int playTimes = 2;

    private int hp_lost=0;

    private PowerTip tip = new PowerTip();

    public NaughtyPuppet() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
        tip.header="@STSLIB:FLAVOR@";
        tip.body=String.format(CardCrawlGame.languagePack.getUIString("gkmasMod:NaughtyPuppetTip").TEXT[0],hp_lost);
        this.tips.add(tip);
        FlavorText.PowerTipFlavorFields.boxColor.set(tip, CardHelper.getColor(73, 224, 254));
    }

    @Override
    public void onVictory() {
        this.counter = playTimes;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],HP,BASE_MAGIC,BASE_MAGIC2,BASE_MAGIC3,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new NaughtyPuppet();
    }

    public void onUseCard(AbstractCard card, UseCardAction useCardAction) {
        if (this.counter > 0) {
            float amount = 1.0F*AbstractDungeon.player.currentHealth / AbstractDungeon.player.maxHealth;
            float HP_ = HP*1.0F/100;
            if (card.type == AbstractCard.CardType.ATTACK && amount<=HP_) {
                int damage = (int)(1.0F*this.hp_lost*BASE_MAGIC/100);
                AbstractCreature target = useCardAction.target;
                if(target==null) {
                    target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                }
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                this.flash();
                addToBot(new ModifyDamageAction(target, new DamageInfo(AbstractDungeon.player, damage, useCardAction.damageType), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, BASE_MAGIC2));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new NoBlockPower(AbstractDungeon.player, BASE_MAGIC3,false), BASE_MAGIC3));
                this.counter--;
                if (this.counter == 0) {
                    this.grayscale = true;
                }
            }
        }
    }

    @Override
    public int onLoseHpLast(int damageAmount) {
        this.hp_lost += damageAmount;
        tip.body=String.format(CardCrawlGame.languagePack.getUIString("gkmasMod:NaughtyPuppetTip").TEXT[0],hp_lost);
        return damageAmount;
    }

    public void atBattleStart() {
        this.counter = playTimes;
        this.hp_lost = 0;
        tip.body=String.format(CardCrawlGame.languagePack.getUIString("gkmasMod:NaughtyPuppetTip").TEXT[0],hp_lost);
    }

    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }
    }
}
