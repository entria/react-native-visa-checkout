
#import "RNVisaCheckout.h"
#import "RNVisaCheckoutButton.h"
#import "RNVisaCheckoutUtils.h"

@interface RNVisaCheckout ()

@property(nonatomic, strong) VisaProfile *profile;

@end

@implementation RNVisaCheckout

- (UIView *)view {
    return [[RNVisaCheckoutButton alloc] initWithProfile:self.profile];
}

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
                     @"Qatar":@(VisaCountryQatar),
                     @"Invalid":@(-1)
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
                     @"Visa":@(VisaCardBrandVisa),
                     @"Invalid":@(-1)
                     },
             @"CardStyle":@{
                     @"Neutral":@(VisaCheckoutButtonStyleNeutral),
                     @"Standard":@(VisaCheckoutButtonStyleStandard)
                     }
             };
}

RCT_EXPORT_MODULE()

RCT_EXPORT_VIEW_PROPERTY(onCardCheckout, RCTDirectEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onCardCheckoutError, RCTDirectEventBlock);

- (NSArray<NSString *> *)supportedEvents
{
    return @[@"onCardCheckout", @"onCardCheckoutError"];
}

RCT_REMAP_METHOD(configureProfile,
                 withEnvironment:(nonnull NSNumber *)environment
                 apiKey:(NSString *)apiKey
                 profileName:(NSString *)profileName)
{
    VisaProfile *profile = [[VisaProfile alloc] initWithEnvironment:environment.integerValue
                                                             apiKey:apiKey
                                                        profileName:profileName];
    profile.datalevel = VisaDataLevelFull;
    
    [profile acceptedCardBrands: @[@(VisaCardBrandVisa),
                                   @(VisaCardBrandMastercard),
                                   @(VisaCardBrandDiscover),
                                   @(VisaCardBrandAmex),
                                   @(VisaCardBrandElectron),
                                   @(VisaCardBrandElo)]];
    self.profile = profile;
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
            resolve([RNVisaCheckoutUtils createJsonResponseFromCheckoutResult:result]);
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

RCT_CUSTOM_VIEW_PROPERTY(cardStyle, NSInteger, RNVisaCheckoutButton)
{
    enum VisaCheckoutButtonStyle argument = [RCTConvert NSInteger:json];
    [view setCardStyle:argument];
}

RCT_CUSTOM_VIEW_PROPERTY(cardAnimations, BOOL, RNVisaCheckoutButton)
{
    BOOL argument = [RCTConvert BOOL:json];
    [view setCardAnimations:argument];
}

RCT_CUSTOM_VIEW_PROPERTY(checkoutOptions, NSDictionary, RNVisaCheckoutButton)
{
    NSDictionary *options = [RCTConvert NSDictionary:json];
    [view updateCheckoutOptions:options];
}

@end
