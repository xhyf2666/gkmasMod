package gkmasmod.ui;

import basemod.abstracts.CustomScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import gkmasmod.relics.PocketBook;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.RankHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PocketBookViewScreen extends CustomScreen {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("gkmasMod:PocketBookUI").TEXT;
    public boolean isOpen = false;
    private ArrayList<AbstractRelic> group;
    private AbstractRelic relic;
    private AbstractRelic prevRelic;
    private AbstractRelic nextRelic;
    private static final int W = 128;
    private Texture relicFrameImg;
    private Texture largeImg;
    private float fadeTimer = 0.0F;
    private Color fadeColor;
    private Hitbox popupHb;
    private Hitbox prevHb;
    private Hitbox nextHb;
    private String rarityLabel;
    private static final String LARGE_IMG_DIR = "images/largeRelics/";
    private static final float DESC_LINE_SPACING;
    private static final float DESC_LINE_WIDTH;
    private final float RELIC_OFFSET_Y;
    private String idolName;
    private static Texture bg =  ImageMaster.loadImage("img/report/bg.png");
    private static Texture lineShortImg = ImageMaster.loadImage("img/report/line_short.png");
    private static Texture lineLongImg = ImageMaster.loadImage("img/report/line_long.png");
    private static Texture idolHeaderImg;
    private static Texture idolGraphImg;
    private String idolDisplayName;
    private String idolClass;
    private String idolBirthday;
    private String idolAge;
    private String idolHeight;
    private String idolWeight;
    private String idolThreeSize;
    private String idolInterest;
    private String idolFriendship;
    private String idolSkill;
    private String[] idolComments;
    private int step;

    private int vo;
    private int da;
    private int vi;
    private String vo_rate;
    private String da_rate;
    private String vi_rate;
    private String vo_rank;
    private String da_rank;
    private String vi_rank;
    private Texture voRankImg;
    private Texture daRankImg;
    private Texture viRankImg;
    private Texture voUpImg;
    private Texture daUpImg;
    private Texture viUpImg;
    private Texture voBg;
    private Texture daBg;
    private Texture viBg;
    private Texture voIconImg;
    private Texture daIconImg;
    private Texture viIconImg;
    private Texture idolEmojiImg1;
    private Texture idolEmojiImg2;
    private ArrayList<Texture> idolCommentImg;
    private Texture idolTextImg;
    private Texture idolDisplayImg1;
    private Texture idolDisplayImg2;
    private boolean splitInterest;
    private boolean splitSkill;


    public PocketBookViewScreen() {
        this.fadeColor = Color.BLACK.cpy();
        this.rarityLabel = "";
        this.RELIC_OFFSET_Y = 76.0F * Settings.scale;
    }

    public static class Enum
    {
        @SpireEnum
        public static AbstractDungeon.CurrentScreen PocketBookView_Screen;
    }

    @Override
    public CurrentScreen curScreen() {
        return Enum.PocketBookView_Screen;
    }

    @Override
    public void reopen() {
        AbstractDungeon.screen = curScreen();
        AbstractDungeon.isScreenUp = true;
    }

    public void open(String idolName, int step) {
        this.idolName = idolName;
        this.step = step;
        this.prevRelic = null;
        this.nextRelic = null;
        this.prevHb = null;
        this.nextHb = null;
        this.popupHb = new Hitbox(550.0F * Settings.scale, 680.0F * Settings.scale);
        this.popupHb.move((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F);
        this.isOpen = true;
        this.group = null;
        this.fadeTimer = 0.25F;
        this.fadeColor.a = 0.0F;
        if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.NONE)
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        System.out.println(AbstractDungeon.previousScreen);
        this.idolHeaderImg = ImageMaster.loadImage(String.format("img/report/header/%s_001.png", idolName));
        this.idolGraphImg = ImageMaster.loadImage(String.format("img/report/growth/cn/%s_00%d.png", idolName,step));
        this.idolDisplayName = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolName",idolName)).TEXT[0];
        this.idolClass = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolClass",idolName)).TEXT[0];
        this.idolBirthday = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolBirthday",idolName)).TEXT[0];
        this.idolAge = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolAge",idolName)).TEXT[0];
        this.idolHeight = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolHeight",idolName)).TEXT[0];
        this.idolWeight = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolWeight",idolName)).TEXT[0];
        this.idolThreeSize = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolThreeSize",idolName)).TEXT[0];
        this.idolInterest = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolInterest",idolName)).TEXT[0];
        //this.idolFriendship = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolFriendship",idolName)).TEXT[0];
        this.idolSkill = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolSkill",idolName)).TEXT[0];
        this.splitInterest = this.idolInterest.indexOf(" NL ") > 0;
        this.splitSkill = this.idolSkill.indexOf(" NL ") > 0;
        this.idolComments = IdolData.getIdol(idolName).getComments(step);
        this.reopen();
        this.vo = PocketBook.vo;
        this.da = PocketBook.da;
        this.vi = PocketBook.vi;
        DecimalFormat df = new DecimalFormat("0.00%");
        this.vo_rate = df.format(PocketBook.vo_rate);
        this.da_rate = df.format(PocketBook.da_rate);
        this.vi_rate = df.format(PocketBook.vi_rate);
        this.vo_rank = RankHelper.getRank(vo);
        this.da_rank = RankHelper.getRank(da);
        this.vi_rank = RankHelper.getRank(vi);
        this.voRankImg = ImageMaster.loadImage(String.format("img/rank/rank_small_%s.png", NameHelper.rankNormalize(vo_rank)));
        this.daRankImg = ImageMaster.loadImage(String.format("img/rank/rank_small_%s.png", NameHelper.rankNormalize(da_rank)));
        this.viRankImg = ImageMaster.loadImage(String.format("img/rank/rank_small_%s.png", NameHelper.rankNormalize(vi_rank)));
        this.voUpImg = ImageMaster.loadImage("img/UI/ThreeSize/vo_up.png");
        this.daUpImg = ImageMaster.loadImage("img/UI/ThreeSize/da_up.png");
        this.viUpImg = ImageMaster.loadImage("img/UI/ThreeSize/vi_up.png");
        int left = RankHelper.getRankBoundary(vo)[0];
        int right = RankHelper.getRankBoundary(vo)[1];
        this.voBg = ImageMaster.loadImage(String.format("img/UI/ThreeSize/circle/vo_%d.png",(int)(1.0F*(vo-left)/(right-left)*15)));
        left = RankHelper.getRankBoundary(da)[0];
        right = RankHelper.getRankBoundary(da)[1];
        this.daBg = ImageMaster.loadImage(String.format("img/UI/ThreeSize/circle/da_%d.png",(int)(1.0F*(da-left)/(right-left)*15)));
        left = RankHelper.getRankBoundary(vi)[0];
        right = RankHelper.getRankBoundary(vi)[1];
        this.viBg = ImageMaster.loadImage(String.format("img/UI/ThreeSize/circle/vi_%d.png",(int)(1.0F*(vi-left)/(right-left)*15)));
        this.voIconImg = ImageMaster.loadImage("img/UI/ThreeSize/vo.png");
        this.daIconImg = ImageMaster.loadImage("img/UI/ThreeSize/da.png");
        this.viIconImg = ImageMaster.loadImage("img/UI/ThreeSize/vi.png");
        this.idolEmojiImg1 = ImageMaster.loadImage(String.format("img/report/emoji/%s_01.png", idolName));
        this.idolEmojiImg2 = ImageMaster.loadImage(String.format("img/report/emoji/%s_02.png", idolName));
        this.idolCommentImg = new ArrayList<>();
        for (int i = 0; i < this.idolComments.length; i++) {
            this.idolCommentImg.add(ImageMaster.loadImage(String.format("img/report/comment/jp/%s_00%s.png", idolName, idolComments[i])));
        }
        this.idolTextImg = ImageMaster.loadImage(String.format("img/report/text/jp/%s_00%s.png", idolName,IdolData.getIdol(idolName).getText(step)));
        this.idolDisplayImg1 = ImageMaster.loadImage(String.format("img/report/display/%s_001.png", idolName));
        this.idolDisplayImg2 = ImageMaster.loadImage(String.format("img/report/display/%s_002.png", idolName));
    }


    public void close() {
        this.isOpen = false;
        if (AbstractDungeon.previousScreen == null || AbstractDungeon.previousScreen == AbstractDungeon.CurrentScreen.NONE) {
            AbstractDungeon.isScreenUp = false;
        } else {
            AbstractDungeon.screen = AbstractDungeon.previousScreen;
        }
    }

    public void update() {
        this.popupHb.update();
        this.updateInput();
        this.updateFade();
    }

    private void updateInput() {
        if (InputHelper.justClickedLeft) {
            if (this.prevRelic != null && this.prevHb.hovered) {
                this.prevHb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
                return;
            }

            if (this.nextRelic != null && this.nextHb.hovered) {
                this.nextHb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
                return;
            }
        }

        if (InputHelper.justReleasedClickLeft) {
            if (!this.popupHb.hovered) {
                this.close();
                FontHelper.ClearSRVFontTextures();
            }
        } else if (InputHelper.pressedEscape || CInputActionSet.cancel.isJustPressed()) {
            CInputActionSet.cancel.unpress();
            InputHelper.pressedEscape = false;
            if (AbstractDungeon.screen == null || AbstractDungeon.screen == CurrentScreen.NONE) {
                AbstractDungeon.isScreenUp = false;
            }

            this.close();
            FontHelper.ClearSRVFontTextures();
        }


    }


    private void updateFade() {
        this.fadeTimer -= Gdx.graphics.getDeltaTime();
        if (this.fadeTimer < 0.0F) {
            this.fadeTimer = 0.0F;
        }

        this.fadeColor.a = Interpolation.pow2In.apply(0.9F, 0.0F, this.fadeTimer * 4.0F);
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.fadeColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float)Settings.WIDTH, (float)Settings.HEIGHT);
        this.renderReportBg(sb);
        this.renderHeader(sb);
        this.renderLine(sb);
        this.renderGrowth(sb);
        this.renderValue(sb);
        this.renderDisplay(sb);


    }

    @Override
    public void openingSettings() {

    }

    private void renderReportBg(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(bg, (float)Settings.WIDTH / 2.0F - 720.0F, (float)Settings.HEIGHT / 2.0F - 540.0F, 960.0F, 540.0F, 1500, 1024, Settings.scale, Settings.scale, 0.0F, 0, 0, 1500, 1024, false, false);
    }

    private void renderHeader(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(idolHeaderImg, (float)Settings.WIDTH / 2.0F - 720.0F + 155.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 747.0F, 128, 128, 192, 192, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);
    }

    private void renderLine(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, idolDisplayName, 690.0F*Settings.scale, 920.0F*Settings.scale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[0], 620.0F*Settings.scale, 925.0F*Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineShortImg, (float)Settings.WIDTH / 2.0F - 720.0F + 370.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 784.0F, 128, 8, 256, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 16, false, false);

        FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, idolClass, 920.0F*Settings.scale, 920.0F*Settings.scale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[1], 850.0F*Settings.scale, 925.0F*Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineShortImg, (float)Settings.WIDTH / 2.0F - 720.0F + 600.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 784.0F, 128, 8, 256, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 16, false, false);

        FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, idolBirthday, 690.0F*Settings.scale, 820.0F*Settings.scale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[2], 620.0F*Settings.scale, 825.0F*Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineShortImg, (float)Settings.WIDTH / 2.0F - 720.0F + 370.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 884.0F, 128, 8, 256, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 16, false, false);

        FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, idolAge, 920.0F*Settings.scale, 820.0F*Settings.scale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[3], 850.0F*Settings.scale, 825.0F*Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineShortImg, (float)Settings.WIDTH / 2.0F - 720.0F + 600.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 884.0F, 128, 8, 256, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 16, false, false);

        FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, idolHeight, 1135.0F*Settings.scale, 920.0F*Settings.scale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[4], 1065.0F*Settings.scale, 925.0F*Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineShortImg, (float)Settings.WIDTH / 2.0F - 720.0F + 815.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 884.0F, 128, 8, 256, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 16, false, false);

        FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, idolWeight, 1365.0F*Settings.scale, 920.0F*Settings.scale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[5], 1295.0F*Settings.scale, 925.0F*Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineShortImg, (float)Settings.WIDTH / 2.0F - 720.0F + 1045.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 884.0F, 128, 8, 256, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 16, false, false);

        FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, idolThreeSize, 1135.0F*Settings.scale, 820.0F*Settings.scale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[6], 1065.0F*Settings.scale, 825.0F*Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineShortImg, (float)Settings.WIDTH / 2.0F - 720.0F + 815.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 784.0F, 128, 8, 256, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 16, false, false);

        if(splitInterest)
            FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, idolInterest, 1365.0F*Settings.scale, 845.0F*Settings.scale, 10000.0F, 25.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.7F);
        else
            FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, idolInterest, 1365.0F*Settings.scale, 820.0F*Settings.scale, 10000.0F, 25.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.7F);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[7], 1295.0F*Settings.scale, 825.0F*Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineLongImg, (float)Settings.WIDTH / 2.0F - 720.0F + 1045.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 784.0F, 200, 8, 320, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 400, 16, false, false);

        FontHelper.renderSmartText(sb, FontHelper.topPanelInfoFont, TEXT[8], 1065.0F*Settings.scale, 725.0F*Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineShortImg, (float)Settings.WIDTH / 2.0F - 720.0F + 815.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 684.0F, 128, 8, 256, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 16, false, false);
        //sb.draw(this.line_long, (float)Settings.WIDTH / 2.0F - 720.0F + 370.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 784.0F, 200, 8, 400, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 400, 16, false, false);

        if(splitSkill)
            FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, idolSkill, 1365.0F*Settings.scale, 745.0F*Settings.scale, 10000.0F, 25.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.7F);
        else
            FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, idolSkill, 1365.0F*Settings.scale, 720.0F*Settings.scale, 10000.0F, 25.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.7F);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[9], 1295.0F*Settings.scale, 725.0F*Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineLongImg, (float)Settings.WIDTH / 2.0F - 720.0F + 1045.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 684.0F, 200, 8, 320, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 400, 16, false, false);
    }

    private void renderGrowth(SpriteBatch sb){
        sb.draw(this.idolGraphImg, (float)Settings.WIDTH / 2.0F - 720.0F, (float)Settings.HEIGHT / 2.0F - 540.0F, 960.0F, 540.0F, 1500, 1024, Settings.scale, Settings.scale, 0.0F, 0, 0, 1500, 1024, false, false);
        for (int i = 0; i < this.idolCommentImg.size(); i++) {
            sb.draw(this.idolCommentImg.get(i), (float)Settings.WIDTH / 2.0F - 720.0F, (float)Settings.HEIGHT / 2.0F - 540.0F, 960.0F, 540.0F, 1500, 1024, Settings.scale, Settings.scale, 0.0F, 0, 0, 1500, 1024, false, false);
        }
        sb.draw(this.idolTextImg, (float)Settings.WIDTH / 2.0F - 720.0F, (float)Settings.HEIGHT / 2.0F - 540.0F, 960.0F, 540.0F, 1500, 1024, Settings.scale, Settings.scale, 0.0F, 0, 0, 1500, 1024, false, false);
    }

    private void renderValue(SpriteBatch sb) {
        sb.draw(this.voBg, (float)Settings.WIDTH / 2.0F - 720.0F + 840.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 524.0F, 128, 128, 128, 128, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);
        sb.draw(this.daBg, (float)Settings.WIDTH / 2.0F - 720.0F + 1010.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 524.0F, 128, 128, 128, 128, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);
        sb.draw(this.viBg, (float)Settings.WIDTH / 2.0F - 720.0F + 1180.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 524.0F, 128, 128, 128, 128, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);

        sb.draw(this.voRankImg, (float)Settings.WIDTH / 2.0F - 720.0F + 860.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 540.0F, 64, 64, 88, 88, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
        sb.draw(this.daRankImg, (float)Settings.WIDTH / 2.0F - 720.0F + 1030.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 540.0F, 64, 64, 88, 88,Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128,  false, false);
        sb.draw(this.viRankImg, (float)Settings.WIDTH / 2.0F - 720.0F + 1200.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 540.0F, 64, 64, 88, 88, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128,  false, false);

        sb.draw(this.voIconImg, (float)Settings.WIDTH / 2.0F - 720.0F + 840.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 490.0F, 128, 128, 52, 52, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);
        sb.draw(this.daIconImg, (float)Settings.WIDTH / 2.0F - 720.0F + 1010.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 490.0F, 128, 128, 52, 52, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);
        sb.draw(this.viIconImg, (float)Settings.WIDTH / 2.0F - 720.0F + 1180.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 490.0F, 128, 128, 52, 52, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);

        sb.draw(this.voUpImg, (float)Settings.WIDTH / 2.0F - 720.0F + 830.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 430.0F, 64, 64, 64, 64, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
        sb.draw(this.daUpImg, (float)Settings.WIDTH / 2.0F - 720.0F + 1000.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 430.0F, 64, 64, 64, 64, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128,  false, false);
        sb.draw(this.viUpImg, (float)Settings.WIDTH / 2.0F - 720.0F + 1170.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 430.0F, 64, 64, 64, 64, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);

        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, String.valueOf(vo), 1132.0F*Settings.scale, 525.0F*Settings.scale, 10000.0F, 20.0F * Settings.scale, Settings.RED_RELIC_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, String.valueOf(da), 1302.0F*Settings.scale, 525.0F*Settings.scale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, String.valueOf(vi), 1472.0F*Settings.scale, 525.0F*Settings.scale, 10000.0F, 20.0F * Settings.scale, Settings.LIGHT_YELLOW_COLOR);


        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, vo_rate, 1125.0F*Settings.scale, 470.0F*Settings.scale, 10000.0F, 20.0F * Settings.scale, Settings.RED_RELIC_COLOR,1.0F);
        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, da_rate, 1295.0F*Settings.scale, 470.0F*Settings.scale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,1.0F);
        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, vi_rate, 1465.0F*Settings.scale, 470.0F*Settings.scale, 10000.0F, 20.0F * Settings.scale, Settings.LIGHT_YELLOW_COLOR,1.0F);
    }

    private void renderDisplay(SpriteBatch sb) {
//        sb.draw(this.idolDisplayImg1, (float)Settings.WIDTH / 2.0F - 720.0F + 754.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 120.0F, 540, 720, 216, 288, Settings.scale, Settings.scale, 0.0F, 0, 0, 1080, 1440, false, false);
//        sb.draw(this.idolDisplayImg2, (float)Settings.WIDTH / 2.0F - 720.0F + 1024.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 120.0F, 540, 720, 216, 288, Settings.scale, Settings.scale, 0.0F, 0, 0, 1080, 1440, false, false);
        sb.draw(this.idolDisplayImg1, (float)Settings.WIDTH / 2.0F - 720.0F + 804.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 120.0F, 108, 144, 216, 288, Settings.scale, Settings.scale, 0.0F, 0, 0, 216, 288, false, false);
        sb.draw(this.idolDisplayImg2, (float)Settings.WIDTH / 2.0F - 720.0F + 1024.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 120.0F, 108, 144, 216, 288, Settings.scale, Settings.scale, 0.0F, 0, 0, 216, 288,false, false);
        sb.draw(this.idolEmojiImg1, (float)Settings.WIDTH / 2.0F - 720.0F + 1252.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 200.0F, 64, 64, 128, 128, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
        sb.draw(this.idolEmojiImg2, (float)Settings.WIDTH / 2.0F - 720.0F + 1252.0F, (float)Settings.HEIGHT / 2.0F - 540.0F + 50.0F, 64, 64, 128, 128, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
    }

    private void renderDescription(SpriteBatch sb) {
        if (UnlockTracker.isRelicLocked(this.relic.relicId)) {
            FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N, TEXT[11], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F - 140.0F * Settings.scale, Settings.CREAM_COLOR, 1.0F);
        } else if (this.relic.isSeen) {
            FontHelper.renderSmartText(sb, FontHelper.cardDescFont_N, this.relic.description, (float)Settings.WIDTH / 2.0F - 200.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F - 140.0F * Settings.scale - FontHelper.getSmartHeight(FontHelper.cardDescFont_N, this.relic.description, DESC_LINE_WIDTH, DESC_LINE_SPACING) / 2.0F, DESC_LINE_WIDTH, DESC_LINE_SPACING, Settings.CREAM_COLOR);
        } else {
            FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N, TEXT[12], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F - 140.0F * Settings.scale, Settings.CREAM_COLOR, 1.0F);
        }

    }

    private void renderQuote(SpriteBatch sb) {
        if (this.relic.isSeen) {
            if (this.relic.flavorText != null) {
                FontHelper.renderWrappedText(sb, FontHelper.SRV_quoteFont, this.relic.flavorText, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F - 310.0F * Settings.scale, DESC_LINE_WIDTH, Settings.CREAM_COLOR, 1.0F);
            } else {
                FontHelper.renderWrappedText(sb, FontHelper.SRV_quoteFont, "\"Missing quote...\"", (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F - 300.0F * Settings.scale, DESC_LINE_WIDTH, Settings.CREAM_COLOR, 1.0F);
            }
        }

    }

    private void renderTips(SpriteBatch sb) {
        if (this.relic.isSeen) {
            ArrayList<PowerTip> t = new ArrayList();
            if (this.relic.tips.size() > 1) {
                for(int i = 1; i < this.relic.tips.size(); ++i) {
                    t.add(this.relic.tips.get(i));
                }
            }

            if (!t.isEmpty()) {
                TipHelper.queuePowerTips((float)Settings.WIDTH / 2.0F + 340.0F * Settings.scale, 420.0F * Settings.scale, t);
            }
        }

    }

    static {
        DESC_LINE_SPACING = 30.0F * Settings.scale;
        DESC_LINE_WIDTH = 418.0F * Settings.scale;
    }
}
