package gkmasmod.ui;


import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.ISubscriber;
import com.megacrit.cardcrawl.helpers.TipHelper;
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
import gkmasmod.utils.CommonEnum;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;

public class SkinSelectScreen implements ISubscriber, CustomSavable<int[]> {
    public static SkinSelectScreen Inst;
    public static boolean isClick = false;
    public static boolean isClick2 = false;
    public static boolean isClick3 = false;
    public Hitbox leftHb;
    public Hitbox rightHb;
    public Hitbox upHb;
    public Hitbox downHb;
    public Hitbox updateHb;
    public Hitbox typeHb;
    public Hitbox styleHb;
    public String curName = "";
    public String nextName = "";
    private String selectIdolHint = "";
    private String typeHintName = "";
    private String styleHintName = "";
    private String typeHint = "";
    private String styleHint = "";
    public String idolName = "shro";

    public String SpecialName = "";
    public int idolIndex;
    public int skinIndex;
    public int updateIndex;
    public CommonEnum.IdolType idolType;
    public CommonEnum.IdolStyle idolStyle;


    public SkinSelectScreen() {
        this.refresh();
        this.leftHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.rightHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.upHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.downHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.updateHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.typeHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.styleHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        selectIdolHint = CardCrawlGame.languagePack.getCharacterString("selectIdol").TEXT[0];
        BaseMod.subscribe(this);
        BaseMod.addSaveField(NameHelper.makePath("skin"), this);
    }
    public Texture usedImg;
    public Texture nameImg;
    public Texture typeImg;
    public Texture styleImg;

    public void refresh() {
        idolName = IdolData.idolNames[this.idolIndex];

        this.curName = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolName",idolName)).TEXT[0];

        String skinName = IdolData.getIdol(idolName).getSkin(this.skinIndex);
        skinName = "skin10";
        // TODO 支持其他皮肤
        IdolCharacter.SELES_STAND = String.format("img/idol/%s/stand/stand_%s.png", idolName, skinName);
        this.usedImg = ImageMaster.loadImage(IdolCharacter.SELES_STAND);
        this.SpecialName = "";
        this.nameImg = ImageMaster.loadImage(String.format("img/idol/%s/stand/name.png", idolName));
        this.idolType = IdolData.getIdol(idolName).getType(this.skinIndex);
        this.idolStyle = IdolData.getIdol(idolName).getStyle(this.skinIndex);
        switch (this.idolType){
            case SENSE:
                this.typeImg = ImageMaster.loadImage("img/UI/sense.png");
                this.typeHintName = CardCrawlGame.languagePack.getUIString("typeHintName:sense").TEXT[0];
                this.typeHint = CardCrawlGame.languagePack.getUIString("typeHint:sense").TEXT[0];
                break;
            case LOGIC:
                this.typeImg = ImageMaster.loadImage("img/UI/logic.png");
                this.typeHintName = CardCrawlGame.languagePack.getUIString("typeHintName:logic").TEXT[0];
                this.typeHint = CardCrawlGame.languagePack.getUIString("typeHint:logic").TEXT[0];
                break;
        }
        switch (this.idolStyle){
            case GOOD_TUNE:
                this.styleImg = ImageMaster.loadImage("img/UI/goodTune.png");
                this.styleHintName = CardCrawlGame.languagePack.getUIString("styleHintName:goodTune").TEXT[0];
                this.styleHint = CardCrawlGame.languagePack.getUIString("styleHint:goodTune").TEXT[0];
                break;
            case FOCUS:
                this.styleImg = ImageMaster.loadImage("img/UI/focus.png");
                this.styleHintName = CardCrawlGame.languagePack.getUIString("styleHintName:focus").TEXT[0];
                this.styleHint = CardCrawlGame.languagePack.getUIString("styleHint:focus").TEXT[0];
                break;
            case GOOD_IMPRESSION:
                this.styleImg = ImageMaster.loadImage("img/UI/goodImpression.png");
                this.styleHintName = CardCrawlGame.languagePack.getUIString("styleHintName:goodImpression").TEXT[0];
                this.styleHint = CardCrawlGame.languagePack.getUIString("styleHint:goodImpression").TEXT[0];
                break;
            case YARUKI:
                this.styleImg = ImageMaster.loadImage("img/UI/yaruki.png");
                this.styleHintName = CardCrawlGame.languagePack.getUIString("styleHintName:yaruki").TEXT[0];
                this.styleHint = CardCrawlGame.languagePack.getUIString("styleHint:yaruki").TEXT[0];
                break;
        }


        if (AbstractDungeon.player instanceof IdolCharacter) {
            IdolCharacter k = (IdolCharacter)AbstractDungeon.player;
            k.idolName = idolName;
            k.refreshSkin();
    }
    }

    public int prevIndex() {
        return this.idolIndex - 1 < 0 ? IdolData.idolNames.length - 1 : this.idolIndex - 1;
    }

    public int nextIndex() {
        return this.idolIndex + 1 > IdolData.idolNames.length - 1 ? 0 : this.idolIndex + 1;
    }

    public int prevSkinIndex() {
        return this.skinIndex - 1 < 0 ? IdolData.getIdol(this.idolIndex).getSkinNum() - 1 : this.skinIndex - 1;
    }

    public int nextSkinIndex() {
        return this.skinIndex + 1 > IdolData.getIdol(this.idolIndex).getSkinNum() - 1 ? 0 : this.skinIndex + 1;
    }

    public void update() {
        float centerX = (float)Settings.WIDTH * 0.8F;
        float centerY = (float)Settings.HEIGHT * 0.5F;
        this.leftHb.move(centerX - 180.0F * Settings.scale, centerY + 50.0F * Settings.scale);
        this.rightHb.move(centerX + 180.0F * Settings.scale, centerY + 50.0F * Settings.scale);
        this.upHb.move(centerX, centerY + 180.0F * Settings.scale + 50.0F * Settings.scale);
        this.downHb.move(centerX, centerY - 180.0F * Settings.scale + 50.0F * Settings.scale);
        this.updateHb.move(centerX - 120.0F *Settings.scale, centerY + 150.0F * Settings.scale + 50.0F * Settings.scale);
        this.typeHb.move(centerX - 900.0F *Settings.scale, centerY  + 80.0F * Settings.scale);
        this.styleHb.move(centerX - 800.0F *Settings.scale, centerY  + 80.0F * Settings.scale);
        this.updateInput();
    }

    private void updateInput() {
        if (CardCrawlGame.chosenCharacter == PlayerColorEnum.gkmasMod_character) {
            this.leftHb.update();
            this.rightHb.update();
            this.upHb.update();
            this.downHb.update();
            this.updateHb.update();
            this.typeHb.update();
            this.styleHb.update();
            if (this.leftHb.clicked) {
                this.leftHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.idolIndex = this.prevIndex();
                selectIdolHint = CardCrawlGame.languagePack.getCharacterString("selectIdol").TEXT[0];
                if (this.skinIndex >= IdolData.getIdol(this.idolIndex).getSkinNum())
                    this.skinIndex = IdolData.getIdol(this.idolIndex).getSkinNum() - 1;
                this.refresh();
                this.isClick = true;
                this.isClick2 = true;
                this.isClick3 = true;

                GkmasMod.listeners.forEach(listener -> listener.onCardImgUpdate());
            }

            if (this.rightHb.clicked) {
                this.rightHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.idolIndex = this.nextIndex();
                selectIdolHint = CardCrawlGame.languagePack.getCharacterString("selectIdol").TEXT[0];
                if (this.skinIndex >= IdolData.getIdol(this.idolIndex).getSkinNum())
                    this.skinIndex = IdolData.getIdol(this.idolIndex).getSkinNum() - 1;
                this.refresh();
                this.isClick = true;
                this.isClick2 = true;
                this.isClick3 = true;

                GkmasMod.listeners.forEach(listener -> listener.onCardImgUpdate());
            }

            if (this.upHb.clicked) {
                this.upHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.skinIndex = this.prevSkinIndex();
                this.refresh();
                this.isClick = true;
                this.isClick2 = true;
                this.isClick3 = true;

            }

            if (this.downHb.clicked) {
                this.downHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.skinIndex = this.nextSkinIndex();
                this.refresh();
                this.isClick = true;
                this.isClick2 = true;
                this.isClick3 = true;

            }

            if (this.updateHb.clicked) {
                this.updateHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.updateIndex = (this.updateIndex + 1) % 2;
                this.refresh();
                this.isClick = true;
                this.isClick2 = true;
                this.isClick3 = true;

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
        sb.draw(this.typeImg,this.typeHb.cX-24.0F, this.typeHb.cY-24.0F);
        sb.draw(this.styleImg,this.styleHb.cX-24.0F, this.styleHb.cY-24.0F);
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

        if(this.typeHb.hovered){
            sb.setColor(Color.LIGHT_GRAY);
            TipHelper.renderGenericTip( this.typeHb.cX + 20.F,  this.typeHb.cY + 20.F, this.typeHintName, this.typeHint);
        }
        else{
            sb.setColor(Color.WHITE);
        }

        if(this.styleHb.hovered){
            sb.setColor(Color.LIGHT_GRAY);
            TipHelper.renderGenericTip(this.styleHb.cX+ 20.F, this.styleHb.cY + 20.F, this.styleHintName, this.styleHint);
        }
        else{
            sb.setColor(Color.WHITE);
        }

        this.rightHb.render(sb);
        this.leftHb.render(sb);
        this.upHb.render(sb);
        this.downHb.render(sb);
        this.updateHb.render(sb);
        this.typeHb.render(sb);
        this.styleHb.render(sb);
    }

    public void renderSkin(SpriteBatch sb, float x, float y) {
        if(this.usedImg != null){
            sb.draw(this.usedImg,x,y);
        }
    }



    public void onLoad(int[] args) {
        if(args != null && args.length == 3){
            this.idolIndex = args[0];
            this.skinIndex = args[1];
            this.updateIndex = args[2];
            refresh();
            System.out.println("load skin select screen");
            GkmasMod.listeners.forEach(listener -> listener.onCardImgUpdate());
        }

   }
    public int[] onSave() {
        return new int[]{this.idolIndex,this.skinIndex,this.updateIndex};
    }

    static {
        Inst = new SkinSelectScreen();
    }


}
