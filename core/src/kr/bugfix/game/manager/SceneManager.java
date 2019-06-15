package kr.bugfix.game.manager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.compression.lzma.Base;

import kr.bugfix.game.scene.BaseScene;

public class SceneManager
{

    private static SceneManager instance = null;

    public BaseScene currentScene;

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

    public BaseScene getCurrentScene() {
        while (true)
            if (currentScene != null) {
                return currentScene;
            }
    }

    public void changeScene(BaseScene scene) {
        try {
            currentScene = scene;
            entryScene.create();
            entryScene.setScreen(scene);
        }
        catch (NullPointerException e) {
            Gdx.app.log("SCENEMANAGER", "Entry scene is NULL");
        }
    }

}
