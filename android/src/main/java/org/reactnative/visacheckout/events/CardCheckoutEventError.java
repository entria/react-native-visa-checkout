package org.reactnative.visacheckout.events;

import android.support.v4.util.Pools;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import org.reactnative.visacheckout.RNVisaCheckoutButtonViewManager;

/**
 * Created by jgfidelis on 08/02/18.
 */

public class CardCheckoutEventError extends Event<CardCheckoutEventError> {
    private static final Pools.SynchronizedPool<CardCheckoutEventError> EVENTS_POOL =
            new Pools.SynchronizedPool<>(3);

    String mErrorCode;
    String mErrorMessage;

    private CardCheckoutEventError() {}

    public static CardCheckoutEventError obtain(int viewTag, String errorCode, String errorMessage) {
        CardCheckoutEventError event = EVENTS_POOL.acquire();
        if (event == null) {
            event = new CardCheckoutEventError();
        }
        event.init(viewTag, errorCode, errorMessage);
        return event;
    }

    private void init(int viewTag, String errorCode, String errorMessage) {
        super.init(viewTag);
        mErrorCode = errorCode;
        mErrorMessage = errorMessage;
    }

    @Override
    public short getCoalescingKey() {
        return 0;
    }

    @Override
    public String getEventName() {
        return RNVisaCheckoutButtonViewManager.Events.EVENT_ON_CARD_CHECKOUT_ERROR.toString();
    }

    @Override
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData(mErrorCode, mErrorMessage));
    }

    private WritableMap serializeEventData(String errorCode, String errorMessage) {
        WritableMap map = Arguments.createMap();
        map.putString("code", errorCode);
        map.putString("message", errorMessage);
        return map;
    }
}
