
#import "RNVisaCheckout.h"

@implementation RNVisaCheckout

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

- (NSDictionary *)constantsToExport
{
    return @{
             @"ConfigStatus":@{
                     @"DebugNotSupported":@(VisaCheckoutConfigStatusDebugModeNotSupported),
                     @"InternalError":@(VisaCheckoutConfigStatusInternalError),
                     @"InvalidAPIKey":@(VisaCheckoutConfigStatusInvalidAPIKey),
                     @"NetworkFailure":@(VisaCheckoutConfigStatusNetworkFailure),
                     @"NoCommonSupportedOrientations":@(VisaCheckoutConfigStatusNoCommonSupportedOrientations),
                     @"SdkVersionDeprecation":@(VisaCheckoutConfigStatusSdkVersionDeprecation),
                     @"Success":@(VisaCheckoutConfigStatusSuccess)
                     },
             @"ResultStatus":@{
                     @"DuplicateCheckoutAttempt":@(VisaCheckoutResultStatusDuplicateCheckoutAttempt),
                     @"InternalError":@(VisaCheckoutResultStatusInternalError),
                     @"NotConfigured":@(VisaCheckoutResultStatusNotConfigured),
                     @"Success":@(VisaCheckoutResultStatusSuccess),
                     @"Cancelled":@(VisaCheckoutResultStatusUserCancelled)
                     },
             @"Environment":@{
                     @"Production":@(VisaEnvironmentProduction),
                     @"Sandbox":@(VisaEnvironmentSandbox)
                     },
             @"Country":@{
                     @"Argentina":@(VisaCountryArgentina),
                     @"Australia":@(VisaCountryAustralia),
                     @"Brazil":@(VisaCountryBrazil),
                     @"Canada":@(VisaCountryCanada),
                     @"Chile":@(VisaCountryChile),
                     @"China":@(VisaCountryChina),
                     @"Colombia":@(VisaCountryColombia),
                     @"France":@(VisaCountryFrance),
                     @"HongKong":@(VisaCountryHongKong),
                     @"India":@(VisaCountryIndia),
                     @"Ireland":@(VisaCountryIreland),
                     @"Malaysia":@(VisaCountryMalaysia),
                     @"Mexico":@(VisaCountryMexico),
                     @"NewZealand":@(VisaCountryNewZealand),
                     @"Peru":@(VisaCountryPeru),
                     @"Poland":@(VisaCountryPoland),
                     @"Singapore":@(VisaCountrySingapore),
                     @"SouthAfrica":@(VisaCountrySouthAfrica),
                     @"Spain":@(VisaCountrySpain),
                     @"UnitedArabEmirates":@(VisaCountryUnitedArabEmirates),
                     @"UnitedKingdom":@(VisaCountryUnitedKingdom),
                     @"UnitedStates":@(VisaCountryUnitedStates),
                     @"Ukraine":@(VisaCountryUkraine),
                     @"Kuwait":@(VisaCountryKuwait),
                     @"SaudiArabia":@(VisaCountrySaudiArabia),
                     @"Qatar":@(VisaCountryQatar)
                     },
             @"Currency":@{
                     @"AED":@(VisaCurrencyAed),
                     @"ARS":@(VisaCurrencyArs),
                     @"AUD":@(VisaCurrencyAud),
                     @"BRL":@(VisaCurrencyBrl),
                     @"CAD":@(VisaCurrencyCad),
                     @"CLP":@(VisaCurrencyClp),
                     @"CNY":@(VisaCurrencyCny),
                     @"COP":@(VisaCurrencyCop),
                     @"EUR":@(VisaCurrencyEur),
                     @"GBP":@(VisaCurrencyGbp),
                     @"HKD":@(VisaCurrencyHkd),
                     @"INR":@(VisaCurrencyInr),
                     @"KWD":@(VisaCurrencyKwd),
                     @"MXN":@(VisaCurrencyMxn),
                     @"MYR":@(VisaCurrencyMyr),
                     @"NZD":@(VisaCurrencyNzd),
                     @"PEN":@(VisaCurrencyPen),
                     @"PLN":@(VisaCurrencyPln),
                     @"QAR":@(VisaCurrencyQar),
                     @"SAR":@(VisaCurrencySar),
                     @"SGD":@(VisaCurrencySgd),
                     @"UAH":@(VisaCurrencyUah),
                     @"USD":@(VisaCurrencyUsd),
                     @"ZAR":@(VisaCurrencyZar)
                     },
             @"Card":@{
                     @"Amex":@(VisaCardBrandAmex),
                     @"Discover":@(VisaCardBrandDiscover),
                     @"Electron":@(VisaCardBrandElectron),
                     @"Elo":@(VisaCardBrandElo),
                     @"Mastercard":@(VisaCardBrandMastercard),
                     @"Visa":@(VisaCardBrandVisa)
                     }
             };
}

RCT_EXPORT_MODULE()

RCT_REMAP_METHOD(configureProfile,
                 withEnvironment:(nonnull NSNumber *)environment
                 apiKey:(NSString *)apiKey
                 profileName:(NSString *)profileName
                 resolver:(RCTPromiseResolveBlock)resolve
                 rejecter:(RCTPromiseRejectBlock)reject)
{
    [VisaCheckoutSDK configureWithEnvironment:environment.integerValue
                                       apiKey:apiKey
                                  profileName:profileName
                                       result:^(NSInteger result) {
                                           VisaCheckoutConfigStatus statusCode = result;
                                           if (statusCode == VisaCheckoutConfigStatusSuccess) {
                                               resolve(@{@"status":@(result)});
                                           } else {
                                               reject(@(result), @"Error during environment configuration of Visa checkout", nil);
                                           }
                                       }];
}

RCT_REMAP_METHOD(checkout,
                 withTotal:(nonnull NSNumber *)total
                 currency:(nonnull NSNumber *)currency
                 resolver:(RCTPromiseResolveBlock)resolve
                 rejecter:(RCTPromiseRejectBlock)reject)
{
    VisaCurrencyAmount *amount = [[VisaCurrencyAmount alloc] initWithDouble:total.doubleValue];
    [VisaCheckoutSDK checkoutWithTotal:amount currency:currency.integerValue completion:^(VisaCheckoutResult *result) {
        if (result.statusCode == VisaCheckoutResultStatusSuccess) {
            resolve([self createJsonResponseFromCheckoutResult:result]);
        } else {
            reject(@(result.statusCode), @"Error during Visa checkout", nil);
        }
    }];
}

- (NSDictionary *) createJsonResponseFromCheckoutResult:(VisaCheckoutResult *)result {
    NSMutableDictionary *response = [[NSMutableDictionary alloc] init];
    response[@"callId"] = result.callId ? result.callId : @"null";
    response[@"cardBrand"] = [NSNumber numberWithInteger:result.cardBrand];
    response[@"country"] = [NSNumber numberWithInteger:result.country];
    response[@"encryptedKey"] = result.encryptedKey ? result.encryptedKey : @"null";
    response[@"encryptedPaymentData"] = result.encryptedPaymentData ? result.encryptedPaymentData : @"null";
    response[@"lastFourDigits"] = result.lastFourDigits ? result.lastFourDigits : @"null";
    response[@"paymentMethodType"] = result.paymentMethodType ? result.paymentMethodType : @"null";
    response[@"postalCode"] = result.postalCode ? result.postalCode : @"null";
    return response;
}

@end



