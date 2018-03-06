package org.reactnative.visacheckout;

import android.view.ViewGroup;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.UIManagerModule;
import com.visa.checkout.Environment;
import com.visa.checkout.Profile;
import com.visa.checkout.PurchaseInfo;
import com.visa.checkout.VisaPaymentSummary;

import org.reactnative.visacheckout.events.CardCheckoutEvent;
import org.reactnative.visacheckout.events.CardCheckoutEventError;

/**
 * Created by jgfidelis on 07/02/18.
 */

public class RNVisaCheckoutHelper {

    public static String getEnvironmentStringFromConstant(int envCode) {
        if (envCode == Constants.ENVIRONMENT_PRODUCTION) {
            return Environment.PRODUCTION;
        } else if (envCode == Constants.ENVIRONMENT_SANDBOX){
            return Environment.SANDBOX;
        } else {
            return "";
        }
    }

    public static String getCountryStringFromConstant(int countryCode) {
        String countryString = "";
        switch (countryCode) {
            case Constants.COUNTRY_ARGENTINA:
                countryString =  Profile.Country.AR;
                break;
            case Constants.COUNTRY_AUSTRALIA:
                countryString =  Profile.Country.AU;
                break;
            case Constants.COUNTRY_BRAZIL:
                countryString =  Profile.Country.BR;
                break;
            case Constants.COUNTRY_CANADA:
                countryString =  Profile.Country.CA;
                break;
            case Constants.COUNTRY_CHILE:
                countryString =  Profile.Country.CL;
                break;
            case Constants.COUNTRY_CHINA:
                countryString =  Profile.Country.CN;
                break;
            case Constants.COUNTRY_COLOMBIA:
                countryString =  Profile.Country.CO;
                break;
            case Constants.COUNTRY_FRANCE:
                countryString =  Profile.Country.FR;
                break;
            case Constants.COUNTRY_HONGKONG:
                countryString =  Profile.Country.HK;
                break;
            case Constants.COUNTRY_INDIA:
                countryString =  Profile.Country.IN;
                break;
            case Constants.COUNTRY_IRELAND:
                countryString =  Profile.Country.IE;
                break;
            case Constants.COUNTRY_KUWAIT:
                countryString =  Profile.Country.KW;
                break;
            case Constants.COUNTRY_MALAYSIA:
                countryString =  Profile.Country.MY;
                break;
            case Constants.COUNTRY_MEXICO:
                countryString =  Profile.Country.MX;
                break;
            case Constants.COUNTRY_NEW_ZEALAND:
                countryString =  Profile.Country.NZ;
                break;
            case Constants.COUNTRY_PERU:
                countryString =  Profile.Country.PE;
                break;
            case Constants.COUNTRY_POLAND:
                countryString =  Profile.Country.PL;
                break;
            case Constants.COUNTRY_QATAR:
                countryString =  Profile.Country.QA;
                break;
            case Constants.COUNTRY_SAUDI_ARABIA:
                countryString =  Profile.Country.SA;
                break;
            case Constants.COUNTRY_SINGAPORE:
                countryString =  Profile.Country.SG;
                break;
            case Constants.COUNTRY_SOUTH_AFRICA:
                countryString =  Profile.Country.ZA;
                break;
            case Constants.COUNTRY_SPAIN:
                countryString =  Profile.Country.ES;
                break;
            case Constants.COUNTRY_UKRAINE:
                countryString =  Profile.Country.UA;
                break;
            case Constants.COUNTRY_UNITED_ARAB_EMIRATES:
                countryString =  Profile.Country.AE;
                break;
            case Constants.COUNTRY_UNITED_KINGDOM:
                countryString =  Profile.Country.GB;
                break;
            case Constants.COUNTRY_UNITED_STATES:
                countryString =  Profile.Country.US;
                break;
            default:
                break;
        }
        return countryString;
    }

    public static int getCountryCodeFromString(String country) {
        int countryCode = -1;
        if (country == null) return countryCode;
        switch (country) {
            case Profile.Country.AR:
                countryCode = Constants.COUNTRY_ARGENTINA;
                break;
            case Profile.Country.AU:
                countryCode = Constants.COUNTRY_AUSTRALIA;
                break;
            case Profile.Country.BR:
                countryCode = Constants.COUNTRY_BRAZIL;
                break;
            case Profile.Country.CA:
                countryCode = Constants.COUNTRY_CANADA;
                break;
            case Profile.Country.CL:
                countryCode = Constants.COUNTRY_CHILE;
                break;
            case Profile.Country.CN:
                countryCode = Constants.COUNTRY_CHINA;
                break;
            case Profile.Country.CO:
                countryCode = Constants.COUNTRY_COLOMBIA;
                break;
            case Profile.Country.FR:
                countryCode = Constants.COUNTRY_FRANCE;
                break;
            case Profile.Country.HK:
                countryCode = Constants.COUNTRY_HONGKONG;
                break;
            case Profile.Country.IN:
                countryCode = Constants.COUNTRY_INDIA;
                break;
            case Profile.Country.IE:
                countryCode = Constants.COUNTRY_IRELAND;
                break;
            case Profile.Country.KW:
                countryCode = Constants.COUNTRY_KUWAIT;
                break;
            case Profile.Country.MY:
                countryCode = Constants.COUNTRY_MALAYSIA;
                break;
            case Profile.Country.MX:
                countryCode = Constants.COUNTRY_MEXICO;
                break;
            case Profile.Country.NZ:
                countryCode = Constants.COUNTRY_NEW_ZEALAND;
                break;
            case Profile.Country.PE:
                countryCode = Constants.COUNTRY_PERU;
                break;
            case Profile.Country.PL:
                countryCode = Constants.COUNTRY_POLAND;
                break;
            case Profile.Country.QA:
                countryCode = Constants.COUNTRY_QATAR;
                break;
            case Profile.Country.SA:
                countryCode = Constants.COUNTRY_SAUDI_ARABIA;
                break;
            case Profile.Country.SG:
                countryCode = Constants.COUNTRY_SINGAPORE;
                break;
            case Profile.Country.ZA:
                countryCode = Constants.COUNTRY_SOUTH_AFRICA;
                break;
            case Profile.Country.ES:
                countryCode = Constants.COUNTRY_SPAIN;
                break;
            case Profile.Country.UA:
                countryCode = Constants.COUNTRY_UKRAINE;
                break;
            case Profile.Country.AE:
                countryCode = Constants.COUNTRY_UNITED_ARAB_EMIRATES;
                break;
            case Profile.Country.GB:
                countryCode = Constants.COUNTRY_UNITED_KINGDOM;
                break;
            case Profile.Country.US:
                countryCode = Constants.COUNTRY_UNITED_STATES;
                break;
            default:
                break;
        }
        return countryCode;
    }

    public static int getCardBrandCodeFromString(String cardBrand) {
        int cardCode = -1;
        if (cardBrand == null) return cardCode;
        switch (cardBrand) {
            case Profile.CardBrand.AMEX:
                cardCode = Constants.CARD_BRAND_AMEX;
                break;
            case Profile.CardBrand.DISCOVER:
                cardCode = Constants.CARD_BRAND_DISCOVER;
                break;
            case Profile.CardBrand.ELECTRON:
                cardCode = Constants.CARD_BRAND_ELECTRON;
                break;
            case Profile.CardBrand.ELO:
                cardCode = Constants.CARD_BRAND_ELO;
                break;
            case Profile.CardBrand.MASTERCARD:
                cardCode = Constants.CARD_BRAND_MASTERCARD;
                break;
            case Profile.CardBrand.VISA:
                cardCode = Constants.CARD_BRAND_VISA;
                break;
            default:
                break;
        }

        return cardCode;
    }

    public static String getCardBrandStringFromConstant(int cardCode) {
        String cardString = "";
        switch (cardCode) {
            case Constants.CARD_BRAND_AMEX:
                cardString = Profile.CardBrand.AMEX;
                break;
            case Constants.CARD_BRAND_DISCOVER:
                cardString = Profile.CardBrand.DISCOVER;
                break;
            case Constants.CARD_BRAND_ELECTRON:
                cardString = Profile.CardBrand.ELECTRON;
                break;
            case Constants.CARD_BRAND_ELO:
                cardString = Profile.CardBrand.ELO;
                break;
            case Constants.CARD_BRAND_MASTERCARD:
                cardString = Profile.CardBrand.MASTERCARD;
                break;
            case Constants.CARD_BRAND_VISA:
                cardString = Profile.CardBrand.VISA;
                break;
            default:
                break;
        }

        return cardString;
    }

    public static String getCurrencyStringFromConstant(int currencyCode) {
        String currencyString = "";
        switch (currencyCode) {
            case Constants.CURRENCY_AED:
                currencyString = PurchaseInfo.Currency.AED;
                break;
            case Constants.CURRENCY_ARS:
                currencyString = PurchaseInfo.Currency.ARS;
                break;
            case Constants.CURRENCY_AUD:
                currencyString = PurchaseInfo.Currency.AUD;
                break;
            case Constants.CURRENCY_BRL:
                currencyString = PurchaseInfo.Currency.BRL;
                break;
            case Constants.CURRENCY_CAD:
                currencyString = PurchaseInfo.Currency.CAD;
                break;
            case Constants.CURRENCY_CLP:
                currencyString = PurchaseInfo.Currency.CLP;
                break;
            case Constants.CURRENCY_CNY:
                currencyString = PurchaseInfo.Currency.CNY;
                break;
            case Constants.CURRENCY_COP:
                currencyString = PurchaseInfo.Currency.COP;
                break;
            case Constants.CURRENCY_EUR:
                currencyString = PurchaseInfo.Currency.EUR;
                break;
            case Constants.CURRENCY_GBP:
                currencyString = PurchaseInfo.Currency.GBP;
                break;
            case Constants.CURRENCY_HKD:
                currencyString = PurchaseInfo.Currency.HKD;
                break;
            case Constants.CURRENCY_INR:
                currencyString = PurchaseInfo.Currency.INR;
                break;
            case Constants.CURRENCY_KWD:
                currencyString = PurchaseInfo.Currency.KWD;
                break;
            case Constants.CURRENCY_MXN:
                currencyString = PurchaseInfo.Currency.MXN;
                break;
            case Constants.CURRENCY_MYR:
                currencyString = PurchaseInfo.Currency.MYR;
                break;
            case Constants.CURRENCY_NZD:
                currencyString = PurchaseInfo.Currency.NZD;
                break;
            case Constants.CURRENCY_PEN:
                currencyString = PurchaseInfo.Currency.PEN;
                break;
            case Constants.CURRENCY_PLN:
                currencyString = PurchaseInfo.Currency.PLN;
                break;
            case Constants.CURRENCY_QAR:
                currencyString = PurchaseInfo.Currency.QAR;
                break;
            case Constants.CURRENCY_SAR:
                currencyString = PurchaseInfo.Currency.SAR;
                break;
            case Constants.CURRENCY_SGD:
                currencyString = PurchaseInfo.Currency.SGD;
                break;
            case Constants.CURRENCY_UAH:
                currencyString = PurchaseInfo.Currency.UAH;
                break;
            case Constants.CURRENCY_USD:
                currencyString = PurchaseInfo.Currency.USD;
                break;
            case Constants.CURRENCY_ZAR:
                currencyString = PurchaseInfo.Currency.ZAR;
                break;
            default:
                break;
        }

        return currencyString;
    }

    public static WritableMap getPaymentSummaryMap(VisaPaymentSummary paymentSummary) {
        WritableMap map = Arguments.createMap();
        map.putString("callId", paymentSummary.getCallId() != null ? paymentSummary.getCallId() : "null");
        map.putString("encryptedKey", paymentSummary.getEncKey() != null ? paymentSummary.getEncKey() : "null");
        map.putString("encryptedPaymentData", paymentSummary.getEncPaymentData() != null ? paymentSummary.getEncPaymentData() : "null");
        map.putString("lastFourDigits", paymentSummary.getLastFourDigits() != null ? paymentSummary.getLastFourDigits() : "null");
        map.putString("paymentMethodType", paymentSummary.getPaymentMethodType() != null ? paymentSummary.getPaymentMethodType() : "null");
        map.putString("postalCode", paymentSummary.getPostalCode() != null ? paymentSummary.getPostalCode() : "null");
        map.putInt("cardBrand", RNVisaCheckoutHelper.getCardBrandCodeFromString(paymentSummary.getCardBrand()));
        map.putInt("country", RNVisaCheckoutHelper.getCountryCodeFromString(paymentSummary.getCountryCode()));
        return map;
    }

    public static void emitCardCheckoutEvent(int viewTag, VisaPaymentSummary paymentSummary, ReactContext ctx) {
        CardCheckoutEvent event = CardCheckoutEvent.obtain(viewTag, paymentSummary);
        ctx.getNativeModule(UIManagerModule.class).getEventDispatcher().dispatchEvent(event);
    }

    public static void emitCardCheckoutErrorEvent(int viewTag, String errorCode, String errorMessage, ReactContext ctx) {
        CardCheckoutEventError event = CardCheckoutEventError.obtain(viewTag, errorCode, errorMessage);
        ctx.getNativeModule(UIManagerModule.class).getEventDispatcher().dispatchEvent(event);
    }
}
