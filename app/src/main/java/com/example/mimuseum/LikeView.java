package com.example.mimuseum;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Switch;

import androidx.core.content.res.ResourcesCompat;

/**
 * TODO: document your custom view class.
 */
public class LikeView extends Switch {
    Drawable drawLike;

    public LikeView(Context context) {
        super(context);
    }
    public LikeView(Context context, AttributeSet att) {
        super(context,att);
        init();
    }
    public LikeView(Context context, AttributeSet att, int defStyleAtt) {
        super(context,att,defStyleAtt);
        init();
    }
    private void init() {
        drawLike = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_thumb_up_alt_24, null);
    }
}