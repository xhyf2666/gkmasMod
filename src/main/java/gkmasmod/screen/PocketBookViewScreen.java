package gkmasmod.screen;

import basemod.BaseMod;
import basemod.abstracts.CustomScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import gkmasmod.characters.IdolCharacter;
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
    private static Texture bg =  ImageMaster.loadImage("gkmasModResource/img/report/bg.png");
    private static Texture bgLine =  ImageMaster.loadImage("gkmasModResource/img/report/bg_line.png");
    private static Texture bgLabel1 =  ImageMaster.loadImage("gkmasModResource/img/report/bg_label1.png");
    private static Texture bgLabel2 =  ImageMaster.loadImage("gkmasModResource/img/report/bg_label2.png");
    private static Texture lineShortImg = ImageMaster.loadImage("gkmasModResource/img/report/line_short.png");
    private static Texture lineLongImg = ImageMaster.loadImage("gkmasModResource/img/report/line_long.png");
    private static Texture clearImg = ImageMaster.loadImage("gkmasModResource/img/report/clear.png");
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
    private int planStep;
    private static final int x_offset = Settings.WIDTH/2;
    private static final int y_offset = Settings.HEIGHT/2;
    private int stage = 0;
    public Hitbox barHb;
    public Hitbox planHb;

    private int[] planTypes;
    private int[] planRequires;
    private int vo;
    private int da;
    private int vi;
    private String vo_rate;
    private String da_rate;
    private String vi_rate;
    private String vo_rank;
    private String da_rank;
    private String vi_rank;
    private String vo_require;
    private String da_require;
    private String vi_require;
    private String vo_damageRate;
    private String da_damageRate;
    private String vi_damageRate;
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
    private Texture idolDisplayImg3;
    private Texture idolDisplayImg4;
    private Texture idolDisplayImg5;
    private Texture idolBarImg;
    private static Texture idolPlanImg = ImageMaster.loadImage("gkmasModResource/img/report/trainPlan.png");
    private boolean splitInterest;
    private boolean splitSkill;
    private String idolPlan1;
    private String idolPlan2;
    private String idolPlan3;
    private String idolRequire;
    private String planRequire1;
    private String planRequire2;
    private String planRequire3;
    private String planReward1;
    private String planReward2;
    private String planReward3;
    private static String[] type2String = new String[]{"VO", "DA", "VI"};
    
    private float proceedX =1760.0F * Settings.xScale;
    private float proceedY = 200.0F * Settings.scale;

    private Hitbox hb = new Hitbox(proceedX, proceedY, 280.0F * Settings.scale, 156.0F * Settings.scale);
    private float wavyTimer = 0.0F;

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
        AbstractDungeon.overlayMenu.cancelButton.show(TEXT[10]);
        AbstractDungeon.screen = curScreen();
        AbstractDungeon.isScreenUp = true;
    }

    public void open(String idolName, int step , int planStep) {
        this.idolName = idolName;
        this.step = step;
        this.planStep = planStep;
        this.prevRelic = null;
        this.nextRelic = null;
        this.prevHb = null;
        this.nextHb = null;
        this.popupHb = new Hitbox(550.0F * Settings.scale, 680.0F * Settings.scale);
        this.popupHb.move((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F);
        this.barHb = new Hitbox(120.0F * Settings.scale, 70.0F * Settings.scale);
        this.planHb = new Hitbox(120.0F * Settings.scale, 70.0F * Settings.scale);
        this.barHb.move(1080.0F * Settings.xScale, 950.0F * Settings.scale);
        this.planHb.move(450.0F * Settings.xScale, 720.0F * Settings.scale);
        this.hb.move(proceedX, proceedY);
        this.isOpen = true;
        this.group = null;
        this.fadeTimer = 0.25F;
        this.fadeColor.a = 0.0F;
        if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.NONE)
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        System.out.println(AbstractDungeon.previousScreen);
        if(step>9)
            step = 9;
        this.idolHeaderImg = ImageMaster.loadImage(String.format("gkmasModResource/img/report/header/%s_001.png", idolName));
        this.idolGraphImg = ImageMaster.loadImage(String.format("gkmasModResource/img/report/growth/cn/%s_00%d.png", idolName,step));
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
        this.planRequire1 = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolPlan1",idolName)).TEXT[0];
        this.planRequire2 = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolPlan2",idolName)).TEXT[0];
        this.planRequire3 = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolPlan3",idolName)).TEXT[0];
        this.planReward1 = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolReward1",idolName)).TEXT[0];
        this.planReward2 = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolReward2",idolName)).TEXT[0];
        this.planReward3 = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolReward3",idolName)).TEXT[0];
        this.idolPlan1 = CardCrawlGame.languagePack.getUIString("gkmasMod:PlanReward1").TEXT[0];
        this.idolPlan2 = CardCrawlGame.languagePack.getUIString("gkmasMod:PlanReward2").TEXT[0];
        this.idolPlan3 = CardCrawlGame.languagePack.getUIString("gkmasMod:PlanReward3").TEXT[0];
        this.idolRequire = CardCrawlGame.languagePack.getUIString("gkmasMod:PlanRequire").TEXT[0];
        this.planTypes = IdolData.getIdol(idolName).getPlanTypes();
        this.planRequires = IdolData.getIdol(idolName).getPlanRequires();
        this.splitInterest = this.idolInterest.indexOf(" NL ") > 0;
        this.splitSkill = this.idolSkill.indexOf(" NL ") > 0;
        this.idolComments = IdolData.getIdol(idolName).getComments(step);
        this.idolBarImg = ImageMaster.loadImage(String.format("gkmasModResource/img/report/bar/%s_bar.png", idolName));
        this.reopen();
        IdolCharacter idol = (IdolCharacter) AbstractDungeon.player;
        this.vo = idol.getVo();
        this.da = idol.getDa();
        this.vi = idol.getVi();
        DecimalFormat df = new DecimalFormat("0.00%");
        this.vo_rate = df.format(idol.getVoRate());
        this.da_rate = df.format(idol.getDaRate());
        this.vi_rate = df.format(idol.getViRate());
        this.vo_rank = RankHelper.getRank(vo);
        this.da_rank = RankHelper.getRank(da);
        this.vi_rank = RankHelper.getRank(vi);
        double[] tmp = idol.calculateDamageRates();
        this.vo_damageRate = df.format(tmp[0]);
        this.da_damageRate = df.format(tmp[1]);
        this.vi_damageRate = df.format(tmp[2]);

        this.vo_require = String.valueOf(IdolData.getIdol(idolName).getThreeSizeRequire(0));
        this.da_require = String.valueOf(IdolData.getIdol(idolName).getThreeSizeRequire(1));
        this.vi_require = String.valueOf(IdolData.getIdol(idolName).getThreeSizeRequire(2));
        this.voRankImg = ImageMaster.loadImage(String.format("gkmasModResource/img/rank/rank_small_%s.png", NameHelper.rankNormalize(vo_rank)));
        this.daRankImg = ImageMaster.loadImage(String.format("gkmasModResource/img/rank/rank_small_%s.png", NameHelper.rankNormalize(da_rank)));
        this.viRankImg = ImageMaster.loadImage(String.format("gkmasModResource/img/rank/rank_small_%s.png", NameHelper.rankNormalize(vi_rank)));
        this.voUpImg = ImageMaster.loadImage("gkmasModResource/img/UI/ThreeSize/vo_up.png");
        this.daUpImg = ImageMaster.loadImage("gkmasModResource/img/UI/ThreeSize/da_up.png");
        this.viUpImg = ImageMaster.loadImage("gkmasModResource/img/UI/ThreeSize/vi_up.png");
        int left = RankHelper.getRankBoundary(vo)[0];
        int right = RankHelper.getRankBoundary(vo)[1];
        this.voBg = ImageMaster.loadImage(String.format("gkmasModResource/img/UI/ThreeSize/circle/vo_%d.png",(int)(1.0F*(vo-left)/(right-left)*15)));
        left = RankHelper.getRankBoundary(da)[0];
        right = RankHelper.getRankBoundary(da)[1];
        this.daBg = ImageMaster.loadImage(String.format("gkmasModResource/img/UI/ThreeSize/circle/da_%d.png",(int)(1.0F*(da-left)/(right-left)*15)));
        left = RankHelper.getRankBoundary(vi)[0];
        right = RankHelper.getRankBoundary(vi)[1];
        this.viBg = ImageMaster.loadImage(String.format("gkmasModResource/img/UI/ThreeSize/circle/vi_%d.png",(int)(1.0F*(vi-left)/(right-left)*15)));
        this.voIconImg = ImageMaster.loadImage("gkmasModResource/img/UI/ThreeSize/vo.png");
        this.daIconImg = ImageMaster.loadImage("gkmasModResource/img/UI/ThreeSize/da.png");
        this.viIconImg = ImageMaster.loadImage("gkmasModResource/img/UI/ThreeSize/vi.png");
        this.idolEmojiImg1 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/emoji/%s_01.png", idolName));
        this.idolEmojiImg2 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/emoji/%s_02.png", idolName));
        this.idolCommentImg = new ArrayList<>();
        for (int i = 0; i < this.idolComments.length; i++) {
            this.idolCommentImg.add(ImageMaster.loadImage(String.format("gkmasModResource/img/report/comment/jp/%s_00%s.png", idolName, idolComments[i])));
        }
        this.idolTextImg = ImageMaster.loadImage(String.format("gkmasModResource/img/report/text/jp/%s_00%s.png", idolName,IdolData.getIdol(idolName).getText(step)));
        this.idolDisplayImg1 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/display/%s_001.png", idolName));
        this.idolDisplayImg2 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/display/%s_002.png", idolName));
        this.idolDisplayImg3 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/display/%s_003.png", idolName));
        this.idolDisplayImg4 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/display/%s_004.png", idolName));
        this.idolDisplayImg5 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/display/%s_005.png", idolName));
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
        this.updateFade();
        this.barHb.update();
        this.planHb.update();
        this.wavyTimer += Gdx.graphics.getDeltaTime() * 3.0F;
        this.hb.update();
        if (this.hb.hovered && InputHelper.justClickedLeft) {
            CardCrawlGame.sound.play("UI_CLICK_1");
            this.stage = (this.stage + 1) % 2;
        }
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
        if(this.stage==0){
            this.renderLine(sb);
            this.renderGrowth(sb);
            this.renderValue(sb);
        }
        if(this.stage==1){
            this.renderBar(sb);
            this.renderPlan(sb);
            this.renderDamageRate(sb);
        }

        this.renderDisplay(sb);
        this.renderButton(sb);

    }

    @Override
    public void openingSettings() {

    }

    private void renderReportBg(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(bg, (float)Settings.WIDTH / 2.0F - 720.0F, (float)Settings.HEIGHT / 2.0F - 540.0F, 960.0F, 540.0F, 1500, 1024, Settings.scale, Settings.scale, 0.0F, 0, 0, 1500, 1024, false, false);
        if(this.stage==0)
            sb.draw(bgLabel1, (float)Settings.WIDTH / 2.0F - 720.0F, (float)Settings.HEIGHT / 2.0F - 540.0F, 960.0F, 540.0F, 1500, 1024, Settings.scale, Settings.scale, 0.0F, 0, 0, 1500, 1024, false, false);
        else if(this.stage==1)
            sb.draw(bgLabel2, (float)Settings.WIDTH / 2.0F - 720.0F, (float)Settings.HEIGHT / 2.0F - 540.0F, 960.0F, 540.0F, 1500, 1024, Settings.scale, Settings.scale, 0.0F, 0, 0, 1500, 1024, false, false);
    }

    private void renderHeader(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(idolHeaderImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 155.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 747.0F*Settings.scale, 128, 128, 192, 192, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);
    }

    private void renderLine(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, idolDisplayName, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+590.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+920.0F*Settings.scale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[0], (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+520.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+925.0F*Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineShortImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 370.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 784.0F*Settings.scale, 128, 8, 256, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 16, false, false);

        FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, idolClass, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+820.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+920.0F*Settings.scale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[1], (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+750.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+925.0F*Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineShortImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 600.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 784.0F*Settings.scale, 128, 8, 256, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 16, false, false);

        FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, idolBirthday, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+590.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+820.0F*Settings.scale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[2], (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+520.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+825.0F*Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineShortImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 370.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 884.0F*Settings.scale, 128, 8, 256, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 16, false, false);

        FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, idolAge, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+820.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+820.0F*Settings.scale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[3], (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+750.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+825.0F*Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineShortImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 600.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 884.0F*Settings.scale, 128, 8, 256, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 16, false, false);

        FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, idolHeight, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+1035.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+920.0F*Settings.scale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[4], (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+965.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+925.0F*Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineShortImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 815.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 884.0F*Settings.scale, 128, 8, 256, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 16, false, false);

        FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, idolWeight, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+1265.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+920.0F*Settings.scale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[5], (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+1195.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+925.0F*Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineShortImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 1045.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 884.0F*Settings.scale, 128, 8, 256, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 16, false, false);

        FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, idolThreeSize, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+1035.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+820.0F*Settings.scale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[6], (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+965.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+825.0F*Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineShortImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 815.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 784.0F*Settings.scale, 128, 8, 256, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 16, false, false);

        if(splitInterest)
            FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, idolInterest, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+1265.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+845.0F*Settings.scale, 10000.0F, 25.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.7F);
        else
            FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, idolInterest, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+1265.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+820.0F*Settings.scale, 10000.0F, 25.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.7F);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[7], (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+1195.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+825.0F*Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineLongImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 1045.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 784.0F*Settings.scale, 200, 8, 320, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 400, 16, false, false);

        FontHelper.renderSmartText(sb, FontHelper.topPanelInfoFont, TEXT[8], (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+965.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+725.0F*Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineShortImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 815.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 684.0F*Settings.scale, 128, 8, 256, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 16, false, false);

        if(splitSkill)
            FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, idolSkill, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+1265.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+745.0F*Settings.scale, 10000.0F, 25.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.7F);
        else
            FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, idolSkill, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+1265.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+720.0F*Settings.scale, 10000.0F, 25.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.7F);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[9], (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+1195.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+725.0F*Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineLongImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 1045.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 684.0F*Settings.scale, 200, 8, 320, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 400, 16, false, false);
    }

    private void renderGrowth(SpriteBatch sb){
        sb.setColor(Color.WHITE);
        sb.draw(bgLine, (float)Settings.WIDTH / 2.0F - 720.0F, (float)Settings.HEIGHT / 2.0F - 540.0F, 960.0F, 540.0F, 1500, 1024, Settings.scale, Settings.scale, 0.0F, 0, 0, 1500, 1024, false, false);
        sb.draw(this.idolGraphImg, (float)Settings.WIDTH / 2.0F - 720.0F, (float)Settings.HEIGHT / 2.0F - 540.0F, 960.0F, 540.0F, 1500, 1024, Settings.scale, Settings.scale, 0.0F, 0, 0, 1500, 1024, false, false);
        for (int i = 0; i < this.idolCommentImg.size(); i++) {
            sb.draw(this.idolCommentImg.get(i), (float)Settings.WIDTH / 2.0F - 720.0F, (float)Settings.HEIGHT / 2.0F - 540.0F, 960.0F, 540.0F, 1500, 1024, Settings.scale, Settings.scale, 0.0F, 0, 0, 1500, 1024, false, false);
        }
        sb.draw(this.idolTextImg, (float)Settings.WIDTH / 2.0F - 720.0F, (float)Settings.HEIGHT / 2.0F - 540.0F, 960.0F, 540.0F, 1500, 1024, Settings.scale, Settings.scale, 0.0F, 0, 0, 1500, 1024, false, false);
    }

    private void renderValue(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(this.voBg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 810.0F*Settings.xScale+30.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 524.0F*Settings.scale, 128, 128, 128, 128, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);
        sb.draw(this.daBg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 980.0F*Settings.xScale+30.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 524.0F*Settings.scale, 128, 128, 128, 128, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);
        sb.draw(this.viBg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 1150.0F*Settings.xScale+30.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 524.0F*Settings.scale, 128, 128, 128, 128, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);

        sb.draw(this.voRankImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 760.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 540.0F*Settings.scale, 64, 64, 88, 88, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
        sb.draw(this.daRankImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 930.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 540.0F*Settings.scale, 64, 64, 88, 88,Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128,  false, false);
        sb.draw(this.viRankImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 1100.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 540.0F*Settings.scale, 64, 64, 88, 88, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128,  false, false);

        sb.draw(this.voIconImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 810.0F*Settings.xScale+30.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 490.0F*Settings.scale, 128, 128, 52, 52, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);
        sb.draw(this.daIconImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 980.0F*Settings.xScale+30.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 490.0F*Settings.scale, 128, 128, 52, 52, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);
        sb.draw(this.viIconImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 1150.0F*Settings.xScale+30.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 490.0F*Settings.scale, 128, 128, 52, 52, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);

        sb.draw(this.voUpImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 800.0F*Settings.xScale+30.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 430.0F*Settings.scale, 64, 64, 64, 64, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
        sb.draw(this.daUpImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 970.0F*Settings.xScale+30.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 430.0F*Settings.scale, 64, 64, 64, 64, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128,  false, false);
        sb.draw(this.viUpImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 1140.0F*Settings.xScale+30.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 430.0F*Settings.scale, 64, 64, 64, 64, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);

        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, String.valueOf(vo), (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+1032.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+450.0F*Settings.scale+75.0F, 10000.0F, 20.0F * Settings.scale, Settings.RED_RELIC_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, String.valueOf(da), (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+1202.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+450.0F*Settings.scale+75.0F, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, String.valueOf(vi), (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+1372.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+450.0F*Settings.scale+75.0F, 10000.0F, 20.0F * Settings.scale, Settings.LIGHT_YELLOW_COLOR);

        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, vo_rate, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+1025.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+440.0F*Settings.scale+30.0F, 10000.0F, 20.0F * Settings.scale, Settings.RED_RELIC_COLOR,1.0F);
        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, da_rate, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+1195.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+440.0F*Settings.scale+30.0F, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,1.0F);
        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, vi_rate, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+1365.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+440.0F*Settings.scale+30.0F, 10000.0F, 20.0F * Settings.scale, Settings.LIGHT_YELLOW_COLOR,1.0F);
    }

    private void renderDamageRate(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(this.voBg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 810.0F*Settings.xScale+30.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 524.0F*Settings.scale, 128, 128, 128, 128, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);
        sb.draw(this.daBg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 980.0F*Settings.xScale+30.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 524.0F*Settings.scale, 128, 128, 128, 128, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);
        sb.draw(this.viBg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 1150.0F*Settings.xScale+30.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 524.0F*Settings.scale, 128, 128, 128, 128, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);

        sb.draw(this.voRankImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 760.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 540.0F*Settings.scale, 64, 64, 88, 88, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
        sb.draw(this.daRankImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 930.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 540.0F*Settings.scale, 64, 64, 88, 88,Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128,  false, false);
        sb.draw(this.viRankImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 1100.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 540.0F*Settings.scale, 64, 64, 88, 88, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128,  false, false);

        sb.draw(this.voIconImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 810.0F*Settings.xScale+30.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 490.0F*Settings.scale, 128, 128, 52, 52, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);
        sb.draw(this.daIconImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 980.0F*Settings.xScale+30.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 490.0F*Settings.scale, 128, 128, 52, 52, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);
        sb.draw(this.viIconImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 1150.0F*Settings.xScale+30.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 490.0F*Settings.scale, 128, 128, 52, 52, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);

        //sb.draw(this.voUpImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 800.0F*Settings.xScale+30.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 430.0F*Settings.scale, 64, 64, 64, 64, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
        //sb.draw(this.daUpImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 970.0F*Settings.xScale+30.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 430.0F*Settings.scale, 64, 64, 64, 64, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128,  false, false);
        //sb.draw(this.viUpImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 1140.0F*Settings.xScale+30.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 430.0F*Settings.scale, 64, 64, 64, 64, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);

        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, String.valueOf(vo), (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+1032.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+450.0F*Settings.scale+75.0F, 10000.0F, 20.0F * Settings.scale, Settings.RED_RELIC_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, String.valueOf(da), (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+1202.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+450.0F*Settings.scale+75.0F, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, String.valueOf(vi), (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+1372.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+450.0F*Settings.scale+75.0F, 10000.0F, 20.0F * Settings.scale, Settings.LIGHT_YELLOW_COLOR);

        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, vo_damageRate, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+1025.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+440.0F*Settings.scale+30.0F, 10000.0F, 20.0F * Settings.scale, Settings.RED_RELIC_COLOR,1.0F);
        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, da_damageRate, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+1195.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+440.0F*Settings.scale+30.0F, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,1.0F);
        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, vi_damageRate, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+1365.0F*Settings.xScale+100.F, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+440.0F*Settings.scale+30.0F, 10000.0F, 20.0F * Settings.scale, Settings.LIGHT_YELLOW_COLOR,1.0F);
    }


    private void renderDisplay(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        if(this.stage==0){
            sb.draw(this.idolDisplayImg1, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 804.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 120.0F*Settings.scale, 108, 144, 216, 288, Settings.scale, Settings.scale, 0.0F, 0, 0, 216, 288, false, false);
            sb.draw(this.idolDisplayImg2, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 1024.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 120.0F*Settings.scale, 108, 144, 216, 288, Settings.scale, Settings.scale, 0.0F, 0, 0, 216, 288,false, false);
        }
        else{
            sb.draw(this.idolDisplayImg3, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 804.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 120.0F*Settings.scale, 108, 144, 216, 288, Settings.scale, Settings.scale, 0.0F, 0, 0, 216, 288, false, false);
            sb.draw(this.idolDisplayImg4, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 1024.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 120.0F*Settings.scale, 108, 144, 216, 288, Settings.scale, Settings.scale, 0.0F, 0, 0, 216, 288,false, false);
            sb.draw(this.idolDisplayImg5, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 370.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 670.0F*Settings.scale, 180, 135, 360, 270, Settings.scale, Settings.scale, 0.0F, 0, 0, 360, 270,false, false);
        }
        sb.draw(this.idolEmojiImg1, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 1252.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 200.0F*Settings.scale, 64, 64, 128, 128, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
        sb.draw(this.idolEmojiImg2, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 1252.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 50.0F*Settings.scale, 64, 64, 128, 128, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
    }

    private void renderBar(SpriteBatch sb){
        sb.setColor(Color.WHITE);
        sb.draw(this.idolBarImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 820.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 650.0F*Settings.scale, 225, 125, 450, 250, Settings.scale, Settings.scale, 0.0F, 0, 0, 450, 250, false, false);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[12], this.barHb.cX-24.0F, this.barHb.cY-24.0F, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, this.vo_require, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 890.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 740.0F*Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, this.da_require, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 1010.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 740.0F*Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, this.vi_require, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 1140.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 740.0F*Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        if(this.barHb.hovered){
            sb.setColor(Color.LIGHT_GRAY);
            TipHelper.renderGenericTip( this.barHb.cX + 20.F,  this.barHb.cY + 20.F, TEXT[12], TEXT[13]);
        }
        else{
            sb.setColor(Color.WHITE);
        }
    }

    private void renderPlan(SpriteBatch sb){
        sb.setColor(Color.WHITE);

        if(this.planStep>0)
            sb.draw(this.clearImg, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+820.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+460.0F*Settings.scale+75.0F, 64, 64, 128, 128, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
        if(this.planStep>1)
            sb.draw(this.clearImg, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+820.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+260.0F*Settings.scale+75.0F, 64, 64, 128, 128, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
        if(this.planStep>2)
            sb.draw(this.clearImg, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+820.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+60.0F*Settings.scale+75.0F, 64, 64, 128, 128, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);


        sb.draw(this.idolPlanImg, (float)Settings.WIDTH / 2.0F - 720.0F*Settings.xScale + 200.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale + 70.0F*Settings.scale, 270, 300, 540, 600, Settings.scale, Settings.scale, 0.0F, 0, 0, 540, 600, false, false);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[14], this.planHb.cX-24.0F, this.planHb.cY-24.0F,10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);

        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, this.planRequire1, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+470.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+550.0F*Settings.scale+75.0F, 10000.0F, 20.0F * Settings.scale, Settings.RED_RELIC_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, this.planRequire2, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+470.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+350.0F*Settings.scale+75.0F, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, this.planRequire3, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+470.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+150.0F*Settings.scale+75.0F, 10000.0F, 20.0F * Settings.scale, Settings.LIGHT_YELLOW_COLOR);

        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, this.planReward1, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+470.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+480.0F*Settings.scale+75.0F, 10000.0F, 20.0F * Settings.scale, Settings.RED_TEXT_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, this.planReward2, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+470.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+280.0F*Settings.scale+75.0F, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_TEXT_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, this.planReward3, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+470.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+80.0F*Settings.scale+75.0F, 10000.0F, 20.0F * Settings.scale, Settings.LIGHT_YELLOW_COLOR);

        FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, this.idolPlan1, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+630.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+470.0F*Settings.scale+75.0F, 10000.0F, 20.0F * Settings.scale, Settings.GREEN_TEXT_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, this.idolPlan2, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+630.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+270.0F*Settings.scale+75.0F, 10000.0F, 20.0F * Settings.scale, Settings.GREEN_TEXT_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, this.idolPlan3, (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+630.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+70.0F*Settings.scale+75.0F, 10000.0F, 20.0F * Settings.scale, Settings.GREEN_TEXT_COLOR,0.8F);

        FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, String.format(this.idolRequire,type2String[this.planTypes[0]],this.planRequires[0]), (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+650.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+540.0F*Settings.scale+75.0F, 10000.0F, 20.0F * Settings.scale, Settings.GREEN_TEXT_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, String.format(this.idolRequire,type2String[this.planTypes[1]],this.planRequires[1]), (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+650.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+340.0F*Settings.scale+75.0F, 10000.0F, 20.0F * Settings.scale, Settings.GREEN_TEXT_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, String.format(this.idolRequire,type2String[this.planTypes[2]],this.planRequires[2]), (float)Settings.WIDTH / 2.0F - 960.0F*Settings.xScale+650.0F*Settings.xScale, (float)Settings.HEIGHT / 2.0F - 540.0F*Settings.scale+140.0F*Settings.scale+75.0F, 10000.0F, 20.0F * Settings.scale, Settings.GREEN_TEXT_COLOR,0.8F);

        if(this.planHb.hovered){
            sb.setColor(Color.LIGHT_GRAY);
            TipHelper.renderGenericTip( this.planHb.cX + 20.F,  this.planHb.cY + 20.F, TEXT[14], TEXT[15]);
        }
        else{
            sb.setColor(Color.WHITE);
        }
    }

    public void renderButton(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.PROCEED_BUTTON_SHADOW, proceedX - 256.0F, proceedY - 256.0F, 256.0F, 256.0F, 512.0F, 512.0F, Settings.scale * 1.1F +

                MathUtils.cos(this.wavyTimer) / 50.0F, Settings.scale * 1.1F +
                MathUtils.cos(this.wavyTimer) / 50.0F, 0.0F, 0, 0, 512, 512, false, false);
        sb.setColor(new Color(1.0F, 0.9F, 0.2F, MathUtils.cos(this.wavyTimer) / 5.0F + 0.6F));
        sb.draw(ImageMaster.PROCEED_BUTTON_OUTLINE, proceedX - 256.0F, proceedY - 256.0F, 256.0F, 256.0F, 512.0F, 512.0F, Settings.scale * 1.1F +

                MathUtils.cos(this.wavyTimer) / 50.0F, Settings.scale * 1.1F +
                MathUtils.cos(this.wavyTimer) / 50.0F, 0.0F, 0, 0, 512, 512, false, false);
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.PROCEED_BUTTON, proceedX - 256.0F, proceedY - 256.0F, 256.0F, 256.0F, 512.0F, 512.0F, Settings.scale * 1.1F +
                MathUtils.cos(this.wavyTimer) / 50.0F, Settings.scale * 1.1F +
                MathUtils.cos(this.wavyTimer) / 50.0F, 0.0F, 0, 0, 512, 512, false, false);
        FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, TEXT[11], proceedX, proceedY, Settings.CREAM_COLOR);
        if (this.hb.hovered) {
            sb.setBlendFunction(770, 1);
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.3F));
            sb.draw(ImageMaster.PROCEED_BUTTON, proceedX - 256.0F, proceedY - 256.0F, 256.0F, 256.0F, 512.0F, 512.0F, Settings.scale * 1.1F +
                    MathUtils.cos(this.wavyTimer) / 50.0F, Settings.scale * 1.1F +
                    MathUtils.cos(this.wavyTimer) / 50.0F, 0.0F, 0, 0, 512, 512, false, false);
            sb.setBlendFunction(770, 771);
        }
    }


    static {
        DESC_LINE_SPACING = 30.0F * Settings.scale;
        DESC_LINE_WIDTH = 418.0F * Settings.scale;
    }
}
