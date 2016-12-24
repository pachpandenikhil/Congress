package com.webtech.homework.congress.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.webtech.homework.congress.R;
import com.webtech.homework.congress.fragments.ActiveBills;
import com.webtech.homework.congress.fragments.BillsFragment;
import com.webtech.homework.congress.fragments.CommitteesFragment;
import com.webtech.homework.congress.fragments.FavoriteBills;
import com.webtech.homework.congress.fragments.FavoriteCommittees;
import com.webtech.homework.congress.fragments.FavoriteLegislators;
import com.webtech.homework.congress.fragments.FavoritesFragment;
import com.webtech.homework.congress.fragments.HouseCommittees;
import com.webtech.homework.congress.fragments.HouseLegislators;
import com.webtech.homework.congress.fragments.JointCommittees;
import com.webtech.homework.congress.fragments.LegislatorsFragment;
import com.webtech.homework.congress.fragments.NewBills;
import com.webtech.homework.congress.fragments.SenateCommittees;
import com.webtech.homework.congress.fragments.SenateLegislators;
import com.webtech.homework.congress.fragments.StateLegislators;

public class MainActivity extends AppCompatActivity implements
        LegislatorsFragment.OnFragmentInteractionListener,
        BillsFragment.OnFragmentInteractionListener,
        CommitteesFragment.OnFragmentInteractionListener,
        FavoritesFragment.OnFragmentInteractionListener,
        StateLegislators.OnFragmentInteractionListener,
        HouseLegislators.OnFragmentInteractionListener,
        SenateLegislators.OnFragmentInteractionListener,
        ActiveBills.OnFragmentInteractionListener,
        NewBills.OnFragmentInteractionListener,
        HouseCommittees.OnFragmentInteractionListener,
        SenateCommittees.OnFragmentInteractionListener,
        JointCommittees.OnFragmentInteractionListener,
        FavoriteLegislators.OnFragmentInteractionListener,
        FavoriteBills.OnFragmentInteractionListener,
        FavoriteCommittees.OnFragmentInteractionListener {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    // Make sure to be using android.support.v7.app.ActionBarDrawerToggle version.
    // The android.support.v4.app.ActionBarDrawerToggle has been deprecated.
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Legislators");

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        drawerToggle = setupDrawerToggle();

        // Setup drawer view
        setupDrawerContent(nvDrawer);

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        //setting up initial content
        Fragment fragment = null;
        Class fragmentClass = LegislatorsFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.legislators_fragment:
                fragmentClass = LegislatorsFragment.class;
                break;
            case R.id.bills_fragment:
                fragmentClass = BillsFragment.class;
                break;
            case R.id.committees_fragment:
                fragmentClass = CommitteesFragment.class;
                break;
            case R.id.favorites_fragment:
                fragmentClass = FavoritesFragment.class;
                break;
            case R.id.about_me_activity:
                mDrawer.closeDrawers();
                Intent intent = new Intent(getApplicationContext(), AboutMeActivity.class);
                startActivity(intent);
                return;
            default:
                fragmentClass = LegislatorsFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        //menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE 1: Make sure to override the method with only a single `Bundle` argument
    // Note 2: Make sure you implement the correct `onPostCreate(Bundle savedInstanceState)` method.
    // There are 2 signatures and only `onPostCreate(Bundle state)` shows the hamburger icon.
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
