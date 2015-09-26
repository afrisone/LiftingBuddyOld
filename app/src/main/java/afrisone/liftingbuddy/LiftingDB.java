package afrisone.liftingbuddy;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public final class LiftingDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LiftingDB";

    //Macros Table
    private static final String TABLE_MACROS = "macros";
    private static final String KEY_CALORIES = "calories";
    private static final String KEY_PROTEIN = "protein";
    private static final String KEY_FAT = "fat";
    private static final String KEY_CARBS = "carbs";

    public LiftingDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create the Macronutrients table in the SQLite database
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_MACROS_TABLE = "CREATE TABLE" + TABLE_MACROS + "(" + KEY_CALORIES
                + " INTEGER PRIMARY KEY," + KEY_PROTEIN + " INTEGER," + KEY_FAT + " INTEGER"
                + KEY_CARBS + " INTEGER" + ")";
        db.execSQL(CREATE_MACROS_TABLE);
    }

    //Create new tables after upgrading
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MACROS);
        onCreate(db);
    }

    public void addMacros(Macro macros){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CALORIES, macros.getCalories());
        values.put(KEY_PROTEIN, macros.getProtein());
        values.put(KEY_FAT, macros.getFat());
        values.put(KEY_CARBS, macros.getCarbohydrates());

        db.insert(TABLE_MACROS, null, values);
        db.close();
    }

    public Macro getMacros(){

    }

    public void deleteMacros(Macro macros){}

}

