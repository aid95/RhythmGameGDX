package kr.bugfix.game.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import kr.bugfix.game.manager.SceneManager;
import kr.bugfix.game.system.DBHelper;
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
    private BitmapFont bestTxtFont;

    // 버튼들을 관리하기 위한 wrapper
    private ButtonWrapper buttonWrapper;

    // 데이터베이스로 부터 최고점수를 받아올 변수
    private int bestScore;

    // 가변길이를 가운데 정렬하기 위해 사용되는 변수들.
    private GlyphLayout bestScoreTxtLayout;
    private GlyphLayout scoreTxtLayout;

    public ResultScene() {
        super();
        stage = new Stage(viewport);
        init();
    }

    @Override
    public void init() {

        // 플레이어1, 2의 점수를 비교해서 가장 높은 점수를 DB에 저장합니다.
        Vector2 score = GameEnv.getInstance().getScore();
        int storeWinScore = (int)((score.x > score.y)? score.x: score.y);
        DBHelper.getInstance().insertScore(storeWinScore);
        bestScore = DBHelper.getInstance().getBestScore();

        // 배경화면 초기화
        backgroundImage = new Texture(Gdx.files.internal("menu_bg.png"));
        textureRegion = new TextureRegion(backgroundImage, 0, 0, backgroundImage.getWidth(), backgroundImage.getHeight());

        // 나가기 버튼
        buttonWrapper = new ButtonWrapper();
        Button btn;
        btn = new Button(BUTTON_EXIT, "exit_down.png", "exit_up.png");
        btn.setPosition(GameEnv.displayWidth-100, 65);
        buttonWrapper.add(btn);

        // 사용할 폰트의 설정을 생성
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("pixel.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.color = Color.WHITE;
        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 3;
        resultTxtFont = generator.generateFont(parameter);

        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.color = Color.WHITE;
        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 3;
        bestTxtFont = generator.generateFont(parameter);

        generator.dispose();

        bestScoreTxtLayout = new GlyphLayout(bestTxtFont, "BEST SCORE : " + bestScore);
        scoreTxtLayout = new GlyphLayout(resultTxtFont, "P1:"+score.x+" / P2:"+score.y);
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
            batch.draw(textureRegion, 0, 0, GameEnv.displayWidth, GameEnv.displayHeight);
            // 버튼 그리기
            buttonWrapper.render(batch);
            // 플레이어 점수 출력
            resultTxtFont.draw(batch, scoreTxtLayout, (GameEnv.displayWidth - scoreTxtLayout.width)/2, (GameEnv.displayHeight + scoreTxtLayout.height)/2);
            bestTxtFont.draw(batch, bestScoreTxtLayout, (GameEnv.displayWidth - bestScoreTxtLayout.width)/2, (GameEnv.displayHeight + bestScoreTxtLayout.height)/2 + 180);
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
