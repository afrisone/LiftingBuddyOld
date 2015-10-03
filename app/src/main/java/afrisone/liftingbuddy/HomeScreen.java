package afrisone.liftingbuddy;


import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class HomeScreen extends Activity {
    int calories;
    int protein;
    int fat;
    int carbohydrates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        setUpCalculatorButton();
        setUpMacrosOnScreen();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
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

    private void setUpCalculatorButton(){
        Button calculatorButton = (Button)findViewById(R.id.go_to_calculator);
        calculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, CalculatorActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpMacrosOnScreen(){
        checkDatabaseForMacros();
        addPreviousMacrosToScreen();
    }

    private void addPreviousMacrosToScreen(){
        TextView text = (TextView)findViewById(R.id.db_calories);
        text.setText(String.valueOf(calories));

        text = (TextView)findViewById(R.id.db_protein);
        text.setText(String.valueOf(protein));

        text = (TextView)findViewById(R.id.db_fat);
        text.setText(String.valueOf(fat));

        text = (TextView)findViewById(R.id.db_carbohydrates);
        text.setText(String.valueOf(carbohydrates));
    }

    public void checkDatabaseForMacros(){
        try{
            getMacrosFromPreviousDatabase();
        }
        catch(Exception e){
            noDatabaseExists();
        }
    }

    private void getMacrosFromPreviousDatabase(){
        LiftingDB db = new LiftingDB(this.getApplicationContext());
        Macro previousMacros;
        previousMacros = db.getMacros(1);

        calories = previousMacros.getCalories();
        protein = previousMacros.getProtein();
        fat = previousMacros.getFat();
        carbohydrates = previousMacros.getCarbohydrates();
    }

    private void noDatabaseExists(){
        Macro dbMacros = new Macro();
        createDatabase(dbMacros);

        calories = 0;
        protein = 0;
        fat = 0;
        carbohydrates = 0;
    }

    private void createDatabase(Macro dbMacros){
        LiftingDB db = new LiftingDB(this.getApplicationContext());
        db.createMacrosTable(dbMacros);
    }

}
