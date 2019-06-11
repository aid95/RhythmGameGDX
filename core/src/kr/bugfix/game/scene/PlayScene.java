package kr.bugfix.game.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import kr.bugfix.game.manager.FontManager;
import kr.bugfix.game.manager.NodeManager;
import kr.bugfix.game.manager.SceneManager;
import kr.bugfix.game.system.GameEnv;

public class PlayScene
        extends BaseScene
{

    // Sprite 사용하기!!!!
    private static final int MAX_TOUCH_COUNT = 2;
    private static final int CURSOR_OFFSET = 30;
    private static final int CURSOR_SPEED = 300;

    // 게임이 진행된 시간
    private Float gamePlayTime;
    private Float lastCreateNodeTime;

    // 모든 노드를 관리하는 매니저
    private NodeManager nodeManager;

    // 게임이 끝났을때 표시될 아이들을 위한 flag
    private boolean isGameOver;

    /**
     * @var leftCursorPosY  왼쪽 커서의 Y 좌표를 가집합니다.
     * @var rightCursorPosY 오른쪽 커서의 Y 좌표를 가집니다.
     */
    private Sprite leftCursor;
    public Rectangle leftCursorRect;
    private Sprite rightCursor;
    public Rectangle rightCursorRect;

    /**
     * 배경화면
     */
    private Texture backgroundImage;
    private TextureRegion mainBackground;

    // 폰트
    private BitmapFont timeLimitTxtFont;

    /**
     * 게임 화면의 가운데 위치를 가집니다.
     */
    private Vector2 displayCenterPos;

    /**
     * 배경음악을 수행할 Music 객체
     */
    private Music music;

    private float delayStartMusic;
    private boolean isStart;

    /**
     * ############################################
     * #               FUNCTIONS                  #
     * ############################################
     */
    public PlayScene() {
        super();
        stage = new Stage(viewport);

        init();
    }

    /**
     * 초기작업을 위해 호출되며 가장 먼저 호출되게 됩니다.
     */
    @Override
    public void init() {
        isGameOver = false;

        String mp3 = GameEnv.getInstance().getCurrentStageInfo().mp3Path;
        music = Gdx.audio.newMusic(Gdx.files.internal(mp3));

        nodeManager = new NodeManager();
        nodeManager.init(batch);

        // 화면 중앙 위치를 가지는 Vector2
        displayCenterPos = new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);

        // 게임 커서 범위, 스프라이트 초기화
        rightCursor = new Sprite(new Texture("right_cursor.png"));
        rightCursorRect = new Rectangle();
        rightCursorRect.x = Gdx.graphics.getWidth() - rightCursor.getWidth() - CURSOR_OFFSET;
        rightCursorRect.y = rightCursor.getHeight()/2 - displayCenterPos.y;
        rightCursorRect.width = rightCursor.getWidth();
        rightCursorRect.height = rightCursor.getHeight();
        leftCursor = new Sprite(new Texture("left_cursor.png"));
        leftCursorRect = new Rectangle();
        leftCursorRect.x = CURSOR_OFFSET;
        leftCursorRect.y = leftCursor.getHeight()/2 - displayCenterPos.y;
        leftCursorRect.width = leftCursor.getWidth();
        leftCursorRect.height = leftCursor.getHeight();

        // 배경화면 등록과 화면 사이즈에 맞게 늘리기
        backgroundImage = new Texture("ingame_bg.png");
        mainBackground = new TextureRegion(backgroundImage, 0, 0, backgroundImage.getWidth(), backgroundImage.getHeight());

        // 시작시간 초기화
        gamePlayTime = 0.0f;
        lastCreateNodeTime = 0.0f;

        delayStartMusic = displayCenterPos.x - CURSOR_OFFSET;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("pixel.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 21;
        parameter.color = Color.WHITE;
        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetX = 2;
        parameter.shadowOffsetY = 2;
        timeLimitTxtFont = generator.generateFont(parameter);
    }

    /**
     * render에 의해 호출되며 draw와 update의 작업을 분리하기 위해 작성하였습니다.
     * update에서는 데이터의 갱신을 담당하게 됩니다. 이 함수는 render로 부터 호출됩니다.
     *
     * @param delta 이전 화면과 현재 화면까지의 갱신된 시간입니다.
     */
    @Override
    public void update(float delta) {
        gamePlayTime += delta;

        stage.act(delta);

        // 게임 중
        if (gamePlayTime < nodeManager.getCurrentMusicPlayTime()) {
            moveCursorWithTouche(delta); // 커서이동

            nodeManager.update(delta);
            nodeManager.hitNodeCheck(delta, leftCursorRect, rightCursorRect);
        }
        else if (gamePlayTime < (nodeManager.getCurrentMusicPlayTime() + 3))
        {
            // 게임이 끝나고 잠시 딜레이를 주기위한 분기점
            isGameOver = true;
        }
        // 씬 change!!
        else {
            SceneManager.getInstance().changeScene(new ResultScene());
        }
    }

    /**
     * 화면 중앙을 기준으로 터치를 구분하여 왼쪽 오른쪽 커서의 Y축으로 움직입니다.
     */
    private void moveCursorWithTouche(float delta) {
        for (int i = 0; i < MAX_TOUCH_COUNT; i++)
        {
            if (Gdx.input.isTouched(i))
            {
                if (Gdx.input.getX(i) < displayCenterPos.x)
                {
                    leftCursorRect.y = Gdx.graphics.getHeight() - Gdx.input.getY(i) - (leftCursor.getHeight()/2) + (CURSOR_SPEED*delta);
                }
                else {
                    rightCursorRect.y = Gdx.graphics.getHeight() - Gdx.input.getY(i) - (rightCursor.getHeight()/2) + (CURSOR_SPEED*delta);
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
        if (!isStart)
        {
            delayStartMusic -= CURSOR_SPEED * delta;
            if (delayStartMusic <= 0)
            {
                isStart = true;
                music.play();
            }
        }

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
            batch.draw(rightCursor, rightCursorRect.x, rightCursorRect.y);
            batch.draw(leftCursor, leftCursorRect.x, leftCursorRect.y);
            if (isGameOver) {
                FontManager.getInstance().init().setShadow(Color.BLACK, 3, 3).setSize(50).getPixelFont().draw(batch, "GAME OVER!!", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
            }
            else {
                timeLimitTxtFont.draw(batch, gamePlayTime.intValue() + " / " + nodeManager.getCurrentMusicPlayTime(), Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
            }
        }
        batch.end();

        nodeManager.render(delta);

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
    public boolean eventTouchUp(int screenX, int screenY, int pointer, int button) {
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
        if (music.isPlaying())
            music.pause();
        music.dispose();
        backgroundImage.dispose();
    }

    @Override
    public void esc() {

    }

}