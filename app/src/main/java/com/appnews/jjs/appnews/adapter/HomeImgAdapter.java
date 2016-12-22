package com.appnews.jjs.appnews.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appnews.jjs.appnews.bean.TopStoriesBean;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15596 on 2016/10/13.
 */

public class HomeImgAdapter extends StaticPagerAdapter {
    private Context mContext;
    private List<TopStoriesBean> imgs;

    public HomeImgAdapter(Context mContext) {
        this.mContext = mContext;
        this.imgs = new ArrayList<>();
    }

    public void setTopEntities(List<TopStoriesBean> topEntities) {
        this.imgs = topEntities;
    }


    @Override
    public View getView(ViewGroup container, int position) {

        ImageView mImageView = new ImageView(container.getContext());
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        Picasso.with(mContext).load(imgs.get(position).getImage()).into(mImageView);


        return mImageView;
    }

    @Override
    public int getCount() {
        return imgs.size();
    }
}
