package jzkt3.umkc.edu.testtestetestsetestsetes;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment implements MyAdapter.ClickListener {

    private RecyclerView recyclerView;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    public static final String PREF_FILE_NAME="testpref";
    public static final String KEY_USER_LEARNED="user_learned_drawer";
    private View cView;
    private MyAdapter adapter;

    Context context;




    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(),KEY_USER_LEARNED,"true"));
        if(savedInstanceState != null){
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        adapter = new MyAdapter(getActivity(),getData());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return layout;
    }

    public static List<Information> getData(){
        List<Information> data = new ArrayList<>();
        int[] icons ={R.drawable.globe_1,R.drawable.money_exchange,R.drawable.check_book};
        String[] titles ={"All Exchange Rates","Convert","Conversion History"};

        for (int i=0;i<titles.length && i<icons.length;i++){
            Information current = new Information();
            current.itemId = icons[i];
            current.title = titles[i];
            data.add(current);
        }

        return data;
    }


    public void setUp(int fragmentID,DrawerLayout drawerLayout, final Toolbar toolbar){
        cView = getActivity().findViewById(fragmentID);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.drawer_open,
                R.string.drawer_close){

                @Override
                public void onDrawerOpened(View drawerView){
                    super.onDrawerOpened(drawerView);
                    if (mUserLearnedDrawer){
                        mUserLearnedDrawer = true;
                        savedToPreferences(getActivity(),KEY_USER_LEARNED,mUserLearnedDrawer+"");
                    }
                    getActivity().invalidateOptionsMenu();
                }
                @Override
                public void onDrawerClosed(View drawerView){
                    super.onDrawerClosed(drawerView);
                    getActivity().invalidateOptionsMenu();

                }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                if (slideOffset < 0.6) {
                    toolbar.setAlpha(1 - slideOffset);
                }
            }
        };

        if (!mUserLearnedDrawer && !mFromSavedInstanceState){
            mDrawerLayout.openDrawer(cView);
        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public static void savedToPreferences(Context context, String preferenceName,String preferenceValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName,preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context,String preferenceName,String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName,defaultValue);
    }

    @Override
    public void itemClicked(View view, int position) {
            if (position == 0){
                startActivity(new Intent(getActivity(),MainActivity.class));
            }
            if (position == 1){
                startActivity(new Intent(getActivity(),ConvertActivity.class));
            }
            if (position == 2){
                startActivity(new Intent(getActivity(),HistoryActivity.class));
            }
    }

    public interface NavigationDrawerCallbacks {
    }
}
