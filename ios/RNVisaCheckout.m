
#import "RNVisaCheckout.h"

@implementation RNVisaCheckout

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

- (NSDictionary *)constantsToExport
{
    return @{
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
                                               NSString *errorString = nil;
                                               NSString *errorCode = nil;
                                               switch (statusCode) {
                                                   case VisaCheckoutConfigStatusDebugModeNotSupported:
                                                       errorString = @"Debug mode not supported error";
                                                       errorCode = @"E_CONFIGURE_DEBUG_MODE";
                                                       break;
                                                   case VisaCheckoutConfigStatusInternalError:
                                                       errorString = @"Visa Checkout internal error";
                                                       errorCode = @"E_CONFIGURE_INTERNAL_ERROR";
                                                       break;
                                                   case VisaCheckoutConfigStatusInvalidAPIKey:
                                                       errorString = @"Invalid API key error";
                                                       errorCode = @"E_CONFIGURE_INVALID_API_KEY";
                                                       break;
                                                   case VisaCheckoutConfigStatusInvalidProfileName:
                                                       errorString = @"Invalid profile name error";
                                                       errorCode = @"E_CONFIGURE_INVALID_PROFILE_NAME";
                                                       break;
                                                   case VisaCheckoutConfigStatusNetworkFailure:
                                                       errorString = @"Network failure error";
                                                       errorCode = @"E_CONFIGURE_NETWORK_FAILURE";
                                                       break;
                                                   case VisaCheckoutConfigStatusNoCommonSupportedOrientations:
                                                       errorString = @"No common supported orientations error";
                                                       errorCode = @"E_CONFIGURE_NO_COMMON_ORIENTATIONS";
                                                       break;
                                                   case VisaCheckoutConfigStatusSdkVersionDeprecation:
                                                       errorString = @"SDK version deprecation error";
                                                       errorCode = @"E_CONFIGURE_UNSUPPORTED_SDK_VERSION";
                                                       break;
                                                   default:
                                                       break;
                                               }
                                               reject(errorCode, errorString, nil);
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
            VisaCheckoutResultStatus resultStatus = result.statusCode;
            NSString *errorString = nil;
            NSString *errorCode = nil;
            switch (resultStatus) {
                case VisaCheckoutResultStatusInternalError:
                    errorString = @"Checkout internal error";
                    errorCode = @"E_CHECKOUT_INTERNAL_ERROR";
                    break;
                case VisaCheckoutResultStatusNotConfigured:
                    errorString = @"Checkout not configured";
                    errorCode = @"E_CHECKOUT_NOT_CONFIGURED";
                    break;
                case VisaCheckoutResultStatusUserCancelled:
                    errorString = @"User cancelled";
                    errorCode = @"E_CHECKOUT_CANCELLED";
                    break;
                case VisaCheckoutResultStatusDuplicateCheckoutAttempt:
                    errorString = @"Duplicate checkout attempt";
                    errorCode = @"E_CHECKOUT_DUPLICATE_CHECKOUT";
                    break;
                default:
                    break;
            }
            reject(errorCode, errorString, nil);
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
