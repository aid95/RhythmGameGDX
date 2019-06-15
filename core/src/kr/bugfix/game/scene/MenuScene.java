package kr.bugfix.game.scene;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

import kr.bugfix.game.system.DBHelper;
import kr.bugfix.game.ui.ButtonWrapper;
import kr.bugfix.game.manager.SceneManager;
import kr.bugfix.game.ui.Button;
import kr.bugfix.game.datastruct.StageInfo;
import kr.bugfix.game.system.GameEnv;

public class MenuScene
        extends BaseScene
{
    // 버튼 핸들링을 위한 버튼 아이디들의 레이블
    private final int BUTTON_SELECT_LEFT = 1;
    private final int BUTTON_SELECT_RIGHT = 2;
    private final int BUTTON_START = 3;
    private final int BUTTON_EXIT = 4;

    // 배경화면을 위한 객체들
    private Texture backgroundImage;
    private TextureRegion textureRegion;

    private TextureRegion thumbnailTextrue;
    private ButtonWrapper buttonWrapper;

    public MenuScene() {
        super();
        stage = new Stage(viewport);

        init();
    }

    @Override
    public void init() {
        // 게임시작전 점수를 0으로 초기화.
        GameEnv.getInstance().resetScore();

        backgroundImage = new Texture(Gdx.files.internal("menu_bg.png"));
        textureRegion = new TextureRegion(backgroundImage, 0, 0, backgroundImage.getWidth(), backgroundImage.getHeight());

        buttonWrapper = new ButtonWrapper();
        Button btn;
        btn = new Button(BUTTON_SELECT_LEFT, "select_btn.png", "select_btn.png");
        btn.setPosition(120, GameEnv.displayHeight/2);
        buttonWrapper.add(btn);
        btn = new Button(BUTTON_SELECT_RIGHT, "select_btn.png", "select_btn.png");
        btn.setPosition(GameEnv.displayWidth-120, GameEnv.displayHeight/2);
        buttonWrapper.add(btn);
        btn = new Button(BUTTON_EXIT, "exit_down.png", "exit_up.png");
        btn.setPosition(GameEnv.displayWidth-100, 65);
        buttonWrapper.add(btn);
        btn = new Button(BUTTON_START, "start_btn.png", "start_btn.png");
        btn.setPosition(GameEnv.displayWidth/2, GameEnv.displayHeight/2-100);
        buttonWrapper.add(btn);

        changeThumbnail(GameEnv.getInstance().stageIndex);
    }

    private void changeThumbnail(int index)
    {
        thumbnailTextrue = null;
        StageInfo stageInfo = GameEnv.getInstance().getStageInfo(index);
        Texture tmp = new Texture(stageInfo.thumbnail);
        thumbnailTextrue = new TextureRegion(tmp, 0, 0, tmp.getWidth(), tmp.getHeight());
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
                Gdx.app.exit();
                break;

            case BUTTON_SELECT_RIGHT:
                // 선곡 오른쪽 버튼
                if (GameEnv.getInstance().stageIndex < GameEnv.getInstance().getStageSize() - 1)
                {
                    GameEnv.getInstance().stageIndex++;
                    changeThumbnail(GameEnv.getInstance().stageIndex);
                }
                break;

            case BUTTON_SELECT_LEFT:
                // 선곡 왼쪽 버튼
                if (GameEnv.getInstance().stageIndex == 0) break;
                GameEnv.getInstance().stageIndex--;
                changeThumbnail(GameEnv.getInstance().stageIndex);
                break;

            case BUTTON_START:
                SceneManager.getInstance().changeScene(new PlayScene());
                break;
        }
        return false;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(textureRegion, 0, 0, GameEnv.displayWidth, GameEnv.displayHeight);

        batch.draw(thumbnailTextrue,
                (GameEnv.displayWidth/2) - (thumbnailTextrue.getRegionWidth()/2) + 50,
                (GameEnv.displayHeight/2) - (thumbnailTextrue.getRegionHeight()/2) + 20,
                365,
                185);

        buttonWrapper.render(batch);
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
        backgroundImage.dispose();
    }

}
