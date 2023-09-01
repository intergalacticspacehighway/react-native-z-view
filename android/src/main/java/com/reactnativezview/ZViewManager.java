package com.reactnativezview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.view.ReactViewGroup;
import com.facebook.react.views.view.ReactViewManager;

public class ZViewManager extends ReactViewManager {
    public static final String REACT_CLASS = "ZView";
    ReactApplicationContext mCallerContext;

    public ZViewManager(ReactApplicationContext reactContext) {
        mCallerContext = reactContext;
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    @ReactProp(name="pointerEvents")
    public void setPointerEvents(ReactViewGroup view, @Nullable String pointerEventsStr) {
        super.setPointerEvents(view, pointerEventsStr);
    }

    @ReactProp(name="touchable", defaultBoolean = true)
    public void setTouchable(ZView view, boolean touchable) {
        view.setTouchable(touchable);
    }

    @ReactProp(name="coordinates")
    public void setCoordinates(ZView view, ReadableMap coords) {
        view.setCoords(coords);
    }

    @NonNull
    @Override
    public ZView createViewInstance(ThemedReactContext context) {
        return new ZView(context);
    }
}
