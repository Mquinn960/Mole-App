package mquinn.whackamole;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mquinn on 05/04/2017.
 */
public class Dbhelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "highscores.db";
    private static final String TABLE_NAME = "scoreboard";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SCORE = "score";

    private static final String TABLE_CREATE = "CREATE TABLE " +
                                                TABLE_NAME + " (" +
                                                COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                                                COLUMN_NAME + " TEXT NOT NULL, "+
                                                COLUMN_SCORE + " TEXT NOT NULL);";

    SQLiteDatabase db;

    public Dbhelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);
        //this.db = db;

    }

    public void addPlayer(Player newPlayer) {

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "SELECT * FROM " +  TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(COLUMN_ID, count);
        values.put(COLUMN_NAME, newPlayer.getVarName());
        values.put(COLUMN_SCORE, newPlayer.getVarScore());

        db.insert(TABLE_NAME, null, values);
        db.close();

    }

    public List<Player> getAll() {

        List<Player> dataList = new ArrayList<>();
        db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
                do {
                    Player data = new Player();
                    data.setVarName(cursor.getString(1));
                    data.setVarScore(Integer.parseInt(cursor.getString(2)));
                    dataList.add(data);
                }
            while(cursor.moveToNext());
        }

        return dataList;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String Upgrade = "DROP TABLE IF EXISTS" + TABLE_NAME;
        db.execSQL(Upgrade);
        this.onCreate(db);

    }
}