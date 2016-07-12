package com.lll.bitmaploader.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Version 1.0
 * Created by lll on 16/7/7.
 * Description 支持滚出屏幕自动回收资源的ImageView
 * copyright generalray4239@gmail.com
 */
public class RecyclingImageView extends ImageView{

    public RecyclingImageView(Context context) {
        super(context);
    }

    public RecyclingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDetachedFromWindow() {
        setImageDrawable(null);
        super.onDetachedFromWindow();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        //获取前一次的drawable
        final  Drawable preDrawable = getDrawable();
        super.setImageDrawable(drawable);
        notifyDrawable(drawable,true);
        notifyDrawable(preDrawable,false);
    }

    /**
     * Drawable 状态是否变化
     * @param drawable
     * @param isDisplayed 是否变化
     */
    private  void notifyDrawable(Drawable drawable, final boolean isDisplayed){
          if(drawable instanceof RecyclingBitmapDrawable){
              ((RecyclingBitmapDrawable) drawable).setIsDisplayed(isDisplayed);
          }else if(drawable instanceof LayerDrawable){//循环迭代回收资源
              LayerDrawable layerDrawable = (LayerDrawable) drawable;
              for (int i = 0, z = layerDrawable.getNumberOfLayers(); i < z; i++) {
                  notifyDrawable(layerDrawable.getDrawable(i), isDisplayed);
              }
          }

    }
}
