package jzkt3.umkc.edu.testtestetestsetestsetes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Message;
import android.database.SQLException;
import android.widget.Toast;

import junit.framework.Assert;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Created by jzz on 4/8/2015.
 */
public class DBAdapter {

    DBHelper helper;
    ArrayList<History> listHistory = new ArrayList<>();

    public DBAdapter(Context context){
        helper = new DBHelper(context);
    }

    public long insertData(String date,String time,String name,String rate,String dollars,String result){

        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.DATE, date);
        contentValues.put(DBHelper.TIME, time);
        contentValues.put(DBHelper.NAME, name);
        contentValues.put(DBHelper.RATE, rate);
        contentValues.put(DBHelper.DOLLARS, dollars);
        contentValues.put(DBHelper.RESULT, result);
        long id = db.insert(DBHelper.TABLE_NAME,null,contentValues);
        return id;
    }


    public ArrayList<History> getData(){
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {DBHelper.UID,DBHelper.DATE,DBHelper.TIME,DBHelper.NAME,DBHelper.RATE,DBHelper.DOLLARS,DBHelper.RESULT};
        Cursor cursor = db.query(DBHelper.TABLE_NAME,columns,null,null,null,null,null);



        while(cursor.moveToNext()) {
            History history = new History();

            int index1 = cursor.getColumnIndex(DBHelper.DATE);
            String date = cursor.getString(index1);
            Assert.assertTrue("Could not set date",history.setDate(date));

            int index2 = cursor.getColumnIndex(DBHelper.TIME);
            String time = cursor.getString(index2);
            Assert.assertTrue("Could not set time",history.setTime(time));

            int index3 = cursor.getColumnIndex(DBHelper.NAME);
            String name = cursor.getString(index3);
            Assert.assertTrue("Could not set name",history.setName(name));

            int index4 = cursor.getColumnIndex(DBHelper.RATE);
            String rate = cursor.getString(index4);
            Assert.assertTrue("Could not set rate",history.setRate(rate));

            int index5 = cursor.getColumnIndex(DBHelper.DOLLARS);
            String dollars = cursor.getString(index5);
            Assert.assertTrue("Could not set dollars",history.setDollars(dollars));

            int index6 = cursor.getColumnIndex(DBHelper.RESULT);
            String result = cursor.getString(index6);
            Assert.assertTrue("Could not set result",history.setResult(result));


            listHistory.add(history);

        }
        cursor.close();
        return listHistory;

    }

    public void reseed(){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ = 0 WHERE NAME = HTABLE");

    }

    public void delete(String d) throws Exception {
        SQLiteDatabase db = helper.getWritableDatabase();

        // Checks if selected database row is below 0
        // If yes, then the row does not exist
        // Throws:
        // Exception - if row to be deleted does not exist
        if(db.delete("HTABLE","result="+d,null) < 0){
            Exception x = new Exception("DNE");
            throw x;
        }

        db.delete("HTABLE", "result=" + d, null);

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
        private static final int DATABASE_VERSION = 10;
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+DATE+" VARCHAR(255), "+TIME+" VARCHAR(255), "+NAME+" VARCHAR(255), "+RATE+" VARCHAR(255), "+DOLLARS+" VARCHAR(255), "+RESULT+" VARCHAR(255));";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;

        public DBHelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
                Toast.makeText(context,"Database Created",Toast.LENGTH_SHORT).show();
            }
            catch(SQLException e){
                Toast.makeText(context,"ERROR MESSAGE", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try {
                db.execSQL(DROP_TABLE);
                Toast.makeText(context,"Database Updated",Toast.LENGTH_SHORT).show();
                onCreate(db);

            }
            catch(SQLException e){
                Toast.makeText(context,"ERROR MESSAGE", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
