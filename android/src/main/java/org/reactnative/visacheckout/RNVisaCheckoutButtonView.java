package org.reactnative.visacheckout;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.facebook.react.uimanager.ThemedReactContext;
import com.visa.checkout.CheckoutButton;
import com.visa.checkout.PurchaseInfo;
import com.visa.checkout.VisaCheckoutSdk;
import com.visa.checkout.VisaPaymentSummary;

import java.math.BigDecimal;

/**
 * Created by jgfidelis on 08/02/18.
 */

public class RNVisaCheckoutButtonView extends RelativeLayout {
    
    private ThemedReactContext mContext;

    public RNVisaCheckoutButtonView(ThemedReactContext context) {
        super(context);
        init(context);
    }

    public RNVisaCheckoutButtonView(ThemedReactContext context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RNVisaCheckoutButtonView(ThemedReactContext context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RNVisaCheckoutButtonView(ThemedReactContext context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(@NonNull ThemedReactContext context) {
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.custom_view, this, true);
        SingletonViewHolder.getInstance().setView(this);
    }

    public void setCardStyle(int cardStyle) {
        CheckoutButton btn = (CheckoutButton)findViewById(R.id.visaCheckoutButton);
        if (cardStyle == Constants.CARD_STYLE_STANDARD) {
            btn.setColorStyle("STANDARD");
        } else {
            btn.setColorStyle("NEUTRAL");
        }
    }

    public void setCardAnimations(boolean isEnabled) {
        CheckoutButton btn = (CheckoutButton)findViewById(R.id.visaCheckoutButton);
        btn.setAnimation(isEnabled);
    }

    public void setCheckoutOptions(final double total, final int currencyCode, final Activity activity) {
        CheckoutButton btn = (CheckoutButton)findViewById(R.id.visaCheckoutButton);
        String currencyString = RNVisaCheckoutHelper.getCurrencyStringFromConstant(currencyCode);
        PurchaseInfo purchaseInfo = new PurchaseInfo.PurchaseInfoBuilder(new BigDecimal(total),
                                                                         currencyString)
                                        .build();
        btn.init(activity, RNVisaCheckoutModule.getProfile(), purchaseInfo, new VisaCheckoutSdk.VisaCheckoutResultListener() {
            @Override
            public void onResult(VisaPaymentSummary visaPaymentSummary) {
                if (VisaPaymentSummary.PAYMENT_SUCCESS.equalsIgnoreCase(visaPaymentSummary.getStatusName())) {
                    RNVisaCheckoutHelper.emitCardCheckoutEvent(SingletonViewHolder.getInstance().getView().getId(), visaPaymentSummary, mContext);
                } else if (VisaPaymentSummary.PAYMENT_CANCEL.equalsIgnoreCase(visaPaymentSummary.getStatusName())) {
                    RNVisaCheckoutHelper.emitCardCheckoutErrorEvent(SingletonViewHolder.getInstance().getView().getId(), "", "User Cancelled", mContext);
                } else if (VisaPaymentSummary.PAYMENT_ERROR.equalsIgnoreCase(visaPaymentSummary.getStatusName())) {
                    RNVisaCheckoutHelper.emitCardCheckoutErrorEvent(SingletonViewHolder.getInstance().getView().getId(), "", "Payment Error", mContext);
                } else if (VisaPaymentSummary.PAYMENT_FAILURE.equalsIgnoreCase(visaPaymentSummary.getStatusName())) {
                    RNVisaCheckoutHelper.emitCardCheckoutErrorEvent(SingletonViewHolder.getInstance().getView().getId(), "", "Payment Failure", mContext);
                }
            }
        });

    }
}
