package tk.kolyanov.reminder;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;


public class DataBaseHelper extends SQLiteOpenHelper implements BaseColumns{

    public static String DATABASE_NAME = "reminder.db";
    public static int DATABASE_VERSION = 1;
    public static String DATABASE_TABLE = "remind";

    public static String HEADER = "header";
    public static String DESCRIPTION = "description";

    public static String CREATE_DATABASE = "create table"
            + DATABASE_TABLE + " (" + BaseColumns._ID
            + " integer primary key autoincrement, "
            + HEADER + " text not null, " + DESCRIPTION
            + " text not null);";

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

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }
}
