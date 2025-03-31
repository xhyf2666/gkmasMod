package gkmasmod.downfall.charbosses.vfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.stance.DivinityStanceChangeParticle;

public class EnemyStanceChangeParticleGenerator extends AbstractGameEffect {
    private final float x;
    private final float y;
    private final String stanceId;

    public EnemyStanceChangeParticleGenerator(float x, float y, String stanceId) {
        this.x = x;
        this.y = y;
        this.stanceId = stanceId;
    }

    public void update() {
        if (!this.stanceId.equals("Calm")) {
            int i;
            if (this.stanceId.equals("Divinity")) {
                for (i = 0; i < 20; ++i) {
                    AbstractDungeon.effectsQueue.add(new DivinityStanceChangeParticle(Color.PINK, this.x, this.y));
                }
            } else if (this.stanceId.equals("Wrath")) {
                for (i = 0; i < 10; ++i) {
                    AbstractDungeon.effectsQueue.add(new EnemyWrathStanceChangeParticle(this.x));
                }
            } else if (this.stanceId.equals("Neutral")) {
                for (i = 0; i < 20; ++i) {
                    AbstractDungeon.effectsQueue.add(new DivinityStanceChangeParticle(Color.WHITE, this.x, this.y));
                }
            } else {
                for (i = 0; i < 20; ++i) {
                    AbstractDungeon.effectsQueue.add(new DivinityStanceChangeParticle(Color.WHITE, this.x, this.y));
                }
            }
        }

        this.isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
