package afrisone.liftingbuddy;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public final class LiftingDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "LiftingDB";
    private static final int DATABASE_VERSION = 2;
    private int caloriesDB;
    private int proteinDB;
    private int fatDB;
    private int carbohydratesDB;

    public LiftingDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    private static final String DATABASE_CREATE = "CREATE TABLE MACROS(calories int primary key,protein int,fat int,carbs int);";

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(LiftingDB.class.getName(), "Upgrading database from version " + oldVersion +
                " to " + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS MACROS");
        onCreate(database);
    }
}

