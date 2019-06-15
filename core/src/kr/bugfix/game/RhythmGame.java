package kr.bugfix.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import kr.bugfix.game.scene.IntroScene;
import kr.bugfix.game.scene.BaseScene;
import kr.bugfix.game.manager.SceneManager;

public class RhythmGame
        extends Game
        implements InputProcessor
{

    private static final int WIDTH = 800;
    private static final int HEIGHT = 480;

    public SpriteBatch batch;
    public OrthographicCamera camera;
    public StretchViewport viewport;

    @Override
    public void create () {
        Gdx.input.setCatchBackKey(true);

        camera = new OrthographicCamera();           // 화면의 크기
        // camera.position.set(WIDTH/2, HEIGHT/2, 0);
        camera.setToOrtho(false, WIDTH, HEIGHT);
        viewport = new StretchViewport(WIDTH, HEIGHT, camera);    // 게임 내부의 크기
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        // 진입점이 되는 Scene을 지정
        SceneManager.getInstance().setEntryScene(this);

        // Scene 등록
        BaseScene nextScene = new IntroScene();
        setScreen(nextScene);

        // 입력작업 등록
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render () {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK) || Gdx.input.isButtonPressed(Input.Buttons.BACK)) {
            ((BaseScene)this.getScreen()).esc();
        }
        super.render();
    }

    /**
     * 사용이 끝난 자원을 반환합니다.
     */
    @Override
    public void dispose () {
        batch.dispose();
        this.getScreen().dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 up = new Vector3(screenX, screenY, 0);
        up = camera.unproject(up);
        SceneManager.getInstance().getCurrentScene().eventTouchUp((int)up.x, (int)up.y, pointer, button);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
