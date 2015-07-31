package afrisone.liftingbuddy;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.content.Intent;
import android.widget.TextView;


public class SetGoalsActivity extends ActionBarActivity {
    private double calorieChangeFactor;
    private int totalDailyCalories;
    private double TDEE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goals);

        setUpButtons();
        getTDEE();
        setUpGoalSpinners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_goals, menu);
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

    private void setUpButtons(){
        setUpSubmitCaloriesButton();
        setUpNextActivityButton();
    }

    private void setUpSubmitCaloriesButton(){
        Button submitForCalories = (Button)findViewById(R.id.submit_for_calories);
        submitForCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGoals();
            }
        });
    }

    private void setUpNextActivityButton(){
        Button goToMacros = (Button)findViewById(R.id.go_to_macros);
        goToMacros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMacroActivity();
            }
        });
    }

    private void goToMacroActivity(){
        Intent macroActivityIntent = new Intent(SetGoalsActivity.this, CustomizeMacrosActivity.class);
        macroActivityIntent.putExtra("totalCaloriesPerDay", totalDailyCalories);
        startActivity(macroActivityIntent);
    }

    //Gets the TDEE value from the Bundle sent from CalculatorActivity
    private void getTDEE(){
        Intent intent = getIntent();
        TDEE = intent.getDoubleExtra("caloriesTDEE", 0);
    }

    //Method to populate the lose and gain weight spinners
    private void setUpGoalSpinners(){
        setUpLoseWeightSpinner();
        setUpGainWeightSpinner();
    }

    //Method to populate the lose weight spinner
    private void setUpLoseWeightSpinner(){
        Spinner loseWeightSpinner = (Spinner) findViewById(R.id.lose_weight_spinner);
        String[] loseWeightGoals = new String[]{"15%", "20%", "25%"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, loseWeightGoals);
        loseWeightSpinner.setAdapter(spinnerAdapter);
    }

    //Method to populate the gain weight spinner
    private void setUpGainWeightSpinner(){
        Spinner gainWeightSpinner = (Spinner) findViewById(R.id.gain_weight_spinner);
        String[] gainWeightGoals = new String[]{"5%", "10%", "15%"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, gainWeightGoals);
        gainWeightSpinner.setAdapter(spinnerAdapter);
    }

    private void getGoals(){
        RadioButton loseWeight = (RadioButton)findViewById(R.id.lose_weight);
        RadioButton maintainWeight = (RadioButton)findViewById(R.id.maintain_weight);
        RadioButton gainWeight = (RadioButton)findViewById(R.id.gain_weight);

        if(loseWeight.isChecked()){
            loseWeight();
        }
        else if(maintainWeight.isChecked()){
            maintainWeight();
        }
        else if(gainWeight.isChecked()){
            gainWeight();
        }
        else{
            customCalories();
        }

        displayCaloriesOnScreen();
    }

    //If the user's goal is to lose weight, set the factor to divide by the appropriate amount of calories
    private void loseWeight() {
        Spinner weightLossSpinner = (Spinner) findViewById(R.id.lose_weight_spinner);
        String weightLossGoal = weightLossSpinner.getSelectedItem().toString();

        switch (weightLossGoal) {
            case "15%":
                calorieChangeFactor = 0.85;
                break;
            case "20%":
                calorieChangeFactor = 0.8;
                break;
            case "25%":
                calorieChangeFactor = 0.75;
                break;
        }

        changeTotalCalories();
    }

    //If the user's goal is to maintain weight, keep the calories the same as TDEE
    private void maintainWeight(){
        calorieChangeFactor = 1;
        changeTotalCalories();
    }

    //If the user's goal is to gain weight, set the factor to add the appropriate amount of calories
    private void gainWeight(){
        Spinner weightGainSpinner = (Spinner)findViewById(R.id.gain_weight_spinner);
        String weightGainGoal = weightGainSpinner.getSelectedItem().toString();

        switch(weightGainGoal){
            case "5%":
                calorieChangeFactor = 1.05;
                break;
            case "10%":
                calorieChangeFactor = 1.1;
                break;
            case "15%":
                calorieChangeFactor = 1.15;
                break;
        }
        changeTotalCalories();
    }

    private void customCalories(){
        EditText customCalories = (EditText)findViewById(R.id.user_entered_calories);
        totalDailyCalories = Integer.parseInt(customCalories.getText().toString());
    }

    private void changeTotalCalories(){
        totalDailyCalories = (int) (TDEE * calorieChangeFactor);
    }

    private void displayCaloriesOnScreen(){
        TextView displayCalories = (TextView)findViewById(R.id.display_calories);
        displayCalories.setText(String.valueOf(totalDailyCalories));
    }

}
