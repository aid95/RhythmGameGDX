package kr.bugfix.game.Manager;

public class GameManager
{

    private static GameManager instance = null;

    private GameManager() {

    }

    public static GameManager getInstance() {

        if (instance == null)
            instance = new GameManager();
        return instance;
    }

}
