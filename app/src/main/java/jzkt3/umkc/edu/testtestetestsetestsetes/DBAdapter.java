package jzkt3.umkc.edu.testtestetestsetestsetes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Message;
import android.database.SQLException;
import android.widget.Toast;


/**
 * Created by jzz on 4/8/2015.
 */
public class DBAdapter {

    DBHelper helper;
    public DBAdapter(Context context){
        helper = new DBHelper(context);
    }

    public long insertData(String date,String time){

        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.DATE, date);
        contentValues.put(DBHelper.TIME, time);
        long id = db.insert(DBHelper.TABLE_NAME,null,contentValues);
        return id;
    }

    static class DBHelper  extends SQLiteOpenHelper{

        private Context context;
        private static final String DATABASE_NAME = "myDB";
        private static final String TABLE_NAME = "HTABLE";
        private static final String UID = "_id";
        private static final String DATE = "date";
        private static final String TIME = "time";
        private static final String NAME = "name";
        private static final String RATE = "rate";
        private static final String DOLLARS = "dollars";
        private static final String RESULT = "result";
        private static final int DATABASE_VERSION = 7;
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+DATE+" VARCHAR(255), "+TIME+" VARCHAR(255));";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;

        public DBHelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
                Toast.makeText(context,"onCreate",Toast.LENGTH_SHORT).show();
            }
            catch(SQLException e){
                Toast.makeText(context,"ERROR MESSAGE", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try {
                db.execSQL(DROP_TABLE);
                Toast.makeText(context,"onUpgrade",Toast.LENGTH_SHORT).show();
                onCreate(db);

            }
            catch(SQLException e){
                Toast.makeText(context,"ERROR MESSAGE", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
