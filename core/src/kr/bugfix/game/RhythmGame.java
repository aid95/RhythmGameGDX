package kr.bugfix.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import kr.bugfix.game.scene.PlayGame;
import kr.bugfix.game.system.BaseScene;

public class RhythmGame extends Game {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;

	public SpriteBatch batch;
	public OrthographicCamera camera;
	public StretchViewport viewport;

	@Override
	public void create () {

	    Gdx.input.setCatchBackKey(true);

		camera = new OrthographicCamera(WIDTH, HEIGHT); 		// 화면의 크기
	    viewport = new StretchViewport(WIDTH, HEIGHT, camera); 	// 게임 내부의 크기
		batch = new SpriteBatch();

		setScreen(new PlayGame(this));
	}

	@Override
	public void render () {

	    if (Gdx.input.isKeyPressed(Input.Keys.BACK) || Gdx.input.isButtonPressed(Input.Buttons.BACK)) {
			((BaseScene)this.getScreen()).esc();
		}
	    super.render();

	}
	
	@Override
	public void dispose () {

		batch.dispose();
	    this.getScreen().dispose();

	}
}
