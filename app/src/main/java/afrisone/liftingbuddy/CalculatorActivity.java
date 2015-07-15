package afrisone.liftingbuddy;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;


public class CalculatorActivity extends ActionBarActivity {
    private String gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        Spinner activityLevel = (Spinner)findViewById(R.id.activity_level);
        String [] activityLevels = new String[]{"Sedentary", "Light activity", "Moderate activity", "Very active"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, activityLevels);
        activityLevel.setAdapter(spinnerAdapter);
    }

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

    //Method which determines if the female box is checked. If not, the gender will be "Male"
    private void isFemale(){
        if( ((RadioButton)findViewById(R.id.genderFemale)).isChecked()){
            gender = "Female";
        }
        else{
            gender = "Male";
        }
    }

    //Method which allows other classes to get the gender
    public String getGender() {
        isFemale();
        return gender;
    }
}
