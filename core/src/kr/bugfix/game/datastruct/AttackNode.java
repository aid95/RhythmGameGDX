package kr.bugfix.game.datastruct;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import kr.bugfix.game.system.GameUtils;

public class AttackNode
    extends Node
{
    // 0: up, 1: down
    private int upDownFlag;

    public AttackNode(Vector2 position, int type) {
        // Set bound box size
        this.rect = new Rectangle();
        this.rect.x = position.x;
        this.rect.y = position.y;
        this.rect.width = leftNodeSprite.getWidth();
        this.rect.height = leftNodeSprite.getHeight();

        // Set direction
        this.type = type;

        upDownFlag = GameUtils.getInstance().getRandomInt(2) - 1;
    }

    public Sprite getNodeSprite() {
        return attackNodeSprite;
    }

    public void setPosition(int x, int y) {
        rect.x = x;
        rect.y = y;
    }

    public Vector2 getCenterPosition() {
        return new Vector2(rect.x + rect.width/2, rect.y + rect.height/2);
    }

    public void move(float delta, int speed) {
        if (Node.DIRECTION_TYPE_LEFT == type)
        {
            rect.x -= speed * delta;
            if (upDownFlag == 0)
            {
                rect.y += speed * delta;
                if (rect.y > Gdx.graphics.getHeight())
                {
                    upDownFlag = 1;
                }
            }
            else {
                rect.y -= speed * delta;
                if (rect.y < 0)
                {
                    upDownFlag = 0;
                }
            }
        }
        else {
            rect.x += speed * delta;
            if (upDownFlag == 0)
            {
                rect.y += speed * delta;
                if (rect.y > Gdx.graphics.getHeight())
                {
                    upDownFlag = 1;
                }
            }
            else {
                rect.y -= speed * delta;
                if (rect.y < 0)
                {
                    upDownFlag = 0;
                }
            }
        }
    }

}
