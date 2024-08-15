package gkmasmod.cards;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import gkmasmod.Listener.CardImgUpdateListener;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.ui.SkinSelectScreen;
import gkmasmod.utils.IdolNameString;
import org.lwjgl.Sys;

public abstract class AbstractDefaultCard extends CustomCard  implements CardImgUpdateListener {

    private static String carduiImgFormat = "img/idol/%s/cardui/%s/%sbg_%s.png";

    public int secondMagicNumber;        // Just like magic number, or any number for that matter, we want our regular, modifiable stat
    public int baseSecondMagicNumber;    // And our base stat - the number in it's base state. It will reset to that by default.
    public boolean upgradedSecondMagicNumber; // A boolean to check whether the number has been upgraded or not.
    public boolean isSecondMagicNumberModified; // A boolean to check whether the number has been modified or not, for coloring purposes. (red/green)
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
        //imgMap.clear();
        updateBackgroundImg();
        updateImg();
    }

    public void displayUpgrades() { // Display the upgrade - when you click a card to upgrade it
        super.displayUpgrades();
//        updateImg();
//        updateBackgroundImg();
        if (upgradedSecondMagicNumber) { // If we set upgradedDefaultSecondMagicNumber = true in our card.
            secondMagicNumber = baseSecondMagicNumber; // Show how the number changes, as out of combat, the base number of a card is shown.
            isSecondMagicNumberModified = true; // Modified = true, color it green to highlight that the number is being changed.
        }

    }

    public void upgradeSecondMagicNumber(int amount) { // If we're upgrading (read: changing) the number. Note "upgrade" and NOT "upgraded" - 2 different things. One is a boolean, and then this one is what you will usually use - change the integer by how much you want to upgrade.
        baseSecondMagicNumber += amount; // Upgrade the number by the amount you provide in your card.
        secondMagicNumber = baseSecondMagicNumber; // Set the number to be equal to the base value.
        upgradedSecondMagicNumber = true; // Upgraded = true - which does what the above method does.
    }

    public void updateImg(){
        //imgMap.clear();
        String CLASSNAME = this.getClass().getSimpleName();
        if (updateShowImg){
            this.textureImg = String.format("img/idol/%s/cards/%s.png",IdolNameString.idolNames[SkinSelectScreen.Inst.idolIndex] , CLASSNAME);
            System.out.println("updateImg: "+this.textureImg);
            loadCardImage(String.format("img/idol/%s/cards/%s.png",IdolNameString.idolNames[SkinSelectScreen.Inst.idolIndex] , CLASSNAME));
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
                String.format(carduiImgFormat,IdolNameString.idolNames[SkinSelectScreen.Inst.idolIndex] ,512,colorTypeString, typeString),
                String.format(carduiImgFormat,IdolNameString.idolNames[SkinSelectScreen.Inst.idolIndex] ,1024,colorTypeString, typeString)
                );
    }

    @Override
    public void onCardImgUpdate() {
        updateImg();
        updateBackgroundImg();
    }
}