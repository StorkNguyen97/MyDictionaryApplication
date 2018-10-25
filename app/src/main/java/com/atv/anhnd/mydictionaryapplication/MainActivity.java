package com.atv.anhnd.mydictionaryapplication;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;

import static com.atv.anhnd.mydictionaryapplication.DataBaseHelper.TB_EN_VN;
import static com.atv.anhnd.mydictionaryapplication.DataBaseHelper.TB_VN_EN;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MenuItem menuSetting;
    Toolbar toolbar;

    DataBaseHelper dataBaseHelper;

    DictionaryFragment dictionaryFragment;
    BookmarkFragment bookmarkFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBaseHelper = new DataBaseHelper(this);

        findViewById();

        dictionaryFragment = new DictionaryFragment();
        bookmarkFragment = new BookmarkFragment();

        goToFragment(dictionaryFragment, true);

        dictionaryFragment.setOnFragmentListener(new FragmentListener() {
            @Override
            public void onItemClick(String value) {
                DetailFragment detailFragment = new DetailFragment();
                String id = Global.getState(MainActivity.this, "dic_type");
                String nghia = dataBaseHelper.getWord(value, id.equals("ev") ? TB_EN_VN : TB_VN_EN).nghia;
                Bundle bundle = new Bundle();
                bundle.putString("tu", value);
                bundle.putString("nghia", nghia);
                detailFragment.setArguments(bundle);
                goToFragment(detailFragment, false);
            }
        });

        bookmarkFragment.setOnFragmentListener(new FragmentListener() {
            @Override
            public void onItemClick(String value) {
                DetailFragment detailFragment = new DetailFragment();
                String nghia = dataBaseHelper.getWordFromBookmark(value).nghia;
                Bundle bundle = new Bundle();
                bundle.putString("tu", value);
                bundle.putString("nghia", nghia);
                detailFragment.setArguments(bundle);
                goToFragment(detailFragment, false);
            }
        });

        EditText edit_search = findViewById(R.id.edit_search);
        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dictionaryFragment.filterValue(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void findViewById() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menuSetting = menu.findItem(R.id.action_settings);
        String id = Global.getState(this, "dic_type");
        if (id != null && id.equals("ev"))
            menuSetting.setIcon(getResources().getDrawable(R.drawable.en_vn_2));
        else menuSetting.setIcon(getResources().getDrawable(R.drawable.vn_en_1));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_en_vn) {
            Global.saveState(this, "dic_type", "ev");
            dictionaryFragment.resetDataSource("ev");
            menuSetting.setIcon(getResources().getDrawable(R.drawable.en_vn_2));
            goToFragment(dictionaryFragment, false);
        } else if (item.getItemId() == R.id.action_vn_en) {
            Global.saveState(this, "dic_type", "ve");
            dictionaryFragment.resetDataSource("ve");
            menuSetting.setIcon(getResources().getDrawable(R.drawable.vn_en_1));
            goToFragment(dictionaryFragment, false);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bookmark) {
            goToFragment(bookmarkFragment, false);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void goToFragment(Fragment fragment, boolean isTop) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, fragment);
        if (!isTop)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        String activeFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container).getClass().getSimpleName();
//        if (!activeFragment.equals(BookmarkFragment.class.getSimpleName())){
//
//        }
//    }
}
