package gkmasmod.cards.special;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.monster.ending.MonsterRinha;
import gkmasmod.monster.friend.FriendOnigiri;
import gkmasmod.powers.*;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;

public class DoNotGo extends GkmasCard {
    private static final String CLASSNAME = DoNotGo.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 0;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorMisuzu;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    public DoNotGo() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.exhaust = true;
        this.backGroundColor = IdolData.ttmr;
        updateBackgroundImg();
        this.tags.add(GkmasCardTag.OUTSIDE_TAG);
        this.cardHeader = CardCrawlGame.languagePack.getUIString("gkmasMod:DoNotGoHeader").TEXT[0];
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractMonster mo: AbstractDungeon.getMonsters().monsters){
            if(!mo.isDeadOrEscaped()&&mo instanceof MonsterRinha){
                MonsterRinha rinha = (MonsterRinha)mo;
                if(rinha.stage==3){
                    rinha.currentHealth = (int) (rinha.maxHealth * 0.004F);
                    rinha.healthBarUpdatedEvent();
                    AbstractDungeon.actionManager.addToBottom(new TalkAction(rinha, CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:MonsterRinha").DIALOG[2], 3.0F, 3.0F));
                    addToBot(new RemoveSpecificPowerAction(rinha,rinha,NegativeNotPower.POWER_ID));
                    addToBot(new ApplyPowerAction(rinha,rinha,new VulnerablePower(rinha,9,false),9));
                    addToBot(new ApplyPowerAction(rinha,rinha,new NotGoodTune(rinha,9),9));
                    addToBot(new ApplyPowerAction(rinha,rinha,new GreatNotGoodTune(rinha,9),9));
                    addToBot(new ApplyPowerAction(rinha,rinha,new FrailPower(rinha,9,false),9));
                    addToBot(new ApplyPowerAction(rinha,rinha,new WeakPower(rinha,9,false),9));
                    addToBot(new ApplyPowerAction(rinha,rinha,new AddDamageReceive(rinha,9),9));
                    addToBot(new ApplyPowerAction(rinha,rinha,new DoubleDamageReceive(rinha,9),9));
                    addToBot(new ApplyPowerAction(rinha,rinha,new AnxietyPower(rinha,9),9));
                    CardCrawlGame.music.dispose();
                    CardCrawlGame.music.unsilenceBGM();
                    AbstractDungeon.scene.fadeOutAmbiance();
                    CardCrawlGame.music.playTempBgmInstantly("gkmasModResource/audio/bgm/ttmr_cry.ogg",true);
                    break;
                }
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new DoNotGo();
    }

    @Override
    public void upgrade() {

    }
}
