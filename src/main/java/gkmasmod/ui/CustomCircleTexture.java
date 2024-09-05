package gkmasmod.ui;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.Gdx;

public class CustomCircleTexture {

    public static Texture createCustomCircleTexture(int radius, int thickness) {
        // 创建一个FrameBuffer来存储绘制的内容
        FrameBuffer fbo = new FrameBuffer(Pixmap.Format.RGBA8888, radius * 2, radius * 2, false);
        fbo.begin();

        // 清空当前的屏幕
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ShapeRenderer shapeRenderer = new ShapeRenderer();

        // 开始绘制形状
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // 绘制红色部分（0度到180度）
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.arc(radius, radius, radius - thickness / 2f, 0, 180);

        // 绘制灰色部分（180度到270度）
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.arc(radius, radius, radius - thickness / 2f, 180, 90);

        shapeRenderer.end();

        // 结束FBO的绘制
        fbo.end();

        // 将FBO的内容转换为纹理
        Texture texture = new Texture(fbo.getColorBufferTexture().getTextureData());
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // 清理资源
        shapeRenderer.dispose();
        fbo.dispose();

        return texture;
    }
}


