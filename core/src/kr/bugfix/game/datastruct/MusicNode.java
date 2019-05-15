package kr.bugfix.game.datastruct;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class MusicNode
{
    public Sprite sprite;
    public Vector2 position;

    public MusicNode(Vector2 position)
    {
        sprite = new Sprite(new Texture("left_cursor.png"));
        this.position = position;
    }

    public void setPosition(int x, int y)
    {
        position.set(x, y);
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public void finalize()
    {
    }
}
