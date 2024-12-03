package gkmasmod.downfall.cards;

import basemod.BaseMod;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.downfall.charbosses.cards.purple.AbstractStanceChangeCard;
import gkmasmod.downfall.charbosses.powers.cardpowers.EnemyWrathNextTurnPower;
import gkmasmod.downfall.charbosses.stances.AbstractEnemyStance;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.utils.IdolData;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class GkmasBossCard extends AbstractBossCard {

    private static String carduiImgFormat = "gkmasModResource/img/idol/%s/cardui/%s/%sbg_%s.png";

    public int secondMagicNumber;
    public int baseSecondMagicNumber;
    public boolean upgradedSecondMagicNumber;
    public boolean isSecondMagicNumberModified;

    public int thirdMagicNumber;
    public int baseThirdMagicNumber;
    public boolean upgradedThirdMagicNumber;
    public boolean isThirdMagicNumberModified;

    public int HPMagicNumber;
    public int baseHPMagicNumber;
    public boolean upgradedHPMagicNumber;
    public boolean isHPMagicNumberModified;

    public int growMagicNumber;
    public int baseGrowMagicNumber;
    public boolean upgradedGrowMagicNumber;
    public boolean isGrowMagicNumberModified;

    public int secondDamage;
    public int baseSecondDamage;
    public boolean upgradedSecondDamage;
    public boolean isSecondDamageModified;

    public int secondBlock;
    public int baseSecondBlock;
    public boolean upgradedSecondBlock;
    public boolean isSecondBlockModified;

    public String bannerColor="";

    public String cardHeader="";

    public ArrayList<AbstractCard> cardPreviewList = null;

    public int cardPreviewCount = 0;

    public int cardPreviewIndex = 0;

    public boolean updateShowImg=false;

    public String backGroundColor="";

    public String textureBannerSmallImg = null;
    public String textureBannerLargeImg = null;
    public TextureAtlas.AtlasRegion bannerSmallRegion = null;
    public TextureAtlas.AtlasRegion bannerLargeRegion = null;
    public String textureBackgroundSmallImg = null;
    public String textureBackgroundLargeImg = null;

    public GkmasBossCard(final String id,
                         final String name,
                         final String img,
                         final int cost,
                         final String rawDescription,
                         final CardType type,
                         final CardColor color,
                         final CardRarity rarity,
                         final CardTarget target) {

        super(id, name, img, cost, rawDescription, type, color, rarity, target);

        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        isSecondMagicNumberModified = false;
        isThirdMagicNumberModified = false;
        isHPMagicNumberModified = false;
        isGrowMagicNumberModified = false;
        isSecondDamageModified = false;
        isSecondBlockModified = false;
        this.intent = AbstractCharBoss.Intent.MAGIC;
        updateBackgroundImg();
        updateImg();
        setBannerColor();
    }

    public GkmasBossCard(final String id,
                         final String name,
                         final String img,
                         final int cost,
                         final String rawDescription,
                         final CardType type,
                         final CardColor color,
                         final CardRarity rarity,
                         final CardTarget target,
                         String bannerColor
                               ) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        this.bannerColor=bannerColor;
        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        isSecondMagicNumberModified = false;
        isThirdMagicNumberModified = false;
        isHPMagicNumberModified = false;
        isGrowMagicNumberModified = false;
        isSecondDamageModified = false;
        isSecondBlockModified = false;
        this.intent = AbstractCharBoss.Intent.MAGIC;
        //imgMap.clear();
        updateBackgroundImg();
        updateImg();
        setBannerColor();
    }


    public void displayUpgrades() { // Display the upgrade - when you click a card to upgrade it
        super.displayUpgrades();
        if (upgradedSecondMagicNumber) {
            secondMagicNumber = baseSecondMagicNumber;
            isSecondMagicNumberModified = true;
        }
        if (upgradedThirdMagicNumber) {
            thirdMagicNumber = baseThirdMagicNumber;
            isThirdMagicNumberModified = true;
        }
        if (upgradedHPMagicNumber) {
            HPMagicNumber = baseHPMagicNumber;
            isHPMagicNumberModified = true;
        }
        if (upgradedSecondDamage) {
            secondDamage = baseSecondDamage;
            isSecondDamageModified = true;
        }
        if (upgradedSecondBlock) {
            secondBlock = baseSecondBlock;
            isSecondBlockModified = true;
        }
        if (upgradedGrowMagicNumber) {
            growMagicNumber = baseGrowMagicNumber;
            isGrowMagicNumberModified = true;
        }

    }

    public void upgradeSecondMagicNumber(int amount) { // If we're upgrading (read: changing) the number. Note "upgrade" and NOT "upgraded" - 2 different things. One is a boolean, and then this one is what you will usually use - change the integer by how much you want to upgrade.
        baseSecondMagicNumber += amount; // Upgrade the number by the amount you provide in your card.
        secondMagicNumber = baseSecondMagicNumber; // Set the number to be equal to the base value.
        upgradedSecondMagicNumber = true; // Upgraded = true - which does what the above method does.
    }

    public void upgradeThirdMagicNumber(int amount) { // If we're upgrading (read: changing) the number. Note "upgrade" and NOT "upgraded" - 2 different things. One is a boolean, and then this one is what you will usually use - change the integer by how much you want to upgrade.
        baseThirdMagicNumber += amount; // Upgrade the number by the amount you provide in your card.
        thirdMagicNumber = baseThirdMagicNumber; // Set the number to be equal to the base value.
        upgradedThirdMagicNumber = true; // Upgraded = true - which does what the above method does.
    }

    public void upgradeHPMagicNumber(int amount) { // If we're upgrading (read: changing) the number. Note "upgrade" and NOT "upgraded" - 2 different things. One is a boolean, and then this one is what you will usually use - change the integer by how much you want to upgrade.
        baseHPMagicNumber += amount; // Upgrade the number by the amount you provide in your card.
        HPMagicNumber = baseHPMagicNumber; // Set the number to be equal to the base value.
        upgradedHPMagicNumber = true; // Upgraded = true - which does what the above method does.
    }

    public void upgradeGrowMagicNumber(int amount) { // If we're upgrading (read: changing) the number. Note "upgrade" and NOT "upgraded" - 2 different things. One is a boolean, and then this one is what you will usually use - change the integer by how much you want to upgrade.
        baseGrowMagicNumber += amount; // Upgrade the number by the amount you provide in your card.
        growMagicNumber = baseGrowMagicNumber; // Set the number to be equal to the base value.
        upgradedGrowMagicNumber = true; // Upgraded = true - which does what the above method does.
    }

    public void upgradeSecondDamage(int amount) { // If we're upgrading (read: changing) the number. Note "upgrade" and NOT "upgraded" - 2 different things. One is a boolean, and then this one is what you will usually use - change the integer by how much you want to upgrade.
        baseSecondDamage += amount; // Upgrade the number by the amount you provide in your card.
        secondDamage = baseSecondDamage; // Set the number to be equal to the base value.
        upgradedSecondDamage = true; // Upgraded = true - which does what the above method does.
    }

    public void upgradeSecondBlock(int amount) { // If we're upgrading (read: changing) the number. Note "upgrade" and NOT "upgraded" - 2 different things. One is a boolean, and then this one is what you will usually use - change the integer by how much you want to upgrade.
        baseSecondBlock += amount; // Upgrade the number by the amount you provide in your card.
        secondBlock = baseSecondBlock; // Set the number to be equal to the base value.
        upgradedSecondBlock = true; // Upgraded = true - which does what the above method does.
    }

    protected void applyPowersToBlock() {
        super.applyPowersToBlock();
        this.isSecondBlockModified = false;
        float tmp = (float)this.baseSecondBlock;

        Iterator var2;
        AbstractPower p;
        for(var2 = this.owner.powers.iterator(); var2.hasNext(); tmp = p.modifyBlock(tmp, this)) {
            p = (AbstractPower)var2.next();
        }

        for(var2 = this.owner.powers.iterator(); var2.hasNext(); tmp = p.modifyBlockLast(tmp)) {
            p = (AbstractPower)var2.next();
        }

        if (this.baseSecondBlock != MathUtils.floor(tmp)) {
            this.isSecondBlockModified = true;
        }

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }

        this.secondBlock = MathUtils.floor(tmp);
    }

    public void applyPowers() {
        super.applyPowers();
        applyPowersToBlock();
        final AbstractPlayer player = AbstractDungeon.player;
        this.isSecondDamageModified = false;
        float tmp = (float) this.baseSecondDamage;
        if (this.owner == null)
            this.owner = AbstractCharBoss.boss;
        if (this.owner.relics != null) {
            for (final AbstractRelic r : this.owner.relics) {
                tmp = r.atDamageModify(tmp, this);
                if (this.baseSecondDamage != (int) tmp) {
                    this.isSecondDamageModified = true;
                }
            }
        }
        for (final AbstractPower p : this.owner.powers) {
            tmp = p.atDamageGive(tmp, this.damageTypeForTurn, this);
        }
        tmp = this.owner.stance.atDamageGive(tmp, this.damageTypeForTurn, this);
        if (this.baseSecondDamage != (int) tmp) {
            this.isSecondDamageModified = true;
        }
        for (final AbstractPower p : player.powers) {
            tmp = p.atDamageReceive(tmp, this.damageTypeForTurn, this);
        }
        tmp = this.owner.stance.atDamageReceive(tmp, this.damageTypeForTurn);
        for (final AbstractPower p : this.owner.powers) {
            tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this);
        }
        for (final AbstractPower p : player.powers) {
            tmp = p.atDamageFinalReceive(tmp, this.damageTypeForTurn, this);
        }
        if (tmp < 0.0f) {
            tmp = 0.0f;
        }
        if (this.baseSecondDamage != MathUtils.floor(tmp)) {
            this.isSecondDamageModified = true;
        }
        this.secondDamage = MathUtils.floor(tmp);

        this.initializeDescription();
        if (this.intent != null) {
            if (!this.bossDarkened) {
                createIntent();
            }
            //destroyIntent();
        }
        if (AbstractCharBoss.boss != null) {
            if (AbstractCharBoss.boss.hasPower(StunMonsterPower.POWER_ID)) {
                bossDarken();
                destroyIntent();
            }
        }

    }

//    public int calculateDamage(int damage){
//        AbstractPlayer player = AbstractDungeon.player;
//        float tmp = (float)damage;
//        Iterator var9 = player.relics.iterator();
//        while(var9.hasNext()) {
//            AbstractRelic r = (AbstractRelic)var9.next();
//            tmp = r.atDamageModify(tmp, this);
//        }
//        AbstractPower p;
//        for(var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageGive(tmp, this.damageTypeForTurn, this)) {
//            p = (AbstractPower)var9.next();
//        }
//
//        tmp = player.stance.atDamageGive(tmp, this.damageTypeForTurn, this);
//        if (this.baseSecondDamage != (int)tmp) {
//            this.isSecondDamageModified = true;
//        }
//
//        for(var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this)) {
//            p = (AbstractPower)var9.next();
//        }
//
//
//        if (tmp < 0.0F) {
//            tmp = 0.0F;
//        }
//        return MathUtils.floor(tmp);
//    }

//    public int calculateBlock(int block) {
//        float tmp = (float)block;
//
//        Iterator var2;
//        AbstractPower p;
//        for(var2 = AbstractDungeon.player.powers.iterator(); var2.hasNext(); tmp = p.modifyBlock(tmp, this)) {
//            p = (AbstractPower)var2.next();
//        }
//
//        for(var2 = AbstractDungeon.player.powers.iterator(); var2.hasNext(); tmp = p.modifyBlockLast(tmp)) {
//            p = (AbstractPower)var2.next();
//        }
//
//        if (tmp < 0.0F) {
//            tmp = 0.0F;
//        }
//
//        return MathUtils.floor(tmp);
//    }
//
//    public int calculateBlockWithoutDexterityPower(int block) {
//        float tmp = (float)block;
//
//        Iterator var2;
//        AbstractPower p;
//        for(var2 = AbstractDungeon.player.powers.iterator(); var2.hasNext(); ) {
//            p = (AbstractPower)var2.next();
//            if(p.ID == DexterityPower.POWER_ID)
//                continue;
//            tmp = p.modifyBlock(tmp, this);
//        }
//
//        for(var2 = AbstractDungeon.player.powers.iterator(); var2.hasNext(); tmp = p.modifyBlockLast(tmp)) {
//            p = (AbstractPower)var2.next();
//        }
//
//        if (tmp < 0.0F) {
//            tmp = 0.0F;
//        }
//
//        return MathUtils.floor(tmp);
//    }
//

    private AbstractEnemyStance getEnemyStanceAtMomentOfCardPlay() {
        AbstractEnemyStance stanceAtCardPlay = (AbstractEnemyStance) this.owner.stance;
        for (final AbstractPower p : this.owner.powers) {
            if (p instanceof EnemyWrathNextTurnPower) {
                stanceAtCardPlay = ((EnemyWrathNextTurnPower)p).getWrathStance();
            }
        }
        for (AbstractCard card : this.owner.hand.group) {
            if (this == card) {
                break;
            }
            if (card instanceof AbstractStanceChangeCard) {
                stanceAtCardPlay = ((AbstractStanceChangeCard) card).changeStanceForIntentCalc(stanceAtCardPlay);
            }
        }
        return stanceAtCardPlay;
    }

    private float calculateDamage(AbstractMonster mo, AbstractPlayer player, float tmp) {
        for (final AbstractRelic r : this.owner.relics) {
            tmp = r.atDamageModify(tmp, this);
            if (this.baseSecondDamage != (int) tmp) {
                this.isSecondDamageModified = true;
            }
        }
        for (final AbstractPower p : this.owner.powers) {
            tmp = p.atDamageGive(tmp, this.damageTypeForTurn, this);
        }
        AbstractEnemyStance stanceAtCardPlay = getEnemyStanceAtMomentOfCardPlay();
        tmp = stanceAtCardPlay.atDamageGive(tmp, this.damageTypeForTurn, this);
        if (this.baseSecondDamage != (int) tmp) {
            this.isSecondDamageModified = true;
        }
        if (mo == this.owner) {
            for (final AbstractPower p : player.powers) {
                tmp = p.atDamageReceive(tmp, this.damageTypeForTurn, this);
            }
            tmp = player.stance.atDamageReceive(tmp, this.damageTypeForTurn);
        } else {
            for (final AbstractPower p : mo.powers) {
                tmp = p.atDamageReceive(tmp, this.damageTypeForTurn, this);
            }
        }
        for (final AbstractPower p : this.owner.powers) {
            tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this);
        }
        if (mo == this.owner) {
            for (final AbstractPower p : player.powers) {
                tmp = p.atDamageFinalReceive(tmp, this.damageTypeForTurn, this);
            }
        } else {
            for (final AbstractPower p : mo.powers) {
                tmp = p.atDamageFinalReceive(tmp, this.damageTypeForTurn, this);
            }
        }
        if (tmp < 0.0f) {
            tmp = 0.0f;
        }
        if (this.baseSecondDamage != MathUtils.floor(tmp)) {
            this.isSecondDamageModified = true;
        }
        return tmp;
    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        final AbstractPlayer player = AbstractDungeon.player;
        this.isSecondDamageModified = false;
        if (mo == null) {
            mo = this.owner;
        } else if (this.owner == null && mo instanceof AbstractCharBoss) {
            this.owner = (AbstractCharBoss) mo;
        }
        if (mo != null) {
            this.secondDamage = MathUtils.floor(calculateDamage(mo, player, this.baseDamage));
            this.intentDmg += MathUtils.floor(manualCustomDamageModifierMult * calculateDamage(mo, player, this.baseSecondDamage + customIntentModifiedDamage() + manualCustomDamageModifier));
        }
        this.initializeDescription();
    }


    public void updateImg(){
        String CLASSNAME = this.getClass().getSimpleName();
        CLASSNAME = CLASSNAME.substring(2);
        String idolName;
        if (updateShowImg){
            idolName = AbstractCharBoss.theIdolName;
            if(idolName == IdolData.jsna)
                idolName = IdolData.fktn;
//            this.textureImg = String.format("gkmasModResource/img/idol/%s/cards/%s.png", idolName , CLASSNAME);
            loadCardImage(String.format("gkmasModResource/img/idol/%s/cards/%s.png", idolName , CLASSNAME));
        }
        else{
            loadCardImage(assetUrl);
        }
    }

    public void updateBackgroundImg(){
        String idolName;
        idolName = AbstractCharBoss.theIdolName;
        if(idolName == IdolData.jsna)
            idolName = IdolData.fktn;
        Color color = Settings.RED_TEXT_COLOR.cpy();
        if(backGroundColor!=""){
            idolName = backGroundColor;
        }
        if(AbstractCharBoss.theIdolName.equals(IdolData.fktn)){
            basemod.ReflectionHacks.setPrivate(this, AbstractCard.class, "goldColor", color);
            this.initializeDescription();
        }
        else{
            color = Settings.GOLD_COLOR.cpy();
            basemod.ReflectionHacks.setPrivate(this, AbstractCard.class, "goldColor", color);
            this.initializeDescription();
        }

        String typeString = "";
        if (this.type == CardType.ATTACK) {
            typeString = "attack";
        } else if (this.type == CardType.SKILL) {
            typeString = "skill";
        } else if (this.type == CardType.POWER) {
            typeString = "power";
        }
        String colorTypeString = "";
        if (this.color == PlayerColorEnum.gkmasModColor){
            colorTypeString = "";
        }
        else if (this.color == PlayerColorEnum.gkmasModColorLogic){
            colorTypeString = "logic_";
        }
        else if (this.color == PlayerColorEnum.gkmasModColorSense){
            colorTypeString = "sense_";
        }
        else if (this.color == PlayerColorEnum.gkmasModColorAnomaly){
            colorTypeString = "anomaly_";
        }

        setBackgroundTexture(
                String.format(carduiImgFormat, idolName ,512,colorTypeString, typeString),
                String.format(carduiImgFormat, idolName ,1024,colorTypeString, typeString)
                );
    }

    public void setBannerColor(){
        if (this.rarity == CardRarity.COMMON){
            setBannerTexture("gkmasModResource/img/cards/banner/512_banner_blue.png","gkmasModResource/img/cards/banner/1024_banner_blue.png");
        }
        else if(this.rarity == CardRarity.UNCOMMON){
            setBannerTexture("gkmasModResource/img/cards/banner/512_banner_yellow.png","gkmasModResource/img/cards/banner/1024_banner_yellow.png");
        }
        else if(this.rarity == CardRarity.RARE){
            setBannerTexture("gkmasModResource/img/cards/banner/512_banner_color.png","gkmasModResource/img/cards/banner/1024_banner_color.png");
        }

        if(bannerColor.equals("blue")){
            setBannerTexture("gkmasModResource/img/cards/banner/512_banner_blue.png","gkmasModResource/img/cards/banner/1024_banner_blue.png");
        }
        else if(bannerColor.equals("yellow")){
            setBannerTexture("gkmasModResource/img/cards/banner/512_banner_yellow.png","gkmasModResource/img/cards/banner/1024_banner_yellow.png");
        }
        else if(bannerColor.equals("color")){
            setBannerTexture("gkmasModResource/img/cards/banner/512_banner_color.png","gkmasModResource/img/cards/banner/1024_banner_color.png");
        }
    }

    public void update(){
        super.update();

        if(this.cardPreviewList!=null){
            this.cardPreviewCount++;
            if (this.cardPreviewCount == 50) {
                this.cardPreviewCount = 0;
                this.cardPreviewIndex= (this.cardPreviewIndex + 1) % this.cardPreviewList.size();
                this.cardsToPreview = this.cardPreviewList.get(this.cardPreviewIndex);
            }
        }
    }

    public void render(SpriteBatch sb) {
        super.render(sb);
        renderCardHeader(sb);
    }
    public void renderInLibrary(SpriteBatch sb) {
        super.renderInLibrary(sb);
        if (!SingleCardViewPopup.isViewingUpgrade || !this.isSeen || this.isLocked)
            renderCardHeader(sb);
    }

    public void renderCardHeader(SpriteBatch sb, float xPos, float yPos, float yOffsetBase, float scale) {
        if (this.cardHeader != "") {
            if (this.isFlipped || this.isLocked || this.transparency <= 0.0F) {
                return;
            }
            float offsetY = yOffsetBase * Settings.scale * scale / 2.0F;
            BitmapFont.BitmapFontData fontData = FontHelper.cardTitleFont.getData();
            float originalScale = fontData.scaleX;
            float scaleMultiplier = 0.8F;

            fontData.setScale(scaleMultiplier * scale * 0.85F);
            Color color = Settings.CREAM_COLOR.cpy();
            color.a = this.transparency;
            FontHelper.renderRotatedText(sb, FontHelper.cardTitleFont, this.cardHeader, xPos, yPos, 0.0F, offsetY, this.angle, true, color);

            fontData.setScale(originalScale);
        }
    }

    public void renderCardHeader(SpriteBatch sb) {
        renderCardHeader(sb, this.current_x, this.current_y, 400.0F, this.drawScale); }

    public void setBannerTexture(String bannerSmallImg, String bannerLargeImg) {
        this.textureBannerSmallImg = bannerSmallImg;
        this.textureBannerLargeImg = bannerLargeImg;
        loadTextureFromString(bannerSmallImg);
        loadTextureFromString(bannerLargeImg);
        Texture t = this.getBannerSmallTexture();
        this.bannerSmallRegion = new TextureAtlas.AtlasRegion(t, 0, 0, t.getWidth(), t.getHeight());
        t = this.getBannerLargeTexture();
        this.bannerLargeRegion = new TextureAtlas.AtlasRegion(t, 0, 0, t.getWidth(), t.getHeight());
    }

    private static void loadTextureFromString(String textureString) {
        if (!imgMap.containsKey(textureString)) {
            imgMap.put(textureString, ImageMaster.loadImage(textureString));
        }

    }

    //Region versions, to make it easier to use basegame regions defined in ImageMaster
    public TextureAtlas.AtlasRegion getBannerSmallRegion() {
        if (bannerSmallRegion == null && textureBannerSmallImg != null)
        {
            Texture t = getBannerSmallTexture();
            bannerSmallRegion = new TextureAtlas.AtlasRegion(t, 0, 0, t.getWidth(), t.getHeight());
        }
        return bannerSmallRegion;
    }

    public TextureAtlas.AtlasRegion getBannerLargeRegion() {
        if (bannerLargeRegion == null && textureBannerLargeImg != null)
        {
            Texture t = getBannerLargeTexture();
            bannerLargeRegion = new TextureAtlas.AtlasRegion(t, 0, 0, t.getWidth(), t.getHeight());
        }
        return bannerLargeRegion;
    }

    public Texture getBannerSmallTexture() {
        return this.textureBannerSmallImg == null ? null : getTextureFromString(this.textureBannerSmallImg);
    }

    private static Texture getTextureFromString(String textureString) {
        loadTextureFromString(textureString);
        return (Texture)imgMap.get(textureString);
    }

    public Texture getBackgroundSmallTexture() {
        if (textureBackgroundSmallImg == null) {
            System.out.println("getBackgroundSmallTexture is null");
            switch (this.type) {
                case ATTACK:
                    return BaseMod.getAttackBgTexture(this.color);
                case POWER:
                    return BaseMod.getPowerBgTexture(this.color);
                default:
                    return BaseMod.getSkillBgTexture(this.color);
            }
        }

        return getTextureFromString(textureBackgroundSmallImg);
    }

    public Texture getBackgroundLargeTexture() {
        if (textureBackgroundLargeImg == null) {
            switch (this.type) {
                case ATTACK:
                    return BaseMod.getAttackBgPortraitTexture(this.color);
                case POWER:
                    return BaseMod.getPowerBgPortraitTexture(this.color);
                default:
                    return BaseMod.getSkillBgPortraitTexture(this.color);
            }
        }

        return getTextureFromString(textureBackgroundLargeImg);
    }

    public Texture getBannerLargeTexture() {
        return this.textureBannerLargeImg == null ? null : getTextureFromString(this.textureBannerLargeImg);
    }

    public void setBackgroundTexture(String backgroundSmallImg, String backgroundLargeImg) {
        this.textureBackgroundSmallImg = backgroundSmallImg;
        this.textureBackgroundLargeImg = backgroundLargeImg;
        loadTextureFromString(backgroundSmallImg);
        loadTextureFromString(backgroundLargeImg);
    }

}