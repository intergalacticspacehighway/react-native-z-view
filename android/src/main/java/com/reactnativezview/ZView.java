package com.reactnativezview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.config.ReactFeatureFlags;
import com.facebook.react.uimanager.FabricViewStateManager;
import com.facebook.react.uimanager.JSPointerDispatcher;
import com.facebook.react.uimanager.JSTouchDispatcher;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.RootView;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.views.view.ReactViewGroup;

public class ZView extends ReactViewGroup {
    @Nullable
    private ZViewRootViewGroup mHostView;

    private  WindowManager windowManager;

    private boolean touchable = true;

    @Nullable
    private ReadableMap coords;

    public ZView(Context context) {
        super(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    @Override
    public void addView(View child, int index, LayoutParams params) {
        mHostView = (ZViewRootViewGroup) child;
        WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT);

        windowParams.gravity = Gravity.TOP | Gravity.LEFT;
        windowParams.x = 0;
        windowParams.y = 0;
        windowManager.addView(mHostView, windowParams);
        updateTouchable();
        updateXAndY();
    }

    private void updateTouchable() {
        if (this.mHostView != null) {
            WindowManager.LayoutParams existingParams = (WindowManager.LayoutParams) this.mHostView.getLayoutParams();
            if (touchable) {
                existingParams.flags &= ~WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
            } else {
                existingParams.flags |= WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
            }
            windowManager.updateViewLayout(this.mHostView, existingParams);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int width = right - left;
        int height = bottom - top;
        WindowManager.LayoutParams existingParams = (WindowManager.LayoutParams) this.mHostView.getLayoutParams();
        existingParams.width = width;
        existingParams.height = height;
        windowManager.updateViewLayout(mHostView, existingParams);
        updateXAndY();
    }


    public void removeView(View child) {
        // ZView will always have a single child
        mHostView = null;
    }

    public void removeViewAt(int index) {
        // ZView will always have a single child
        mHostView = null;
    }

    public void setCoords(ReadableMap coords) {
        this.coords = coords;
        updateXAndY();
    }

    public void setTouchable(boolean touchable) {
        this.touchable = touchable;
        updateTouchable();
    }

    private void updateXAndY() {
        if (mHostView != null) {
            int decorWidth = this.mHostView.getWidth();
            int decorHeight = this.mHostView.getHeight();
            int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
            int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
            int top = 0;
            int left = 0;
            if (coords != null) {
                if (coords.hasKey("top")) {
                    Dynamic topDynamic = coords.getDynamic("top");
                    if (topDynamic.getType() == ReadableType.Number) {
                        top = (int) PixelUtil.toPixelFromDIP(coords.getInt("top"));
                    } else if (topDynamic.getType() == ReadableType.String && topDynamic.asString().endsWith("%")) {
                        int topPer = Integer.parseInt(topDynamic.asString().replace("%", ""));
                        top = (int) ((topPer/100.0) * screenHeight);
                    }
                } else if (coords.hasKey("bottom")){
                    Dynamic bottomDynamic = coords.getDynamic("bottom");
                    if (bottomDynamic.getType() == ReadableType.Number) {
                        top = screenHeight - decorHeight - (int) PixelUtil.toPixelFromDIP(coords.getInt("bottom"));
                    } else if (bottomDynamic.getType() == ReadableType.String && bottomDynamic.asString().endsWith("%")) {
                        int bottomPer = Integer.parseInt(bottomDynamic.asString().replace("%", ""));
                        top = screenHeight - (int) (bottomPer/100.0 * screenHeight) - decorHeight;
                    }
                }

                if (coords.hasKey("left")) {
                    Dynamic leftDynamic = coords.getDynamic("left");
                    if (leftDynamic.getType() == ReadableType.Number) {
                        left = (int) PixelUtil.toPixelFromDIP(coords.getInt("left"));
                    } else if (leftDynamic.getType() == ReadableType.String && leftDynamic.asString().endsWith("%")) {
                        int leftPer = Integer.parseInt(leftDynamic.asString().replace("%", ""));
                        left = (int) ((leftPer / 100.0) * screenWidth);
                    }
                } else if (coords.hasKey("right")){
                    Dynamic rightDynamic = coords.getDynamic("right");
                    if (rightDynamic.getType() == ReadableType.Number) {
                        left = screenWidth - decorWidth - (int) PixelUtil.toPixelFromDIP(coords.getInt("right"));
                    } else if (rightDynamic.getType() == ReadableType.String && rightDynamic.asString().endsWith("%")) {
                        int rightPer = Integer.parseInt(rightDynamic.asString().replace("%", ""));
                        left = screenWidth - (int) (rightPer/100.0 * screenWidth) - decorWidth;
                    }
                }
            }

            WindowManager.LayoutParams existingParams = (WindowManager.LayoutParams) mHostView.getLayoutParams();
            existingParams.x = left;
            existingParams.y = top;
            windowManager.updateViewLayout(mHostView, existingParams);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mHostView != null) {
            windowManager.removeView(this.mHostView);
            mHostView = null;
        }
    }
}
