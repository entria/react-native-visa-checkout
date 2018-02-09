//
//  RNVisaCheckoutUtils.h
//  RNVisaCheckout
//
//  Created by Joao Guilherme Daros Fidelis on 07/02/18.
//  Copyright © 2018 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>

@class VisaCheckoutResult;

@interface RNVisaCheckoutUtils : NSObject

+ (NSDictionary *) createJsonResponseFromCheckoutResult:(VisaCheckoutResult *)result;

@end
