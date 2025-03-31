package gkmasmod.screen;


import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.ISubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.*;
import gkmasmod.cards.GkmasCard;
import gkmasmod.characters.IdolCharacter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.utils.CommonEnum;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

import java.io.IOException;

import static gkmasmod.characters.PlayerColorEnum.gkmasMod_character;

public class SkinSelectScreen implements ISubscriber, CustomSavable<int[]> {
    public static SkinSelectScreen Inst;
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
    public Hitbox achievementHb1;
    public Hitbox achievementHb2;
    public String curName = "";
    public String nextName = "";
    private String selectIdolHint = "";
    private String typeHintName = "";
    private String styleHintName = "";
    private String typeHint = "";
    private String styleHint = "";
    public String idolName = "shro";
    public VideoPlayer videoPlayer;
    private Hitbox video_hb;

    public String SpecialName = "";
    public int idolIndex;
    public int skinIndex=3;
    public int updateIndex;
    public CommonEnum.IdolType idolType;
    public CommonEnum.IdolStyle idolStyle;
    public AbstractCard specialCard;
    public boolean flag = false;
    public boolean hasVideo = false;
    public boolean hideSameIdol = false;

    private static Texture achievementImg1 = ImageMaster.loadImage("gkmasModResource/img/UI/achievement1_1.png");
    private static Texture achievementImg2 = ImageMaster.loadImage("gkmasModResource/img/UI/achievement1_2.png");
    private static Texture achievementImg3 = ImageMaster.loadImage("gkmasModResource/img/UI/achievement2.png");

    private static String achievementHintName1 = CardCrawlGame.languagePack.getUIString("gkmasMod:HintName:achievement1_1").TEXT[0];
    private static String achievementHintName2 = CardCrawlGame.languagePack.getUIString("gkmasMod:HintName:achievement1_2").TEXT[0];
    private static String achievementHintName3 = CardCrawlGame.languagePack.getUIString("gkmasMod:HintName:achievement2").TEXT[0];
    private static String achievementHint1 = CardCrawlGame.languagePack.getUIString("gkmasMod:Hint:achievement1_1").TEXT[0];
    private static String achievementHint2 = CardCrawlGame.languagePack.getUIString("gkmasMod:Hint:achievement1_2").TEXT[0];
    private static String achievementHint3 = CardCrawlGame.languagePack.getUIString("gkmasMod:Hint:achievement2").TEXT[0];
    private static String updateHint = CardCrawlGame.languagePack.getUIString("gkmasMod:Hint:update").TEXT[0];
    private static String updateHintTitle = CardCrawlGame.languagePack.getUIString("gkmasMod:Name:update").TEXT[0];
    private static String hideHint = CardCrawlGame.languagePack.getUIString("gkmasMod:Hint:hide").TEXT[0];
    private static String hideHintTitle = CardCrawlGame.languagePack.getUIString("gkmasMod:Name:hide").TEXT[0];
    public SkinSelectScreen() {
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
        this.achievementHb1 = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.achievementHb2 = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.video_hb = new Hitbox(400.0F*Settings.scale, 600.0F*Settings.scale);
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

        String skinName = IdolData.getIdol(idolName).getSkinImg(this.skinIndex);
        IdolCharacter.SELES_STAND = String.format("gkmasModResource/img/idol/%s/stand/stand_%s.png", idolName, skinName);
        this.usedImg = ImageMaster.loadImage(IdolCharacter.SELES_STAND);
        this.SpecialName = "";
        this.nameImg = ImageMaster.loadImage(String.format("gkmasModResource/img/idol/%s/stand/name.png", idolName));
        this.idolType = IdolData.getIdol(idolName).getType(this.skinIndex);
        this.idolStyle = IdolData.getIdol(idolName).getStyle(this.skinIndex);
        if(flag){
            this.specialCard = CardLibrary.getCard(gkmasMod_character, IdolData.getIdol(idolName).getCard(skinIndex)).makeCopy();
//            ((GkmasCard)this.specialCard).setIdolBackgroundTexture(idolName);
            if(GkmasMod.beat_hmsz>0)
                this.specialCard.upgrade();
        }
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
        if (AbstractDungeon.player instanceof IdolCharacter) {
            IdolCharacter k = (IdolCharacter)AbstractDungeon.player;
            k.idolName = idolName;
            k.idolData = IdolData.getIdol(idolName);
            k.skinIndex = skinIndex;
            k.refreshSkin();
    }
    }

    public int prevIndex() {
        return this.idolIndex - 1 < 0 ? IdolData.idolNames.length - 1 : this.idolIndex - 1;
    }

    public int nextIndex() {
        return this.idolIndex + 1 > IdolData.idolNames.length - 1 ? 0 : this.idolIndex + 1;
    }

    public int prevSameSkinIndex(){
        int tmp = this.skinIndex;
        String currentRelic = IdolData.getIdol(this.idolIndex).getRelic(tmp);
        tmp = tmp - 1 < 0 ? IdolData.getIdol(this.idolIndex).getSkinNum() - 1 : tmp - 1;
        while(!IdolData.getIdol(this.idolIndex).getRelic(tmp).equals(currentRelic)){
            tmp = tmp - 1 < 0 ? IdolData.getIdol(this.idolIndex).getSkinNum() - 1 : tmp - 1;
        }
        return tmp;
    }

    public int nextSameSkinIndex(){
        int tmp = this.skinIndex;
        String currentRelic = IdolData.getIdol(this.idolIndex).getRelic(tmp);
        tmp = tmp + 1 > IdolData.getIdol(this.idolIndex).getSkinNum() - 1 ? 0 : tmp + 1;
        while(!IdolData.getIdol(this.idolIndex).getRelic(tmp).equals(currentRelic)){
            tmp = tmp + 1 > IdolData.getIdol(this.idolIndex).getSkinNum() - 1 ? 0 : tmp + 1;
        }
        return tmp;
    }

    public int prevSkinIndex() {
        if(this.hideSameIdol){
            int tmp = this.skinIndex;
            String currentRelic = IdolData.getIdol(this.idolIndex).getRelic(tmp);
            String tmpRelic;
            while(true){
                tmp = tmp - 1 < 0 ? IdolData.getIdol(this.idolIndex).getSkinNum() - 1 : tmp - 1;
                tmpRelic = IdolData.getIdol(this.idolIndex).getRelic(tmp);
                boolean flag = false;
                for (int i = 0; i < tmp; i++) {
                    if(IdolData.getIdol(this.idolIndex).getRelic(i).equals(tmpRelic)){
                        flag = true;
                        break;
                    }
                }
                if(!flag&&!tmpRelic.equals(currentRelic)){
                    break;
                }
            }
            return tmp;
        }
        return this.skinIndex - 1 < 0 ? IdolData.getIdol(this.idolIndex).getSkinNum() - 1 : this.skinIndex - 1;
    }

    public int nextSkinIndex() {
        if(this.hideSameIdol){
            int tmp = this.skinIndex;
            String currentRelic = IdolData.getIdol(this.idolIndex).getRelic(tmp);
            String tmpRelic;
            while(true){
                tmp = tmp + 1 > IdolData.getIdol(this.idolIndex).getSkinNum() - 1 ? 0 : tmp + 1;
                tmpRelic = IdolData.getIdol(this.idolIndex).getRelic(tmp);
                boolean flag = false;
                for (int i = 0; i < tmp; i++) {
                    if(IdolData.getIdol(this.idolIndex).getRelic(i).equals(tmpRelic)){
                        flag = true;
                        break;
                    }
                }
                if(!flag&&!tmpRelic.equals(currentRelic)){
                    break;
                }
            }
            return tmp;
        }
        return this.skinIndex + 1 > IdolData.getIdol(this.idolIndex).getSkinNum() - 1 ? 0 : this.skinIndex + 1;
    }

    public void update() {
        float centerX = (float)Settings.WIDTH * 0.8F;
        float centerY = (float)Settings.HEIGHT * 0.5F;
        this.leftHb.move(centerX - 180.0F * Settings.scale, centerY + 50.0F * Settings.scale);
        this.rightHb.move(centerX + 180.0F * Settings.scale, centerY + 50.0F * Settings.scale);
        this.leftHb2.move(centerX - 180.0F * Settings.scale, centerY - 240.0F * Settings.scale);
        this.rightHb2.move(centerX + 180.0F * Settings.scale, centerY - 240.0F * Settings.scale);
        this.upHb.move(centerX, centerY + 180.0F * Settings.scale + 50.0F * Settings.scale);
        this.downHb.move(centerX, centerY - 180.0F * Settings.scale + 50.0F * Settings.scale);
        this.updateHb.move(centerX - 120.0F *Settings.scale, centerY + 150.0F * Settings.scale + 50.0F * Settings.scale);
        this.hideHb.move(centerX - 120.0F *Settings.scale, centerY - 150.0F * Settings.scale + 50.0F * Settings.scale);
        this.typeHb.move(centerX - 900.0F *Settings.scale, centerY  + 80.0F * Settings.scale);
        this.styleHb.move(centerX - 800.0F *Settings.scale + Settings.WIDTH*0.02F, centerY  + 80.0F * Settings.scale);
        this.achievementHb1.move(centerX - 900.0F *Settings.scale, centerY  + 150.0F * Settings.scale);
        this.achievementHb2.move(centerX - 800.0F *Settings.scale + Settings.WIDTH*0.02F, centerY  + 150.0F * Settings.scale);
        this.video_hb.move((Settings.WIDTH - 540.0F) / 2.0F+ 100.0F*Settings.scale, (Settings.HEIGHT - 960.0F) / 2.0F+ 200.0F*Settings.scale);
        this.updateInput();
        this.updateVideo();
    }

    private void updateInput() {
        if (CardCrawlGame.chosenCharacter == gkmasMod_character) {
            this.leftHb.update();
            this.rightHb.update();
            this.leftHb2.update();
            this.rightHb2.update();
            this.upHb.update();
            this.downHb.update();
            this.updateHb.update();
            this.hideHb.update();
            this.typeHb.update();
            this.styleHb.update();
            this.achievementHb1.update();
            this.achievementHb2.update();
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
                this.hasVideo = false;

                //GkmasMod.listeners.forEach(listener -> listener.onCardImgUpdate());
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
                this.hasVideo = false;
                //GkmasMod.listeners.forEach(listener -> listener.onCardImgUpdate());
            }

            if (this.leftHb2.clicked&&this.hideSameIdol) {
                this.leftHb2.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.skinIndex = this.prevSameSkinIndex();
                this.refresh();
                this.isClick = true;
                this.isClick2 = true;
                this.isClick3 = true;
                this.hasVideo = false;
            }

            if (this.rightHb2.clicked&&this.hideSameIdol) {
                this.rightHb2.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.skinIndex = this.nextSameSkinIndex();
                this.refresh();
                this.isClick = true;
                this.isClick2 = true;
                this.isClick3 = true;
                this.hasVideo = false;
            }

            if (this.upHb.clicked) {
                this.upHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.skinIndex = this.prevSkinIndex();
                this.refresh();
                this.isClick = true;
                this.isClick2 = true;
                this.isClick3 = true;
                this.hasVideo = false;
            }

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

            if (this.hideHb.clicked) {
                this.hideHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.hideSameIdol = !this.hideSameIdol;
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
                if (this.upHb.hovered) {
                    this.upHb.clickStarted = true;
                }
                if (this.downHb.hovered) {
                    this.downHb.clickStarted = true;
                }
                if (this.updateHb.hovered) {
                    this.updateHb.clickStarted = true;
                }
                if (this.hideHb.hovered) {
                    this.hideHb.clickStarted = true;
                }
                if (this.video_hb.hovered){
                    this.video_hb.clickStarted = true;
                }
            }
        }

    }

    public void updateVideo(){
        if(!hasVideo){
            clearVideo();
            this.videoPlayer =  VideoPlayerCreator.createVideoPlayer();
            if (this.videoPlayer == null) {
                clearVideo();
            }
            (new Thread(() -> {
                try {
                    this.videoPlayer.setLooping(true);
                    this.videoPlayer.setVolume(1.0f*Settings.MUSIC_VOLUME);
                    String videoPath = String.format("gkmasModResource/video/gacha/%s_%s.webm",idolName,IdolData.getIdol(idolName).getVideo(skinIndex));
                    if(Gdx.files.local(videoPath).exists())
                        this.videoPlayer.play(Gdx.files.local(videoPath));
                } catch (Exception e) {
                    e.printStackTrace();
                    clearVideo();
                }
            })).start();
            hasVideo = true;
        }
        this.video_hb.update();
//        if (this.video_hb.hovered && InputHelper.justClickedLeft) {
//            InputHelper.justClickedLeft = false;
//            this.video_hb.clickStarted = true;
//        }
        if (this.video_hb.clicked) {
            this.video_hb.clicked = false;
            if (this.videoPlayer != null) {
                this.videoPlayer.dispose();
                this.videoPlayer = null;
            }
        }
        if(this.videoPlayer != null){
            this.videoPlayer.update();
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
        if(GkmasMod.cardRate>0.7f)
            sb.draw(achievementImg2,this.achievementHb1.cX-24.0F, this.achievementHb1.cY-24.0F);
        else if(GkmasMod.cardRate>0.4f)
            sb.draw(achievementImg1,this.achievementHb1.cX-24.0F, this.achievementHb1.cY-24.0F);
        if(GkmasMod.beat_hmsz>0)
            sb.draw(achievementImg3,this.achievementHb2.cX-24.0F, this.achievementHb2.cY-24.0F);
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, selectIdolHint, centerX, centerY + 300.0F * Settings.scale, Color.WHITE, 1.25F);
        Color color = Settings.GOLD_COLOR.cpy();
        color.a /= 2.0F;
        float dist = 100.0F * Settings.scale;

//        if(idolName!=IdolData.jsna)
            renderCardPreviewInSingleView(sb);
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

        if(this.hideSameIdol){
            if(this.leftHb2.hovered){
                sb.setColor(Color.LIGHT_GRAY);
            }else{
                sb.setColor(Color.WHITE);
            }
            sb.draw(ImageMaster.CF_LEFT_ARROW, this.leftHb2.cX - 24.0F, this.leftHb2.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
            if(this.rightHb2.hovered){
                sb.setColor(Color.LIGHT_GRAY);
            }else{
                sb.setColor(Color.WHITE);
            }
            sb.draw(ImageMaster.CF_RIGHT_ARROW, this.rightHb2.cX - 24.0F, this.rightHb2.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
        }

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
            TipHelper.renderGenericTip( this.updateHb.cX + 20.F,  this.updateHb.cY + 20.F, this.updateHintTitle, this.updateHint);
        } else {
            sb.setColor(Color.WHITE);
        }

        if(this.hideHb.hovered){
            sb.setColor(Color.LIGHT_GRAY);
            TipHelper.renderGenericTip( this.hideHb.cX + 20.F,  this.hideHb.cY + 20.F, this.hideHintTitle, this.hideHint);
        } else {
            sb.setColor(Color.WHITE);
        }
        if(this.hideSameIdol){
            sb.draw(ImageMaster.OPTION_TOGGLE,this.hideHb.cX - 24.0F, this.hideHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
            sb.draw(ImageMaster.OPTION_TOGGLE_ON,this.hideHb.cX - 24.0F, this.hideHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
        }
        else
            sb.draw(ImageMaster.OPTION_TOGGLE,this.hideHb.cX - 24.0F, this.hideHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);

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

        if(this.achievementHb1.hovered){
            sb.setColor(Color.LIGHT_GRAY);
            if(GkmasMod.cardRate>0.7f)
                TipHelper.renderGenericTip(this.achievementHb1.cX+ 20.F, this.achievementHb1.cY + 20.F, this.achievementHintName2, this.achievementHint2);
            else if(GkmasMod.cardRate>0.4f){
                TipHelper.renderGenericTip(this.achievementHb1.cX+ 20.F, this.achievementHb1.cY + 20.F, this.achievementHintName1, this.achievementHint1);
            }
        }
        else{
            sb.setColor(Color.WHITE);
        }

        if(this.achievementHb2.hovered){
            sb.setColor(Color.LIGHT_GRAY);
            if(GkmasMod.beat_hmsz>0)
                TipHelper.renderGenericTip(this.achievementHb2.cX+ 20.F, this.achievementHb2.cY + 20.F, this.achievementHintName3, this.achievementHint3);

        }
        else{
            sb.setColor(Color.WHITE);
        }

        this.rightHb.render(sb);
        this.leftHb.render(sb);
        this.upHb.render(sb);
        this.downHb.render(sb);
        this.updateHb.render(sb);
        this.hideHb.render(sb);
        this.typeHb.render(sb);
        this.styleHb.render(sb);
        if(GkmasMod.cardRate>0.4f)
            this.achievementHb1.render(sb);
        if(GkmasMod.beat_hmsz>0)
            this.achievementHb2.render(sb);

        if(this.videoPlayer != null){
            renderVideo(sb);
        }
    }

    public void renderSkin(SpriteBatch sb, float x, float y) {
        if(this.usedImg != null){
            sb.draw(this.usedImg,x,y);
        }
    }

    public void renderVideo(SpriteBatch sb){
        Texture texture = this.videoPlayer.getTexture();
        if (texture != null) {
            float width = texture.getWidth() * Settings.scale/2;
            float height = texture.getHeight() * Settings.scale/2;
            float x = this.video_hb.cX;
            float y = this.video_hb.cY;
            sb.setColor(Color.WHITE);
            sb.draw(texture, x, y, width, height);
        }
    }

    public void renderCardPreviewInSingleView(SpriteBatch sb) {
        this.specialCard.current_x = Settings.WIDTH * 0.35F;
        this.specialCard.current_y = Settings.HEIGHT * 0.35F;
        this.specialCard.render(sb);
    }


    public void onLoad(int[] args) {
        if(args != null && args.length == 3){
            this.idolIndex = args[0];
            this.skinIndex = args[1];
            this.updateIndex = args[2];
            refresh();
            GkmasMod.listeners.forEach(listener -> listener.onCardImgUpdate());
            try {
                GkmasMod.cardRate = PlayerHelper.getCardRate();
                GkmasMod.config.setFloat("cardRate", GkmasMod.cardRate);
                GkmasMod.config.setInt("beat_hmsz", GkmasMod.beat_hmsz);
                GkmasMod.config.setBool("onlyModBoss", GkmasMod.onlyModBoss);
                GkmasMod.config.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

   }
    public int[] onSave() {

        try {
            GkmasMod.cardRate = PlayerHelper.getCardRate();
            GkmasMod.config.setFloat("cardRate", GkmasMod.cardRate);
            GkmasMod.config.setInt("beat_hmsz", GkmasMod.beat_hmsz);
            GkmasMod.config.setBool("onlyModBoss", GkmasMod.onlyModBoss);
            GkmasMod.config.save();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new int[]{this.idolIndex,this.skinIndex,this.updateIndex};
    }

    public void clearVideo() {
        if (this.videoPlayer != null) {
            this.videoPlayer.dispose();
            this.videoPlayer = null;
        }
    }

    static {
        Inst = new SkinSelectScreen();
    }

}
