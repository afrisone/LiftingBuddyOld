package afrisone.liftingbuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class CustomizeMacrosActivity extends Activity {
    private int totalDailyCalories;
    private int totalProtein;
    private int totalFat;
    private int totalCarbohydrates;
    private boolean customFatIsFilledIn = true;
    private boolean customProteinIsFilledIn = true;
    private double weightInPounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_macros);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        getWeightAndCalories();
        setRadioGroupListeners();
        setUpFinalResultsButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_customize_macros, menu);
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

    private void setRadioGroupListeners(){
        setProteinListener();
        setFatListener();
    }

    private void setProteinListener(){
        RadioGroup proteinGroup = (RadioGroup)findViewById(R.id.protein_group);

        proteinGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                clearCustomErrorMessages();
            }
        });
    }

    private void setFatListener(){
        RadioGroup fatGroup = (RadioGroup)findViewById(R.id.fat_group);

        fatGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId){
                clearCustomErrorMessages();
            }
        });
    }

    private void setUpFinalResultsButton(){
        Button finalresultsButton = (Button)findViewById(R.id.final_results_button);
        finalresultsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                getMacros();
                if (customFatIsFilledIn && customProteinIsFilledIn) {
                    goToFinalCaloriesAndMacrosActivity();
                }
            }
        });
    }

    private void goToFinalCaloriesAndMacrosActivity(){
        Intent finalCaloriesAndMacros = new Intent(CustomizeMacrosActivity.this, FinalCaloriesAndMacrosActivity.class);
        finalCaloriesAndMacros.putExtra("calories", totalDailyCalories);
        finalCaloriesAndMacros.putExtra("protein", totalProtein);
        finalCaloriesAndMacros.putExtra("carbs", totalCarbohydrates);
        finalCaloriesAndMacros.putExtra("fat", totalFat);
        startActivity(finalCaloriesAndMacros);
    }

    private void getWeightAndCalories(){
        getTotalDailyCalories();
        getWeightInPounds();
    }

    private void getTotalDailyCalories(){
        Intent intent = getIntent();
        totalDailyCalories = intent.getIntExtra("totalCaloriesPerDay", 0);
    }

    private void getWeightInPounds(){
        final double KILOGRAMS_TO_POUNDS_CONVERSION = 2.20462;
        Intent intent = getIntent();
        weightInPounds = intent.getDoubleExtra("weightInKilograms", 0) * KILOGRAMS_TO_POUNDS_CONVERSION;
    }

    private void getMacros(){
        getProtein();
        getFat();
        calculateCarbohydrates();
    }

    private void getProtein(){
        RadioButton protein07 = (RadioButton)findViewById(R.id.protein_07);
        RadioButton protein08 = (RadioButton)findViewById(R.id.protein_08);
        RadioButton protein09 = (RadioButton)findViewById(R.id.protein_09);
        RadioButton proteinCustom = (RadioButton)findViewById(R.id.protein_custom);
        customProteinIsFilledIn = true;

        if(protein07.isChecked()){
            totalProtein = (int)(weightInPounds *0.70);
        }
        else if(protein08.isChecked()){
            totalProtein = (int)(weightInPounds *0.80);
        }
        else if(protein09.isChecked()){
            totalProtein = (int)(weightInPounds *0.90);
        }
        else if(proteinCustom.isChecked()){
            getCustomProtein();
        }
    }

    private void getCustomProtein(){
        EditText customProtein = (EditText)findViewById(R.id.user_entered_protein);
        if(!checkMissingFieldError(customProtein)){
            customProteinIsFilledIn = false;
        }
        else {
            double proteinPerPound = Double.parseDouble(customProtein.getText().toString());
            totalProtein = (int) (weightInPounds * proteinPerPound);
        }
    }

    private void getFat(){
        RadioButton fat35 = (RadioButton)findViewById(R.id.fat_35);
        RadioButton fat40 = (RadioButton)findViewById(R.id.fat_40);
        RadioButton fat45 = (RadioButton)findViewById(R.id.fat_45);
        RadioButton fatCustom = (RadioButton)findViewById(R.id.fat_custom);
        customFatIsFilledIn = true;

        if(fat35.isChecked()){
            totalFat = (int)(weightInPounds * 0.35);
        }
        else if(fat40.isChecked()){
            totalFat = (int)(weightInPounds * 0.40);
        }
        else if(fat45.isChecked()){
            totalFat = (int)(weightInPounds * 0.45);
        }
        else if(fatCustom.isChecked()){
            getCustomFat();
        }
    }

    private void getCustomFat(){
        EditText customFat = (EditText)findViewById(R.id.user_entered_fat);
        if(!checkMissingFieldError(customFat)){
            customFatIsFilledIn = false;
        }
        else {
            double fatPerPound = Double.parseDouble(customFat.getText().toString());
            totalFat = (int) (weightInPounds * fatPerPound);
        }
    }

    private void calculateCarbohydrates(){
        int proteinCalories = totalProtein * 4;
        int fatCalories = totalFat * 9;
        totalCarbohydrates = (totalDailyCalories - proteinCalories - fatCalories) / 4;
    }

    private boolean checkMissingFieldError(EditText textField){
        if (textField.getText().toString().equals("")) {
            textField.setError("You must enter a valid value.");
            return false;
        }
        else{
            return true;
        }
    }

    private void clearCustomErrorMessages(){
        RadioButton customProtein = (RadioButton)findViewById(R.id.protein_custom);
        RadioButton customFat = (RadioButton)findViewById(R.id.fat_custom);

        if(!customProtein.isChecked()){
            TextView userEnteredProtein = (TextView)findViewById(R.id.user_entered_protein);
            userEnteredProtein.setError(null);
        }
        if(!customFat.isChecked()){
            TextView userEnteredFat = (TextView)findViewById(R.id.user_entered_fat);
            userEnteredFat.setError(null);
        }
    }
}
