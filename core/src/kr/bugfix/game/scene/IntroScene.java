package kr.bugfix.game.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

import kr.bugfix.game.Manager.SceneManager;

public class IntroScene
    extends BaseScene
    implements Screen
{
    Texture backgroundImage;
    TextureRegion textureRegion;

    public IntroScene() {
        super();
        stage = new Stage(viewport);

        init();
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(textureRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        if (Gdx.input.justTouched()) {
            SceneManager.getInstance().changeScene(new MenuScene());
        }
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
        backgroundImage.dispose();
    }

    @Override
    public void init() {
        backgroundImage = new Texture("intro_bg.png");
        textureRegion = new TextureRegion(backgroundImage, 0, 0, backgroundImage.getWidth(), backgroundImage.getHeight());
    }

    @Override
    public void show() {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void esc() {

    }

    @Override
    public boolean eventTouchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean eventTouchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean eventTouchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

}
