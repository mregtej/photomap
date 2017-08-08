package com.mregt.photomap;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * <p><h1>Gallery screen</h1><br>
 * <p><b>Description</b>:<br>
 * <p><b>Main functionalities & features</b>:<br>
 *     + Added Tab navigation menu (bottom)</p>
 * <p><b>Bugs</b>:<br>
 *     Not yet tested.</p>
 *
 * @author Modesto Rego
 */
public class GalleryActivity extends AppCompatActivity {

    /** Class name (Logging) */
    private static final String TAG = GalleryActivity.class.getName();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.gallery_tab_photos:
                    // TODO
                    return true;
                case R.id.gallery_tab_albums:
                    // TODO
                    return true;
                case R.id.gallery_tab_network:
                    // TODO
                    return true;
                case R.id.home:
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        // Inflate bottom navigation view
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bn_gallery_menu);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Display ActionBar & setTitle
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.gallery_tab_title_gallery);

        // TODO inflate Photos frame by default

        Log.d(TAG, "onCreate() - finished");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the ActionBar's menu
        getMenuInflater().inflate(R.menu.app_drawer_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here
        int id = item.getItemId();

        // Open Settings Screen
        if (id == R.id.action_settings) {
            Intent intent = new Intent(GalleryActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
