package gkmasmod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class SimplePlayVideoEffect extends AbstractGameEffect {
    private Hitbox hb;

    private VideoPlayer videoPlayer;

    private static final String COMMENT = "The video format needs to be webm !!!";

    public SimplePlayVideoEffect(String videoPath,boolean absolutePath) {

        this.videoPlayer =  VideoPlayerCreator.createVideoPlayer();
        this.hb = new Hitbox(Settings.WIDTH, Settings.HEIGHT);
        this.hb.x = 0.0F;
        this.hb.y = 0.0F;
        if (this.videoPlayer == null) {
            clearVideo();
            return;
        }
        this.videoPlayer.setOnCompletionListener(e -> clearVideo());
        (new Thread(() -> {
            try {
                if(absolutePath){
                    this.videoPlayer.play(Gdx.files.absolute(videoPath));
                }
                else{
                    this.videoPlayer.play(Gdx.files.internal(videoPath));
                }
            } catch (Exception e) {
                e.printStackTrace();
                clearVideo();
            }
        })).start();
    }

    public void update() {
        this.videoPlayer.update();
        this.hb.update();
        if (this.hb.hovered && InputHelper.justClickedLeft) {
            InputHelper.justClickedLeft = false;
            this.hb.clickStarted = true;
        }
        if (this.hb.clicked) {
            this.hb.clicked = false;
            this.isDone = true;
            AbstractDungeon.screen = AbstractDungeon.CurrentScreen.VICTORY;
            AbstractDungeon.victoryScreen = new VictoryScreen(null);
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
        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.VICTORY;
        AbstractDungeon.victoryScreen = new VictoryScreen(null);
        if (this.videoPlayer != null) {
            this.videoPlayer.dispose();
            this.videoPlayer = null;
        }
    }
}
