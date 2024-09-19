package gkmasmod.vfx.effect;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class DelayedEffect extends AbstractGameEffect {
    private AbstractGameEffect effect;
    private float delayTime;
    private boolean added;

    public DelayedEffect(AbstractGameEffect effect, float delayTime) {
        this.effect = effect;
        this.delayTime = delayTime;
        this.added = false;
    }

    @Override
    public void update() {
        // 每帧减少剩余时间
        delayTime -= Gdx.graphics.getDeltaTime();
        if (delayTime <= 0.0f && !added) {
            // 延迟时间到了，添加效果
            AbstractDungeon.effectList.add(effect);
            added = true;  // 确保只添加一次
            this.isDone = true;  // 标记该延迟效果完成
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        // 这里无需渲染，因为这是一个延迟管理类，不实际显示
    }

    @Override
    public void dispose() {
        // 无需特殊清理
    }
}
