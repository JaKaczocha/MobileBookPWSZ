package com.example.mobilecookbook;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
public class MainActivity extends AppCompatActivity {

    private String[] titles; // options in the navigation drawer
    private ListView drawerList;
    private DrawerLayout drawerLayout; // as a private method to make it available to many methods
    private ActionBarDrawerToggle drawerToggle; // -II-
    private int currentPosition = 0; // is used to handle the name in actionLabel so that it is valid after a change of perspective

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // specify the options to display in the navigation drawer list
        titles = getResources().getStringArray(R.array.titles);
        drawerList = (ListView) findViewById(R.id.drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);  // download references
        drawerList.setAdapter(new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_activated_1,titles));
        // add a new instance of the OnClickListener object
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        if(savedInstanceState == null) {
            selectItem(0);
        } else {//if the activity was closed and replayed, we go back to the saved state of the activity
            currentPosition = savedInstanceState.getInt("position");// to specify the title on the bar
            setActionBarTitle(currentPosition);
        }

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.open_drawer,R.string.close_drawer) {
            @Override// when it corresponds to complete closure
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
            @Override // when it corresponds to a complete opening
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();

            }
        };

        drawerLayout.setDrawerListener(drawerToggle); // only responsible for standby or open closed

        // to change the visibility of the selected button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);// enable the UP button to be used by ActionBarDrawerToggle
            getSupportActionBar().setHomeButtonEnabled(true);// -II-

            getSupportFragmentManager().addOnBackStackChangedListener(
                    new FragmentManager.OnBackStackChangedListener() {
                        @Override
                        public void onBackStackChanged() {
                            FragmentManager fragMan = getSupportFragmentManager();
                            Fragment fragment = fragMan.findFragmentByTag("visible_fragment");
                            if(fragment instanceof TopFragment) {
                                currentPosition = 0;
                            }
                            if(fragment instanceof PizzaFragment) {
                                currentPosition = 1;
                            }
                            if(fragment instanceof PastaFragment) {
                                currentPosition = 2;
                            }
                            if(fragment instanceof SoupFragment) {
                                currentPosition = 3;
                            }
                            setActionBarTitle(currentPosition);
                            drawerList.setItemChecked(currentPosition,true);
                        }
                    }
            );


    }
    @Override // display the action_create_order button in the activity bar
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }


    private class DrawerItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        Fragment fragment;
        currentPosition = position; // update value when navigation drawer item is selected
        switch(position) { // depending on the position, we open the selected fragment
            case 1:
                fragment =new PizzaFragment();
                break;
            case 2:
                fragment = new PastaFragment();
                break;
            case 3:
                fragment = new SoupFragment();
                break;
            default:
                fragment = new TopFragment();
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame,fragment,"visible_fragment");// 3rd current argument is responsible for adding a tag
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        setActionBarTitle(position); // pass the position of the clicked item // change the string into action parr

        // don't know if you need to fetch references

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); // get references to the drawer_layout
        drawerLayout.closeDrawer(drawerList);// closes drawers

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position",currentPosition);// if the activity is to be deleted, we save the state of the currentPosition variable
    }
    private void setActionBarTitle(int position) {
        String title;
        if(position == 0 ) {
            title = getResources().getString(R.string.app_name); // if he clicks home screen
        } else {
            title = titles[position];// get a string from the titles array based on the position of the clicked item
        }
        getSupportActionBar().setTitle(title);// changes the string in the title of the action bar

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //prepare the menu, if the action bar exists, we add items to it
        getMenuInflater().inflate(R.menu.menu_main,menu); // arg1 - resource file, arg2 - object representing the action bar
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) { // we synchronize the state of the button with the state of the ActionBarDrawerToggle
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) { // newConfig pass configuration changes to the drawerToggle button
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)) { // responsible for the reaction of the ActionBarDrawerToggle button to clicks
            return true;
        }
        switch( item.getItemId()) {
            case R.id.action_create_order:
                // code after clicking the place order button (we start the OrderActivity)
                Intent intent = new Intent(this,OrderActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                //code after executing setting item
                return true; // true tells the system that clicking on an action bar item has been handled
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}