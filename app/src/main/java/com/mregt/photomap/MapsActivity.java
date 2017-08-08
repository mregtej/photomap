package com.mregt.photomap;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * <p><h1>PhotoMap screen (Main Activity)</h1><br>
 * <p><b>Description</b>:<br>
 * <p><b>Main functionalities & features</b>:<br>
 *     + Displays Google Maps.<br>
 *     + App Navigation drawer implemented</p>
 * <p><b>Bugs</b>:<br>
 *     Not yet tested.</p>
 *
 * @author Modesto Rego
 */
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        NavigationView.OnNavigationItemSelectedListener{

    /** Class name (Logging) */
    private static final String TAG = MapsActivity.class.getName();

    /** GoogleMap instance  */
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set activity_maps ContentView (Layout inflate)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fr_map);

        // Obtain ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* FloatingActionButtons*/
        /* - Location button    */
        /* - Camera button      */
        // Set listener for location button. TODO Action shall be defined
        FloatingActionButton locationButton = (FloatingActionButton) findViewById(R.id.fb_location);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        // Set listener for camera button. TODO Action shall be defined
        FloatingActionButton cameraButton = (FloatingActionButton) findViewById(R.id.fb_camera);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Obtain DrawerLayout, inflate NavigationView and set NavigationItemSelectedListener
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Set MapListener (Async events)
        mapFragment.getMapAsync(this);

        Log.d(TAG,"onCreate() - finished.");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the ActionBar's menu
        getMenuInflater().inflate(R.menu.app_drawer_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks
        int id = item.getItemId();

        // Open Settings Screen
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MapsActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle gallery_bottom_navigation view item clicks here.
        switch(item.getItemId()) {
            case R.id.nav_gallery:
                Intent intent = new Intent(MapsActivity.this, GalleryActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_camera:
            case R.id.nav_slideshow:
            case R.id.nav_manage:
            case R.id.nav_share:
            case R.id.nav_send:
            default:
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * <p>Manipulates the map once available.<br>
     * This callback is triggered when the map is ready to be used.</p>
     *
     * <p>Current functionalities:<br>
     *     + Adds a marker near Sydney, Australia.
     * </p>
     *
     * <p>If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.</p>
     *
     * @param   googleMap   Google Map instance.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

}
