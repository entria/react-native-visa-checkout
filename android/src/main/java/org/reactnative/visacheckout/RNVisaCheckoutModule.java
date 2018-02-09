
package org.reactnative.visacheckout;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.visa.checkout.VisaCheckoutSdk;
import com.visa.checkout.VisaCheckoutSdkInitListener;
import com.visa.checkout.VisaPaymentSummary;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class RNVisaCheckoutModule extends ReactContextBaseJavaModule {

    private Promise mCheckoutPromise;
    private RNVisaCheckoutButtonView mView;

    private static final String E_CHECKOUT_CANCELLED = "E_CHECKOUT_CANCELLED";
    private static final String E_FAILED_TO_SHOW_CHECKOUT = "E_FAILED_TO_SHOW_CHECKOUT";
    private static final String E_CHECKOUT_INTERNAL_ERROR = "E_CHECKOUT_INTERNAL_ERROR";
    private static final String E_CHECKOUT_INITIALIZED_FAILED = "E_CHECKOUT_INITIALIZED_FAILED";
    private static final String E_CHECKOUT_SDK_NOT_INITIALIZED = "E_CHECKOUT_SDK_NOT_INITIALIZED";
    private static final String E_CHECKOUT_MISSING_PARAMETER = "E_CHECKOUT_MISSING_PARAMETER";
    private static final String E_CHECKOUT_FAIL_TO_LAUNCH = "E_CHECKOUT_FAIL_TO_LAUNCH";
    private static final String E_CHECKOUT_CLOSED_CALLED = "E_CHECKOUT_CLOSED_CALLED";
    private static final String E_CONFIGURE_INVALID_API_KEY = "E_CONFIGURE_INVALID_API_KEY";
    private static final String E_CONFIGURE_UNSUPPORTED_SDK_VERSION = "E_CONFIGURE_UNSUPPORTED_SDK_VERSION";
    private static final String E_CONFIGURE_OS_VERSION_NOT_SUPPORTED = "E_CONFIGURE_OS_VERSION_NOT_SUPPORTED";
    private static final String E_CONFIGURE_FAIL_TO_INITIALIZE = "E_CONFIGURE_FAIL_TO_INITIALIZE";
    private static final String E_CONFIGURE_MISSING_PARAMETER = "E_CONFIGURE_MISSING_PARAMETER";
    private static final String E_CONFIGURE_INTERNAL_ERROR = "E_CONFIGURE_INTERNAL_ERROR";

    private final ReactApplicationContext reactContext;

    public RNVisaCheckoutModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        reactContext.addActivityEventListener(mActivityEventListener);
    }

    @Override
    public String getName() {
        return "RNVisaCheckout";
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        return Collections.unmodifiableMap(new HashMap<String, Object>() {
            {
                put("Environment", getEnvConstants());
                put("Country", getCountryConstants());
                put("Currency", getCurrencyConstants());
                put("Card", getCardConstants());
                put("CardStyle", getCardStyleConstants());
            }

            private Map<String, Object> getEnvConstants() {
                return Collections.unmodifiableMap(new HashMap<String, Object>() {
                    {
                        put("Production", Constants.ENVIRONMENT_PRODUCTION);
                        put("Sandbox", Constants.ENVIRONMENT_SANDBOX);
                    }
                });
            }

            private Map<String, Object> getCardStyleConstants() {
                return Collections.unmodifiableMap(new HashMap<String, Object>() {
                    {
                        put("Neutral", Constants.CARD_STYLE_NEUTRAL);
                        put("Standard", Constants.CARD_STYLE_STANDARD);
                    }
                });
            }
            private Map<String, Object> getCountryConstants() {
                return Collections.unmodifiableMap(new HashMap<String, Object>() {
                    {
                        put("Argentina", Constants.COUNTRY_ARGENTINA);
                        put("Australia", Constants.COUNTRY_AUSTRALIA);
                        put("Brazil", Constants.COUNTRY_BRAZIL);
                        put("Canada", Constants.COUNTRY_CANADA);
                        put("Chile", Constants.COUNTRY_CHILE);
                        put("China", Constants.COUNTRY_CHINA);
                        put("Colombia", Constants.COUNTRY_COLOMBIA);
                        put("France", Constants.COUNTRY_FRANCE);
                        put("HongKong", Constants.COUNTRY_HONGKONG);
                        put("India", Constants.COUNTRY_INDIA);
                        put("Ireland", Constants.COUNTRY_IRELAND);
                        put("Kuwait", Constants.COUNTRY_KUWAIT);
                        put("Malaysia", Constants.COUNTRY_MALAYSIA);
                        put("Mexico", Constants.COUNTRY_MEXICO);
                        put("NewZealand", Constants.COUNTRY_NEW_ZEALAND);
                        put("Peru", Constants.COUNTRY_PERU);
                        put("Poland", Constants.COUNTRY_POLAND);
                        put("Qatar", Constants.COUNTRY_QATAR);
                        put("SaudiArabia", Constants.COUNTRY_SAUDI_ARABIA);
                        put("Singapore", Constants.COUNTRY_SINGAPORE);
                        put("SouthAfrica", Constants.COUNTRY_SOUTH_AFRICA);
                        put("Spain", Constants.COUNTRY_SPAIN);
                        put("Ukraine", Constants.COUNTRY_UKRAINE);
                        put("UnitedArabEmirates", Constants.COUNTRY_UNITED_ARAB_EMIRATES);
                        put("UnitedKingdom", Constants.COUNTRY_UNITED_KINGDOM);
                        put("UnitedStates", Constants.COUNTRY_UNITED_STATES);
                    }
                });
            }

            private Map<String, Object> getCurrencyConstants() {
                return Collections.unmodifiableMap(new HashMap<String, Object>() {
                    {
                        put("AED", Constants.CURRENCY_AED);
                        put("ARS", Constants.CURRENCY_ARS);
                        put("AUD", Constants.CURRENCY_AUD);
                        put("BRL", Constants.CURRENCY_BRL);
                        put("CAD", Constants.CURRENCY_CAD);
                        put("CLP", Constants.CURRENCY_CLP);
                        put("CNY", Constants.CURRENCY_CNY);
                        put("COP", Constants.CURRENCY_COP);
                        put("EUR", Constants.CURRENCY_EUR);
                        put("GBP", Constants.CURRENCY_GBP);
                        put("HKD", Constants.CURRENCY_HKD);
                        put("INR", Constants.CURRENCY_INR);
                        put("KWD", Constants.CURRENCY_KWD);
                        put("MXN", Constants.CURRENCY_MXN);
                        put("MYR", Constants.CURRENCY_MYR);
                        put("NZD", Constants.CURRENCY_NZD);
                        put("PEN", Constants.CURRENCY_PEN);
                        put("PLN", Constants.CURRENCY_PLN);
                        put("QAR", Constants.CURRENCY_QAR);
                        put("SAR", Constants.CURRENCY_SAR);
                        put("SGD", Constants.CURRENCY_SGD);
                        put("UAH", Constants.CURRENCY_UAH);
                        put("USD", Constants.CURRENCY_USD);
                        put("ZAR", Constants.CURRENCY_ZAR);
                    }
                });
            }

            private Map<String, Object> getCardConstants() {
                return Collections.unmodifiableMap(new HashMap<String, Object>() {
                    {
                        put("Amex", Constants.CARD_BRAND_AMEX);
                        put("Discover", Constants.CARD_BRAND_DISCOVER);
                        put("Electron", Constants.CARD_BRAND_ELECTRON);
                        put("Elo", Constants.CARD_BRAND_ELO);
                        put("Mastercard", Constants.CARD_BRAND_MASTERCARD);
                        put("Visa", Constants.CARD_BRAND_VISA);
                    }
                });
            }
        });
    }

    public WritableMap getResponseData(int resultCode) {
        String errorCode = null;
        String errorMessage = null;
        boolean status = false;

        if (resultCode == Activity.RESULT_CANCELED) {
            errorCode = "E_CHECKOUT_CANCELLED";
            errorMessage = "Visa Checkout checkout was cancelled";
        } else if (resultCode == Activity.RESULT_OK) {
            status = true;
        } else if (resultCode == VisaCheckoutSdk.ResultCode.RESULT_INTERNAL_ERROR) {
            errorCode = "E_CHECKOUT_INTERNAL_ERROR";
            errorMessage = "Visa Checkout internal error";
        } else if (resultCode == VisaCheckoutSdk.ResultCode.RESULT_INITIALIZED_FAILED) {
            errorCode = "E_CHECKOUT_INITIALIZED_FAILED";
            errorMessage = "Visa Checkout initialized failed";
        } else if (resultCode == VisaCheckoutSdk.ResultCode.RESULT_SDK_NOT_INITIALIZED) {
            errorCode = "E_CHECKOUT_SDK_NOT_INITIALIZED";
            errorMessage = "Visa Checkout SDK not initialized";
        } else if (resultCode == VisaCheckoutSdk.ResultCode.RESULT_MISSING_PARAMETER) {
            errorCode = "E_CHECKOUT_MISSING_PARAMETER";
            errorMessage = "Visa Checkout missing parameter";
        } else if (resultCode == VisaCheckoutSdk.ResultCode.RESULT_FAIL_TO_LAUNCH) {
            errorCode = "E_CHECKOUT_FAIL_TO_LAUNCH";
            errorMessage = "Visa Checkout failed to launch";
        } else if (resultCode == VisaCheckoutSdk.ResultCode.RESULT_CLOSED_CALLED) {
            errorCode = "E_CHECKOUT_CLOSED_CALLED";
            errorMessage = "Visa Checkout closed called";
        }
        WritableMap myMap = Arguments.createMap();
        myMap.putBoolean("status", status);
        myMap.putString("errorCode", errorCode);
        myMap.putString("errorMessage", errorMessage);
        return myMap;
    }

    private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {

        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
            if (requestCode == Constants.VISA_CARD_CHECKOUT) {
                WritableMap map = getResponseData(resultCode);
                if (map.getBoolean("status") && intent != null) {
                    final VisaPaymentSummary paymentSummary = intent.getParcelableExtra(VisaCheckoutSdk.INTENT_PAYMENT_SUMMARY);
                    RNVisaCheckoutHelper.emitCardCheckoutEvent(SingletonViewHolder.getInstance().getView().getId(), paymentSummary, getReactApplicationContext());
                } else {
                    String errorCode = map.getString("errorCode");
                    String errorMessage = map.getString("errorMessage");
                    RNVisaCheckoutHelper.emitCardCheckoutErrorEvent(SingletonViewHolder.getInstance().getView().getId(), errorCode, errorMessage, getReactApplicationContext());
                }
            } else if (requestCode == Constants.VISA_CHECKOUT_REQUEST) {
                if (mCheckoutPromise != null) {
                    WritableMap map = getResponseData(resultCode);
                    if (map.getBoolean("status") && intent != null) {
                        final VisaPaymentSummary paymentSummary = intent.getParcelableExtra(VisaCheckoutSdk.INTENT_PAYMENT_SUMMARY);
                        mCheckoutPromise.resolve(RNVisaCheckoutHelper.getPaymentSummaryMap(paymentSummary));
                    } else {
                        String errorCode = map.getString("errorCode");
                        String errorMessage = map.getString("errorMessage");
                        mCheckoutPromise.reject(errorCode, errorMessage);
                    }
                    mCheckoutPromise = null;
                }
            }
        }
    };

    @ReactMethod
    public void configureProfile(final int environment, final String apiKey, final String profileName, final Promise promise) {
        String envString = RNVisaCheckoutHelper.getEnvironmentStringFromConstant(environment);
        VisaCheckoutSdk.init(getReactApplicationContext(), envString,
                apiKey, profileName,
                new VisaCheckoutSdkInitListener() {
                    @Override public void status(int code, String message) {
                        Log.d("TRAKS", "Code:" + code + "  Message:" + message);
                        if (code == VisaCheckoutSdk.Status.SUCCESS) {
                            WritableMap map = Arguments.createMap();
                            map.putInt("status", VisaCheckoutSdk.Status.SUCCESS);
                            promise.resolve(map);
                        } else if (code == VisaCheckoutSdk.Status.INVALID_API_KEY){
                            promise.reject(E_CONFIGURE_INVALID_API_KEY, "Invalid API Key");
                        } else if (code == VisaCheckoutSdk.Status.UNSUPPORTED_SDK_VERSION) {
                            promise.reject(E_CONFIGURE_UNSUPPORTED_SDK_VERSION, "Unsupported SDK version");
                        } else if (code == VisaCheckoutSdk.Status.OS_VERSION_NOT_SUPPORTED) {
                            promise.reject(E_CONFIGURE_OS_VERSION_NOT_SUPPORTED, "OS version not supported");
                        } else if (code == VisaCheckoutSdk.Status.FAIL_TO_INITIALIZE) {
                            promise.reject(E_CONFIGURE_FAIL_TO_INITIALIZE, "Failed to initialize");
                        } else if (code == VisaCheckoutSdk.Status.MISSING_PARAMETER) {
                            promise.reject(E_CONFIGURE_MISSING_PARAMETER, "Missing parameter");
                        } else if (code == VisaCheckoutSdk.Status.INTERNAL_ERROR) {
                            promise.reject(E_CONFIGURE_INTERNAL_ERROR, "Internal error");
                        }
                    }
                }
        );
    }

    @ReactMethod
    public void checkout(final double total, final int currency, final Promise promise) {
        Activity currentActivity = getCurrentActivity();
        String currencyString = RNVisaCheckoutHelper.getCurrencyStringFromConstant(currency);
        Intent intent = VisaCheckoutSdk.getCheckoutIntent(
                currentActivity,
                new BigDecimal(total),
                currencyString
        );
        mCheckoutPromise = promise;
        try {
            currentActivity.startActivityForResult(intent, Constants.VISA_CHECKOUT_REQUEST);
        } catch (Exception e) {
            mCheckoutPromise.reject(E_FAILED_TO_SHOW_CHECKOUT, "Visa Checkout failed to show checkout activity");
            mCheckoutPromise = null;
        }
    }
}
