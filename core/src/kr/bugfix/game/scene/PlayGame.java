package kr.bugfix.game.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import kr.bugfix.game.RhythmGame;
import kr.bugfix.game.system.BaseScene;

public class PlayGame extends BaseScene {

    public PlayGame(RhythmGame app) {
        super(app);
        stage = new Stage(viewport);
        init();
    }

    @Override
    public void init() {
        // 기타 리소스를 초기화
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        {
            // Draw
        }
        batch.end();

        stage.draw();

        update(delta);
    }

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

