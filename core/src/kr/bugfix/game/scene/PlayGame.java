package kr.bugfix.game.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import kr.bugfix.game.RhythmGame;
import kr.bugfix.game.system.BaseScene;

public class PlayGame extends BaseScene {

    // Sprite 사용하기!!!!
    private static final int FINGER_COUNT = 2;

    /**
     * @var leftCursorPosY  왼쪽 커서의 Y 좌표를 가집합니다.
     * @var rightCursorPosY 오른쪽 커서의 Y 좌표를 가집니다.
     */
    Sprite leftCursor;
    private Vector2 leftCursorPos;
    Sprite rightCursor;
    private Vector2 rightCursorPos;

    public PlayGame(RhythmGame app) {
        super(app);
        stage = new Stage(viewport);
        init();
    }

    /**
     * 초기작업을 위해 호출되며 가장 먼저 호출되게 됩니다.
     */
    @Override
    public void init() {

        // 게임 커서 위치, 스프라이트 초기화
        rightCursor = new Sprite(new Texture("right_cursor.png"));
        rightCursorPos = new Vector2(Gdx.graphics.getWidth() - rightCursor.getWidth() - 20, Gdx.graphics.getHeight()/2 - rightCursor.getHeight()/2);
        leftCursor = new Sprite(new Texture("left_cursor.png"));
        leftCursorPos = new Vector2(20,  Gdx.graphics.getHeight()/2 - leftCursor.getHeight()/2);

    }

    /**
     * render에 의해 호출되며 draw와 update의 작업을 분리하기 위해 작성하였습니다.
     * update에서는 데이터의 갱신을 담당하게 됩니다. 이는 render로 부터 호출됩니다.
     *
     * @param delta 이전 화면과 현재 화면까지의 갱신된 시간입니다.
     */
    @Override
    public void update(float delta) {

        stage.act(delta);

    }

    @Override
    public void show() {

    }

    /**
     * 화면갱신을 할때 호출됩니다. 이는 다른 엔진의 update와 유사하게 동작합니다.
     *
     * @param delta 이전 화면과 현재 화면까지의 갱신된 시간입니다. 이는 GDX에서 Gdx.graphics.getDeltaTime()으로 전달받습니다.
     */
    @Override
    public void render(float delta) {

        camera.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        {
            // Draw
            batch.draw(rightCursor, rightCursorPos.x, rightCursorPos.y);
            batch.draw(leftCursor, leftCursorPos.x, leftCursorPos.y);
        }
        batch.end();

        stage.draw();

        update(delta);

    }

    /**
     * 화면의 사이즈가 변경되면 호출됩니다.
     *
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {



    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void esc() {

    }
}
