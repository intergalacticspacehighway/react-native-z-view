package com.reactnativezview;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.PointerEvents;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerHelper;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.views.view.ReactViewGroup;
import com.facebook.react.views.view.ReactViewManager;

public class ZViewRootViewGroupManager extends ReactViewManager {
    public static final String REACT_CLASS = "ZViewRootViewGroup";
    ReactApplicationContext mCallerContext;

    public ZViewRootViewGroupManager(ReactApplicationContext reactContext) {
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

    @Override
    protected void addEventEmitters(@NonNull ThemedReactContext reactContext, @NonNull ReactViewGroup view) {
        super.addEventEmitters(reactContext, view);
        final EventDispatcher dispatcher =
                UIManagerHelper.getEventDispatcherForReactTag(mCallerContext, view.getId());
        ((ZViewRootViewGroup) view).setEventDispatcher(dispatcher);
    }

    @NonNull
    @Override
    public ZViewRootViewGroup createViewInstance(ThemedReactContext context) {
        return new ZViewRootViewGroup(context);
    }
}
