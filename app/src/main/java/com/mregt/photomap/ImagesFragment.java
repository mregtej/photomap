package com.mregt.photomap;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.mregt.photomap.config.PhotoMapGlobals;
import com.mregt.photomap.utils.ImageAdapter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mregt on 12/08/2017.
 */

public class ImagesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    /***************************  Global variables  ********************************/

    /** Activity Context */
    private Context mContext;

    /** GridView of images located in external storage */
    private GridView mImagesGridView;

    /** Full paths of images located in external storage */
    private ArrayList<String> mFileNames;

    /***************************     Constants      ********************************/

    private static final String IMAGE_PATH = "image-path";

    /** Request code for permission.READ_EXTERNAL_STORAGE */
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 100;

    /** Image loader ID */
    private static final int IMAGES_LOADER_ID = 200;

    /** Class name - Logging tag */
    private static final String TAG = ImagesFragment.class.getName();


    /******************************************************************************/
    /*                          Fragment (UI) methods                             */
    /******************************************************************************/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFileNames = new ArrayList<String>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mContext = container.getContext();

        // Load fragment layout and set ItemView OnClickListener
        mImagesGridView = (GridView) inflater.inflate(R.layout.photos_gallery_fragment,
                container, false);
        mImagesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(mContext, DetailedImageActivity.class);
                intent.putExtra(IMAGE_PATH, mFileNames.get(position));
                startActivity(intent);
            }
        });

        // TODO Move the DEMO version to the test implementation
        if (!PhotoMapGlobals.DEMO) {
            // Check if the app has READ_EXTERNAL_STORAGE permissions
            if(ContextCompat.checkSelfPermission(mContext,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Force the request
                requestReadExternalStoragePermissions();
            } else {
                // Prepare the loader
                getLoaderManager().initLoader(IMAGES_LOADER_ID, null, this);
            }
        } else {
            mImagesGridView.setAdapter(new ImageAdapter(mContext));
        }

        return mImagesGridView;
    }

    /******************************************************************************/
    /*                      Permission handlers/methods                           */
    /******************************************************************************/

    /**
     * Checks it the app has READ_EXTERNAL_STORAGE permission and, in case negative, requests
     * user to grant it.
     */
    @TargetApi(23)
    private void requestReadExternalStoragePermissions() {
        if (shouldShowRequestPermissionRationale(
                android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            // TODO Explain to the user why we need to read the contacts
        } else {
            // Requests explicitly
            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_READ_EXTERNAL_STORAGE);
        }
    }

    /**
     * Callback for the result from requesting permissions.
     * @param requestCode       Permission request code. Available permission-requests:
     *                          - REQUEST_READ_EXTERNAL_STORAGE
     * @param permissions       Requested permissions
     * @param grantResults      Grant result for the corresponding request
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch(requestCode) {
            case REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Init images-loader
                    getLoaderManager().initLoader(IMAGES_LOADER_ID, null, this);
                } else {
                    /* TODO DialogBox to inform user and close app */
                }
                return;
        }
    }

    /******************************************************************************/
    /*                      CursorLoader methods                                  */
    /******************************************************************************/

    /**
     * Instantiate and return a new Loader for the following IDs:
     * - IMAGES_LOADER_ID: Read all image-media-content from external storage
     * @param loaderId      Supported loader ID
     * @param args          Additional args given by caller
     * @return              Loader<Cursor> ready to start loading.
     */
    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        switch(loaderId) {
            case IMAGES_LOADER_ID:
                Uri baseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                return new CursorLoader(
                        mContext,
                        baseUri,
                        null,                                               // Projection
                        null,                                               // Selection
                        null,                                               // SelectionArgs
                        MediaStore.Images.Media.DEFAULT_SORT_ORDER          // SortOrder
                );
            default:
                throw new UnsupportedOperationException("Loader unknown: " + loaderId);
        }
    }

    /**
     * Process the data retrieved by the loader
     * @param loader        Loader that has finished
     * @param data          Data generated by the query (Cursor)
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        int i = 0;

        // Retrieve full-path of all images
        data.moveToFirst();
        while (!data.isAfterLast()) {
            mFileNames.add(i++,data.getString(data.getColumnIndex(MediaStore.Images.Media.DATA)));
            data.moveToNext();
        }
        // Fill in the GridView
        mImagesGridView.setAdapter(new ImageAdapter(mContext, mFileNames));
    }

    /**
     * Called when a previously created loader is being reset, and thus making its data unavailable
     * @param loader Loader to reset
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
