package tk.kolyanov.reminder.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import tk.kolyanov.reminder.Objects.Remind;


public class DataBaseHelper extends SQLiteOpenHelper implements BaseColumns, IDataBaseHelper{

    static DataBaseHelper mDataBaseHelper;

    public static String DATABASE_NAME = "reminder.db";
    public static int DATABASE_VERSION = 2;
    public static String DATABASE_TABLE = "remind";

    public static String HEADER = "header";
    public static String DESCRIPTION = "description";
    public static String DATE_TIME = "date_time";

    public static String CREATE_DATABASE = "create table "
            + DATABASE_TABLE + " (" + BaseColumns._ID
            + " integer primary key autoincrement, "
            + HEADER + " text not null, " + DESCRIPTION
            + " text not null, " + DATE_TIME + " integer not null);";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public static DataBaseHelper getInstance(Context context){
        if(mDataBaseHelper == null){
            mDataBaseHelper = new DataBaseHelper(context);
        }
        return mDataBaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }

    @Override
    public void add(Remind object) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HEADER, object.getHeader());
        values.put(DESCRIPTION, object.getDescription());
        values.put(DATE_TIME, object.getDateTime());

        db.insert(DATABASE_TABLE, null, values);

        db.close();

    }

    @Override
    public Remind getElementById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Remind remind = null;

        String [] columns = {BaseColumns._ID, HEADER, DESCRIPTION, DATE_TIME};
        String [] selectionArgs = {"" + id};
        Cursor cursor = db.query(DATABASE_TABLE, columns, BaseColumns._ID + "= ?", selectionArgs,
                null, null, null);

        if (cursor.moveToFirst()){

            int idColumnIndex = cursor.getColumnIndex(BaseColumns._ID);
            int headerColumnIndex = cursor.getColumnIndex(HEADER);
            int descriptionColumnIndex = cursor.getColumnIndex(DESCRIPTION);
            int dateTimeColumnIndex = cursor.getColumnIndex(DATE_TIME);

            if (cursor.moveToNext()){
                remind = new Remind(cursor.getLong(idColumnIndex),
                        cursor.getString(headerColumnIndex),
                        cursor.getString(descriptionColumnIndex),
                        cursor.getLong(dateTimeColumnIndex));
            }
        }

        db.close();
        return remind;
    }

    @Override
    public List<Remind> getAllElements() {
        SQLiteDatabase db = getReadableDatabase();
        List<Remind> list = new ArrayList<>();

        String [] columns = {BaseColumns._ID, HEADER, DESCRIPTION, DATE_TIME};
        Cursor cursor = db.query(DATABASE_TABLE, columns, null, null, null, null, null);

        if (cursor.moveToFirst()){

            int idColumnIndex = cursor.getColumnIndex(BaseColumns._ID);
            int headerColumnIndex = cursor.getColumnIndex(HEADER);
            int descriptionColumnIndex = cursor.getColumnIndex(DESCRIPTION);
            int dateTimeColumnIndex = cursor.getColumnIndex(DATE_TIME);

            do{
                list.add(new Remind(cursor.getLong(idColumnIndex),
                        cursor.getString(headerColumnIndex),
                        cursor.getString(descriptionColumnIndex),
                        cursor.getLong(dateTimeColumnIndex)));
            }while (cursor.moveToNext());
        }

        db.close();
        return list;
    }

    @Override
    public void update(Remind object) {
        deleteElement(object);
        add(object);
    }

    @Override
    public void deleteElement(Remind object) {
        SQLiteDatabase db = getWritableDatabase();

        String [] whereArgs = {"" + object.getId()};
        db.delete(DATABASE_TABLE, BaseColumns._ID + " = ?", whereArgs);

        db.close();
    }
}
