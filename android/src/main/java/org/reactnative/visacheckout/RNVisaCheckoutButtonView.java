package org.reactnative.visacheckout;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.facebook.react.uimanager.ThemedReactContext;
import com.visa.checkout.VisaCheckoutSdk;
import com.visa.checkout.widget.VisaCheckoutButton;

import java.math.BigDecimal;

/**
 * Created by jgfidelis on 08/02/18.
 */

public class RNVisaCheckoutButtonView extends RelativeLayout {

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
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.custom_view, this, true);
        SingletonViewHolder.getInstance().setView(this);
    }

    public void setCardStyle(int cardStyle) {
        VisaCheckoutButton btn = (VisaCheckoutButton)findViewById(R.id.visaCheckoutButton);
        if (cardStyle == Constants.CARD_STYLE_STANDARD) {
            btn.setColorStyle("STANDARD");
        } else {
            btn.setColorStyle("NEUTRAL");
        }
    }

    public void setCardAnimations(boolean isEnabled) {
        VisaCheckoutButton btn = (VisaCheckoutButton)findViewById(R.id.visaCheckoutButton);
        btn.setAnimation(isEnabled);
    }

    public void setCheckoutOptions(final double total, final int currencyCode, final Activity activity) {
        VisaCheckoutButton btn = (VisaCheckoutButton)findViewById(R.id.visaCheckoutButton);
        btn.setCheckoutListener(new VisaCheckoutButton.CheckoutWithVisaListener() {
            @Override
            public void onClick() {
                String currencyString = RNVisaCheckoutHelper.getCurrencyStringFromConstant(currencyCode);
                Intent intent = VisaCheckoutSdk.getCheckoutIntent(
                        activity,
                        new BigDecimal(total),
                        currencyString
                );
                activity.startActivityForResult(intent, Constants.VISA_CARD_CHECKOUT);
            }
        });

    }
}
