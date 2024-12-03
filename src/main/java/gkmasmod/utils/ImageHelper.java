package gkmasmod.utils;

import com.badlogic.gdx.graphics.Texture;
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
}
