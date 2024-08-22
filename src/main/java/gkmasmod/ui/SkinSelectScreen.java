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
    public static boolean isClick = false;
    public Hitbox leftHb;
    public Hitbox rightHb;
    public Hitbox upHb;
    public Hitbox downHb;
    public Hitbox updateHb;
    public String curName = "";
    public String nextName = "";

    public String SpecialName = "";
    public int idolIndex;
    public int skinIndex;
    public int updateIndex;


    public SkinSelectScreen() {
        this.refresh();
        this.leftHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.rightHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.upHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.downHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.updateHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
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
        skinName = "skin1";
        // TODO 支持其他皮肤
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

    public int prevSkinIndex() {
        return this.skinIndex - 1 < 0 ? IdolSkin.SKINS.get(IdolNameString.idolNames[this.idolIndex]).size() - 1 : this.skinIndex - 1;
    }

    public int nextSkinIndex() {
        return this.skinIndex + 1 > IdolSkin.SKINS.get(IdolNameString.idolNames[this.idolIndex]).size() - 1 ? 0 : this.skinIndex + 1;
    }

    public void update() {

        float centerX = (float)Settings.WIDTH * 0.8F;
        float centerY = (float)Settings.HEIGHT * 0.5F;
        this.leftHb.move(centerX - 180.0F * Settings.scale, centerY + 50.0F * Settings.scale);
        this.rightHb.move(centerX + 180.0F * Settings.scale, centerY + 50.0F * Settings.scale);
        this.upHb.move(centerX, centerY + 180.0F * Settings.scale + 50.0F * Settings.scale);
        this.downHb.move(centerX, centerY - 180.0F * Settings.scale + 50.0F * Settings.scale);
        this.updateHb.move(centerX - 120.0F *Settings.scale, centerY + 150.0F * Settings.scale + 50.0F * Settings.scale);
        this.updateInput();
    }

    private void updateInput() {
        if (CardCrawlGame.chosenCharacter == PlayerColorEnum.gkmasMod_character) {
            this.leftHb.update();
            this.rightHb.update();
            this.upHb.update();
            this.downHb.update();
            this.updateHb.update();
            if (this.leftHb.clicked) {
                this.isClick = true;
                this.leftHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.idolIndex = this.prevIndex();
                if (this.skinIndex >= IdolSkin.SKINS.get(IdolNameString.idolNames[this.idolIndex]).size())
                    this.skinIndex = IdolSkin.SKINS.get(IdolNameString.idolNames[this.idolIndex]).size() - 1;
                this.refresh();
                GkmasMod.listeners.forEach(listener -> listener.onCardImgUpdate());
            }

            if (this.rightHb.clicked) {
                this.isClick = true;
                this.rightHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.idolIndex = this.nextIndex();
                if (this.skinIndex >= IdolSkin.SKINS.get(IdolNameString.idolNames[this.idolIndex]).size())
                    this.skinIndex = IdolSkin.SKINS.get(IdolNameString.idolNames[this.idolIndex]).size() - 1;
                this.refresh();
                GkmasMod.listeners.forEach(listener -> listener.onCardImgUpdate());
            }

            if (this.upHb.clicked) {
                this.isClick = true;
                this.upHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.skinIndex = this.prevSkinIndex();
                this.refresh();
            }

            if (this.downHb.clicked) {
                this.isClick = true;
                this.downHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.skinIndex = this.nextSkinIndex();
                this.refresh();
            }

            if (this.updateHb.clicked) {
                this.isClick = true;
                this.updateHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.updateIndex = (this.updateIndex + 1) % 2;
                this.refresh();
            }

            if (InputHelper.justClickedLeft) {
                if (this.leftHb.hovered) {
                    this.leftHb.clickStarted = true;
                }

                if (this.rightHb.hovered) {
                    this.rightHb.clickStarted = true;
                }
                if (this.upHb.hovered) {
                    this.upHb.clickStarted = true;
                }
                if (this.downHb.hovered) {
                    this.downHb.clickStarted = true;
                }
                if (this.updateHb.hovered) {
                    this.updateHb.clickStarted = true;
                }
            }
        }

    }

    public void render(SpriteBatch sb) {

        Color RC = new Color(0.0F, 204.0F, 255.0F, 1.0F);
        float centerX = (float) Settings.WIDTH * 0.8F;
        float centerY = (float)Settings.HEIGHT * 0.5F;
        float skin_x = (float) this.usedImg.getWidth() /2;
        float skin_y = (float) this.usedImg.getWidth() /2;
        this.renderSkin(sb, centerX-skin_x, centerY-skin_y + 50.0F * Settings.scale);
        sb.draw(this.nameImg,centerX-250, centerY-420);
        String selectIdolHint = CardCrawlGame.languagePack.getCharacterString("selectIdol").TEXT[0];
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, selectIdolHint, centerX, centerY + 300.0F * Settings.scale, Color.WHITE, 1.25F);
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

        if (this.upHb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }

        sb.draw(ImageMaster.CF_RIGHT_ARROW, this.upHb.cX - 24.0F, this.upHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 90.0F, 0, 0, 48, 48, false, false);

        if (this.downHb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }

        sb.draw(ImageMaster.CF_LEFT_ARROW, this.downHb.cX - 24.0F, this.downHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 90.0F, 0, 0, 48, 48, false, false);

        if (this.updateHb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }

        if (this.updateIndex == 1)
            sb.draw(ImageMaster.OPTION_TOGGLE_ON,this.updateHb.cX - 24.0F, this.updateHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
        else
            sb.draw(ImageMaster.OPTION_TOGGLE,this.updateHb.cX - 24.0F, this.updateHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);

        this.rightHb.render(sb);
        this.leftHb.render(sb);
        this.upHb.render(sb);
        this.downHb.render(sb);
        this.updateHb.render(sb);
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
