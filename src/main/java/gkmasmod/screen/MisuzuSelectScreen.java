package gkmasmod.screen;


import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.ISubscriber;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import gkmasmod.characters.OtherIdolCharacter;
import gkmasmod.utils.CommonEnum;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;

import static gkmasmod.characters.PlayerColorEnum.gkmasModMisuzu_character;
import static gkmasmod.characters.PlayerColorEnum.gkmasModOther_character;

public class MisuzuSelectScreen implements ISubscriber {
    public static MisuzuSelectScreen Inst;
    public Hitbox leftHb;

    public MisuzuSelectScreen() {
        this.leftHb = new Hitbox(400.0F * Settings.scale, 300.0F * Settings.scale);
        BaseMod.subscribe(this);
    }

    public void update() {
        float centerX = (float)Settings.WIDTH * 0.15F;
        float centerY = (float)Settings.HEIGHT * 0.4F;
        this.leftHb.move(centerX - 180.0F * Settings.scale, centerY + 180.0F * Settings.scale);
    }

    private void updateInput() {
        if (CardCrawlGame.chosenCharacter == gkmasModMisuzu_character) {
            this.leftHb.update();
            if (this.leftHb.clicked) {
                this.leftHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
            }
        }
    }

    public void render(SpriteBatch sb) {
        Color RC = new Color(0.0F, 204.0F, 255.0F, 1.0F);
        float centerX = (float)Settings.WIDTH * 0.15F;
        float centerY = (float)Settings.HEIGHT * 0.4F;
        sb.setColor(Color.WHITE);

        Color color = Settings.GOLD_COLOR.cpy();
        color.a /= 2.0F;
        float dist = 100.0F * Settings.scale;

        if (this.leftHb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }
        sb.draw(ImageMaster.CF_LEFT_ARROW, this.leftHb.cX - 24.0F, this.leftHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);


        this.leftHb.render(sb);
    }


    static {
        Inst = new MisuzuSelectScreen();
    }

}
