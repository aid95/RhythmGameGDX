package kr.bugfix.game.datastruct;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

public class MusicNode
{
    public static final int TYPE_NOMAR_RIGHT = 0;
    public static final int TYPE_NOMAR_LEFT = 1;

    public static Sprite leftNodeSprite = new Sprite(new Texture("left_cursor.png"));
    public static Sprite rightNodeSprite = new Sprite(new Texture("right_cursor.png"));

    public Rectangle rect;

    public int type;

    public MusicNode(Vector2 position, int type) {
        // Set bound box size
        this.rect = new Rectangle();
        this.rect.x = position.x;
        this.rect.y = position.y;
        this.rect.width = leftNodeSprite.getWidth();
        this.rect.height = leftNodeSprite.getHeight();

        // Set direction
        this.type = type;
    }

    public Sprite getNodeSprite() {
        if (type == TYPE_NOMAR_LEFT)
        {
            return leftNodeSprite;
        }
        return rightNodeSprite;
    }

    public void setPosition(int x, int y) {
        position.set(x, y);
    }
}
