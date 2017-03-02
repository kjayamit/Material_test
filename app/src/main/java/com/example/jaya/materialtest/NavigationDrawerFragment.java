package com.example.jaya.materialtest;


import android.content.Context;
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
public class NavigationDrawerFragment extends Fragment {

    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    private View containerView;

    private RecyclerView recyclerView;

    private VizAdapter adapter;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,"false"));
        if(savedInstanceState!=null){
            mFromSavedInstanceState=true;
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);

        adapter = new VizAdapter(getActivity(),getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    public static List<Information> getData(){
        List<Information> data = new ArrayList<>();
        int[] icons={ R.drawable.checkbox24, R.drawable.mancycling,R.drawable.filterresults};
        String[] title = {"one","two","three"};
        for(int i=0;i<title.length;i++){
            Information current = new Information();
            current.iconID = icons[i];
            current.title = title[i];
            data.add(current);
        }
        return data;
    }

    public void setUp(int fragmentID, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentID);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),mDrawerLayout,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer=true;
                    saveToPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,mUserLearnedDrawer+"");
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
                if(slideOffset < 0.6){
                    toolbar.setAlpha(1 - slideOffset);
                }
            }
        };

        if(!mUserLearnedDrawer && !mFromSavedInstanceState){
            mDrawerLayout.openDrawer(containerView);
        }

//        ----- Deprecated -----
//        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName,preferenceValue);
        editor.apply();

    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName,defaultValue);
    }
}
