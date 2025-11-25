package gkmasmod.music;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

abstract public class AbstractMusicCard implements Comparable<AbstractMusicCard> {
    private boolean hovered;
    public Hitbox hb = new Hitbox(256*Settings.scale, 256*Settings.scale);
    public int musicID;
    public float current_x;
    public float current_y;
    public float target_x;
    public float target_y;
    public float targetDrawScale;
    protected Texture texture;
    private String imgUrl;
    private Color renderColor;
    private static final float SHADOW_OFFSET_X = 18.0F * Settings.scale;
    private static final float SHADOW_OFFSET_Y = 14.0F * Settings.scale;
    public float drawScale;
    public float angle;
    public static HashMap<String, Texture> imgMap = new HashMap();

    public static final String IMG_PATH = "gkmasModResource/img/music/%s_%03d.png";

    public static final String MUSIC_PATH = "gkmasModResource/audio/song/%s_%03d.ogg";

    public static final String BACK = "gkmasModResource/img/music/back.png";

    public static Texture BACK_TEXTURE = getTextureFromString(BACK);

    public String singer;
    public MusicType musicType;
    public ArrayList<String> multiSinger;
    public boolean solo;
    public boolean unit;

    public AbstractMusicCard(String singer,MusicType musicType){
        this.singer = singer;
        this.musicType = musicType;
        musicID = musicType.getValue();
        this.solo = true;
        this.unit = false;
        this.imgUrl = String.format(IMG_PATH, singer, musicID);
        this.texture = getTextureFromString(this.imgUrl);
        this.renderColor = Color.WHITE.cpy();
    }

    public AbstractMusicCard(String unitName, MusicType musicType, String[] multiSinger, int musicID){
        this.singer = unitName;
        this.multiSinger = new ArrayList<>(Arrays.asList(multiSinger));
        this.musicType = musicType;
        this.musicID = musicID;
        this.solo = false;
        this.unit = true;
        this.imgUrl = String.format(IMG_PATH, "unit", musicID);
        this.texture = getTextureFromString(this.imgUrl);
        this.renderColor = Color.WHITE.cpy();
    }

    public void update(){

    }

    public void updateHoverLogic(){

    }

    @Override
    public int compareTo(AbstractMusicCard other) {
        return this.musicID - other.musicID;
    }

    public void render(SpriteBatch sb) {
        this.render(sb, false);
    }

    public void render(SpriteBatch sb, boolean selected) {
        this.renderCard(sb, this.hovered, selected);
        this.hb.render(sb);
    }

    private boolean isOnScreen() {
        return !(this.current_y < -200.0F * Settings.scale) && !(this.current_y > (float)Settings.HEIGHT + 200.0F * Settings.scale);
    }

    private void renderCard(SpriteBatch sb, boolean hovered, boolean selected) {
        if (!Settings.hideCards) {
            if (!this.isOnScreen()) {
                return;
            }
            this.renderImage(sb, hovered, selected);
        }
    }

    private void renderImage(SpriteBatch sb, boolean hovered, boolean selected) {
        if (AbstractDungeon.player != null) {
            if (selected) {
                sb.draw(BACK_TEXTURE, this.current_x + SHADOW_OFFSET_X * this.drawScale, this.current_y - SHADOW_OFFSET_Y * this.drawScale, 128.0F, 128.0F, 256.0F, 256.0F, this.drawScale * Settings.scale * 1.03F, this.drawScale * Settings.scale * 1.03F, this.angle, 0, 0, 256, 256, false, false);
            }
        }
        this.renderPortrait(sb);
    }

    private void renderPortrait(SpriteBatch sb) {
        float drawX = this.current_x;
        float drawY = this.current_y;
        Texture img = null;
        if (this.texture != null) {
            img = this.texture;
        }
        if (img != null) {
            sb.setColor(this.renderColor);
            sb.draw(img, drawX, drawY + 72.0F, 128.0F, 128.0F, 256.0F, 256.0F, this.drawScale * Settings.scale, this.drawScale * Settings.scale, this.angle, 0, 0, 256, 256, false, false);
        }
    }

    private static void loadTextureFromString(String textureString) {
        if (!imgMap.containsKey(textureString)) {
            imgMap.put(textureString, ImageMaster.loadImage(textureString));
        }

    }

    private static Texture getTextureFromString(String textureString) {
        loadTextureFromString(textureString);
        return imgMap.get(textureString);
    }


    public static enum MusicType {
        HAJIME(1),       // 初
        SOLO1(2),        // solo1
        SOLO2(3),        // solo2
        SWIM(4),         // 泳装
        SUMMER(5),       // 冠菊
        HALLOWEEN(6),    // 万圣
        CHRISTMAS(7),    // 圣诞
        ANIMATE(8),      // ANIMATE
        BIRTHDAY(9),     // 生日曲
        CAMPUS(10),      // campus mode
        CHOCOLATE(11),   // 情人节
        SNOW(12),        // 雪解
        SAKURA(13),      // 樱花
        HOWLING(14),     // HOWLING
        MIRACLE(15),     // 奇迹
        FULL(16),        // 全力以赴
        SOLO4(22),       // solo4
        SOLO3(23),       // solo3
        OTHER(2),        // 其他
        UNIT(99);

        private final int value;

        // 构造函数
        MusicType(int value) {
            this.value = value;
        }

        // 获取值
        public int getValue() {
            return value;
        }

        public static MusicType getTypeByValue(int value) {
            for (MusicType type : MusicType.values()) {
                if (type.getValue() == value) {
                    return type;
                }
            }
            return null;
        }
    }

}
