package afrisone.liftingbuddy;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;


public class SetGoalsActivity extends ActionBarActivity {
    double calorieChangeFactor;
    int totalDailyCalories;
    double TDEE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goals);

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

    //Method to set the total daily calories based on the user's goals
    private void determineCalorieGoals(){
        getUserGoal();
        totalDailyCalories = (int) (TDEE * calorieChangeFactor);
    }

    //Method to call the appropriate method based on the user's goal
    private void getUserGoal(){
        RadioGroup goalGroup = (RadioGroup)findViewById(R.id.goal_group);
        goalGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.lose_weight) {
                    loseWeight();
                } else if (checkedId == R.id.gain_weight) {
                    gainWeight();
                } else if (checkedId == R.id.custom_goals) {
                    customCalories();
                } else {
                    maintainWeight();
                }
            }});
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
    }

    //If the user's goal is to maintain weight, keep the calories the same as TDEE
    private void maintainWeight(){
        calorieChangeFactor = 1;
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
    }

    private void customCalories(){
        EditText customCalories = (EditText)findViewById(R.id.user_entered_calories);
        totalDailyCalories = Integer.parseInt(customCalories.getText().toString());
    }
}
