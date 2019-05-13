package kr.bugfix.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import kr.bugfix.game.scene.PlayGame;
import kr.bugfix.game.system.BaseScene;

public class RhythmGame
        extends Game
        implements InputProcessor
{
    public static final int WIDTH = 2220;
    public static final int HEIGHT = 1080;

    public SpriteBatch batch;
    public OrthographicCamera camera;
    public StretchViewport viewport;

    private BaseScene currentScene;

    @Override
    public void create () {

        Gdx.input.setCatchBackKey(true);

        camera = new OrthographicCamera(WIDTH, HEIGHT);        // 화면의 크기
        viewport = new StretchViewport(WIDTH, HEIGHT, camera);    // 게임 내부의 크기
        batch = new SpriteBatch();

        // Scene 등록
        currentScene = new PlayGame(this);
        setScreen(currentScene);

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
