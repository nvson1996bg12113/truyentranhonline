package com.binhtt.truyentranhonline.widget;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author binhtt <binhjdev@gmail.com>
 * @version bg.0.0
 * @since 14/08/2017
 */
public class AlphaCardView extends CardView {
    public AlphaCardView(Context context) {
        super(context);
    }

    public AlphaCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlphaCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isClickable() && isEnabled()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    setAlpha(0.7f);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    setAlpha(1f);
                    break;
                default:
                    break;
            }
        }

        return super.onTouchEvent(event);
    }
}
