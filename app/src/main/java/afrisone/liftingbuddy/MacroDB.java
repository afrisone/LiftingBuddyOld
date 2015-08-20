package afrisone.liftingbuddy;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MacroDB {
    private LiftingDB myDatabase;
    private SQLiteDatabase database;
    public final static String MACRO_TABLE = "Macros";
    public final static String CALORIES_ENTRY = "Calories";
    public final static String PROTEIN_ENTRY = "Protein";
    public final static String FAT_ENTRY = "Fat";
    public final static String CARB_ENTRY = "Carbohydrates";

    public MacroDB(Context context){
        myDatabase = new LiftingDB(context);
        database = myDatabase.getWritableDatabase();
    }

    public long createRecords(int calories, int protein, int fat, int carbs){
        ContentValues values = new ContentValues();
        values.put(CALORIES_ENTRY, calories);
        values.put(PROTEIN_ENTRY, protein);
        values.put(FAT_ENTRY, fat);
        values.put(CARB_ENTRY, carbs);

        return database.insert(MACRO_TABLE, null, values);
    }

    public Cursor selectRecords(){
        String [] columns = new String[] {CALORIES_ENTRY, PROTEIN_ENTRY, FAT_ENTRY, CARB_ENTRY};
        Cursor myCursor = database.query(true, MACRO_TABLE, columns, null, null, null, null, null, null);
        if(myCursor != null){
            myCursor.moveToFirst();
        }
        return myCursor;
    }
}
