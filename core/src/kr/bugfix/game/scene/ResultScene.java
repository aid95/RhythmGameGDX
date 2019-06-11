package kr.bugfix.game.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.awt.Font;

import kr.bugfix.game.manager.FontManager;
import kr.bugfix.game.manager.SceneManager;
import kr.bugfix.game.system.GameEnv;
import kr.bugfix.game.ui.Button;
import kr.bugfix.game.ui.ButtonWrapper;

public class ResultScene
        extends BaseScene
{
    private final int BUTTON_EXIT = 4;

    // 배경화면을 위한 객체들
    private Texture backgroundImage;
    private TextureRegion textureRegion;

    // 폰트
    private BitmapFont resultTxtFont;

    // 버튼들을 관리하기 위한 wrapper
    private ButtonWrapper buttonWrapper;

    public ResultScene() {
        super();
        stage = new Stage(viewport);
        init();
    }

    @Override
    public void init() {
        // 배경화면 초기화
        backgroundImage = new Texture(Gdx.files.internal("menu_bg.png"));
        textureRegion = new TextureRegion(backgroundImage, 0, 0, backgroundImage.getWidth(), backgroundImage.getHeight());

        // 나가기 버튼
        buttonWrapper = new ButtonWrapper();
        Button btn;
        btn = new Button(BUTTON_EXIT, "exit_down.png", "exit_up.png");
        btn.setPosition(Gdx.graphics.getWidth()-100, 65);
        buttonWrapper.add(btn);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("pixel.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.color = Color.WHITE;
        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 3;
        resultTxtFont = generator.generateFont(parameter);
        generator.dispose();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void esc() {

    }

    @Override
    public boolean eventTouchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean eventTouchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean eventTouchUp(int screenX, int screenY, int pointer, int button) {
        int bid = buttonWrapper.checkPress(screenX, screenY);
        Gdx.app.log("TOUCH EVENT", "ID:" + bid + " // X:Y" + screenX + ":" + screenY);
        switch (bid)
        {
            case BUTTON_EXIT:
                SceneManager.getInstance().changeScene(new MenuScene());
                break;
        }
        return false;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Vector2 score = GameEnv.getInstance().getScore();

        batch.begin();
            // 배경화면 그리기
            batch.draw(textureRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            // 버튼 그리기
            buttonWrapper.render(batch);
            // 플레이어 점수 출력
            resultTxtFont.draw(batch, "P1:"+score.x+" / P2"+score.y, Gdx.graphics.getWidth()/2 - 130, Gdx.graphics.getHeight()/2);
        batch.end();

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

}
