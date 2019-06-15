package kr.bugfix.game.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.sql.Database;
import com.badlogic.gdx.sql.DatabaseCursor;
import com.badlogic.gdx.sql.DatabaseFactory;
import com.badlogic.gdx.sql.SQLiteGdxException;

public class DBHelper {

    private static DBHelper instance = null;

    public static DBHelper getInstance() {
        if (instance == null) {
            instance = new DBHelper();
        }
        return instance;
    }

    Database dbHandler;

    public static final String TABLE_COMMENTS = "ranks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_COMMENT = "score";

    private static final String DATABASE_NAME = "ranks.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table if not exists "
            + TABLE_COMMENTS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_COMMENT
            + " int not null);";

    private DBHelper() {
        Gdx.app.log("DatabaseTest", "creation started");
        dbHandler = DatabaseFactory.getNewDatabase(DATABASE_NAME,
                DATABASE_VERSION, DATABASE_CREATE, null);

        dbHandler.setupDatabase();
        try {
            dbHandler.openOrCreateDatabase();
            dbHandler.execSQL(DATABASE_CREATE);
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 데이터베이스로 전달받은 스코어를 넣어줍니다.
     * @param score
     */
    public void insertScore(int score) {
        try {
            dbHandler.execSQL("INSERT INTO ranks ('score') VALUES ('"+score+"')");
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 데이터베이에서 ranks를 내림차순으로 정렬하여 가장 큰 점수를 돌려줍니다.
     * @return 현재 기록된 점수중 가장 큰 점수
     */
    public int getBestScore() {
        DatabaseCursor cursor = null;

        try {
            cursor = dbHandler.rawQuery("SELECT * FROM ranks order by score desc");
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
        while (cursor.next()) {
            int ret = cursor.getInt(1);
            cursor.close();
            return ret;
        }
        return -1;
    }

    /**
     * 호출되면 데이터베이스를 종료합니다.
     */
    public void dbclose() {
        try {
            dbHandler.closeDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
        dbHandler = null;
    }

}
