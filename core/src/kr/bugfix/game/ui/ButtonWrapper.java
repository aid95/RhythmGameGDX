package kr.bugfix.game.ui;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;

public class ButtonWrapper
{
    private ArrayList<Button> buttons;

    public ButtonWrapper()
    {
        buttons = new ArrayList<Button>();
    }

    public void add(Button button)
    {
        buttons.add(button);
    }

    public void render(Batch batch)
    {
        for (Button button: buttons)
        {
            button.render(batch);
        }
    }

    /**
     * 모든 버튼을 검사해서 해당된 버튼의 ID를 return. 사용자는 해당 ID를 통해 동작수행.
     *
     * @param touchX touch의 된 위치의 X
     * @param touchY touch의 된 위치의 Y
     * @return 버튼의 ID
     */
    public int checkPress(int touchX, int touchY)
    {
        for (Button button: buttons)
        {
            int bid = button.checkPress(touchX, touchY);
            if (bid != -1)
                return bid;
        }
        return -1;
    }

}
