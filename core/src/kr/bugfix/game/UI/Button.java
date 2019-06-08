package kr.bugfix.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Button
{

    private Sprite pressDown;
    private Sprite pressUp;

    private Rectangle buttonRange;

    private boolean isPress;

    private int buttonId;

    public Button(int buttonId, String pressDownImagePath, String pressUpImagePath) {
        this.pressDown = new Sprite(new Texture(Gdx.files.internal(pressDownImagePath)));
        this.pressUp = new Sprite(new Texture(Gdx.files.internal(pressUpImagePath)));

        buttonRange = new Rectangle();
        buttonRange.width = pressUp.getWidth();
        buttonRange.height = pressUp.getHeight();

        this.buttonId = buttonId;

        isPress = false;
    }

    /**
     * 쉽게 위치를 지정하기 위해 버튼의 위치 중앙을 받아 위치를 설정.
     *
     * @param centerX 버튼의 위치 중앙x
     * @param centerY 버튼의 위치 중앙y
     */
    public void setPosition(int centerX, int centerY)
    {
        centerX -= pressUp.getWidth() / 2;
        centerY -= pressUp.getHeight() / 2;

        buttonRange.x = centerX;
        buttonRange.y = centerY;
    }

    public void render(Batch batch)
    {
        // 눌린상태와 구분하여 sprite를 그림.
        Sprite curSprite = (isPress)? pressDown: pressUp;
        batch.draw(curSprite, buttonRange.x, buttonRange.y);
    }

    public int checkPress(int touchX, int touchY)
    {
        Gdx.app.log("BUTTON POS", buttonRange.x + ", " + buttonRange.y + "// w" + buttonRange.width + ", " + buttonRange.height);
        // 터치 위치와 버튼의 범위를 비교해서 포함된다면 지정된 버튼의 id를 return
        int ret = -1;

        if (buttonRange.x < touchX && touchX < (buttonRange.x + buttonRange.width))
            if (buttonRange.y < touchY && touchY < (buttonRange.y + buttonRange.height))
                ret = buttonId;

        if (ret != -1)
            if (isPress)
                isPress = false;
            else
                isPress = true;

        return ret;
    }

}
