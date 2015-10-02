package afrisone.liftingbuddy;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;


public class FinalCaloriesAndMacrosActivity extends ActionBarActivity {
    private int totalProtein;
    private int totalFat;
    private int totalCarbohydrates;
    private int totalDailyCalories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_calories_and_macros);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        getMacronutrients();
        displayMacrosOnScreen();
        setDBTable();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_final_calories_and_macros, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getMacronutrients(){
        Intent intent = getIntent();
        totalDailyCalories = intent.getIntExtra("calories", 0);
        totalProtein = intent.getIntExtra("protein", 0);
        totalCarbohydrates = intent.getIntExtra("carbs", 0);
        totalFat = intent.getIntExtra("fat", 0);
    }

    private void displayMacrosOnScreen(){
        displayCalories();
        displayProtein();
        displayFat();
        displayCarbohydrates();
    }

    private void displayCalories(){
        TextView calories = (TextView)findViewById(R.id.calories_per_day);
        calories.setText(String.valueOf(totalDailyCalories));
    }

    private void displayProtein(){
        TextView protein = (TextView)findViewById(R.id.protein_per_day);
        protein.setText(String.valueOf(totalProtein));
    }

    private void displayFat(){
        TextView fat = (TextView)findViewById(R.id.fat_per_day);
        fat.setText(String.valueOf(totalFat));
    }

    private void displayCarbohydrates(){
        TextView carbohydrates = (TextView)findViewById(R.id.carbohydrates_per_day);
        carbohydrates.setText(String.valueOf(totalCarbohydrates));
    }

    //TODO: finish sqlite implementation
    private void setDBTable(){
        LiftingDB db = new LiftingDB(this.getApplicationContext());

        db.addMacros(new Macro(totalDailyCalories, totalProtein, totalFat, totalCarbohydrates));
    }
}
