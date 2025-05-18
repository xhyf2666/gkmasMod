package gkmasmod.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class ImageHelper {
    public static Texture VoTagImg = ImageMaster.loadImage("gkmasModResource/img/UI/ThreeSize/VoTag.png");
    public static Texture DaTagImg = ImageMaster.loadImage("gkmasModResource/img/UI/ThreeSize/DaTag.png");
    public static Texture ViTagImg = ImageMaster.loadImage("gkmasModResource/img/UI/ThreeSize/ViTag.png");
    public static Texture misuzuNameImg = ImageMaster.loadImage("gkmasModResource/img/UI/misuzuName.png");
    public static Texture finalCircleBg = ImageMaster.loadImage("gkmasModResource/img/UI/ThreeSize/finalCircle/bg.png");
    public static Texture BlackBg = ImageMaster.loadImage("gkmasModResource/img/UI/black.png");
    public static Texture[] arcs= new Texture[3];
    public static Texture CARD_FRAME_ATTACK_COLOR = ImageMaster.loadImage("gkmasModResource/img/cards/banner/512_frame_attack_color.png");
    public static Texture CARD_FRAME_SKILL_COLOR = ImageMaster.loadImage("gkmasModResource/img/cards/banner/512_frame_skill_color.png");
    public static Texture CARD_FRAME_POWER_COLOR = ImageMaster.loadImage("gkmasModResource/img/cards/banner/512_frame_power_color.png");
    public static Texture CARD_FRAME_ATTACK_COLOR_L = ImageMaster.loadImage("gkmasModResource/img/cards/banner/1024_frame_attack_color.png");
    public static Texture CARD_FRAME_SKILL_COLOR_L = ImageMaster.loadImage("gkmasModResource/img/cards/banner/1024_frame_skill_color.png");
    public static Texture CARD_FRAME_POWER_COLOR_L = ImageMaster.loadImage("gkmasModResource/img/cards/banner/1024_frame_power_color.png");

    public static TextureAtlas.AtlasRegion ATTACK_COLOR_REGION = new TextureAtlas.AtlasRegion(CARD_FRAME_ATTACK_COLOR, 0, 0, CARD_FRAME_ATTACK_COLOR.getWidth(), CARD_FRAME_ATTACK_COLOR.getHeight());
    public static TextureAtlas.AtlasRegion SKILL_COLOR_REGION = new TextureAtlas.AtlasRegion(CARD_FRAME_SKILL_COLOR, 0, 0, CARD_FRAME_SKILL_COLOR.getWidth(), CARD_FRAME_SKILL_COLOR.getHeight());
    public static TextureAtlas.AtlasRegion POWER_COLOR_REGION = new TextureAtlas.AtlasRegion(CARD_FRAME_POWER_COLOR, 0, 0, CARD_FRAME_POWER_COLOR.getWidth(), CARD_FRAME_POWER_COLOR.getHeight());
    public static TextureAtlas.AtlasRegion ATTACK_COLOR_REGION_L = new TextureAtlas.AtlasRegion(CARD_FRAME_ATTACK_COLOR_L, 0, 0, CARD_FRAME_ATTACK_COLOR_L.getWidth(), CARD_FRAME_ATTACK_COLOR_L.getHeight());
    public static TextureAtlas.AtlasRegion SKILL_COLOR_REGION_L = new TextureAtlas.AtlasRegion(CARD_FRAME_SKILL_COLOR_L, 0, 0, CARD_FRAME_SKILL_COLOR_L.getWidth(), CARD_FRAME_SKILL_COLOR_L.getHeight());
    public static TextureAtlas.AtlasRegion POWER_COLOR_REGION_L = new TextureAtlas.AtlasRegion(CARD_FRAME_POWER_COLOR_L, 0, 0, CARD_FRAME_POWER_COLOR_L.getWidth(), CARD_FRAME_POWER_COLOR_L.getHeight());

    static {
        arcs[0] = ImageMaster.loadImage("gkmasModResource/img/UI/ThreeSize/finalCircle/arc_vo.png");
        arcs[1] = ImageMaster.loadImage("gkmasModResource/img/UI/ThreeSize/finalCircle/arc_da.png");
        arcs[2] = ImageMaster.loadImage("gkmasModResource/img/UI/ThreeSize/finalCircle/arc_vi.png");
    }

    public static String idolImgPath(String idolName, String className) {
        String path = String.format("gkmasModResource/img/idol/%s/cards/%s.png", idolName, className);
        if(Gdx.files.internal(path).exists()) {
            return path;
        } else {
            return String.format("gkmasModResource/img/idol/%s/cards/%s.png", IdolData.shro, className);
        }
    }

    public static String getCardImgPath(String className, AbstractCard.CardType type) {
        String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", className);
        if(Gdx.files.internal(IMG_PATH).exists()) {
            return IMG_PATH;
        } else {
            if(type == AbstractCard.CardType.ATTACK) {
                return "gkmasModResource/img/cards/common/DefaultAttack.png";
            } else if(type == AbstractCard.CardType.SKILL) {
                return "gkmasModResource/img/cards/common/DefaultSkill.png";
            } else {
                return "gkmasModResource/img/cards/common/DefaultPower.png";
            }
        }
    }
}
