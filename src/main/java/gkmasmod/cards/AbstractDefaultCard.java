package gkmasmod.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.Listener.CardImgUpdateListener;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.ui.SkinSelectScreen;
import gkmasmod.utils.IdolData;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class AbstractDefaultCard extends CustomCard  implements CardImgUpdateListener {

    private static String carduiImgFormat = "img/idol/%s/cardui/%s/%sbg_%s.png";

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

    public int secondDamage;
    public int baseSecondDamage;
    public boolean upgradedSecondDamage;
    public boolean isSecondDamageModified;

    public int secondBlock;
    public int baseSecondBlock;
    public boolean upgradedSecondBlock;
    public boolean isSecondBlockModified;

    public String bannerColor="";

    public boolean updateShowImg=false;
    public AbstractDefaultCard(final String id,
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
        isSecondDamageModified = false;
        isSecondBlockModified = false;
        //imgMap.clear();
        updateBackgroundImg();
        updateImg();
        setBannerColor();
    }

    public AbstractDefaultCard(final String id,
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
        if (updateShowImg){
            this.textureImg = String.format("img/idol/%s/cards/%s.png", SkinSelectScreen.Inst.idolName , CLASSNAME);
            System.out.println("updateImg: "+this.textureImg);
            loadCardImage(String.format("img/idol/%s/cards/%s.png", SkinSelectScreen.Inst.idolName , CLASSNAME));
        }
    }

    public void updateBackgroundImg(){
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
                String.format(carduiImgFormat, SkinSelectScreen.Inst.idolName ,512,colorTypeString, typeString),
                String.format(carduiImgFormat, SkinSelectScreen.Inst.idolName ,1024,colorTypeString, typeString)
                );
    }

    @Override
    public void onCardImgUpdate() {
        updateImg();
        updateBackgroundImg();
    }

    public void setBannerColor(){
        if (this.rarity == CardRarity.COMMON){
            setBannerTexture("img/cards/banner/512_banner_blue.png","img/cards/banner/1024_banner_blue.png");
        }
        else if(this.rarity == CardRarity.UNCOMMON){
            setBannerTexture("img/cards/banner/512_banner_yellow.png","img/cards/banner/1024_banner_yellow.png");
        }
        else if(this.rarity == CardRarity.RARE){
            setBannerTexture("img/cards/banner/512_banner_color.png","img/cards/banner/1024_banner_color.png");
        }

        if(bannerColor.equals("blue")){
            setBannerTexture("img/cards/banner/512_banner_blue.png","img/cards/banner/1024_banner_blue.png");
        }
        else if(bannerColor.equals("yellow")){
            setBannerTexture("img/cards/banner/512_banner_yellow.png","img/cards/banner/1024_banner_yellow.png");
        }
        else if(bannerColor.equals("color")){
            setBannerTexture("img/cards/banner/512_banner_color.png","img/cards/banner/1024_banner_color.png");
        }
    }

}