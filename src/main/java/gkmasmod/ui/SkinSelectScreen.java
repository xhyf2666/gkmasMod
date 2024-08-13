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
import gkmasmod.utils.IdolNameString;
import gkmasmod.utils.NameHelper;


import java.util.ArrayList;

public class SkinSelectScreen implements ISubscriber, CustomSavable<Integer> {
    private static String[] TEXT;

    private static String[] Special;
    private static final ArrayList<Skin> SKINS = new ArrayList();
    public static SkinSelectScreen Inst;
    public Hitbox leftHb;
    public Hitbox rightHb;
    public String curName = "";
    public String nextName = "";

    public String SpecialName = "";
    public int index;

    public static Skin getSkin() {

        return (Skin)SKINS.get(Inst.index);

    }

    public SkinSelectScreen() {
        this.refresh();
        this.leftHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.rightHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        BaseMod.subscribe(this);
        BaseMod.addSaveField(NameHelper.makePath("skin"), this);
    }
    public Texture usedImg;

    public void refresh() {
        String name = IdolNameString.idolNames[this.index];

        this.curName = CardCrawlGame.languagePack.getCharacterString(NameHelper.addSplitWords("IdolName",name)).TEXT[0];

        Skin skin = (Skin)SKINS.get(this.index);
        this.usedImg = ImageMaster.loadImage(skin.charPath);
        IdolCharacter.SELES_STAND = skin.charPath;
        this.curName = skin.name;
        this.SpecialName = skin.special;
//        this.loadAnimation(Anon[this.index], null, 0);
        this.nextName = ((Skin)SKINS.get(this.nextIndex())).name;
//        if (AbstractDungeon.player instanceof char_Anon) {
//            char_Anon var2 = (char_Anon)AbstractDungeon.player;
//        }
        if (AbstractDungeon.player instanceof IdolCharacter) {
            IdolCharacter k = (IdolCharacter)AbstractDungeon.player;
            k.refreshSkin();
    }
    }

    public int prevIndex() {
        return this.index - 1 < 0 ? SKINS.size() - 1 : this.index - 1;
    }

    public int nextIndex() {
        return this.index + 1 > SKINS.size() - 1 ? 0 : this.index + 1;
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
                this.index = this.prevIndex();
                this.refresh();
            }

            if (this.rightHb.clicked) {
                this.rightHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.index = this.nextIndex();
                this.refresh();
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
        String selectIdolHint = CardCrawlGame.languagePack.getCharacterString("selectIdol").TEXT[0];
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, selectIdolHint, centerX, centerY + 250.0F * Settings.scale, Color.WHITE, 1.25F);
        Color color = Settings.GOLD_COLOR.cpy();
        color.a /= 2.0F;
        float dist = 100.0F * Settings.scale;
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, this.curName, centerX, centerY-200, RC);
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, this.SpecialName, centerX, centerY-250, RC);
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
     this.index = arg0.intValue();
     refresh();
   }
    public Integer onSave() {
        return this.index;
    }

    static {
        String[] special1;
            special1 = new String[]{"",
                    "","用“机械心”代替你的“基础音乐”","初始携带“吉他-浅草&微露”,更换专属初始卡组","","","","","","","",""};


        Special = special1;
        TEXT = IdolNameString.idolNames;
        SKINS.add(new Skin(0, "Anon"));
        SKINS.add(new Skin(1, "AshAnon"));
        SKINS.add(new Skin(2, "white"));
        SKINS.add(new Skin(3, "caicai"));
        SKINS.add(new Skin(4, "AnonFes"));
        SKINS.add(new Skin(5, "AnonGive"));
        SKINS.add(new Skin(6, "AnonSix"));
        SKINS.add(new Skin(7, "shiro"));
        SKINS.add(new Skin(8, "feimali"));
        SKINS.add(new Skin(9, "PAREO"));
        SKINS.add(new Skin(10, "yukina"));
        SKINS.add(new Skin(11, "soyo"));
        SKINS.add(new Skin(12, "smallworker"));
        SKINS.add(new Skin(13, "tech"));
        SKINS.add(new Skin(14, "leader"));
        SKINS.add(new Skin(15, "KSM"));
        Inst = new SkinSelectScreen();
    }


    public static class Skin {
        public String charPath;
        public String shoulder;
        public String name;
        public String special;
        public Skin(int index, String charPath) {
            if(charPath.equals("Anon")){
                this.charPath = "img/char/anon.png";
            }
            if(charPath.equals("AshAnon")){
                this.charPath = "img/huijinaiyin/huijinaiyin260_2.png";
            }
            if(charPath.equals("caicai")){
                this.charPath = "img/test/caicai.png";
            }
            if(charPath.equals("AnonFes")){
                this.charPath = "img/test/AnonFes.png";
            }
            if(charPath.equals("AnonGive")){
                this.charPath = "img/test/AnonGive.png";
            }
            if(charPath.equals("AnonSix")){
                this.charPath = "img/test/AnonSix.png";
            }
            if(charPath.equals("shiro")){
                this.charPath = "img/test/shiro.png";
            }
            if(charPath.equals("feimali")){
                this.charPath = "img/test/feimali.png";
            }
            if(charPath.equals("PAREO")){
                this.charPath = "img/test/PAREO.png";
            }
            if(charPath.equals("yukina")){
                this.charPath = "img/test/yukina.png";
            }
            if(charPath.equals("soyo")){
                this.charPath = "img/test/soyo.png";
            }
            if(charPath.equals("smallworker")){
                this.charPath = "img/test/smallworker.png";
            }
            if(charPath.equals("tech")){
                this.charPath = "img/test/tech.png";
            }
            if(charPath.equals("leader")){
                this.charPath = "img/test/leader.png";
            }
            if(charPath.equals("white")){
                this.charPath = "img/test/Anon white.png";
            }
            if(charPath.equals("KSM")){
                this.charPath = "img/test/KSM.png";
            }
            this.name = SkinSelectScreen.TEXT[index + 1];
            this.special = SkinSelectScreen.Special[index + 1];
        }
    }

}
