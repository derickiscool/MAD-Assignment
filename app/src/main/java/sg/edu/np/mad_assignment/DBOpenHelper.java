package sg.edu.np.mad_assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {

    private final static String CREATE_EVENTS_TABLE = "create table " + DBStructure.EVENT_TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            +DBStructure.EVENT + " TEXT, "  +DBStructure.DATE+ " TEXT, " +DBStructure.MONTH + " TEXT)";
    private final static String DROP_EVENTS_TABLE= "DROP TABLE IF EXISTS "+DBStructure.EVENT_TABLE_NAME;

    public DBOpenHelper(@Nullable Context context) {

        super(context, DBStructure.DB_NAME, null, DBStructure.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EVENTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_EVENTS_TABLE);
        onCreate(db);

    }
    public void SaveEvent(String event,String date,String month,SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBStructure.EVENT,event);
        contentValues.put(DBStructure.DATE,date);
        contentValues.put(DBStructure.MONTH,month);
        database.insert(DBStructure.EVENT_TABLE_NAME,null,contentValues);

    }
    public Cursor ReadEvents(String date, SQLiteDatabase database){
        String [] Projections = {DBStructure.EVENT,DBStructure.DATE,DBStructure.MONTH};
        String Selection = DBStructure.DATE +"=?";
        String [] SelectionArgs = {date};

        return database.query(DBStructure.EVENT_TABLE_NAME,Projections,Selection,SelectionArgs,null,null,null);
    }
    public Cursor ReadEventsperMonth(String month,SQLiteDatabase database){
        String [] Projections = {DBStructure.EVENT,DBStructure.DATE,DBStructure.MONTH,};
        String Selection = DBStructure.MONTH +"=?";
        String [] SelectionArgs = {month};
        return database.query(DBStructure.EVENT_TABLE_NAME,Projections,Selection,SelectionArgs,null,null,null);
    }
    public void deleteEvent(String event,String date,SQLiteDatabase database){
        String selection = DBStructure.EVENT+"=? and "+DBStructure.DATE+"=?";
        String[] selectionArg = {event,date};
        database.delete(DBStructure.EVENT_TABLE_NAME,selection,selectionArg);
    }

}
