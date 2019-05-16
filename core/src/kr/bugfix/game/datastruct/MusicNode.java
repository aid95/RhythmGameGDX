package kr.bugfix.game.datastruct;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class MusicNode
{
    public static final int TYPE_NOMAR_RIGHT = 0;
    public static final int TYPE_NOMAR_LEFT = 1;

    public Sprite sprite;
    public Vector2 position;
    public int type;

    public MusicNode(Vector2 position, int type) {
        sprite = new Sprite(new Texture("left_cursor.png"));
        this.position = position;
        this.type = type;
    }

    public void setPosition(int x, int y) {
        position.set(x, y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void finalize()
    {
    }
}
