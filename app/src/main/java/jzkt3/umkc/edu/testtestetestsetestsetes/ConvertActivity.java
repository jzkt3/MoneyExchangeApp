package jzkt3.umkc.edu.testtestetestsetestsetes;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.List;


public class ConvertActivity extends ActionBarActivity {

    private static double USdollars;
    private static double conversion1;
    private static double finalResult;
    private static String displayedResult;
    EditText mEdit;
    TextView converted;
    private static String dollars;
    private static double getSpinnerItemRate;
    private static String getSpinnerItemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<Rate> rates = (MainActivity.listRates);



        final Spinner spinner =(Spinner) findViewById(R.id.ratesSpinner);
        ArrayAdapter adapter = new ArrayAdapter(ConvertActivity.this,android.R.layout.simple_spinner_item,rates);
        spinner.setAdapter(adapter);



        mEdit   = (EditText)findViewById(R.id.editDollars);

        Button convertButton = (Button) findViewById(R.id.convertButton);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dollars = mEdit.getText().toString();
                if(!dollars.isEmpty()) {

                    USdollars = Double.parseDouble(dollars);


                    Rate rate = (Rate) ((Spinner) findViewById(R.id.ratesSpinner)).getSelectedItem();
                    getSpinnerItemName = rate.getName();
                    getSpinnerItemRate = rate.getExchangeRate();

                    //conversion
                    conversion1 = USdollars * getSpinnerItemRate;

                    // Set double precision to 2 decimal places
                    Double toBeTruncated = new Double(conversion1);
                    Double truncatedDouble = new BigDecimal(toBeTruncated).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    finalResult = truncatedDouble;


                    displayedResult = Double.toString(truncatedDouble);
                    converted = (TextView) findViewById(R.id.convertedText);
                    converted.setText("$"+displayedResult);


                    // Items I need to store in database
                    // USdollars (dollar amount entered by user) - double
                    // getSpinnerItemName (Name of currency) - string
                    // getSpinnerItemRate (rate of currency) - double
                    // finalResult (converted result) - double
                }
                else {
                    Toast.makeText(ConvertActivity.this,"Please Enter Dollar Amount",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub, menu);
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

        if(id == android.R.id.home){
            //NavUtils.navigateUpFromSameTask(this);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
