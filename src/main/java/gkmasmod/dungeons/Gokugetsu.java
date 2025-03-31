package gkmasmod.dungeons;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;

import java.util.ArrayList;

public class Gokugetsu extends AbstractDungeon {
    public Gokugetsu(String name, String levelId, AbstractPlayer p, ArrayList<String> newSpecialOneTimeEventList) {
        super(name, levelId, p, newSpecialOneTimeEventList);
    }

    public Gokugetsu(String name, AbstractPlayer p, SaveFile saveFile) {
        super(name, p, saveFile);
    }

    @Override
    protected void initializeLevelSpecificChances() {

    }

    @Override
    protected ArrayList<String> generateExclusions() {
        return null;
    }

    @Override
    protected void generateMonsters() {

    }

    @Override
    protected void generateWeakEnemies(int i) {

    }

    @Override
    protected void generateStrongEnemies(int i) {

    }

    @Override
    protected void generateElites(int i) {

    }

    @Override
    protected void initializeBoss() {

    }

    @Override
    protected void initializeEventList() {

    }

    @Override
    protected void initializeEventImg() {

    }

    @Override
    protected void initializeShrineList() {

    }
}
