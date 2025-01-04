package gkmasmod.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class ImageHelper {
    public static Texture VoTagImg = ImageMaster.loadImage("gkmasModResource/img/UI/ThreeSize/VoTag.png");
    public static Texture DaTagImg = ImageMaster.loadImage("gkmasModResource/img/UI/ThreeSize/DaTag.png");
    public static Texture ViTagImg = ImageMaster.loadImage("gkmasModResource/img/UI/ThreeSize/ViTag.png");
    public static Texture finalCircleBg = ImageMaster.loadImage("gkmasModResource/img/UI/ThreeSize/finalCircle/bg.png");
    public static Texture BlackBg = ImageMaster.loadImage("gkmasModResource/img/UI/black.png");
    public static Texture[] arcs= new Texture[3];

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
