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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;

//TODO: Add error handling for all user input fields to add a default value.

public class CalculatorActivity extends ActionBarActivity {
    private String gender;
    private String units;
    private double height; //cm
    private double age;
    private double weight; //kg
    private double activityLevel;
    private double REE;
    private double TDEE;
    private static final double POUNDS_TO_KG_CONVERSION = 0.453592;
    private static final double INCHES_TO_CM_CONVERSION = 2.54;
    private static final int FEET_TO_INCHES_CONVERSION = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        populateActivitySpinner();
        setUnitsUp();
        setUpSubmitButton();
    }

    //@Override
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

    //Change the weight and height from imperial to metric units using the radio buttons
    private void setUnitsUp(){
        RadioGroup unitChange = (RadioGroup) findViewById(R.id.unit_radio_group);
        unitChange.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.metric_units) {
                    metricUnits();
                } else {
                    imperialUnits();
                }
            }
        });
    }

    //Set weight and height fields to metric units
    private void metricUnits(){
        TextView height = (TextView) findViewById(R.id.height);
        TextView weight = (TextView) findViewById(R.id.weight);
        EditText hideInchesField = (EditText) findViewById(R.id.height_in_inches);

        hideInchesField.setVisibility(View.GONE);
        height.setText("Height (cm)");
        weight.setText("Weight (kg)");
    }

    //Set weight and height fields to imperial units
    private void imperialUnits(){
        TextView height = (TextView) findViewById(R.id.height);
        TextView weight = (TextView) findViewById(R.id.weight);
        EditText hideInchesField = (EditText) findViewById(R.id.height_in_inches);

        hideInchesField.setVisibility(View.VISIBLE);
        height.setText("Height (ft/in)");
        weight.setText("Weight (lbs)");
    }

    //Method to set up the submit button to calculate the TDEE and start the next activity
    private void setUpSubmitButton(){
        Button calculatorSubmit = (Button) findViewById(R.id.button_calculate);
        calculatorSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                calculateTDEE();
                Intent goalsActivityIntent = new Intent(CalculatorActivity.this, SetGoalsActivity.class);
                goalsActivityIntent.putExtra("caloriesTDEE", TDEE);
                startActivity(goalsActivityIntent);
            }
        });
    }

    //Method to populate the spinner to set the activity level
    private void populateActivitySpinner(){
        Spinner activityLevel = (Spinner) findViewById(R.id.activity_level);
        String[] activityLevels = new String[]{"Sedentary", "Light activity", "Moderate activity", "Very active"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, activityLevels);
        activityLevel.setAdapter(spinnerAdapter);
    }

    //Method to calculate Total Daily Energy Expenditure (Calories to maintain weight)
    private void calculateTDEE() {
        calculateREE();
        TDEE = REE * activityLevel;
    }

    //Method to calculate the Resting Energy Expenditure
    private void calculateREE() {
        setValues();

        if (gender.equals("Female")) {
            REE = (10 * weight) + (6.25 * height) - (5 * age) - 161;
        } else {
            REE = (10 * weight) + (6.25 * height) - (5 * age) + 5;
        }
    }

    //Method to call all of the getter methods
    private void setValues() {
        getUnits();
        getGender();
        getHeight();
        getAge();
        getWeight();
        getActivityLevel();
    }

    //Method to determine if metric is selected. Imperial units by default
    private void getUnits() {
        if (((RadioButton) findViewById(R.id.metric_units)).isChecked()) {
            units = "Metric";
        } else {
            units = "Imperial";
        }
    }

    //Method to set the member variable gender based on user input
    private void getGender() {
        if (((RadioButton) findViewById(R.id.genderFemale)).isChecked()) {
            gender = "Female";
        } else {
            gender = "Male";
        }
    }

    //Method to get the user input for the height
    private void getHeight() {
        EditText mEdit;
        mEdit = (EditText) findViewById(R.id.height_input);
        if (units.equals("Imperial")) {
            double feet = Double.parseDouble(mEdit.getText().toString());

            double inches;
            mEdit = (EditText) findViewById(R.id.height_in_inches);
            inches = Double.parseDouble(mEdit.getText().toString());

            inches += (feet * FEET_TO_INCHES_CONVERSION);
            height = inches * INCHES_TO_CM_CONVERSION;
        }
        else {
            height = Double.parseDouble(mEdit.getText().toString());
        }
    }

    //Method to get user input for weight
    private void getWeight() {
        EditText mEdit;
        mEdit = (EditText) findViewById(R.id.weight_input);
        if (units.equals("Imperial")) {
            double pounds = Double.parseDouble(mEdit.getText().toString());
            weight = pounds * POUNDS_TO_KG_CONVERSION;
        } else {
            weight = Double.parseDouble(mEdit.getText().toString());

        }
    }
    //Method to get user input for age
    private void getAge() {
        EditText mEdit;
        mEdit = (EditText) findViewById(R.id.age_in_years);
        age = Double.parseDouble(mEdit.getText().toString());
    }

    //Method to get user input for the activity level
    private void getActivityLevel() {
        Spinner activity = (Spinner) findViewById(R.id.activity_level);
        String levelToString = activity.getSelectedItem().toString();

        switch (levelToString) {
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
}
