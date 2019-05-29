package kr.bugfix.game.datastruct;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class AttackNode
    extends Node
{
    public AttackNode(Vector2 position, int type) {
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
        if (type == DIRECTION_TYPE_LEFT) {
            return leftNodeSprite;
        }
        return rightNodeSprite;
    }

    public void setPosition(int x, int y) {

        rect.x = x;
        rect.y = y;

    }

    public Vector2 getCenterPosition() {
        return new Vector2(rect.x + rect.width/2, rect.y + rect.height/2);
    }

}
