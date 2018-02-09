package org.reactnative.visacheckout;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

/**
 * Created by jgfidelis on 08/02/18.
 */

public class RNVisaCheckoutButtonViewManager extends SimpleViewManager {
    public enum Events {
//        EVENT_CAMERA_READY("onCameraReady"),
//        EVENT_ON_MOUNT_ERROR("onMountError"),
//        EVENT_ON_BAR_CODE_READ("onBarCodeRead"),
//        EVENT_ON_FACES_DETECTED("onFacesDetected"),
//        EVENT_ON_FACE_DETECTION_ERROR("onFaceDetectionError");

        EVENT_ON_CARD_CHECKOUT("onCardCheckout"),
        EVENT_ON_CARD_CHECKOUT_ERROR("onCardCheckoutError");

        private final String mName;

        Events(final String name) {
            mName = name;
        }

        @Override
        public String toString() {
            return mName;
        }
    }

    private ThemedReactContext mContext = null;

    private static final String REACT_CLASS = "RNVisaCheckoutButton";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected RNVisaCheckoutButtonView createViewInstance(ThemedReactContext themedReactContext) {
        mContext = themedReactContext;
        return new RNVisaCheckoutButtonView(mContext);
//        return new VisaCheckoutButton(themedReactContext);
    }

    @ReactProp(name = "cardStyle")
    public void setCardStyle(RNVisaCheckoutButtonView view, int cardStyle) {
        view.setCardStyle(cardStyle);
    }

    @ReactProp(name = "cardAnimations")
    public void setCardAnimations(RNVisaCheckoutButtonView view, boolean isEnabled) {
        view.setCardAnimations(isEnabled);
    }

    @ReactProp(name = "checkoutOptions")
    public void setCheckoutOptions(RNVisaCheckoutButtonView view, ReadableMap options) {
        double total = options.getDouble("total");
        int currencyCode = options.getInt("currency");
        Activity act = mContext.getCurrentActivity();
        view.setCheckoutOptions(total, currencyCode, act);
    }

    @Override
    @Nullable
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        MapBuilder.Builder<String, Object> builder = MapBuilder.builder();
        for (Events event : Events.values()) {
            builder.put(event.toString(), MapBuilder.of("registrationName", event.toString()));
        }
        return builder.build();
    }
}
