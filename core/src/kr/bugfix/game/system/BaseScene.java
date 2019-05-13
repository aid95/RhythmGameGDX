package kr.bugfix.game.system;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import kr.bugfix.game.RhythmGame;

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
    public BaseScene(RhythmGame app) {
        this.camera = app.camera;
        this.viewport = app.viewport;
        this.batch = app.batch;
        this.app = app;
    }

    public abstract void init();
    public abstract void update(float delta);
    public abstract void esc();

    public abstract boolean eventTouchDown(int screenX, int screenY, int pointer, int button);
    public abstract boolean eventTouchDragged(int screenX, int screenY, int pointer);
}
