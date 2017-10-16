package com.mregt.photomap.utils;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mregt.photomap.R;
import com.mregt.photomap.config.PhotoMapGlobals;

import java.util.ArrayList;

/**
 * Created by mregt on 12/08/2017.
 */

public class ImageAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<String> mFileNames;

    private int mScreenHeight;
    private int mScreenWidth;

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_0, R.drawable.sample_1,
    };

    public ImageAdapter(Context context) {
        mContext = context;
        mScreenHeight = getScreenHeight();
        mScreenWidth = getScreenWidth();
    }

    public ImageAdapter(Context context, ArrayList<String> fileNames) {
        mContext = context;
        mFileNames = fileNames;
        mScreenHeight = getScreenHeight();
        mScreenWidth = getScreenWidth();
    }

    @Override
    public int getCount() {
        if(PhotoMapGlobals.DEMO)
            return mThumbIds.length;
        else
            return mFileNames.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    public int getmThumbId(int pos) {
        return (int) mThumbIds[pos];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;

        if (view == null) {
            // Set GridView LayoutParams (number of images in a row depending of
            // the phone orientation: portrait / landscape
            imageView = new ImageView(mContext);
            switch(mContext.getResources().getConfiguration().orientation) {
                case 2: // Landscape Mode
                    imageView.setLayoutParams(new GridView.LayoutParams(
                            mScreenWidth / PhotoMapGlobals.PHOTO_GRID_NUM_HORIZONTAL_COLUMNS,
                            mScreenWidth / PhotoMapGlobals.PHOTO_GRID_NUM_HORIZONTAL_COLUMNS) );
                    break;
                case 1: // Portrait Mode
                default:
                    imageView.setLayoutParams(new GridView.LayoutParams(
                            mScreenWidth / PhotoMapGlobals.PHOTO_GRID_NUM_VERTICAL_COLUMNS,
                            mScreenWidth / PhotoMapGlobals.PHOTO_GRID_NUM_VERTICAL_COLUMNS) );
                    break;
            }
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(6, 6, 6, 6);
        } else {
            // Retrieve loaded view
            imageView = (ImageView) view;
        }

        // Set image resource
        if(PhotoMapGlobals.DEMO)
            imageView.setImageResource(mThumbIds[i]);
        else {
            Glide.with(mContext).load(mFileNames.get(i)).into(imageView);
        }
        return imageView;
    }

    private static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    private static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

}
