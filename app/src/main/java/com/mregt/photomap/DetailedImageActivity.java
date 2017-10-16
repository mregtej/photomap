package com.mregt.photomap;

import android.annotation.TargetApi;
import android.media.ExifInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mregt.photomap.config.PhotoMapGlobals;

import java.io.IOException;

public class DetailedImageActivity extends AppCompatActivity {

    /***************************  Global variables  ********************************/

    /** Image file (bmp) */
    private ImageView mImageView;
    /** Image-filename */
    private TextView mFileNameTextView;
    /** Image-filepath */
    private TextView mFilePathTextView;
    /** Captured time of the image */
    private TextView mCapturedTimeTextView;
    /** Geolocation of the image */
    private TextView mGeolocationTextView;
    /** Image orientation */
    private TextView mOrientationTextView;
    /** Camera maker */
    private TextView mCameraMakerTextView;
    /** Camera model */
    private TextView mCameraModelTextView;

    /***************************     Constants      ********************************/

    /** Extra Intent ID for image-path */
    private static final String IMAGE_PATH = "image-path";
    /** Extra Intent ID for drawable resource (DEMO) */
    private static final String IMAGE_RES_ID = "image-res-id";

    /** Class name - Logging tag */
    private static final String TAG = DetailedImageActivity.class.getName();

    /******************************************************************************/
    /*                          Activity (UI) methods                             */
    /******************************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Inflate layout and identify views
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_image);
        mImageView = (ImageView) findViewById(R.id.iv_image);
        mFileNameTextView = (TextView) findViewById(R.id.tv_image_filename_value);
        mFilePathTextView = (TextView) findViewById(R.id.tv_image_filepath_value);
        mCapturedTimeTextView = (TextView) findViewById(R.id.tv_image_captured_time_value);
        mGeolocationTextView = (TextView) findViewById(R.id.tv_image_geolocation_value);
        mOrientationTextView = (TextView) findViewById(R.id.tv_image_orientation_value);
        mCameraMakerTextView = (TextView) findViewById(R.id.tv_image_camera_maker_value);
        mCameraModelTextView = (TextView) findViewById(R.id.tv_image_camera_model_value);

        // Retrieve image resource from Intent
        // Absolute path vs internal drawable resource (DEMO)
        Bundle extras = getIntent().getExtras();
        if(!PhotoMapGlobals.DEMO) {
            String imagePath = extras.getString(IMAGE_PATH);
            displayImageAndContent(imagePath);
        } else {
            int imageResId = extras.getInt(IMAGE_RES_ID);
            displayImageAndContent(imageResId);
        }

    }


    /******************************************************************************/
    /*                            Private methods                                 */
    /******************************************************************************/

    /**
     * Display image on the screen and retrieve & display some EXIF info of the image
     * @param path  Image-path
     */
    private void displayImageAndContent(String path) {

        ExifInterface exifData;
        float[] LatLong = new float[]{0, 0};

        // Find image-resource and retrieve EXIF data
        try {
            exifData = new ExifInterface(path);
        } catch (IOException e) {
            Log.e(TAG,"Image not found: " + path);
            return;
        }

        // Set image bitmap
        Glide.with(this).load(path).into(mImageView);

        /**********************/
        /* Display EXIF data **/
        /**********************/

        /* Image- path */
        mFilePathTextView.setText(path);

        /* Image- name */
        String filename = path.substring(path.lastIndexOf("/")+1);
        mFileNameTextView.setText(filename);

        /* Image captured time*/
        /* TODO Locale Date/Time format */
        mCapturedTimeTextView.setText(exifData.getAttribute(ExifInterface.TAG_DATETIME));

        /* Geo location */
        /* TODO GoogleApi directions to retrieve an address bz LatLong */
        if(exifData.getLatLong(LatLong)) {
            mGeolocationTextView.setText(String.format("%.2f, %.2f",LatLong[0],LatLong[1]));
        } else {
            mGeolocationTextView.setText(getString(R.string.unknown));
        }

        /* TODO Image orientation */
        /*
        switch(exifData.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                mOrientationTextView.setText(getString(R.string.detailed_image_horizontal_orientation));
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                mOrientationTextView.setText(getString(R.string.detailed_image_vertical_orientation));
                break;
            default:
                mOrientationTextView.setText(getString(R.string.unknown));
                break;
        }
        */

        /* Camera maker and model */
        mCameraMakerTextView.setText(exifData.getAttribute(ExifInterface.TAG_MAKE));
        mCameraModelTextView.setText(exifData.getAttribute(ExifInterface.TAG_MODEL));
    }

    @TargetApi(21)
    private void displayImageAndContent(int res) {
        mImageView.setImageResource(res);
    }
}
