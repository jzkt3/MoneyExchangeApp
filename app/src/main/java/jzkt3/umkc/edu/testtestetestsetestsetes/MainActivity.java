package jzkt3.umkc.edu.testtestetestsetestsetes;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.JsonWriter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;


import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static jzkt3.umkc.edu.testtestetestsetestsetes.Keys.EndpointExchangeRates.KEY_BASE;
import static jzkt3.umkc.edu.testtestetestsetestsetes.Keys.EndpointExchangeRates.KEY_DISCLAIMER;
import static jzkt3.umkc.edu.testtestetestsetestsetes.Keys.EndpointExchangeRates.KEY_LICENSE;
import static jzkt3.umkc.edu.testtestetestsetestsetes.Keys.EndpointExchangeRates.KEY_RATES;
import static jzkt3.umkc.edu.testtestetestsetestsetes.Keys.EndpointExchangeRates.KEY_TIMESTAMP;


public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;


    public static ArrayList<Rate> listRates = new ArrayList<>();
    public static ArrayList<String> listFullNames = new ArrayList<>();



    private static VolleySingleton volleySingleton;
    private static RequestQueue requestQueue;
    public static final String URL_EXHANGE_RATES = "http://openexchangerates.org/api/latest.json";
    public static final String URL_FULLNAME = "http://openexchangerates.org/api/currencies.json";

    public static String savedText;
    public static String savedText2;

    private static ListView listRatesView;
    //private AdapterGlobalRates adapterGlobalRates;

    public static String getRequestURL(){
        return URL_EXHANGE_RATES+"?app_id="+ MyApplication.API_KEY;
    }
    public static String getRequestURL2(){
        return URL_FULLNAME;
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("All Exchange Rates");

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

             listRates = parseJSONResponse(response);

                sendJsonRequest2();

            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(getApplicationContext(), "ERROR" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(request);

    }

    private void sendJsonRequest2() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,getRequestURL2(),null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                listFullNames = parseJSONResponse2(response);

                for (int i = 0; i < listFullNames.size();i++){
                    listRates.get(i).setFullName(listFullNames.get(i));
                }


                listRatesView = (ListView) findViewById(R.id.datlist);
                Log.d(getPackageName(), listRatesView != null ? "THELIST is not null!" : "THELIST is null!");
                Adapter2 adapter = new Adapter2(getApplicationContext(),R.layout.rates_layout,listRates);
                listRatesView.setAdapter(adapter);

            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(getApplicationContext(), "ERROR" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(request);
    }


    private ArrayList<Rate> parseJSONResponse(JSONObject response){

        ArrayList<Rate> ListRates = new ArrayList<>();

        if (response != null && response.length() > 0) {
            try {

                String disclaimer = response.getString(KEY_DISCLAIMER);
                savedText = disclaimer;

                String license = response.getString(KEY_LICENSE);
                savedText2 = license;


                //Parse timestamp
                String timestamp = response.getString(KEY_TIMESTAMP);
                int timeValue = Integer.parseInt(timestamp);
                Date date = new Date ();
                date.setTime((long)timeValue*1000);
                TextView timestampView = (TextView) findViewById(R.id.timestampText);
                timestampView.setText(date.toString());



                String base = response.getString(KEY_BASE);

                JSONObject objectRates = response.getJSONObject(KEY_RATES);


                Iterator<?> keys = objectRates.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    String value = objectRates.getString(key);

                    Rate rate = new Rate();
                    rate.setName(key);
                    double doubleValue = Double.parseDouble(value);
                    rate.setExchangeRate(doubleValue);
                    ListRates.add(rate);

                }

            } catch (JSONException e) {
            }

        }
        return ListRates;
    }


    private ArrayList<String> parseJSONResponse2(JSONObject response){

        ArrayList<String> ListFullNames = new ArrayList<>();

        if (response != null && response.length() > 0) {
            try {


                Iterator<?> keys = response.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    String value = response.getString(key);



                    ListFullNames.add(value);

                }



                //for(int i = 0; i < ListFullNames.size(); i++) {
                 //   Toast.makeText(this, ListFullNames.get(i), Toast.LENGTH_SHORT)
                  //          .show();
                //}

            } catch (JSONException e) {
            }

        }
        return ListFullNames;
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
