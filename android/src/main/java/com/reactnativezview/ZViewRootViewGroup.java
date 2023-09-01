package com.reactnativezview;


import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.facebook.react.config.ReactFeatureFlags;
import com.facebook.react.uimanager.JSPointerDispatcher;
import com.facebook.react.uimanager.JSTouchDispatcher;
import com.facebook.react.uimanager.RootView;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.views.view.ReactViewGroup;

class ZViewRootViewGroup extends ReactViewGroup implements RootView {
    private EventDispatcher mEventDispatcher;
    private final JSTouchDispatcher mJSTouchDispatcher = new JSTouchDispatcher(this);
    @Nullable
    private JSPointerDispatcher mJSPointerDispatcher;

    public ZViewRootViewGroup(Context context) {
        super(context);
        if (ReactFeatureFlags.dispatchPointerEvents) {
            this.mJSPointerDispatcher = new JSPointerDispatcher(this);
        }
    }

    public void setEventDispatcher(EventDispatcher eventDispatcher) {
        this.mEventDispatcher = eventDispatcher;
    }

    public void handleException(Throwable t) {
        this.getReactContext().getReactApplicationContext().handleException(new RuntimeException(t));
    }

    private ThemedReactContext getReactContext() {
        return (ThemedReactContext)this.getContext();
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        mJSTouchDispatcher.handleTouchEvent(event, mEventDispatcher);
        if (mJSPointerDispatcher != null) {
            mJSPointerDispatcher.handleMotionEvent(event, mEventDispatcher, true);
        }
        return super.onInterceptTouchEvent(event);
    }

    public boolean onTouchEvent(MotionEvent event) {
        mJSTouchDispatcher.handleTouchEvent(event, mEventDispatcher);
        if (mJSPointerDispatcher != null) {
            mJSPointerDispatcher.handleMotionEvent(event, mEventDispatcher, false);
        }
        super.onTouchEvent(event);
        // In case when there is no children interested in handling touch event, we return true from
        // the root view in order to receive subsequent events related to that gesture
        return true;
    }

    public boolean onInterceptHoverEvent(MotionEvent event) {
       if (this.mJSPointerDispatcher != null) {
           this.mJSPointerDispatcher.handleMotionEvent(event, this.mEventDispatcher, true);
       }

        return false;
    }

    public boolean onHoverEvent(MotionEvent event) {
       if (this.mJSPointerDispatcher != null) {
           this.mJSPointerDispatcher.handleMotionEvent(event, this.mEventDispatcher, false);
       }

        return false;
    }

    public void onChildStartedNativeGesture(MotionEvent ev) {
       this.onChildStartedNativeGesture((View)null, ev);
    }

    public void onChildStartedNativeGesture(View childView, MotionEvent ev) {
       this.mJSTouchDispatcher.onChildStartedNativeGesture(ev, this.mEventDispatcher);
       if (this.mJSPointerDispatcher != null) {
           this.mJSPointerDispatcher.onChildStartedNativeGesture(childView, ev, this.mEventDispatcher);
       }

    }

    public void onChildEndedNativeGesture(View childView, MotionEvent ev) {
       this.mJSTouchDispatcher.onChildEndedNativeGesture(ev, this.mEventDispatcher);
       if (this.mJSPointerDispatcher != null) {
           this.mJSPointerDispatcher.onChildEndedNativeGesture();
       }

    }

    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
}
