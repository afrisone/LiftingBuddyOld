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
import android.widget.TextView;
import java.text.DecimalFormat;


public class CalculatorActivity extends ActionBarActivity {
    private String gender;
    private String units;
    private double height; //cm
    private double age;
    private double weight; //kg
    private double activityLevel; //1.2 sedentary, 1.375 lightly, 1.55 moderately, 1.725 very
    private double REE;
    private double TDEE;
    private double caloriesPerDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        //Spinner to set the activity level by the user
        Spinner activityLevel = (Spinner)findViewById(R.id.activity_level);
        String [] activityLevels = new String[]{"Sedentary", "Light activity", "Moderate activity", "Very active"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, activityLevels);
        activityLevel.setAdapter(spinnerAdapter);

        //Submit button after user enters age, height, weight, gender, and activity level
        Button calculatorSubmit = (Button)findViewById(R.id.button_calculate);
        calculatorSubmit.setOnClickListener(submitResults);
    }

    //Listener to change the view from activity_calculator ot calculator_after_submit
    View.OnClickListener submitResults = new View.OnClickListener(){
        public void onClick(View v){
            calculateTDEE();
            setContentView(R.layout.calculator_after_submit);

            //Buttons on the calculator_after_submit view
            Button caloriesSubmitButton = (Button)findViewById(R.id.submit_goals);
            caloriesSubmitButton.setOnClickListener(submitGoals);
            Button goToMacrosButton = (Button)findViewById(R.id.go_to_macros);
            goToMacrosButton.setOnClickListener(goToMacros);
        }
    };

    //Listener to display calories after the user submits his/her goals on the calculator_after_submit view
    View.OnClickListener submitGoals = new View.OnClickListener(){
        public void onClick(View v){
            //Determine user goals to output corrent calories
           if(((RadioButton)findViewById(R.id.lose_weight)).isChecked()){
                caloriesPerDay = TDEE * 0.8; //Cut 20%
            }
            else if(((RadioButton)findViewById(R.id.gain_weight)).isChecked()){
                caloriesPerDay = TDEE * 1.1; //Add 10%
            }
            else{
                caloriesPerDay = TDEE; //Same as TDEE
            }

            //Output calories
            TextView showCalories = (TextView)findViewById(R.id.display_calories);
            showCalories.setText(String.valueOf(new DecimalFormat("#").format(caloriesPerDay)));
        }
    };

    //Listener to change the view from calculator_after_submit to macro_customization
    View.OnClickListener goToMacros = new View.OnClickListener(){
        public void onClick(View v){
            setContentView(R.layout.macro_customization);

            Button submitMacroButton = (Button)findViewById(R.id.submit_macros);
            submitMacroButton.setOnClickListener(finalResults);
        }
    };

    //Listener to change the view from macro_customization to
    View.OnClickListener finalResults = new View.OnClickListener(){
        public void onClick(View v){
            setContentView(R.layout.final_results_from_calculator);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calculator, menu);
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

    //Method to determine if metric is selected. Imperial units by default
    private void getUnits(){
        //TODO: if metric, change height and weight units to cm and kg
        if(((RadioButton)findViewById(R.id.metric_units)).isChecked()){
            units = "Metric";
        }
        else{
            units = "Imperial";
        }
    }

    //Method to set the member variable gender based on user input
    private void getGender(){
        if( ((RadioButton)findViewById(R.id.genderFemale)).isChecked()){
            gender = "Female";
        }
        else{
            gender = "Male";
        }
    }

    //Method to get the user input for the height
    private void getHeight(){
        EditText mEdit;
        if(units.equals("Imperial")) {
            double feet;
            mEdit = (EditText) findViewById(R.id.height_in_feet);
            feet = Double.parseDouble(mEdit.getText().toString());

            double inches;
            mEdit = (EditText) findViewById(R.id.height_in_inches);
            inches = Double.parseDouble(mEdit.getText().toString());

            inches += (feet * 12);

            height = inches * 2.54;
        }
        else{
            //TODO add height_in_cm to layout and add metric capabilities
        }
    }

    //Method to get user input for age
    private void getAge(){
        EditText mEdit;
        mEdit = (EditText) findViewById(R.id.age_in_years);
        age = Double.parseDouble(mEdit.getText().toString());
    }

    //Method to get user input for weight
    private void getWeight(){
        EditText mEdit;
        if(units.equals("Imperial")) {
            mEdit = (EditText) findViewById(R.id.weight_in_pounds);
            double pounds = Double.parseDouble(mEdit.getText().toString());
            weight = pounds * 0.453592;
        }
        else{
            //TODO add height_in_cm to layout and add metric capabilities
        }
    }

    //Method to get user input for the activity level
    private void getActivityLevel(){
        Spinner activity = (Spinner)findViewById(R.id.activity_level);
        String levelToString = activity.getSelectedItem().toString();

        switch(levelToString){
            case "Sedentary":
                activityLevel = 1.2;
                break;
            case "Light activity":
                activityLevel = 1.375;
                break;
            case "Moderate activity":
                activityLevel = 1.55;
                break;
            case "Very active":
                activityLevel = 1.725;
                break;
        }
    }

    //Method to call all of the getter methods
    private void setValues(){
        getUnits();
        getGender();
        getHeight();
        getAge();
        getWeight();
        getActivityLevel();
    }

    //Method to calculate the Resting Energy Expenditure
    private void calculateREE(){
        setValues();

        if (gender.equals("Female")){
            REE = (10 * weight) + (6.25 * height) - (5 * age) - 161;
        }
        else{
            REE = (10 * weight) + (6.25 * height) - (5 * age) + 5;
        }
    }

    //Method to calculate Total Daily Energy Expenditure (Calories to maintain weight)
    private void calculateTDEE(){
        calculateREE();
        TDEE = REE * activityLevel;
    }
}
