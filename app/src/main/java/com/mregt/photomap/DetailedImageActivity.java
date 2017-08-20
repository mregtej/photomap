package com.mregt.photomap;

import android.media.ExifInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;

/**
 * TODO Better an AlertDialog?
 */
public class DetailedImageActivity extends AppCompatActivity {

    /***************************  Global variables  ********************************/

    /** Image file (bmp) */
    private ImageView mImageView;
    /** Image-filename */
    private TextView mNameTextView;
    /** Date of image */
    private TextView mDateTextView;

    /***************************     Constants      ********************************/

    /** Extra Intent ID for image-path */
    private static final String IMAGE_PATH = "image-path";

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
        mNameTextView = (TextView) findViewById(R.id.tv_image_name_value);
        mDateTextView = (TextView) findViewById(R.id.tv_image_date_value);

        // Retrieve image path from Intent
        Bundle extras = getIntent().getExtras();
        String imagePath = extras.getString(IMAGE_PATH);

        // Fill in content
        displayImageAndContent(imagePath);
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

        // Display EXIF data
        String filename = path.substring(path.lastIndexOf("/")+1);
        mNameTextView.setText(filename);
        mDateTextView.setText(exifData.getAttribute(ExifInterface.TAG_DATETIME));
        if(exifData.getLatLong(LatLong)) {
            // TODO Continue...
        }
    }
}
