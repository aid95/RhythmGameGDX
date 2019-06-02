package kr.bugfix.game.system;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.compression.lzma.Base;

import kr.bugfix.game.scene.BaseScene;

public class SceneManager
{
    private static SceneManager instance = null;

    private SceneManager() {

    }

    public static SceneManager getInstance() {

        if (instance == null)
        {
            instance = new SceneManager();
        }
        return instance;
    }

    private Game entryScene = null;

    public void setEntryScene(Game entryScene) {
        this.entryScene = entryScene;
    }

    public Game getEntryScene() {
        return entryScene;
    }

    public void changeScene(BaseScene scene) {

        try {
            scene.init();
            entryScene.setScreen(scene);
        }
        catch (NullPointerException e) {
            Gdx.app.log("SCENEMANAGER", "Entry scene is NULL");
        }
    }
}
