package afrisone.liftingbuddy;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class SetGoalsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goals);
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

    private void setUpGoalSpinners(){
        setUpLoseWeightSpinner();
        setUpGainWeightSpinner();
    }

    private void setUpLoseWeightSpinner(){
        Spinner loseWeightSpinner = (Spinner) findViewById(R.id.lose_weight_spinner);
        String[] loseWeightGoals = new String[]{"15%", "20%", "25%"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, loseWeightGoals);
        loseWeightSpinner.setAdapter(spinnerAdapter);
    }

    private void setUpGainWeightSpinner(){
        Spinner gainWeightSpinner = (Spinner) findViewById(R.id.gain_weight_spinner);
        String[] gainWeightGoals = new String[]{"5%", "10%", "15%"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, gainWeightGoals);
        gainWeightSpinner.setAdapter(spinnerAdapter);
    }
}
