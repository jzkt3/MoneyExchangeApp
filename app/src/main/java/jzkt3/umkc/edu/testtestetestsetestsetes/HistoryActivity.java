package jzkt3.umkc.edu.testtestetestsetestsetes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class HistoryActivity extends ActionBarActivity implements View.OnClickListener {

    DBAdapter helper;
    private static ListView listHistoryView;
    public static ArrayList<History> data;
    private FloatingActionButton actionButton;
    private ImageView sortNameIcon;
    private ImageView sortNameIcon2;
    private SubActionButton sortNameButton;
    private SubActionButton sortNameButton2;
    private FloatingActionMenu actionMenu;
    private static final String TAG_SORT_NAME = "sortName";
    private static final String TAG_SORT_NAME2 = "sortName2";
    public HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        helper = new DBAdapter(this);

        viewData();

        ImageView floatingButton = new ImageView(this);
        floatingButton.setImageResource(R.drawable.plus_button);
        actionButton = new FloatingActionButton.Builder(this).setBackgroundDrawable(R.drawable.selector_button)
                .setContentView(floatingButton)
                .build();

        setupActionMenu();

    }

    private void setupActionMenu() {

        sortNameIcon = new ImageView(this);
        sortNameIcon.setImageResource(R.drawable.a_z);
        sortNameIcon2 = new ImageView(this);
        sortNameIcon2.setImageResource(R.drawable.z_a);

        setupActionButton();

        actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(sortNameButton2)
                .addSubActionView(sortNameButton)
                .attachTo(actionButton)
                .build();

    }

    private void setupActionButton() {

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        sortNameButton = itemBuilder.setContentView(sortNameIcon).build();
        sortNameButton2 = itemBuilder.setContentView(sortNameIcon2).build();

        onclickAndTag();
    }

    private void onclickAndTag() {

        sortNameButton.setTag(TAG_SORT_NAME);
        sortNameButton2.setTag(TAG_SORT_NAME2);
        sortNameButton.setOnClickListener(this);
        sortNameButton2.setOnClickListener(this);

    }


    public void viewData(){

        data = helper.getData();
        listHistoryView = (ListView) findViewById(R.id.historylist);
        Log.d(getPackageName(), listHistoryView != null ? "THE LIST is not null!" : "THELIST is null!");
        adapter = new HistoryAdapter(getApplicationContext(),R.layout.history_layout,data);
        listHistoryView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
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
        if (id == R.id.deleteAll){

            new AlertDialog.Builder(this)
                    .setTitle("Delete All")
                    .setMessage("Are you sure?")
                    .setIcon(R.drawable.caution_icon)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            DBAdapter helper;
                            helper = new DBAdapter(getApplicationContext());
                            helper.deleteAll();
                            data.clear();
                            adapter.notifyDataSetChanged();
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
        }

        if(id == android.R.id.home){
            //NavUtils.navigateUpFromSameTask(this);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getTag().equals(TAG_SORT_NAME)) {
            Collections.sort(data, new Comparator<History>() {
                @Override
                public int compare(History lhs, History rhs) {
                    return lhs.getName().compareTo(rhs.getName());
                }
            });
            adapter.notifyDataSetChanged();
        }

        if (v.getTag().equals(TAG_SORT_NAME2)){
            Collections.sort(data, new Comparator<History>() {
                @Override
                public int compare(History lhs, History rhs) {
                    return rhs.getName().compareTo(lhs.getName());
                }
            });
            adapter.notifyDataSetChanged();
        }
    }
}
