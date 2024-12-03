package gkmasmod.characters;

import basemod.abstracts.CustomEnergyOrb;

public class IdolEnergyOrb extends CustomEnergyOrb {
    private static final String[] ORB_TEXTURES = new String[] {
            "gkmasModResource/img/UI/energy_bg.png",
            "gkmasModResource/img/UI/layer.png",
            "gkmasModResource/img/UI/star.png",
            "gkmasModResource/img/UI/energy_bg.png",
            "gkmasModResource/img/UI/energy_bg.png",
            "gkmasModResource/img/UI/layer_d.png",
            "gkmasModResource/img/UI/star_d.png" };

    private static final String ORB_VFX = "gkmasModResource/img/UI/vfx.png";

    // 每个图层的旋转速度
    private static final float[] LAYER_SPEED = new float[]{-40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F, -5.0F, 0.0F};

    public IdolEnergyOrb() {
        super(ORB_TEXTURES, ORB_VFX, LAYER_SPEED);
    }
}
