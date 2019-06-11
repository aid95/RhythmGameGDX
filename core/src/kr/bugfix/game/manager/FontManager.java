package kr.bugfix.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontManager
{

    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    private static FontManager instance;
    public static FontManager getInstance() {
        if (instance == null)
            instance = new FontManager();
        return instance;
    }

    private FontManager() {
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    }

    public FontManager init() {
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        return instance;
    }

    public FontManager setColor(float r, float g, float b, float a) {
        parameter.color = new Color(r, g, b, a);
        return instance;
    }

    public FontManager setSize(int size) {
        parameter.size = size;
        return instance;
    }

    public FontManager setShadow(Color color, int offX, int offY) {
        parameter.shadowColor = color;
        parameter.shadowOffsetX = offX;
        parameter.shadowOffsetY = offY;
        return instance;
    }

    public BitmapFont getPixelFont() {
        BitmapFont ret;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("pixel.ttf"));
        ret = generator.generateFont(parameter);
        generator.dispose();
        return ret;
    }

}
