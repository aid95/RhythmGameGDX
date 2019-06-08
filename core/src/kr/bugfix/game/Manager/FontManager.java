package kr.bugfix.game.Manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontManager
{

    private BitmapFont pixelFont;

    private static FontManager instance;
    public static FontManager getInstance()
    {
        if (instance == null)
            instance = new FontManager();
        return instance;
    }

    private FontManager()
    {
        // Pixel font size 18
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("pixel.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 18;
        pixelFont = generator.generateFont(parameter);
        generator.dispose();
    }

    public BitmapFont getPixelFont18()
    {
        return pixelFont;
    }

}
