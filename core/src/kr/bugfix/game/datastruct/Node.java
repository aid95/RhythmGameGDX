package kr.bugfix.game.datastruct;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Node {
    public static final int DIRECTION_TYPE_RIGHT = 0;
    public static final int DIRECTION_TYPE_LEFT = 1;

    public static final Sprite leftNodeSprite = new Sprite(new Texture("music_node_left.png"));
    public static final Sprite rightNodeSprite = new Sprite(new Texture("music_node_right.png"));
    public static final Sprite attackNodeSprite = new Sprite(new Texture("attack_node.png"));

    public Rectangle rect;

    public int type;
}
