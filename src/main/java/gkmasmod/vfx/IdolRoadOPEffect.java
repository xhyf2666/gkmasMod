package gkmasmod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.utils.ImageHelper;

public class IdolRoadOPEffect extends AbstractGameEffect {
    private Hitbox hb;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("gkmasMod:CreditsScreen");
    private VideoPlayer videoPlayer;

    private boolean isSkippable;
    private float skipTimer;
    private boolean closingSkipMenu;
    private static final float SKIP_START_X = -300.0F * Settings.scale;

    private static final float SKIP_END_X = 50.0F * Settings.scale;

    private float skipX;

    private static final String COMMENT = "The video format needs to be webm !!!";

    public IdolRoadOPEffect(String videoPath, boolean localPath) {
        GkmasMod.playVideo = true;
        this.videoPlayer =  VideoPlayerCreator.createVideoPlayer();
        this.hb = new Hitbox(Settings.WIDTH, Settings.HEIGHT);
        this.hb.x = 0.0F;
        this.hb.y = 0.0F;
        this.skipTimer = 0.0F;
        this.isSkippable = false;
        this.closingSkipMenu = false;
        this.skipX = SKIP_START_X;
        if (this.videoPlayer == null) {
            clearVideo();
            return;
        }
        this.videoPlayer.setOnCompletionListener(e -> clearVideo());
        (new Thread(() -> {
            try {
                if(localPath){
                    this.videoPlayer.play(Gdx.files.local(videoPath));
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
        skipLogic();
        this.hb.update();

    }

    private void skipLogic() {
        if (InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed())
            if (this.isSkippable) {
                this.isDone = true;
                clearVideo();
            } else if (!this.isSkippable && this.skipTimer == 0.0F) {
                this.skipTimer = 0.5F;
                this.skipX = SKIP_END_X;
            }
        if (this.skipTimer != 0.0F) {
            this.skipTimer -= Gdx.graphics.getDeltaTime();
            if (!this.isSkippable && !this.closingSkipMenu) {
                this.skipX = Interpolation.swingIn.apply(SKIP_END_X, SKIP_START_X, this.skipTimer * 2.0F);
            } else if (this.closingSkipMenu) {
                this.skipX = Interpolation.fade.apply(SKIP_START_X, SKIP_END_X, this.skipTimer * 2.0F);
            } else {
                this.skipX = SKIP_END_X;
            }
            if (this.skipTimer < 0.0F)
                if (!this.isSkippable && !this.closingSkipMenu) {
                    this.isSkippable = true;
                    this.skipTimer = 2.0F;
                } else if (!this.closingSkipMenu) {
                    this.closingSkipMenu = true;
                    this.isSkippable = false;
                    this.skipTimer = 0.5F;
                } else {
                    this.isSkippable = false;
                    this.closingSkipMenu = false;
                    this.skipTimer = 0.0F;
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
//            sb.draw(ImageHelper.BlackBg,0,0,width,Settings.HEIGHT*Settings.scale);
            sb.draw(texture, x, y, width, height);

        }
        if (Settings.isTouchScreen) {
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipBodyFont, uiStrings.TEXT[2], this.skipX, 50.0F * Settings.scale, Settings.BLUE_TEXT_COLOR);
        } else if (!Settings.isControllerMode) {
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipBodyFont, uiStrings.TEXT[0], this.skipX, 50.0F * Settings.scale, Settings.BLUE_TEXT_COLOR);
        } else {
            sb.setColor(Color.GOLD);
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipBodyFont, uiStrings.TEXT[1], this.skipX + 46.0F * Settings.scale, 50.0F * Settings.scale, Settings.BLUE_TEXT_COLOR);
            sb.setColor(Color.WHITE);
            sb.draw(CInputActionSet.select
                    .getKeyImg(), this.skipX - 32.0F + 10.0F * Settings.scale, -32.0F + 44.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale * 0.9F, Settings.scale * 0.9F, 0.0F, 0, 0, 64, 64, false, false);
        }
    }

    public void dispose() {}

    public void clearVideo() {
        this.isDone = true;
        if (this.videoPlayer != null) {
            this.videoPlayer.dispose();
            this.videoPlayer = null;
        }
        GkmasMod.playVideo = false;
    }
}
