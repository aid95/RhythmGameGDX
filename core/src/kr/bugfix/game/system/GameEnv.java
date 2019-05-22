package kr.bugfix.game.system;

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
        score = 0;
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
    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int point) { this.score += point; }

    public void subScore(int point) { this.score -= point; }
}
