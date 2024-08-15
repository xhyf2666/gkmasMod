package gkmasmod.ui;


import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.ISubscriber;
import gkmasmod.characters.IdolCharacter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.utils.IdolNameString;
import gkmasmod.utils.IdolSkin;
import gkmasmod.utils.NameHelper;

public class SkinSelectScreen implements ISubscriber, CustomSavable<Integer> {
    private static String[] TEXT;

    private static String[] Special;
//    private static final ArrayList<Skin> SKINS = new ArrayList();
    public static SkinSelectScreen Inst;
    public Hitbox leftHb;
    public Hitbox rightHb;
    public String curName = "";
    public String nextName = "";

    public String SpecialName = "";
    public int idolIndex;
    public int skinIndex;


    public SkinSelectScreen() {
        this.refresh();
        this.leftHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.rightHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        BaseMod.subscribe(this);
        BaseMod.addSaveField(NameHelper.makePath("skin"), this);
    }
    public Texture usedImg;
    public Texture nameImg;

    public void refresh() {
        String name = IdolNameString.idolNames[this.idolIndex];

        this.curName = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolName",name)).TEXT[0];

        String skinName = IdolSkin.SKINS.get(name).get(this.skinIndex);
        //IdolCharacter.SELES_STAND = String.format("img/idol/stand/%s_%s.png", name, skinName);
        IdolCharacter.SELES_STAND = String.format("img/idol/%s/stand/stand_%s.png", name, skinName);
        this.usedImg = ImageMaster.loadImage(IdolCharacter.SELES_STAND);
        this.SpecialName = "";
        this.nameImg = ImageMaster.loadImage(String.format("img/idol/%s/stand/name.png", name));


        if (AbstractDungeon.player instanceof IdolCharacter) {
            IdolCharacter k = (IdolCharacter)AbstractDungeon.player;
            k.refreshSkin();
    }
    }

    public int prevIndex() {
        return this.idolIndex - 1 < 0 ? IdolNameString.idolNames.length - 1 : this.idolIndex - 1;
    }

    public int nextIndex() {
        return this.idolIndex + 1 > IdolNameString.idolNames.length - 1 ? 0 : this.idolIndex + 1;
    }

    public void update() {

        float centerX = (float)Settings.WIDTH * 0.8F;
        float centerY = (float)Settings.HEIGHT * 0.5F;
        this.leftHb.move(centerX - 200.0F * Settings.scale, centerY);
        this.rightHb.move(centerX + 200.0F * Settings.scale, centerY);
        this.updateInput();
    }

    private void updateInput() {
        if (CardCrawlGame.chosenCharacter == PlayerColorEnum.gkmasMod_character) {
            this.leftHb.update();
            this.rightHb.update();
            if (this.leftHb.clicked) {
                this.leftHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.idolIndex = this.prevIndex();
                this.refresh();
                GkmasMod.listeners.forEach(listener -> listener.onCardImgUpdate());
            }

            if (this.rightHb.clicked) {
                this.rightHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.idolIndex = this.nextIndex();
                this.refresh();

                GkmasMod.listeners.forEach(listener -> listener.onCardImgUpdate());
            }

            if (InputHelper.justClickedLeft) {
                if (this.leftHb.hovered) {
                    this.leftHb.clickStarted = true;
                }

                if (this.rightHb.hovered) {
                    this.rightHb.clickStarted = true;
                }
            }
        }

    }

    public void render(SpriteBatch sb) {

        Color RC = new Color(0.0F, 204.0F, 255.0F, 1.0F);
        float centerX = (float) Settings.WIDTH * 0.8F;
        float centerY = (float)Settings.HEIGHT * 0.5F;
        float usedx = (float) this.usedImg.getWidth() /2;
        float usedy = (float) this.usedImg.getWidth() /2;
        this.renderSkin(sb, centerX-usedx, centerY-usedy);
        sb.draw(this.nameImg,centerX-250, centerY-420);
        String selectIdolHint = CardCrawlGame.languagePack.getCharacterString("selectIdol").TEXT[0];
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, selectIdolHint, centerX, centerY + 250.0F * Settings.scale, Color.WHITE, 1.25F);
        Color color = Settings.GOLD_COLOR.cpy();
        color.a /= 2.0F;
        float dist = 100.0F * Settings.scale;


        //FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, this.curName, centerX, centerY-200, RC);
        //FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, this.SpecialName, centerX, centerY-250, RC);
        if (this.leftHb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }

        sb.draw(ImageMaster.CF_LEFT_ARROW, this.leftHb.cX - 24.0F, this.leftHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
        if (this.rightHb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }

        sb.draw(ImageMaster.CF_RIGHT_ARROW, this.rightHb.cX - 24.0F, this.rightHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
        this.rightHb.render(sb);
        this.leftHb.render(sb);
    }

    public void renderSkin(SpriteBatch sb, float x, float y) {
        if(this.usedImg != null){
            sb.draw(this.usedImg,x,y);
        }
    }



    public void onLoad(Integer arg0) {
     this.idolIndex = arg0.intValue();
     refresh();
   }
    public Integer onSave() {
        return this.idolIndex;
    }

    static {
        Inst = new SkinSelectScreen();
    }


}
