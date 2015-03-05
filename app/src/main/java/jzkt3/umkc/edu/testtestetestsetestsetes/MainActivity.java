package jzkt3.umkc.edu.testtestetestsetestsetes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.JsonWriter;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Iterator;

import static jzkt3.umkc.edu.testtestetestsetestsetes.Keys.EndpointExchangeRates.*;


public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;

    private ArrayList<Rate> listRates = new ArrayList<>();


    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    public static final String URL_EXHANGE_RATES = "http://openexchangerates.org/api/latest.json";

    public static String getRequestURL(){
        return URL_EXHANGE_RATES+"?app_id="+ MyApplication.API_KEY;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_appbar);
        setTitle("Global Exchange Rates");

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_drawer);
        drawerFragment.setUp(R.id.nav_drawer,(DrawerLayout)findViewById(R.id.drawer_layout),toolbar);



        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();


        sendJsonRequest();



    }

    private void sendJsonRequest() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,getRequestURL(),null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                parseJSONResponse(response);

            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(getApplicationContext(),"ERROR"+error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(request);

    }


    private void parseJSONResponse(JSONObject response){

        if (response == null || response.length() == 0) {
            return;
        }
        try {
            if(response.has(KEY_DISCLAIMER)) {
                String disclaimer = response.getString(KEY_DISCLAIMER);
                String license = response.getString(KEY_LICENSE);
                String timestamp = response.getString(KEY_TIMESTAMP);
                String base = response.getString(KEY_BASE);

                JSONObject objectRates = response.getJSONObject(KEY_RATES);

                String temporaryAbbreviation = "AED";
                String rateValue = objectRates.getString("AED");

                double doubleValue = Double.parseDouble(rateValue);

                //Toast.makeText(getApplicationContext(),rateSearch,Toast.LENGTH_SHORT).show();

                Rate rate = new Rate();
                rate.setName(temporaryAbbreviation);
                rate.setExchangeRate(doubleValue);

                listRates.add(rate);


                StringBuilder builder = new StringBuilder();
                for (Rate i : listRates){
                    builder.append("" + i + "");
                }
                Toast.makeText(getApplicationContext(),builder,Toast.LENGTH_LONG).show();


            }
        } catch (JSONException e){


        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()){
            case R.id.action_settings:
                Toast.makeText(this,item.getTitle(),Toast.LENGTH_SHORT).show();
                return true;
            case R.id.navigate:
                Intent myIntent = new Intent(this,InfoActivity.class);
                startActivity(myIntent);

                Toast.makeText(this,item.getTitle(),Toast.LENGTH_SHORT).show();
                return true;
        }
           return super.onOptionsItemSelected(item);
    }
}
