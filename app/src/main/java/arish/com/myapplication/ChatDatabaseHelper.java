package arish.com.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class ChatDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "chatDatabase";
    public static int VERSION_NUM = 2;
    public static final String TABLE_NAME = "Chat";
    public static final String KEY_ID= "id";
    public static final String KEY_Message = "message";

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CHAT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_Message + " TEXT)";

        db.execSQL(CREATE_CHAT_TABLE);
    }

    public void insertData(String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_Message, msg);
        long insertResult = db.insert(TABLE_NAME, null, contentValues);
        Log.i("ChatDatabaseHelper", "insert data result: " + insertResult );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int l) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        Log.i("ChatDatabaseHelper", "onUpdate version " + i +" to new database version: " +  l );
        onCreate(db);

    }

    public Cursor getChatMessages(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor;
    }
}
