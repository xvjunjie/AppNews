package com.appnews.jjs.appnews.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appnews.jjs.appnews.R;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

/**
 * Created by 15596 on 2016/10/13.
 */

public class HomeImgAdapter extends StaticPagerAdapter {
    private Context mContext;

    private int[] imags = {
            R.mipmap.home_hot2 ,
            R.mipmap.home_recommend1 ,
            R.mipmap.home_recommend2
    };

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView mImageView = new ImageView(container.getContext());
        mImageView.setImageResource(imags[position]);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        return mImageView;
    }

    @Override
    public int getCount() {
        return imags.length;
    }
}
