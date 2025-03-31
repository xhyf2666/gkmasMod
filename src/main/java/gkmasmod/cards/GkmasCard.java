package gkmasmod.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import gkmasmod.Listener.CardImgUpdateListener;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.patches.SingleCardViewPopupPatch;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class GkmasCard extends CustomCard  implements CardImgUpdateListener, SpawnModificationCard {

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

    public String backGroundColor="";

    public int customLimit=0;

    public int currentCustomCount=0;

//    public ArrayList<Integer> customCountList = null;

    public ArrayList<ArrayList<CustomEffect>> customEffectList = null;

    public boolean updateShowImg=false;
    public GkmasCard(final String id,
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
        //imgMap.clear();
        updateBackgroundImg();
        updateImg();
        setBannerColor();
    }

    public GkmasCard(final String id,
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
        for(var2 = AbstractDungeon.player.powers.iterator(); var2.hasNext(); tmp = p.modifyBlock(tmp, this)) {
            p = (AbstractPower)var2.next();
        }

        for(var2 = AbstractDungeon.player.powers.iterator(); var2.hasNext(); tmp = p.modifyBlockLast(tmp)) {
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
        AbstractPlayer player = AbstractDungeon.player;
        this.isSecondDamageModified = false;
        if (!this.isMultiDamage) {
            float tmp = (float)this.baseSecondDamage;
            Iterator var3 = player.relics.iterator();

            while(var3.hasNext()) {
                AbstractRelic r = (AbstractRelic)var3.next();
                tmp = r.atDamageModify(tmp, this);
                if (this.baseSecondDamage != (int)tmp) {
                    this.isSecondDamageModified = true;
                }
            }

            AbstractPower p;
            for(var3 = player.powers.iterator(); var3.hasNext(); tmp = p.atDamageGive(tmp, this.damageTypeForTurn, this)) {
                p = (AbstractPower)var3.next();
            }

            tmp = player.stance.atDamageGive(tmp, this.damageTypeForTurn, this);
            if (this.baseSecondDamage != (int)tmp) {
                this.isSecondDamageModified = true;
            }

            for(var3 = player.powers.iterator(); var3.hasNext(); tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this)) {
                p = (AbstractPower)var3.next();
            }

            if (tmp < 0.0F) {
                tmp = 0.0F;
            }

            if (this.baseSecondDamage != MathUtils.floor(tmp)) {
                this.isSecondDamageModified = true;
            }

            this.secondDamage = MathUtils.floor(tmp);
        }

    }

    public int calculateDamage(int damage){
        AbstractPlayer player = AbstractDungeon.player;
        float tmp = (float)damage;
        Iterator var9 = player.relics.iterator();
        while(var9.hasNext()) {
            AbstractRelic r = (AbstractRelic)var9.next();
            tmp = r.atDamageModify(tmp, this);
        }
        AbstractPower p;
        for(var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageGive(tmp, this.damageTypeForTurn, this)) {
            p = (AbstractPower)var9.next();
        }

        tmp = player.stance.atDamageGive(tmp, this.damageTypeForTurn, this);
        if (this.baseSecondDamage != (int)tmp) {
            this.isSecondDamageModified = true;
        }

        for(var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this)) {
            p = (AbstractPower)var9.next();
        }


        if (tmp < 0.0F) {
            tmp = 0.0F;
        }
        return MathUtils.floor(tmp);
    }

    public int calculateBlock(int block) {
        float tmp = (float)block;

        Iterator var2;
        AbstractPower p;
        for(var2 = AbstractDungeon.player.powers.iterator(); var2.hasNext(); tmp = p.modifyBlock(tmp, this)) {
            p = (AbstractPower)var2.next();
        }

        for(var2 = AbstractDungeon.player.powers.iterator(); var2.hasNext(); tmp = p.modifyBlockLast(tmp)) {
            p = (AbstractPower)var2.next();
        }

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }

        return MathUtils.floor(tmp);
    }

    public int calculateBlockWithoutDexterityPower(int block) {
        float tmp = (float)block;

        Iterator var2;
        AbstractPower p;
        for(var2 = AbstractDungeon.player.powers.iterator(); var2.hasNext(); ) {
            p = (AbstractPower)var2.next();
            if(p.ID == DexterityPower.POWER_ID)
                continue;
            tmp = p.modifyBlock(tmp, this);
        }

        for(var2 = AbstractDungeon.player.powers.iterator(); var2.hasNext(); tmp = p.modifyBlockLast(tmp)) {
            p = (AbstractPower)var2.next();
        }

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }

        return MathUtils.floor(tmp);
    }


    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        applyPowersToBlock();
        AbstractPlayer player = AbstractDungeon.player;
        this.isSecondDamageModified = false;
        if (!this.isMultiDamage && mo != null) {
            float tmp = (float)this.baseSecondDamage;
            Iterator var9 = player.relics.iterator();

            while(var9.hasNext()) {
                AbstractRelic r = (AbstractRelic)var9.next();
                tmp = r.atDamageModify(tmp, this);
                if (this.baseSecondDamage != (int)tmp) {
                    this.isSecondDamageModified = true;
                }
            }

            AbstractPower p;
            for(var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageGive(tmp, this.damageTypeForTurn, this)) {
                p = (AbstractPower)var9.next();
            }

            tmp = player.stance.atDamageGive(tmp, this.damageTypeForTurn, this);
            if (this.baseSecondDamage != (int)tmp) {
                this.isSecondDamageModified = true;
            }

            for(var9 = mo.powers.iterator(); var9.hasNext(); tmp = p.atDamageReceive(tmp, this.damageTypeForTurn, this)) {
                p = (AbstractPower)var9.next();
            }

            for(var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this)) {
                p = (AbstractPower)var9.next();
            }

            for(var9 = mo.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalReceive(tmp, this.damageTypeForTurn, this)) {
                p = (AbstractPower)var9.next();
            }

            if (tmp < 0.0F) {
                tmp = 0.0F;
            }

            if (this.baseSecondDamage != MathUtils.floor(tmp)) {
                this.isSecondDamageModified = true;
            }

            this.secondDamage = MathUtils.floor(tmp);
        }

    }


    public void updateImg(){
        //imgMap.clear();
        String CLASSNAME = this.getClass().getSimpleName();
        String idolName;
        if (updateShowImg){
            idolName = SkinSelectScreen.Inst.idolName;
            this.textureImg = String.format("gkmasModResource/img/idol/%s/cards/%s.png", idolName , CLASSNAME);
            if(!Gdx.files.internal(this.textureImg).exists()){
                this.textureImg = String.format("gkmasModResource/img/idol/%s/cards/%s.png", IdolData.shro , CLASSNAME);
            }
//            System.out.println("updateImg: "+this.textureImg);
            loadCardImage(this.textureImg);
        }
    }

    public void updateBackgroundImg(){
        if(this.color == PlayerColorEnum.gkmasModColorMisuzu||this.color == PlayerColorEnum.gkmasModColorMoon)
            return;
        String idolName;
        idolName = SkinSelectScreen.Inst.idolName;
        if(AbstractDungeon.player !=null && AbstractDungeon.player instanceof MisuzuCharacter){
            idolName = IdolData.hmsz;
        }
        Color color = Settings.RED_TEXT_COLOR.cpy();
        Color textColor = Settings.PURPLE_RELIC_COLOR.cpy();
        if(backGroundColor!="")
            idolName = backGroundColor;
        if(idolName.equals(IdolData.empty)){
            setPortraitTextures("gkmasModResource/img/cards/background/empty.png", "gkmasModResource/img/cards/background/empty.png");
            setBannerTexture("gkmasModResource/img/cards/background/empty.png", "gkmasModResource/img/cards/background/empty.png");
        }

        if(idolName.equals(IdolData.fktn)){
            basemod.ReflectionHacks.setPrivate(this, AbstractCard.class, "textColor", textColor);
            basemod.ReflectionHacks.setPrivate(this, AbstractCard.class, "goldColor", color);
            this.initializeDescription();
        }
        else if(idolName.equals(IdolData.jsna)){
            basemod.ReflectionHacks.setPrivate(this, AbstractCard.class, "textColor", textColor);
            basemod.ReflectionHacks.setPrivate(this, AbstractCard.class, "goldColor", color);
            this.initializeDescription();
        }
        else{
            color = Settings.GOLD_COLOR.cpy();
            textColor = Settings.CREAM_COLOR.cpy();
            basemod.ReflectionHacks.setPrivate(this, AbstractCard.class, "textColor", textColor);
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

    public void setIdolBackgroundTexture(String idolName){
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

        setBackgroundTexture(
                String.format(carduiImgFormat, idolName ,512,colorTypeString, typeString),
                String.format(carduiImgFormat, idolName ,1024,colorTypeString, typeString)
        );
    }

    @Override
    public void onCardImgUpdate() {
        updateImg();
        updateBackgroundImg();
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

    public void renderCardCustom(SpriteBatch sb, float xPos, float yPos, float yOffsetBase, float scale) {
        if (this.customEffectList.size()>0) {
            if (this.isFlipped || this.isLocked || this.transparency <= 0.0F) {
                return;
            }
            float offsetY = yOffsetBase * Settings.scale * scale / 2.0F;
            BitmapFont.BitmapFontData fontData = FontHelper.cardTitleFont.getData();
            float originalScale = fontData.scaleX;
            float scaleMultiplier = 1.2F;

            fontData.setScale(scaleMultiplier * scale);
            Color color = Settings.CREAM_COLOR.cpy();
            color.a = this.transparency;
            for (int i = 0; i < SingleCardViewPopupPatch.customEffectLength; i++) {
                FontHelper.renderRotatedText(sb, FontHelper.cardTitleFont, SingleCardViewPopupPatch.customDescription.get(i), xPos, yPos, 0.0F, offsetY+50.F*i*Settings.scale, this.angle, true, color);
                sb.draw(ImageMaster.UI_GOLD, xPos +105.0F * Settings.scale, yPos-30*Settings.scale+offsetY+50.F*i*Settings.scale, ImageMaster.UI_GOLD.getWidth() * Settings.scale, ImageMaster.UI_GOLD.getWidth() * Settings.scale);
                FontHelper.renderRotatedText(sb, FontHelper.cardTitleFont, String.valueOf(SingleCardViewPopupPatch.customPrice.get(i)), xPos+155.0F*Settings.scale, yPos, 0.0F, offsetY+50.F*i*Settings.scale, this.angle, true, Color.GOLD);
                FontHelper.renderRotatedText(sb, FontHelper.cardTitleFont, String.format("(%d)",SingleCardViewPopupPatch.customLimit.get(i)), xPos+195.0F*Settings.scale, yPos, 0.0F, offsetY+50.F*i*Settings.scale, this.angle, true, Color.GREEN);
            }
            FontHelper.renderRotatedText(sb, FontHelper.cardTitleFont, String.format(SingleCardViewPopupPatch.customTip,this.customLimit), xPos+40*Settings.scale, yPos, 0.0F, offsetY-80F*Settings.scale, this.angle, true,color);
            fontData.setScale(originalScale);
            renderCardCustomPreviewInSingleView(sb);
        }
    }

    public void renderCardCustomPreviewInSingleView(SpriteBatch sb) {
        SingleCardViewPopupPatch.customCard.current_x = 435.0F * Settings.scale;
        SingleCardViewPopupPatch.customCard.current_y = 305.0F * Settings.scale;
        SingleCardViewPopupPatch.customCard.drawScale = 0.8F;
        SingleCardViewPopupPatch.customCard.render(sb);
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

    public void customTrigger(){

    }

    public void renderCardCustom(SpriteBatch sb) {
        renderCardCustom(sb, Settings.WIDTH / 2f + 400.0f * Settings.scale, Settings.HEIGHT / 2f + 160f * Settings.yScale, 400.0F, this.drawScale); }


    public void renderCardHeader(SpriteBatch sb) {
        renderCardHeader(sb, this.current_x, this.current_y, 400.0F, this.drawScale); }




}