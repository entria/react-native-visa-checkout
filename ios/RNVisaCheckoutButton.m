//
//  RNVisaCheckoutButton.m
//  RNVisaCheckout
//
//  Created by Joao Guilherme Daros Fidelis on 07/02/18.
//  Copyright © 2018 Facebook. All rights reserved.
//

#import "RNVisaCheckoutButton.h"
#import <React/UIView+React.h>
#import <VisaCheckoutSDK/VisaCheckout.h>
#import "RNVisaCheckoutUtils.h"
#import <math.h>

@interface RNVisaCheckoutButton()

@property (nonatomic, strong) VisaCheckoutButton *visaCheckoutButton;
@property (nonatomic, strong) VisaProfile *profile;

@property (nonatomic, assign) float transactionTotal;
@property (nonatomic, assign) NSInteger currencyCode;

@property (nonatomic, copy) RCTDirectEventBlock onCardCheckout;
@property (nonatomic, copy) RCTDirectEventBlock onCardCheckoutError;

@end

@implementation RNVisaCheckoutButton

-(instancetype)initWithProfile:(VisaProfile *)profile {
    if (self = [super init]) {
        self.visaCheckoutButton = [[VisaCheckoutButton alloc] init];
        self.profile = profile;
    }
    return self;
}

- (void)setCardStyle:(NSInteger)cardStyle{
    _cardStyle = cardStyle;
    self.visaCheckoutButton.style = _cardStyle;
}

- (void)setCardAnimations:(BOOL)cardAnimations {
    _cardAnimations = cardAnimations;
    self.visaCheckoutButton.enableAnimation = _cardAnimations;
}

- (void)updateCheckoutOptions:(NSDictionary *)options {
    self.transactionTotal = [options[@"total"] doubleValue];
    self.currencyCode = [options[@"currency"] integerValue];
    [self updateOnCheckout];
}

- (void) updateOnCheckout {
    VisaCurrencyAmount *amount = [[VisaCurrencyAmount alloc] initWithDouble:trunc(self.transactionTotal * 100) / 100];
    VisaPurchaseInfo *purchaseInfo = [[VisaPurchaseInfo alloc] initWithTotal:amount currency:self.currencyCode];
    UIViewController *vc = [[[[UIApplication sharedApplication] delegate] window] rootViewController];
    [self.visaCheckoutButton onCheckoutWithProfile:self.profile
                                      purchaseInfo:purchaseInfo
                          presentingViewController:vc
                                        completion:^(VisaCheckoutResult * _Nonnull result) {
                                            if (result.statusCode == VisaCheckoutResultStatusSuccess) {
                                                if (_onCardCheckout) {
                                                    _onCardCheckout([RNVisaCheckoutUtils createJsonResponseFromCheckoutResult:result]);
                                                }
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
                                                if (_onCardCheckoutError) {
                                                    NSMutableDictionary *response = [[NSMutableDictionary alloc] init];
                                                    response[@"code"] = errorCode;
                                                    response[@"message"] = errorString;
                                                    _onCardCheckoutError(response);
                                                }
                                            }
                                            }];
}

- (void)layoutSubviews
{
    [super layoutSubviews];
    self.visaCheckoutButton.frame = self.bounds;
    [self addSubview:self.visaCheckoutButton];
}

@end
