package gkmasmod.screen;


import basemod.BaseMod;
import basemod.interfaces.ISubscriber;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import gkmasmod.characters.OtherIdolCharacter;
import gkmasmod.utils.CommonEnum;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;
import java.util.Random;

import static gkmasmod.characters.PlayerColorEnum.gkmasModOther_character;

public class OtherSkinSelectScreen implements ISubscriber {
    public static OtherSkinSelectScreen Inst;
    public static boolean isClick = false;
    public static boolean isClick2 = false;
    public static boolean isClick3 = false;
    public Hitbox leftHb;
    public Hitbox rightHb;
    public Hitbox leftHb2;
    public Hitbox rightHb2;
    public Hitbox upHb;
    public Hitbox downHb;
    public Hitbox updateHb;
    public Hitbox hideHb;
    public Hitbox typeHb;
    public Hitbox styleHb;
//    public Hitbox achievementHb1;
//    public Hitbox achievementHb2;
    public String curName = "";
    public String nextName = "";
    private String selectIdolHint = "";
    private String typeHintName = "";
    private String styleHintName = "";
    private String typeHint = "";
    private String styleHint = "";
    public String idolName = "prod";

    public String SpecialName = "";
    public int idolIndex;
    public int skinIndex=0;
    public int updateIndex;
    public CommonEnum.IdolType idolType;
    public CommonEnum.IdolStyle idolStyle;
    public boolean flag = false;
    public boolean hasVideo = false;

    private static String updateHint = CardCrawlGame.languagePack.getUIString("gkmasMod:Hint:update").TEXT[0];
    private static String updateHintTitle = CardCrawlGame.languagePack.getUIString("gkmasMod:Name:update").TEXT[0];
    private static String hideHint = CardCrawlGame.languagePack.getUIString("gkmasMod:Hint:hide").TEXT[0];
    private static String hideHintTitle = CardCrawlGame.languagePack.getUIString("gkmasMod:Name:hide").TEXT[0];

//    public int defaultBackgroundIndex=0;
    public OtherSkinSelectScreen() {
        this.refresh();
        this.leftHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.rightHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.leftHb2 = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.rightHb2 = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.upHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.downHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.updateHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.hideHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.typeHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.styleHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        selectIdolHint = CardCrawlGame.languagePack.getCharacterString("selectIdol").TEXT[0];
        BaseMod.subscribe(this);
//        defaultBackgroundIndex = new Random().nextInt(2);
    }
    public Texture usedImg;
    public Texture nameImg;
    public Texture typeImg;
    public Texture styleImg;

    public void refresh() {
        idolName = IdolData.otherIdolNames[this.idolIndex];

        this.curName = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolName",idolName)).TEXT[0];

        String skinName = IdolData.getOtherIdol(idolName).getSkinImg(this.skinIndex);
        OtherIdolCharacter.SELES_STAND = String.format("gkmasModResource/img/idol/othe/%s/stand/stand_%s.png", idolName, skinName);
        this.usedImg = ImageMaster.loadImage(OtherIdolCharacter.SELES_STAND);
        this.SpecialName = "";
        this.nameImg = ImageMaster.loadImage(String.format("gkmasModResource/img/idol/othe/%s/stand/name.png", idolName));
        this.idolType = IdolData.getOtherIdol(idolName).getType(this.skinIndex);
        this.idolStyle = IdolData.getOtherIdol(idolName).getStyle(this.skinIndex);
        switch (this.idolType){
            case SENSE:
                this.typeImg = ImageMaster.loadImage("gkmasModResource/img/UI/sense.png");
                this.typeHintName = CardCrawlGame.languagePack.getUIString("typeHintName:sense").TEXT[0];
                this.typeHint = CardCrawlGame.languagePack.getUIString("typeHint:sense").TEXT[0];
                break;
            case LOGIC:
                this.typeImg = ImageMaster.loadImage("gkmasModResource/img/UI/logic.png");
                this.typeHintName = CardCrawlGame.languagePack.getUIString("typeHintName:logic").TEXT[0];
                this.typeHint = CardCrawlGame.languagePack.getUIString("typeHint:logic").TEXT[0];
                break;
            case ANOMALY:
                this.typeImg = ImageMaster.loadImage("gkmasModResource/img/UI/anomaly.png");
                this.typeHintName = CardCrawlGame.languagePack.getUIString("typeHintName:anomaly").TEXT[0];
                this.typeHint = CardCrawlGame.languagePack.getUIString("typeHint:anomaly").TEXT[0];
                break;
        }
        switch (this.idolStyle){
            case GOOD_TUNE:
                this.styleImg = ImageMaster.loadImage("gkmasModResource/img/UI/goodTune.png");
                this.styleHintName = CardCrawlGame.languagePack.getUIString("styleHintName:goodTune").TEXT[0];
                this.styleHint = CardCrawlGame.languagePack.getUIString("styleHint:goodTune").TEXT[0];
                break;
            case FOCUS:
                this.styleImg = ImageMaster.loadImage("gkmasModResource/img/UI/focus.png");
                this.styleHintName = CardCrawlGame.languagePack.getUIString("styleHintName:focus").TEXT[0];
                this.styleHint = CardCrawlGame.languagePack.getUIString("styleHint:focus").TEXT[0];
                break;
            case GOOD_IMPRESSION:
                this.styleImg = ImageMaster.loadImage("gkmasModResource/img/UI/goodImpression.png");
                this.styleHintName = CardCrawlGame.languagePack.getUIString("styleHintName:goodImpression").TEXT[0];
                this.styleHint = CardCrawlGame.languagePack.getUIString("styleHint:goodImpression").TEXT[0];
                break;
            case YARUKI:
                this.styleImg = ImageMaster.loadImage("gkmasModResource/img/UI/yaruki.png");
                this.styleHintName = CardCrawlGame.languagePack.getUIString("styleHintName:yaruki").TEXT[0];
                this.styleHint = CardCrawlGame.languagePack.getUIString("styleHint:yaruki").TEXT[0];
                break;
            case CONCENTRATION:
                this.styleImg = ImageMaster.loadImage("gkmasModResource/img/UI/concentration.png");
                this.styleHintName = CardCrawlGame.languagePack.getUIString("styleHintName:concentration").TEXT[0];
                this.styleHint = CardCrawlGame.languagePack.getUIString("styleHint:concentration").TEXT[0];
                break;
            case FULL_POWER:
                this.styleImg = ImageMaster.loadImage("gkmasModResource/img/UI/fullPower.png");
                this.styleHintName = CardCrawlGame.languagePack.getUIString("styleHintName:fullPower").TEXT[0];
                this.styleHint = CardCrawlGame.languagePack.getUIString("styleHint:fullPower").TEXT[0];
                break;
        }
        if(idolName.equals(IdolData.prod)){
            this.typeImg = ImageMaster.loadImage("gkmasModResource/img/UI/produce.png");
            this.typeHintName = CardCrawlGame.languagePack.getUIString("typeHintName:produce").TEXT[0];
            this.typeHint = CardCrawlGame.languagePack.getUIString("typeHint:produce").TEXT[0];
            this.styleImg = ImageMaster.loadImage("gkmasModResource/img/UI/allCard.png");
            this.styleHintName = CardCrawlGame.languagePack.getUIString("styleHintName:allCard").TEXT[0];
            this.styleHint = CardCrawlGame.languagePack.getUIString("styleHint:allCard").TEXT[0];
        }
        else if(idolName.equals(IdolData.sson)){
            this.styleImg = ImageMaster.loadImage("gkmasModResource/img/UI/notGoodTune.png");
            this.styleHintName = CardCrawlGame.languagePack.getUIString("styleHintName:notGoodTune").TEXT[0];
            this.styleHint = CardCrawlGame.languagePack.getUIString("styleHint:notGoodTune").TEXT[0];
        }
        else if(idolName.equals(IdolData.sgka)){
            this.styleImg = ImageMaster.loadImage("gkmasModResource/img/UI/fightWill.png");
            this.styleHintName = CardCrawlGame.languagePack.getUIString("styleHintName:fightWill").TEXT[0];
            this.styleHint = CardCrawlGame.languagePack.getUIString("styleHint:fightWill").TEXT[0];
        }
        else if(idolName.equals(IdolData.nasr)){
            this.styleImg = ImageMaster.loadImage("gkmasModResource/img/UI/lifeNotStop.png");
            this.styleHintName = CardCrawlGame.languagePack.getUIString("styleHintName:lifeNotStop").TEXT[0];
            this.styleHint = CardCrawlGame.languagePack.getUIString("styleHint:lifeNotStop").TEXT[0];
        }
        else if(idolName.equals(IdolData.andk)){
            this.typeImg = ImageMaster.loadImage("gkmasModResource/img/UI/logicSense.png");
            this.typeHintName = CardCrawlGame.languagePack.getUIString("typeHintName:logic").TEXT[0];
            this.typeHint = CardCrawlGame.languagePack.getUIString("typeHint:logicSense").TEXT[0];
            this.styleImg = ImageMaster.loadImage("gkmasModResource/img/UI/pinkGirl.png");
            this.styleHintName = CardCrawlGame.languagePack.getUIString("styleHintName:pinkGirl").TEXT[0];
            this.styleHint = CardCrawlGame.languagePack.getUIString("styleHint:pinkGirl").TEXT[0];
        }
        if (AbstractDungeon.player instanceof OtherIdolCharacter) {
            OtherIdolCharacter k = (OtherIdolCharacter)AbstractDungeon.player;
            k.idolName = idolName;
            k.idolData = IdolData.getOtherIdol(idolName);
            k.skinIndex = skinIndex;
            k.refreshSkin();
    }
    }

    public int prevIndex() {
        return this.idolIndex - 1 < 0 ? IdolData.otherIdolNames.length - 1 : this.idolIndex - 1;
    }

    public int nextIndex() {
        return this.idolIndex + 1 > IdolData.otherIdolNames.length - 1 ? 0 : this.idolIndex + 1;
    }

    public int prevSkinIndex() {
        return this.skinIndex - 1 < 0 ? IdolData.getOtherIdol(this.idolIndex).getSkinNum() - 1 : this.skinIndex - 1;
    }

    public int nextSkinIndex() {
        return this.skinIndex + 1 > IdolData.getOtherIdol(this.idolIndex).getSkinNum() - 1 ? 0 : this.skinIndex + 1;
    }

    public void update() {
        float centerX = (float)Settings.WIDTH * 0.8F;
        float centerY = (float)Settings.HEIGHT * 0.5F;
        this.leftHb.move(centerX - 180.0F * Settings.scale, centerY + 50.0F * Settings.scale);
        this.rightHb.move(centerX + 180.0F * Settings.scale, centerY + 50.0F * Settings.scale);
        this.leftHb2.move(centerX - 180.0F * Settings.scale, centerY - 240.0F * Settings.scale);
        this.rightHb2.move(centerX + 180.0F * Settings.scale, centerY - 240.0F * Settings.scale);
//        this.upHb.move(centerX, centerY + 380.0F * Settings.scale + 50.0F * Settings.scale);
        this.downHb.move(centerX, centerY - 180.0F * Settings.scale + 50.0F * Settings.scale);
        this.updateHb.move(centerX - 120.0F *Settings.scale, centerY + 150.0F * Settings.scale + 50.0F * Settings.scale);
        this.hideHb.move(centerX - 120.0F *Settings.scale, centerY - 150.0F * Settings.scale + 50.0F * Settings.scale);
        this.typeHb.move(centerX - 900.0F *Settings.scale, centerY  + 80.0F * Settings.scale);
        this.styleHb.move(centerX - 800.0F *Settings.scale + Settings.WIDTH*0.02F, centerY  + 80.0F * Settings.scale);
        this.updateInput();
    }

    private void updateInput() {
        if (CardCrawlGame.chosenCharacter == gkmasModOther_character) {
            this.leftHb.update();
            this.rightHb.update();
            this.leftHb2.update();
            this.rightHb2.update();
//            this.upHb.update();
            this.downHb.update();
            this.updateHb.update();
            this.hideHb.update();
            this.typeHb.update();
            this.styleHb.update();
            if (this.leftHb.clicked) {
                this.leftHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.idolIndex = this.prevIndex();
                selectIdolHint = CardCrawlGame.languagePack.getCharacterString("selectIdol").TEXT[0];
                if (this.skinIndex >= IdolData.getOtherIdol(this.idolIndex).getSkinNum())
                    this.skinIndex = IdolData.getOtherIdol(this.idolIndex).getSkinNum() - 1;
                this.refresh();
                this.isClick = true;
                this.isClick2 = true;
                this.isClick3 = true;
                this.hasVideo = false;

                //GkmasMod.listeners.forEach(listener -> listener.onCardImgUpdate());
            }

            if (this.rightHb.clicked) {
                this.rightHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.idolIndex = this.nextIndex();
                selectIdolHint = CardCrawlGame.languagePack.getCharacterString("selectIdol").TEXT[0];
                if (this.skinIndex >= IdolData.getOtherIdol(this.idolIndex).getSkinNum())
                    this.skinIndex = IdolData.getOtherIdol(this.idolIndex).getSkinNum() - 1;
                this.refresh();
                this.isClick = true;
                this.isClick2 = true;
                this.isClick3 = true;
                this.hasVideo = false;
                //GkmasMod.listeners.forEach(listener -> listener.onCardImgUpdate());
            }

//            if (this.upHb.clicked) {
//                this.upHb.clicked = false;
//                CardCrawlGame.sound.play("UI_CLICK_1");
//                this.skinIndex = this.prevSkinIndex();
//                this.refresh();
//                this.isClick = true;
//                this.isClick2 = true;
//                this.isClick3 = true;
//                this.hasVideo = false;
//            }

            if (this.downHb.clicked) {
                this.downHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.skinIndex = this.nextSkinIndex();
                this.refresh();
                this.isClick = true;
                this.isClick2 = true;
                this.isClick3 = true;
                this.hasVideo = false;
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
                if (this.leftHb2.hovered) {
                    this.leftHb2.clickStarted = true;
                }
                if (this.rightHb2.hovered) {
                    this.rightHb2.clickStarted = true;
                }
//                if (this.upHb.hovered) {
//                    this.upHb.clickStarted = true;
//                }
                if (this.downHb.hovered) {
                    this.downHb.clickStarted = true;
                }
                if (this.updateHb.hovered) {
                    this.updateHb.clickStarted = true;
                }
                if (this.hideHb.hovered) {
                    this.hideHb.clickStarted = true;
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
        sb.setColor(Color.WHITE);
        this.renderSkin(sb, centerX-skin_x, centerY-skin_y + 50.0F * Settings.scale);

        if(this.nameImg != null)
            sb.draw(this.nameImg,centerX-250, centerY-420);

        sb.draw(this.typeImg,this.typeHb.cX-24.0F, this.typeHb.cY-24.0F);
        if(this.styleImg != null)
            sb.draw(this.styleImg,this.styleHb.cX-24.0F, this.styleHb.cY-24.0F);
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, selectIdolHint, centerX-250.0F*Settings.xScale, centerY + 400.0F * Settings.scale, Color.WHITE, 1.25F);
        Color color = Settings.GOLD_COLOR.cpy();
        color.a /= 2.0F;
        float dist = 100.0F * Settings.scale;

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

//        if (this.upHb.hovered) {
//            sb.setColor(Color.LIGHT_GRAY);
//        } else {
//            sb.setColor(Color.WHITE);
//        }
//
//        sb.draw(ImageMaster.CF_RIGHT_ARROW, this.upHb.cX - 24.0F, this.upHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 90.0F, 0, 0, 48, 48, false, false);

        if (this.downHb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }

        sb.draw(ImageMaster.CF_LEFT_ARROW, this.downHb.cX - 24.0F, this.downHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 90.0F, 0, 0, 48, 48, false, false);

        if (this.updateHb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
            TipHelper.renderGenericTip( this.updateHb.cX + 20.F,  this.updateHb.cY + 20.F, this.updateHintTitle, this.updateHint);
        } else {
            sb.setColor(Color.WHITE);
        }

//        if(this.hideHb.hovered){
//            sb.setColor(Color.LIGHT_GRAY);
//            TipHelper.renderGenericTip( this.hideHb.cX + 20.F,  this.hideHb.cY + 20.F, this.hideHintTitle, this.hideHint);
//        } else {
//            sb.setColor(Color.WHITE);
//        }

        if (this.updateIndex == 1){
            sb.draw(ImageMaster.OPTION_TOGGLE,this.updateHb.cX - 24.0F, this.updateHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
            sb.draw(ImageMaster.OPTION_TOGGLE_ON,this.updateHb.cX - 24.0F, this.updateHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
        }
        else
            sb.draw(ImageMaster.OPTION_TOGGLE,this.updateHb.cX - 24.0F, this.updateHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);

        if(this.typeHb.hovered){
            sb.setColor(Color.LIGHT_GRAY);
            TipHelper.renderGenericTip( this.typeHb.cX + 20.F,  this.typeHb.cY + 20.F, this.typeHintName, this.typeHint);
        }
        else{
            sb.setColor(Color.WHITE);
        }


        if(this.styleImg!=null){
            if(this.styleHb.hovered){
                sb.setColor(Color.LIGHT_GRAY);
                TipHelper.renderGenericTip(this.styleHb.cX+ 20.F, this.styleHb.cY + 20.F, this.styleHintName, this.styleHint);
            }
            else{
                sb.setColor(Color.WHITE);
            }
        }

        this.rightHb.render(sb);
        this.leftHb.render(sb);
//        this.upHb.render(sb);
        this.downHb.render(sb);
        this.updateHb.render(sb);
        this.hideHb.render(sb);
        this.typeHb.render(sb);
        this.styleHb.render(sb);
    }

    public void renderSkin(SpriteBatch sb, float x, float y) {
        if(this.usedImg != null){
            sb.draw(this.usedImg,x,y);
        }
    }

    static {
        Inst = new OtherSkinSelectScreen();
    }

}
