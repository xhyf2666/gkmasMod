//package gkmasmod.powers;
//
//import com.megacrit.cardcrawl.core.AbstractCreature;
//import com.megacrit.cardcrawl.core.CardCrawlGame;
//import com.megacrit.cardcrawl.localization.PowerStrings;
//import com.megacrit.cardcrawl.powers.AbstractPower;
//import gkmasmod.actions.RinhaAttackAction;
//import gkmasmod.utils.NameHelper;
//
//public class FriendBlackHolePower extends AbstractPower {
//    private static final String CLASSNAME = FriendBlackHolePower.class.getSimpleName();
//
//    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
//
//    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);
//
//    private static final String NAME = powerStrings.NAME;
//
//    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
//
//    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
//    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);
//
//    public FriendBlackHolePower(AbstractCreature owner) {
//        this.name = NAME;
//        this.ID = POWER_ID;
//        this.owner = owner;
//        this.type = PowerType.BUFF;
//        loadRegion("beat");
//
////        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
////        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
//
//        this.updateDescription();
//    }
//
//    public void updateDescription() {
//        this.description = String.format(DESCRIPTIONS[0]);
//    }
//
//
//}
