package gkmasmod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class SimplePlayVideoEffect2 extends AbstractGameEffect {
    private Hitbox video_hb;

    private VideoPlayer videoPlayer;

    private static final String COMMENT = "The video format needs to be webm !!!";

    public SimplePlayVideoEffect2(String videoPath) {

        this.videoPlayer =  VideoPlayerCreator.createVideoPlayer();
        this.video_hb = new Hitbox(Settings.WIDTH, Settings.HEIGHT);
        this.video_hb.x = 0.0F;
        this.video_hb.y = 0.0F;
        if (this.videoPlayer == null) {
            clearVideo();
            return;
        }

    }

    public void update() {
        this.videoPlayer.update();
        this.video_hb.update();
        if (this.video_hb.hovered && InputHelper.justClickedLeft) {
            InputHelper.justClickedLeft = false;
            this.video_hb.clickStarted = true;
        }
        if (this.video_hb.clicked) {
            this.video_hb.clicked = false;
            this.isDone = true;
            if (this.videoPlayer != null) {
                this.videoPlayer.dispose();
                this.videoPlayer = null;
            }
        }
    }

    public void render(SpriteBatch sb) {
        Texture texture = this.videoPlayer.getTexture();
        if (texture != null) {
            float width = texture.getWidth() * Settings.scale;
            float height = texture.getHeight() * Settings.scale;
            float x = (Settings.WIDTH - width) / 2.0F;
            float y = (Settings.HEIGHT - height) / 2.0F;
            sb.setColor(Color.WHITE);
            sb.draw(texture, x, y, width, height);
        }
    }

    public void dispose() {}

    public void clearVideo() {
        this.isDone = true;
        if (this.videoPlayer != null) {
            this.videoPlayer.dispose();
            this.videoPlayer = null;
        }
    }
}
