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
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.downfall.bosses.*;
import gkmasmod.relics.ChristmasLion;
import gkmasmod.relics.PocketBook;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.RankHelper;
import gkmasmod.utils.ThreeSizeHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PocketBookViewScreen extends CustomScreen {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("gkmasMod:PocketBookUI").TEXT;
    public boolean isOpen = false;
    private float fadeTimer = 0.0F;
    private Color fadeColor;
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
    private float x_offset = Settings.xScale * 240.0F;
    private float y_offset = ((float) Settings.HEIGHT-1024*Settings.yScale-50)/2;
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
    private Texture idolEmojiImg3;
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
    private Texture friendImg1;
    private Texture friendImg2;
    private Texture friendImg3;
    
    private float proceedX =1760.0F * Settings.xScale;
    private float proceedY = 200.0F * Settings.scale;

    private Hitbox hb = new Hitbox(proceedX, proceedY, 280.0F * Settings.scale, 156.0F * Settings.scale);
    private float wavyTimer = 0.0F;

    public PocketBookViewScreen() {
        this.fadeColor = Color.BLACK.cpy();
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
        this.barHb = new Hitbox(120.0F * Settings.scale, 70.0F * Settings.scale);
        this.planHb = new Hitbox(120.0F * Settings.scale, 70.0F * Settings.scale);
        this.barHb.move(1080.0F * Settings.xScale, 950.0F * Settings.yScale);
        this.planHb.move(450.0F * Settings.xScale, 720.0F * Settings.yScale);
        this.hb.move(proceedX, proceedY);
        this.isOpen = true;
        this.fadeTimer = 0.25F;
        this.fadeColor.a = 0.0F;
        if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.NONE)
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        if(step>9)
            step = 9;
        if(AbstractDungeon.player instanceof IdolCharacter){
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
            this.vo_require = String.valueOf(IdolData.getIdol(idolName).getThreeSizeRequire(0));
            this.da_require = String.valueOf(IdolData.getIdol(idolName).getThreeSizeRequire(1));
            this.vi_require = String.valueOf(IdolData.getIdol(idolName).getThreeSizeRequire(2));
            if(idolName.equals(IdolData.hume)&&IdolData.getIdol(idolName).getRelic(SkinSelectScreen.Inst.skinIndex).equals(ChristmasLion.ID)){
                this.vo_require = String.valueOf(IdolData.getIdol(idolName).getAnotherThreeSizeRequire(0));
                this.da_require = String.valueOf(IdolData.getIdol(idolName).getAnotherThreeSizeRequire(1));
                this.vi_require = String.valueOf(IdolData.getIdol(idolName).getAnotherThreeSizeRequire(2));
                this.idolBarImg = ImageMaster.loadImage(String.format("gkmasModResource/img/report/bar/%s_bar_2.png", idolName));
            }
            this.idolTextImg = ImageMaster.loadImage(String.format("gkmasModResource/img/report/text/jp/%s_00%s.png", idolName,IdolData.getIdol(idolName).getText(step)));
            this.idolEmojiImg1 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/emoji/%s_01.png", idolName));
            this.idolEmojiImg2 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/emoji/%s_02.png", idolName));
            this.idolEmojiImg3 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/emoji/%s_03.png", idolName));
            this.idolDisplayImg1 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/display/%s_001.png", idolName));
            this.idolDisplayImg2 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/display/%s_002.png", idolName));
            this.idolDisplayImg3 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/display/%s_003.png", idolName));
            this.idolDisplayImg4 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/display/%s_004.png", idolName));
            this.idolDisplayImg5 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/display/%s_005.png", idolName));
        }
        else if(AbstractDungeon.player instanceof MisuzuCharacter){
            String tmpIdolName = IdolData.hmsz;
            this.idolHeaderImg = ImageMaster.loadImage(String.format("gkmasModResource/img/report/header/%s_001.png", tmpIdolName));
            this.idolGraphImg = ImageMaster.loadImage(String.format("gkmasModResource/img/report/growth/cn/%s_00%d.png", tmpIdolName,1));
            this.idolDisplayName = AbstractDungeon.player.title;
            this.idolClass = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolClass",tmpIdolName)).TEXT[0];
            this.idolBirthday = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolBirthday",tmpIdolName)).TEXT[0];
            this.idolAge = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolAge",tmpIdolName)).TEXT[0];
            this.idolHeight = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolHeight",tmpIdolName)).TEXT[0];
            this.idolWeight = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolWeight",tmpIdolName)).TEXT[0];
            this.idolThreeSize = "??";
            this.idolInterest = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolInterest",tmpIdolName)).TEXT[0];
            this.idolSkill = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolSkill",tmpIdolName)).TEXT[0];
            this.planRequire1 = "??";
            this.planRequire2 = "??";
            this.planRequire3 = "??";
            this.planReward1 = "??";
            this.planReward2 = "??";
            this.planReward3 = "??";
            this.idolPlan1 = "??";
            this.idolPlan2 = "??";
            this.idolPlan3 = "??";
            this.idolRequire = "??";
            this.planTypes = IdolData.hmszData.getPlanTypes();
            this.planRequires = IdolData.hmszData.getPlanRequires();
            this.splitInterest = this.idolInterest.indexOf(" NL ") > 0;
            this.splitSkill = this.idolSkill.indexOf(" NL ") > 0;
            this.idolComments = new String[0];
            this.idolBarImg = ImageMaster.loadImage(String.format("gkmasModResource/img/report/bar/%s_bar.png", "amao"));
            this.vo_require = String.valueOf(IdolData.hmszData.getThreeSizeRequire(0));
            this.da_require = String.valueOf(IdolData.hmszData.getThreeSizeRequire(1));
            this.vi_require = String.valueOf(IdolData.hmszData.getThreeSizeRequire(2));
            this.idolTextImg = ImageMaster.loadImage(String.format("gkmasModResource/img/report/text/jp/%s_00%d.png", tmpIdolName,1));
            this.idolEmojiImg1 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/emoji/%s_01.png", tmpIdolName));
            this.idolEmojiImg2 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/emoji/%s_02.png", tmpIdolName));
            this.idolEmojiImg3 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/emoji/%s_03.png", tmpIdolName));
            this.idolDisplayImg1 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/display/%s_001.png", tmpIdolName));
            this.idolDisplayImg2 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/display/%s_002.png", tmpIdolName));
            this.idolDisplayImg3 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/display/%s_003.png", tmpIdolName));
            this.idolDisplayImg4 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/display/%s_004.png", tmpIdolName));
            this.idolDisplayImg5 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/display/%s_005.png", tmpIdolName));
        }
        else{
            this.idolHeaderImg = ImageMaster.loadImage(String.format("gkmasModResource/img/report/header/%s_001.png", idolName));
            this.idolGraphImg = ImageMaster.loadImage(String.format("gkmasModResource/img/report/growth/cn/%s_00%d.png", idolName,step));
            this.idolDisplayName = AbstractDungeon.player.title;
            this.idolClass = "??";
            this.idolBirthday = "??";
            this.idolAge = "??";
            this.idolHeight = "??";
            this.idolWeight = "??";
            this.idolThreeSize = "??";
            this.idolInterest = "??";
            //this.idolFriendship = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolFriendship",idolName)).TEXT[0];
            this.idolSkill = "??";
            this.planRequire1 = "??";
            this.planRequire2 = "??";
            this.planRequire3 = "??";
            this.planReward1 = "??";
            this.planReward2 = "??";
            this.planReward3 = "??";
            this.idolPlan1 = "??";
            this.idolPlan2 = "??";
            this.idolPlan3 = "??";
            this.idolRequire = "??";
            this.planTypes = IdolData.getIdol(idolName).getPlanTypes();
            this.planRequires = IdolData.getIdol(idolName).getPlanRequires();
            this.splitInterest = false;
            this.splitSkill = false;
            this.idolComments = IdolData.getIdol(idolName).getComments(step);
            this.idolBarImg = ImageMaster.loadImage(String.format("gkmasModResource/img/report/bar/%s_bar.png", "special"));
            this.vo_require = String.valueOf(1000);
            this.da_require = String.valueOf(1000);
            this.vi_require = String.valueOf(1000);
            this.idolTextImg = ImageMaster.loadImage(String.format("gkmasModResource/img/report/text/jp/%s_00%s.png", idolName,IdolData.getIdol(idolName).getText(step)));
            this.idolEmojiImg1 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/emoji/%s_01.png", idolName));
            this.idolEmojiImg2 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/emoji/%s_02.png", idolName));
            this.idolEmojiImg3 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/emoji/%s_03.png", idolName));
            this.idolDisplayImg1 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/display/%s_001.png", idolName));
            this.idolDisplayImg2 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/display/%s_002.png", idolName));
            this.idolDisplayImg3 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/display/%s_003.png", idolName));
            this.idolDisplayImg4 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/display/%s_004.png", idolName));
            this.idolDisplayImg5 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/display/%s_005.png", idolName));
        }
        this.reopen();
        this.vo = ThreeSizeHelper.getVo();
        this.da = ThreeSizeHelper.getDa();
        this.vi = ThreeSizeHelper.getVi();
        DecimalFormat df = new DecimalFormat("0.00%");
        this.vo_rate = df.format(ThreeSizeHelper.getVoRate());
        this.da_rate = df.format(ThreeSizeHelper.getDaRate());
        this.vi_rate = df.format(ThreeSizeHelper.getViRate());
        this.vo_rank = RankHelper.getRank(vo);
        this.da_rank = RankHelper.getRank(da);
        this.vi_rank = RankHelper.getRank(vi);
        double[] tmp = ThreeSizeHelper.calculateDamageRates();
        this.vo_damageRate = df.format(tmp[0]);
        this.da_damageRate = df.format(tmp[1]);
        this.vi_damageRate = df.format(tmp[2]);

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
        this.idolCommentImg = new ArrayList<>();
        for (int i = 0; i < this.idolComments.length; i++) {
            this.idolCommentImg.add(ImageMaster.loadImage(String.format("gkmasModResource/img/report/comment/jp/%s_00%s.png", idolName, idolComments[i])));
        }

        generateBoss();
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
//            this.renderFriend(sb);
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
        // 1920,1080,1    : 240,0 -- 1740,0   0.125--0.90625
        // 1680,1050,0.875:
        sb.draw(bg, x_offset, y_offset,1500*Settings.xScale, 1024*Settings.yScale);
        if(this.stage==0)
            sb.draw(bgLabel1, x_offset, y_offset,1500*Settings.xScale, 1024*Settings.yScale);
        else if(this.stage==1)
            sb.draw(bgLabel2, x_offset, y_offset,1500*Settings.xScale, 1024*Settings.yScale);
    }

    private void renderHeader(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(idolHeaderImg, x_offset + 155.0F*Settings.xScale, y_offset + 747.0F*Settings.yScale, 192*Settings.xScale, 192*Settings.yScale);
    }

    private void renderLine(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, idolDisplayName, x_offset+450.0F*Settings.xScale, y_offset+920.0F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[0], x_offset+380.0F*Settings.xScale, y_offset+925.0F*Settings.yScale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineShortImg, x_offset + 370.0F*Settings.xScale, y_offset + 784.0F*Settings.yScale, 128, 8, 256, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 16, false, false);

        FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, idolClass, x_offset+680.0F*Settings.xScale, y_offset+920.0F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[1], x_offset+610.0F*Settings.xScale, y_offset+925.0F*Settings.yScale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineShortImg, x_offset + 600.0F*Settings.xScale, y_offset + 784.0F*Settings.yScale, 128, 8, 256, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 16, false, false);

        FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, idolBirthday, x_offset+450.0F*Settings.xScale, y_offset+820.0F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[2], x_offset+380.0F*Settings.xScale, y_offset+825.0F*Settings.yScale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineShortImg, x_offset + 370.0F*Settings.xScale, y_offset + 884.0F*Settings.yScale, 128, 8, 256, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 16, false, false);

        FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, idolAge, x_offset+680.0F*Settings.xScale, y_offset+820.0F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[3], x_offset+610.0F*Settings.xScale, y_offset+825.0F*Settings.yScale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineShortImg, x_offset + 600.0F*Settings.xScale, y_offset + 884.0F*Settings.yScale, 128, 8, 256, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 16, false, false);

        FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, idolHeight, x_offset+895.0F*Settings.xScale, y_offset+920.0F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[4], x_offset+825.0F*Settings.xScale, y_offset+925.0F*Settings.yScale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineShortImg, x_offset + 815.0F*Settings.xScale, y_offset + 884.0F*Settings.yScale, 128, 8, 256, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 16, false, false);

        FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, idolWeight, x_offset+1125.0F*Settings.xScale, y_offset+920.0F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[5], x_offset+1055.0F*Settings.xScale, y_offset+925.0F*Settings.yScale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineShortImg, x_offset + 1045.0F*Settings.xScale, y_offset + 884.0F*Settings.yScale, 128, 8, 256, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 16, false, false);

        FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, idolThreeSize, x_offset+895.0F*Settings.xScale, y_offset+820.0F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[6], x_offset+825.0F*Settings.xScale, y_offset+825.0F*Settings.yScale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineShortImg, x_offset + 815.0F*Settings.xScale, y_offset + 784.0F*Settings.yScale, 128, 8, 256, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 16, false, false);

        if(splitInterest)
            FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, idolInterest, x_offset+1125.0F*Settings.xScale, y_offset+845.0F*Settings.yScale, 10000.0F, 25.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.7F);
        else
            FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, idolInterest, x_offset+1125.0F*Settings.xScale, y_offset+820.0F*Settings.yScale, 10000.0F, 25.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.7F);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[7], x_offset+1055.0F*Settings.xScale, y_offset+825.0F*Settings.yScale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineLongImg, x_offset + 1045.0F*Settings.xScale, y_offset + 784.0F*Settings.yScale, 200, 8, 320, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 400, 16, false, false);

        FontHelper.renderSmartText(sb, FontHelper.topPanelInfoFont, TEXT[8], x_offset+825.0F*Settings.xScale, y_offset+725.0F*Settings.yScale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineShortImg, x_offset + 815.0F*Settings.xScale, y_offset + 684.0F*Settings.yScale, 128, 8, 256, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 16, false, false);
        sb.draw(this.friendImg1, x_offset + 880.0F*Settings.xScale, y_offset + 684.0F*Settings.yScale, 64*Settings.xScale,64*Settings.yScale);
        sb.draw(this.friendImg2, x_offset + 930.0F*Settings.xScale, y_offset + 684.0F*Settings.yScale, 64*Settings.xScale,64*Settings.yScale);
        sb.draw(this.friendImg3, x_offset + 980.0F*Settings.xScale, y_offset + 684.0F*Settings.yScale, 64*Settings.xScale,64*Settings.yScale);
        if(splitSkill)
            FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, idolSkill, x_offset+1125.0F*Settings.xScale, y_offset+745.0F*Settings.yScale, 10000.0F, 25.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.7F);
        else
            FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, idolSkill, x_offset+1125.0F*Settings.xScale, y_offset+720.0F*Settings.yScale, 10000.0F, 25.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,0.7F);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[9], x_offset+1055.0F*Settings.xScale, y_offset+725.0F*Settings.yScale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        sb.draw(this.lineLongImg, x_offset + 1045.0F*Settings.xScale, y_offset + 684.0F*Settings.yScale, 200, 8, 320, 16, Settings.scale, Settings.scale, 0.0F, 0, 0, 400, 16, false, false);
    }

    private void renderGrowth(SpriteBatch sb){
        sb.setColor(Color.WHITE);
        sb.draw(bgLine, x_offset, y_offset ,1500*Settings.xScale, 1024*Settings.yScale);
        sb.draw(this.idolGraphImg, x_offset, y_offset,1500*Settings.xScale, 1024*Settings.yScale);
        for (int i = 0; i < this.idolCommentImg.size(); i++) {
            sb.draw(this.idolCommentImg.get(i), x_offset, y_offset,1500*Settings.xScale, 1024*Settings.yScale);
        }
        sb.draw(this.idolTextImg, x_offset, y_offset,1500*Settings.xScale, 1024*Settings.yScale);
    }

    private void renderValue(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(this.voBg, x_offset + 840.0F*Settings.xScale, y_offset + 524.0F*Settings.yScale, 128*Settings.xScale,128*Settings.yScale);
        sb.draw(this.daBg, x_offset + 1010.0F*Settings.xScale, y_offset + 524.0F*Settings.yScale, 128*Settings.xScale,128*Settings.yScale);
        sb.draw(this.viBg, x_offset + 1180.0F*Settings.xScale, y_offset + 524.0F*Settings.yScale, 128*Settings.xScale,128*Settings.yScale);

        sb.draw(this.voRankImg, x_offset + 860.0F*Settings.xScale, y_offset + 540.0F*Settings.yScale, 88*Settings.xScale,88*Settings.yScale);
        sb.draw(this.daRankImg, x_offset + 1030.0F*Settings.xScale, y_offset + 540.0F*Settings.yScale, 88*Settings.xScale,88*Settings.yScale);
        sb.draw(this.viRankImg, x_offset + 1200.0F*Settings.xScale, y_offset + 540.0F*Settings.yScale, 88*Settings.xScale,88*Settings.yScale);

        sb.draw(this.voIconImg, x_offset + 840.0F*Settings.xScale, y_offset + 490.0F*Settings.yScale, 52*Settings.xScale,52*Settings.yScale);
        sb.draw(this.daIconImg, x_offset + 1010.0F*Settings.xScale, y_offset + 490.0F*Settings.yScale, 52*Settings.xScale,52*Settings.yScale);
        sb.draw(this.viIconImg, x_offset + 1180.0F*Settings.xScale, y_offset + 490.0F*Settings.yScale, 52*Settings.xScale,52*Settings.yScale);

        sb.draw(this.voUpImg, x_offset + 830.0F*Settings.xScale, y_offset + 430.0F*Settings.yScale, 64*Settings.xScale,64*Settings.yScale);
        sb.draw(this.daUpImg, x_offset + 1000.0F*Settings.xScale, y_offset + 430.0F*Settings.yScale, 64*Settings.xScale,64*Settings.yScale);
        sb.draw(this.viUpImg, x_offset + 1170.0F*Settings.xScale, y_offset + 430.0F*Settings.yScale, 64*Settings.xScale,64*Settings.yScale);

        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, String.valueOf(vo), x_offset+892.0F*Settings.xScale, y_offset+525F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.RED_RELIC_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, String.valueOf(da), x_offset+1062.0F*Settings.xScale, y_offset+525F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, String.valueOf(vi), x_offset+1232.0F*Settings.xScale, y_offset+525F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.LIGHT_YELLOW_COLOR);

        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, vo_rate, x_offset+885.0F*Settings.xScale, y_offset+470.0F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.RED_RELIC_COLOR,1.0F);
        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, da_rate, x_offset+1055.0F*Settings.xScale, y_offset+470.0F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,1.0F);
        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, vi_rate, x_offset+1225.0F*Settings.xScale, y_offset+470.0F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.LIGHT_YELLOW_COLOR,1.0F);
    }

    private void renderDamageRate(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(this.voBg, x_offset + 840.0F*Settings.xScale, y_offset + 524.0F*Settings.yScale, 128*Settings.xScale,128*Settings.yScale);
        sb.draw(this.daBg, x_offset + 1010.0F*Settings.xScale, y_offset + 524.0F*Settings.yScale, 128*Settings.xScale,128*Settings.yScale);
        sb.draw(this.viBg, x_offset + 1180.0F*Settings.xScale, y_offset + 524.0F*Settings.yScale, 128*Settings.xScale,128*Settings.yScale);

        sb.draw(this.voRankImg, x_offset + 860.0F*Settings.xScale, y_offset + 540.0F*Settings.yScale, 88*Settings.xScale,88*Settings.yScale);
        sb.draw(this.daRankImg, x_offset + 1030.0F*Settings.xScale, y_offset + 540.0F*Settings.yScale, 88*Settings.xScale,88*Settings.yScale);
        sb.draw(this.viRankImg, x_offset + 1200.0F*Settings.xScale, y_offset + 540.0F*Settings.yScale, 88*Settings.xScale,88*Settings.yScale);

        sb.draw(this.voIconImg, x_offset + 840.0F*Settings.xScale, y_offset + 490.0F*Settings.yScale, 52*Settings.xScale,52*Settings.yScale);
        sb.draw(this.daIconImg, x_offset + 1010.0F*Settings.xScale, y_offset + 490.0F*Settings.yScale, 52*Settings.xScale,52*Settings.yScale);
        sb.draw(this.viIconImg, x_offset + 1180.0F*Settings.xScale, y_offset + 490.0F*Settings.yScale, 52*Settings.xScale,52*Settings.yScale);

        //sb.draw(this.voUpImg, x_offset + 800.0F*Settings.xScale+30.F, y_offset + 430.0F*Settings.scale, 64, 64, 64, 64, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
        //sb.draw(this.daUpImg, x_offset + 970.0F*Settings.xScale+30.F, y_offset + 430.0F*Settings.scale, 64, 64, 64, 64, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128,  false, false);
        //sb.draw(this.viUpImg, x_offset + 1140.0F*Settings.xScale+30.F, y_offset + 430.0F*Settings.scale, 64, 64, 64, 64, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);

        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, String.valueOf(vo), x_offset+892.0F*Settings.xScale, y_offset+525F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.RED_RELIC_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, String.valueOf(da), x_offset+1062.0F*Settings.xScale, y_offset+525F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, String.valueOf(vi), x_offset+1232.0F*Settings.xScale, y_offset+525F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.LIGHT_YELLOW_COLOR);

        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, vo_damageRate, x_offset+885.0F*Settings.xScale, y_offset+470.0F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.RED_RELIC_COLOR,1.0F);
        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, da_damageRate, x_offset+1055.0F*Settings.xScale, y_offset+470.0F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR,1.0F);
        FontHelper.renderSmartText(sb, FontHelper.blockInfoFont, vi_damageRate, x_offset+1225.0F*Settings.xScale, y_offset+470.0F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.LIGHT_YELLOW_COLOR,1.0F);
    }


    private void renderDisplay(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        if(this.stage==0){
            sb.draw(this.idolDisplayImg1, x_offset + 804.0F*Settings.xScale, y_offset + 120.0F*Settings.yScale,216*Settings.xScale,288*Settings.yScale);
            sb.draw(this.idolDisplayImg2, x_offset + 1024.0F*Settings.xScale, y_offset + 120.0F*Settings.yScale, 216*Settings.xScale,288*Settings.yScale);
            sb.draw(this.idolEmojiImg1, x_offset + 1252.0F*Settings.xScale, y_offset + 200.0F*Settings.yScale, 128*Settings.xScale,128*Settings.yScale);
            sb.draw(this.idolEmojiImg2, x_offset + 1252.0F*Settings.xScale, y_offset + 50.0F*Settings.yScale, 128*Settings.xScale,128*Settings.yScale);
        }
        else{
            sb.draw(this.idolDisplayImg3, x_offset + 804.0F*Settings.xScale, y_offset + 120.0F*Settings.yScale, 216*Settings.xScale,288*Settings.yScale);
            sb.draw(this.idolDisplayImg4, x_offset + 1024.0F*Settings.xScale, y_offset + 120.0F*Settings.yScale, 216*Settings.xScale,288*Settings.yScale);
            sb.draw(this.idolDisplayImg5, x_offset + 370.0F*Settings.xScale, y_offset + 670.0F*Settings.yScale, 360*Settings.xScale,270*Settings.yScale);
            sb.draw(this.idolEmojiImg2, x_offset + 1252.0F*Settings.xScale, y_offset + 200.0F*Settings.yScale, 128*Settings.xScale,128*Settings.yScale);
            sb.draw(this.idolEmojiImg3, x_offset + 1252.0F*Settings.xScale, y_offset + 50.0F*Settings.yScale, 128*Settings.xScale,128*Settings.yScale);
        }
    }

    private void renderBar(SpriteBatch sb){
        sb.setColor(Color.WHITE);
        sb.draw(this.idolBarImg, x_offset + 820.0F*Settings.xScale, y_offset + 650.0F*Settings.yScale, 450*Settings.xScale,250*Settings.yScale);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[12], this.barHb.cX-24.0F, this.barHb.cY-24.0F, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, this.vo_require, x_offset + 890.0F*Settings.xScale, y_offset + 740.0F*Settings.yScale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, this.da_require, x_offset + 1010.0F*Settings.xScale, y_offset + 740.0F*Settings.yScale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, this.vi_require, x_offset + 1140.0F*Settings.xScale, y_offset + 740.0F*Settings.yScale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
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
            sb.draw(this.clearImg, x_offset + 580.0F*Settings.xScale, y_offset+500.0F*Settings.yScale+35.0F, 128*Settings.xScale,128*Settings.yScale);
        if(this.planStep>1)
            sb.draw(this.clearImg, x_offset + 580.0F*Settings.xScale, y_offset+300.0F*Settings.yScale+35.0F, 128*Settings.xScale,128*Settings.yScale);
        if(this.planStep>2)
            sb.draw(this.clearImg, x_offset + 580.0F*Settings.xScale, y_offset+100.0F*Settings.yScale+35.0F, 128*Settings.xScale,128*Settings.yScale);


        sb.draw(this.idolPlanImg, x_offset + 200.0F*Settings.xScale, y_offset + 70.0F*Settings.scale, 540*Settings.xScale,600*Settings.yScale);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[14], this.planHb.cX-24.0F, this.planHb.cY-24.0F,10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);

        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, this.planRequire1, x_offset+230.0F*Settings.xScale, y_offset+625.0F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.RED_RELIC_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, this.planRequire2, x_offset+230.0F*Settings.xScale, y_offset+425.0F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_RELIC_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, this.planRequire3, x_offset+230.0F*Settings.xScale, y_offset+225.0F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.LIGHT_YELLOW_COLOR);

        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, this.planReward1, x_offset+230.0F*Settings.xScale, y_offset+555.0F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.RED_TEXT_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, this.planReward2, x_offset+230.0F*Settings.xScale, y_offset+355.0F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.BLUE_TEXT_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, this.planReward3, x_offset+230.0F*Settings.xScale, y_offset+155.0F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.LIGHT_YELLOW_COLOR);

        FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, this.idolPlan1, x_offset+390.0F*Settings.xScale, y_offset+545.0F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.GREEN_TEXT_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, this.idolPlan2, x_offset+390.0F*Settings.xScale, y_offset+345.0F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.GREEN_TEXT_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, this.idolPlan3, x_offset+390.0F*Settings.xScale, y_offset+145.0F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.GREEN_TEXT_COLOR,0.8F);

        FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, String.format(this.idolRequire,type2String[this.planTypes[0]],this.planRequires[0]), x_offset+410.0F*Settings.xScale, y_offset+615.0F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.GREEN_TEXT_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, String.format(this.idolRequire,type2String[this.planTypes[1]],this.planRequires[1]), x_offset+410.0F*Settings.xScale, y_offset+415.0F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.GREEN_TEXT_COLOR,0.8F);
        FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, String.format(this.idolRequire,type2String[this.planTypes[2]],this.planRequires[2]), x_offset+410.0F*Settings.xScale, y_offset+215.0F*Settings.yScale, 10000.0F, 20.0F * Settings.scale, Settings.GREEN_TEXT_COLOR,0.8F);

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

    public void generateBoss(){
        ArrayList<AbstractMonster> bossList = new ArrayList<>();
        bossList.add(new IdolBoss_amao());
        bossList.add(new IdolBoss_fktn());
        bossList.add(new IdolBoss_hrnm());
        bossList.add(new IdolBoss_hski());
        bossList.add(new IdolBoss_hume());
        bossList.add(new IdolBoss_shro());
        bossList.add(new IdolBoss_kcna());
        bossList.add(new IdolBoss_kllj());
        bossList.add(new IdolBoss_ssmk());
        bossList.add(new IdolBoss_ttmr());
        bossList.add(new IdolBoss_jsna());

        String curIdolName = "";
        if(AbstractDungeon.player instanceof IdolCharacter){
            curIdolName = SkinSelectScreen.Inst.idolName;
            curIdolName = String.format("IdolBoss_%s",curIdolName);
        }
        if(!curIdolName.equals("")){
            for(int i=0;i<bossList.size();i++){
                if(bossList.get(i).id.equals(curIdolName)){
                    bossList.remove(i);
                    break;
                }
            }
        }

        //使用Setting.seed作为随机数，打乱bosslist的顺序
        java.util.Random rng = new java.util.Random(Settings.seed);
        Collections.shuffle(bossList, rng);

        int battle0_1 = 4;

        // 8进4的3位胜者
        int battle1_1 = rng.nextInt(2);
        int battle1_2 = rng.nextInt(2)+2;
        int battle1_3 = rng.nextInt(2)+5;

        // 4进2的1位胜者
        int battle2_1 = rng.nextInt(2);
        if(battle2_1 == 0){
            battle2_1 = battle1_1;
        }else{
            battle2_1 = battle1_2;
        }

        List<String> res = new ArrayList<>();
        res.add(bossList.get(battle0_1).id.replace("IdolBoss_",""));
        res.add(bossList.get(battle1_3).id.replace("IdolBoss_",""));
        res.add(bossList.get(battle2_1).id.replace("IdolBoss_",""));

        this.friendImg1 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/emoji/%s_01.png", res.get(0)));
        this.friendImg2 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/emoji/%s_01.png", res.get(1)));
        this.friendImg3 = ImageMaster.loadImage(String.format("gkmasModResource/img/report/emoji/%s_01.png", res.get(2)));
    }

}
