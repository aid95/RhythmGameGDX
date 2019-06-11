package kr.bugfix.game.scene;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import kr.bugfix.game.RhythmGame;
import kr.bugfix.game.manager.SceneManager;

public abstract class BaseScene
        implements Screen
{
    protected Stage stage;
    protected OrthographicCamera camera;
    protected StretchViewport viewport;
    protected SpriteBatch batch;
    protected RhythmGame app;

    /**
     * Scene의 Base를 지정합니다.
     *
     * @param app   기반이 될 Root Scene
     */
    public BaseScene() {
        RhythmGame entryScene = (RhythmGame) SceneManager.getInstance().getEntryScene();

        this.camera = entryScene.camera;
        this.viewport = entryScene.viewport;
        this.batch = entryScene.batch;
        this.app = entryScene;
    }

    public abstract void init();
    public abstract void update(float delta);
    public abstract void esc();

    public abstract boolean eventTouchDown(int screenX, int screenY, int pointer, int button);
    public abstract boolean eventTouchDragged(int screenX, int screenY, int pointer);
    public abstract boolean eventTouchUp(int screenX, int screenY, int pointer, int button);
}
