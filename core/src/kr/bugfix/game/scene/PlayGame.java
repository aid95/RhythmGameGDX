package kr.bugfix.game.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;

import kr.bugfix.game.RhythmGame;
import kr.bugfix.game.datastruct.MusicNode;
import kr.bugfix.game.datastruct.MusicDataInfo;
import kr.bugfix.game.system.GameUtils;

public class PlayGame
        extends BaseScene
{
    // Sprite 사용하기!!!!
    private static final int MAX_TOUCH_COUNT = 2;
    private static final int CURSOR_OFFSET = 30;
    private static final int CURSOR_SPEED = 300;
    private static final int NODE_SPEED = 50;

    // 게임이 진행된 시간
    private Float gamePlayTime;

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

    /**
     * Json 사용을 위한 클래스
     */
    private MusicDataInfo musicDataInfo;

    /**
     * 생성된 노드를 관리하기 위한 가변배열
     */
    private ArrayList<MusicNode> nodeArrayList;

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

    public PlayGame() {
        super();
        stage = new Stage(viewport);
        init();
    }

    /**
     * 초기작업을 위해 호출되며 가장 먼저 호출되게 됩니다.
     */
    @Override
    public void init() {
        music = Gdx.audio.newMusic(Gdx.files.internal("01-Courtesy.mp3"));

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
        backgroundImage = new Texture("music_bg.jpg");
        mainBackground = new TextureRegion(backgroundImage, 0, 0, backgroundImage.getWidth(), backgroundImage.getHeight());

        // 시작시간 초기화
        gamePlayTime = 0.0f;

        // 노드의 정보를 가지는 가변배열 nodeArrayList 할당
        nodeArrayList = new ArrayList<MusicNode>();

        readJsonFromFile("01-Courtesy.json");
        delayStartMusic = displayCenterPos.x - CURSOR_OFFSET;
    }

    /**
     * render에 의해 호출되며 draw와 update의 작업을 분리하기 위해 작성하였습니다.
     * update에서는 데이터의 갱신을 담당하게 됩니다. 이 함수는 render로 부터 호출됩니다.
     *
     * @param delta 이전 화면과 현재 화면까지의 갱신된 시간입니다.
     */
    @Override
    public void update(float delta) {

        stage.act(delta);

        moveCursorWithTouche(delta); // 커서이동
        createMusicNode(delta);      // 음악노드 생성
        hitNodeCheck(delta);         // 노드와 커서의 충돌처리
        moveNodePosition(delta);     // 노드의 x축 이동
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

    private void hitNodeCheck(float delta) {
        ArrayList<MusicNode> removeNodes = new ArrayList<MusicNode>();

        for (MusicNode node : nodeArrayList)
        {
            if (node.type == MusicNode.DIRECTION_TYPE_RIGHT)
            {
                if (node.rect.overlaps(rightCursorRect))
                {
                    // nodeArrayList.remove(node);
                    removeNodes.add(node);
                }
            }
            else {
                if (node.rect.overlaps(leftCursorRect))
                {
                    // nodeArrayList.remove(node);
                    removeNodes.add(node);
                }
            }
        }

        for (MusicNode node : removeNodes)
        {
            nodeArrayList.remove(node);
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
        if ( !isStart )
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

            for (MusicNode node : nodeArrayList)
            {
                batch.draw(node.getNodeSprite(), node.rect.x, node.rect.y);
            }
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

    private void createMusicNode(float delta) {
        gamePlayTime += delta;
        for(Object nodeObj : musicDataInfo.nodeTimeLine){
            float p = (Long)nodeObj;
            p /= 1000;
            if (gamePlayTime <= p && (p < (gamePlayTime + 0.1)))
            {
                nodeArrayList.add(new MusicNode(new Vector2(displayCenterPos.x, GameUtils.getInstance().getRandomInt(Gdx.graphics.getHeight())), 1));
                musicDataInfo.nodeTimeLine.remove(nodeObj);
                break;
            }
        }
    }

    private void moveNodePosition(float delta) {
        ArrayList<MusicNode> removeNodes = new ArrayList<MusicNode>();

        for (MusicNode node : nodeArrayList)
        {
            if (node.type == MusicNode.DIRECTION_TYPE_RIGHT)
            {
                node.rect.x += NODE_SPEED * delta;
                if (node.rect.x > Gdx.graphics.getWidth())
                {
                    removeNodes.add(node);
                }
            }
            else {
                node.rect.x -= NODE_SPEED * delta;
                if (node.rect.x < 0)
                {
                    removeNodes.add(node);
                }
            }
        }

        for (MusicNode node : removeNodes)
        {
            nodeArrayList.remove(node);
        }
    }

    /**
     * 지정된 파일로부터 Json 데이터를 읽어옵니다. Json의 형식은 MusicDataInfo class에 의해 정해집니다.
     *
     * @param filepath Json파일의 위치를 의미합니다.
     */
    private void readJsonFromFile(String filepath) {

        FileHandle handle = Gdx.files.internal(filepath);
        String fileContent = handle.readString();
        Json  json = new Json();
        json.setElementType(MusicDataInfo.class, "nodeTimeLine", Long.class);
        musicDataInfo = json.fromJson(MusicDataInfo.class, fileContent);
        Gdx.app.log("JSON", "Data name = " + musicDataInfo.title);
    }
}