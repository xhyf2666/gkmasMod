package gkmasmod.vfx.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GakuenLinkMasterEffect extends AbstractGameEffect {
    private static final float GRAVITY;
    private static final float START_VY;
    private static final float START_VY_JITTER;
    private static final float START_VX;
    private static final float START_VX_JITTER;
    private static final float TARGET_JITTER;
    private float rotationSpeed;
    private float x;
    private float y;
    private float vX;
    private float vY;
    private float targetX;
    private float targetY;
    private int[] order;
    private static Texture[] IMGs = new Texture[]{
            new Texture(String.format("gkmasModResource/img/monsters/Nadeshiko/FriendNunu%s.png",1)),
            new Texture(String.format("gkmasModResource/img/monsters/Nadeshiko/FriendNunu%s.png",2)),
            new Texture(String.format("gkmasModResource/img/monsters/Nadeshiko/FriendNunu%s.png",3)),
            new Texture(String.format("gkmasModResource/img/monsters/Nadeshiko/FriendNunu%s.png",4)),
            new Texture(String.format("gkmasModResource/img/monsters/Nadeshiko/FriendNunu%s.png",5)),
            new Texture(String.format("gkmasModResource/img/monsters/Nadeshiko/FriendNunu%s.png",6)),
            new Texture(String.format("gkmasModResource/img/monsters/Nadeshiko/FriendNunu%s.png",7)),
            new Texture(String.format("gkmasModResource/img/monsters/Nadeshiko/FriendNunu%s.png",8)),
            new Texture(String.format("gkmasModResource/img/monsters/Nadeshiko/FriendNunu%s.png",9)),
            new Texture(String.format("gkmasModResource/img/monsters/Nadeshiko/FriendNunu%s.png",10)),
            new Texture(String.format("gkmasModResource/img/monsters/Nadeshiko/FriendNunu%s.png",11)),
            new Texture(String.format("gkmasModResource/img/monsters/Nadeshiko/FriendNunu%s.png",12))
    };
    private float alpha;
    private float suctionTimer;
    public GakuenLinkMasterEffect() {
        this.alpha = 1.0F;
        this.suctionTimer = 0.0F;
        this.x = 0;
        this.y = 0;
        this.targetX = Settings.WIDTH;
        this.targetY = 0;
        this.vX = MathUtils.random(START_VX - 50.0F * Settings.scale, START_VX_JITTER);
        this.vY = MathUtils.random(START_VY, START_VY_JITTER);
        this.rotationSpeed = MathUtils.random(1000.0F, 2000.0F);
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            list.add(i);
        }

        // 打乱列表顺序
        Collections.shuffle(list);
        int[] shuffledArray = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            shuffledArray[i] = list.get(i);
        }
        this.order = shuffledArray;
        this.scale = Settings.scale;
        this.color = new Color(1.0F, 1.0F, 1.0F, 1.0F);
    }


    public void update() {
//        if (this.alpha != 1.0F) {
//            this.alpha += Gdx.graphics.getDeltaTime() * 2.0F;
//            if (this.alpha > 1.0F) {
//                this.alpha = 1.0F;
//            }
//
//            this.color.a = this.alpha;
//        }

        this.rotation += Gdx.graphics.getDeltaTime() * this.rotationSpeed;
        this.x += Gdx.graphics.getDeltaTime() * this.vX;
//        this.y += Gdx.graphics.getDeltaTime() * this.vY;
//        this.vY -= Gdx.graphics.getDeltaTime() * GRAVITY;
        if (this.suctionTimer > 0.0F) {
            this.suctionTimer -= Gdx.graphics.getDeltaTime();
        } else {
//            this.vY = MathUtils.lerp(this.vY, 0.0F, Gdx.graphics.getDeltaTime() * 5.0F);
            this.vX = MathUtils.lerp(this.vX, 0.0F, Gdx.graphics.getDeltaTime() * 5.0F);
            this.x = MathUtils.lerp(this.x, this.targetX, Gdx.graphics.getDeltaTime() * 4.0F);
//            this.y = MathUtils.lerp(this.y, this.targetY, Gdx.graphics.getDeltaTime() * 4.0F);
            if (Math.abs(this.x - this.targetX) < 20.0F) {
                this.isDone = true;
            }
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        for (int i = 0; i < this.order.length; i++) {
            sb.draw(IMGs[this.order[i] - 1], this.x, this.y+(Settings.HEIGHT-100)/12*i, 64, 64, 128, 128, Settings.scale, Settings.scale, rotation, 0, 0, 256, 256, false, false);
        }
    }

    public void dispose() {
    }

    static {
        GRAVITY = 2000.0F * Settings.scale;
        START_VY = 800.0F * Settings.scale;
        START_VY_JITTER = 400.0F * Settings.scale;
        START_VX = 100.0F * Settings.scale;
        START_VX_JITTER = 100.0F * Settings.scale;
        TARGET_JITTER = 50.0F * Settings.scale;
    }
}
