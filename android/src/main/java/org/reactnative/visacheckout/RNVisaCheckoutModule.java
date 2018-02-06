
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
import com.visa.checkout.Environment;
import com.visa.checkout.Profile;
import com.visa.checkout.PurchaseInfo;
import com.visa.checkout.VisaCheckoutSdk;
import com.visa.checkout.VisaCheckoutSdkInitListener;
import com.visa.checkout.VisaPaymentSummary;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class RNVisaCheckoutModule extends ReactContextBaseJavaModule {

  private static final int VISA_CHECKOUT_REQUEST = 10012;

  private Promise mCheckoutPromise;

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
            }

            private Map<String, Object> getEnvConstants() {
                return Collections.unmodifiableMap(new HashMap<String, Object>() {
                    {
                        put("Production", Environment.PRODUCTION);
                        put("Sandbox", Environment.SANDBOX);
                    }
                });
            }
          private Map<String, Object> getCountryConstants() {
              return Collections.unmodifiableMap(new HashMap<String, Object>() {
                  {
                      put("Argentina", Profile.Country.AR);
                      put("Australia", Profile.Country.AU);
                      put("Brazil", Profile.Country.BR);
                      put("Canada", Profile.Country.CA);
                      put("Chile", Profile.Country.CL);
                      put("China", Profile.Country.CN);
                      put("Colombia", Profile.Country.CO);
                      put("France", Profile.Country.FR);
                      put("HongKong", Profile.Country.HK);
                      put("India", Profile.Country.IN);
                      put("Ireland", Profile.Country.IE);
                      put("Kuwait", Profile.Country.KW);
                      put("Malaysia", Profile.Country.MY);
                      put("Mexico", Profile.Country.MX);
                      put("NewZealand", Profile.Country.NZ);
                      put("Peru", Profile.Country.PE);
                      put("Poland", Profile.Country.PL);
                      put("Qatar", Profile.Country.QA);
                      put("SaudiArabia", Profile.Country.SA);
                      put("Singapore", Profile.Country.SG);
                      put("SouthAfrica", Profile.Country.ZA);
                      put("Spain", Profile.Country.ES);
                      put("Ukraine", Profile.Country.UA);
                      put("UnitedArabEmirates", Profile.Country.AE);
                      put("UnitedKingdom", Profile.Country.GB);
                      put("UnitedStates", Profile.Country.US);
                  }
              });
          }

          private Map<String, Object> getCurrencyConstants() {
              return Collections.unmodifiableMap(new HashMap<String, Object>() {
                  {
                      put("AED", PurchaseInfo.Currency.AED);
                      put("ARS", PurchaseInfo.Currency.ARS);
                      put("AUD", PurchaseInfo.Currency.AUD);
                      put("BRL", PurchaseInfo.Currency.BRL);
                      put("CAD", PurchaseInfo.Currency.CAD);
                      put("CLP", PurchaseInfo.Currency.CLP);
                      put("CNY", PurchaseInfo.Currency.CNY);
                      put("COP", PurchaseInfo.Currency.COP);
                      put("EUR", PurchaseInfo.Currency.EUR);
                      put("GBP", PurchaseInfo.Currency.GBP);
                      put("HKD", PurchaseInfo.Currency.HKD);
                      put("INR", PurchaseInfo.Currency.INR);
                      put("KWD", PurchaseInfo.Currency.KWD);
                      put("MXN", PurchaseInfo.Currency.MXN);
                      put("MYR", PurchaseInfo.Currency.MYR);
                      put("NZD", PurchaseInfo.Currency.NZD);
                      put("PEN", PurchaseInfo.Currency.PEN);
                      put("PLN", PurchaseInfo.Currency.PLN);
                      put("QAR", PurchaseInfo.Currency.QAR);
                      put("SAR", PurchaseInfo.Currency.SAR);
                      put("SGD", PurchaseInfo.Currency.SGD);
                      put("UAH", PurchaseInfo.Currency.UAH);
                      put("USD", PurchaseInfo.Currency.USD);
                      put("ZAR", PurchaseInfo.Currency.ZAR);
                  }
              });
          }

          private Map<String, Object> getCardConstants() {
              return Collections.unmodifiableMap(new HashMap<String, Object>() {
                  {
                      put("Amex", Profile.CardBrand.AMEX);
                      put("Discover", Profile.CardBrand.DISCOVER);
                      put("Electron", Profile.CardBrand.ELECTRON);
                      put("Elo", Profile.CardBrand.ELO);
                      put("Mastercard", Profile.CardBrand.MASTERCARD);
                      put("Visa", Profile.CardBrand.VISA);
                  }
              });
          }

        });
    }

  private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
        if (requestCode == VISA_CHECKOUT_REQUEST) {
            if (mCheckoutPromise != null) {
                if (resultCode == Activity.RESULT_CANCELED) {
                    mCheckoutPromise.reject(E_CHECKOUT_CANCELLED, "Visa Checkout checkout was cancelled");
                } else if (resultCode == Activity.RESULT_OK && intent != null) {
                    final VisaPaymentSummary paymentSummary = intent.getParcelableExtra(VisaCheckoutSdk.INTENT_PAYMENT_SUMMARY);
                    mCheckoutPromise.resolve(getPaymentSummaryMap(paymentSummary));
                }
                else if (resultCode == VisaCheckoutSdk.ResultCode.RESULT_INTERNAL_ERROR) {
                    mCheckoutPromise.reject(E_CHECKOUT_INTERNAL_ERROR, "Visa Checkout internal error");
                } else if (resultCode == VisaCheckoutSdk.ResultCode.RESULT_INITIALIZED_FAILED) {
                    mCheckoutPromise.reject(E_CHECKOUT_INITIALIZED_FAILED, "Visa Checkout initialized failed");
                } else if (resultCode == VisaCheckoutSdk.ResultCode.RESULT_SDK_NOT_INITIALIZED) {
                    mCheckoutPromise.reject(E_CHECKOUT_SDK_NOT_INITIALIZED, "Visa Checkout SDK not initialized");
                } else if (resultCode == VisaCheckoutSdk.ResultCode.RESULT_MISSING_PARAMETER) {
                    mCheckoutPromise.reject(E_CHECKOUT_MISSING_PARAMETER, "Visa Checkout missing parameter");
                } else if (resultCode == VisaCheckoutSdk.ResultCode.RESULT_FAIL_TO_LAUNCH) {
                    mCheckoutPromise.reject(E_CHECKOUT_FAIL_TO_LAUNCH, "Visa Checkout failed to launch");
                } else if (resultCode == VisaCheckoutSdk.ResultCode.RESULT_CLOSED_CALLED) {
                    mCheckoutPromise.reject(E_CHECKOUT_CLOSED_CALLED, "Visa Checkout closed called");
                }
                mCheckoutPromise = null;
            }
        }
    }
  };

  @ReactMethod
  public void configureProfile(final String environment, final String apiKey, final String profileName, final Promise promise) {
      VisaCheckoutSdk.init(getReactApplicationContext(), environment,
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
  public void checkout(final double total, final String currency, final Promise promise) {
    Activity currentActivity = getCurrentActivity();
    Intent intent = VisaCheckoutSdk.getCheckoutIntent(
            currentActivity,
            new BigDecimal(total),
            currency
    );
    mCheckoutPromise = promise;
    try {
        currentActivity.startActivityForResult(intent, VISA_CHECKOUT_REQUEST);
    } catch (Exception e) {
        mCheckoutPromise.reject(E_FAILED_TO_SHOW_CHECKOUT, "Visa Checkout failed to show checkout activity");
        mCheckoutPromise = null;
    }
  }

  private WritableMap getPaymentSummaryMap(VisaPaymentSummary paymentSummary) {
      WritableMap map = Arguments.createMap();
      map.putString("callId", paymentSummary.getCallId() != null ? paymentSummary.getCallId() : "null");
      map.putString("encryptedKey", paymentSummary.getEncKey() != null ? paymentSummary.getEncKey() : "null");
      map.putString("encryptedPaymentData", paymentSummary.getEncPaymentData() != null ? paymentSummary.getEncPaymentData() : "null");
      map.putString("lastFourDigits", paymentSummary.getLastFourDigits() != null ? paymentSummary.getLastFourDigits() : "null");
      map.putString("paymentMethodType", paymentSummary.getPaymentMethodType() != null ? paymentSummary.getPaymentMethodType() : "null");
      map.putString("postalCode", paymentSummary.getPostalCode() != null ? paymentSummary.getPostalCode() : "null");
      map.putString("cardBrand", paymentSummary.getCardBrand() != null ? paymentSummary.getCardBrand() : "null");
      map.putString("country", paymentSummary.getCountryCode() != null ? paymentSummary.getCountryCode() : "null");
      return map;
  }
}