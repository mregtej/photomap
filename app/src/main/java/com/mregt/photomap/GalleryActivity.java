package com.mregt.photomap;

import android.content.Intent;
import android.os.Bundle;
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
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.gallery_tab_photos:
                    // TODO
                    return true;
                case R.id.gallery_tab_albums:
                    // Create Albums fragment
                    // AlbumsFragment albumsFragment = new AlbumsFragment();

                    // FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack so the user can navigate back
                    // transaction.replace(R.id.fr_gallery_container, albumsFragment);
                    // transaction.addToBackStack(null);

                    // Commit the transaction
                    // transaction.commit();
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
        getSupportActionBar().setTitle(R.string.activity_gallery_title);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fr_gallery_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            ImagesFragment photosFragment = new ImagesFragment();

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fr_gallery_container, photosFragment).commit();
        }

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
