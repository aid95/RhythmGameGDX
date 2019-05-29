package kr.bugfix.game.system;

import com.badlogic.gdx.math.Vector2;

public class GameEnv
{
    private static GameEnv instance = null;

    public static GameEnv getInstance() {

        if ( instance == null )
        {
            instance = new GameEnv();
        }
        return instance;
    }

    /**
     * 싱글톤을 유지하기 위해 외부에서 GameEnv를 생성할 수 없습니다.
     */
    private GameEnv() {
        gameSpeed = 1;
        score = new Vector2();
    }

    /**
     * 노드의 움직이는 속도를 정합니다.
     */
    private int gameSpeed;

    public int getGameSpeed() {
        return gameSpeed;
    }

    public void setGameSpeed(int gameSpeed) {
        this.gameSpeed = gameSpeed;
    }

    /**
     * 가장 최근에한 게임의 점수를 가지고 있습니다. Scene이 변경되더라도 유지되는 상황을 위해 존재합니다.
     * ex) 게임씬에서 얻은 점수가 결과씬에서 출력
     */
    private Vector2 score;

    public Vector2 getScore() {
        return score;
    }

    public void setScore(int score, int playerId) {
        if (playerId == 0)
            this.score.x = score;
        else
            this.score.y = score;
    }

    public void addScore(int point, int playerId) {
        if (playerId == 0)
            this.score.x += point;
        else
            this.score.y += point;
    }

    public void subScore(int point, int playerId) {
        if (playerId == 0)
            this.score.x -= point;
        else
            this.score.y -= point;
    }

    public void resetScore() {
        score.x = 0;
        score.y = 0;
    }
}
