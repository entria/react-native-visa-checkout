//
//  RNVisaCheckoutUtils.m
//  RNVisaCheckout
//
//  Created by Joao Guilherme Daros Fidelis on 07/02/18.
//  Copyright © 2018 Facebook. All rights reserved.
//

#import "RNVisaCheckoutUtils.h"
#import <VisaCheckoutSDK/VisaCheckout.h>

@implementation RNVisaCheckoutUtils

+ (NSDictionary *) createJsonResponseFromCheckoutResult:(VisaCheckoutResult *)result {
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
