package com.liicon.uil;

import android.graphics.Bitmap;
import android.view.View;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public abstract class ImageLoadCallBack<T extends View> implements ImageLoadingListener {
    
    private T view;
    
    public ImageLoadCallBack(T view) {
        super();
        this.view = view;
    }

    @Override
    public final void onLoadingStarted(String imageUri, View view) {
        onLoadingStarted(this.view, imageUri);
    }
    @Override
    public final void onLoadingFailed(String imageUri, View view,
            FailReason failReason) {
        onLoadingFailed(this.view, imageUri, failReason);
    }
    @Override
    public final void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        onLoadingComplete(this.view, imageUri, loadedImage);
    }
    @Override
    public final void onLoadingCancelled(String imageUri, View view) {
        onLoadingCancelled(this.view, imageUri);
    }
    
    
//    public abstract void onLoadingStarted(T view, String imageUri);
//    public abstract void onLoadingFailed(T view, String imageUri, FailReason failReason);
//    public abstract void onLoadingComplete(T view, String imageUri, Bitmap loadedImage);
//    public abstract void onLoadingCancelled(T view, String imageUri);
    public void onLoadingStarted(T view, String imageUri) {
    }
    public void onLoadingFailed(T view, String imageUri, FailReason failReason) {
    }
    public void onLoadingComplete(T view, String imageUri, Bitmap loadedImage) {
    }
    public void onLoadingCancelled(T view, String imageUri) {
    }

}
