package com.example.jasmine.goalideatracker;

/**
 * Created by jasmine on 22/12/17.
 */

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerViewOnTouchListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    GestureDetector mGestureDetector;

    public RecyclerViewOnTouchListener(Context context, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
        }
        return false;
    }
    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {


    }

    public static boolean isViewClicked(View view, MotionEvent e) {
        Rect rect = new Rect();

        view.getGlobalVisibleRect(rect);

        return rect.contains((int) e.getRawX(), (int) e.getRawY());
    }
}