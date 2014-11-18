package com.android.example.leanback.fastlane;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.example.leanback.R;
import com.android.example.leanback.data.Video;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class CardPresenter extends Presenter {

    private static int CARD_WIDTH = 200;
    private static int CARD_HEIGHT = 200;

    private static Context sContext;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        sContext = viewGroup.getContext().getApplicationContext();
        final ImageCardView cardView = new ImageCardView(viewGroup.getContext());
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        ((TextView) cardView.findViewById(R.id.content_text)).setTextColor(Color.LTGRAY);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object o) {
        Video video = (Video) o;
        ViewHolder vh = (ViewHolder) viewHolder;
        vh.mImageCardView.setTitleText(video.getTitle());
        vh.mImageCardView.setContentText(video.getDescription());
        vh.mImageCardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
        vh.updateCardViewImage(video.getThumbUrl());
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {

    }

    private static class PicassoImageCardViewTarget implements Target {

        private ImageCardView mCardView;

        public PicassoImageCardViewTarget(ImageCardView cardView) {
            mCardView = cardView;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            Drawable d = new BitmapDrawable(sContext.getResources(), bitmap);
            mCardView.setMainImage(d);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    }

    static class ViewHolder extends Presenter.ViewHolder {

        private ImageCardView mImageCardView;
        private Drawable mDrawable;
        private Target mTarget;

        public ViewHolder(View view) {
            super(view);
            mImageCardView = (ImageCardView) view;
            mTarget = new PicassoImageCardViewTarget(mImageCardView);
            mDrawable = sContext.getResources().getDrawable(R.drawable.filmi);
        }

        public ImageCardView getImageCardView() {
            return mImageCardView;
        }

        protected void updateCardViewImage(String url) {
            Picasso.with(sContext).
                    load(url).
                    resize(CARD_WIDTH << 1, CARD_HEIGHT << 1).
                    centerCrop().
                    error(mDrawable).
                    into(mTarget);
        }
    }
}
