package kr.bugfix.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;

import kr.bugfix.game.datastruct.AttackNode;
import kr.bugfix.game.datastruct.MusicDataInfo;
import kr.bugfix.game.datastruct.MusicNode;
import kr.bugfix.game.datastruct.Node;
import kr.bugfix.game.system.GameEnv;

public class NodeManager
{
    private static final int NODE_SPEED = 500;

    /**
     * Json 사용을 위한 클래스
     */
    private MusicDataInfo musicDataInfo;

    private ArrayList<MusicNode> nodeArrayList;
    private ArrayList<AttackNode> attackNodeArrayList;
    private Batch mainBatch;

    private float lastCreateNodeTime;
    private float gamePlayTime;

    public void init(Batch batch) {
        readJsonFromFile(GameEnv.getInstance().getCurrentStageInfo().jsonPath);

        lastCreateNodeTime = 0;
        gamePlayTime = 0;

        mainBatch = batch;
        nodeArrayList = new ArrayList<MusicNode>();
        attackNodeArrayList = new ArrayList<AttackNode>();
    }

    public void render(float delta) {
        mainBatch.begin();
        // 음악 노드의 렌더링
        for (MusicNode node : nodeArrayList) {
            mainBatch.draw(node.getNodeSprite(), node.rect.x, node.rect.y);
        }

        // 공격 노드의 렌더링
        for (AttackNode attackNode : attackNodeArrayList) {
            mainBatch.draw(attackNode.getNodeSprite(), attackNode.rect.x, attackNode.rect.y);
        }
        mainBatch.end();
    }

    public int getCurrentMusicPlayTime()
    {
        return musicDataInfo.playTime;
    }

    public void update(float delta) {
        createMusicNode(delta);      // 음악노드 생성
        moveNodePosition(delta);     // 노드의 x축 이동
        moveAttackNodePosition(delta); // AttackNode의 이동
    }

    public void createMusicNode(float delta) {
        gamePlayTime += delta;

        for (Object nodeObj : musicDataInfo.nodeTimeLineLeft) {
            Float energy = (Float) nodeObj;
            if ((gamePlayTime - lastCreateNodeTime) >= 0.3) {
                nodeArrayList.add(new MusicNode(new Vector2(GameEnv.displayWidth/2.0f, energy * 10), MusicNode.DIRECTION_TYPE_LEFT));
                musicDataInfo.nodeTimeLineLeft.remove(nodeObj);
                break;
            }
        }

        for (Object nodeObj : musicDataInfo.nodeTimeLineRight) {
            Float energy = (Float) nodeObj;
            if ((gamePlayTime - lastCreateNodeTime) >= 0.3) {
                nodeArrayList.add(new MusicNode(new Vector2(GameEnv.displayWidth/2.0f, energy * 10), MusicNode.DIRECTION_TYPE_RIGHT));
                musicDataInfo.nodeTimeLineRight.remove(nodeObj);
                lastCreateNodeTime = gamePlayTime;
                break;
            }
        }
    }

    public void moveNodePosition(float delta) {
        ArrayList<MusicNode> removeNodes = new ArrayList<MusicNode>();

        for (MusicNode node : nodeArrayList) {
            if (node.type == MusicNode.DIRECTION_TYPE_RIGHT) {
                node.rect.x += NODE_SPEED * delta;
                if (node.rect.x > GameEnv.displayWidth) {
                    removeNodes.add(node);
                }
            } else {
                node.rect.x -= NODE_SPEED * delta;
                if (node.rect.x < 0) {
                    removeNodes.add(node);
                }
            }
        }

        for (MusicNode node : removeNodes) {
            nodeArrayList.remove(node);
        }
    }

    public void moveAttackNodePosition(float delta) {
        for (AttackNode attackNode : attackNodeArrayList) {
            attackNode.move(delta, NODE_SPEED);
        }
    }

    /**
     * 지정된 파일로부터 Json 데이터를 읽어옵니다. Json의 형식은 MusicDataInfo class에 의해 정해집니다.
     *
     * @param filepath Json파일의 위치를 의미합니다.
     */
    private void readJsonFromFile(String filepath) {
        FileHandle handle = Gdx.files.internal(filepath);
        String fileContent = handle.readString();
        Json json = new Json();
        json.setElementType(MusicDataInfo.class, "nodeTimeLineLeft", Float.class);
        json.setElementType(MusicDataInfo.class, "nodeTimeLineRight", Float.class);
        musicDataInfo = json.fromJson(MusicDataInfo.class, fileContent);
        Gdx.app.log("JSON", "Data name = " + musicDataInfo.title);
    }

    /**
     * 노드의 충돌을 검사합니다.
     *
     * @param delta
     */
    public void hitNodeCheck(float delta, Rectangle leftCursorRect, Rectangle rightCursorRect) {
        ArrayList<MusicNode> removeNodes = new ArrayList<MusicNode>();
        ArrayList<AttackNode> removeAttackNodes = new ArrayList<AttackNode>();

        for (MusicNode node : nodeArrayList) {
            if (node.type == MusicNode.DIRECTION_TYPE_RIGHT)
            {
                if (node.rect.overlaps(rightCursorRect))
                {
                    removeNodes.add(node);
                    attackNodeArrayList.add(new AttackNode(node.getCenterPosition(), MusicNode.DIRECTION_TYPE_LEFT));
                }
            }
            else {
                if (node.rect.overlaps(leftCursorRect))
                {
                    removeNodes.add(node);
                    attackNodeArrayList.add(new AttackNode(node.getCenterPosition(), MusicNode.DIRECTION_TYPE_RIGHT));
                }
            }
        }

        // LEFT가 player 0, RIGHT가 player 1
        for (AttackNode attackNode : attackNodeArrayList) {
            // 막거나 공격을 성공하면 일정 점수를 각 플레이어에게 더함.
            if (attackNode.type == Node.DIRECTION_TYPE_RIGHT)
            {
                if (attackNode.rect.overlaps(rightCursorRect))
                {
                    GameEnv.getInstance().addScore(10, 1); // Player1 점수 추가
                    removeAttackNodes.add(attackNode);
                }
                else if (attackNode.getCenterPosition().x > GameEnv.displayWidth)
                {
                    GameEnv.getInstance().addScore(5, 0); // Player0 점수 점수추가
                    removeAttackNodes.add(attackNode);
                }
            } else {
                if (attackNode.type == Node.DIRECTION_TYPE_LEFT && attackNode.rect.overlaps(leftCursorRect))
                {
                    GameEnv.getInstance().addScore(10, 0);
                    removeAttackNodes.add(attackNode);
                }
                else if (attackNode.getCenterPosition().x < 0)
                {
                    GameEnv.getInstance().addScore(5, 1);
                    removeAttackNodes.add(attackNode);
                }
            }
        }

        for (MusicNode node : removeNodes) {
            nodeArrayList.remove(node);
        }
        for (AttackNode attackNode : removeAttackNodes) {
            attackNodeArrayList.remove(attackNode);
        }
    }

}
