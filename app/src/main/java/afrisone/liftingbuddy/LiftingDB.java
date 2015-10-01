package afrisone.liftingbuddy;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    private static final String KEY_ID = "table_id";

    public LiftingDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create the Macronutrients table in the SQLite database
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_MACROS_TABLE = "CREATE TABLE" + TABLE_MACROS + "(" + KEY_ID
                + " INTEGER PRIMARY KEY," + KEY_CALORIES + " INTEGER," + KEY_PROTEIN + " INTEGER,"
                + KEY_FAT + " INTEGER" + KEY_CARBS + " INTEGER" + ")";
        db.execSQL(CREATE_MACROS_TABLE);
    }

    //Create new tables after upgrading
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MACROS);
        onCreate(db);
    }

    //Add a new DB entry
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

    //Pull macros from DB
    public Macro getMacros(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MACROS, new String[] { KEY_ID, KEY_CALORIES, KEY_PROTEIN,
            KEY_FAT, KEY_CARBS }, KEY_ID + "=?", new String[] {String.valueOf(id)},
                null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }

        Macro macros = new Macro(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)),
                Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)));

        return macros;

    }

    public int updateMacros(Macro macros){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CALORIES, macros.getCalories());
        values.put(KEY_PROTEIN, macros.getProtein());
        values.put(KEY_CARBS, macros.getCarbohydrates());
        values.put(KEY_FAT, macros.getFat());

        return db.update(TABLE_MACROS, values, KEY_CALORIES + " = ?", new String[]
                {String.valueOf(macros.getID())});
    }

}

