package kr.bugfix.game.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import org.json.JSONArray;

import kr.bugfix.game.RhythmGame;
import kr.bugfix.game.system.BaseScene;

public class PlayGame
        extends BaseScene
{
    // Sprite 사용하기!!!!
    private static final int FINGER_COUNT = 2;
    private static final int CURSOR_OFFSET = 30;
    private static final int CURSOR_SPEED = 300;

    /**
     * @var leftCursorPosY  왼쪽 커서의 Y 좌표를 가집합니다.
     * @var rightCursorPosY 오른쪽 커서의 Y 좌표를 가집니다.
     */
    private Sprite leftCursor;
    private Vector2 leftCursorPos;
    private Sprite rightCursor;
    private Vector2 rightCursorPos;

    /**
     * 배경화면
     */
    private Texture backgroundImage;
    private TextureRegion mainBackground;

    /**
     * Json 사용을 위한 자료
     */
    JSONArray jsonMusicData;

    /**
     * 게임 화면의 가운데 위치를 가집니다.
     */
    private Vector2 displayCenterPos;

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

        // 화면 중앙 위치를 가지는 Vector2
        displayCenterPos = new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);

        // 게임 커서 위치, 스프라이트 초기화
        rightCursor = new Sprite(new Texture("right_cursor.png"));
        rightCursorPos = new Vector2(Gdx.graphics.getWidth() - rightCursor.getWidth() - CURSOR_OFFSET, displayCenterPos.y - rightCursor.getHeight()/2);
        leftCursor = new Sprite(new Texture("left_cursor.png"));
        leftCursorPos = new Vector2(CURSOR_OFFSET,  displayCenterPos.y - leftCursor.getHeight()/2);

        // 배경화면 등록과 화면 사이즈에 맞게 늘리기
        backgroundImage = new Texture(Gdx.files.internal("music_bg.jpg"));
        mainBackground = new TextureRegion(backgroundImage, 0, 0, backgroundImage.getWidth(), backgroundImage.getHeight());

        jsonMusicData = new JSONArray();
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

        // 터치입력 컨트롤
        for (int i = 0; i < 2; i++)
        {
            if (Gdx.input.isTouched(i))
            {
                if (Gdx.input.getX(i) < displayCenterPos.x)
                {
                    leftCursorPos.y = Gdx.graphics.getHeight() - Gdx.input.getY(i) - (leftCursor.getHeight()/2) + (CURSOR_SPEED*delta);
                }
                else
                {
                    rightCursorPos.y = Gdx.graphics.getHeight() - Gdx.input.getY(i) - (rightCursor.getHeight()/2) + (CURSOR_SPEED*delta);
                }
            }
        }

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

        batch.enableBlending();
        batch.begin();
        {
            // Draw
            // Background
            batch.draw(mainBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            // Cursors
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
    public boolean eventTouchDown(int screenX, int screenY, int pointer, int button) { return false; }

    @Override
    public boolean eventTouchDragged(int screenX, int screenY, int pointer) {
        return false;
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

    // PRIVATE
    private void createMusicNode() {

    }

    private void readJsonFromFile(String filepath) {

    }
}
