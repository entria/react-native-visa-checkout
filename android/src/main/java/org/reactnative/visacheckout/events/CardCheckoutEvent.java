package org.reactnative.visacheckout.events;

import android.support.v4.util.Pools;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.visa.checkout.VisaPaymentSummary;

import org.reactnative.visacheckout.RNVisaCheckoutButtonViewManager;
import org.reactnative.visacheckout.RNVisaCheckoutHelper;

/**
 * Created by jgfidelis on 08/02/18.
 */

public class CardCheckoutEvent extends Event<CardCheckoutEvent> {
    private static final Pools.SynchronizedPool<CardCheckoutEvent> EVENTS_POOL =
            new Pools.SynchronizedPool<>(3);

    VisaPaymentSummary mPaymentSummary;

    private CardCheckoutEvent() {}

    public static CardCheckoutEvent obtain(int viewTag, VisaPaymentSummary paymentSummary) {
        CardCheckoutEvent event = EVENTS_POOL.acquire();
        if (event == null) {
            event = new CardCheckoutEvent();
        }
        event.init(viewTag, paymentSummary);
        return event;
    }

    private void init(int viewTag, VisaPaymentSummary paymentSummary) {
        super.init(viewTag);
        mPaymentSummary = paymentSummary;
    }

    @Override
    public short getCoalescingKey() {
        return 0;
    }

    @Override
    public String getEventName() {
        return RNVisaCheckoutButtonViewManager.Events.EVENT_ON_CARD_CHECKOUT.toString();
    }

    @Override
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData(mPaymentSummary));
    }

    private WritableMap serializeEventData(VisaPaymentSummary paymentSummary) {
        return RNVisaCheckoutHelper.getPaymentSummaryMap(paymentSummary);
    }
}
