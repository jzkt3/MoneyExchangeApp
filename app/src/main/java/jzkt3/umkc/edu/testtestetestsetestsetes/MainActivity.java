package jzkt3.umkc.edu.testtestetestsetestsetes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;


import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;

import static jzkt3.umkc.edu.testtestetestsetestsetes.Keys.EndpointExchangeRates.KEY_BASE;
import static jzkt3.umkc.edu.testtestetestsetestsetes.Keys.EndpointExchangeRates.KEY_DISCLAIMER;
import static jzkt3.umkc.edu.testtestetestsetestsetes.Keys.EndpointExchangeRates.KEY_LICENSE;
import static jzkt3.umkc.edu.testtestetestsetestsetes.Keys.EndpointExchangeRates.KEY_RATES;
import static jzkt3.umkc.edu.testtestetestsetestsetes.Keys.EndpointExchangeRates.KEY_TIMESTAMP;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private Toolbar toolbar;
    public static ArrayList<Rate> listRates = new ArrayList<Rate>();
    public static ArrayList<String> listFullNames = new ArrayList<>();
    private static VolleySingleton volleySingleton;
    private static RequestQueue requestQueue;
    public static final String URL_EXHANGE_RATES = "http://openexchangerates.org/api/latest.json";
    public static final String URL_FULLNAME = "http://openexchangerates.org/api/currencies.json";
    public static String savedText;
    public static String savedText2;
    private static ListView listRatesView;
    private static final String TAG_SORT_NAME = "sortName";
    private static final String TAG_SORT_RATE = "sortRate";
    private static final String TAG_SORT_NAME2 = "sortName2";
    private static final String TAG_SORT_RATE2 = "sortRate2";
    public RateAdapter adapter;
    public static Sorter sorter = null;
    private FloatingActionMenu actionMenu;
    private FloatingActionButton actionButton;

    public static String getRequestURL() {
        return URL_EXHANGE_RATES + "?app_id=" + MyApplication.API_KEY;
    }

    public static String getRequestURL2() {
        return URL_FULLNAME;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Exchange Rates");

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_drawer);
        drawerFragment.setUp(R.id.nav_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();

        sendJsonRequest();

        ImageView floatingButton = new ImageView(this);
        floatingButton.setImageResource(R.drawable.plus_button);

        actionButton = new FloatingActionButton.Builder(this).setBackgroundDrawable(R.drawable.selector_button)
                .setContentView(floatingButton)
                .build();



        ImageView sortNameIcon = new ImageView(this);
        sortNameIcon.setImageResource(R.drawable.a_z);
        ImageView sortNameIcon2 = new ImageView(this);
        sortNameIcon2.setImageResource(R.drawable.z_a);
        ImageView sortRateIcon = new ImageView(this);
        sortRateIcon.setImageResource(R.drawable.one_two);
        ImageView sortRateIcon2 = new ImageView(this);
        sortRateIcon2.setImageResource(R.drawable.two_one);

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        SubActionButton sortRateButton = itemBuilder.setContentView(sortRateIcon).build();
        SubActionButton sortNameButton = itemBuilder.setContentView(sortNameIcon).build();
        SubActionButton sortRateButton2 = itemBuilder.setContentView(sortRateIcon2).build();
        SubActionButton sortNameButton2 = itemBuilder.setContentView(sortNameIcon2).build();

        sortRateButton.setTag(TAG_SORT_RATE);
        sortNameButton.setTag(TAG_SORT_NAME);
        sortRateButton2.setTag(TAG_SORT_RATE2);
        sortNameButton2.setTag(TAG_SORT_NAME2);
        sortRateButton.setOnClickListener(this);
        sortNameButton.setOnClickListener(this);
        sortRateButton2.setOnClickListener(this);
        sortNameButton2.setOnClickListener(this);

        actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(sortRateButton2)
                .addSubActionView(sortRateButton)
                .addSubActionView(sortNameButton2)
                .addSubActionView(sortNameButton)
                .attachTo(actionButton)
                .build();

    }

    private void sendJsonRequest() {

        JSONObject placeholder = new JSONObject();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getRequestURL(), placeholder, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                listRates = parseJSONResponse(response);
                Assert.assertNotNull("The list of rates is empty", listRates);
                sendJsonRequest2();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }

    private void sendJsonRequest2() {

        JSONObject placeholder = new JSONObject();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getRequestURL2(), placeholder, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                listFullNames = parseJSONResponse2(response);

                for (int i = 0; i < listFullNames.size(); i++) {
                    listRates.get(i).setFullName(listFullNames.get(i));
                }

                listRatesView = (ListView) findViewById(R.id.datlist);
                Log.d(getPackageName(), listRatesView != null ? "THELIST is not null!" : "THELIST is null!");
                adapter = new RateAdapter(getApplicationContext(), R.layout.rates_layout, listRates);
                listRatesView.setAdapter(adapter);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }


    private ArrayList<Rate> parseJSONResponse(JSONObject response) {

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
                Date date = new Date();
                date.setTime((long) timeValue * 1000);
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


    private ArrayList<String> parseJSONResponse2(JSONObject response) {

        ArrayList<String> ListFullNames = new ArrayList<>();

        if (response != null && response.length() > 0) {
            try {

                Iterator<?> keys = response.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    String value = response.getString(key);

                    ListFullNames.add(value);

                }
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
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.navigate:
                Intent myIntent = new Intent(this, InfoActivity.class);
                startActivity(myIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if (v.getTag().equals(TAG_SORT_NAME)) {
            Collections.sort(listRates, new Comparator<Rate>() {
                @Override
                public int compare(Rate lhs, Rate rhs) {
                    return lhs.getFullName().compareTo(rhs.getFullName());
                }
            });
            adapter.notifyDataSetChanged();
        }

        if (v.getTag().equals(TAG_SORT_NAME2)){
            Collections.sort(listRates, new Comparator<Rate>() {
                @Override
                public int compare(Rate lhs, Rate rhs) {
                    return rhs.getFullName().compareTo(lhs.getFullName());
                }
            });
            adapter.notifyDataSetChanged();
        }

        if (v.getTag().equals(TAG_SORT_RATE)) {
            Collections.sort(listRates, new Comparator<Rate>() {
                @Override
                public int compare(Rate lhs, Rate rhs) {

                    return Double.compare(lhs.getExchangeRate(), rhs.getExchangeRate());
                }
            });
            adapter.notifyDataSetChanged();
        }

        if (v.getTag().equals(TAG_SORT_RATE2)){
            Collections.sort(listRates, new Comparator<Rate>() {
                @Override
                public int compare(Rate lhs, Rate rhs) {
                    return Double.compare(rhs.getExchangeRate(), lhs.getExchangeRate());
                }
            });
            adapter.notifyDataSetChanged();
        }

    }

    public void toggleTranslateFloatingActionButton(float slideOffset){
        if(actionMenu != null){
            if(actionMenu.isOpen()){
                actionMenu.close(true);
            }
            actionButton.setTranslationY(slideOffset * 300);
        }
    }

    public void onDrawerSlide(float slideOffset){
        toggleTranslateFloatingActionButton(slideOffset);
    }
}
