package com.example.nikmc.agima.model;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Created by NIKMC-I on 03.05.2016.
 */
public class ExtHScrollView extends HorizontalScrollView {

    private ScrollViewListener scrollViewListener = null;
    private int extentVertical, extentHorizontal;
    public ExtHScrollView(Context context) {
        super(context);
    }

    public ExtHScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExtHScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExtHScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    protected int computeHorizontalScrollExtent() {
        extentHorizontal = super.computeHorizontalScrollExtent();
        return extentHorizontal;
    }

    public int getExtentHorizontal() {
        return extentHorizontal;
    }

    // -------------------------------------------------------------------------------
    /**
     * @return ширину контента
     */
    final public int getChildWidth() {
        return getChildAt(0).getWidth();
    }

    /**
     * @return высоту контента
     */
    final public int getChildHeight() {
        return getChildAt(0).getHeight();
    }

    // -------------------------------------------------------------------------------
    /**
     * установить обработчик прокрутки
     *
     * @param svl
     */
    public void setOnScrollChanged(ScrollViewListener svl) {
        scrollViewListener = svl;
    }

    // -------------------------------------------------------------------------------
    /**
     * интерфейс обработчика прокрутки
     *
     */
    public interface ScrollViewListener {

        /**
         * @param scrollView
         *            - окно прокрутки
         * @param l
         * @param t
         *            - y координата индикатора прокрутки
         * @param oldl
         * @param oldt
         *            - старая y координата индикатора прокрутки
         */
        void onScrollChanged(ExtHScrollView scrollView, int l, int t, int oldl,
                             int oldt);
    }
}
